package com.task.store.service.serviceImpl;

import java.util.List;

import com.task.store.dao.ProductDao;
import com.task.store.domain.PageModel;
import com.task.store.domain.Product;
import com.task.store.service.ProductService;
import com.task.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
//	ProductDao productDao =  new ProductDaoImpl();
	ProductDao productDao = (ProductDao) BeanFactory.createObject("ProductDao");

	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		return productDao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductByCidWithPage(String cid, int curNum) throws Exception {
		// 1.创建pageModel对象目的：计算分页参数
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		// 2.关联集合
		List<?> list = productDao.findProductByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
		// 3.关联URL
		pm.setList(list);
		pm.setUrl("ProductServlet?method=findProductByCidWithPage&cid=" + cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
//		for (Product product : list) {
//			System.out.println(product);
//		}
		pm.setList(list);
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		productDao.saveProduct(product);
	}

}
