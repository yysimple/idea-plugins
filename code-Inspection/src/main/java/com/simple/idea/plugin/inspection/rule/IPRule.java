package com.simple.idea.plugin.inspection.rule;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-11 21:50
 **/
public class IPRule extends AbstractBaseJavaLocalInspectionTool {

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {

        return new JavaElementVisitor() {
            @Override
            public void visitLiteralExpression(PsiLiteralExpression expression) {
                IElementType type = expression.getFirstChild().getNode().getElementType();
                // 判断文档节点是否是String，然后再判断是否是IP格式的，是的话给出提示
                if (type == JavaTokenType.STRING_LITERAL) {
                    Object v = expression.getValue();
                    if (v != null && isSensitiveIp(v.toString())) {
                        holder.registerProblem(
                                expression,
                                "IP地址请不要写死在代码里，请选则域名或配置文件方式",
                                ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                }
            }
        };
    }

    private static boolean isSensitiveIp(String ip) {

        if (ip.length() < 7 || ip.length() > 15) {
            return false;
        }

        String[] ipArray = ip.split("\\.");
        if (ipArray.length != 4) {
            return false;
        }

        for (int i = 0; i < ipArray.length; i++) {
            try {
                int number = Integer.parseInt(ipArray[i]);
                // 判断每段数字是否都在0-255之间
                if (number < 0 || number > 255) {
                    return false;
                }

                // 忽略 127.0.0.1/8
                if (i == 0 && number == 127) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return !"255.255.255.255".equals(ip) &&
                !"0.0.0.0".equals(ip) &&
                !ip.startsWith("2.5.");
    }
}
