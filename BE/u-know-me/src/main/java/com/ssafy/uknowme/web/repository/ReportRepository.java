package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Integer>{


    List<Report> findAllByCreateDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);

}

