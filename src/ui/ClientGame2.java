package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameManager;
import utils.AlertClass;
import utils.Colors;
import utils.GameUtils;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ClientGame2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private static Thread threadClient;
	private JLabel label;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientGame2 dialog = new ClientGame2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void failedHandleConnection() {
		GameManager.getClient().shutdown();
		dispose();
		MainWindow.setVisible(true);
	}
	
	private boolean handleConnection() {
		String err = GameManager.initClient(IpPortDialog.ip, IpPortDialog.port, NicknameDialog.nickname);
		if(!err.equals("true")) {
			utils.AlertClass.showErrBox(null, "Connection Error", err);
			return false;
		}

		return true;
	}
	
	/**
	 * Create the dialog.
	 */
	public ClientGame2() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			label = new JLabel("");
			label.setOpaque(true);
			label.setBackground(new Color(0, 255, 0));
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!label.getText().isEmpty()) {
						return;
					}
					label.setText(GameManager.getClientShape());
				}
			});
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
			label.setForeground(new Color(0, 0, 128));
			label.setBounds(68, 41, 215, 125);
			contentPanel.add(label);
			
			threadClient = new Thread() {
				public void run() {
					if(!handleConnection()) {
						failedHandleConnection();
						return;
					}
					/* get the shape of the client */
					try {
						GameManager.setClientShape(GameManager.getClient().read());
					} catch (IOException e) {
						AlertClass.showErrBox(null, "Connection Error", e.getMessage());
						failedHandleConnection();
						return;
					}
					System.out.print("\nClient Shape: " + GameManager.getClientShape());
				}
			};
			
			threadClient.start();
		}
	}

}
