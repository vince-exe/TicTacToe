package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import utils.*;

@SuppressWarnings("serial")
public class NicknameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	public static String nickname;
	
	public static boolean doneBtnClicked;
	
	private final int MIN_LEN_NICKNAME = 2;
	private final int MAX_LEN_NICKNAME = 15;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			doneBtnClicked = false;
			NicknameDialog dialog = new NicknameDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(DEFAULT_MODALITY_TYPE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NicknameDialog() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Nickname Dialog");
		setIconImage(Toolkit.getDefaultToolkit().getImage(NicknameDialog.class.getResource("/ui/resources/icon.png")));
		setBounds(100, 100, 308, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChooseNickname = new JLabel("Choose Nickname");
		lblChooseNickname.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseNickname.setForeground(new Color(235, 235, 235));
		lblChooseNickname.setFont(new Font("Comic Sans MS", Font.BOLD, 29));
		lblChooseNickname.setBounds(10, 11, 274, 52);
		contentPanel.add(lblChooseNickname);
		
		JLabel lblNewLabel_1 = new JLabel("Copyright © 2023 Vincenzo Caliendo. All rights reserved");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(193, 193, 193));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(10, 232, 267, 18);
		contentPanel.add(lblNewLabel_1);
		
		JTextField nicknameBox = new JTextField("nickname", 20);
		nicknameBox.setCaretColor(new Color(235, 235, 235));
		nicknameBox.setForeground(new Color(235, 235, 235));
		nicknameBox.setBackground(new Color(15, 49, 66));
		nicknameBox.setBorder(new LineBorder(new Color(2, 21, 31), 4));
		nicknameBox.setHorizontalAlignment(JTextField.CENTER);
		nicknameBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		nicknameBox.setBounds(44, 82, 208, 50);
		contentPanel.add(nicknameBox);
		
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
				String nickname_ = nicknameBox.getText();
				if(nickname_.length() < MIN_LEN_NICKNAME || nickname_.length() > MAX_LEN_NICKNAME) { 
					AlertClass.showWarningBox(null, "Invalid Nickname", "Nickname must be > " + MIN_LEN_NICKNAME + " and < " + MAX_LEN_NICKNAME);
				}
				else {
					nickname = nickname_;
					doneBtnClicked = true;
					AlertClass.showMsgBox(null, "Success", "Successfully saved the nickname");
					dispose();
				}
			}
		});
		doneBtn.setForeground(new Color(235, 235, 235));
		doneBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		doneBtn.setFocusPainted(false);
		doneBtn.setContentAreaFilled(false);
		doneBtn.setBorder(new LineBorder(new Color(2, 21, 31), 6, true));
		doneBtn.setBackground(new Color(15, 49, 66));
		doneBtn.setBounds(64, 156, 165, 52);
		contentPanel.add(doneBtn);
	}
}
