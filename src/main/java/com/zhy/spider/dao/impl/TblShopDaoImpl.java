package com.zhy.spider.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.zhy.spider.bean.Shop;
import com.zhy.spider.dao.TblShopDao;

public class TblShopDaoImpl extends SqlSessionDaoSupport implements TblShopDao {

	public void insert(Shop shop) {
		this.getSqlSession().insert("TblShop.insert",shop);
	}

	public Shop selectByKey(Integer key) {
		return this.getSqlSession().selectOne("TblShop.selectByKey", key);
	}

	public void insertBatch(List<Shop> shopList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopList", shopList);
		this.getSqlSession().insert("TblShop.insertBatch",map);
	}

}
