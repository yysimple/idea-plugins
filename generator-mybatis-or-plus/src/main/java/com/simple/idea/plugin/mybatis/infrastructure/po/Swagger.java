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
 * @create: 2022-04-01 20:39
 **/
public class Swagger extends Base {

    private Model model;

    public Swagger(String comment, String name, Model model) {
        super(comment, name);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public Set<String> getImports() {
        Set<String> imports = new HashSet<>();
        imports.add(model.getPackage() + "." + model.getSimpleName());
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
