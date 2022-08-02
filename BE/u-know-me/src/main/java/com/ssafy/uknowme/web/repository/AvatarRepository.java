package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    Avatar findBySeq(int seq);
}
