package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "userEntity",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phoneNumber"),
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class UserEntity extends BaseInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private boolean isProfileComplete;
    private Boolean activeStatus;
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Project> projects = new ArrayList<>();

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserRole> roles = new ArrayList<>();

//    Many-to-many relationship with role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userEntity", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleName")
    )
    private Set<Role> roles = new LinkedHashSet<>();


//    @ManyToMany(mappedBy = "users")
//    private Set<ProjectReport> projectReports = new LinkedHashSet<>();

//    public void addProject(Project project){
//        this.projects.add(project);
//        project.setUserEntity(this);
//    }
//
//    public void removeProject(Project project){
//        project.setUserEntity(null);
//        this.projects.remove(project);
//    }
//
//    public void removeProjects(){
//        Iterator<Project> iterator = this.projects.iterator();
//
//        while(iterator.hasNext()){
//            Project project = iterator.next();
//            project.setUserEntity(null);
//            iterator.remove();
//        }
//    }
    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((UserEntity) obj).id);
    }

    @Override
    public int hashCode(){
        return 2026;
    }
}
