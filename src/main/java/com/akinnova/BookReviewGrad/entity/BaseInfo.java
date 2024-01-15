package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@MappedSuperclass
@Getter
@Setter
public abstract class BaseInfo {
    @CreatedDate
    protected LocalDateTime createdOn;
    @CreatedBy
    protected String createdBy;
    @LastModifiedDate
    protected LocalDateTime modifiedOn;
    @LastModifiedBy
    protected String lastModifiedBy;
}
