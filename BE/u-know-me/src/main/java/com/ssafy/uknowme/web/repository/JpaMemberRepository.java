//package com.ssafy.uknowme.web.repository;
//
//import com.ssafy.uknowme.model.dto.MemberRequestDto;
//import com.ssafy.uknowme.web.domain.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//
//@Repository
//@RequiredArgsConstructor
//public class JpaMemberRepository implements MemberRepository {
//
//    private final EntityManager em;
//
//    @Override
//    public boolean login(MemberRequestDto memberDto) {
//        Member findMember = em.createQuery("select m from Member m where m.id = :id and m.password = :password", Member.class)
//                .setParameter("id", memberDto.getId())
//                .setParameter("password", memberDto.getPassword())
//                .getSingleResult();
//
//        return findMember != null;
//    }
//}
