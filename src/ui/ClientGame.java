package ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.GameManager;
import main.Point;
import utils.AlertClass;
import utils.Colors;
import utils.GameUtils;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.KeyAdapter;
import javax.swing.JScrollPane;

public class ClientGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField msgBox;
	private JLabel titleLabel;
	private JLabel lblNewLabel_1;
	private JLabel nickServerLabel;
	private JTextArea chatBox;
	private JLabel timeLabel;
	private JLabel row1, row2, row3, row4;
	private JLabel waitingLabel;
	
	private static ClientGame dialog;
	private static Thread threadClient;
	private static ArrayList<Point> coordinates;
	private static ArrayList<ArrayList<String>> moves;
	
	private static Point lastPoint;
	
	private JLabel playLabel_0_0;
	private JLabel playLabel_1_0;
	private JLabel playLabel_2_0;
	private JLabel playLabel_2_1;
	private JLabel playLabel_2_2;
	private JLabel playLabel_1_2;
	private JLabel playLabel_1_1;
	private JLabel playLabel_0_1;
	private JLabel playLabel_0_2;
	private JLabel notificationLabel;
	
	private JScrollPane jPaneChat;
	
	private static boolean windowClosed;
	private static boolean handleConnErr;
	private static boolean updateChat;
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			windowClosed = false;
			handleConnErr = false;
			updateChat = true;
			
			coordinates = new ArrayList<Point>();
			moves = GameUtils.createMovesMatrix();
			
			lastPoint = new Point();
			
			dialog = new ClientGame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(DEFAULT_MODALITY_TYPE);
			dialog.setVisible(true);
			
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					try {
						if(!GameManager.getClient().isClosed() && !handleConnErr) {
							windowClosed = true;
							GameManager.getClient().sendByte(GameUtils.EXIT_MESSAGE);
							dialog.closeSocketAndWindow();
						}
					}
					catch(NullPointerException e2) {
						
					}
					catch (IOException e1) {
						dialog.closeSocketAndWindow();
					}
				}
			});
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean handleConnection() {
		String err = GameManager.initClient(IpPortDialog.ip, IpPortDialog.port, NicknameDialog.nickname);
		if(!err.equals("true")) {
			utils.AlertClass.showErrBox(null, "Connection Error", err);
			handleConnErr = true;
			
			return false;
		}

		return true;
	}
	
	public void closeSocketAndWindow() {
		GameManager.getClient().shutdown();
		dispose();
	}
	
	public boolean isDraw() {
		return !playLabel_0_0.getText().isEmpty() && !playLabel_0_1.getText().isEmpty() && !playLabel_0_2.getText().isEmpty() &&
			   !playLabel_1_0.getText().isEmpty() && !playLabel_1_1.getText().isEmpty() && !playLabel_1_2.getText().isEmpty() && 
			   !playLabel_2_0.getText().isEmpty() && !playLabel_2_1.getText().isEmpty() && !playLabel_2_2.getText().isEmpty();
	}
	
	public void saveMoves() {
		moves.get(0).set(0, playLabel_0_0.getText());
		moves.get(0).set(1, playLabel_0_1.getText());
		moves.get(0).set(2, playLabel_0_2.getText());
		
		moves.get(1).set(0, playLabel_1_0.getText());
		moves.get(1).set(1, playLabel_1_1.getText());
		moves.get(1).set(2, playLabel_1_2.getText());
		
		moves.get(2).set(0, playLabel_2_0.getText());
		moves.get(2).set(1, playLabel_2_1.getText());
		moves.get(2).set(2, playLabel_2_2.getText());
	}
	
	public void clearPlayLabels() {
		playLabel_0_0.setText(""); playLabel_0_1.setText(""); playLabel_0_2.setText("");
		playLabel_1_0.setText(""); playLabel_1_1.setText(""); playLabel_1_2.setText("");
		playLabel_2_0.setText(""); playLabel_2_1.setText(""); playLabel_2_2.setText("");
	}
	
	public void makeThingsVisible(boolean b) {
		/* waiting label is gonna be always the opposite */
		waitingLabel.setVisible(!b);
		
		titleLabel.setVisible(b);
		lblNewLabel_1.setVisible(b);
		row1.setVisible(b);
		row2.setVisible(b);
		row3.setVisible(b);
		row4.setVisible(b);
		msgBox.setVisible(b);
		timeLabel.setVisible(b);
		jPaneChat.setVisible(b);
		
		nickServerLabel.setVisible(b);
		nickServerLabel.setText(GameManager.getNickServer());
	}
	
	public void printRowAndCol(int r, int c, String text) {
		if(r == 0) {
			if(c == 0) {
				playLabel_0_0.setText(text);
			}
			else if(c == 1) {
				playLabel_0_1.setText(text);
			}
			else {
				playLabel_0_2.setText(text);
			}
		}
		else if (r == 1) {
			if(c == 0) {
				playLabel_1_0.setText(text);
			}
			else if(c == 1) {
				playLabel_1_1.setText(text);
			}
			else {
				playLabel_1_2.setText(text);
			}
		}
		else {
			if(c == 0) {
				playLabel_2_0.setText(text);
			}
			else if(c == 1) {
				playLabel_2_1.setText(text);
			}
			else {
				playLabel_2_2.setText(text);
			}
		}
	}
	
	public void resetGame() {
		try {
			if(RevengeDialog.listenThread.isAlive()) {
				RevengeDialog.listenThread.interrupt();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public ClientGame() {
		setResizable(false);
		setTitle("Tic-Tac-Toe  [client]");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGame.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAutoRequestFocus(false);
		setBounds(100, 100, 580, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		titleLabel = new JLabel("Tic-Tac-Toe");
		titleLabel.setBounds(141, 21, 245, 61);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(235, 235, 235));
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
		contentPanel.add(titleLabel);
		
		lblNewLabel_1 = new JLabel("Tu");
		lblNewLabel_1.setBounds(44, 37, 58, 39);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblNewLabel_1.setForeground(new Color(235, 235, 235));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		contentPanel.add(lblNewLabel_1);
		
		nickServerLabel = new JLabel(GameManager.getNickServer());
		nickServerLabel.setBounds(416, 37, 127, 39);
		nickServerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nickServerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nickServerLabel.setForeground(new Color(235, 235, 235));
		nickServerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		contentPanel.add(nickServerLabel);
		
		chatBox = new JTextArea(" [Game]: Good luck and have fun!!\n");
		chatBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) && chatBox.getDocument().getLength() > 150) {
					updateChat = false;
				}
			}
		});
		chatBox.setLineWrap(true);
		chatBox.setForeground(new Color(190, 190, 190));
		chatBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		chatBox.setEditable(false);
		chatBox.setBounds(308, 124, 235, 174);
		chatBox.setBorder(new LineBorder(new Color(10, 34, 46), 3, true));
		chatBox.setBackground(new Color(15, 55, 77));
		
		jPaneChat = new JScrollPane(chatBox);
		jPaneChat.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(chatBox.getDocument().getLength() > 150) {
					updateChat = false;
				}
			}
		});
		jPaneChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jPaneChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jPaneChat.setBorder(null);
		jPaneChat.setBounds(308, 124, 245, 174);
		contentPanel.add(jPaneChat);

		
		msgBox = new JTextField();
		/* send a message */
		msgBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && msgBox.getText().length() < GameUtils.MAX_LEN_MESSAGE_BOX) {
					try {
						if(msgBox.getText().length() == 0) {
							return;
						}
						
						GameManager.getClient().sendByte(GameUtils.NORMAL_MESSAGE);
						GameManager.getClient().send(msgBox.getText());
						System.out.print("\nMessage Sent Client: " + msgBox.getText());
						chatBox.append(" [You]: " + msgBox.getText() + "\n");
						chatBox.setCaretPosition(chatBox.getDocument().getLength());
						notificationLabel.setVisible(false);
						msgBox.setText("");
					} 
					catch (Exception e1) {
						e1.printStackTrace();
						AlertClass.showErrBox(null, "Connection Error", "An error occured while trying to send a message. Pleasy retry");
						return;
					}
				}
				if(msgBox.getText().length() > GameUtils.MAX_LEN_MESSAGE_BOX) {
					msgBox.setForeground(Colors.red);
				}
				else {
					msgBox.setForeground(Colors.chatBoxColor);
				}
			}
		});
		msgBox.setText("Send a message");
		msgBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(msgBox.getText().equalsIgnoreCase("Send a message")) {
					msgBox.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(msgBox.getText().trim().isEmpty()) {
					msgBox.setText("Send a message");
				}
			}
		});
		msgBox.setHorizontalAlignment(SwingConstants.CENTER);
		msgBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		msgBox.setForeground(new Color(221, 221, 221));
		msgBox.setText("Send a message");
		msgBox.setCaretColor(new Color(218, 218, 218));
		msgBox.setBorder(new LineBorder(new Color(15, 34, 46), 3, true));
		msgBox.setBackground(new Color(15, 55, 77));
		msgBox.setBounds(324, 309, 215, 35);
		contentPanel.add(msgBox);
		msgBox.setColumns(10);
		
		timeLabel = new JLabel("2:00");
		timeLabel.setBounds(21, 314, 58, 39);
		timeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
		timeLabel.setForeground(new Color(235, 235, 235));
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPanel.add(timeLabel);
		
		row1 = new JLabel("");
		row1.setBounds(114, 124, 5, 180);
		row1.setOpaque(true);
		row1.setBackground(new Color(235, 235, 235));
		contentPanel.add(row1);
		
		row2 = new JLabel("");
		row2.setBounds(189, 124, 5, 180);
		row2.setOpaque(true);
		row2.setBackground(new Color(235, 235, 235));
		contentPanel.add(row2);
		
		row3 = new JLabel("");
		row3.setBounds(46, 184, 215, 5);
		row3.setOpaque(true);
		row3.setBackground(new Color(235, 235, 235));
		contentPanel.add(row3);
		
		row4 = new JLabel("");
		row4.setBounds(44, 242, 215, 5);
		row4.setOpaque(true);
		row4.setBackground(new Color(235, 235, 235));
		contentPanel.add(row4);
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setBounds(111, 145, 348, 58);
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		contentPanel.add(waitingLabel);
		
		playLabel_0_0 = new JLabel("");
		playLabel_0_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_0_0.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 0, 0)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(0, 0);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(0, 0, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_0_0.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_0_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_0_0.setForeground(new Color(235, 235, 235));
		playLabel_0_0.setBounds(49, 129, 58, 49);
		contentPanel.add(playLabel_0_0);
		
		playLabel_1_0 = new JLabel("");
		playLabel_1_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_1_0.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 1, 0)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(1, 0);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(1, 0, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_1_0.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_1_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_0.setForeground(new Color(235, 235, 235));
		playLabel_1_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_1_0.setBounds(50, 194, 58, 43);
		contentPanel.add(playLabel_1_0);
		
		playLabel_2_0 = new JLabel("");
		playLabel_2_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_2_0.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 2, 0)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(2, 0);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(2, 0, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_2_0.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_2_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_0.setForeground(new Color(235, 235, 235));
		playLabel_2_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_2_0.setBounds(50, 254, 58, 45);
		contentPanel.add(playLabel_2_0);
		
		playLabel_2_1 = new JLabel("");
		playLabel_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_2_1.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 2, 1)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(2, 1);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(2, 1, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_2_1.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_1.setForeground(new Color(235, 235, 235));
		playLabel_2_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_2_1.setBounds(125, 253, 58, 47);
		contentPanel.add(playLabel_2_1);
		
		playLabel_2_2 = new JLabel("");
		playLabel_2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_2_2.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 2, 2)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(2, 2);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(2, 2, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_2_2.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_2_2.setForeground(new Color(235, 235, 235));
		playLabel_2_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_2_2.setBounds(200, 251, 55, 49);
		contentPanel.add(playLabel_2_2);
		
		playLabel_1_2 = new JLabel("");
		playLabel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_1_2.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 1, 2)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(1, 2);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(1, 2, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_1_2.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_2.setForeground(new Color(235, 235, 235));
		playLabel_1_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_1_2.setBounds(200, 196, 58, 43);
		contentPanel.add(playLabel_1_2);
		
		playLabel_1_1 = new JLabel("");
		playLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_1_1.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 1, 1)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(1, 1);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(1, 1, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_1_1.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_1_1.setForeground(new Color(235, 235, 235));
		playLabel_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_1_1.setBounds(125, 196, 58, 41);
		contentPanel.add(playLabel_1_1);
		
		playLabel_0_1 = new JLabel("");
		playLabel_0_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_0_1.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 0, 1)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(0, 1);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(0, 1, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_0_1.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_0_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_1.setForeground(new Color(235, 235, 235));
		playLabel_0_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_0_1.setBounds(124, 132, 59, 46);
		contentPanel.add(playLabel_0_1);
		
		playLabel_0_2 = new JLabel("");
		playLabel_0_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel_0_2.getText().isEmpty() || !GameManager.isClientTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getClient().getDataOutputStream(), 0, 2)) {
					closeSocketAndWindow();
					return;
				}
				lastPoint.set(0, 2);
				
				GameManager.setServerTurn();
				GameManager.addToMatrix(0, 2, GameManager.getClientShape().charAt(0));
				
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, false);
				playLabel_0_2.setText(GameManager.getClientShape());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
	            dialog.setCursor(GameUtils.CROSSHAIR_CURSOR);
	            dialog.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
	            dialog.setCursor(GameUtils.DEFAULT_CURSOR);
	            dialog.setVisible(true);
			}
		});
		playLabel_0_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel_0_2.setForeground(new Color(235, 235, 235));
		playLabel_0_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel_0_2.setBounds(200, 134, 57, 46);
		contentPanel.add(playLabel_0_2);
		
		notificationLabel = new JLabel();
		notificationLabel.setVisible(false);
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		notificationLabel.setForeground(new Color(15, 49, 66));
		notificationLabel.setBackground(new Color(15, 49, 66));
		notificationLabel.setIcon(new ImageIcon(ServerGame.class.getResource("/ui/resources/32-32.png")));
		notificationLabel.setBounds(518, 94, 25, 25);
		contentPanel.add(notificationLabel);
		
		lblNewLabel = new JLabel("Copyright © 2023 Vincenzo Caliendo. All rights reserved");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(193, 193, 193));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		lblNewLabel.setBounds(95, 355, 291, 18);
		contentPanel.add(lblNewLabel);
		
		/* Hide all the components */
		makeThingsVisible(false);
		/* start the thread to listen a client connection */
		threadClient = new Thread() {
			public void run() {
				if(!handleConnection()) {
					closeSocketAndWindow();
					return;
				}

				try {
					/* get the shape of the client */
					GameManager.setClientShape(GameManager.getClient().read());
					
					if(GameManager.getClientShape().equals("O")) {
						GameManager.setServerShape("X");
					}
					else {
						GameManager.setServerShape("O");
					}
					
					/* get the turn */
					if(GameManager.getClient().read().equals("client")) {
						GameManager.setClientTurn();
					}
					else {
						GameManager.setServerTurn();
					}
				} catch (IOException e) {
					AlertClass.showErrBox(null, "Connection Error", e.getMessage());
					closeSocketAndWindow();
					return;
				}
				/* initialize the tic-tac-toe matrix */
				GameManager.initMatrix();

				makeThingsVisible(true);
				GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, GameManager.isClientTurn());
				
				Byte bMsg;
				String msg;
				int row = 0, col = 0;
				
				/* listen messages */
				while(true) {
					try {
						bMsg = GameManager.getClient().readByte();
					
						switch(bMsg) {
						case GameUtils.NORMAL_MESSAGE:
							msg = GameManager.getClient().read();
							chatBox.append(" [" + GameManager.getNickServer() + "]: " + msg + "\n");
							
							if(updateChat) {
								chatBox.setCaretPosition(chatBox.getDocument().getLength());
							}
							else if(chatBox.getCaretPosition() < chatBox.getDocument().getLength()) {
								notificationLabel.setVisible(true);
							}
							break;
							
						case GameUtils.GAME_MESSAGE:
							row = GameManager.getClient().readByte();
							col = GameManager.getClient().readByte();
							coordinates.clear();
							
							GameManager.addToMatrix(row, col, GameManager.getServerShape().charAt(0));
							GameManager.setClientTurn();
							
							printRowAndCol(row, col, GameManager.getServerShape());
							GameUtils.setTurnColors(lblNewLabel_1, nickServerLabel, true);
							
							coordinates = GameUtils.checkTrees(GameManager.getMatrix(), row, col, GameManager.getServerShape().charAt(0));
							
							/* check if the server has won */
							if(!coordinates.isEmpty()) {
								GameManager.getClient().sendByte(GameUtils.GAME_VICTORY);
								
								saveMoves();
								RevengeDialog.main(GameUtils.GameStatus.LOST, false, coordinates, moves);
								
								if(!RevengeDialog.ok) {
									dispose();
									return;
								}
								else {
									resetGame();
									break;
								}
							}
							/* check the draw */
							if(isDraw()) {
								GameManager.getClient().sendByte(GameUtils.GAME_DRAW);
								
								saveMoves();
								RevengeDialog.main(GameUtils.GameStatus.DRAW, false, coordinates, moves);
								
								if(!RevengeDialog.ok) {
									dispose();
									return;
								}
								else {
									resetGame();
								}
							}	
							break;
							
						case GameUtils.GAME_DRAW:
							saveMoves();
							RevengeDialog.main(GameUtils.GameStatus.DRAW, false, coordinates, moves);
							
							if(!RevengeDialog.ok) {
								dispose();
								return;
							}
							resetGame();
							
							break;
							
						case GameUtils.GAME_VICTORY:
							/* store the coordinates of my victory */
							coordinates = GameUtils.checkTrees(GameManager.getMatrix(), lastPoint.first(), lastPoint.second(), GameManager.getClientShape().charAt(0));
							
							saveMoves();
							RevengeDialog.main(GameUtils.GameStatus.WON, false, coordinates, moves);
							
							if(!RevengeDialog.ok) {
								dispose();
								return;
							}
							resetGame();
							
							break;
							
						case GameUtils.EXIT_MESSAGE:
							AlertClass.showMsgBox(null, "Game Info", GameManager.getNickServer() + " left the game ;/");
							closeSocketAndWindow();
							return;
							
						default:
							System.out.print("\nDefault: " + bMsg);
							break;
						}
					} 
					catch (Exception e) {
						e.printStackTrace();
						if(windowClosed) {
							closeSocketAndWindow();
							return;
						}
						else {
							AlertClass.showErrBox(null, "Connection Error", "An error occured while trying to comunicate with the other side.");
							closeSocketAndWindow();
							return;
						}
					}
				}
			}
		};
		
		threadClient.start();
	}
}
