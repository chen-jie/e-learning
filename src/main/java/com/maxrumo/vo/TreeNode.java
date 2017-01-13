package com.maxrumo.vo;

import java.util.List;

public class TreeNode {

	private String  title;
	
	private Integer id;
	
	private Integer parentId;
	
	private String type;
	
	private String url;

	private List<TreeNode> children; 
	
	public TreeNode(String title, Integer id,Integer parentId, String type, String url) {
		super();
		this.title = title;
		this.id = id;
		this.type = type;
		this.parentId = parentId;
		this.url = url;
	}
	public TreeNode(){}

	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getTitle() {
		return title;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
