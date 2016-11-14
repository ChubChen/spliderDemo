package com.zhy.spider.bean;

public class ShopError {

	private long id;
	
	private String url;
	
	private String errmsg;
	
	private String cityName;

	private String firstCateGory;
	
	private String secondCateGory;
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFirstCateGory() {
		return firstCateGory;
	}

	public void setFirstCateGory(String firstCateGory) {
		this.firstCateGory = firstCateGory;
	}

	public String getSecondCateGory() {
		return secondCateGory;
	}

	public void setSecondCateGory(String secondCateGory) {
		this.secondCateGory = secondCateGory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private int type;
	
	
}
