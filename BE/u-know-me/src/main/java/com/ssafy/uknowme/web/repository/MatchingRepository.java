package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.MatchingResponseDto;
import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface MatchingRepository extends JpaRepository<Member, Integer> {

    Member findById(String id);
}
