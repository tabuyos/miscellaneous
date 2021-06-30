package com.tabuyos.mockito.controller;

import com.tabuyos.mockito.model.User;
import com.tabuyos.mockito.repository.UserRepository;
import com.tabuyos.mockito.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

/**
 * TODO
 *
 * @author bjliu(a.k.a tabuyos)
 * @since 2021/6/29
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @BeforeEach
  void setUp() {
    // 和 @ExtendWith(MockitoExtension.class) 二选一
    //    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void getAllUser() {
    User model = new User();
    model.setAge(25);
    model.setUsername("tabuyos");
    Mockito.when(userRepository.getAllUser()).thenReturn(Collections.singletonList(model));
    userService.getAllUser().forEach(System.out::println);
  }
}
