package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.ExpertiseLevel;
import com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationReviewStatus;
import com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationStatus;
import com.akinnova.BookReviewGrad.enums.ServiceProviderSpecialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ServiceProviderDetail extends BaseInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String cvLink;
    private String idLink;
    private Double chargePerHour;
    @Enumerated(EnumType.STRING)
    private ServiceProviderSpecialization specialization;
    @Enumerated(EnumType.STRING)
    private ExpertiseLevel expertiseLevel;
    @Enumerated(EnumType.STRING)
    private ServiceProviderApplicationStatus providerApplicationStatus;
    @Enumerated(EnumType.STRING)
    private ServiceProviderApplicationReviewStatus provideReviewStatus;

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((ServiceProviderDetail) obj).id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
