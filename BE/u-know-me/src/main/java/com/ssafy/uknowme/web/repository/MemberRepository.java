package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.MemberDto.ManageMemberInfoResponseDto;
import com.ssafy.uknowme.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer>{

    // id로 회원 검색
    Optional<Member> findById(String id);

    Optional<Member> findByNameAndTel(String name, String tel);

    @Query("select new com.ssafy.uknowme.model.dto.MemberDto.ManageMemberInfoResponseDto(m.seq, m.id, m.name, m.nickname, m.gender, m.birth, m.tel, m.smoke, m.address, m.reportState, count(r.seq), m.createDate, m.updateDate) " +
            "from Member m " +
            "left join fetch Report r " +
            "on m = r.accusedMember " +
            "and r.createDate > :validDate " +
            "and r.state = 'REPORT' " +
            "group by m.seq")
    List<ManageMemberInfoResponseDto> findManageMemberInfoResponseDtoList(@Param("validDate") LocalDateTime validDate);

    @Query("select new com.ssafy.uknowme.model.dto.MemberDto.ManageMemberInfoResponseDto(m.seq, m.id, m.name, m.nickname, m.gender, m.birth, m.tel, m.smoke, m.address, m.reportState, count(r.seq), m.createDate, m.updateDate) " +
            "from Member m " +
            "left join fetch Report r " +
            "on m = r.accusedMember " +
            "and r.createDate > :validDate " +
            "and r.state = 'REPORT' " +
            "where m.seq = :memberSeq " +
            "group by m.seq")
    Optional<ManageMemberInfoResponseDto> findManageMemberInfoResponseDto(@Param("validDate") LocalDateTime validDate, @Param("memberSeq") int memberSeq);

    //아이디 중복체크
    boolean existsById(String memberId);

    boolean existsByNickname(String memberNickname);

    boolean existsByTel(String memberTel);
}

