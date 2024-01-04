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
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phoneNumber"),
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class User extends BaseInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

//    Many-to-many relationship with role
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_role_relationship",
//            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
//            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleName")
//    )
//    private Set<RoleName> roleName;


//    @ManyToMany(mappedBy = "users")
//    private Set<ProjectReport> projectReports = new LinkedHashSet<>();

//    public void addProject(Project project){
//        this.projects.add(project);
//        project.setUser(this);
//    }
//
//    public void removeProject(Project project){
//        project.setUser(null);
//        this.projects.remove(project);
//    }
//
//    public void removeProjects(){
//        Iterator<Project> iterator = this.projects.iterator();
//
//        while(iterator.hasNext()){
//            Project project = iterator.next();
//            project.setUser(null);
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

        return id != null && id.equals(((User) obj).id);
    }

    @Override
    public int hashCode(){
        return 2026;
    }
}
