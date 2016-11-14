package com.zhy.spider.dao;

import java.util.List;

import com.zhy.spider.bean.ShopError;

public interface TblShopErrorDao {
	public void insert(ShopError shopError);
	
	public ShopError selectByKey(Integer key);
	
	public void insertBatch(List<ShopError> shopList);
	
	public ShopError selectOne();
	
	public void deleteById(Long id);
}
