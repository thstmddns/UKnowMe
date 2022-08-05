package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Optional<Notice> findBySeq(int seq);


}
