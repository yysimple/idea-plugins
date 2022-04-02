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
            String fileModelName = Constants.YES.equals(options.getIsCreateSwagger()) ? "domain/orm/SwaggerModel.ftl" : "domain/orm/model.ftl";
            writeFile(project, codeGenContext.getModelPackage(), model.getSimpleName() + ".java", fileModelName, model);

            // 生成DAO
            Mapper mapper = new Mapper(table.getComment(), codeGenContext.getDaoPackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Mapper", model);
            mapper.setAuthor(codeGenContext.getAuthor());
            mapper.setProjectName(codeGenContext.getProjectName());
            String fileDaoName = Constants.YES.equals(options.getIsPlus()) ? "domain/orm/plus/PlusDao.ftl" : "domain/orm/dao.ftl";
            writeFile(project, codeGenContext.getDaoPackage(), mapper.getSimpleName() + ".java", fileDaoName, mapper);

            // 生成Mapper
            writeFile(project, codeGenContext.getMapperDir(), mapper.getModel().getSimpleName() + "Mapper.xml", "domain/orm/mapper.ftl", mapper);

            Service service = new Service(table.getComment() + "Service类",
                    codeGenContext.getServicePackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Service",
                    model);
            // 生成Service
            if (Constants.YES.equals(options.getIsCreateService())) {
                service.setAuthor(codeGenContext.getAuthor());
                service.setProjectName(codeGenContext.getProjectName());
                String fileServiceName = Constants.YES.equals(options.getIsPlus()) ? "domain/orm/plus/PlusService.ftl" : "domain/orm/service.ftl";
                writeFile(project, codeGenContext.getServicePackage(), service.getSimpleName() + ".java", fileServiceName, service);

                // 生成对应的impl类
                Impl impl = new Impl(table.getComment() + "ServiceImpl类",
                        codeGenContext.getImplPackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "ServiceImpl",
                        model, service, mapper);
                impl.setAuthor(codeGenContext.getAuthor());
                impl.setProjectName(codeGenContext.getProjectName());
                String fileServiceImplName = Constants.YES.equals(options.getIsPlus()) ? "domain/orm/plus/PlusImpl.ftl" : "domain/orm/impl.ftl";
                writeFile(project, codeGenContext.getImplPackage(), impl.getSimpleName() + ".java", fileServiceImplName, impl);
            }

            if (Constants.YES.equals(options.getIsCreateController())) {
                // 生成controller需要的返回类
                createResponse(project, codeGenContext, model, "BaseResponse", "domain/orm/BaseResponse.ftl");
                Response simpleResponse = createResponse(project, codeGenContext, model, "SimpleResponse", "domain/orm/SimpleResponse.ftl");
                Controller controller = new Controller(table.getComment() + "Controller类",
                        codeGenContext.getControllerPackage() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Controller",
                        model, service, simpleResponse);
                controller.setAuthor(codeGenContext.getAuthor());
                controller.setProjectName(codeGenContext.getProjectName());
                if (Constants.YES.equals(options.getIsCreateSwagger())) {
                    // 生成swagger的配置文件
                    createSwaggerConfig(project, codeGenContext, model);
                    writeFile(project, codeGenContext.getControllerPackage(), controller.getSimpleName() + ".java", "domain/orm/SwaggerController.ftl", controller);
                } else {
                    writeFile(project, codeGenContext.getControllerPackage(), controller.getSimpleName() + ".java", "domain/orm/controller.ftl", controller);
                }
            }
        }

    }

    private Swagger createSwaggerConfig(Project project, CodeGenContextVO codeGenContext, Model model) {
        Swagger swagger = new Swagger("Swagger的基础配置", codeGenContext.getControllerPackage() + "SwaggerConfig", model);
        String fileName = "domain/orm/swagger.ftl";
        swagger.setAuthor(codeGenContext.getAuthor());
        swagger.setProjectName(codeGenContext.getProjectName());
        writeFile(project, codeGenContext.getControllerPackage() + "config/", swagger.getSimpleName() + ".java", fileName, swagger);
        return swagger;
    }

    private Response createResponse(Project project, CodeGenContextVO codeGenContext, Model model, String className, String fileName) {
        Response baseResponse = new Response("controller返回消息体", codeGenContext.getModelPackage() + className, model);
        baseResponse.setAuthor(codeGenContext.getAuthor());
        baseResponse.setProjectName(codeGenContext.getProjectName());
        writeFile(project, codeGenContext.getModelPackage(), baseResponse.getSimpleName() + ".java", fileName, baseResponse);
        return baseResponse;
    }
}
