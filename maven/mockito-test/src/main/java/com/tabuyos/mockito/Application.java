/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito;

import com.tabuyos.mockito.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TODO
 *
 * @author tabuyos
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    UserController bean = context.getBean(UserController.class);
    bean.getAllUser();
  }
}
