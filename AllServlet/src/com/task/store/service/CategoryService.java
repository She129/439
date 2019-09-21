package com.task.store.service;

import java.util.List;

import com.task.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCats() throws Exception;

	void addCateGory(Category c) throws Exception;

}
