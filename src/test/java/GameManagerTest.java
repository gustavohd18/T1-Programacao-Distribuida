package test.java;

import org.junit.jupiter.api.Test;

import main.java.logic.GameManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameManagerTest {
  @Test
  void shouldReturnAIdToUser() throws InterruptedException {
    GameManager gameManager = new GameManager(5);
    int userId = gameManager.registerUser("userTest");
    assertEquals(userId, gameManager.getListOfUser().get(0).getUserId());
  } 

  @Test
  void shouldRemoveUserFromIp() throws InterruptedException {
    GameManager gameManager = new GameManager(5);
    int userId = gameManager.registerUser("userTest");
    gameManager.removeUserIp("userTest");
    assertEquals(0, gameManager.getListOfUser().size());
  } 

  @Test
  void shouldRemoveUserFromId() throws InterruptedException {
    GameManager gameManager = new GameManager(5);
    int userId = gameManager.registerUser("userTest");
    gameManager.removeUserId(userId);
    assertEquals(0, gameManager.getListOfUser().size());
  } 

  @Test
  void shouldNotAddUserToList() throws InterruptedException {
    GameManager gameManager = new GameManager(1);
    int userId = gameManager.registerUser("User1");
    int userId2 = gameManager.registerUser("User2");
    assertEquals(userId, gameManager.getListOfUser().get(0).getUserId());
    assertEquals(1, gameManager.getListOfUser().size());
  }   
}
