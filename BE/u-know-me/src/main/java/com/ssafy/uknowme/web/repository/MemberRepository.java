package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer>{

    // id로 회원 검색
    Optional<Member> findById(String id);

    Optional<Member> findByNameAndTel(String name, String tel);

    //아이디 중복체크
    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);
}

