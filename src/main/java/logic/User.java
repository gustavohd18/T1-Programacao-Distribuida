package main.java.logic;

public class User {
  private String userIp;
  private Integer userId;

  public User(String uIp, Integer uId) {
    userIp = uIp;
    userId = uId;
  }

  public String getUserIP() {
    return userIp;
  }

  public Integer getUserId() {
    return userId;
  }
}
