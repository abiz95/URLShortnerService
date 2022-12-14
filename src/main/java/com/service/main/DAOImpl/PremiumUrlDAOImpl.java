package com.service.main.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.PremiumUrlDAO;
import com.service.main.entity.PremiumUrlEntity;
import com.service.main.repository.PremiumUrlRepository;

@Component
public class PremiumUrlDAOImpl implements PremiumUrlDAO {
	
	@Autowired
	private PremiumUrlRepository premiumUrlRepository;
	
	public List<PremiumUrlEntity> getAllPremiumUrl() throws Exception {
		return premiumUrlRepository.findAll();
	}
	
	public void savePremiumUrl(PremiumUrlEntity premiumUrlEntity) throws Exception {
		premiumUrlRepository.save(premiumUrlEntity);
	}
	
	public PremiumUrlEntity getPremiumUrl(String shortenUrl) throws Exception {
		return premiumUrlRepository.findByShortenUrl(shortenUrl);
	}
	
	public PremiumUrlEntity getByUserIdAndPremiumUrl(String userId, String shortenUrl) throws Exception {
		return premiumUrlRepository.findByUserIdAndShortenUrl(userId, shortenUrl);
	}
	
	public void deletePremiumUrl(PremiumUrlEntity premiumUrlEntity) throws Exception {
		premiumUrlRepository.delete(premiumUrlEntity);
	}
	
	public List<PremiumUrlEntity> getPremiumUrlListByUserId(String userId) throws Exception {
		return premiumUrlRepository.findByUserId(userId);
	}
	
	public PremiumUrlEntity getPremiumUrlByRecId(long premiumUrlIdRec) throws Exception {
		return premiumUrlRepository.findByPremiumUrlIdRec(premiumUrlIdRec);
	}
}
