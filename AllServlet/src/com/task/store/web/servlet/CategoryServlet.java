package com.task.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.task.store.domain.Category;
import com.task.store.service.CategoryService;
import com.task.store.service.serviceImpl.CategoryServiceImpl;
import com.task.store.utils.JedisUtils;
import com.task.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findAllCats
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 调用业务层获取全部分类
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if (null == jsonStr || "".equals(jsonStr) || "".equals(jsonStr.trim())) {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.getAllCats();
			jsonStr = JSONArray.fromObject(list).toString();
			System.out.println(jsonStr);
			jedis.set("allCats", jsonStr);
			System.out.println("redis缓存中没有数据");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} else {
			System.out.println("缓存中有数据");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);

		}
		// 将全部数据转换为JSON格式数据
		// 将全部分类信息响应到客户端
		JedisUtils.closeJedis(jedis);
		return null;
	}
}
