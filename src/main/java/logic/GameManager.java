package main.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// necessita ser um singleton para ter uma unica instancia em toda a parte do server multiplas theads
public class GameManager {
  private List<Integer> users;
  private List<String> usersIp;
  private Random randomGenerator;
  private int actualNumberOfPlayers;
  private int totNumberOfPlayer;
  //por enquanto utilizar semaforos para garantir na escrita da lista somente sera realizado 1 por vez
  static Semaphore semaphore = new Semaphore(1);

  public GameManager(int players) {
    users = new ArrayList<Integer>();
    usersIp = new ArrayList<String>();
    randomGenerator = new Random();
    totNumberOfPlayer = players;
    actualNumberOfPlayers = 0;
  }

  public List<Integer> getListOfUser() {
		return users;
	}

  public boolean isFullPlayers() {
    return actualNumberOfPlayers >= totNumberOfPlayer;
  }

  public void addUserIp(String id) throws InterruptedException {
    semaphore.acquire();
    usersIp.add(id);
    semaphore.release();
  }

  public void removeUserIp(String id) throws InterruptedException {
    semaphore.acquire();
    usersIp.remove(id);
    semaphore.release();
  }

  public List<String> getListOfUserIp() {
		return usersIp;
	}

	public int registerUser() throws InterruptedException {
    semaphore.acquire();
    int userId = randomGenerator.nextInt();

    if(!isFullPlayers()) {
      while(isUserRegisted(userId)) {
        userId = randomGenerator.nextInt();
      }

      users.add(userId);
      actualNumberOfPlayers++;
      semaphore.release();

      return userId;
    } else {
      semaphore.release();
      return -1;
    }
	}

  private boolean isUserRegisted(int id) {
    return users.contains(id);
  }
}
