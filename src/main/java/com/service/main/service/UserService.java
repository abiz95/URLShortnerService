package com.service.main.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.service.main.model.UserAuthModel;
import com.service.main.model.UserCredentialModel;
import com.service.main.model.UserModel;
import com.service.main.model.UserPersonalInfoModel;
import com.service.main.model.UserPlanModel;
import com.service.main.model.UserProfileInfoModel;

public interface UserService {

	public List<UserModel> getAllUsers() throws Exception;
	public String saveUser(UserModel userModel) throws Exception;
	public String emailVerification(String email) throws Exception;
	public UserProfileInfoModel getProfileInfo(String userId) throws Exception;
	public String UsersAuthentication(UserAuthModel userAuthModel) throws Exception;
	public UserModel getPersonalInfo(String userId) throws Exception;
	public String updateUserPersonalInfo(UserPersonalInfoModel userPersonalInfoModel) throws Exception;
	public String updateUserCredential(UserCredentialModel userCredentialModel) throws Exception;
	public String updateUserPlan(UserPlanModel userPlanModel) throws Exception;
	public String getUserId(UserAuthModel userAuthModel) throws Exception;
	public String getToken(UserAuthModel userAuthModel) throws Exception;
	public String getRefreshToken(String tokenParam) throws Exception;
	public void saveUserImage(MultipartFile file, String userId) throws Exception;
	public byte[] getUserImage(String userId) throws Exception;
	public HashMap<String, byte[]> getAllUserImages(String userId) throws Exception;
	public void saveprofilePicture(String userId, String imagePath) throws Exception;
}
