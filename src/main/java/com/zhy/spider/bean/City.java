package com.zhy.spider.bean;

import java.io.Serializable;

public class City  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name ;
	
	private String key;
	
	private String smallName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSmallName() {
		return smallName;
	}

	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
}
