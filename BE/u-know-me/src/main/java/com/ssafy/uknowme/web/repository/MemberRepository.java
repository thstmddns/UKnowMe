package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer>{

    // id로 회원 검색
    Optional<Member> findById(String id);

    Optional<Member> findByNameAndTel(String name, String tel);

    Optional<Member> findByTel(String tel);

    @Query("select m from Member m where substring(m.birth, 5, 4) = :birthday")
    Optional<Member> findByBirthday(@Param("birthday") String birthday);

    //아이디 중복체크
    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);
}

