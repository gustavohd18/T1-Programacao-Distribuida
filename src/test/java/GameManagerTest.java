package test.java;

import org.junit.jupiter.api.Test;

import main.java.logic.GameManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameManagerTest {
  @Test
  void shouldReturnAIdToUser(){
    GameManager gameManager = new GameManager();
    int userId = gameManager.registerUser();
    assertEquals(userId, gameManager.getListOfUser().get(0));
  }  
}
