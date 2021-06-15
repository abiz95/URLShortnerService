package com.service.main.DAO;

import java.util.List;

import com.service.main.entity.ReportIssueEntity;

public interface ReportIssueDAO {

	public List<ReportIssueEntity> getIssueListByUser(String userId) throws Exception;
	public ReportIssueEntity getIssueReport(String issueId) throws Exception;
	public ReportIssueEntity saveIssue(ReportIssueEntity reportIssueEntity) throws Exception;
}
