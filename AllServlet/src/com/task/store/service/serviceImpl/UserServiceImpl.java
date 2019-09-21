package com.task.store.service.serviceImpl;

import java.sql.SQLException;

import com.task.store.dao.ProductDao;
import com.task.store.dao.UserDao;
import com.task.store.dao.daoImpl.UserDaoImpl;
import com.task.store.domain.User;
import com.task.store.service.UserService;
import com.task.store.utils.BeanFactory;

public class UserServiceImpl implements UserService {

//	UserDao userDao = new UserDaoImpl();
	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");

	@Override
	public void userRegist(User user) throws Exception {
		// 实现注册
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws Exception {
		User user = userDao.userActive(code);
		if (null != user) {
			user.setState(1);
			user.setCode(null);
			System.out.println("激活码已清除");
			userDao.updateUser(user);
			System.out.println("用户已激活");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws Exception {
		UserDao userDao = new UserDaoImpl();
		User uu = userDao.userLogin(user);
		if (null == uu)
			throw new RuntimeException("密码有误！！！");
		else if (uu.getState() == 0)
			throw new RuntimeException("用户未激活！！！");
		else
			return uu;
	}

}
