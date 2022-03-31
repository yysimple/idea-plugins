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
 * @create: 2022-03-31 17:18
 **/
public class Impl extends Base {
    private Model model;
    private Service service;
    private Mapper mapper;

    public Impl(String comment, String name, Model model, Service service, Mapper mapper) {
        super(comment, name);
        this.model = model;
        this.service = service;
        this.mapper = mapper;
    }

    public Model getModel() {
        return model;
    }

    public Service getService() {
        return service;
    }

    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public Set<String> getImports() {
        Set<String> imports = new HashSet<>();
        imports.add(model.getPackage() + "." + model.getSimpleName());
        imports.add(service.getPackage() + "." + service.getSimpleName());
        imports.add(mapper.getPackage() + "." + mapper.getSimpleName());
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
