package com.task.store.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.task.store.dao.OrderDao;
import com.task.store.domain.Order;
import com.task.store.domain.OrderItem;
import com.task.store.domain.PageModel;
import com.task.store.domain.User;
import com.task.store.service.OrderService;
import com.task.store.utils.BeanFactory;
import com.task.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {
//	OrderDao orderDao = new OrderDaoImpl();
	OrderDao orderDao = (OrderDao) BeanFactory.createObject("OrderDao");

	@Override
	public void saveOrder(Order order) throws SQLException {
		/*
		 * try { JDBCUtils.startTransaction(); orderDao.saveOrder(order); for (OrderItem
		 * orderItem : order.getList()) { orderDao.saveOrderItem(orderItem); }
		 * JDBCUtils.commitAndClose();
		 * 
		 * } catch (Exception e) { JDBCUtils.rollbackAndClose(); }
		 */
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			orderDao.saveOrder(conn, order);
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn, item);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		} finally {
//			if (null != conn) {
//				conn.close();
//				conn = null;
//			}
		}

	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		List<?> list = orderDao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		
		return orderDao.findAllOrders();
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		
		return orderDao.findAllOrders(state);
	}

}
