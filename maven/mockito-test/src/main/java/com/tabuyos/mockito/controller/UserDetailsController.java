/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.controller;

import com.tabuyos.mockito.service.UserDetailsService;
import com.tabuyos.mockito.service.UserService;
import org.springframework.stereotype.Controller;

/**
 * TODO
 *
 * @author tabuyos
 */
@Controller
public class UserDetailsController {

  private final UserService userService;
  private final UserDetailsService userDetailsService;

  public UserDetailsController(UserService userService, UserDetailsService userDetailsService) {
    this.userService = userService;
    this.userDetailsService = userDetailsService;
  }

  public void getAll() {
    userDetailsService.getAllUserDetails().forEach(System.out::println);
    userService.getAllUser().forEach(System.out::println);
  }
}
