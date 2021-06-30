/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.repository;

import com.tabuyos.mockito.model.UserDetails;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author tabuyos
 */
@Repository
public class UserDetailsRepository {

  private final JdbcTemplate jdbcTemplate;

  public UserDetailsRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<UserDetails> getAllUserDetails() {
    List<UserDetails> list =
        jdbcTemplate.query(
            "select * from user_details", new BeanPropertyRowMapper<>(UserDetails.class));
    return list;
  }
}
