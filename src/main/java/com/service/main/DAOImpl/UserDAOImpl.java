package com.service.main.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.UserDAO;
import com.service.main.entity.UserEntity;
import com.service.main.repository.UserRepository;

@Component
public class UserDAOImpl implements UserDAO {

	@Autowired
	private UserRepository userRepository;
	
	//To be removed
	public List<UserEntity> getAllUsers() throws Exception {
		return userRepository.findAll();
	}
	
	public void saveUser(UserEntity userEnt) throws Exception {
		userRepository.save(userEnt);
	}
	
	public UserEntity UserAuthentication(String email, String password) throws Exception {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	public UserEntity getUserByEmail(String email) throws Exception {
		return userRepository.findByEmail(email);
	}
	
	public UserEntity getUserId(String userId) throws Exception {
		return userRepository.findByUserId(userId);
	}
}
