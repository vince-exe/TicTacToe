package ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import main.GameManager;
import main.Point;
import utils.AlertClass;
import utils.Colors;
import utils.GameUtils;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class ServerGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel waitingLabel;
	private JLabel titleLabel;
	private JLabel lblNewLabel_1;
	private JLabel nickClientLabel;
	private JLabel row1;
	private JLabel row2;
	private JLabel row3;
	private JLabel row4;
	private JLabel timeLabel;
	private JTextArea chatBox;
	private JScrollPane jPaneChat;
	private JLabel notificationLabel;
	
	private static boolean windowClosed;
	private static boolean handleConnErr;
	private static boolean updateChat;
	
	private static Thread threadServer;
	private static ServerGame dialog;
	private static ArrayList<Point> coordinates;
	
	private JTextField msgBox;
	private JLabel playLabel0;
	private JLabel playLabel0_1;
	private JLabel playLabel0_2;
	private JLabel playLabel1_2;
	private JLabel playLabel1_0;
	private JLabel playLabel1_1;
	private JLabel playLabel2_0;
	private JLabel playLabel2_1;
	private JLabel playLabel2_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			windowClosed = false;
			handleConnErr = false;
			updateChat = true;

			coordinates = new ArrayList<Point>();
			
			dialog = new ServerGame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(DEFAULT_MODALITY_TYPE);
			dialog.setVisible(true);
					
			dialog.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowClosing(WindowEvent e) {

				}

				@Override
				public void windowClosed(WindowEvent e) {
					if(!GameManager.getServer().isClosed() && !handleConnErr) {
						try {
							windowClosed = true;
							GameManager.getServer().sendByte(GameUtils.EXIT_MESSAGE);
							dialog.closeSocketAndWindow();
						} 
						catch (IOException e1) {
							dialog.closeSocketAndWindow();
						}
					}
				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean handleConnection() {
		String err = GameManager.initServer(IpPortDialog.ip, IpPortDialog.port, NicknameDialog.nickname);
		if(!err.equals("true")) {
			utils.AlertClass.showErrBox(null, "Connection Error", err);
			
			handleConnErr = true;
			return false;
		}

		return true;
	}
	
	public void closeSocketAndWindow() {
		GameManager.getServer().shutdown();
		dispose();
	}
	
	public boolean isDraw() {
		return !playLabel0.getText().isEmpty() && !playLabel0_1.getText().isEmpty() && !playLabel0_2.getText().isEmpty() &&
			   !playLabel1_0.getText().isEmpty() && !playLabel1_1.getText().isEmpty() && !playLabel1_2.getText().isEmpty() && 
			   !playLabel2_0.getText().isEmpty() && !playLabel2_1.getText().isEmpty() && !playLabel2_2.getText().isEmpty();
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
		chatBox.setVisible(b);
		msgBox.setVisible(b);
		timeLabel.setVisible(b);
		jPaneChat.setVisible(b);
		
		nickClientLabel.setVisible(b);
		nickClientLabel.setText(GameManager.getNickClient());
	}
	public void printRowAndCol(int r, int c, String text) {
		if(r == 0) {
			if(c == 0) {
				playLabel0.setText(text);
			}
			else if(c == 1) {
				playLabel0_1.setText(text);
			}
			else {
				playLabel0_2.setText(text);
			}
		}
		else if (r == 1) {
			if(c == 0) {
				playLabel1_0.setText(text);
			}
			else if(c == 1) {
				playLabel1_1.setText(text);
			}
			else {
				playLabel1_2.setText(text);
			}
		}
		else {
			if(c == 0) {
				playLabel2_0.setText(text);
			}
			else if(c == 1) {
				playLabel2_1.setText(text);
			}
			else {
				playLabel2_2.setText(text);
			}
		}
	}
	
	/**
	 * Create the dialog
	 */
	public ServerGame() {
		setTitle("Tic-Tac-Toe  [server]");
		setResizable(false);
		setAutoRequestFocus(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServerGame.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 403);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		titleLabel = new JLabel("Tic-Tac-Toe");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(235, 235, 235));
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
		titleLabel.setBounds(141, 21, 245, 61);
		contentPanel.add(titleLabel);
		
		lblNewLabel_1 = new JLabel("Tu");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblNewLabel_1.setForeground(new Color(235, 235, 235));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(44, 37, 58, 39);
		contentPanel.add(lblNewLabel_1);
		
		nickClientLabel = new JLabel(GameManager.getNickClient());
		nickClientLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nickClientLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nickClientLabel.setForeground(new Color(235, 235, 235));
		nickClientLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		nickClientLabel.setBounds(416, 37, 127, 39);
		contentPanel.add(nickClientLabel);
		
		row1 = new JLabel("");
		row1.setOpaque(true);
		row1.setBackground(new Color(235, 235, 235));
		row1.setBounds(114, 124, 5, 180);
		contentPanel.add(row1);
		
		row2 = new JLabel("");
		row2.setOpaque(true);
		row2.setBackground(new Color(235, 235, 235));
		row2.setBounds(189, 124, 5, 180);
		contentPanel.add(row2);
		
		row3 = new JLabel("");
		row3.setOpaque(true);
		row3.setBackground(new Color(235, 235, 235));
		row3.setBounds(46, 184, 215, 5);
		contentPanel.add(row3);
		
		row4 = new JLabel("");
		row4.setOpaque(true);
		row4.setBackground(new Color(235, 235, 235));
		row4.setBounds(44, 242, 215, 5);
		contentPanel.add(row4);
		
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
		/* send a message */
		msgBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && msgBox.getText().length() < GameUtils.MAX_LEN_MESSAGE_BOX) {
					try {
						if(msgBox.getText().length() == 0) {
							return;
						}
						
						GameManager.getServer().sendByte(GameUtils.NORMAL_MESSAGE);
						GameManager.getServer().send(msgBox.getText());
						
						chatBox.append(" [You]: " + msgBox.getText() + "\n");
						chatBox.setCaretPosition(chatBox.getDocument().getLength());
						notificationLabel.setVisible(false);
						msgBox.setText("");
					} 
					catch (IOException e1) {
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
		
		playLabel0 = new JLabel("");
		playLabel0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0.setForeground(new Color(235, 235, 235));
		playLabel0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 0, 0)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(0, 0, GameManager.getServerShape().charAt(0));
				playLabel0.setText(GameManager.getServerShape());	
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
		playLabel0.setBounds(47, 126, 60, 52);
		contentPanel.add(playLabel0);
		
		playLabel0_1 = new JLabel("");
		playLabel0_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0_1.setForeground(new Color(235, 235, 235));
		playLabel0_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0_1.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 0, 1)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(0, 1, GameManager.getServerShape().charAt(0));
				playLabel0_1.setText(GameManager.getServerShape());
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
		playLabel0_1.setBounds(121, 124, 63, 55);
		contentPanel.add(playLabel0_1);
		
		playLabel0_2 = new JLabel("");
		playLabel0_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0_2.setForeground(new Color(235, 235, 235));
		playLabel0_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0_2.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 0, 2)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(0, 2, GameManager.getServerShape().charAt(0));
				playLabel0_2.setText(GameManager.getServerShape());
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
		playLabel0_2.setBounds(198, 124, 63, 55);
		contentPanel.add(playLabel0_2);
		
		playLabel1_2 = new JLabel("");
		playLabel1_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_2.setForeground(new Color(235, 235, 235));
		playLabel1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_2.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 1, 2)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(1, 2, GameManager.getServerShape().charAt(0));
				playLabel1_2.setText(GameManager.getServerShape());
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
		playLabel1_2.setBounds(198, 192, 58, 47);
		contentPanel.add(playLabel1_2);
		
	    playLabel1_0 = new JLabel("");
		playLabel1_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_0.setForeground(new Color(235, 235, 235));
		playLabel1_0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_0.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 1, 0)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameManager.addToMatrix(1, 0, GameManager.getServerShape().charAt(0));
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				playLabel1_0.setText(GameManager.getServerShape());
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
		playLabel1_0.setBounds(45, 192, 62, 47);
		contentPanel.add(playLabel1_0);
		
		playLabel1_1 = new JLabel("");
		playLabel1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_1.setForeground(new Color(235, 235, 235));
		playLabel1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_1.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 1, 1)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(1, 1, GameManager.getServerShape().charAt(0));
				playLabel1_1.setText(GameManager.getServerShape());
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
		playLabel1_1.setBounds(123, 193, 60, 44);
		contentPanel.add(playLabel1_1);
		
		playLabel2_0 = new JLabel("");
		playLabel2_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_0.setForeground(new Color(235, 235, 235));
		playLabel2_0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_0.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 2, 0)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(2, 0, GameManager.getServerShape().charAt(0));
				playLabel2_0.setText(GameManager.getServerShape());
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
		playLabel2_0.setBounds(48, 252, 60, 48);
		contentPanel.add(playLabel2_0);
		
		playLabel2_1 = new JLabel("");
		playLabel2_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_1.setForeground(new Color(235, 235, 235));
		playLabel2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_1.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 2, 1)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(2, 1, GameManager.getServerShape().charAt(0));
				playLabel2_1.setText(GameManager.getServerShape());
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
		playLabel2_1.setBounds(123, 255, 59, 44);
		contentPanel.add(playLabel2_1);
		
		playLabel2_2 = new JLabel("");
		playLabel2_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_2.setForeground(new Color(235, 235, 235));
		playLabel2_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_2.getText().isEmpty() || !GameManager.isServerTurn()) {
					return;
				}
				if(!GameUtils.sendCoordinates(GameManager.getServer().getDataOutputStream(), 2, 2)) {
					closeSocketAndWindow();
					return;
				}
				GameManager.setClientTurn();
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, false);
				GameManager.addToMatrix(2, 2, GameManager.getServerShape().charAt(0));
				playLabel2_2.setText(GameManager.getServerShape());
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
		playLabel2_2.setBounds(202, 255, 52, 44);
		contentPanel.add(playLabel2_2);
		
		timeLabel = new JLabel("2:00");
		timeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
		timeLabel.setForeground(new Color(235, 235, 235));
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timeLabel.setBounds(21, 314, 58, 39);
		contentPanel.add(timeLabel);
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		waitingLabel.setBounds(111, 145, 348, 58);
		contentPanel.add(waitingLabel);
		
		notificationLabel = new JLabel("");
		notificationLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chatBox.setCaretPosition(chatBox.getDocument().getLength());
				notificationLabel.setVisible(false);
				updateChat = true;
			}
		});
		notificationLabel.setVisible(false);
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		notificationLabel.setForeground(new Color(15, 49, 66));
		notificationLabel.setBackground(new Color(15, 49, 66));
		notificationLabel.setIcon(new ImageIcon(ServerGame.class.getResource("/ui/resources/32-32.png")));
		notificationLabel.setBounds(518, 94, 25, 25);
		contentPanel.add(notificationLabel);
		
		/* Hide all the components */
		makeThingsVisible(false);
		
		/* start the thread to listen a client connection */
		threadServer = new Thread() {
			public void run() {
				if(GameManager.isFirstTime()) {
					if(!handleConnection()) {
						closeSocketAndWindow();
						return;
					}
				}
				/* asssign the X or O to the players ( random ) */
				GameManager.initShapes();
				/* assign the turn */
				GameUtils.setTurn();
				
				/* initialize the tic-tac-toe matrix */
				GameManager.initMatrix();
				
				try {
					/* send the shape to the client */
					GameManager.getServer().send(GameManager.getClientShape());
					/* send the turn */
					GameManager.getServer().send(GameManager.getTurn());
				} 
				catch (IOException e) {
					AlertClass.showErrBox(null, "Connection Error", e.getMessage());
					closeSocketAndWindow();
					return;
				}
				makeThingsVisible(true);
				GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, GameManager.isServerTurn());
				
				Byte bMsg;
				String msg;
				int row, col;
				
				/* listen messages */
				while(true) {
					try {
						bMsg = GameManager.getServer().readByte();
						
						switch(bMsg) {
						case GameUtils.NORMAL_MESSAGE:
							msg = GameManager.getServer().read();
							chatBox.append(" [" + GameManager.getNickClient() + "]: " + msg + "\n");
							
							if(updateChat) {
								chatBox.setCaretPosition(chatBox.getDocument().getLength());
							}
							else if(chatBox.getCaretPosition() < chatBox.getDocument().getLength()) {
								notificationLabel.setVisible(true);
							}
							break;
							
						case GameUtils.GAME_MESSAGE:
							row = GameManager.getServer().readByte();
							col = GameManager.getServer().readByte();
							coordinates.clear();
							
							GameManager.setServerTurn();
							printRowAndCol(row, col, GameManager.getClientShape());
							GameManager.addToMatrix(row, col, GameManager.getClientShape().charAt(0));
							GameUtils.setTurnColors(lblNewLabel_1, nickClientLabel, true);
							
							coordinates = GameUtils.checkTrees(GameManager.getMatrix(), row, col, GameManager.getClientShape().charAt(0));
							
							/* check if the client has won */
							if(!coordinates.isEmpty()) {
								GameManager.getServer().sendByte(GameUtils.GAME_VICTORY);
								RevengeDialog.main(GameUtils.GameStatus.LOST, true, coordinates);
								
								if(!RevengeDialog.ok) {
									dispose();
									return;
								}
							}
							/* check the draw */
							if(isDraw()) {
								GameManager.getServer().sendByte(GameUtils.GAME_DRAW);
								RevengeDialog.main(GameUtils.GameStatus.DRAW, true, coordinates);
								
								if(!RevengeDialog.ok) {
									dispose();
									return;
								}
							}	
							break;
							
						case GameUtils.GAME_DRAW:
							RevengeDialog.main(GameUtils.GameStatus.DRAW, true, coordinates);
							
							if(!RevengeDialog.ok) {
								dispose();
								return;
							}
							break;
							
						case GameUtils.GAME_VICTORY:
							RevengeDialog.main(GameUtils.GameStatus.WON, true, coordinates);

							if(!RevengeDialog.ok) {
								dispose();
								return;
							}
							break;
							
						case GameUtils.EXIT_MESSAGE:
							AlertClass.showMsgBox(null, "Game Info", GameManager.getNickClient() + " left the game ;/");
							closeSocketAndWindow();
							return;
							
						default:
							break;
						}
					} 
					catch (IOException e) {
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
		threadServer.start();
	}
}
