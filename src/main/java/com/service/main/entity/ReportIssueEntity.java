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
@Table(name="ReportIssue")
@Data
public class ReportIssueEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "issueRecId", nullable = false, updatable = false, insertable = false)
	private long issueRecId;
	@Column(name = "issueId", nullable = false, updatable = true, insertable = true)
	private String issueId;
	@Column(name = "issueTitle", nullable = false, updatable = true, insertable = true)
	private String issueTitle;
	@Column(name = "issueDescription", nullable = false, updatable = true, insertable = true)
	private String issueDescription;
	@Column(name = "issueStatus", nullable = false, updatable = true, insertable = true)
	private String issueStatus;
	@Column(name = "assignTo", nullable = true, updatable = true, insertable = true)
	private String assignTo;
	@Column(name = "userId", nullable = false, updatable = true, insertable = true)
	private String userId;
	@Column(name = "cre_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date cre_rec_ts;
	@Column(name = "upd_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date upd_rec_ts;
	
}
