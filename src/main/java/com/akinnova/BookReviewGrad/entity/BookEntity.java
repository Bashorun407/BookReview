package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.entity.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "book_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ISBN")
        }
)
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coverImage;
    private String title;
    private String author;
    private String genre;
    private String publicationYear;
    private String ISBN;
    private String serialNumber;
    private String summary;
    private ReviewStatus reviewStatus;
    private Boolean activeStatus;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime modifiedOn;
}
