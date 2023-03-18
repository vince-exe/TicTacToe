package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {
	private JFrame frmTictactoe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTictactoe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTictactoe = new JFrame();
		frmTictactoe.getContentPane().setBackground(new Color(15, 49, 66));
		frmTictactoe.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tic-Tac-Toe");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
		lblNewLabel.setForeground(new Color(235, 235, 235));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(60, 11, 274, 61);
		frmTictactoe.getContentPane().add(lblNewLabel);
		
		JButton hostGameBtn = new JButton("Host Game");
		hostGameBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hostGameBtn.setOpaque(true);
				hostGameBtn.setBackground(new Color(11, 38, 51));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hostGameBtn.setOpaque(true);
				hostGameBtn.setBackground(new Color(15, 49, 66));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		hostGameBtn.setForeground(new Color(235, 235, 235));
		hostGameBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		hostGameBtn.setFocusPainted(false);
		hostGameBtn.setContentAreaFilled(false);
		hostGameBtn.setBorder(new LineBorder(new Color(2, 21, 31), 6, true));
		hostGameBtn.setBackground(new Color(15, 49, 66));
		hostGameBtn.setBounds(76, 114, 249, 61);
		frmTictactoe.getContentPane().add(hostGameBtn);
		
		JButton joinGameBtn = new JButton("Join Game");
		joinGameBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				joinGameBtn.setOpaque(true);
				joinGameBtn.setBackground(new Color(11, 38, 51));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hostGameBtn.setOpaque(true);
				joinGameBtn.setBackground(new Color(15, 49, 66));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				NicknameDialog.main(null);
			}
		});
		joinGameBtn.setForeground(new Color(235, 235, 235));
		joinGameBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		joinGameBtn.setFocusPainted(false);
		joinGameBtn.setContentAreaFilled(false);
		joinGameBtn.setBorder(new LineBorder(new Color(2, 21, 31), 6, true));
		joinGameBtn.setBackground(new Color(15, 49, 66));
		joinGameBtn.setBounds(76, 212, 249, 61);
		frmTictactoe.getContentPane().add(joinGameBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Copyright © 2023 Vincenzo Caliendo. All rights reserved");
		lblNewLabel_1.setForeground(new Color(193, 193, 193));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(38, 312, 333, 18);
		frmTictactoe.getContentPane().add(lblNewLabel_1);
		frmTictactoe.setTitle("TicTacToe");
		frmTictactoe.setResizable(false);
		frmTictactoe.setAlwaysOnTop(true);
		frmTictactoe.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/ui/resources/icon.png")));
		frmTictactoe.setBounds(100, 100, 420, 380);
		frmTictactoe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
