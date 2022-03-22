package com.simple.idea.plugin.xo2xo.domain.service.impl;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.simple.idea.plugin.xo2xo.domain.model.GenerateContext;
import com.simple.idea.plugin.xo2xo.domain.model.GetObjConfigDO;
import com.simple.idea.plugin.xo2xo.domain.model.SetObjConfigDO;
import com.simple.idea.plugin.xo2xo.domain.service.AbstractGenerateXxo2Xxo;
import com.simple.idea.plugin.xo2xo.infrastructure.Utils;
import com.simple.idea.plugin.xo2xo.ui.ConvertSettingUI;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:03
 **/
public class GenerateXxo2XxoImpl extends AbstractGenerateXxo2Xxo {

    @Override
    protected GenerateContext getGenerateContext(Project project, DataContext dataContext, PsiFile psiFile) {
        // 基础信息
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        PsiElement psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext);
        assert editor != null;
        // 这里面有整个类的信息，例如该java文件里面所有的文字信息等
        Document document = editor.getDocument();

        ((PsiJavaFileImpl) psiFile).getImportList();

        // 封装生成对象上下文
        GenerateContext generateContext = new GenerateContext();
        generateContext.setProject(project);
        generateContext.setPsiFile(psiFile);
        generateContext.setDataContext(dataContext);
        generateContext.setEditor(editor);
        generateContext.setPsiElement(psiElement);
        // 关于offset：https://plugins.jetbrains.com/docs/intellij/coordinates-system.html
        generateContext.setOffset(editor.getCaretModel().getOffset());
        generateContext.setDocument(document);
        generateContext.setLineNumber(document.getLineNumber(generateContext.getOffset()));
        // 开始行数，这里指的是我们在生成代码的那行的上一行，比如我们光标在第6行生成，那么这里的开始行数是第5行
        generateContext.setStartOffset(document.getLineStartOffset(generateContext.getLineNumber()));
        generateContext.setEditorText(document.getCharsSequence());

        return generateContext;
    }

    @Override
    protected SetObjConfigDO getSetObjConfigDO(GenerateContext generateContext) {
        int repair = 0;
        PsiClass psiClass = null;
        String clazzParamName = null;
        // 光标定位到的元素（这里举个例子，User user；如果光标在User上，就是PsiClass，否则就是属性）
        PsiElement psiElement = generateContext.getPsiElement();

        // 鼠标定位到类
        if (psiElement instanceof PsiClass) {
            psiClass = (PsiClass) generateContext.getPsiElement();

            // 通过光标步长递进找到属性名称
            PsiFile psiFile = generateContext.getPsiFile();
            Editor editor = generateContext.getEditor();
            int offsetStep = generateContext.getOffset() + 1;

            PsiElement elementAt = psiFile.findElementAt(editor.getCaretModel().getOffset());

            while (null == elementAt || elementAt.getText().equals(psiClass.getName()) || elementAt instanceof PsiWhiteSpace) {
                elementAt = psiFile.findElementAt(++offsetStep);
            }

            // 最终拿到属性名称
            clazzParamName = elementAt.getText();
        }

        // 鼠标定位到属性
        if (psiElement instanceof PsiLocalVariable) {
            PsiLocalVariable psiLocalVariable = (PsiLocalVariable) psiElement;

            clazzParamName = psiLocalVariable.getName();

            // 通过光标步长递进找到属性名称
            PsiFile psiFile = generateContext.getPsiFile();
            Editor editor = generateContext.getEditor();
            int offsetStep = generateContext.getOffset() - 1;

            PsiElement elementAt = psiFile.findElementAt(editor.getCaretModel().getOffset());
            while (null == elementAt || elementAt.getText().equals(clazzParamName) || elementAt instanceof PsiWhiteSpace) {
                elementAt = psiFile.findElementAt(--offsetStep);
                if (elementAt instanceof PsiWhiteSpace) {
                    ++repair;
                }
            }

            String clazzName = elementAt.getText();

            PsiClass[] psiClasses = PsiShortNamesCache.getInstance(generateContext.getProject()).getClassesByName(clazzName, GlobalSearchScope.allScope(generateContext.getProject()));

            if (psiClasses.length > 1) {
                assert elementAt.getContext() != null;
                String qualifiedName = ((PsiJavaCodeReferenceElementImpl) elementAt.getContext()).getQualifiedName();
                for (PsiClass clazz : psiClasses) {
                    if (Objects.equals(clazz.getQualifiedName(), qualifiedName)) {
                        psiClass = clazz;
                        break;
                    }
                }
            } else {
                psiClass = psiClasses[0];
            }

            if (null == psiClass) {
                psiClass = psiClasses[0];
            }

            repair += Objects.requireNonNull(psiClass.getName()).length();
        }

        Pattern setMtd = Pattern.compile(setRegex);
        // 获取类的set方法并存放起来
        List<String> paramList = new ArrayList<>();
        Map<String, String> paramMtdMap = new HashMap<>(16);

        List<PsiClass> psiClassLinkList = getPsiClassLinkList(psiClass);
        for (PsiClass psi : psiClassLinkList) {
            List<String> methodsList = getMethods(psi, setRegex, "set");
            for (String methodName : methodsList) {
                // 替换属性
                String param = setMtd.matcher(methodName).replaceAll("$1").toLowerCase();
                // 保存获取的属性信息
                paramMtdMap.put(param, methodName);
                paramList.add(param);
            }
        }

        return new SetObjConfigDO(null == psiClass ? "" : psiClass.getName(), null == psiClass ? "" : psiClass.getQualifiedName(), clazzParamName, paramList, paramMtdMap, repair);
    }

    @Override
    protected GetObjConfigDO getObjConfigDOByClipboardText(GenerateContext generateContext) {
        // 获取剪切板信息 【实际使用可补充一些必要的参数判断】
        String systemClipboardText = Utils.getSystemClipboardText().trim();

        // 按照默认规则提取信息，例如：UserDto userDto
        String[] split = systemClipboardText.split("\\s");
        // 这里是判断，我们上次复制的是否是完整的 类 + 实例变量，如果不是则构造空的Get对象
        if (split.length < 2) {
            return new GetObjConfigDO("", null, null, new HashMap<>());
        }

        // 摘取复制对象中的类和属性，同时支持复制 cn.xxx.class
        String clazzName;
        String clazzParam = split[1].trim();

        String clazzNameImport = "";
        String clazzNameStr = split[0].trim();
        if (clazzNameStr.indexOf(".") > 0) {//这里的判断也很简单，就是出现com.simple.Xxo xxo 这种情况也是可以的
            clazzName = clazzNameStr.substring(clazzNameStr.lastIndexOf(".") + 1);
            clazzNameImport = clazzNameStr;
        } else {
            clazzName = split[0].trim();
        }

        // 获取同名类集合
        PsiClass[] psiClasses = PsiShortNamesCache.getInstance(generateContext.getProject()).getClassesByName(clazzName, GlobalSearchScope.allScope(generateContext.getProject()));

        // 上下文检测，找到符合的复制类
        PsiClass psiContextClass = null;
        // 相同类名处理
        if (psiClasses.length > 1) {
            // 获取比对包文本
            List<String> importList;
            if (!"".equals(clazzNameImport)) {
                importList = Collections.singletonList(clazzNameImport);
            } else {
                importList = getImportList(generateContext.getDocument().getText());
            }
            // 循环比对，通过引入的包名与类做包名做对比
            for (PsiClass psiClass : psiClasses) {
                String qualifiedName = Objects.requireNonNull(psiClass.getQualifiedName());
                String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
                if (importList.contains(packageName)) {
                    psiContextClass = psiClass;
                    break;
                }
            }
            // 同包下比对
            if (null == psiContextClass) {
                String psiFilePackageName = ((PsiJavaFileImpl) generateContext.getPsiFile()).getPackageName();
                for (PsiClass psiClass : psiClasses) {
                    String qualifiedName = Objects.requireNonNull(psiClass.getQualifiedName());
                    String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
                    if (psiFilePackageName.equals(packageName)) {
                        psiContextClass = psiClass;
                        break;
                    }
                }
            }
        }

        if (null == psiContextClass) {
            psiContextClass = psiClasses[0];
        }

        List<PsiClass> psiClassLinkList = getPsiClassLinkList(psiContextClass);

        Map<String, String> paramMtdMap = new HashMap<>();
        Pattern getM = Pattern.compile(getRegex);

        for (PsiClass psi : psiClassLinkList) {
            List<String> methodsList = getMethods(psi, getRegex, "get");
            for (String methodName : methodsList) {
                String param = getM.matcher(methodName).replaceAll("$1").toLowerCase();
                paramMtdMap.put(param, methodName);
            }
        }
        // 构建好需要注入的get方法
        return new GetObjConfigDO(psiContextClass.getQualifiedName(), clazzName, clazzParam, paramMtdMap);
    }

    @Override
    protected void convertSetting(Project project, GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO) {
        ShowSettingsUtil.getInstance().editConfigurable(project, new ConvertSettingUI(project, generateContext, setObjConfigDO, getObjConfigDO));
    }

    /**
     * 这种是直接织入代码的，不需要自己去选择框里面选择指定的字段去get/set
     *
     * @param generateContext
     * @param setObjConfigDO
     * @param getObjConfigDO
     */
    @Override
    protected void weavingSetGetCode(GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO) {
        Application application = ApplicationManager.getApplication();

        // 获取空格位置长度
        int distance = Utils.getWordStartOffset(generateContext.getEditorText(), generateContext.getOffset()) - generateContext.getStartOffset() - setObjConfigDO.getRepair();

        // 下面这段代码，其实也可以抽出来
        application.runWriteAction(() -> {
            StringBuilder blankSpace = new StringBuilder();
            for (int i = 0; i < distance; i++) {
                // 这里是以
                blankSpace.append(" ");
            }

            int lineNumberCurrent = generateContext.getDocument().getLineNumber(generateContext.getOffset()) + 1;

            List<String> setMtdList = setObjConfigDO.getParamList();
            for (String param : setMtdList) {
                int lineStartOffset = generateContext.getDocument().getLineStartOffset(lineNumberCurrent++);

                WriteCommandAction.runWriteCommandAction(generateContext.getProject(), () -> {
                    generateContext.getDocument().insertString(lineStartOffset, blankSpace + setObjConfigDO.getClazzParamName() + "." + setObjConfigDO.getParamMtdMap().get(param) + "(" + (null == getObjConfigDO.getParamMtdMap().get(param) ? "" : getObjConfigDO.getClazzParam() + "." + getObjConfigDO.getParamMtdMap().get(param) + "()") + ");\n");
                    generateContext.getEditor().getCaretModel().moveToOffset(lineStartOffset + 2);
                    generateContext.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                });

            }

        });

    }

}
