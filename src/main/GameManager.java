package main;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import network.*;

public class GameManager {
	private static Server server;
	private static Client client;
	
	private static final int TIMEOUT_CONN = 20000;
	
	private static String nickClient;
	private static String nickServer;
	
	private static String clientShape;
	private static String serverShape;
	
	public static String getClientShape() {
		return clientShape;
	}
	
	public static String getServerShape() {
		return serverShape;
	}
	
	public static String getNickClient() {
		return nickClient;
	}
	
	public static String getNickServer() {
		return nickServer;
	}
	
	public static Server getServer() {
		return server;
	}
	
	public static Client getClient() {
		return client;
	}
	
	public static String initServer(String ip, int port, String nickServer_) {
		try {
			server = new Server(ip, port);
			server.accept(TIMEOUT_CONN);
			
			nickServer = nickServer_;
			nickClient = server.read();
			server.send(nickServer);
			
			return "true";
		} 
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public static String initClient(String ip, int port, String nickClient_) {
		try {
			client = new Client(ip, port);
			client.connect(TIMEOUT_CONN);
			client.send(nickClient_);
			
			nickClient = nickClient_;
			nickServer = client.read();
			
			return "true";
		} 
		catch (IOException e) {
			return e.getMessage();
		}
	}
	
	public static void initShapes() {
		if((ThreadLocalRandom.current().nextInt(0, 1 + 1)) == 1) {
			serverShape = "X";
			clientShape = "O";
		}
		else {
			serverShape = "O";
			clientShape = "X";
		}
	}
}
