/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.model;

/**
 * TODO
 *
 * @author tabuyos
 */
public class UserDetails {

  private int id;
  private String username;
  private String password;
  private int age;
  private String remark;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  @Override
  public String toString() {
    return "UserDetails{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", age=" + age +
      ", remark='" + remark + '\'' +
      '}';
  }
}
