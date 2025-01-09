package com.PanikaSos.PS_springB.persistence.dao.impl;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.persistence.dao.ControlUserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ControlUserDaoImpl implements ControlUserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ControlUser> findAll() {
        return this.em.createQuery("SELECT u FROM ControlUser u").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ControlUser> findById(Long id) {
        return Optional.ofNullable(this.em.find(ControlUser.class, id));
    }
}
