package com.service.main.DAO;

import java.util.List;

import com.service.main.entity.UserPictureEntity;

public interface UserPictureDAO {
	public List<UserPictureEntity> getAllImages(String userId) throws Exception;
	public UserPictureEntity getProfilePicture(String userId, Integer imgStatus) throws Exception;
	public void saveUser(UserPictureEntity userImg) throws Exception;
	public UserPictureEntity getProfilePicturePath(String imagePath) throws Exception;
	public UserPictureEntity deleteUserImage(Integer userPicIdRec) throws Exception;
}
