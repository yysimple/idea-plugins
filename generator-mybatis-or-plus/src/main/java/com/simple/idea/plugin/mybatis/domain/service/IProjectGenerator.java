package com.simple.idea.plugin.mybatis.domain.service;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mybatis.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.mybatis.infrastructure.data.GenerateOptions;

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
     * @param options
     */
    void generation(Project project, CodeGenContextVO codeGenContext, GenerateOptions options);
}
