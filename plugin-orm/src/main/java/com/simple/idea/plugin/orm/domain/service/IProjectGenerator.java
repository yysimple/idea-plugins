package com.simple.idea.plugin.orm.domain.service;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.orm.domain.model.vo.CodeGenContextVO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:10
 **/
public interface IProjectGenerator {

    /**
     * 这里是生成代码的主流程
     *
     * @param project
     * @param codeGenContext
     */
    void generation(Project project, CodeGenContextVO codeGenContext);
}
