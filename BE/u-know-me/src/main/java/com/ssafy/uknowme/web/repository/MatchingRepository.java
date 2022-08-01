package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.MatchingResonseDto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchingRepository extends JpaRepository {

    MatchingResonseDto findById(String id);
}
