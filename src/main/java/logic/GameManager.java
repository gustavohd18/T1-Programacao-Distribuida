package main.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

// essa classe possivelmente pode ser que seja somente utilizada pelo servidor nao sendo necessario thead ou algo assim
public class GameManager {
  private List<Integer> users;
  private Random randomGenerator;

  public GameManager() {
    users = new ArrayList<Integer>();
    randomGenerator = new Random();
  }

  public List<Integer> getListOfUser() {
		return users;
	}

  // funcao recursiva para lidar com o caso do usuario ja esta registrado chama novamente ate retornar um usuario que nao esta registrado
	public int registerUser() {
		int userId = randomGenerator.nextInt();
    if(!isUserRegisted(userId)) {
      users.add(userId);
      return userId;
    }else {
      return registerUser();
    }
	}

  private boolean isUserRegisted(int id) {
    return users.contains(id);
  }
}
