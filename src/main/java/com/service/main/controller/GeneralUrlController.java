package com.service.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.entity.GeneralUrlEntity;
import com.service.main.model.GeneralUrlModel;
import com.service.main.service.GeneralUrlService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class GeneralUrlController {

	@Autowired
	private GeneralUrlService generalUrlService;
	
	static final Logger logger = LoggerFactory.getLogger(GeneralUrlController.class);	
	
	//To be removed
	@GetMapping("/allGeneralUrls")
    public ResponseEntity<?> getAllGeneralUrl() {
       
		List<GeneralUrlEntity> generalUrlList = null;
        try {
        	generalUrlList = generalUrlService.getAllGeneralUrl();
            logger.info("[GeneralUrlController] [getAllGeneralUrl] getting general URL");
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[GeneralUrlController] [getAllGeneralUrl] error occured while retriving general URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(generalUrlList, HttpStatus.OK);
    }
	
//	@GetMapping("/general/details/{userId}/{shortenUrl}")
//    public ResponseEntity<?> getGeneralUrlDetails(@PathVariable String userId, @PathVariable String shortenUrl) {
//       
//		GeneralUrlDetailModel generalUrlList = null;
//        try {
//        	generalUrlList = generalUrlService.getGeneralUrlDetails(userId, shortenUrl);
//            logger.info("[GeneralUrlController] [getAllGeneralUrl] getting general URL");
//           
//        } catch (Exception e) {
//            // TODO: handle exception
//            logger.error("[GeneralUrlController] [getAllGeneralUrl] error occured while retriving general URL"+e);
//            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>(generalUrlList, HttpStatus.OK);
//    }
	
    @PostMapping("/general/saveUrl")
    public ResponseEntity<?> saveGeneralUrl(@RequestBody GeneralUrlModel generalUrlModel) {
       
    	String shortenUrl = null;
        try {
        	shortenUrl = generalUrlService.saveGeneralUrl(generalUrlModel);
            logger.info("[GeneralUrlController] [saveGeneralUrl] saving URL details...");
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[GeneralUrlController] [saveGeneralUrl] error occured while saving URL details"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(shortenUrl, HttpStatus.OK);
    }
	
//	@PutMapping("/general/update")
//    public ResponseEntity<?> updateGeneralUrlDetails(@RequestBody GeneralUrlDetailModel generalUrlDetailModel) {
//       
//		String updateStatus = null;
//        try {
//        	updateStatus = generalUrlService.updateGeneralUrl(generalUrlDetailModel);
//            logger.info("[GeneralUrlController] [updateGeneralUrlDetails] updating URL details");
//            if (updateStatus == "failed") {
//            	logger.error("[GeneralUrlController] [updateGeneralUrlDetails] URL details not found");
//            	return new ResponseEntity<>(updateStatus,HttpStatus.NO_CONTENT);
//			}
//           
//        } catch (Exception e) {
//            // TODO: handle exception
//            logger.error("[GeneralUrlController] [updateGeneralUrlDetails] error occured while updating URL details"+e);
//            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
//    }
//	
//    @DeleteMapping("/general/deleteUrl")
//    public ResponseEntity<?> deleteGeneralUrl(@RequestBody GeneralUrlParamModel generalUrlParamModel) {
//       
//		String deleteStatus = null;
//        try {
//        	deleteStatus = generalUrlService.deleteGeneralUrl(generalUrlParamModel);
//            logger.info("[GeneralUrlController] [deleteGeneralUrl] deleting URL details");
//            if (deleteStatus == "failed") {
//            	logger.error("[GeneralUrlController] [deleteGeneralUrl] URL details not found");
//            	return new ResponseEntity<>(deleteStatus,HttpStatus.NO_CONTENT);
//			}
//           
//        } catch (Exception e) {
//            // TODO: handle exception
//            logger.error("[GeneralUrlController] [deleteGeneralUrl] error occured while deleting URL details"+e);
//            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
//        
//    }
	
}
