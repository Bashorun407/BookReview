package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "rating_table")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Integer starRating;
    private Long rateCount;
    private Double averageRating;
    private LocalDateTime rateTime;

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Rating) obj).id);
    }

    @Override
    public int hashCode(){
        return 2023;
    }
}
