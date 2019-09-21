package com.task.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.store.domain.PageModel;
import com.task.store.domain.Product;
import com.task.store.service.ProductService;
import com.task.store.service.serviceImpl.ProductServiceImpl;
import com.task.store.web.base.BaseServlet;

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findProductByPid
	public String findProductByPid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String pid = req.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		req.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	}

	public String findProductByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		PageModel pageModel = productService.findProductByCidWithPage(cid, curNum);
		request.setAttribute("page", pageModel);
		return "/jsp/product_list.jsp";
	}
}
