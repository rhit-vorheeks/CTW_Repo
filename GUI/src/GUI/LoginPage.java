package GUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

public class LoginPage {

	public final Dimension SCREEN_SIZE = new Dimension(750, 600);
	private JFrame frame;
	private JPanel loginButtonPanel;
	private JPanel loginDataPanel;
	private JPanel masterPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JButton registerButton;
	private JButton loginButton;
	private JLabel usernameLabel

	public LoginPage(JFrame frame) {
		this.frame = frame;
		loginButtonPanel = new JPanel();
		loginDataPanel = new JPanel();
		masterPanel = new JPanel();
		usernamePanel = new JPanel();
		passwordPanel = new JPanel();	
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		loginDataPanel.setLayout(new BoxLayout(loginDataPanel, BoxLayout.Y_AXIS));
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		
		
		JLabel usernameLabel = new JLabel(" Username: ");
		JLabel passwordLabel = new JLabel("Password:  ");
		JTextField usernameTextBox = new JTextField();
		JTextField passwordTextBox = new JTextField();
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");
		
		usernameTextBox.setMaximumSize(new Dimension(200, 25));
		passwordTextBox.setMaximumSize(new Dimension(200, 25));
	}
	
	public boolean show() {

		loginButtonPanel.add(loginButton, BorderLayout.CENTER);
		loginButtonPanel.add(registerButton, BorderLayout.CENTER);

		usernamePanel.add(usernameLabel, BorderLayout.CENTER);
		usernamePanel.add(usernameTextBox, BorderLayout.CENTER);

		passwordPanel.add(passwordLabel, BorderLayout.CENTER);
		passwordPanel.add(passwordTextBox, BorderLayout.CENTER);

		loginDataPanel.add(usernamePanel, BorderLayout.SOUTH);
		loginDataPanel.add(passwordPanel, BorderLayout.SOUTH);

		masterPanel.add(loginDataPanel, BorderLayout.CENTER);
		masterPanel.add(loginButtonPanel, BorderLayout.CENTER);

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
