package main.java.server;

public class Server {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Server <server ip>");
			System.exit(1);
		}
    System.out.println("Server"+ args[0]);
	}
}
