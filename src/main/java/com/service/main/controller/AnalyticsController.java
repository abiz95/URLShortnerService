package com.service.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.model.DailyTopHitsModel;
import com.service.main.model.MonthlyHitsModel;
import com.service.main.service.AnalyticsService;

@RestController
public class AnalyticsController {

	@Autowired
	private AnalyticsService analyticsService;
	
	static final Logger logger = LoggerFactory.getLogger(PremiumUrlController.class);	
	
	@GetMapping("/query/{month}/{year}")
    public ResponseEntity<?> getPremiumUrlByMonth(@PathVariable int month, @PathVariable int year) {
       
		int premiumUrlList = 0;
        try {
        	premiumUrlList = analyticsService.getPremiumUrlByMonth("Mwbwvk", month, year);
            logger.info("[AnalyticsController] [getPremiumUrlByMonth] getting premium URL");
//            if (premiumUrlList.isEmpty()) {
//				
//            	logger.info("[AnalyticsController] [getPremiumUrlByMonth] premium URL details not found");
//            	return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
//			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[AnalyticsController] [getPremiumUrlByMonth] error occured while retriving premium URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/analytics/monthly/{userId}")
    public ResponseEntity<?> getMonthlyHitAnalytics(@PathVariable String userId) {
       
		List<MonthlyHitsModel> premiumUrlList = null;
        try {
        	premiumUrlList = analyticsService.getHitsByMonth(userId);
            logger.info("[AnalyticsController] [getMonthlyHitAnalytics] analyzing premium URL hits");
            if (premiumUrlList.isEmpty()) {
				
            	logger.info("[AnalyticsController] [getMonthlyHitAnalytics] no data available for the URL");
            	return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[AnalyticsController] [getMonthlyHitAnalytics] error occured while analyzing premium URL hits"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/analytics/top/daily/{userId}")
    public ResponseEntity<?> getDailyHitAnalytics(@PathVariable String userId) {
       
		List<DailyTopHitsModel> premiumUrlList = null;
        try {
        	premiumUrlList = analyticsService.getTopDailyHits(userId);
            logger.info("[AnalyticsController] [getDailyHitAnalytics] analyzing daily URL hits");
            if (premiumUrlList.isEmpty()) {
				
            	logger.info("[AnalyticsController] [getDailyHitAnalytics] no daily data available for the URL");
            	return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[AnalyticsController] [getDailyHitAnalytics] error occured while analyzing top daily URL hits"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/analytics/top/monthly/{userId}")
    public ResponseEntity<?> getTopMonthlyHitAnalytics(@PathVariable String userId) {
       
		List<DailyTopHitsModel> premiumUrlList = null;
        try {
        	premiumUrlList = analyticsService.getTopMonthlyHits(userId);
            logger.info("[AnalyticsController] [getTopMonthlyHitAnalytics] analyzing monthly URL hits");
            if (premiumUrlList.isEmpty()) {
				
            	logger.info("[AnalyticsController] [getTopMonthlyHitAnalytics] no monthly data available for the URL");
            	return new ResponseEntity<Error>(HttpStatus.NO_CONTENT);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[AnalyticsController] [getTopMonthlyHitAnalytics] error occured while analyzing top monthly URL hits"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(premiumUrlList, HttpStatus.OK);
    }
	
	@GetMapping("/test")
    public ResponseEntity<?> test() {
       
		HashMap<ArrayList<String>, ArrayList<String>> map = new HashMap<>();
		
        try {
            logger.info("[AnalyticsController] [getPremiumUrlByMonth] getting premium URL");
    		ArrayList<String> key = new ArrayList<String>();
    		key.add("Jan");
    		key.add("Feb");
    		ArrayList<String> value = new ArrayList<String>();
    		value.add("20");
    		value.add("90");
    		map.put(key, value);
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[AnalyticsController] [getPremiumUrlByMonth] error occured while retriving premium URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	
}
