package com.PanikaSos.PS_springB.persistence.dao.impl;

import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.persistence.dao.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.em.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(this.em.find(User.class, id));
    }

    @Override
    public boolean isValidate(String field,String value,Integer id) {
        try{
            Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u." + field + " = :value AND u.id != :id", Long.class)
                    .setParameter("value", value)
                    .setParameter("id", id)
                    .getSingleResult();
            return count>0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
