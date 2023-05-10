package main;

import java.io.IOException;

import network.*;

public class GameManager {
	private static Server server;
	private static Client client;
	
	private static final int TIMEOUT_CONN = 20000;
	
	private static String nickClient;
	private static String nickServer;
	
	public static String getNickClient() {
		return nickClient;
	}
	
	public static String getNickServer() {
		return nickServer;
	}
	
	public static boolean initServer(String ip, int port, String nickServer_) {
		try {
			server = new Server(ip, port);
			server.accept(TIMEOUT_CONN);
			
			nickServer = nickServer_;
			nickClient = server.read();
			server.send(nickServer + "\n");
			
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean initClient(String ip, int port, String nickClient_) {
		try {
			client = new Client(ip, port);
			client.connect(TIMEOUT_CONN);
			client.send(nickClient_ + "\n");
			
			nickClient = nickClient_;
			nickServer = client.read();
			
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
