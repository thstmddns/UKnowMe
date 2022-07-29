package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.MemberRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer>{
    static boolean existsByMember(String memberId) {
        return MemberRepository.existsByMemberId(memberId);
    }


    // 회원생성
    @Override
    Member save(Member member);
//
//    boolean login(MemberRequestDto member);

    Member findByIdAndPassword(String id, String password);

    // id로 회원 검색
    Optional<Member> findById(String id);

    //아이디 중복체크
    static boolean existsByMemberId(String memberId) {
        return MemberRepository.existsByMemberId(memberId);

    }

    // name으로 회원 검색
//    Optional<Member> findByName(String name);

    // 회원 모두 출력
//    List<Member> findAll();
}
