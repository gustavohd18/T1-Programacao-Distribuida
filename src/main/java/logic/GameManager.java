package main.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import main.java.logic.User;

// necessita ser um singleton para ter uma unica instancia em toda a parte do server multiplas theads
public class GameManager {
  private List<User> users;
  private Random randomGenerator;
  private int actualNumberOfPlayers;
  private int totNumberOfPlayer;
  private int valueToGenerateBonus; 
  //por enquanto utilizar semaforos para garantir na escrita da lista somente sera realizado 1 por vez
  static Semaphore semaphore = new Semaphore(1);

  public GameManager(int players) {
    users = new ArrayList<User>();
    randomGenerator = new Random();
    totNumberOfPlayer = players;
    actualNumberOfPlayers = 0;
    valueToGenerateBonus = 5;
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
    return actualNumberOfPlayers >= totNumberOfPlayer;
  }

  public boolean isGiftBonus() {
    // realizar calcula correto para achar a probabilidade de 3% de um valor
    int numberSelected = randomGenerator.nextInt(100);
    return numberSelected == valueToGenerateBonus;
  }

  public void removeUserIp(String userIp) throws InterruptedException {
    semaphore.acquire();
    removeUserFromIp(userIp);
    semaphore.release();
  }

  public void removeUserId(int userid) throws InterruptedException {
    semaphore.acquire();
    removeUserFromId(userid);
    semaphore.release();
  }

	public int registerUser(String userIp) throws InterruptedException {
    semaphore.acquire();
    int userId = randomGenerator.nextInt();

    if(!isFullPlayers()) {
      while(isUserRegisted(userId)) {
        userId = randomGenerator.nextInt();
      }

      User user = new User(userIp, userId);
      users.add(user);
      actualNumberOfPlayers++;
      semaphore.release();

      return userId;
    } else {
      semaphore.release();
      return -1;
    }
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

  private void removeUserFromId(int userId) {
    for(int i =0; i< users.size(); i++) {
      if(users.get(i).getUserId() == userId) {
        users.remove(i);
      }
    }
  }
}
