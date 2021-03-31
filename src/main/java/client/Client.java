package main.java.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;

public class Client extends UnicastRemoteObject implements JogadorInterface {

  public Client() throws RemoteException {
	}

  public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: java Client <server ip> <client ip>");
			System.exit(1);
		}

    try {
			System.setProperty("java.rmi.server.hostname", args[1]);
			LocateRegistry.createRegistry(52369);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

    try {
			String client = "rmi://" + args[1] + ":52369/Game2";
			Naming.rebind(client, new Client());
			System.out.println("Server is ready.");
		} catch (Exception e) {
			System.out.println("Server Serverfailed: " + e);
		}

    String remoteHostName = args[0];
		String connectLocation = "rmi://" + remoteHostName + ":52369/Game";

		JogoInterface game = null;
		try {
			System.out.println("Connecting to server at : " + connectLocation);
			game = (JogoInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Client failed: ");
			e.printStackTrace();
		}

    while (true) {
			try {
				game.registra();
				System.out.println("Call to server..." );
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {}
		}
	}

  public int Result(int val) {
		System.out.println("Called back, result is: " + val);
		
		return val;
	}

  public int sumTest(int n1, int n2) {
    return n1 + n2;
  }

  @Override
  public void inicia() throws RemoteException {
    // TODO Auto-generated method stub
    System.out.println("test");
    
  }

  @Override
  public void bonifica() throws RemoteException {
    // TODO Auto-generated method stub
    System.out.println("test");
    
  }

  @Override
  public void verifica() throws RemoteException {
    // TODO Auto-generated method stub
    System.out.println("test");
    
  }
}
