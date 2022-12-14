package com.service.main.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.main.entity.PremiumUrlTrackerEntity;

public interface PremiumUrlTrackerRepository extends JpaRepository<PremiumUrlTrackerEntity, String> {

	PremiumUrlTrackerEntity findByUserIdAndShortenUrlAndHitDate(String userId, String shortenUrl, LocalDate hitDate);
	PremiumUrlTrackerEntity findByPremiumUrlTrackerIdRec(long premiumUrlTrackerIdRec);
	
//	@Query("select p from PremiumUrlTrackerEntity p where p.userId=:userId and year(p.hitDate)=:hitYear and month(p.hitDate)=:hitMonth order by p.hits desc limit 10") 
//	List<String> getHitsByMonth(@Param("userId") String userId, @Param("hitMonth") int hitMonth, @Param("hitYear") int hitYear);
	
	@Query("select sum(p.hits) from PremiumUrlTrackerEntity p where p.userId=:userId and year(p.hitDate)=:hitYear and month(p.hitDate)=:hitMonth") 
	Integer getSumOfMonthlyHits(@Param("userId") String userId, @Param("hitMonth") Integer hitMonth, @Param("hitYear") Integer hitYear);
	
}
