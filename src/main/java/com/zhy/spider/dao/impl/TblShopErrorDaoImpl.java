package com.zhy.spider.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.zhy.spider.bean.ShopError;
import com.zhy.spider.dao.TblShopErrorDao;

public class TblShopErrorDaoImpl extends SqlSessionDaoSupport implements TblShopErrorDao{

	public void insert(ShopError shopError) {
		this.getSqlSession().insert("TblShopError.insert",shopError);
		
	}

	public ShopError selectByKey(Integer key) {
		return this.getSqlSession().selectOne("TblShopError.selectByKey", key);
	}

	public void insertBatch(List<ShopError> shopList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopList", shopList);
		this.getSqlSession().insert("TblShopError.insertBatch",map);
	}

	public ShopError selectOne() {
		return this.getSqlSession().selectOne("TblShopError.selectOne");
	}

	public void deleteById(Long id) {
		 this.getSqlSession().selectOne("TblShopError.deleteById", id);
	}



}
