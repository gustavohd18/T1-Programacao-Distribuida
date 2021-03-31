package main.java.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;
import main.java.logic.GameManager;

public class Server extends UnicastRemoteObject implements JogoInterface {
	private static volatile int result;
	private static volatile boolean changed;
	private static volatile String remoteHostName;
	private static GameManager gamerManager;
	public Server() throws RemoteException {
		
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
		while (true) {
			if (changed == true) {
				changed = false;

				String connectLocation = "rmi://" + remoteHostName + ":52369/Game2";

				JogadorInterface player = null;
				try {
					System.out.println("Calling client back at : " + connectLocation);
					player = (JogadorInterface) Naming.lookup(connectLocation);
				} catch (Exception e) {
					System.out.println ("Callback failed: ");
					e.printStackTrace();
				}

				try {
					player.Result(result);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {}
		}
	}

	@Override
	public int registra() throws RemoteException {
		try {
			result = gamerManager.registerUser();
			changed = true;
			try {
				remoteHostName = getClientHost();
			} catch (Exception e) {
				System.out.println ("Failed to get client IP");
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int joga(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int desiste(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int finaliza(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
}
