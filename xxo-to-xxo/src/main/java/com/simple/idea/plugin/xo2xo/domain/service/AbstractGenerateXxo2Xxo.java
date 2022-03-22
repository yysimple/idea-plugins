package com.simple.idea.plugin.xo2xo.domain.service;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.simple.idea.plugin.xo2xo.application.IGenerateXxo2Xxo;
import com.simple.idea.plugin.xo2xo.domain.model.GenerateContext;
import com.simple.idea.plugin.xo2xo.domain.model.GetObjConfigDO;
import com.simple.idea.plugin.xo2xo.domain.model.SetObjConfigDO;
import com.simple.idea.plugin.xo2xo.infrastructure.DataSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:00
 **/
public abstract class AbstractGenerateXxo2Xxo implements IGenerateXxo2Xxo {
    public static final String SHOW_STATUS = "hide";
    protected final String setRegex = "set(\\w+)";
    protected final String getRegex = "get(\\w+)";

    @Override
    public void doGenerate(Project project, DataContext dataContext, PsiFile psiFile) {
        // 1. 获取上下文
        GenerateContext generateContext = this.getGenerateContext(project, dataContext, psiFile);

        // 2. 获取对象的 set 方法集合
        SetObjConfigDO setObjConfigDO = this.getSetObjConfigDO(generateContext);

        // 3. 获取对的的 get 方法集合 【从剪切板获取】
        GetObjConfigDO getObjConfigDO = this.getObjConfigDOByClipboardText(generateContext);

        // 4. 弹框选择，织入代码。分为弹窗提醒和非弹窗提醒
        DataSetting.DataState state = DataSetting.getInstance(project).getState();
        assert state != null;
        if (SHOW_STATUS.equals(state.getConfigRadio())) {
            // 如果是隐藏的话，这里就不会展示对比边框，直接会织入代码，也就是直接生成在代码的上下文
            this.weavingSetGetCode(generateContext, setObjConfigDO, getObjConfigDO);
        } else {
            this.convertSetting(project, generateContext, setObjConfigDO, getObjConfigDO);
        }

    }

    protected abstract GenerateContext getGenerateContext(Project project, DataContext dataContext, PsiFile psiFile);

    protected abstract SetObjConfigDO getSetObjConfigDO(GenerateContext generateContext);

    protected abstract GetObjConfigDO getObjConfigDOByClipboardText(GenerateContext generateContext);

    protected abstract void convertSetting(Project project, GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO);

    protected abstract void weavingSetGetCode(GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO);

    /**
     * 获取当前类的所有父类（除Objects之外）
     *
     * @param psiClass
     * @return
     */
    protected List<PsiClass> getPsiClassLinkList(PsiClass psiClass) {
        List<PsiClass> psiClassList = new ArrayList<>();
        // 这里的PsiClass跟我们通过反射拿到类信息的原理很像
        PsiClass currentClass = psiClass;
        while (null != currentClass && !"Object".equals(currentClass.getName())) {
            psiClassList.add(currentClass);
            // 一直递归获取其父类
            currentClass = currentClass.getSuperClass();
        }
        // 然后将最开始的子类放到集合最前面
        Collections.reverse(psiClassList);
        // 返回类列表
        return psiClassList;
    }

    /**
     * 获取该类对应的一些方法
     *
     * @param psiClass
     * @param regex
     * @param typeStr
     * @return
     */
    protected List<String> getMethods(PsiClass psiClass, String regex, String typeStr) {
        PsiMethod[] methods = psiClass.getMethods();
        List<String> methodList = new ArrayList<>();

        // 判断使用了 lombok，需要补全生成 get、set
        if (isUsedLombok(psiClass)) {
            Pattern p = Pattern.compile("static.*?final|final.*?static");
            PsiField[] fields = psiClass.getFields();
            for (PsiField psiField : fields) {
                String fieldVal = Objects.requireNonNull(psiField.getNameIdentifier().getContext()).getText();
                // serialVersionUID 判断
                if (fieldVal.contains("serialVersionUID")) {
                    continue;
                }
                // static final 常量判断过滤
                Matcher matcher = p.matcher(fieldVal);
                if (matcher.find()) {
                    continue;
                }
                String name = psiField.getNameIdentifier().getText();
                methodList.add(typeStr + name.substring(0, 1).toUpperCase() + name.substring(1));
            }

            for (PsiMethod method : methods) {
                String methodName = method.getName();
                if (Pattern.matches(regex, methodName) && !methodList.contains(methodName)) {
                    methodList.add(methodName);
                }
            }

            return methodList;
        }


        // 正常创建的get、set，直接获取即可
        for (PsiMethod method : methods) {
            String methodName = method.getName();
            if (Pattern.matches(regex, methodName)) {
                methodList.add(methodName);
            }
        }

        return methodList;
    }

    /**
     * 判断是否使用了lombok注解
     *
     * @param psiClass
     * @return
     */
    private boolean isUsedLombok(PsiClass psiClass) {
        return null != psiClass.getAnnotation("lombok.Data");
    }

    /**
     * 获取对应类的导入包的列表
     *
     * @param docText
     * @return
     */
    protected List<String> getImportList(String docText) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("import(.*?);");
        Matcher m = p.matcher(docText);
        while (m.find()) {
            String val = m.group(1).replaceAll(" ", "");
            list.add(val.substring(0, val.lastIndexOf(".")));
        }
        return list;
    }
}
