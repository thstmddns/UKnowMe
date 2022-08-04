package com.ssafy.uknowme.web.domain.common;

import com.ssafy.uknowme.web.domain.enums.DeleteState;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 생성 멤버. 현재는 UUID값이 들어간다. 추후에 시큐리티와 연동 예정 -> 리팩토링 필요!
     */
    @CreatedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, updatable = false, name = "create_member_seq")
    private String createMember;

    /**
     * 수정 멤버. 현재는 UUID값이 들어간다. 추후에 시큐리티와 연동 예정 -> 리팩토링 필요!
     */
    @LastModifiedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "update_member_seq")
    private String updateMember;

    @Enumerated(EnumType.STRING)
    private DeleteState deleteYn;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }

    public BaseEntity() {
        this.deleteYn = DeleteState.N;
    }
}
