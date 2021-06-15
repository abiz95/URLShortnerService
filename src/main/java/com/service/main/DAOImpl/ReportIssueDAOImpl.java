package com.service.main.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.ReportIssueDAO;
import com.service.main.entity.ReportIssueEntity;
import com.service.main.repository.ReportIssueRepository;

@Component
public class ReportIssueDAOImpl implements ReportIssueDAO {

	@Autowired
	private ReportIssueRepository reportIssueRepository;
	
	public List<ReportIssueEntity> getIssueListByUser(String userId) throws Exception {
		return reportIssueRepository.findByUserId(userId);
	}
	
//	public ReportIssueEntity getIssueByUser(String userId) throws Exception {
//		return reportIssueRepository.findByUserId(userId);
//	}
	
	public ReportIssueEntity getIssueReport(String issueId) throws Exception {
		return reportIssueRepository.findByIssueId(issueId);
	}
	
	public ReportIssueEntity saveIssue(ReportIssueEntity reportIssueEntity) throws Exception {
		return reportIssueRepository.save(reportIssueEntity);
	}
	
}
