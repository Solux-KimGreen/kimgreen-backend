package com.kimgreen.backend.domain.community.repository;

import com.kimgreen.backend.domain.community.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
