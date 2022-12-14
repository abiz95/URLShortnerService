package com.service.main.DAO;

import java.util.List;

import com.service.main.entity.PremiumUrlEntity;

public interface PremiumUrlDAO {

	public List<PremiumUrlEntity> getAllPremiumUrl() throws Exception;
	public void savePremiumUrl(PremiumUrlEntity premiumUrlEntity) throws Exception;
	public PremiumUrlEntity getPremiumUrl(String shortenUrl) throws Exception;
	public PremiumUrlEntity getByUserIdAndPremiumUrl(String userId, String shortenUrl) throws Exception;
//	public void deletePremiumUrl(String userId, String shortenUrl) throws Exception;
	public void deletePremiumUrl(PremiumUrlEntity premiumUrlEntity) throws Exception;
	public List<PremiumUrlEntity> getPremiumUrlListByUserId(String userId) throws Exception;
	public PremiumUrlEntity getPremiumUrlByRecId(long premiumUrlIdRec) throws Exception;
}
