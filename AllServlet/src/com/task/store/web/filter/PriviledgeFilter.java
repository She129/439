package com.task.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.task.store.domain.User;

public class PriviledgeFilter implements Filter {

	public PriviledgeFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest myReq = (HttpServletRequest) request;
		User user = (User) myReq.getSession().getAttribute("loginUser");
		if (null != user)
			chain.doFilter(request, response);
		else {
			myReq.setAttribute("msg", "请用户登录后再访问");
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		// chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
