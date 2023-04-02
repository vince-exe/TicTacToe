package utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AlertClass {
	public static void showMsgBox(JFrame frame, String title, String body) {
		JOptionPane.showMessageDialog(frame, body, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showWarningBox(JFrame frame, String title, String body) {
		JOptionPane.showMessageDialog(frame, body, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showErrBox(JFrame frame, String title, String body) {
		JOptionPane.showMessageDialog(frame, body, title, JOptionPane.ERROR_MESSAGE);
	}
}
