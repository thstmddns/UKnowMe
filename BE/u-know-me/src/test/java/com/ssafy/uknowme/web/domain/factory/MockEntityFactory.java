package com.ssafy.uknowme.web.domain.factory;

import com.ssafy.uknowme.web.domain.Avatar;
import com.ssafy.uknowme.web.domain.Member;
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
                .role(Role.ROLE_USER)
                .build();
    }

    public static Avatar createAvatar() {
        return Avatar.builder()
                .seq(4)
                .name("유노")
                .frequency(0)
                .build();
    }
}
