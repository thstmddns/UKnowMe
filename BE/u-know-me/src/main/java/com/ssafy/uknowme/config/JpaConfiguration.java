package com.ssafy.uknowme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class JpaConfiguration {

    /**
     * web.domain.common.BaseEntity의 @CreatedBy, @LastModifiedBy 애노테이션이 적용된 필드에 자동으로 들어가는 값.
     * 현재는 UUID를 넣고 추후에 spring security와 연동해 authentification 정보를 적용한다.
     *
     * @return 랜덤 UUID
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }

}
