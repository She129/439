package com.task.store.dao;

import java.util.List;

import com.task.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCats() throws Exception;

	void addCategory(Category c) throws Exception;

}
