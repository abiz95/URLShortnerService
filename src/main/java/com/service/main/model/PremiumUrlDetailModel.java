package com.service.main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumUrlDetailModel {

	long premiumUrlIdRec;
	private String userId;
	private String actualUrl;
	private String shortenUrl;
}
