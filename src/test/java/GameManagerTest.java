package test.java;

import org.junit.jupiter.api.Test;

import main.java.logic.GameManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameManagerTest {
  @Test
  void shouldReturnAIdToUser() throws InterruptedException {
    GameManager gameManager = new GameManager(5);
    int userId = gameManager.registerUser();
    assertEquals(userId, gameManager.getListOfUser().get(0));
  } 
  @Test
  void shouldNotAddUserToList() throws InterruptedException {
    GameManager gameManager = new GameManager(1);
    int userId = gameManager.registerUser();
    int userId2 = gameManager.registerUser();
    assertEquals(userId, gameManager.getListOfUser().get(0));
    assertEquals(1, gameManager.getListOfUser().size());
  } 

  @Test
  void shouldReturnAIdToUserIps() throws InterruptedException {
    GameManager gameManager = new GameManager(1);
    gameManager.addUserIp("user1");
    assertEquals("user1", gameManager.getListOfUserIp().get(0));
  }   
}
