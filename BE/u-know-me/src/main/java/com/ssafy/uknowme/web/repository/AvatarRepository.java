package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.model.dto.AvatarDto.AvatarResponseDto;
import com.ssafy.uknowme.web.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    @Query("select new com.ssafy.uknowme.model.dto.AvatarDto.AvatarResponseDto(a.seq, a.name, a.frequency) from Avatar a")
    List<AvatarResponseDto> findAllDto();
}
