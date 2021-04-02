package main.java.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import main.java.client.ClientRegisterToServerThread;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;
import main.java.logic.PlayerManager;

import java.util.Random;

public class Client extends UnicastRemoteObject implements JogadorInterface {

	private static PlayerManager playerManager;
	private static int defaultPort;

  public Client() throws RemoteException {
		defaultPort = 52369;
	}

  public static void main(String[] args) {
	if (args.length < 2) 
	{
		System.out.println("Usage: java Client <server ip> <client ip>");
		System.exit(1);
	}
    try 
	{
		System.setProperty("java.rmi.server.hostname", args[1]);
		//verifica se passou a porta por parametro
		if(args.length == 3) {
			defaultPort = Integer.parseInt(args[2]);
		}
		LocateRegistry.createRegistry(defaultPort);
		System.out.println("java RMI registry created.");
	} 
	catch (RemoteException e)
	{
		System.out.println("java RMI registry already exists.");
	}

  try {
		String client = "rmi://" + args[1] + ":"+defaultPort+"/Game2";
		Naming.rebind(client, new Client());
		playerManager = new PlayerManager();
		playerManager.setOwnIp(client);
		System.out.println("Server is ready.");
	} 
	catch (Exception e) {
		System.out.println("Server Serverfailed: " + e);
	}

	ClientRegisterToServerThread clientRegisterToServerThread =	new ClientRegisterToServerThread(args, playerManager, "Register user");
	clientRegisterToServerThread.start();

	while(true) 
	{
		if(playerManager.getCanPlay())
			break;

			try{
				Thread.sleep(100);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	Random rnd = new Random();

	while(true) 
	{
		int between = rnd.nextInt(700) + 250;
		try{
			Thread.sleep(between);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(true)  // Não desistiu (implementar)
		{
			sendToServerToPlay(playerManager.getUserId());
			// Se jogou M vezes...
		}			
	}
}

  public int sumTest(int n1, int n2) {
    return n1 + n2;
  }

  @Override
  public void inicia() throws RemoteException {
		try {
			playerManager.setCanPlay(true);	
		} catch(Exception e) {
			e.printStackTrace();
		}
    System.out.println("Game Started");
    
  }

  @Override
  public void bonifica() throws RemoteException {
    	System.out.println("Gift from server to player: "+playerManager.getUserId());
  }

  @Override
  public void verifica() throws RemoteException {
    System.out.println("I'm a live");
  }

	private static void sendToServerToPlay(int id) {
		String connectLocation = "rmi://" + playerManager.getHost() + ":52369/Game";

		JogoInterface game = null;
		try {
			//System.out.println("Connecting to server at : " + connectLocation);
			game = (JogoInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Client failed: ");
			e.printStackTrace();
		}
	
		try {
			int resultFromServer = game.joga(id);
			System.out.println("You played");
		  } catch (RemoteException e) {
		}
	}
}
