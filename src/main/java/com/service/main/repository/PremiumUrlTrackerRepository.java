package com.service.main.repository;

import java.time.LocalDate;
import java.util.List;

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
	
	@Query(value="SELECT t.premium_url_tracker_id_rec, t.cre_rec_ts, t.hit_date, t.hits, t.shorten_url, t.upd_rec_ts, t.user_id FROM premium_url_tracker t WHERE EXTRACT(DAY FROM t.hit_date) = EXTRACT(DAY FROM NOW()) AND t.user_id=:userId ORDER BY hits DESC LIMIT 3", nativeQuery = true) 
	List<PremiumUrlTrackerEntity> getDailyHits(@Param("userId") String userId);
	
	@Query(value="SELECT t.premium_url_tracker_id_rec, t.cre_rec_ts, t.hit_date, t.hits, t.shorten_url, t.upd_rec_ts, t.user_id FROM premium_url_tracker t WHERE EXTRACT(MONTH FROM t.hit_date) = EXTRACT(MONTH FROM NOW()) AND t.user_id=:userId ORDER BY hits DESC LIMIT 5", nativeQuery = true) 
	List<PremiumUrlTrackerEntity> getMonthlyTopHits(@Param("userId") String userId);
	
}
