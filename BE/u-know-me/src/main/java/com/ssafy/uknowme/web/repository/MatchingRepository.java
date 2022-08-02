package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MatchingRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(String id);
}
