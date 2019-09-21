package com.task.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.task.store.dao.UserDao;
import com.task.store.domain.User;

public class BeanFactory {
	public static Object createObject(String name) {
		try {
			SAXReader reader = new SAXReader();
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document docu = reader.read(is);
			Element rootElement = docu.getRootElement();
			List<Element> list = rootElement.elements();
			for (Element ele : list) {
				String id = ele.attributeValue("id");
				if (id.equals(name)) {
					String str = ele.attributeValue("class");
					Class clazz = Class.forName(str);
					return clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static void main(String[] args) throws Exception {
//		User user=new User();
//		user.setUsername("aaa");
//		user.setPassword("aaa");
//		UserDao ud=(UserDao)BeanFactory.createObject("UserDao");
//		User uu = ud.userLogin(user);
//		System.out.println(uu);
//		
//	}
}
