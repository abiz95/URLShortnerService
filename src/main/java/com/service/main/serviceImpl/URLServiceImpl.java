package com.service.main.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.GeneralUrlDAO;
import com.service.main.DAO.PremiumUrlDAO;
import com.service.main.DAO.PremiumUrlTrackerDAO;
import com.service.main.entity.GeneralUrlEntity;
import com.service.main.entity.PremiumUrlEntity;
import com.service.main.entity.PremiumUrlTrackerEntity;
import com.service.main.service.URLService;

@Service
public class URLServiceImpl implements URLService {

	@Autowired
	private GeneralUrlDAO generalUrlDAO;
	
	@Autowired
	private PremiumUrlDAO premiumUrlDAO;
	
	@Autowired
	private PremiumUrlTrackerDAO premiumUrlTrackerDAO;
	
	public String getUrlDetails(String shortenUrl) throws Exception {
		
		Optional<GeneralUrlEntity> generalNullCheck = Optional.ofNullable(generalUrlDAO.getGeneralUrl(shortenUrl));  
        Optional<PremiumUrlEntity> premiumNullCheck = Optional.ofNullable(premiumUrlDAO.getPremiumUrl(shortenUrl)); 
		
        if (premiumNullCheck.isPresent() && premiumNullCheck.get().getUrlStatus()==1) {
        	
        	java.util.Date CurrentDate = new Date();
        	Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        	   
        	Optional<PremiumUrlTrackerEntity> premiumUrlTrackerData = Optional.ofNullable(premiumUrlTrackerDAO.getPremiumUrlTracker(premiumNullCheck.get().getUserId(), premiumNullCheck.get().getShortenUrl(), java.time.LocalDate.now()));
        	
        	if (premiumUrlTrackerData.isPresent()) {
        		
        		if (premiumUrlTrackerData.get().getHitDate().compareTo(java.time.LocalDate.now()) == 0) {
					
            		PremiumUrlTrackerEntity premiumUrlTrackerDetails = premiumUrlTrackerDAO.getPremiumUrlTrackerDetails(premiumUrlTrackerData.get().getPremiumUrlTrackerIdRec());
            		
            		premiumUrlTrackerDetails.setHitDate(premiumUrlTrackerData.get().getHitDate());
            		premiumUrlTrackerDetails.setHits(premiumUrlTrackerData.get().getHits()+1);
            		premiumUrlTrackerDetails.setUpd_rec_ts(timeStamp);
            		
            		premiumUrlTrackerDAO.savePremiumUrlTracker(premiumUrlTrackerDetails);
				}
				
			}
        	else {
				
        		PremiumUrlTrackerEntity premiumUrlTrackerEnt = new PremiumUrlTrackerEntity();
        		premiumUrlTrackerEnt.setUserId(premiumNullCheck.get().getUserId());
        		premiumUrlTrackerEnt.setShortenUrl(premiumNullCheck.get().getShortenUrl());
        		premiumUrlTrackerEnt.setHits(1);
        		premiumUrlTrackerEnt.setHitDate(java.time.LocalDate.now());
        		premiumUrlTrackerEnt.setUpd_rec_ts(timeStamp);
        		premiumUrlTrackerEnt.setCre_rec_ts(timeStamp);
        		
        		premiumUrlTrackerDAO.savePremiumUrlTracker(premiumUrlTrackerEnt);
			}
        	return premiumNullCheck.get().getActualUrl();
		}
        else if (generalNullCheck.isPresent()) {
			return generalNullCheck.get().getActualUrl();
		}
		return "NOT_FOUND";
        
	}
	
}
