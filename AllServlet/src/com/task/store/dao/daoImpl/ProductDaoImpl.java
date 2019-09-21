package com.task.store.dao.daoImpl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.task.store.dao.ProductDao;
import com.task.store.domain.Product;
import com.task.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHots() throws Exception {
		String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0 ,9 ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() throws Exception {
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0, 9 ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));

	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		String sql = "SELECT * FROM product WHERE pid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	/**
	 * 统计当前类别下商品的个数
	 */
	@Override
	public int findTotalRecords(String cid) throws Exception {
		String sql = "SELECT count(*)  FROM product WHERE cid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler(), cid);
		return num.intValue();
	}

	/**
	 * 统计当前类别的当前页的商品信息
	 */
	@Override
	public List<?> findProductByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
		String sql = "SELECT *  FROM product WHERE cid=? LIMIT ?,? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
	}

	@Override
	public int findTotalRecords() throws Exception {
		String sql = "SELECT COUNT(*) FROM product";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler());
		return num.intValue();
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
		String sql = "SELECT *  FROM product ORDER BY pdate DESC LIMIT ?,? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class), startIndex, pageSize);
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		String sql = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
				product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),
				product.getCid() };
		qr.update(sql, params);
	}

}
