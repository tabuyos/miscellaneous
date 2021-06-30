/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.service;

import com.tabuyos.mockito.model.User;
import com.tabuyos.mockito.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author tabuyos
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUser() {
    return userRepository.getAllUser();
  }
}
