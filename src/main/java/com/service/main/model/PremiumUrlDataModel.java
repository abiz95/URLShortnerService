package com.service.main.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumUrlDataModel {

	long premiumUrlIdRec;
	private String actualUrl;
	private String shortenUrl;
	private int customStatus;
	private int urlStatus;
	private Date cre_rec_ts;
}
