package com.service.main.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.main.DAO.PremiumUrlTrackerDAO;
import com.service.main.entity.PremiumUrlTrackerEntity;
import com.service.main.model.DailyTopHitsModel;
import com.service.main.model.MonthlyHitsModel;
import com.service.main.service.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

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
	
	public List<DailyTopHitsModel> getTopDailyHits(String userId) throws Exception {
		ArrayList<DailyTopHitsModel> topDailyURLList = new ArrayList<DailyTopHitsModel>();
		Optional<List<PremiumUrlTrackerEntity>> topHitsDaily = Optional.ofNullable(premiumUrlTrackerDAO.getDailyHitsDetails(userId)); 
		if (topHitsDaily.isPresent()) {
			for (PremiumUrlTrackerEntity topDailyURL : topHitsDaily.get()) {
				DailyTopHitsModel dailyTopHitsModel = new DailyTopHitsModel();
				dailyTopHitsModel.setShortenUrl(topDailyURL.getShortenUrl());
				dailyTopHitsModel.setHitDate(topDailyURL.getHitDate());
				dailyTopHitsModel.setHits(topDailyURL.getHits());
				dailyTopHitsModel.setUserId(topDailyURL.getUserId());
				topDailyURLList.add(dailyTopHitsModel);
			}
		}
		return topDailyURLList;
	}

	public List<DailyTopHitsModel> getTopMonthlyHits(String userId) throws Exception {
		ArrayList<DailyTopHitsModel> topMonthlyURLList = new ArrayList<DailyTopHitsModel>();
		Optional<List<PremiumUrlTrackerEntity>> topMonthlyHits = Optional.ofNullable(premiumUrlTrackerDAO.getTopMonthlyHitsDetails(userId)); 
		if (topMonthlyHits.isPresent()) {
			for (PremiumUrlTrackerEntity topMonthlyURL : topMonthlyHits.get()) {
				DailyTopHitsModel MonthlyTopHitsModel = new DailyTopHitsModel();
				MonthlyTopHitsModel.setShortenUrl(topMonthlyURL.getShortenUrl());
				MonthlyTopHitsModel.setHitDate(topMonthlyURL.getHitDate());
				MonthlyTopHitsModel.setHits(topMonthlyURL.getHits());
				MonthlyTopHitsModel.setUserId(topMonthlyURL.getUserId());
				topMonthlyURLList.add(MonthlyTopHitsModel);
			}
		}
		return topMonthlyURLList;
	}
}
