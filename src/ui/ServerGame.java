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

public class ServerGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	JLabel waitingLabel;
	JLabel titleLabel;
	JLabel lblNewLabel_1;
	JLabel nickClientLabel;
	JLabel row1;
	JLabel row2;
	JLabel row3;
	JLabel row4;
	JTextArea chatBox;
	
	private static Thread threadServer;
	private JTextField msgBox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ServerGame dialog = new ServerGame();
			
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
					GameManager.getServer().shutdown();
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
		String err = GameManager.initServer(IpPortDialog.ip, IpPortDialog.port, NicknameDialog.nickname);
		if(!err.equals("true")) {
			utils.AlertClass.showErrBox(null, "Connection Error", err);
			return false;
		}

		return true;
	}
	
	public void failedHandleConnection() {
		GameManager.getServer().shutdown();
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
		
		nickClientLabel.setVisible(b);
		nickClientLabel.setText(GameManager.getNickClient());
	}
	
	/**
	 * Create the dialog.
	 */
	public ServerGame() {
		setTitle("TicTacToe  [server]");
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
		
		lblNewLabel_1 = new JLabel("You");
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
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		waitingLabel.setBounds(111, 145, 348, 58);
		contentPanel.add(waitingLabel);
		
		/* Hide all the components */
		makeThingsVisible(false);
		threadServer = new Thread() {
			public void run() {
				if(!handleConnection()) {
					failedHandleConnection();
					return;
				}
				makeThingsVisible(true);
				GameUtils.setTurnsColors(lblNewLabel_1, nickClientLabel, Colors.green, Colors.whiteSmoke);
			}
		};
		threadServer.start();
	}
}
