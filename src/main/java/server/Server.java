package main.java.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;
import main.java.logic.GameManager;

public class Server extends UnicastRemoteObject implements JogoInterface {
	private static volatile int result;
	private static volatile boolean isFullPlayer;
	private static volatile GameManager gamerManager;

	public Server() throws RemoteException {
		isFullPlayer = false;
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Server <server ip> <number players>");
			System.exit(1);
		}

		try {
			gamerManager = new GameManager(Integer.parseInt(args[1]));
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(52369);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}
		
		try {
			String server = "rmi://" + args[0] + ":52369/Game";
			Naming.rebind(server, new Server());
			System.out.println("Server is ready.");
		} catch (Exception e) {
			System.out.println("Server Serverfailed: " + e);
		}
		// HeartBeat 
		new Timer().scheduleAtFixedRate(new HeartBeatPlayersTask(gamerManager), 0, 5000);

		while (true) {
			//verifica se todos os usuarios ja entraram no game para lançar o player
			if(!isFullPlayer) {
				isFullPlayer = gamerManager.isFullPlayers();
			} else {
				ServerSendToClientStartThread serverSendToClientStartThread = new ServerSendToClientStartThread(gamerManager.getListOfUser(), "Send message to start game for players");
				serverSendToClientStartThread.start();
				System.out.println("Game will be start");
				break;
			}
		}
	}

	@Override
	public int registra() throws RemoteException {
		try {
			result = gamerManager.registerUser();
			System.out.println("Player added: " + result);
			
		} catch (Exception e) {
			System.out.println ("Failed to register user");
		}

		return result;
	}

	@Override
	public int registraIp(String ip, int id) throws RemoteException {
		try {
			gamerManager.registerUserIp(id, ip);
			System.out.println("Registed ip Client: " + result);
			
		} catch (Exception e) {
			System.out.println ("Failed to register ip to user");
		}

		return 0;
	}


	@Override
	public int joga(int id) throws RemoteException {
		System.out.println("Player id played: "+ id);

		boolean isBonus = gamerManager.isGiftBonus();
		String userIp = gamerManager.getUserIp(id);

		if(isBonus) {
			if(!userIp.isEmpty()) {
				sendBonusToPlayer(userIp);
			}
		}
		return 0;
	}

	@Override
	public int desiste(int id) throws RemoteException {
		System.out.println("Player id gave up the move: "+ id);
		return 0;
	}

	@Override
	public int finaliza(int id) throws RemoteException {
		try {
			gamerManager.removeUserId(id);
			System.out.println("Removed user with id: "+ id);
		} catch (Exception e) {
			System.out.println ("Failed to removed user");
		}
		return 0;
	}

	private void sendBonusToPlayer(String userIp) {
		String connectLocation =  userIp;
  
		JogadorInterface player = null;

		try {
			player = (JogadorInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Player don't connected ");
		}

		try {
			player.bonifica();
		} catch (RemoteException e) {
			System.out.println ("Player don't connected ");
		}
	}
}
