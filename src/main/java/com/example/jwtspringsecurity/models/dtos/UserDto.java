package com.example.jwtspringsecurity.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

  @JsonProperty("username")
  private String userName;
  private String password;
  @JsonProperty("confirmpassword")
  private String confirmPassword;

  public UserDto() {
  }

  public UserDto(String userName, String password, String confirmPassword) {
    this.userName = userName;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
