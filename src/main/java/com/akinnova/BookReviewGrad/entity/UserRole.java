package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * this class serves as a many-to-many or junction class between User and Role classes
 */
@Getter
@Setter
@Entity
public class UserRole implements Serializable {

    private static final Long serialVersionUID = 1L;

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId(user.getId(), role.getId());
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final UserRole other = (UserRole) obj;

        if(!Objects.equals(this.user, other.user)){
            return false;
        }

        return Objects.equals(this.role, other.role);
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.user);
        hash = 31 * hash + Objects.hashCode(this.role);

        return hash;
    }
}
