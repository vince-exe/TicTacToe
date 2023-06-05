package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JLabel;

import utils.AlertClass;
import utils.Colors;
import utils.GameUtils;
import main.GameManager;
import main.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class RevengeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private static GameUtils.GameStatus status;
	private static boolean isHosting;
	private static ArrayList<Point> coordinates;
	private static ArrayList<ArrayList<String>> trisPoints;
	
	private Thread listenThread;
	
	private JLabel row1, row2, row3, row4, enemyLabel;
	private JButton revengeBtn, exitBtn;
	
	public static boolean ok;
	public static boolean rematchRequested;
	
	private JLabel titleLabel;
	private JLabel youLabel;
	private JLabel messageLabel;
	private JLabel playLabel_0_0;
	private JLabel playLabel_1_0;
	private JLabel playLabel_2_0;
	private JLabel playLabel_0_1;
	private JLabel playLabel_1_1;
	private JLabel playLabel_2_1;
	private JLabel playLabel_0_2;
	private JLabel playLabel_1_2;
	private JLabel playLabel_2_2;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(GameUtils.GameStatus gameStatus, boolean isHosting_, ArrayList<Point> coordinates_, ArrayList<ArrayList<String>> trisPoints_) {
		try {
			ok = false;
			rematchRequested = false;
			
			status = gameStatus;
			isHosting = isHosting_;
			coordinates = coordinates_;
			trisPoints = trisPoints_;
			
			RevengeDialog dialog = new RevengeDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(DEFAULT_MODALITY_TYPE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillMatrix() {
		playLabel_0_0.setText(trisPoints.get(0).get(0));	
		playLabel_0_1.setText(trisPoints.get(0).get(1));
		playLabel_0_2.setText(trisPoints.get(0).get(2));
		
		playLabel_1_0.setText(trisPoints.get(1).get(0));
		playLabel_1_1.setText(trisPoints.get(1).get(1));
		playLabel_1_2.setText(trisPoints.get(1).get(2));
		
		playLabel_2_0.setText(trisPoints.get(2).get(0));
		playLabel_2_1.setText(trisPoints.get(2).get(1));
		playLabel_2_2.setText(trisPoints.get(2).get(2));
	}
	
	public void setWinnerColors() {	 
		for(Point i : coordinates) {
			/* first row */
			if(i.first() == 0 && i.second() == 0) {
				playLabel_0_0.setForeground(Colors.green);
			}
			else if(i.first() == 0 && i.second() == 1) {
				playLabel_0_1.setForeground(Colors.green);
			}
			else if(i.first() == 0 && i.second() == 2) {
				playLabel_0_2.setForeground(Colors.green);
			}
			/* second row */
			else if(i.first() == 1 && i.second() == 0) {
				playLabel_1_0.setForeground(Colors.green);
			}
			else if(i.first() == 1 && i.second() == 1) {
				playLabel_1_1.setForeground(Colors.green);
			}
			else if(i.first() == 1 && i.second() == 2) {
				playLabel_1_2.setForeground(Colors.green);
			}
			/* third row */
			else if(i.first() == 2 && i.second() == 0) {
				playLabel_2_0.setForeground(Colors.green);
			}
			else if(i.first() == 2 && i.second() == 1) {
				playLabel_2_1.setForeground(Colors.green);
			}
			else if(i.first() == 2 && i.second() == 2) {
				playLabel_2_2.setForeground(Colors.green);
			}
		}
	}
	
	public void closeSocketAndWindow() {
		if(isHosting) {
			GameManager.getServer().shutdown();
		}
		else {
			GameManager.getClient().shutdown();
		}
		ok = false;
		dispose();
	}
	/**
	 * Create the dialog.
	 */
	public RevengeDialog() {
		setAutoRequestFocus(false);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(ok) {
					if(isHosting) {
						GameManager.getServer().shutdown();
					}
					else {
						GameManager.getClient().shutdown();
					}
				}
				else {
					try {
						if(isHosting) {
							if(!GameManager.getServer().isClosed()) {
								GameManager.getServer().sendByte(GameUtils.EXIT_MESSAGE);
								GameManager.getServer().shutdown();
							}
						}
						else {
							if(!GameManager.getClient().isClosed()){
								GameManager.getClient().sendByte(GameUtils.EXIT_MESSAGE);
								GameManager.getClient().shutdown();
							}
						}
					}
					catch(Exception e1) {
						if(isHosting) {
							GameManager.getServer().shutdown();
						}
						else {
							GameManager.getClient().shutdown();
						}
					}
				}
			}
		});
		setTitle("Tic-Tac-Toe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RevengeDialog.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		titleLabel = new JLabel("Tic-Tac-Toe");
		titleLabel.setBounds(134, 23, 245, 61);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(235, 235, 235));
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
		contentPanel.add(titleLabel);
		
		row1 = new JLabel("");
		row1.setOpaque(true);
		row1.setBackground(new Color(235, 235, 235));
		row1.setBounds(225, 100, 5, 140);
		contentPanel.add(row1);
		
		row2 = new JLabel("");
		row2.setOpaque(true);
		row2.setBackground(new Color(235, 235, 235));
		row2.setBounds(282, 100, 5, 140);
		contentPanel.add(row2);
		
		row3 = new JLabel("");
		row3.setOpaque(true);
		row3.setBackground(new Color(235, 235, 235));
		row3.setBounds(175, 144, 160, 5);
		contentPanel.add(row3);
		
		row4 = new JLabel("");
		row4.setOpaque(true);
		row4.setBackground(new Color(235, 235, 235));
		row4.setBounds(175, 188, 160, 5);
		contentPanel.add(row4);
		
		JLabel lblPictureYou = new JLabel("");
		lblPictureYou.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		lblPictureYou.setForeground(new Color(235, 235, 235));
		lblPictureYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblPictureYou.setBounds(43, 32, 41, 45);
		contentPanel.add(lblPictureYou);
		
		JLabel lblPictureEnemy = new JLabel("");
		lblPictureEnemy.setHorizontalAlignment(SwingConstants.CENTER);
		lblPictureEnemy.setForeground(new Color(235, 235, 235));
		lblPictureEnemy.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		lblPictureEnemy.setBounds(423, 32, 41, 45);
		contentPanel.add(lblPictureEnemy);
		
		enemyLabel = new JLabel("Enemy");
		enemyLabel.setBounds(393, 100, 114, 39);
		enemyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		enemyLabel.setHorizontalAlignment(SwingConstants.LEFT);
		enemyLabel.setForeground(new Color(235, 235, 235));
		enemyLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
		contentPanel.add(enemyLabel);
		
		youLabel = new JLabel("Tu");
		youLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		youLabel.setHorizontalAlignment(SwingConstants.CENTER);
		youLabel.setForeground(new Color(235, 235, 235));
		youLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		youLabel.setBounds(46, 100, 66, 39);
		contentPanel.add(youLabel);
		
		messageLabel = new JLabel("La vita è più divertente se si gioca.  (Roald Dahl)");
		messageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(new Color(235, 235, 235));
		messageLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
		messageLabel.setBounds(60, 257, 407, 28);
		contentPanel.add(messageLabel);
		
		revengeBtn = new JButton("Rivincita");
		revengeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(rematchRequested) {
						if(isHosting) {
							GameManager.getServer().sendByte(GameUtils.CONFIRM_REMATCH);
						}
						else {
							GameManager.getClient().sendByte(GameUtils.CONFIRM_REMATCH);
						}
						ok = true;
						dispose();
					}
					else {
						if(isHosting) {
							GameManager.getServer().sendByte(GameUtils.REMATCH);
						}
						else {
							GameManager.getClient().sendByte(GameUtils.REMATCH);
						}
						messageLabel.setText("Richiesta di rivincita inviata... In attesa di una risposta!");
					}
				}
				catch(IOException e1) {
					closeSocketAndWindow();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				revengeBtn.setBorder(new LineBorder(new Color(2, 21, 31), 4, true));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				revengeBtn.setBorder(new LineBorder(new Color(2, 21, 31), 3, true));
			}
		});
		revengeBtn.setForeground(new Color(235, 235, 235));
		revengeBtn.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		revengeBtn.setFocusPainted(false);
		revengeBtn.setContentAreaFilled(false);
		revengeBtn.setBorder(new LineBorder(new Color(2, 21, 31), 3, true));
		revengeBtn.setBackground(new Color(15, 49, 66));
		revengeBtn.setBounds(87, 302, 129, 40);
		contentPanel.add(revengeBtn);
		
		exitBtn = new JButton("Abbandona");
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(rematchRequested) {
						if(isHosting) {
							GameManager.getServer().sendByte(GameUtils.REMATCH_REFEUSED);
						}
						else {
							GameManager.getClient().sendByte(GameUtils.REMATCH_REFEUSED);
						}
						utils.AlertClass.showMsgBox(null, "Rivincita Rifiutata", "Richiesta di rivincita rifiutata");
						closeSocketAndWindow();
					}
					else {
						dispose();
						return;
					}
				}
				catch(Exception e1) {
					closeSocketAndWindow();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBtn.setBorder(new LineBorder(new Color(2, 21, 31), 4, true));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitBtn.setBorder(new LineBorder(new Color(2, 21, 31), 3, true));
			}
		});
		exitBtn.setForeground(new Color(235, 235, 235));
		exitBtn.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		exitBtn.setFocusPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorder(new LineBorder(new Color(2, 21, 31), 3, true));
		exitBtn.setBackground(new Color(15, 49, 66));
		exitBtn.setBounds(294, 302, 129, 40);
		contentPanel.add(exitBtn);
		
		playLabel_0_0 = new JLabel("O");
		playLabel_0_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_0.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_0_0.setForeground(new Color(235, 235, 235));
		playLabel_0_0.setBounds(180, 110, 41, 28);
		contentPanel.add(playLabel_0_0);
		
		playLabel_1_0 = new JLabel("O");
		playLabel_1_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_0.setForeground(new Color(235, 235, 235));
		playLabel_1_0.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_1_0.setBounds(180, 155, 41, 28);
		contentPanel.add(playLabel_1_0);
		
		playLabel_2_0 = new JLabel("X");
		playLabel_2_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_0.setForeground(new Color(235, 235, 235));
		playLabel_2_0.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_2_0.setBounds(180, 204, 41, 28);
		contentPanel.add(playLabel_2_0);
		
		playLabel_0_1 = new JLabel("O");
		playLabel_0_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_1.setForeground(new Color(235, 235, 235));
		playLabel_0_1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_0_1.setBounds(236, 110, 41, 28);
		contentPanel.add(playLabel_0_1);
		
		playLabel_1_1 = new JLabel("X");
		playLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_1.setForeground(new Color(235, 235, 235));
		playLabel_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_1_1.setBounds(236, 155, 41, 28);
		contentPanel.add(playLabel_1_1);
		
		playLabel_2_1 = new JLabel("O");
		playLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_1.setForeground(new Color(235, 235, 235));
		playLabel_2_1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_2_1.setBounds(236, 204, 41, 28);
		contentPanel.add(playLabel_2_1);
		
		playLabel_0_2 = new JLabel("X");
		playLabel_0_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_2.setForeground(new Color(235, 235, 235));
		playLabel_0_2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_0_2.setBounds(291, 110, 41, 28);
		contentPanel.add(playLabel_0_2);
		
		playLabel_1_2 = new JLabel("O");
		playLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_2.setForeground(new Color(235, 235, 235));
		playLabel_1_2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_1_2.setBounds(291, 155, 41, 28);
		contentPanel.add(playLabel_1_2);
		
		playLabel_2_2 = new JLabel("X");
		playLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_2.setForeground(new Color(235, 235, 235));
		playLabel_2_2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		playLabel_2_2.setBounds(291, 204, 41, 28);
		contentPanel.add(playLabel_2_2);
		
		lblNewLabel = new JLabel("Copyright © 2023 Vincenzo Caliendo. All rights reserved");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(193, 193, 193));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		lblNewLabel.setBounds(120, 353, 290, 18);
		contentPanel.add(lblNewLabel);
		
		if(status == GameUtils.GameStatus.WON) {
			titleLabel.setText("Hai Vinto!");
			lblPictureYou.setForeground(Colors.green);
			youLabel.setForeground(Colors.green);
		}
		else if(status == GameUtils.GameStatus.LOST) {
			titleLabel.setText("Hai Perso!");
			lblPictureEnemy.setForeground(Colors.green);
			enemyLabel.setForeground(Colors.green);
		}
		else {
			titleLabel.setText("Pareggio!");
		}
		
		if(isHosting) {
			this.setTitle("Tic-Tac-Toe [server]");
			lblPictureYou.setText(GameManager.getServerShape());
			lblPictureEnemy.setText(GameManager.getClientShape());
			enemyLabel.setText(GameManager.getNickClient());
		}
		else {
			this.setTitle("Tic-Tac-Toe [client]");
			lblPictureYou.setText(GameManager.getClientShape());
			lblPictureEnemy.setText(GameManager.getServerShape());
			enemyLabel.setText(GameManager.getNickServer());
		}
		
		fillMatrix();
		if(status != GameUtils.GameStatus.DRAW) {
			setWinnerColors();
		}
		
		listenThread = new Thread() {
			public void run() {
				byte msg = 0;
				
				while(true) {
					try {
						if(isHosting) {
							msg = GameManager.getServer().readByte();
						}
						else {
							msg = GameManager.getClient().readByte();
						}
						
						switch (msg) {
						case GameUtils.EXIT_MESSAGE:
							if(isHosting) {
								AlertClass.showMsgBox(null, "Game Info", GameManager.getNickClient() + " left the game :/");
							}
							else {
								AlertClass.showMsgBox(null, "Game Info", GameManager.getNickServer() + " left the game :/");
							}
							dispose();
							return;
						
						case GameUtils.REMATCH:
							messageLabel.setText("Hai una richiesta di rivincita... Cosa aspetti accetta!");
							revengeBtn.setText("Accetta");
							exitBtn.setText("Rifiuta");
							rematchRequested = true;
							break;
							
						case GameUtils.REMATCH_REFEUSED:
							if(isHosting) {
								utils.AlertClass.showMsgBox(null, "Rivincita Rifiutata", GameManager.getNickClient() + " ha rifiutato la tua richiesta di rivincita");
							}
							else {
								utils.AlertClass.showMsgBox(null, "Rivincita Rifiutata", GameManager.getNickServer() + " ha rifiutato la tua richiesta di rivincita");
							}
							closeSocketAndWindow();
							return;
							
						case GameUtils.CONFIRM_REMATCH:
							ok = true;
							dispose();
							return;
						}
					}
					catch(IOException e) {
						closeSocketAndWindow();
						return;
					}
				}
			}
		};
		listenThread.start();
	}
}
