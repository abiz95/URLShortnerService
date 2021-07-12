package com.service.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.main.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	UserEntity findByEmailAndPassword(String email, String password) throws Exception;
	UserEntity findByEmail(String email) throws Exception;
	UserEntity findByUserId(String userId) throws Exception;
}
