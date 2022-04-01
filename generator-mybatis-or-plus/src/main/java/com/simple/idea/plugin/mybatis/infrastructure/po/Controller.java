package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-31 17:19
 **/
public class Controller extends Base {
    private Model model;
    private Service service;
    private Response response;

    public Controller(String comment, String name, Model model, Service service, Response response) {
        super(comment, name);
        this.model = model;
        this.service = service;
        this.response = response;
    }

    public Model getModel() {
        return model;
    }

    public Service getService() {
        return service;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public Set<String> getImports() {
        Set<String> imports = new HashSet<>();
        imports.add(model.getPackage() + "." + model.getSimpleName());
        imports.add(service.getPackage() + "." + service.getSimpleName());
        imports.add(response.getPackage() + "." + response.getSimpleName());
        List<Field> fields = model.getFields();
        for (Field field : fields) {
            if (field.isId() && field.isImport()) {
                imports.add(field.getTypeName());
                break;
            }
        }
        return imports;
    }
}
