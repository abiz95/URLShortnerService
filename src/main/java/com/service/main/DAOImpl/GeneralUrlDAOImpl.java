package com.service.main.DAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.main.DAO.GeneralUrlDAO;
import com.service.main.entity.GeneralUrlEntity;
import com.service.main.repository.GeneralUrlRepository;

@Component
public class GeneralUrlDAOImpl implements GeneralUrlDAO {

	@Autowired
	private GeneralUrlRepository generalUrlRepository;
	
	public List<GeneralUrlEntity> getAllGeneralUrl() throws Exception {
		return generalUrlRepository.findAll();
	}
	
	public void saveGeneralUrl(GeneralUrlEntity generalUrlEnt) throws Exception {
		generalUrlRepository.save(generalUrlEnt);
	}
	
	public GeneralUrlEntity getGeneralUrl(String shortenUrl) throws Exception {
		return generalUrlRepository.findByShortenUrl(shortenUrl);
	}
	
	public GeneralUrlEntity getByUserIdAndGeneralUrl(String userId, String shortenUrl) throws Exception {
		return generalUrlRepository.findByUserIdAndShortenUrl(userId, shortenUrl);
	}
	
	public void deleteGeneralUrl(String userId, String shortenUrl) throws Exception {
		generalUrlRepository.deleteByUserIdAndShortenUrl(userId, shortenUrl);
	}
	
}
