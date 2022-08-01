package com.ssafy.uknowme.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Setter
@Getter
@ToString
@Configuration
@EnableSwagger2
public class MatchingRequestDto {

    @ApiParam(value ="d")
    private String id;


}
