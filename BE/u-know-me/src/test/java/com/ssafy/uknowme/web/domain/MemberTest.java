package com.ssafy.uknowme.web.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Transactional
@SpringBootTest
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void createMember() throws Exception {

        // given
        Member member = Member.builder()
                .id("mungmnb777")
                .password("asd12345")
                .name("이명범")
                .nickname("명범")
                .build();

        // when
        em.persist(member);

        // then
        log.info("createMember = {}", member.getCreateMember());
        log.info("createDate = {}", member.getCreateDate());
        log.info("updateMember = {}", member.getUpdateMember());
        log.info("updateDate = {}", member.getUpdateDate());
    }
}