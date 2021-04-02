package main.java.client;
import java.rmi.Naming;
import java.rmi.RemoteException;

import main.java.interfaces.JogoInterface;
import main.java.logic.PlayerManager;

public class ClientRegisterToServerThread extends Thread {
	protected String[] thread_args;
	protected String thread_name;
	protected PlayerManager playerManager;

	public ClientRegisterToServerThread (String[] args, PlayerManager player, String name) {
		thread_args = args;
		thread_name = name;
		playerManager = player;		
	}

	public void run() {
			
		String remoteHostName = thread_args[0];
		String connectLocation = "rmi://" + remoteHostName + ":52369/Game";

		JogoInterface game = null;
		try {
			playerManager.setHost(remoteHostName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Connecting to server at : " + connectLocation);
			game = (JogoInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Client failed: ");
			e.printStackTrace();
		}
	
		try {
			int resultFromServer = game.registra(playerManager.getOwnIp());
      if(resultFromServer == -1) {
        System.out.println("Sorry we can't register you!");
      } else {

				try {
					playerManager.setUserId(resultFromServer);
					System.out.println("register with successful your id: " + resultFromServer);
				} catch (Exception e) {
					System.out.println("Error to set user");
				}
			}			
	
		  } catch (RemoteException e) {
		}
	}
}
