package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;

public class RevengeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private static boolean hasWon;
	private static boolean isHosting;
	
	private JLabel titleLabel;
	/**
	 * Launch the application.
	 */
	public static void main(boolean hasWon_, boolean isHosting_) {
		try {
			hasWon = hasWon_;
			isHosting = isHosting_;
			
			RevengeDialog dialog = new RevengeDialog();
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
	public RevengeDialog() {
		setTitle("Tic-Tac-Toe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RevengeDialog.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAutoRequestFocus(false);
		setBounds(100, 100, 548, 395);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(15, 49, 66));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		titleLabel = new JLabel("Tic-Tac-Toe");
		titleLabel.setBounds(134, 23, 245, 61);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(235, 235, 235));
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
		contentPanel.add(titleLabel);
		
		if(hasWon) {
			titleLabel.setText("Hai Vinto!");
		}
		else {
			titleLabel.setText("Hai Perso!");
		}
	}
}
