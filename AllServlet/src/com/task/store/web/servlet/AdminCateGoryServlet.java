package com.task.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.store.domain.Category;
import com.task.store.service.CategoryService;
import com.task.store.service.serviceImpl.CategoryServiceImpl;
import com.task.store.utils.UUIDUtils;
import com.task.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCateGoryServlet
 */
public class AdminCateGoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// AdminCateGoryServlet?method=findAllCats
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoryService cateGoryService = new CategoryServiceImpl();
		List<Category> list = cateGoryService.getAllCats();
		request.setAttribute("allCats", list);
		return "/admin/category/list.jsp";
	}

	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/admin/category/add.jsp";
	}

	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cname = request.getParameter("cname");
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCid(id);
		c.setCname(cname);
		CategoryService cateGoryService = new CategoryServiceImpl();
		cateGoryService.addCateGory(c);
		response.sendRedirect("/AllServlet/AdminCateGoryServlet?method=findAllCats");
		return null;
	}



}
