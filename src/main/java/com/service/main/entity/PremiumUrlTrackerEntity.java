package com.service.main.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name="PremiumUrlTracker")
@Data
public class PremiumUrlTrackerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "premiumUrlTrackerIdRec", nullable = false, updatable = false, insertable = false)
	private long premiumUrlTrackerIdRec;
	@Column(name = "shortenUrl", nullable = false, updatable = true, insertable = true)
	private String shortenUrl;
	@Column(name = "userId", nullable = false, updatable = true, insertable = true)
	private String userId;
	@Column(name = "hitDate", nullable = false, updatable = true, insertable = true)
	private LocalDate hitDate;
	@Column(name = "hits", nullable = false, updatable = true, insertable = true)
	private int hits;
	@Column(name = "cre_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date cre_rec_ts;
	@Column(name = "upd_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date upd_rec_ts;
	
}
