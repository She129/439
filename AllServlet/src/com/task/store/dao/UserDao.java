package com.task.store.dao;

import java.sql.SQLException;

import com.task.store.domain.User;

public interface UserDao {

	void userRegist(User user) throws Exception;

	User userActive(String code) throws Exception;

	void updateUser(User user) throws Exception;

	User userLogin(User user) throws Exception;

}
