package com.service.main.service;

import java.util.List;

import com.service.main.entity.PremiumUrlEntity;
import com.service.main.model.PremiumUrlDataModel;
import com.service.main.model.PremiumUrlDetailModel;
import com.service.main.model.PremiumUrlModel;
import com.service.main.model.PremiumUrlStatusModel;

public interface PremiumUrlService {

	public List<PremiumUrlEntity> getAllPremiumUrl() throws Exception;
	public String savePremiumUrl(PremiumUrlModel premiumUrlModel) throws Exception;
	public String savePremiumCustomUrl(PremiumUrlDetailModel premiumUrlDetailModel) throws Exception;
	public String getPremiumCustomUrl(String shortenUrl) throws Exception;
	public PremiumUrlDetailModel getPremiumUrlDetails(String userId, String shortenUrl) throws Exception;
	public String updatePremiumUrl(PremiumUrlDetailModel premiumUrlDetailModel) throws Exception;
	public String deletePremiumUrl(String userId, String shortenUrl) throws Exception;
	public String updatePremiumUrlStatus(PremiumUrlStatusModel premiumUrlStatusModel) throws Exception;
	public List<PremiumUrlDataModel> getPremiumUrlByUserId(String userId) throws Exception;
}
