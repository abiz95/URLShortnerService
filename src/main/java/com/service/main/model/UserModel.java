package com.service.main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

	private String userId;
	private String firstName;
	private String lastName;
	private String country;
	private String email;
	private String phoneNumber;
	private String password;
	private String plan;
	private Integer planStatus;
	private Integer userStatus;
	
}
