package com.task.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.task.store.domain.Category;
import com.task.store.domain.User;
import com.task.store.service.CategoryService;
import com.task.store.service.UserService;
import com.task.store.service.serviceImpl.CategoryServiceImpl;
import com.task.store.service.serviceImpl.UserServiceImpl;
import com.task.store.utils.MailUtils;
import com.task.store.utils.MyBeanUtils;
import com.task.store.utils.UUIDUtils;
import com.task.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		request.setAttribute("allCats", list);
		return "/jsp/login.jsp";
	}

	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接受表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		// 为用户的其他属性赋值
		MyBeanUtils.populate(user, map);
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);
		// 调用业务层注册功能
		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活");
		} catch (Exception e) {
			request.setAttribute("msg", "用户注册失败，请重新注册");
		}
		// 注册成功。向邮箱发送信息，跳转提示页面
		// 注册失败。跳转提示页面
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		UserService userService = new UserServiceImpl();
		boolean flag = false;
		try {
			flag = userService.userActive(code);
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag = true) {
			request.setAttribute("msg", "用户激活成功，请登录");
			return "/jsp/login.jsp";
		} else {
			request.setAttribute("msg", "用户激活失败，请重新激活");
			return "/jsp/info.jsp";
		}
	}

	/**
	 * 用户登录
	 */
	public String userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());

		UserService userService = new UserServiceImpl();
		User user02 = null;
		try {
			user02 = userService.userLogin(user);
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("index.jsp");
			return null;
		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.println(msg);
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}

	public String logOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 清除session
		request.getSession().invalidate();
		// 充当定向到首页
		response.sendRedirect("/AllServlet/index.jsp");

		return null;
	}

}
