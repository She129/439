package com.task.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.store.domain.Order;
import com.task.store.service.OrderService;
import com.task.store.service.serviceImpl.OrderServiceImpl;
import com.task.store.web.base.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = new OrderServiceImpl();

	public String findOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String state = req.getParameter("state");
		List<Order> list = null;
		if (null == state || "".equals(state))
			list = orderService.findAllOrders();
		else
			list = orderService.findAllOrders(state);
		req.setAttribute("Orders", list);
		return "/admin/order/list.jsp";
	}

	public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String oid = req.getParameter("id");
		Order order = orderService.findOrderByOid(oid);
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().println(jsonStr);
		return null;
	}

	// updataOrderByOid
	public String updataOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String oid = req.getParameter("oid");
		Order order = orderService.findOrderByOid(oid);
		int state = order.getState();
		if (state == 1) {
			order.setState(2);
			resp.sendRedirect("/AllServlet/AdminOrderServlet?method=findOrders&state=2");
		}
		if (state == 2) {
			order.setState(3);
			resp.sendRedirect("/AllServlet/AdminOrderServlet?method=findOrders&state=3");
		}
		if (state == 3) {
			order.setState(4);
			resp.sendRedirect("/AllServlet/AdminOrderServlet?method=findOrders&state=4");
		}
		orderService.updateOrder(order);
		return null;
	}
}
