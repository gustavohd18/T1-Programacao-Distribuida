package main.java.logic;

import java.util.concurrent.Semaphore;

public class PlayerManager {
  private Integer userId;
  private boolean canPlay;
  private String hostServer;
  static Semaphore semaphore = new Semaphore(1);
  
  public void setUserId(Integer id) throws InterruptedException {
    semaphore.acquire();
    userId = id;
    semaphore.release();
  }

  public Integer getUserId() {
   return userId;
  }

  public void setHost(String host) throws InterruptedException {
    semaphore.acquire();
    hostServer = host;
    semaphore.release();
  }

  public String getHost() {
   return hostServer;
  }

  public void setCanPlay(boolean canPlay) throws InterruptedException {
    semaphore.acquire();
    this.canPlay = canPlay;
    semaphore.release();
  }

  public boolean getCanPlay() {
    return canPlay;
  }
}
