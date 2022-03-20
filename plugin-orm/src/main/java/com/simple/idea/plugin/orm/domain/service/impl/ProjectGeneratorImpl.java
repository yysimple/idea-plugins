package com.simple.idea.plugin.orm.domain.service.impl;

import com.google.common.base.CaseFormat;
import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.orm.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.orm.domain.service.AbstractProjectGenerator;
import com.simple.idea.plugin.orm.infrastructure.po.*;
import com.simple.idea.plugin.orm.infrastructure.utils.JavaType;

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
    protected void generateORM(Project project, CodeGenContextVO codeGenContext) {

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
            writeFile(project, codeGenContext.getModelPackage(), model.getSimpleName() + ".java", "domain/orm/model.ftl", model);

            // 生成DAO
            Mapper mapper = new Mapper(table.getComment(), codeGenContext.getDaoPackage() + "I" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Mapper", model);
            writeFile(project, codeGenContext.getDaoPackage(), mapper.getSimpleName() + ".java", "domain/orm/dao.ftl", mapper);

            // 生成Mapper
            writeFile(project, codeGenContext.getMapperDir(), mapper.getModel().getSimpleName() + "Mapper.xml", "domain/orm/mapper.ftl", mapper);
        }

    }
}
