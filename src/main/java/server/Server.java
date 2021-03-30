package main.java.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import main.java.interfaces.JogoInterface;

public class Server extends UnicastRemoteObject implements JogoInterface {

	public Server() throws RemoteException {
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Server <server ip>");
			System.exit(1);
		}

		try {
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(52369);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}
    System.out.println("Server"+ args[0]);
	}

	@Override
	public int registra() throws RemoteException {
		return 0;
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
