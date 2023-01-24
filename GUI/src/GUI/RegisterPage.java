package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RegisterPage {
	
	public final Dimension SCREEN_SIZE = new Dimension(750, 600);
	private JFrame frame;
	private JPanel textPanel = new JPanel();
	private JPanel dropDownPanel = new JPanel();
	private JPanel masterPanel = new JPanel();
	private JLabel promptLabel = new JLabel("Are you a player or a coach?");
	private JButton continueButton = new JButton("Continue");
	private String[]options = {"", "Player", "Coach" };
	private JComboBox<String> drop = new JComboBox<>(options);

	public RegisterPage(JFrame frame) {
		this.frame = frame;
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public boolean show() {
		
		textPanel.add(promptLabel, BorderLayout.CENTER);
		textPanel.add(drop, BorderLayout.CENTER);
		textPanel.setMaximumSize(new Dimension(900, 300));

		dropDownPanel.add(continueButton, BorderLayout.NORTH);

		masterPanel.add(textPanel, BorderLayout.SOUTH);
		masterPanel.add(dropDownPanel, BorderLayout.CENTER);

		frame.add(masterPanel);

		frame.setSize(SCREEN_SIZE);
		frame.setTitle("Circle The Wagons");

		// 5. Show it.
		frame.setVisible(true);
		return true;
	}
	
	public boolean hide() {
		frame.setVisible(false);
		return true;
	}
	
	public void clear() {
		frame.getContentPane().removeAll();
		frame.repaint();
	}

}
