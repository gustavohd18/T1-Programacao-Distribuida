package main.java.client;

public class Client {
  public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: java Client <server ip> <client ip>");
			System.exit(1);
		}

    System.out.println("Server ip"+ args[0]);
    System.out.println("client ip"+ args[1]);
	}
}
