package com.service.main.DAO;

import java.time.LocalDate;
import java.util.List;

import com.service.main.entity.PremiumUrlTrackerEntity;

public interface PremiumUrlTrackerDAO {

	public PremiumUrlTrackerEntity getPremiumUrlTrackerDetails(long premiumUrlTrackerIdRec) throws Exception;
	public PremiumUrlTrackerEntity getPremiumUrlTracker(String userId, String shortenUrl, LocalDate localDate) throws Exception;
	public PremiumUrlTrackerEntity savePremiumUrlTracker(PremiumUrlTrackerEntity premiumUrlTrackerEntity) throws Exception;
	public Integer getSumOfMonthlyHits(String userId, Integer hitDate, Integer hitYear) throws Exception;
	public List<PremiumUrlTrackerEntity> getDailyHitsDetails(String userId) throws Exception;
	public List<PremiumUrlTrackerEntity> getTopMonthlyHitsDetails(String userId) throws Exception;
//	public List<String> getUrlMothlyHits(String userId, Integer hitDate, Integer hitYear) throws Exception;
}
