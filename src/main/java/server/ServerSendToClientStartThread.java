package main.java.server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

import interfaces.JogadorInterface;
import main.java.interfaces.JogoInterface;
import main.java.logic.User;

public class ServerSendToClientStartThread extends Thread {
	protected List<User> users;
	protected String thread_name;

	public ServerSendToClientStartThread (List<User> args, String name) {
		users = args;
		thread_name = name;
	}

	public void run() {
		for (User user : users) {
		String connectLocation = user.getUserIP();

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
