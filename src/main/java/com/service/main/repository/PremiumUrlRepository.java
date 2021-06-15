package com.service.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.main.entity.PremiumUrlEntity;

public interface PremiumUrlRepository extends JpaRepository<PremiumUrlEntity, String> {
	PremiumUrlEntity findByShortenUrl(String shortenUrl) throws Exception;
	PremiumUrlEntity findByUserIdAndShortenUrl(String userId, String shortenUrl);
	void deleteByShortenUrl(String shortenUrl);
	void deleteByUserIdAndShortenUrl(String userId, String shortenUrl);
	List<PremiumUrlEntity> findByUserId(String userId) throws Exception;
	PremiumUrlEntity findByPremiumUrlIdRec(long premiumUrlIdRec) throws Exception;
}
