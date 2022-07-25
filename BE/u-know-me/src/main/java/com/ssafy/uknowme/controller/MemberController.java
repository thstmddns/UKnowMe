package com.ssafy.uknowme.controller;

import com.ssafy.uknowme.service.MemberService;
import com.ssafy.uknowme.web.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // private final MemberService memberService = new memberService();
    // 여러개 생성할 필요 없이 스프링 컨테이너 하나만 생성하면 됨
    private final MemberService memberService;

    // 생성자로 연결
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //회원가입
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = Member.builder()
                .name(form.getName())
                .build();

        memberService.join(member);

        return "redirect:/";
    }

    // 회원조회
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
