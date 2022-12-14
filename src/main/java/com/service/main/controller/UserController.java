package com.service.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.model.UserAuthModel;
import com.service.main.model.UserCredentialModel;
import com.service.main.model.UserModel;
import com.service.main.model.UserPersonalInfoModel;
import com.service.main.model.UserPlanModel;
import com.service.main.service.UserService;
import com.sun.net.httpserver.Authenticator.Success;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	static final Logger logger = LoggerFactory.getLogger(UserController.class);	
	
	//To be removed
	@GetMapping(path="/helloworld")
	public String hello() {
		return "helloworld";
	}
	
	//To be removed
	@GetMapping("/users")
    public ResponseEntity<?> getUsers() {
       
		List<UserModel> userList = null;
        try {
        	userList = userService.getAllUsers();
            logger.info("[UserController] [getUsers] getting user details");
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [getUsers] error occured while retriving user details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
	
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserModel userModel) {
       
    	String status = null;
        try {
        	status = userService.saveUser(userModel);
            logger.info("[UserController] [saveUser] saving user details...");
            if (status == "failed") {
            	
            	logger.error("[UserController] [saveUser] unable to save user");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [saveUser] error occured while saving user details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
    
	@PutMapping("/login")
    public ResponseEntity<?> getUserAuthentication(@RequestBody UserAuthModel userAuthModel) {
       
		String loginUser = null;
        try {
        	loginUser = userService.UsersAuthentication(userAuthModel);
            logger.info("[UserController] [getUserAuthentication] getting user details");
            if (loginUser=="failed") {
            	logger.error("[UserController] [getUserAuthentication] unauthorized access");
            	return new ResponseEntity<>(loginUser,HttpStatus.UNAUTHORIZED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [getUserAuthentication] error occured while retriving user details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
	
	@GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable String userId) {
       
		UserModel userDetails = null;
        try {
        	userDetails = userService.getPersonalInfo(userId);
            logger.info("[UserController] [getUserDetails] getting user details");
            if (userDetails.getUserId() == null) {
				
            	logger.info("[UserController] [getUserDetails] user details not found");
            	return new ResponseEntity<>(userDetails,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [getUserDetails] error occured while retriving user details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
	
    @PutMapping("/user/info")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserPersonalInfoModel userPersonalInfoModel) {
       
    	String status = null;
        try {
        	status = userService.updateUserPersonalInfo(userPersonalInfoModel);
            logger.info("[UserController] [updateUserInfo] update user's personal details...");
            if (status == "failed") {
            	
            	logger.error("[UserController] [updateUserInfo] unable to update user's personal details");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [updateUserInfo] error occured while updating user details "+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
    
    @PutMapping("/user/credential")
    public ResponseEntity<?> updateUserCredential(@RequestBody UserCredentialModel userCredentialModel) {
       
    	String status = null;
        try {
        	status = userService.updateUserCredential(userCredentialModel);
            logger.info("[UserController] [updateUserCredential] update user's credentials...");
            if (status == "failed") {
            	
            	logger.error("[UserController] [updateUserCredential] unable to update user's credential details");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
            else if (status == "NOT_MATCH") {
				
            	logger.error("[UserController] [updateUserCredential] unable to update user's credential because password doesn't match");
            	return new ResponseEntity<>("Password doesn't match", HttpStatus.NOT_ACCEPTABLE);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [updateUserCredential] error occured while updating user credentials "+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
    
    @PutMapping("/user/plan")
    public ResponseEntity<?> updateUserPlan(@RequestBody UserPlanModel userPlanModel) {
       
    	String status = null;
        try {
        	status = userService.updateUserPlan(userPlanModel);
            logger.info("[UserController] [updateUserPlan] update user's plan...");
            if (status == "failed") {
            	
            	logger.error("[UserController] [updateUserPlan] unable to update user's plan details");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[UserController] [updateUserPlan] error occured while updating user plan details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
	
}
