package ui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameManager;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ServerGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	JLabel waitingLabel;
	JLabel titleLabel;
	JLabel youLabelTurn;
	JLabel lblNewLabel_1;
	JLabel clientLabelTurn;
	JLabel nickClientLabel;
	
	private static Thread threadServer;
	
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
		youLabelTurn.setVisible(b);
		lblNewLabel_1.setVisible(b);
		clientLabelTurn.setVisible(b);
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
		titleLabel.setBounds(141, 21, 274, 61);
		contentPanel.add(titleLabel);
		
		youLabelTurn = new JLabel("");
		youLabelTurn.setBounds(34, 105, 37, 35);
		youLabelTurn.setBackground(utils.Colors.green);
		contentPanel.add(youLabelTurn);
		
		lblNewLabel_1 = new JLabel("You");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblNewLabel_1.setForeground(new Color(235, 235, 235));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(101, 103, 58, 39);
		contentPanel.add(lblNewLabel_1);
		
		clientLabelTurn = new JLabel("");
		clientLabelTurn.setBackground(new Color(2, 125, 15));
		clientLabelTurn.setBounds(490, 105, 37, 35);
		clientLabelTurn.setBackground(utils.Colors.red);
		contentPanel.add(clientLabelTurn);
		
		nickClientLabel = new JLabel(GameManager.getNickClient());
		nickClientLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nickClientLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nickClientLabel.setForeground(new Color(235, 235, 235));
		nickClientLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		nickClientLabel.setBounds(328, 103, 135, 39);
		contentPanel.add(nickClientLabel);
		
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
				System.out.print("\nNick Client: " + GameManager.getNickClient());
			}
		};
		threadServer.start();
	}
}
