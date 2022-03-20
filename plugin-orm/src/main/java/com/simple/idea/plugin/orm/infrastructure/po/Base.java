package com.simple.idea.plugin.orm.infrastructure.po;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:00
 **/
public abstract class Base {
    private int ormType;
    private String comment;
    private String name;

    public Base(String comment, String name) {
        this.comment = comment;
        this.name = name;
    }

    public String getPackage() {
        String str = StringUtils.substringAfterLast(name, "java/");
        str = str.substring(0, str.lastIndexOf(getSimpleName()) - 1);
        return str.replaceAll("/", ".");
    }

    /**
     * 获取需要倒入的包
     *
     * @return
     */
    public abstract Set<String> getImports();

    public String getComment() {
        return comment;
    }

    public String getSimpleName() {
        return name.lastIndexOf("/") == -1 ? name : StringUtils.substringAfterLast(name, "/");
    }

    public String getVarName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getSimpleName());
    }

    public String getName() {
        return name;
    }

    public void setOrmType(int ormType) {
        this.ormType = ormType;
    }

    public int getOrmType() {
        return ormType;
    }
}
