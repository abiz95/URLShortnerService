package com.service.main.service;

import java.util.List;

import com.service.main.model.ReportIssueDetailsModel;
import com.service.main.model.ReportIssueModel;

public interface ReportIssueService {

	public List<ReportIssueDetailsModel> getIssues(String userId) throws Exception;
	public List<ReportIssueDetailsModel> getIssueHistory(String userId) throws Exception;
	public ReportIssueDetailsModel getIssueInfo(String userId) throws Exception;
	public String saveIssue(ReportIssueModel reportIssueModel) throws Exception;
	public String updateIssue(ReportIssueDetailsModel reportIssueDetailsModel) throws Exception;
	public String deleteIssueInfo(String issueId) throws Exception;
}
