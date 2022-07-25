package com.ssafy.uknowme.service;

import com.ssafy.uknowme.repository.MemberRepository;
import com.ssafy.uknowme.web.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private final MemberRepository memberRepository;

    public boolean login(Member member) {
        Optional<Member> optional = memberRepository.findById(member.getId());

        if (optional.isPresent()) {
            return true;
        } else {
            return false;
        }

        if (!findMember.getPassword().equals(member.getPassword())){
            return false;
        }
        return true;
    }
};
