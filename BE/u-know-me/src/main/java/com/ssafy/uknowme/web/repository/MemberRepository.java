package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {


//
//    boolean login(MemberRequestDto member);

    Member findByIdAndPassword(String id, String password);

    // id로 회원 검색
    Optional<Member> findById(String id);

    //아이디 중복체크
    boolean existsById(String memberId);

    // name으로 회원 검색
//    Optional<Member> findByName(String name);

    // 회원 모두 출력
//    List<Member> findAll();

//    Optional<Member> findByEmail(String email);
    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);

    Optional<User> findByEmail(String email);
}