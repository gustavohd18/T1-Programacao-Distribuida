package main.java.logic;

public class User {
  private String userIp;
  private Integer userId;

  public User(Integer uId) {
    userId = uId;
  }

  public void setUserIp(String ip) {
     userIp = ip;
  }

  public String getUserIP() {
    return userIp;
  }

  public Integer getUserId() {
    return userId;
  }
}
