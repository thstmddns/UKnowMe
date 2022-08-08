package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Block;
import com.ssafy.uknowme.web.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface BlockRepository extends JpaRepository<Block, Integer>{


    List<Block> findByBlockingMemberSeq(int seq);
}

