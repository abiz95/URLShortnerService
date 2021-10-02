package com.service.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.service.main.entity.UserPictureEntity;

@Repository
public interface UserPictureRepository extends JpaRepository<UserPictureEntity, String> {
	List<UserPictureEntity> findByUserId(String userId) throws Exception;
	UserPictureEntity findByUserIdAndImgStatus(String userId, Integer imgStatus) throws Exception;
	UserPictureEntity findByImagePath(String imagePath) throws Exception;

	@Transactional
	@Modifying
	UserPictureEntity deleteByUserPicIdRec(Integer userPicIdRec) throws Exception;
	
}
