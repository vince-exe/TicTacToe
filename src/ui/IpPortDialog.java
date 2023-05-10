package ui;

import network.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class IpPortDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField ipBox;
	private JTextField portBox;
	
	public static boolean success;
	public static int port;
	public static String ip;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IpPortDialog.success = false;
			
			IpPortDialog dialog = new IpPortDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setModalityType(DEFAULT_MODALITY_TYPE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkClient() {
		try {
			IpPortDialog.ip = ipBox.getText();
			IpPortDialog.port = Integer.parseInt(portBox.getText());
			
			Client client = new Client(IpPortDialog.ip, IpPortDialog.port);
			
			client.connect(15000);
			client.shutdown();
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkServer() {
		try {
			IpPortDialog.port = Integer.parseInt(portBox.getText());
			IpPortDialog.ip = ipBox.getText();
			
			/* test */
			System.out.print("\nIp: " + IpPortDialog.ip);
			System.out.print("\nPort: " + IpPortDialog.port);
			
			final Server server = new Server(IpPortDialog.ip, IpPortDialog.port);
			
			Thread thread = new Thread() {
				public void run() {
					try {
						server.accept(15000);
						IpPortDialog.success = true;
					} 
					catch (IOException e) {
						IpPortDialog.success = false;
					}
				}
			};
			thread.start();
			
			/* try to connect at the server (localhost) */
			Client client = new Client("127.0.0.1", IpPortDialog.port);
			client.connect(15000);
			
			thread.join();
			server.shutdown();
			client.shutdown();
			
			return IpPortDialog.success;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public IpPortDialog() {
		setTitle("Network");
		setIconImage(Toolkit.getDefaultToolkit().getImage(IpPortDialog.class.getResource("/ui/resources/icon.png")));
		setBounds(100, 100, 308, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		ipBox = new JTextField("Ip Address", 20);
		ipBox.setHorizontalAlignment(SwingConstants.CENTER);
		ipBox.setForeground(new Color(235, 235, 235));
		ipBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		ipBox.setCaretColor(new Color(235, 235, 235));
		ipBox.setBorder(new LineBorder(new Color(2, 21, 31), 4));
		ipBox.setBackground(new Color(15, 49, 66));
		ipBox.setBounds(40, 73, 208, 50);
		contentPanel.add(ipBox);
		
		portBox = new JTextField("Port", 20);
		portBox.setHorizontalAlignment(SwingConstants.CENTER);
		portBox.setForeground(new Color(235, 235, 235));
		portBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		portBox.setCaretColor(new Color(235, 235, 235));
		portBox.setBorder(new LineBorder(new Color(2, 21, 31), 4));
		portBox.setBackground(new Color(15, 49, 66));
		portBox.setBounds(40, 147, 208, 50);
		contentPanel.add(portBox);
		
		JButton doneBtn = new JButton("Done");
		doneBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				doneBtn.setOpaque(true);
				doneBtn.setBackground(new Color(11, 38, 51));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				doneBtn.setOpaque(true);
				doneBtn.setBackground(new Color(15, 49, 66));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(MainWindow.isHosting) {
					IpPortDialog.success = checkServer();
					
					if(IpPortDialog.success) {
						utils.AlertClass.showMsgBox(null, "Success", "Successfully started the server");
					}
					else {
						utils.AlertClass.showErrBox(null, "Hosting Error", "The software failed to host the game..");
					}
				}
				else {
					IpPortDialog.success = checkClient();
					
					if(IpPortDialog.success) {
						utils.AlertClass.showMsgBox(null, "Success", "Successfully connected to the server");
					}
					else {
						utils.AlertClass.showErrBox(null, "Hosting Error", "Can't connect to the server");
					}
				}
				
				dispose();
			}
		});
		doneBtn.setForeground(new Color(235, 235, 235));
		doneBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		doneBtn.setText("Test");
		doneBtn.setFocusPainted(false);
		doneBtn.setContentAreaFilled(false);
		doneBtn.setBorder(new LineBorder(new Color(2, 21, 31), 4, true));
		doneBtn.setBackground(new Color(15, 49, 66));
		doneBtn.setBounds(82, 221, 129, 40);
		contentPanel.add(doneBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Copyright © 2023 Vincenzo Caliendo. All rights reserved");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(193, 193, 193));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(10, 275, 267, 18);
		contentPanel.add(lblNewLabel_1);
		
		JLabel titleLabel = new JLabel("Host");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(235, 235, 235));
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 29));
		titleLabel.setBounds(10, 10, 274, 52);
		contentPanel.add(titleLabel);
		
		if(MainWindow.isHosting) {
			titleLabel.setText("Host");
			ipBox.setText("0.0.0.0");
			ipBox.setEditable(false);
		}
	}
}
