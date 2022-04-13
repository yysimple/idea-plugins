package com.simple.idea.plugin.inspection.rule;

import com.intellij.codeInsight.FileModificationService;
import com.intellij.codeInsight.daemon.impl.actions.AddImportAction;
import com.intellij.codeInspection.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-11 21:54
 **/
public class RandomRule extends AbstractBaseJavaLocalInspectionTool {

    /**
     * 继承这个方法在对应我们的java文件中，就会开始扫描
     *
     * @param holder
     * @param isOnTheFly
     * @param session
     * @return
     */
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
        return new JavaElementVisitor() {
            /**
             * 这里的 PsiNewExpression 是用来在文件中搜索new 相关的信息
             */
            @Override
            public void visitNewExpression(PsiNewExpression expression) {
                // 如果表达式全限定名 == 这个 那么 将提示一些信息
                if ("java.util.Random".equals(Objects.requireNonNull(expression.getClassReference()).getQualifiedName())) {
                    holder.registerProblem(expression,
                            "请不要使用伪随机API-Random",
                            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                            new ReplacePseudorandomGeneratorQuickFix()
                    );
                }
            }
        };
    }

    class ReplacePseudorandomGeneratorQuickFix implements LocalQuickFix {

        /**
         * 修复插件的名称，提示的信息，光标放在对应提示处就可以触发
         */
        @Override
        public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getFamilyName() {
            return "!Fix：replace by SecureRandom";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiNewExpression newExp = ObjectUtils.tryCast(descriptor.getPsiElement(), PsiNewExpression.class);
            if (newExp == null) {
                return;
            }
            // 这里的parent其实就是 new XXX()前面的Random r 相关的信息
            PsiElement parent = newExp.getParent();
            // Random r 中的 Random，这里可能是 field、局部变量等
            PsiTypeElement typeElement = null;
            if (parent instanceof PsiAssignmentExpression) {
                // 变量初始化, parent指向声明点
                PsiAssignmentExpression assignmentExpression = ObjectUtils.tryCast(parent, PsiAssignmentExpression.class);
                if (assignmentExpression != null) {
                    PsiReference lRef = assignmentExpression.getLExpression().getReference();
                    if (lRef != null) {
                        parent = lRef.resolve();
                    }
                }
            }

            if (parent instanceof PsiLocalVariable) {
                // 变量声明同时初始化（局部变量）
                PsiLocalVariable localVariable = ObjectUtils.tryCast(parent, PsiLocalVariable.class);
                if (localVariable != null) {
                    typeElement = localVariable.getTypeElement();
                }
            } else if (parent instanceof PsiField) {
                // field 变量
                PsiField field = ObjectUtils.tryCast(parent, PsiField.class);
                if (field != null) {
                    typeElement = field.getTypeElement();
                }
            }

            if (typeElement == null) {
                return;
            }

            PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
            // 修改类类型，也就是 Random r -> SecureRandom r
            typeElement.replace(factory.createTypeElementFromText("SecureRandom", null));
            // 构建新的 new 的表达式，之后进行文本替换
            PsiNewExpression secureNewExp = (PsiNewExpression) factory.createExpressionFromText("new SecureRandom()", null);
            newExp.replace(secureNewExp);

            // point NewExpression to element in file
            secureNewExp = ObjectUtils.tryCast(((PsiVariable) parent).getInitializer(), PsiNewExpression.class);
            if (secureNewExp == null) {
                return;
            }

            // import java.security.SecureRandom
            try {
                PsiFile file = descriptor.getPsiElement().getContainingFile();
                // 拿到文件的元数据信息，整个java文件的文本信息
                Document document = PsiDocumentManager.getInstance(project).getDocument(file);
                PsiJavaCodeReferenceElement secureRefElem = secureNewExp.getClassOrAnonymousClassReference();

                if (document != null && secureRefElem != null) {
                    Editor[] editors = EditorFactory.getInstance().getEditors(document, project);
                    if (!FileModificationService.getInstance().prepareFileForWrite(file)) {
                        return;
                    }
                    if (secureRefElem.getReferenceName() == null) {
                        return;
                    }

                    ApplicationManager.getApplication().runWriteAction(() -> {
                        PsiClass[] classes = PsiShortNamesCache.getInstance(project).getClassesByName(secureRefElem.getReferenceName(), secureRefElem.getResolveScope());
                        for (PsiClass clazz : classes) {
                            if ("java.security.SecureRandom".equals(clazz.getQualifiedName())) {
                                (new AddImportAction(project, secureRefElem, editors[0], new PsiClass[]{clazz}) {
                                    @Override
                                    protected void bindReference(PsiReference ref, PsiClass targetClass) {
                                        ref.bindToElement(targetClass);
                                    }
                                }).execute();
                            }
                        }
                    });
                }
            } catch (Exception ignore) {
            }
        }
    }
}
