package com.advocacia.api.services;

import java.util.List;
import java.util.Optional;

import com.advocacia.api.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {

    UserDetails findByLogin(String login);
    boolean existsByLogin(String login);

    User save(User user);

    void deleteById(String id);

    Optional<User> findById(String id);

    List<User> findAll();

    boolean verifyPassword(String password, User user);

    List<User> searchUsersByName(String name);
}