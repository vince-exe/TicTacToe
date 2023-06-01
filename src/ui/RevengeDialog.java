package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JLabel;

import utils.GameUtils;
import main.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class RevengeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private static GameUtils.GameStatus status;
	private static boolean isHosting;
	private static ArrayList<Point> coordinates;
	
	public static boolean ok;
	
	private JLabel titleLabel;
	/**
	 * Launch the application.
	 */
	public static void main(GameUtils.GameStatus gameStatus, boolean isHosting_, ArrayList<Point> coordinates_) {
		try {
			ok = false;
			status = gameStatus;
			isHosting = isHosting_;
			coordinates = coordinates_;
			
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
	
			}
		});
		
		setAlwaysOnTop(true);
		setTitle("Tic-Tac-Toe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RevengeDialog.class.getResource("/ui/resources/icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		
		if(status == GameUtils.GameStatus.WON) {
			titleLabel.setText("Hai Vinto!");
		}
		else if(status == GameUtils.GameStatus.LOST) {
			titleLabel.setText("Hai Perso!");
		}
		else {
			titleLabel.setText("Pareggio!");
		}
		
		if(isHosting) {
			this.setTitle("Tic-Tac-Toe [server]");
		}
		else {
			this.setTitle("Tic-Tac-Toe [client]");
		}
	}
}
