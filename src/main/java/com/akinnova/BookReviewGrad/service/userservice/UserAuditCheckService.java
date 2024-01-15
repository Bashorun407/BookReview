package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.enums.UserType;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAuditCheckService implements IUserAuditCheckService{

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public UserAuditCheckService(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void queryEntityHistory() {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery queryAtRev = auditReader.createQuery().forEntitiesAtRevision(UserEntity.class, 3);
        System.out.println("Get all AuthorBook entities modified at revision #3");
        System.out.println(queryAtRev.getResultList());

        AuditQuery queryOfRevs = auditReader.createQuery().forRevisionsOfEntity(UserEntity.class, true, true);
        System.out.println("Get all AuthorBook instances in all their states that were audited");
        System.out.println(queryOfRevs.getResultList());
    }

    @Override
    @Transactional
    public void addUsers() {
        for(int i = 0; i < 20; i++){
            UserEntity user = new UserEntity();
            user.setFirstName("firstName"+i);
            user.setLastName("lastName"+i);
            user.setUsername("username"+i);
            user.setEmail("email@gmail.com"+i);
            user.setPassword("password"+i);
            user.setUserType(UserType.CLIENT);
            //userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void updateUsers() {
        List<UserEntity> allUsers = userRepository.findAll();
        for(UserEntity user : allUsers){
            user.setFirstName(user.getFirstName()+1);
            user.setLastName(user.getLastName()+2);
            user.setEmail(user.getEmail() + 2024);

        }
    }
}
