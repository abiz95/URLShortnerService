package com.service.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.entity.PremiumUrlEntity;
import com.service.main.model.PremiumUrlDataModel;
import com.service.main.model.PremiumUrlDetailModel;
import com.service.main.model.PremiumUrlModel;
import com.service.main.model.PremiumUrlStatusModel;
import com.service.main.service.PremiumUrlService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class PremiumUrlController {

	@Autowired
	private PremiumUrlService premiumUrlService;
	
	static final Logger logger = LoggerFactory.getLogger(PremiumUrlController.class);	
	
	//To be removed
	@GetMapping("/allPremiumUrls")
    public ResponseEntity<?> getAllPremiumUrl() {
       
		List<PremiumUrlEntity> premiumUrlList = null;
        try {
        	premiumUrlList = premiumUrlService.getAllPremiumUrl();
            logger.info("[PremiumUrlController] [getAllPremiumUrl] getting premium URL");
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [getAllPremiumUrl] error occured while retriving premium URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/premium/details/{userId}/{shortenUrl}")
    public ResponseEntity<?> getPremiumUrlDetails(@PathVariable String userId, @PathVariable String shortenUrl) {
       
		PremiumUrlDetailModel premiumUrlList = null;
        try {
        	premiumUrlList = premiumUrlService.getPremiumUrlDetails(userId, shortenUrl);
            logger.info("[PremiumUrlController] [getPremiumUrlDetails] getting premium URL");
            if (premiumUrlList.getShortenUrl() == null) {
				
            	logger.info("[PremiumUrlController] [getPremiumUrlDetails] premium URL details not found");
            	return new ResponseEntity<>(premiumUrlList,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [getPremiumUrlDetails] error occured while retriving premium URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/premium/custom/find/{shortenUrl}")
    public ResponseEntity<?> getPremiumCustomUrlDetails(@PathVariable String shortenUrl) {
       
		String premiumCustomUrl = null;
        try {
        	premiumCustomUrl = premiumUrlService.getPremiumCustomUrl(shortenUrl);
            logger.info("[PremiumUrlController] [getPremiumCustomUrlDetails] getting premium custom URL");
            if (premiumCustomUrl == "NOT_FOUND") {
				
            	logger.info("[PremiumUrlController] [getPremiumCustomUrlDetails] premium custom URL details not found");
            	return new ResponseEntity<>("204: NO CONTENT FOUND",HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [getPremiumCustomUrlDetails] error occured while retriving premium custom URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumCustomUrl, HttpStatus.OK);
    }
	
    @PostMapping("/premium/saveUrl")
    public ResponseEntity<?> savePremiumUrl(@RequestBody PremiumUrlModel premiumUrlModel) {
       
    	String saveStatus = null;
        try {
        	saveStatus = premiumUrlService.savePremiumUrl(premiumUrlModel);
            logger.info("[PremiumUrlController] [savePremiumUrl] saving premium URL details...");
            if (saveStatus == "failed") {
            	logger.error("[PremiumUrlController] [savePremiumUrl] unable to generate premium URL");
            	return new ResponseEntity<>(saveStatus,HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [savePremiumUrl] error occured while saving premium URL details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(saveStatus, HttpStatus.OK);
    }
    
    @PostMapping("/premium/custom/saveUrl")
    public ResponseEntity<?> savePremiumCustomUrl(@RequestBody PremiumUrlDetailModel premiumUrlDetailModel) {
       
    	String saveStatus = null;
        try {
        	saveStatus = premiumUrlService.savePremiumCustomUrl(premiumUrlDetailModel);
            logger.info("[PremiumUrlController] [savePremiumCustomUrl] saving premium URL details...");
            if (saveStatus == "failed") {
            	logger.error("[PremiumUrlController] [savePremiumCustomUrl] premium custom URL already exist");
            	return new ResponseEntity<>(saveStatus,HttpStatus.NOT_IMPLEMENTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [savePremiumUrl] error occured while saving premium URL details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(saveStatus, HttpStatus.OK);
    }
    
	@PutMapping("/premium/update")
    public ResponseEntity<?> updatePremiumUrlDetails(@RequestBody PremiumUrlDetailModel premiumUrlDetailModel) {
       
		String updateStatus = null;
        try {
        	updateStatus = premiumUrlService.updatePremiumUrl(premiumUrlDetailModel);
            logger.info("[PremiumUrlController] [updatePremiumUrlDetails] updating premium URL details");
            if (updateStatus == "failed") {
            	logger.error("[PremiumUrlController] [updatePremiumUrlDetails] premium URL details not found");
            	return new ResponseEntity<>(updateStatus,HttpStatus.NO_CONTENT);
			}
            if (updateStatus == "ALREADY_EXIST") {
            	logger.error("[PremiumUrlController] [updatePremiumUrlDetails] premium URL details not found");
            	return new ResponseEntity<>(updateStatus,HttpStatus.ALREADY_REPORTED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [updatePremiumUrlDetails] error occured while updating premium URL details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }
	
    @DeleteMapping("/premium/deleteUrl/{userId}/{shortenUrl}")
    public ResponseEntity<?> deletePremiumUrl(@PathVariable String userId, @PathVariable String shortenUrl) {
       
		String deleteStatus = null;
        try {
        	deleteStatus = premiumUrlService.deletePremiumUrl(userId, shortenUrl);
            logger.info("[PremiumUrlController] [deletePremiumUrl] deleting premium URL details");
            if (deleteStatus == "failed") {
            	logger.error("[PremiumUrlController] [deletePremiumUrl] premium URL details not found");
            	return new ResponseEntity<>(deleteStatus,HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [deletePremiumUrl] error occured while deleting premium URL details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
        
    }
    
	@PutMapping("/premium/status")
    public ResponseEntity<?> updatePremiumUrlStatus(@RequestBody PremiumUrlStatusModel premiumUrlStatusModel) {
       
		String updateStatus = null;
        try {
        	updateStatus = premiumUrlService.updatePremiumUrlStatus(premiumUrlStatusModel);
            logger.info("[PremiumUrlController] [updatePremiumUrlStatus] updating premium URL status details");
            if (updateStatus == "failed") {
            	logger.error("[PremiumUrlController] [updatePremiumUrlStatus] premium URL status details not found");
            	return new ResponseEntity<>(updateStatus,HttpStatus.NOT_MODIFIED);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [updatePremiumUrlDetails] error occured while updating premium URL status details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }
	
	@GetMapping("/premium/list/{userId}")
    public ResponseEntity<?> getPremiumUrlList(@PathVariable String userId) {
       
		List<PremiumUrlDataModel> premiumUrlList = null;
        try {
        	premiumUrlList = premiumUrlService.getPremiumUrlByUserId(userId);
            logger.info("[PremiumUrlController] [getPremiumUrlList] getting premium URL");
            if (premiumUrlList.isEmpty()) {
				
            	logger.info("[PremiumUrlController] [getPremiumUrlList] premium URL details not found");
            	return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[PremiumUrlController] [getPremiumUrlList] error occured while retriving premium URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
}
