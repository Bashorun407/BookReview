package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Audited
@AuditTable("project_audit")
public class Project extends BaseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String imageUrl;
    private String title;
    private Category category;
    //private ProjectFormat projectFormat;

    @ManyToOne
    @JoinColumn(name = "userEntity", referencedColumnName = "id")
    private UserEntity userEntity;

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
        return 2021;
    }
}
