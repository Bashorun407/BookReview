package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is a composite id class for UserRole class
 */
@Embeddable
@Getter
//@Setter
public class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;

    public UserRoleId() {
    }

    public UserRoleId(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final UserRoleId other = (UserRoleId) obj;

        if(!Objects.equals(this.userId, other.userId)){
            return false;
        }

        return Objects.equals(this.roleId, other.roleId);
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = hash * 29 + Objects.hashCode(this.userId);
        hash = hash * 29 + Objects.hashCode(this.roleId);

        return hash;
    }
}
