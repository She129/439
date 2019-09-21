package com.task.store.service;

import com.task.store.domain.User;

public interface UserService {

	void userRegist(User user) throws Exception;

	boolean userActive(String code) throws Exception;

	User userLogin(User user) throws Exception;

}
