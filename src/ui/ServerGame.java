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
	
	/**
	 * Create the dialog.
	 */
	public ServerGame() {
		setTitle("TicTacToe  [server]");
		setResizable(false);
		setAutoRequestFocus(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServerGame.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		waitingLabel.setBounds(44, 80, 348, 58);
		contentPanel.add(waitingLabel);
		
		threadServer = new Thread() {
			public void run() {
				if(!handleConnection()) {
					failedHandleConnection();
					return;
				}
				
				afterStart();
			}
		};
		threadServer.start();
	}
	
	public void afterStart() {
		waitingLabel.setVisible(false);
	}
}
