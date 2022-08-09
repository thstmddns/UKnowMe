package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    @Query("select p from Participation p where p.member.seq = :memberSeq")
    Participation findByMemberSeq(@Param("memberSeq") int memberSeq);
}
