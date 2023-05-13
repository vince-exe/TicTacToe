package utils;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;


public class GameUtils {
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public final static Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public static void setTurnsColors(JLabel l1, JLabel l2, Color youC, Color clientC) {
		l1.setForeground(youC);
		l2.setBackground(clientC);
	}
}
