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
	
    public static boolean checkTrees(ArrayList<ArrayList<Character>> tab, int r, int m, Character who) {
        if (checkRow(tab, r, who)) {
            return true;
        }
        
        if (checkColumn(tab, m, who)) {
            return true;
        }
        
        if (((r + m) % 2 != 0)) {
            return checkDiagonal(tab, who, r, m);
        }
        return false;
    }
    
    private static boolean checkMainDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
        int count = 0;
        
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(i);
            if (current == null || !current.equals(who)) {
                return false;
            }
            count++;
        }
        
        return count == 3;
    }
    
    private static boolean checkSecondaryDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
        int count = 0;
        
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(2 - i);
            if (current == null || !current.equals(who)) {
                return false;
            }
            count++;
        }
        
        return count == 3;
    }
    
    private static boolean checkRow(ArrayList<ArrayList<Character>> tab, int r, Character who) {
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(r).get(i);
            if (current == null || !current.equals(who)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkColumn(ArrayList<ArrayList<Character>> tab, int m, Character who) {
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(m);
            if (current == null || !current.equals(who)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkDiagonal(ArrayList<ArrayList<Character>> tab, Character who, int r, int m) {
        if (r != m) {
            return checkSecondaryDiagonal(tab, who);
        } else {
            return checkMainDiagonal(tab, who);
        }
    }
}
