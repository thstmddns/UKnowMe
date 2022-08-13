package com.ssafy.uknowme.web.domain.factory;

import com.ssafy.uknowme.web.domain.*;
import com.ssafy.uknowme.web.domain.enums.Role;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MockEntityFactory {

    public static Member createMember() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return Member.builder()
                .id("mungmnb777")
                .avatar(createAvatar())
                .password(encoder.encode("password"))
                .name("이명범")
                .nickname("명범짱")
                .gender("M")
                .birth("19960513")
                .tel("01012345678")
                .smoke("Y")
                .address("우리집")
                .role(Role.ROLE_MANAGER)
                .build();
    }

    public static Avatar createAvatar() {
        return Avatar.builder()
                .seq(4)
                .name("유노")
                .frequency(0)
                .build();
    }

    public static Balance createBalance() {
        return Balance.builder()
                .question("중국집")
                .answer1("짬뽕")
                .answer2("짜장면")
                .build();
    }

    public static Keyword createKeyword() {
        return Keyword.builder()
                .keyword("취미")
                .build();
    }

    public static Notice createNotice() {
        return Notice.builder()
                .member(createMember())
                .title("공지사항")
                .content("내용")
                .hit(0)
                .build();
    }
}
