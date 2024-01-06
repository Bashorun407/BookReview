package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role extends BaseInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> userEntities;

//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserRole> users = new ArrayList<>();

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Role) obj).id);
    }

    @Override
    public int hashCode(){
        return 2027;
    }
}
