package com.service.main.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportIssueDetailsModel {

	private String issueId;
	private String issueTitle;
	private String issueDescription;
	private String issueStatus;
	private String userId;
	private Date cre_rec_ts;
}
