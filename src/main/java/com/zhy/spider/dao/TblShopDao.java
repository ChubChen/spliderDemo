package com.zhy.spider.dao;

import java.util.List;

import com.zhy.spider.bean.Shop;

public interface TblShopDao {

	public void insert(Shop shop);
	
	public Shop selectByKey(Integer key);
	
	public void insertBatch(List<Shop> shopList);
	
	
}
