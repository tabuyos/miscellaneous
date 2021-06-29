/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.controller;

import com.tabuyos.mockito.service.UserService;
import org.springframework.stereotype.Controller;

/**
 * TODO
 *
 * @author tabuyos
 */
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public void getAllUser() {
    userService.getAllUser().forEach(System.out::println);
  }
}
