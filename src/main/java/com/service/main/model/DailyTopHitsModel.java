package com.service.main.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyTopHitsModel {
	
	private String shortenUrl;
	private String userId;
	private LocalDate hitDate;
	private int hits;
}
