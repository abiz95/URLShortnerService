package com.service.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.main.entity.ReportIssueEntity;

public interface ReportIssueRepository extends JpaRepository<ReportIssueEntity, String> {

	List<ReportIssueEntity> findByUserId(String userId);
	ReportIssueEntity findByIssueId(String issueId);
}
