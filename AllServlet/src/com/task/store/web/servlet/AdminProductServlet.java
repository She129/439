package com.task.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.task.store.domain.Category;
import com.task.store.domain.PageModel;
import com.task.store.domain.Product;
import com.task.store.service.CategoryService;
import com.task.store.service.ProductService;
import com.task.store.service.serviceImpl.CategoryServiceImpl;
import com.task.store.service.serviceImpl.ProductServiceImpl;
import com.task.store.utils.UUIDUtils;
import com.task.store.utils.UploadUtils;
import com.task.store.web.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int curNum = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		request.setAttribute("page", pm);

		return "/admin/product/list.jsp";
	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取全部分类信息，将全部分类信息转发到/admin/product/add.jsp;
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		request.setAttribute("allCats", list);
		return "/admin/product/add.jsp";
	}

	// addProduct
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//存储表单数据
		Map<String, String> map = new HashMap<String, String>();
		//携带表单中数据，调用service，dao
		Product product = new Product();
		try {
			// 利用req.getInputStream();获取到请求体中全部数据，进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(request);
			// 遍历集合
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 如果当前的fileitem对象是普通项
					// 将普通项上的name属性的值作为键，获取到的内容作为值，放入map中
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					String oldFileName = item.getName();
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					// 上传项
					InputStream is = item.getInputStream();
					// 通过fileitem获取到输入流对象，获取到图片二进制数据
					String realPath =getServletContext().getRealPath("/products/3");
					System.out.println(realPath);
					// 生成随机目录
					String dir = UploadUtils.getDir(newFileName);
					System.out.println(dir);
					String path = realPath + dir;
					System.out.println(path);
					File newDir = new File(path);
					if (!newDir.exists())
						newDir.mkdirs();
					// 在服务端穿件一个空文件（后缀必须和上传到服务端的文件名后缀一直）
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists())
						finalFile.createNewFile();
					// 建立和空文件对应的输出流
					FileOutputStream os = new FileOutputStream(finalFile);
					// 将输入流的数据刷到输出流中
					IOUtils.copy(is, os);
					// 释放资源
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					// 向map中存入一个键值对的数据
					map.put("pimage", "/products/3/" + dir + "/" + newFileName);
				}
			}
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			ProductService productService=new ProductServiceImpl();
			productService.saveProduct(product);
			response.sendRedirect("/AllServlet/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
