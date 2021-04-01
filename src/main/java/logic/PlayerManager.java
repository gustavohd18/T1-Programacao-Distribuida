package main.java.logic;

import java.util.concurrent.Semaphore;

public class PlayerManager {
  private Integer userId;
  static Semaphore semaphore = new Semaphore(1);
  
  public void setUserId(Integer id) throws InterruptedException {
    semaphore.acquire();
    userId = id;
    semaphore.release();
  }

  public Integer getUserId() {
   return userId;
  }
}
