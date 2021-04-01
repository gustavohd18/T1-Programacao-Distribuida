package main.java.client;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

import main.java.interfaces.JogoInterface;

public class ClientRegisterToServerThread extends Thread {
	protected String[] thread_args;
	protected String thread_name;

	public ClientRegisterToServerThread (String[] args, String name) {
		thread_args = args;
		thread_name = name;
	}

	public void run() {
			
		String remoteHostName = thread_args[0];
		String connectLocation = "rmi://" + remoteHostName + ":52369/Game";

		JogoInterface game = null;
		try {
			System.out.println("Connecting to server at : " + connectLocation);
			game = (JogoInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Client failed: ");
			e.printStackTrace();
		}
	
		try {
			int resultFromServer = game.registra();
      if(resultFromServer == -1) {
        System.out.println("usuario nao registrado limite de jogadores atingidos");
      } else {
				System.out.println("register() successful, id: " + resultFromServer);
			}			
	
		  } catch (RemoteException e) {
		}
	}
}
