package com.service.main.DAO;

import java.util.List;

import com.service.main.entity.GeneralUrlEntity;

public interface GeneralUrlDAO {

	public List<GeneralUrlEntity> getAllGeneralUrl() throws Exception;
	public void saveGeneralUrl(GeneralUrlEntity generalUrlEnt) throws Exception;
	public GeneralUrlEntity getGeneralUrl(String shortenUrl) throws Exception;
	public GeneralUrlEntity getByUserIdAndGeneralUrl(String userId, String shortenUrl) throws Exception;
	public void deleteGeneralUrl(String userId, String shortenUrl) throws Exception;
}
