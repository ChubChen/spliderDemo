package com.zhy.spider.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.zhy.spider.test.DaZhongDianPing;

public class Shop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String keyStr;

	private String name;

	private String url;
	
	private String address;
	
	private String otherName;
	
	private String firstTime;
	
	private String firstCateGory;
	
	private String cityName;
	
	private String secondCateGory;
	
	private String tel;


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstCateGory() {
		return firstCateGory;
	}

	public void setFirstCateGory(String firstCateGory) {
		this.firstCateGory = firstCateGory;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSecondCateGory() {
		return secondCateGory;
	}

	public void setSecondCateGory(String secondCateGory) {
		this.secondCateGory = secondCateGory;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		if(StringUtils.isNotEmpty(url)){
			String[] array =  url.replace(DaZhongDianPing.urlDomain, "").split("/");
			this.keyStr = array[1];
		}
	}
	
	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
