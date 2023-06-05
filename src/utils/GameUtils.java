package utils;

import java.awt.Cursor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

import main.GameManager;
import main.Point;

public class GameUtils {
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public final static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public final static int MAX_LEN_MESSAGE_BOX = 35;
	
	public final static byte NORMAL_MESSAGE = 50;
	public final static byte GAME_MESSAGE = 60;
	public final static byte EXIT_MESSAGE = 70;
	
	public final static byte GAME_DRAW = 80;
	public final static byte GAME_VICTORY = 90;
	
	public final static byte REMATCH = 91;
	public final static byte CONFIRM_REMATCH = 92;
	public final static byte REMATCH_REFEUSED = 93;
	
	public final static byte TEST = 94;
	
	public enum GameStatus {
		DRAW,
		WON,
		LOST
	}
	
	public static void setTurnColors(JLabel l1, JLabel l2, boolean myTurn) {
		if(myTurn) {
			l1.setForeground(Colors.green);
			l2.setForeground(Colors.whiteSmoke);
		}
		else {
			l1.setForeground(Colors.whiteSmoke);
			l2.setForeground(Colors.green);
		}
	}
	
	public static void setTurn() {
		if(GameManager.isFirstTime()) {
			System.out.print("\nprima volta!");
			GameManager.randomAssignTurn();
		}
		else if(GameManager.isClientTurn()) {
			System.out.print("\ntoccava a client ora tocca a server");
			GameManager.setServerTurn();
		}
		else {
			System.out.print("\ntoccava a server ora tocca a client");
			GameManager.setClientTurn();
		}
	}
	
	public static boolean sendCoordinates(DataOutputStream data, int row, int col) {
		try {
			data.writeByte(GAME_MESSAGE);
			data.writeByte(row);
			data.writeByte(col);
			
			return true;
		} 
		catch (IOException e) {
			return false;
		}

	}
	
	public static ArrayList<ArrayList<String>> createMovesMatrix() {
		ArrayList<ArrayList<String>> moves_ = new ArrayList<ArrayList<String>>(3);
		
        for (int i = 0; i < 3; i++) {
            ArrayList<String> row = new ArrayList<>(3);
            for (int j = 0; j < 3; j++) {
                row.add(null);
            }
            moves_.add(row);
        }
        
        return moves_;
	}
	
    public static ArrayList<Point> checkTrees(ArrayList<ArrayList<Character>> tab, int r, int m, Character who) {
    	ArrayList<Point> coords = checkRow(tab, r, who);
    	if (coords != null) {
            return coords;
        }
    	coords = checkColumn(tab, m, who);
        if (coords != null) {
            return coords;
        }
        
        if (((r + m) % 2 == 0)) {
            return checkDiagonal(tab, who, r, m);
        }
        
        return new ArrayList<Point>();
    }
    
    private static ArrayList<Point> checkRow(ArrayList<ArrayList<Character>> tab, int r, Character who) {
    	ArrayList<Point> coords = new ArrayList<>();               
    	for (int i = 0; i < 3; i++) {
            Character current = tab.get(r).get(i);
            if (current == null || !current.equals(who)) {
            	return null;
            }
            coords.add(new Point(r,i));
        }
        return coords;
    }
    
    private static  ArrayList<Point> checkColumn(ArrayList<ArrayList<Character>> tab, int m, Character who) {
    	ArrayList<Point> coords = new ArrayList<>();
    	for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(m);
            if (current == null || !current.equals(who)) {
                return null;
            }
            coords.add(new Point(i,m));
        }
        return coords;
    }
    
    private static ArrayList<Point> checkDiagonal(ArrayList<ArrayList<Character>> tab, Character who, int r, int m) {
    	ArrayList<Point> coords = null;
    	if(r == 1 && m == 1) {
    		coords = checkSecondaryDiagonal(tab, who);
    		if(coords != null) return coords; 
    		coords =  checkMainDiagonal(tab, who);
    		if(coords != null) return coords; 
    		
    		return new ArrayList<Point>();
        }
    	if (r != m) {
    		return checkSecondaryDiagonal(tab, who);
    	}
    	else {
    		return checkMainDiagonal(tab, who);
    	}
    }
    
    private static ArrayList<Point> checkMainDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
    	ArrayList<Point> coords = new ArrayList<>();
    	for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(i);
            if (current == null || !current.equals(who)) {
                return new ArrayList<Point>();
            }
            coords.add(new Point(i,i));
        }
    	
        return coords;
    }
    
    private static ArrayList<Point> checkSecondaryDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
        ArrayList<Point> coords = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(2 - i);
            if (current == null || !current.equals(who)) {
                return new ArrayList<Point>();
            }
            coords.add(new Point(i, 2-i));
        }
        
        return coords;
    }
}
