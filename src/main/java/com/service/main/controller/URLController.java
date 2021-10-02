package com.service.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.service.main.service.URLService;

@RestController
public class URLController {

	@Autowired
	private URLService urlService;
	
	static final Logger logger = LoggerFactory.getLogger(URLController.class);
	
	@GetMapping("/url/{shortenUrl}")
    public ResponseEntity<?> getUrl(@PathVariable String shortenUrl) {
       
		String actualUrl = null;
        try {
            logger.info("[URLController] [getUrl] getting actual URL");
            actualUrl = urlService.getUrlDetails(shortenUrl);
            if (actualUrl=="NOT_FOUND") {
            	logger.info("[URLController] [getUrl] URL not found");
            	return new ResponseEntity<>("404: NOT FOUND", HttpStatus.NOT_FOUND);
			}
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("[URLController] [getUrl] error occured while retriving actual URL"+e);
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(actualUrl, HttpStatus.OK);
    }
	

	
}
