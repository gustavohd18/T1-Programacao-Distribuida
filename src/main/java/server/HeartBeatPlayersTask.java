package main.java.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.TimerTask;

import interfaces.JogadorInterface;
import main.java.logic.GameManager;

public class HeartBeatPlayersTask  extends TimerTask{
  private GameManager gameManager;
  public HeartBeatPlayersTask(GameManager gm) {
    gameManager = gm;
  }
  @Override
  public void run() {
    List<String> userIps = gameManager.getListOfUserIp();
		for (int i = 0; i< userIps.size(); i++) {
      String userIp = userIps.get(i);
      String connectLocation = "rmi://" + userIp + ":52369/Game2";
  
      JogadorInterface player = null;
      try {
        System.out.println("Verify player is alive : " + connectLocation);
        player = (JogadorInterface) Naming.lookup(connectLocation);
      } catch (Exception e) {
         // caso tenha erro possivelmente temos que remover o usuario da lista pois ele nao esta no jogo mais
        System.out.println ("Player don't aswers, so will be remove:" + connectLocation);
        try {
          gameManager.removeUserIp(userIp);
        } catch(InterruptedException error) {
          error.printStackTrace();
        }
      }
  
      try {
        player.verifica();
      } catch (RemoteException e) {
        // caso tenha erro possivelmente temos que remover o usuario da lista pois ele nao esta no jogo mais
        System.out.println ("Player don't aswers, so will be remove:" + connectLocation);
        try {
          gameManager.removeUserIp(userIp);
        } catch(InterruptedException error) {
          error.printStackTrace();
        }

      }
    }
  }
}
