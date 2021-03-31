package main.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// essa classe possivelmente pode ser que seja somente utilizada pelo servidor nao sendo necessario thead ou algo assim
public class GameManager {
  private List<Integer> users;
  private Random randomGenerator;
  private int actualNumberOfPlayers;
  private int totNumberOfPlayer;
  //por enquanto utilizar semaforos para garantir na escrita da lista somente sera realizado 1 por vez
  static Semaphore semaphore = new Semaphore(1);

  public GameManager(int players) {
    users = new ArrayList<Integer>();
    randomGenerator = new Random();
    totNumberOfPlayer = players;
    actualNumberOfPlayers = 0;
  }

  public List<Integer> getListOfUser() {
		return users;
	}

  public boolean isFullPlayers() {
    return actualNumberOfPlayers == totNumberOfPlayer;
  }

	public int registerUser() throws InterruptedException {
    semaphore.acquire();
    int userId = randomGenerator.nextInt();
    if(actualNumberOfPlayers < totNumberOfPlayer) {
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
