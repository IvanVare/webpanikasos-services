package com.PanikaSos.PS_springB.persistence.dao;

import com.PanikaSos.PS_springB.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> findAll();

    Optional<User> findById(Integer id);

    boolean isValidate(String field,String value, Integer id);
}
