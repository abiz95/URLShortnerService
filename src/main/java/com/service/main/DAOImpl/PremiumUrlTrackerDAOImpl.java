package com.service.main.DAOImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.PremiumUrlTrackerDAO;
import com.service.main.entity.PremiumUrlTrackerEntity;
import com.service.main.repository.PremiumUrlTrackerRepository;

@Component
public class PremiumUrlTrackerDAOImpl implements PremiumUrlTrackerDAO {

	@Autowired
	private PremiumUrlTrackerRepository premiumUrlTrackerRepository;
	
	public PremiumUrlTrackerEntity getPremiumUrlTrackerDetails(long premiumUrlTrackerIdRec) throws Exception {
		return premiumUrlTrackerRepository.findByPremiumUrlTrackerIdRec(premiumUrlTrackerIdRec);
	}
	
	public PremiumUrlTrackerEntity getPremiumUrlTracker(String userId, String shortenUrl, LocalDate hitDate) throws Exception {
		return premiumUrlTrackerRepository.findByUserIdAndShortenUrlAndHitDate(userId, shortenUrl, hitDate);
	}
	
	public PremiumUrlTrackerEntity savePremiumUrlTracker(PremiumUrlTrackerEntity premiumUrlTrackerEntity) throws Exception {
		return premiumUrlTrackerRepository.save(premiumUrlTrackerEntity);
	}
	
	public Integer getSumOfMonthlyHits(String userId, Integer hitDate, Integer hitYear) throws Exception {
		return premiumUrlTrackerRepository.getSumOfMonthlyHits(userId, hitDate, hitYear);
	}
	
	public List<PremiumUrlTrackerEntity> getDailyHitsDetails(String userId) throws Exception {
		return premiumUrlTrackerRepository.getDailyHits(userId);
	}
	
	public List<PremiumUrlTrackerEntity> getTopMonthlyHitsDetails(String userId) throws Exception {
		return premiumUrlTrackerRepository.getMonthlyTopHits(userId);
	}
}
