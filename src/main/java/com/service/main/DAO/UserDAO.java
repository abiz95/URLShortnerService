package com.service.main.DAO;

import java.util.List;

import com.service.main.entity.UserEntity;

public interface UserDAO {

	public List<UserEntity> getAllUsers() throws Exception;
	public void saveUser(UserEntity userEnt) throws Exception;
	public UserEntity getUserByEmail(String email) throws Exception;
	public UserEntity UserAuthentication(String email, String password) throws Exception;
	public UserEntity getUserId(String userId) throws Exception;
}
