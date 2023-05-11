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
import java.awt.Font;
import javax.swing.SwingConstants;

public class ClientGame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Thread threadClient;
	
	private final JPanel contentPanel = new JPanel();
	JLabel waitingLabel;
	
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
	
	public ClientGame() {
		setResizable(false);
		setTitle("TicTacToe  [client]");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGame.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		waitingLabel = new JLabel("Waiting for a connection..");
		waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waitingLabel.setForeground(new Color(233, 233, 233));
		waitingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		waitingLabel.setBounds(44, 80, 348, 58);
		contentPanel.add(waitingLabel);
		
		threadClient = new Thread() {
			public void run() {
				if(!handleConnection()) {
					failedHandleConnection();
					return;
				}
				
				afterStart();
			}
		};
		threadClient.start();
	}

	public void afterStart() {
		waitingLabel.setVisible(false);
	}

}
