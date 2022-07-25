package com.ssafy.uknowme.repository;

import com.ssafy.uknowme.web.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 회원생성
    Member save(Member member);

    Member login(Member member);

    // id로 회원 검색
    Optional<Member> findById(String id);

    // name으로 회원 검색
    Optional<Member> findByName(String name);

    // 회원 모두 출력
    List<Member> findAll();
}
