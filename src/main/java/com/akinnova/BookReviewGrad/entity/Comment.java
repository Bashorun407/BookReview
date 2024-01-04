package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Comment extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private String username;

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Comment) obj).id);
    }

    @Override
    public int hashCode(){
        return 2020;
    }

}
