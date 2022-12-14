package com.service.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.main.entity.GeneralUrlEntity;

@Repository
public interface GeneralUrlRepository extends JpaRepository<GeneralUrlEntity, String> {

	GeneralUrlEntity findByShortenUrl(String shortenUrl) throws Exception;
	GeneralUrlEntity findByUserIdAndShortenUrl(String userId, String shortenUrl) throws Exception;
	void deleteByUserIdAndShortenUrl(String userId, String shortenUrl);
}
