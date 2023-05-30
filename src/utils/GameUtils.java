package utils;

import java.awt.Cursor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

import main.GameManager;


public class GameUtils {
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public final static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public final static int MAX_LEN_MESSAGE_BOX = 35;
	
	public final static byte NORMAL_MESSAGE = 0;
	public final static byte GAME_MESSAGE = 1;
	public final static byte EXIT_MESSAGE = 2;
	public final static byte GAME_VICTORY = 10;
	
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
			GameManager.randomAssignTurn();
		}
		else if(GameManager.isClientTurn()) {
			GameManager.setServerTurn();
		}
		else {
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
	
    public static boolean checkTrees(ArrayList<ArrayList<Character>> tab, int r, int m, Character who) {
        if (checkRow(tab, r, who)) {
            return true;
        }
        
        if (checkColumn(tab, m, who)) {
            return true;
        }
        
        if (((r + m) % 2 == 0)) {
            return checkDiagonal(tab, who, r, m);
        }
        return false;
    }
    
    public static void printTab(ArrayList<ArrayList<Character>> tab) {
        for(int i = 0; i <3 ; i++) {
        	for(int j = 0; j < 3; j++) {
        		System.out.print(tab.get(i).get(j) + " ");
        	}
        	System.out.print("\n");
        }
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
        if(r == 1 && m == 1) {
        	return (checkSecondaryDiagonal(tab, who) == true || checkMainDiagonal(tab, who) == true);
        }
    	if (r != m) {
    		return checkSecondaryDiagonal(tab, who);
    	}
    	else {
    		return checkMainDiagonal(tab, who);
    	}
    }
    
    private static boolean checkMainDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(i);
            if (current == null || !current.equals(who)) {
                return false;
            }
        }
        
        return true;
    }
    
    private static boolean checkSecondaryDiagonal(ArrayList<ArrayList<Character>> tab, Character who) {
        
        for (int i = 0; i < 3; i++) {
            Character current = tab.get(i).get(2 - i);
            if (current == null || !current.equals(who)) {
                return false;
            }
        }
        
        return true;
    }
}
