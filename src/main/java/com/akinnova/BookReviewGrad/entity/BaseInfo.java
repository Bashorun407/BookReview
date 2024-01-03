package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseInfo {
    @CreationTimestamp
    protected LocalDateTime createdOn;
    @UpdateTimestamp
    protected LocalDateTime modifiedOn;

    protected Long createdBy;

    public BaseInfo(Long createdBy) {
        this.createdBy = createdBy;
    }
}
