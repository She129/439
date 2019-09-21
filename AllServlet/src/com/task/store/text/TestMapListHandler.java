package com.task.store.text;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.task.store.domain.OrderItem;
import com.task.store.domain.Product;
import com.task.store.utils.JDBCUtils;

public class TestMapListHandler {
	@Test
	/**
	 * 根据订单id查询每笔订单下的订单项和对应商品的信息 select * from orderitem o,product p where
	 * o.pid=p.pid and oid='2FF58719DB7042C0994472F7C0EEE1F5';
	 * 
	 */
	public void test() throws Exception {
		ArrayList<OrderItem> list1 = new ArrayList<OrderItem>();
		String sql = "select * from orderitem o,product p where o.pid=p.pid and oid='2FF58719DB7042C0994472F7C0EEE1F5';";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Map<String, Object>> list = qr.query(sql, new MapListHandler());
		for (Map<String, Object> map : list) {
			// for (Map.Entry<String, Object> en : map.entrySet()) {
			// }
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			BeanUtils.populate(orderItem, map);
			BeanUtils.populate(product, map);
			// 让orderItem和product发生关系
			orderItem.setProduct(product);
			list1.add(orderItem);

		}
	}
}
