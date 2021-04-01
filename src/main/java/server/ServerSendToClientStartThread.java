package main.java.server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;

public class ServerSendToClientStartThread extends Thread {
	protected List<String> usersIps;
	protected String thread_name;

	public ServerSendToClientStartThread (List<String> args, String name) {
		usersIps = args;
		thread_name = name;
	}

	public void run() {
		for (String userIp : usersIps) {
		String connectLocation = "rmi://" + userIp + ":52369/Game2";

		JogadorInterface player = null;
		try {
			System.out.println("Calling client back at : " + connectLocation);
			player = (JogadorInterface) Naming.lookup(connectLocation);
		} catch (Exception e) {
			System.out.println ("Callback failed: ");
			e.printStackTrace();
		}

		try {
			player.inicia();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	}
}
