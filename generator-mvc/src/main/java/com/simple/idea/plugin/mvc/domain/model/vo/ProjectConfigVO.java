package com.simple.idea.plugin.mvc.domain.model.vo;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 项目基本配置
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:28
 **/
public class ProjectConfigVO {

    /**
     * 这里加上"_"是防止package冲突
     */
    private String _groupId;
    private String _artifactId;
    private String _version;
    private String _package;

    /**
     * 作者名称
     */
    private String _author;

    public String get_groupId() {
        return _groupId;
    }

    public void set_groupId(String _groupId) {
        this._groupId = _groupId;
    }

    public String get_artifactId() {
        return _artifactId;
    }

    public void set_artifactId(String _artifactId) {
        this._artifactId = _artifactId;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public String get_package() {
        return _package;
    }

    public void set_package(String _package) {
        this._package = _package;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }
}
