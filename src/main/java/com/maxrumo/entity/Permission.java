package com.maxrumo.entity;

import java.util.List;

public class Permission {
    private Integer id;

    private String name;

    private String type;

    private Integer parentId;

    private String code;

    private String url;

    private List<Permission> children;
    
    public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", type=" + type
				+ ", parentId=" + parentId + ", code=" + code + ", url=" + url
				+ "]";
	}
    
}