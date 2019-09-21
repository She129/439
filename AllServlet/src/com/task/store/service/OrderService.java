package com.task.store.service;

import java.util.List;

import com.task.store.domain.Order;
import com.task.store.domain.PageModel;
import com.task.store.domain.User;

public interface OrderService {
	/**
	 * 保存订单
	 * 
	 * @param order
	 * @throws Exception
	 */
	void saveOrder(Order order) throws Exception;

	/**
	 * 根据参数返回订单模型
	 * 
	 * @param user   用户
	 * @param curNum 第几页
	 * @return
	 * @throws Exception
	 */
	PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	/**
	 * 根据订单号找订单
	 * 
	 * @param oid
	 * @return
	 * @throws Exception
	 */
	Order findOrderByOid(String oid) throws Exception;

	/**
	 * 更新订单
	 * 
	 * @param order
	 * @throws Exception
	 */
	void updateOrder(Order order) throws Exception;

	/**
	 * 返回所有订单
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Order> findAllOrders() throws Exception;

	List<Order> findAllOrders(String state) throws Exception;
}
