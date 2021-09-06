package com.service.main.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.UserPictureDAO;
import com.service.main.entity.UserPictureEntity;
import com.service.main.repository.UserPictureRepository;

@Component
public class UserPictureDAOImpl implements UserPictureDAO {

	@Autowired
	private UserPictureRepository userPictureRepository;
	
	public List<UserPictureEntity> getAllImages(String userId) throws Exception {
		return userPictureRepository.findByUserId(userId);
	}
	
	public UserPictureEntity getProfilePicture(String userId, Integer imgStatus) throws Exception {
		return userPictureRepository.findByUserIdAndImgStatus(userId, imgStatus);
	}
	
	public void saveUser(UserPictureEntity userImg) throws Exception {
		userPictureRepository.save(userImg);
	}
	
	public UserPictureEntity getProfilePicturePath(String imagePath) throws Exception {
		return userPictureRepository.findByImagePath(imagePath);
	}
}
