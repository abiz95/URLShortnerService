package com.service.main.service;

import java.util.List;

import com.service.main.model.DailyTopHitsModel;
import com.service.main.model.MonthlyHitsModel;

public interface AnalyticsService {
	public List<MonthlyHitsModel> getHitsByMonth(String userId) throws Exception;
	public int getPremiumUrlByMonth(String userId, int month, int hitYear) throws Exception;
	public List<DailyTopHitsModel> getTopDailyHits(String userId) throws Exception;
	public List<DailyTopHitsModel> getTopMonthlyHits(String userId) throws Exception;
}
