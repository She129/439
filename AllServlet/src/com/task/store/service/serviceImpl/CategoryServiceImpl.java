package com.task.store.service.serviceImpl;

import java.util.List;

import com.task.store.dao.CategoryDao;
import com.task.store.domain.Category;
import com.task.store.service.CategoryService;
import com.task.store.utils.BeanFactory;
import com.task.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

//	CategoryDao categoryDao = new CategoryDaoImpl();
	CategoryDao categoryDao = (CategoryDao)BeanFactory.createObject("CategoryDao");
	@Override
	public List<Category> getAllCats() throws Exception {
		return categoryDao.getAllCats();
	}

	@Override
	public void addCateGory(Category c) throws Exception {
		categoryDao.addCategory(c);
		//更新Redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
