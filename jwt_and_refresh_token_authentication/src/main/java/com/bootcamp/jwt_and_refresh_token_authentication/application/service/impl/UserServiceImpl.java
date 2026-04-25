package com.bootcamp.jwt_and_refresh_token_authentication.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository _userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this._userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return _userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return _userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public User save(User user) {
        return _userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        // user nesnesinin ID'si dolu olmalı
        if (user.getId() == null || !_userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found or id is null");
        }
        return _userRepository.save(user);
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        if (!_userRepository.existsById(id)) {
            return false;
        }
        _userRepository.deleteById(id);
        return true;
    }

}
