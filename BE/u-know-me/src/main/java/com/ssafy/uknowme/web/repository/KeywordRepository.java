package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    Optional<Keyword> findBySeq(int seq);
}
