package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Project extends BaseInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String title;
    private Category category;
    private ProjectFormat projectFormat;
//    @OneToOne(mappedBy = "project")
//    private ProjectReport projectReport;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Project) obj).id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
