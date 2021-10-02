package com.service.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.model.ReportIssueDetailsModel;
import com.service.main.model.ReportIssueModel;
import com.service.main.service.ReportIssueService;
import com.sun.net.httpserver.Authenticator.Success;

@RestController
public class ReportIssueController {

	@Autowired
	private ReportIssueService reportIssueService;
	
	static final Logger logger = LoggerFactory.getLogger(ReportIssueController.class);
	
	@GetMapping("/report/all/{userId}")
    public ResponseEntity<?> getIssueList(@PathVariable String userId) {
       
		List<ReportIssueDetailsModel> issueList = null;
        try {
        	issueList = reportIssueService.getIssues(userId);
            logger.info("[ReportIssueController] [getIssueList] getting issue list");
            if (issueList.isEmpty()) {
				
            	logger.info("[ReportIssueController] [getIssueList] no issues found under user id: "+userId);
            	return new ResponseEntity<>(issueList,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [getIssueList] error occured while retriving issue list"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(issueList, HttpStatus.OK);
    }
	
	@GetMapping("/report/History/{userId}")
    public ResponseEntity<?> getIssueHistory(@PathVariable String userId) {
       
		List<ReportIssueDetailsModel> issueList = null;
        try {
        	issueList = reportIssueService.getIssueHistory(userId);
            logger.info("[ReportIssueController] [getIssueHistory] getting issue history");
            if (issueList.isEmpty()) {
				
            	logger.info("[ReportIssueController] [getIssueHistory] no issue history found under user id: "+userId);
            	return new ResponseEntity<>(issueList,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [getIssueHistory] error occured while retriving issue history"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(issueList, HttpStatus.OK);
    }
	
	@GetMapping("/report/issue/{issueId}")
    public ResponseEntity<?> getIssueDetails(@PathVariable String issueId) {
       
		ReportIssueDetailsModel issueDetails = null;
        try {
        	issueDetails = reportIssueService.getIssueInfo(issueId);
            logger.info("[ReportIssueController] [getIssueDetails] getting issue details");
            if (issueDetails.getUserId() == null) {
				
            	logger.info("[ReportIssueController] [getIssueDetails] issue details not found");
            	return new ResponseEntity<>(issueDetails,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [getIssueDetails] error occured while retriving issue details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(issueDetails, HttpStatus.OK);
    }
	
    @PostMapping("/report/save")
    public ResponseEntity<?> saveIssue(@RequestBody ReportIssueModel reportIssueModel) {
       
    	String status = null;
        try {
        	status = reportIssueService.saveIssue(reportIssueModel);
            logger.info("[ReportIssueController] [saveIssue] saving issue details...");
            if (status == "failed") {
            	
            	logger.error("[ReportIssueController] [saveIssue] unable to register issue");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [saveIssue] error occured while saving issue details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
    
    @PutMapping("/issue/update")
    public ResponseEntity<?> updateIssueDetails(@RequestBody ReportIssueDetailsModel reportIssueDetailsModel) {
       
		String status = null;
        try {
        	status = reportIssueService.updateIssue(reportIssueDetailsModel);
            logger.info("[ReportIssueController] [getIssueDetails] getting issue details");
            if (status == "failed") {
				
            	logger.info("[ReportIssueController] [getIssueDetails] issue details not found");
            	return new ResponseEntity<>(status,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [getIssueDetails] error occured while retriving issue details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    
    @GetMapping("/issue/resolved/{issueId}")
    public ResponseEntity<?> resolvedIssue(@PathVariable String issueId) {
       
    	String status = null;
        try {
        	status = reportIssueService.deleteIssueInfo(issueId);
            logger.info("[ReportIssueController] [resolvedIssue] issue resolved");
            if (status == "failed") {
            	
            	logger.error("[ReportIssueController] [resolvedIssue] unable to update issue status");
            	return new ResponseEntity<>(status, HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[ReportIssueController] [resolvedIssue] error occured while updating issue status"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Success>(HttpStatus.OK);
    }
    
}
