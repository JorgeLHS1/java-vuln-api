package com.advocacia.api.services;

import com.advocacia.api.domain.user.User;
import com.advocacia.api.infra.security.Pass;
import com.advocacia.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Pass pass;

    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserRepository userRepository, Pass pass) {
        this.userRepository = userRepository;
        this.pass = pass;
    }

    @Override
    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean verifyPassword(String password, User user) {
        return pass.matches(password, user.getPassword());
    }

    public List<User> searchUsersByName(String name) {
        String query = "SELECT * FROM users WHERE name LIKE '%" + name + "%'";
        return entityManager.createNativeQuery(query, User.class).getResultList();
    }
}