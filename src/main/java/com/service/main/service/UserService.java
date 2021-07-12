package com.service.main.service;

import java.util.List;

import com.service.main.model.UserAuthModel;
import com.service.main.model.UserCredentialModel;
import com.service.main.model.UserModel;
import com.service.main.model.UserPersonalInfoModel;
import com.service.main.model.UserPlanModel;

public interface UserService {

	public List<UserModel> getAllUsers() throws Exception;
	public String saveUser(UserModel userModel) throws Exception;
	public String UsersAuthentication(UserAuthModel userAuthModel) throws Exception;
	public UserModel getPersonalInfo(String userId) throws Exception;
	public String updateUserPersonalInfo(UserPersonalInfoModel userPersonalInfoModel) throws Exception;
	public String updateUserCredential(UserCredentialModel userCredentialModel) throws Exception;
	public String updateUserPlan(UserPlanModel userPlanModel) throws Exception;
	public String getUserId(UserAuthModel userAuthModel) throws Exception;
}
