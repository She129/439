package com.task.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.store.domain.Cart;
import com.task.store.domain.CartItem;
import com.task.store.domain.Product;
import com.task.store.service.ProductService;
import com.task.store.service.serviceImpl.ProductServiceImpl;
import com.task.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (null == cart) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		cart.addCartItemToCart(cartItem);
		response.sendRedirect("/AllServlet/jsp/cart.jsp");
		return null;
	}

	public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("id");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.removeCartItem(pid);
		response.sendRedirect("/AllServlet/jsp/cart.jsp");
		return null;
	}

	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clearCart();
		response.sendRedirect("/AllServlet/jsp/cart.jsp");

		return null;
	}
}
