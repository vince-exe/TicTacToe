package ui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameManager;
import utils.Colors;
import utils.GameUtils;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel waitingLabel;
	private JLabel titleLabel;
	private JLabel lblNewLabel_1;
	private JLabel nickServerLabel;
	private JLabel row1;
	private JLabel row2;
	private JLabel row3;
	private JLabel row4;
	private JLabel timeLabel;
	private JTextArea chatBox;
	
	private static Thread threadClient;
	private JTextField msgBox;
	
	private static ClientGame dialog;
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new ClientGame();
			
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
					GameManager.getClient().shutdown();
					MainWindow.setVisible(true);
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
		String err = GameManager.initClient(IpPortDialog.ip, IpPortDialog.port, NicknameDialog.nickname);
		if(!err.equals("true")) {
			utils.AlertClass.showErrBox(null, "Connection Error", err);
			return false;
		}

		return true;
	}
	
	public void failedHandleConnection() {
		GameManager.getClient().shutdown();
		dispose();
		MainWindow.setVisible(true);
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
		
		nickServerLabel.setVisible(b);
		nickServerLabel.setText(GameManager.getNickServer());
	}
	
	/**
	 * Create the dialog.
	 */
	public ClientGame() {
		setTitle("TicTacToe  [client]");
		setResizable(false);
		setAutoRequestFocus(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGame.class.getResource("/ui/resources/icon.png")));
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
		
		lblNewLabel_1 = new JLabel("You");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblNewLabel_1.setForeground(new Color(235, 235, 235));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(44, 37, 58, 39);
		contentPanel.add(lblNewLabel_1);
		
		nickServerLabel = new JLabel(GameManager.getNickClient());
		nickServerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nickServerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nickServerLabel.setForeground(new Color(235, 235, 235));
		nickServerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		nickServerLabel.setBounds(416, 37, 127, 39);
		contentPanel.add(nickServerLabel);
		
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
		
		chatBox = new JTextArea();
		chatBox.setBorder(new LineBorder(new Color(10, 34, 46), 3, true));
		chatBox.setBackground(new Color(15, 55, 77));
		chatBox.setBounds(308, 124, 235, 174);
		contentPanel.add(chatBox);
		
		msgBox = new JTextField();
		msgBox.setCaretColor(new Color(218, 218, 218));
		msgBox.setHorizontalAlignment(SwingConstants.CENTER);
		msgBox.setBorder(new LineBorder(new Color(15, 34, 46), 3, true));
		msgBox.setBackground(new Color(15, 55, 77));
		msgBox.setBounds(337, 309, 181, 35);
		contentPanel.add(msgBox);
		msgBox.setColumns(10);
		
		JLabel playLabel0_0 = new JLabel("");
		playLabel0_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0_0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0_0.setForeground(new Color(235, 235, 235));
		playLabel0_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0_0.getText().isEmpty()) {
					return;
				}
				
				playLabel0_0.setText(GameManager.getClientShape());
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
		playLabel0_0.setBounds(47, 126, 60, 52);
		contentPanel.add(playLabel0_0);
		
		JLabel playLabel0_1 = new JLabel("");
		playLabel0_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0_1.setForeground(new Color(235, 235, 235));
		playLabel0_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0_1.getText().isEmpty()) {
					return;
				}
				playLabel0_1.setText(GameManager.getClientShape());
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
		
		JLabel playLabel0_2 = new JLabel("");
		playLabel0_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel0_2.setForeground(new Color(235, 235, 235));
		playLabel0_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel0_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel0_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel0_2.getText().isEmpty()) {
					return;
				}
				playLabel0_2.setText(GameManager.getClientShape());
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
		
		JLabel playLabel1_2 = new JLabel("");
		playLabel1_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_2.setForeground(new Color(235, 235, 235));
		playLabel1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_2.getText().isEmpty()) {
					return;
				}
				playLabel1_2.setText(GameManager.getClientShape());
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
		
		JLabel playLabel1_0 = new JLabel("");
		playLabel1_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_0.setForeground(new Color(235, 235, 235));
		playLabel1_0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_0.getText().isEmpty()) {
					return;
				}
				playLabel1_0.setText(GameManager.getClientShape());
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
		
		JLabel playLabel1_1 = new JLabel("");
		playLabel1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel1_1.setForeground(new Color(235, 235, 235));
		playLabel1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel1_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel1_1.getText().isEmpty()) {
					return;
				}
				playLabel1_1.setText(GameManager.getClientShape());
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
		
		JLabel playLabel2_0 = new JLabel("");
		playLabel2_0.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_0.setForeground(new Color(235, 235, 235));
		playLabel2_0.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_0.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_0.getText().isEmpty()) {
					return;
				}
				playLabel2_0.setText(GameManager.getClientShape());
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
		
		JLabel playLabel2_1 = new JLabel("");
		playLabel2_1.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_1.setForeground(new Color(235, 235, 235));
		playLabel2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_1.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_1.getText().isEmpty()) {
					return;
				}
				playLabel2_1.setText(GameManager.getClientShape());
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
		
		JLabel playLabel2_2 = new JLabel("");
		playLabel2_2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		playLabel2_2.setForeground(new Color(235, 235, 235));
		playLabel2_2.setHorizontalTextPosition(SwingConstants.CENTER);
		playLabel2_2.setHorizontalAlignment(SwingConstants.CENTER);
		playLabel2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!playLabel2_2.getText().isEmpty()) {
					return;
				}
				playLabel2_2.setText(GameManager.getClientShape());
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
		
		/* Hide all the components */
		makeThingsVisible(false);
		/* start the thread to listen a client connection */
		threadClient = new Thread() {
			public void run() {
				if(!handleConnection()) {
					failedHandleConnection();
					return;
				}
				makeThingsVisible(true);
				GameUtils.setTurnsColors(lblNewLabel_1, nickServerLabel, Colors.whiteSmoke, Colors.green);
			}
		};
		threadClient.start();
	}
}
