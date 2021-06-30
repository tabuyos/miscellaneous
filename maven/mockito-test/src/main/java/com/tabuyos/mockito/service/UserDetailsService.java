/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.service;

import com.tabuyos.mockito.model.UserDetails;
import com.tabuyos.mockito.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author tabuyos
 */
@Service
public class UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;

  public UserDetailsService(UserDetailsRepository userDetailsRepository) {
    this.userDetailsRepository = userDetailsRepository;
  }

  public List<UserDetails> getAllUserDetails() {
    return userDetailsRepository.getAllUserDetails();
  }
}
