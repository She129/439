package com.task.store.dao.daoImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.task.store.dao.OrderDao;
import com.task.store.domain.Order;
import com.task.store.domain.OrderItem;
import com.task.store.domain.Product;
import com.task.store.domain.User;
import com.task.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws Exception {
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		String sql = "SELECT * FROM orders WHERE state=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class),state);
	}

	@Override
	public List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "SELECT * FROM orders WHERE uid=? LIMIT ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
		// 遍历所有订单
		for (Order order : list) {
			// 获取每笔订单 枚举订单下的订单项及商品对应的信息
			String oid = order.getOid();
			sql = "SELECT * FROM orderItem o,product p where o.pid=p.pid and oid=?";
			List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(), oid);
			for (Map<String, Object> map : list2) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				// 将map中属于orderitem的数据自动填充到orderitem对象上
				BeanUtils.populate(orderItem, map);
				// 将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);
				// 让每个订单项发生关系
				orderItem.setProduct(product);
				// 将每个订单项存入订单下的集合中
				order.getList().add(orderItem);
				// 67E085E10400438BB26C6CE6353D1BEC
			}
		}
		return list;
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(),
				item.getOrder().getOid() };
		qr.update(conn, sql, params);
	}

	@Override
	public int getTotalRecords(User user) throws Exception {
		String sql = "SELECT COUNT(*) FROM orders WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler(), user.getUid());
		return num.intValue();
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "SELECT * FROM orders WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		sql = "SELECT * FROM orderitem o,product p WHERE o.pid=p.pid AND oid=?";
		List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : list2) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			// 将map中属于orderitem的数据自动填充到orderitem对象上
			BeanUtils.populate(orderItem, map);
			// 将map中属于product的数据自动填充到product对象上
			BeanUtils.populate(product, map);
			// 让每个订单项发生关系
			orderItem.setProduct(product);
			// 将每个订单项存入订单下的集合中
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		String sql = "UPDATE orders SET ordertime=?, total=?,state=?,address=?,name=?,telephone=? WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getOid() };
		qr.update(sql, params);

	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		String sql = "SELECT * FROM orders";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}

}
