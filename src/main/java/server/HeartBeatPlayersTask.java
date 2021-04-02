package main.java.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.TimerTask;

import interfaces.JogadorInterface;
import main.java.logic.GameManager;
import main.java.logic.User;

public class HeartBeatPlayersTask  extends TimerTask{
  private GameManager gameManager;
  public HeartBeatPlayersTask(GameManager gm) {
    gameManager = gm;
  }
  @Override
  public void run() {
    List<User> users = gameManager.getListOfUser();
		for (int i = 0; i< users.size(); i++) {
      User user = users.get(i);
      String userIp = user.getUserIP();
      String connectLocation =  userIp ;
  
      JogadorInterface player = null;
      try {
        System.out.println("Verify player is alive : " + userIp);
        player = (JogadorInterface) Naming.lookup(connectLocation);
      } catch (Exception e) {
         // caso tenha erro possivelmente temos que remover o usuario da lista pois ele nao esta no jogo mais
        System.out.println ("Player don't aswers, so will be remove 2: " + user.getUserIP());
        try {
          gameManager.removeUserIp(userIp);
          return;
        } catch(InterruptedException error) {
          error.printStackTrace();
        }
      }
  
      try {
        player.verifica();
      } catch (RemoteException e) {
        try {
          gameManager.removeUserIp(userIp);
        } catch(InterruptedException error) {
          error.printStackTrace();
        }
        // caso tenha erro possivelmente temos que remover o usuario da lista pois ele nao esta no jogo mais
        System.out.println ("Player don't aswers, so will be remove: " + user.getUserIP());
      }
    }
  }
}
