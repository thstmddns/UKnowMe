package com.ssafy.uknowme.service;

import com.ssafy.uknowme.repository.MemberRepository;
import com.ssafy.uknowme.web.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    // MemberService & MemoryMemberRepository 다른 인스턴스 쓰고 있음
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;


    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //회원가입
    public String join(Member member)  {
        validateDuplicateMember(member);   //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 회원 검색
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }
}
