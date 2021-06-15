package com.service.main.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.GeneralUrlDAO;
import com.service.main.DAO.PremiumUrlDAO;
import com.service.main.entity.GeneralUrlEntity;
import com.service.main.entity.PremiumUrlEntity;
import com.service.main.model.PremiumUrlDataModel;
import com.service.main.model.PremiumUrlDetailModel;
import com.service.main.model.PremiumUrlModel;
import com.service.main.model.PremiumUrlStatusModel;
import com.service.main.service.PremiumUrlService;
import com.service.main.util.RandomIdGenerator;

@Service
public class PremiumUrlServiceImpl implements PremiumUrlService {

	@Autowired
	private PremiumUrlDAO premiumUrlDAO;
	
	@Autowired
	private GeneralUrlDAO generalUrlDAO;
	
	@Autowired
	private RandomIdGenerator randomIdGenerator;
	
	static final Logger logger = LoggerFactory.getLogger(PremiumUrlServiceImpl.class);	
	
	//To be removed
	public List<PremiumUrlEntity> getAllPremiumUrl() throws Exception {
		
		List<PremiumUrlEntity> premiumUrlData = premiumUrlDAO.getAllPremiumUrl();
		return premiumUrlData;
	}
	
	public String savePremiumUrl(PremiumUrlModel premiumUrlModel) throws Exception {
		
		Boolean premiumIdIndicator = true;
		String shortenUrl;
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        shortenUrl = randomIdGenerator.customId(6);
        Optional<GeneralUrlEntity> generalNullCheck = Optional.ofNullable(generalUrlDAO.getGeneralUrl(shortenUrl));  
        Optional<PremiumUrlEntity> premiumNullCheck = Optional.ofNullable(premiumUrlDAO.getPremiumUrl(shortenUrl));  
        
        do {
        	
        	if (generalNullCheck.isPresent() || premiumNullCheck.isPresent()) {
        		
        		shortenUrl = randomIdGenerator.customId(6);
			}
        	else {
        		
        		PremiumUrlEntity newPremiumUrl = new PremiumUrlEntity();
        		newPremiumUrl.setActualUrl(premiumUrlModel.getActualUrl());
        		newPremiumUrl.setShortenUrl(shortenUrl);
        		newPremiumUrl.setUserId(premiumUrlModel.getUserId());
        		newPremiumUrl.setCustomStatus(0);
        		newPremiumUrl.setUrlStatus(1);
        		newPremiumUrl.setCre_rec_ts(timeStamp);
        		newPremiumUrl.setUpd_rec_ts(timeStamp);
        		
        		premiumUrlDAO.savePremiumUrl(newPremiumUrl);
        		premiumIdIndicator = false;
        		
        		return shortenUrl;
			}
    		
		} while (premiumIdIndicator);
        return "failed";
	}
	
	public String savePremiumCustomUrl(PremiumUrlDetailModel premiumUrlDetailModel) throws Exception {
		
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        Optional<GeneralUrlEntity> generalNullCheck = Optional.ofNullable(generalUrlDAO.getGeneralUrl(premiumUrlDetailModel.getShortenUrl()));  
        Optional<PremiumUrlEntity> premiumNullCheck = Optional.ofNullable(premiumUrlDAO.getPremiumUrl(premiumUrlDetailModel.getShortenUrl()));  
        
    	if (generalNullCheck.isPresent() || premiumNullCheck.isPresent()) {
    		
        	return "failed";
		}
    	else {
		
    		PremiumUrlEntity newPremiumUrl = new PremiumUrlEntity();
    		newPremiumUrl.setActualUrl(premiumUrlDetailModel.getActualUrl());
    		newPremiumUrl.setShortenUrl(premiumUrlDetailModel.getShortenUrl());
    		newPremiumUrl.setUserId(premiumUrlDetailModel.getUserId());
    		newPremiumUrl.setCustomStatus(1);
    		newPremiumUrl.setUrlStatus(1);
    		newPremiumUrl.setCre_rec_ts(timeStamp);
    		newPremiumUrl.setUpd_rec_ts(timeStamp);
    		
    		premiumUrlDAO.savePremiumUrl(newPremiumUrl);
    		
    		logger.info("Custom URL "+premiumUrlDetailModel.getShortenUrl()+" for customer Id:"+premiumUrlDetailModel.getUserId());
    		return premiumUrlDetailModel.getShortenUrl();
		}
    	
	}
	
	public String getPremiumCustomUrl(String shortenUrl) throws Exception {
		
		Optional<PremiumUrlEntity> premiumUrlData = Optional.ofNullable(premiumUrlDAO.getPremiumUrl(shortenUrl));
		if (premiumUrlData.isPresent()) {
			return premiumUrlData.get().getShortenUrl();
		}
		return "NOT_FOUND";
	}
	
	public PremiumUrlDetailModel getPremiumUrlDetails(String userId, String shortenUrl) throws Exception {
		
		Optional<PremiumUrlEntity> premiumUrlDetails = Optional.ofNullable(premiumUrlDAO.getByUserIdAndPremiumUrl(userId, shortenUrl));
		PremiumUrlDetailModel premiumUrlData = new PremiumUrlDetailModel();
		if (premiumUrlDetails.isPresent()) {
			
			premiumUrlData.setUserId(premiumUrlDetails.get().getUserId());
			premiumUrlData.setActualUrl(premiumUrlDetails.get().getActualUrl());
			premiumUrlData.setShortenUrl(premiumUrlDetails.get().getShortenUrl());
    		
		}
		return premiumUrlData;
	}
	
	public String updatePremiumUrl(PremiumUrlDetailModel premiumUrlDetailModel) throws Exception {
		
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        PremiumUrlEntity premiumUrlDetails = premiumUrlDAO.getPremiumUrlByRecId(premiumUrlDetailModel.getPremiumUrlIdRec());
		Optional<PremiumUrlEntity> checkNull = Optional.ofNullable(premiumUrlDetails);
		
		if (checkNull.isPresent()) {
			logger.info("From param: "+premiumUrlDetailModel.getShortenUrl()+" from db: "+checkNull.get().getShortenUrl());
			if (checkNull.get().getCustomStatus()==1 && !(premiumUrlDetailModel.getShortenUrl().matches(checkNull.get().getShortenUrl()))) {
				
				Optional<PremiumUrlEntity> premiumUrlCheck = Optional.ofNullable(premiumUrlDAO.getPremiumUrl(premiumUrlDetailModel.getShortenUrl())); 
				if (!(premiumUrlCheck.isPresent())) {
					premiumUrlDetails.setShortenUrl(premiumUrlDetailModel.getShortenUrl());
				}
				else {
					return "ALREADY_EXIST";
				}
			}
			premiumUrlDetails.setActualUrl(premiumUrlDetailModel.getActualUrl());
			premiumUrlDetails.setUpd_rec_ts(timeStamp);
    		
			premiumUrlDAO.savePremiumUrl(premiumUrlDetails);
    		return "Success";
    		
		}
		return "failed";
	}
	
	public String deletePremiumUrl(String userId, String shortenUrl) throws Exception {
        
		PremiumUrlEntity premiumUrlDetails = premiumUrlDAO.getByUserIdAndPremiumUrl(userId, shortenUrl);
		Optional<PremiumUrlEntity> checkNull = Optional.ofNullable(premiumUrlDetails);
		
		if (checkNull.isPresent()) {
			
			premiumUrlDAO.deletePremiumUrl(premiumUrlDetails);
    		return "Success";
		}
		return "failed";
	}
	
	public String updatePremiumUrlStatus(PremiumUrlStatusModel premiumUrlStatusModel) throws Exception {
		
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        PremiumUrlEntity premiumUrlDetails = premiumUrlDAO.getByUserIdAndPremiumUrl(premiumUrlStatusModel.getUserId(), premiumUrlStatusModel.getShortenUrl());
		Optional<PremiumUrlEntity> checkNull = Optional.ofNullable(premiumUrlDAO.getByUserIdAndPremiumUrl(premiumUrlStatusModel.getUserId(), premiumUrlStatusModel.getShortenUrl()));
		
		if (checkNull.isPresent()) {
			
			if (premiumUrlStatusModel.getUrlStatus() == 1) {
				
				premiumUrlDetails.setUrlStatus(1);
				premiumUrlDetails.setUpd_rec_ts(timeStamp);
				
				premiumUrlDAO.savePremiumUrl(premiumUrlDetails);
	    		return "Success";
			}
			else {
				
				premiumUrlDetails.setUrlStatus(0);
				premiumUrlDetails.setUpd_rec_ts(timeStamp);
				
				premiumUrlDAO.savePremiumUrl(premiumUrlDetails);
	    		return "Success";
			}
    		
		}
		return "failed";
	}
	
	public List<PremiumUrlDataModel> getPremiumUrlByUserId(String userId) throws Exception {
		
		ArrayList<PremiumUrlDataModel> listOfPremiumUrls = new ArrayList<PremiumUrlDataModel>();
		Optional<List<PremiumUrlEntity>> premiumUrlList = Optional.ofNullable(premiumUrlDAO.getPremiumUrlListByUserId(userId));

		if (premiumUrlList.isPresent()) {
			
			for (PremiumUrlEntity PremiumUrlEnt : premiumUrlList.get()) {
				
				PremiumUrlDataModel premiumUrlDataModel = new PremiumUrlDataModel();
				premiumUrlDataModel.setPremiumUrlIdRec(PremiumUrlEnt.getPremiumUrlIdRec());
				premiumUrlDataModel.setActualUrl(PremiumUrlEnt.getActualUrl());
				premiumUrlDataModel.setShortenUrl(PremiumUrlEnt.getShortenUrl());
				premiumUrlDataModel.setCustomStatus(PremiumUrlEnt.getCustomStatus());
				premiumUrlDataModel.setUrlStatus(PremiumUrlEnt.getUrlStatus());
				premiumUrlDataModel.setCre_rec_ts(PremiumUrlEnt.getCre_rec_ts());
				listOfPremiumUrls.add(premiumUrlDataModel);
			}

		}
		return listOfPremiumUrls;
	}
	
}
