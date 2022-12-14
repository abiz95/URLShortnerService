package com.service.main.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.GeneralUrlDAO;
import com.service.main.DAO.PremiumUrlDAO;
import com.service.main.entity.GeneralUrlEntity;
import com.service.main.entity.PremiumUrlEntity;
import com.service.main.model.GeneralUrlDetailModel;
import com.service.main.model.GeneralUrlModel;
import com.service.main.model.GeneralUrlParamModel;
import com.service.main.service.GeneralUrlService;
import com.service.main.util.RandomIdGenerator;

@Service
public class GeneralUrlServiceImpl implements GeneralUrlService {

	@Autowired
	private GeneralUrlDAO generalUrlDAO;
	
	@Autowired
	private PremiumUrlDAO premiumUrlDAO;
	
	@Autowired
	private RandomIdGenerator randomIdGenerator;
	
	//To be removed
	public List<GeneralUrlEntity> getAllGeneralUrl() throws Exception {
		
		List<GeneralUrlEntity> generalUrlData = generalUrlDAO.getAllGeneralUrl();
		return generalUrlData;
	}
	
	public String saveGeneralUrl(GeneralUrlModel generalUrlModel) throws Exception {
		
		Boolean generalIdIndicator = true;
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
        		
        		GeneralUrlEntity newGeneralUrl = new GeneralUrlEntity();
        		newGeneralUrl.setActualUrl(generalUrlModel.getActualUrl());
        		newGeneralUrl.setShortenUrl(shortenUrl);
        		newGeneralUrl.setUserId("NA");
        		newGeneralUrl.setUrlStatus(1);
        		newGeneralUrl.setCre_rec_ts(timeStamp);
        		newGeneralUrl.setUpd_rec_ts(timeStamp);
        		
        		generalUrlDAO.saveGeneralUrl(newGeneralUrl);
        		generalIdIndicator = false;
			}
    		
		} while (generalIdIndicator);
        return shortenUrl;
        
	}
	
//	public String getGeneralUrl(String shortenUrl) throws Exception {
//		
//		GeneralUrlEntity generalUrlData = generalUrlDAO.getGeneralUrl(shortenUrl);
//		return generalUrlData.getActualUrl();
//	}
	
	public GeneralUrlDetailModel getGeneralUrlDetails(String userId, String shortenUrl) throws Exception {
		
		GeneralUrlEntity generalUrlDetails = generalUrlDAO.getByUserIdAndGeneralUrl(userId, shortenUrl);
		Optional<GeneralUrlEntity> checkNull = Optional.ofNullable(generalUrlDetails);
		GeneralUrlDetailModel generalUrlData = new GeneralUrlDetailModel();
		if (checkNull.isPresent()) {
			
			generalUrlData.setUserId(generalUrlDetails.getUserId());
			generalUrlData.setActualUrl(generalUrlDetails.getActualUrl());
			generalUrlData.setShortenUrl(generalUrlDetails.getShortenUrl());
    		
		}
		return generalUrlData;
	}
	
	public String updateGeneralUrl(GeneralUrlDetailModel generalUrlDetailModel) throws Exception {
		
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
		GeneralUrlEntity generalUrlDetails = generalUrlDAO.getByUserIdAndGeneralUrl(generalUrlDetailModel.getUserId(), generalUrlDetailModel.getShortenUrl());
		Optional<GeneralUrlEntity> checkNull = Optional.ofNullable(generalUrlDetails);
		
		if (checkNull.isPresent()) {
			
			generalUrlDetails.setActualUrl(generalUrlDetailModel.getActualUrl());
			generalUrlDetails.setUpd_rec_ts(timeStamp);
    		
    		generalUrlDAO.saveGeneralUrl(generalUrlDetails);
    		return "Success";
    		
		}
		return "failed";
	}
	
	public String deleteGeneralUrl(GeneralUrlParamModel generalUrlParamModel) throws Exception {
        
		GeneralUrlEntity generalUrlDetails = generalUrlDAO.getByUserIdAndGeneralUrl(generalUrlParamModel.getUserId(), generalUrlParamModel.getShortenUrl());
		Optional<GeneralUrlEntity> checkNull = Optional.ofNullable(generalUrlDetails);
		
		if (checkNull.isPresent()) {
			
    		generalUrlDAO.deleteGeneralUrl(generalUrlParamModel.getUserId(), generalUrlParamModel.getShortenUrl());
    		return "Success";
		}
		return "failed";
	}
		
	
}
