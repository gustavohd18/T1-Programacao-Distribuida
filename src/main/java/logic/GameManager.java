package main.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import main.java.logic.User;

public class GameManager {
  private List<User> users;
  private Random randomGenerator;
  private int actualNumberOfPlayers;
  private int totNumberOfPlayer;
  //Semaforo para evitar problemas de escrita
  static Semaphore semaphore = new Semaphore(1);

  public GameManager(int players) {
    users = new ArrayList<User>();
    randomGenerator = new Random();
    totNumberOfPlayer = players;
    actualNumberOfPlayers = 0;
  }

  public List<User> getListOfUser() {
		return users;
	}

  public String getUserIp(int id) {
    for(int i = 0; i < users.size(); i++) {
      if(users.get(i).getUserId() == id) {
        return users.get(i).getUserIP();
      }
    }

    return "";
  }

  public boolean isFullPlayers() {
    return (actualNumberOfPlayers >= totNumberOfPlayer) && verifyAllUserHasIp();
  }

  public boolean isGiftBonus() {
    float lucky = 1 - randomGenerator.nextFloat();
    return lucky >= 0.98;
  }

  public void removeUserIp(String userIp) throws InterruptedException {
    semaphore.acquire();
    removeUserFromIp(userIp);
    semaphore.release();
  }

  public void removeUserId(int userId) throws InterruptedException {
    semaphore.acquire();
    removeUserFromId(userId);
    semaphore.release();
  }

  public int registerUser() throws InterruptedException {
    semaphore.acquire();
    int userId = randomGenerator.nextInt();

    if(!isFullPlayers()) {
      while(isUserRegisted(userId)) {
        userId = randomGenerator.nextInt();
      }

      User user = new User(userId);
      users.add(user);
      actualNumberOfPlayers++;
      semaphore.release();

      return userId;
    } else {
      semaphore.release();
      return -1;
    }
	}

	public int registerUserIp(int userId, String userIp) throws InterruptedException {
    semaphore.acquire();

    for(int i =0; i< users.size(); i++) {
      if(users.get(i).getUserId() == userId) {
        users.get(i).setUserIp(userIp);
      }
    }

    semaphore.release();

    return -1;
	}

  private boolean isUserRegisted(int id) {
    for(User user: users) {
      if(user.getUserId() == id) {
        return true;
      }
    }
    return false;
  }

  private void removeUserFromIp(String userIp) {
    for(int i =0; i< users.size(); i++) {
      if(users.get(i).getUserIP() == userIp) {
        users.remove(i);
      }
    }
  }

  private boolean verifyAllUserHasIp() {
    for(int i =0; i< users.size(); i++) {
      if(users.get(i).getUserIP() == null) {
        return false;
      }
    }
    return true;
  }

   void removeUserFromId(int userId) {
    for(int i =0; i< users.size(); i++) {
      if(users.get(i).getUserId() == userId) {
        users.remove(i);
      }
    }
  }
}
