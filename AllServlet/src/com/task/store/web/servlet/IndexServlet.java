package com.task.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.store.domain.Product;
import com.task.store.service.ProductService;
import com.task.store.service.serviceImpl.ProductServiceImpl;
import com.task.store.web.base.BaseServlet;

/**
 * Servlet implementation class IndexServlet 最新最热商品
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("执行execute.....");
		// 调用业务层功能：获取全部分类信息，返回集合
//		CategoryService categoryService = new CategoryServiceImpl();
//		List<Category> list = categoryService.getAllCats();
//		request.setAttribute("allCats", list);
		// 将返回的集合放入request
		// 调用业务层查询最新商品
		// 将两个集合放入到request
		ProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.findHots();
		List<Product> list02 = productService.findNews();
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);

		// 转发到真实的首页
		return "/jsp/index.jsp";
	}
}
