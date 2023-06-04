package main;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private static boolean firstTime;
	private static String playerTurn;
	
	private static ArrayList<ArrayList<Character>> matrix;
	
	public static String getTurn() {
		return playerTurn;
	}
	
	public static boolean isClientTurn() {
		return playerTurn.equals("client");
	}
	
	public static boolean isServerTurn() {
		return playerTurn.equals("server");
	}
	
	public static void setClientTurn() {
		playerTurn = "client";
	}
	
	public static boolean isFirstTime() {
		return firstTime;
	}
	
	public static void setNoFirstTime() {
		firstTime = false;
	}
	
	public static void setServerTurn() {
		playerTurn = "server";
	}
	
	public static void setClientShape(String s) {
		clientShape = s;
	}
	
	public static void setServerShape(String s) {
		serverShape = s;
	}
	
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
		
	public static void initGameVariables() {
		firstTime = true;
		playerTurn = "";
	}
	
	public static void initMatrix() {
		matrix = new ArrayList<>(3);
		
        for (int i = 0; i < 3; i++) {
            ArrayList<Character> row = new ArrayList<>(3);
            for (int j = 0; j < 3; j++) {
                row.add(null);
            }
            matrix.add(row);
        }
	}
	
	public static void clearMatrix() {
		for(int i = 0; i < 3; i++) {
			matrix.get(i).clear();
		}
		matrix.clear();
	}
	
	public static ArrayList<ArrayList<Character>> getMatrix() {
		return matrix;
	}
	
	public static void addToMatrix(int row, int cols, Character c) {
		matrix.get(row).set(cols, c);
	}
	
	public static void randomAssignTurn() {
		playerTurn = (ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1) ? "server" : "client";
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
