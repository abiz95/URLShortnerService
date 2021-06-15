package com.service.main.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.UserDAO;
import com.service.main.entity.UserEntity;
import com.service.main.model.UserAuthModel;
import com.service.main.model.UserCredentialModel;
import com.service.main.model.UserModel;
import com.service.main.model.UserPersonalInfoModel;
import com.service.main.model.UserPlanModel;
import com.service.main.service.UserService;
import com.service.main.util.RandomIdGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RandomIdGenerator randomIdGenerator;
	
	public List<UserModel> getAllUsers() throws Exception {
		
		ArrayList<UserModel> userList = new ArrayList<UserModel>();
		List<UserEntity> userData = userDAO.getAllUsers();
		for (UserEntity userEntity : userData) {
			UserModel userModel = new UserModel();
			userModel.setPhoneNumber(userEntity.getPhoneNumber());
			userModel.setUserId(userEntity.getUserId());
			userModel.setUserStatus(userEntity.getUserStatus());
			userModel.setEmail(userEntity.getEmail());
			userModel.setPassword(userEntity.getPassword());
			userList.add(userModel);
		}
		
		return userList;
	}
	
	public String saveUser(UserModel userModel) throws Exception {
		
		Boolean userIdIndicator = true;
		String randomId;
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        randomId = randomIdGenerator.randomId();
//        UserEntity userIdCheck = userDAO.getUserId(randomId);
        Optional<UserEntity> checkNull = Optional.ofNullable(userDAO.getUserId(randomId));  
        
        do {
        	
        	if (checkNull.isPresent()) {
        		
        		randomId = randomIdGenerator.randomId();
			}
        	else {
        		
        		UserEntity newUser = new UserEntity();
        		newUser.setUserId(randomId);
        		newUser.setFirstName(userModel.getFirstName());
        		newUser.setLastName(userModel.getLastName());
        		newUser.setCountry(userModel.getCountry());
        		newUser.setPhoneNumber(userModel.getPhoneNumber());
        		newUser.setEmail(userModel.getEmail());
        		newUser.setPassword(userModel.getPassword());
        		newUser.setPlan(userModel.getPlan());
        		newUser.setPlanStatus(1);
        		newUser.setUserStatus(1);
        		newUser.setCre_rec_ts(timeStamp);
        		newUser.setUpd_rec_ts(timeStamp);
        		
        		userDAO.saveUser(newUser);
        		userIdIndicator = false;
        		
        		return "success";
			}
    		
		} while (userIdIndicator);
        
        return "failed";
	}
	
	public String UsersAuthentication(UserAuthModel userAuthModel) throws Exception {
		
		Optional<UserEntity> userDetails = Optional.ofNullable(userDAO.UserAuthentication(userAuthModel.getEmail(), userAuthModel.getPassword()));  
		
		if (userDetails.isPresent()) {
			if (userAuthModel.getEmail().equals(userDetails.get().getEmail()) && userAuthModel.getPassword().equals(userDetails.get().getPassword())) {
				return userDetails.get().getUserId();
			} else {
				return "failed";
			}
		}
		return "failed";
		
	}
	
	public UserModel getPersonalInfo(String userId) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userId);
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		UserModel userModel = new UserModel();
		
		if (isUserExist.isPresent()) {
			
    		userModel.setUserId(userDetails.getUserId());
    		userModel.setFirstName(userDetails.getFirstName());
    		userModel.setLastName(userDetails.getLastName());
    		userModel.setCountry(userDetails.getCountry());
    		userModel.setPhoneNumber(userDetails.getPhoneNumber());
    		userModel.setEmail(userDetails.getEmail());
    		userModel.setPassword(userDetails.getPassword());
    		userModel.setPlan(userDetails.getPlan());
    		userModel.setPlanStatus(userDetails.getPlanStatus());
    		userModel.setUserStatus(userDetails.getUserStatus());
			
			return userModel;
		}
		
		return userModel;
	}
	
	public String updateUserPersonalInfo(UserPersonalInfoModel userPersonalInfoModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userPersonalInfoModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			userDetails.setFirstName(userPersonalInfoModel.getFirstName());
			userDetails.setLastName(userPersonalInfoModel.getLastName());
			userDetails.setCountry(userPersonalInfoModel.getCountry());
			userDetails.setPhoneNumber(userPersonalInfoModel.getPhoneNumber());
//			userDetails.setEmail(userPersonalInfoModel.getEmail());
			userDAO.saveUser(userDetails);
			
			return "success";
		}
		
		return "failed";
	}
	
	public String updateUserCredential(UserCredentialModel userCredentialModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userCredentialModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			if (userCredentialModel.getCurrentPassword().matches(userDetails.getPassword())) {
			
				userDetails.setPassword(userCredentialModel.getPassword());
				userDAO.saveUser(userDetails);
				return "success";
			}
			
			return "NOT_MATCH";
		}
		
		return "failed";
	}
	
	public String updateUserPlan(UserPlanModel userPlanModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userPlanModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			userDetails.setPlan(userPlanModel.getPlan());
			userDetails.setPlanStatus(userPlanModel.getPlanStatus());

			userDAO.saveUser(userDetails);
			
			return "success";
		}
		
		return "failed";
	}
	
}
