package com.task.store.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.task.store.domain.User;

public class ServletDemo01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		User user = new User();
		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
//					System.out.println(item.getFieldName()+"---");
//					System.out.println(item.getName()+"---");
//					System.out.println(item.getString()+"---");
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
//					System.out.println(item.getFieldName());
//					System.out.println(item.getName());
//					System.out.println(item.getString());
					InputStream inputStream = item.getInputStream();
					String realPath = getServletContext().getRealPath("/");
					File file = new File(realPath, item.getName());
					if (!file.exists())
						file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					IOUtils.copy(inputStream, fos);
					IOUtils.closeQuietly(inputStream);
					IOUtils.closeQuietly(fos);
					map.put("userhead", "/images/img" + item.getName());
				}
			}
			BeanUtils.populate(user, map);
			System.out.println("用户保存数据");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
