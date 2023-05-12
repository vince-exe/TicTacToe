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

public class ClientGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	JLabel waitingLabel;
	JLabel titleLabel;
	JLabel lblNewLabel_1;
	JLabel nickServerLabel;
	
	private static Thread threadClient;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientGame dialog = new ClientGame();
			
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
		
		nickServerLabel = new JLabel(GameManager.getNickServer());
		nickServerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nickServerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nickServerLabel.setForeground(new Color(235, 235, 235));
		nickServerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		nickServerLabel.setBounds(408, 37, 135, 39);
		contentPanel.add(nickServerLabel);
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		waitingLabel.setBounds(111, 145, 348, 58);
		contentPanel.add(waitingLabel);
		
		/* Hide all the components */
		makeThingsVisible(false);
		threadClient = new Thread() {
			public void run() {
				if(!handleConnection()) {
					failedHandleConnection();
					return;
				}
				makeThingsVisible(true);
				GameUtils.setTurnsColors(lblNewLabel_1, nickServerLabel, Colors.green, Colors.whiteSmoke);
			}
		};
		threadClient.start();
	}
}
