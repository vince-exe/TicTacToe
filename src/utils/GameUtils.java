package utils;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;


public class GameUtils {
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public final static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public final static int MAX_LEN_MESSAGE_BOX = 35;
	
	public final static byte NORMAL_MESSAGE = 0;
	public final static byte GAME_MESSAGE = 1;
	public final static byte EXIT_MESSAGE = 2;
	public final static byte CONFIRM_EXIT = 3;
	
	public static void setTurnsColors(JLabel l1, JLabel l2, Color youC, Color clientC) {
		l1.setForeground(youC);
		l2.setForeground(clientC);
	}
}
