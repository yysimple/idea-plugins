package com.simple.idea.plugin.mybatis.domain.service.impl;

import com.google.common.base.CaseFormat;
import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mybatis.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.mybatis.domain.service.AbstractProjectGenerator;
import com.simple.idea.plugin.mybatis.infrastructure.data.GenerateOptions;
import com.simple.idea.plugin.mybatis.infrastructure.po.*;
import com.simple.idea.plugin.mybatis.infrastructure.utils.Constants;
import com.simple.idea.plugin.mybatis.infrastructure.utils.JavaType;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 实际生成文件的操作
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:12
 **/
public class ProjectGeneratorImpl extends AbstractProjectGenerator {

    @Override
    protected void generateORM(Project project, CodeGenContextVO codeGenContext, GenerateOptions options) {

        List<Table> tables = codeGenContext.getTables();
        for (Table table : tables) {
            List<Column> columns = table.getColumns();
            List<Field> fields = new ArrayList<>();

            for (Column column : columns) {
                Field field = new Field(column.getComment(), JavaType.convertType(column.getType()), column.getName());
                field.setId(column.isId());
                fields.add(field);
            }

            // 生成PO
            Model model = new Model(table.getComment(), codeGenContext.getModelPackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()), table.getName(), fields);
            model.setAuthor(codeGenContext.getAuthor());
            model.setProjectName(codeGenContext.getProjectName());
            writeFile(project, codeGenContext.getModelPackage(), model.getSimpleName() + ".java", "domain/orm/model.ftl", model);

            // 生成DAO
            Mapper mapper = new Mapper(table.getComment(), codeGenContext.getDaoPackage() + "I" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Mapper", model);
            mapper.setAuthor(codeGenContext.getAuthor());
            mapper.setProjectName(codeGenContext.getProjectName());
            String fileDaoName = Constants.YES.equals(options.getIsPlus()) ? "domain/orm/plus/PlusDao.ftl" : "domain/orm/dao.ftl";
            writeFile(project, codeGenContext.getDaoPackage(), mapper.getSimpleName() + ".java", fileDaoName, mapper);

//            // 生成Mapper
//            writeFile(project, codeGenContext.getMapperDir(), mapper.getModel().getSimpleName() + "Mapper.xml", "domain/orm/mapper.ftl", mapper);
        }

    }
}
