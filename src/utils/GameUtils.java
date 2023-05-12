package utils;

import java.awt.Color;

import javax.swing.JLabel;


public class GameUtils {
	public static void setTurnsColors(JLabel l1, JLabel l2, Color youC, Color clientC) {
		l1.setForeground(youC);
		l2.setBackground(clientC);
	}
}
