package com.service.main.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.PremiumUrlTrackerDAO;
import com.service.main.model.MonthlyHitsModel;

@Service
public class AnalyticsServiceImpl {

	@Autowired
	private PremiumUrlTrackerDAO premiumUrlTrackerDAO;
	
	public int getPremiumUrlByMonth(String userId, int month, int hitYear) throws Exception {
		
//		ArrayList<PremiumUrlDataModel> listOfPremiumUrls = new ArrayList<PremiumUrlDataModel>();
		int premiumUrlList = premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, hitYear);

		return premiumUrlList;
	}
	
	public List<MonthlyHitsModel> getHitsByMonth(String userId) throws Exception {
		
	    Calendar calendar = Calendar.getInstance();
	    
	    SimpleDateFormat dateFormat=new SimpleDateFormat("MMM YYYY");
	    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
	    SimpleDateFormat yearFormat=new SimpleDateFormat("YYYY");
	    
	    String date = dateFormat.format(calendar.getTime());
	    Integer month = Integer.parseInt(monthFormat.format(calendar.getTime()));
	    Integer year = Integer.parseInt(yearFormat.format(calendar.getTime()));
	    		
	    ArrayList<MonthlyHitsModel> monthlyList = new ArrayList<MonthlyHitsModel>();
	    
//	    Integer totalHits = premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, year);
	    Optional<Integer> totalHits = Optional.ofNullable(premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, year));
	    
	    if (totalHits.isPresent()) {
	    	
	    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
		    monthlyHitsModel.setDate(date);
		    monthlyHitsModel.setHits(totalHits.get());
		    monthlyList.add(monthlyHitsModel);
		}
	    else {
	    	
	    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
		    monthlyHitsModel.setDate(date);
		    monthlyHitsModel.setHits(0);
		    monthlyList.add(monthlyHitsModel);
		}
	    
	    for (int i = 1; i < 12; i++) {
	    	
	    	calendar.add(Calendar.MONTH, -1);
	    	
		    date = dateFormat.format(calendar.getTime());
		    month = Integer.parseInt(monthFormat.format(calendar.getTime()));
		    year = Integer.parseInt(yearFormat.format(calendar.getTime()));
		    
		    totalHits = Optional.ofNullable(premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, year));
		    
		    if (totalHits.isPresent()) {
		    	
		    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
			    monthlyHitsModel.setDate(date);
			    monthlyHitsModel.setHits(totalHits.get());
			    monthlyList.add(monthlyHitsModel);
			}
		    else {
		    	
		    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
			    monthlyHitsModel.setDate(date);
			    monthlyHitsModel.setHits(0);
			    monthlyList.add(monthlyHitsModel);
			}
		    
	    }

	    return monthlyList;
	}
	
//	public List<MonthlyHitsModel> getTopHitsByMonth(String userId) throws Exception {
//		
//	    Calendar calendar = Calendar.getInstance();
//	    
//	    SimpleDateFormat dateFormat=new SimpleDateFormat("MMM YYYY");
//	    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
//	    SimpleDateFormat yearFormat=new SimpleDateFormat("YYYY");
//	    
//	    String date = dateFormat.format(calendar.getTime());
//	    Integer month = Integer.parseInt(monthFormat.format(calendar.getTime()));
//	    Integer year = Integer.parseInt(yearFormat.format(calendar.getTime()));
//	    		
////	    ArrayList<MonthlyHitsModel> monthlyList = new ArrayList<MonthlyHitsModel>();
//	    
////	    Integer totalHits = premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, year);
//	    Optional<List<String>> topHits = Optional.ofNullable(premiumUrlTrackerDAO.getUrlMothlyHits(userId, month, year));
//	    
//	    for (String topHitUrls : topHits.get()) {
//			
//		}
//	    
//	    if (totalHits.isPresent()) {
//	    	
//	    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
//		    monthlyHitsModel.setDate(date);
//		    monthlyHitsModel.setHits(totalHits.get());
//		    monthlyList.add(monthlyHitsModel);
//		}
//	    else {
//	    	
//	    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
//		    monthlyHitsModel.setDate(date);
//		    monthlyHitsModel.setHits(0);
//		    monthlyList.add(monthlyHitsModel);
//		}
//	    
//	    for (int i = 1; i < 12; i++) {
//	    	
//	    	calendar.add(Calendar.MONTH, -1);
//	    	
//		    date = dateFormat.format(calendar.getTime());
//		    month = Integer.parseInt(monthFormat.format(calendar.getTime()));
//		    year = Integer.parseInt(yearFormat.format(calendar.getTime()));
//		    
//		    totalHits = Optional.ofNullable(premiumUrlTrackerDAO.getSumOfMonthlyHits(userId, month, year));
//		    
//		    if (totalHits.isPresent()) {
//		    	
//		    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
//			    monthlyHitsModel.setDate(date);
//			    monthlyHitsModel.setHits(totalHits.get());
//			    monthlyList.add(monthlyHitsModel);
//			}
//		    else {
//		    	
//		    	MonthlyHitsModel monthlyHitsModel = new MonthlyHitsModel();
//			    monthlyHitsModel.setDate(date);
//			    monthlyHitsModel.setHits(0);
//			    monthlyList.add(monthlyHitsModel);
//			}
//		    
//	    }
//
//	    return monthlyList;
//	}
	
}
