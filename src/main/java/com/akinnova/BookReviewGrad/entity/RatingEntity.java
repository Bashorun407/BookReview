package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rating_table")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String ISBN;
    private Integer starRating;
    private Long rateCount;
    private Double averageRating;
    private LocalDateTime rateTime;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_rates_review",
            joinColumns = @JoinColumn(name = "rating", referencedColumnName = "averageRating"),
            inverseJoinColumns = @JoinColumn(name = "book", referencedColumnName = "title")
    )
    private List<BookEntity> books;
}
