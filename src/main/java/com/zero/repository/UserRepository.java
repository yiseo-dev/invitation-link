package com.zero.repository;

import com.zero.entity.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository{

    @PersistenceContext
    EntityManager em;
    public UserInfo findByUserId(Integer userId) {
        return em.find(UserInfo.class,userId);
    }
    public void update(UserInfo userInfo){
        em.merge(userInfo);
    }
    public UserInfo saveUserInfo(UserInfo userInfo) {
        em.persist(userInfo);
        return userInfo;
    }
}
