package com.service.main.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.ReportIssueDAO;
import com.service.main.entity.ReportIssueEntity;
import com.service.main.model.ReportIssueDetailsModel;
import com.service.main.model.ReportIssueModel;
import com.service.main.service.ReportIssueService;
import com.service.main.util.RandomIdGenerator;

@Component
public class ReportIssueServiceImpl implements ReportIssueService {

	@Autowired
	private ReportIssueDAO reportIssueDAO;
	
	@Autowired
	private RandomIdGenerator randomIdGenerator;
	
	public List<ReportIssueDetailsModel> getIssues(String userId) throws Exception {
		
		List<ReportIssueEntity> issueDetails = reportIssueDAO.getIssueListByUser(userId);
		Optional<List<ReportIssueEntity>> isIssueExist = Optional.ofNullable(issueDetails);
		ArrayList<ReportIssueDetailsModel> issueList = new ArrayList<ReportIssueDetailsModel>();
		
		if (isIssueExist.isPresent()) {
			
			for (ReportIssueEntity reportIssueEnt : issueDetails) {
				
				if (!(reportIssueEnt.getIssueStatus().matches("Resolved"))) {
					
					if (!(reportIssueEnt.getIssueStatus().matches("Cancelled"))) {
						
						ReportIssueDetailsModel issueModel = new ReportIssueDetailsModel();
						issueModel.setIssueId(reportIssueEnt.getIssueId());
						issueModel.setIssueTitle(reportIssueEnt.getIssueTitle());
						issueModel.setIssueDescription(reportIssueEnt.getIssueDescription());
						issueModel.setIssueStatus(reportIssueEnt.getIssueStatus());
						issueModel.setCre_rec_ts(reportIssueEnt.getCre_rec_ts());
						issueModel.setUserId(reportIssueEnt.getUserId());
						
						issueList.add(issueModel);
					}

				}
			}
		}
		
		return issueList;
	}
	
	public List<ReportIssueDetailsModel> getIssueHistory(String userId) throws Exception {
		
		List<ReportIssueEntity> issueDetails = reportIssueDAO.getIssueListByUser(userId);
		Optional<List<ReportIssueEntity>> isIssueExist = Optional.ofNullable(issueDetails);
		ArrayList<ReportIssueDetailsModel> issueList = new ArrayList<ReportIssueDetailsModel>();
		
		if (isIssueExist.isPresent()) {
			
			for (ReportIssueEntity reportIssueEnt : issueDetails) {
				
				if (!(reportIssueEnt.getIssueStatus().matches("Open"))) {
					
					if (!(reportIssueEnt.getIssueStatus().matches("in-progress"))) {
						
						ReportIssueDetailsModel issueModel = new ReportIssueDetailsModel();
						issueModel.setIssueId(reportIssueEnt.getIssueId());
						issueModel.setIssueTitle(reportIssueEnt.getIssueTitle());
						issueModel.setIssueDescription(reportIssueEnt.getIssueDescription());
						issueModel.setIssueStatus(reportIssueEnt.getIssueStatus());
						issueModel.setCre_rec_ts(reportIssueEnt.getCre_rec_ts());
						issueModel.setUserId(reportIssueEnt.getUserId());
						
						issueList.add(issueModel);
					}

				}
			}
		}
		
		return issueList;
	}
	
	public ReportIssueDetailsModel getIssueInfo(String issueId) throws Exception {
		
		ReportIssueEntity issueDetails = reportIssueDAO.getIssueReport(issueId);
		Optional<ReportIssueEntity> isIssueExist = Optional.ofNullable(issueDetails);
		ReportIssueDetailsModel issueModel = new ReportIssueDetailsModel();
		
		if (isIssueExist.isPresent()) {
			
			issueModel.setIssueId(issueDetails.getIssueId());
			issueModel.setIssueTitle(issueDetails.getIssueTitle());
			issueModel.setIssueDescription(issueDetails.getIssueDescription());
			issueModel.setIssueStatus(issueDetails.getIssueStatus());
			issueModel.setUserId(issueDetails.getUserId());
			issueModel.setCre_rec_ts(issueDetails.getCre_rec_ts());
			return issueModel;

		}
		
		return issueModel;
	}
	
	public String saveIssue(ReportIssueModel reportIssueModel) throws Exception {
		
		Boolean issueIdIndicator = true;
		String newIssueId;
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        newIssueId = randomIdGenerator.customId(6);
        Optional<ReportIssueEntity> issueDetails = Optional.ofNullable(reportIssueDAO.getIssueReport(newIssueId));  
        
        do {
        	
        	if (issueDetails.isPresent()) {
        		
        		newIssueId = randomIdGenerator.customId(6);
			}
        	else {
        		
        		ReportIssueEntity reportIssueEntity = new ReportIssueEntity();
        		reportIssueEntity.setIssueId(newIssueId);
        		reportIssueEntity.setIssueTitle(reportIssueModel.getIssueTitle());
        		reportIssueEntity.setIssueDescription(reportIssueModel.getIssueDescription());
        		reportIssueEntity.setUserId(reportIssueModel.getUserId());
        		reportIssueEntity.setIssueStatus("Open");
        		reportIssueEntity.setUpd_rec_ts(timeStamp);
        		reportIssueEntity.setCre_rec_ts(timeStamp);
        		
        		reportIssueDAO.saveIssue(reportIssueEntity);
        		issueIdIndicator = false;
        		
        		return "Success";
			}
    		
		} while (issueIdIndicator);
        return "failed";
	}
	
	public String updateIssue(ReportIssueDetailsModel reportIssueDetailsModel) throws Exception {
		
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        Optional<ReportIssueEntity> issueDetails = Optional.ofNullable(reportIssueDAO.getIssueReport(reportIssueDetailsModel.getIssueId()));  
        
    	if (issueDetails.isPresent()) {
    		
    		ReportIssueEntity reportIssueEntity = new ReportIssueEntity();
    		reportIssueEntity.setIssueTitle(reportIssueDetailsModel.getIssueTitle());
    		reportIssueEntity.setIssueDescription(reportIssueDetailsModel.getIssueDescription());
    		reportIssueEntity.setUpd_rec_ts(timeStamp);
    		
    		reportIssueDAO.saveIssue(reportIssueEntity);
    		return "success";
		}
    	
        return "failed";
	}
	
	public String deleteIssueInfo(String issueId) throws Exception {
		
		ReportIssueEntity issueDetails = reportIssueDAO.getIssueReport(issueId);
		Optional<ReportIssueEntity> isIssueExist = Optional.ofNullable(issueDetails);
		
		if (isIssueExist.isPresent()) {
			
			issueDetails.setIssueStatus("Cancelled");
			reportIssueDAO.saveIssue(issueDetails);
			
			return "success";
		}
		
		return "failed";
	}
}
