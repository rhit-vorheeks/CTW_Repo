package GUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

/**
 * Page used to Log into the system.
 *
 */
public class LoginPage extends AbstractPage {

	// Panels
	private JPanel loginButtonPanel;
	private JPanel loginDataPanel;
	private JPanel masterPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;

	// Labels
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	// Text Fields
	private JTextField usernameTextBox;
	private JTextField passwordTextBox;
	
	// Buttons
	private JButton loginButton;
	private JButton registerButton;
	
	// Page references
	private RegisterPage regPage;
	
	/**
	 * Creates a LoginPage so the user can log in
	 * @param frame
	 * @param regPage
	 */
	public LoginPage(JFrame frame, RegisterPage regPage) {
		super(frame);
		
		this.regPage = regPage;
		loginButtonPanel = new JPanel();
		loginDataPanel = new JPanel();
		masterPanel = new JPanel();
		usernamePanel = new JPanel();
		passwordPanel = new JPanel();	
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		loginDataPanel.setLayout(new BoxLayout(loginDataPanel, BoxLayout.Y_AXIS));
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		
		usernameLabel = new JLabel(" Username: ");
		passwordLabel = new JLabel("Password:  ");
		usernameTextBox = new JTextField();
		passwordTextBox = new JTextField();
		loginButton = new JButton("Login");
		
		// Setup Button
		registerButton = new JButton("Register");
		this.onRegisterButtonClick();
		
		usernameTextBox.setMaximumSize(new Dimension(200, 25));
		passwordTextBox.setMaximumSize(new Dimension(200, 25));
	}
	
	public void show() {
		JFrame frame = this.getFrame();

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

		frame.setVisible(true);
	}
	
	public void onRegisterButtonClick() {
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Register");
				clear();
				regPage.show();
				
			} 			
			
		});
		
	}
	
	
	
}
