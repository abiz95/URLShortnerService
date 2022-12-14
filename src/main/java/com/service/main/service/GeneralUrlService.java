package com.service.main.service;

import java.util.List;

import com.service.main.entity.GeneralUrlEntity;
import com.service.main.model.GeneralUrlDetailModel;
import com.service.main.model.GeneralUrlModel;
import com.service.main.model.GeneralUrlParamModel;

public interface GeneralUrlService {
	
	public List<GeneralUrlEntity> getAllGeneralUrl() throws Exception;
	public String saveGeneralUrl(GeneralUrlModel generalUrlModel) throws Exception;
//	public String getGeneralUrl(String shortenUrl) throws Exception;
	public GeneralUrlDetailModel getGeneralUrlDetails(String userId, String shortenUrl) throws Exception;
	public String updateGeneralUrl(GeneralUrlDetailModel generalUrlDetailModel) throws Exception;
	public String deleteGeneralUrl(GeneralUrlParamModel generalUrlParamModel) throws Exception;
}
