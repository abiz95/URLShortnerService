package com.service.main.entity;

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
@Table(name="GeneralUrl")
@Data
public class GeneralUrlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "generalUrlIdRec", nullable = false, updatable = false, insertable = false)
	private long generalUrlIdRec;
	@Column(name = "shortenUrl", nullable = false, updatable = true, insertable = true)
	private String shortenUrl;
	@Column(name = "actualUrl", nullable = false, updatable = true, insertable = true)
	private String actualUrl;
	@Column(name = "userId", nullable = false, updatable = true, insertable = true)
	private String userId;
	@Column(name = "urlStatus", nullable = false, updatable = true, insertable = true)
	private int urlStatus;
	@Column(name = "cre_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date cre_rec_ts;
	@Column(name = "upd_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date upd_rec_ts;
	
}
