package com.task.store.text;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.task.store.domain.User;

public class TestBeanUtils {
	@Test
	public void test1() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		User user = new User();
		map.put("username", new String[] { "tom" });
		map.put("password", new String[] { "1234" });
		BeanUtils.populate(user, map);
		System.out.println(user);
	}

	@Test
	public void test2() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		User user = new User();
		map.put("username", new String[] { "tom" });
		map.put("password", new String[] { "1234" });
		map.put("birthday", new String[] { "1992-3-3" });
		DateConverter dt = new DateConverter();
		// 2_设置转换的格式
		dt.setPattern("yyyy-MM-dd");
		// 3_注册转换器
		ConvertUtils.register(dt, java.util.Date.class);
		BeanUtils.populate(user, map);
		System.out.println(user);
	}
}
