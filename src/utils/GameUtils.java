package utils;

import java.awt.Cursor;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JLabel;

import main.GameManager;


public class GameUtils {
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public final static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public final static int MAX_LEN_MESSAGE_BOX = 35;
	
	public final static byte NORMAL_MESSAGE = 0;
	public final static byte GAME_MESSAGE = 1;
	public final static byte EXIT_MESSAGE = 2;

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
}
