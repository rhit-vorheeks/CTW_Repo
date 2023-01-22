import java.awt.*;
import javax.swing.*;

public class main {
	public static final Dimension SCREEN_SIZE = new Dimension(750, 600);

	public static void createLoginPage(JFrame frame) {
//		/1. Create the frame.
		JPanel loginButtonPanel = new JPanel();
		JPanel loginDataPanel = new JPanel();
		JPanel masterPanel = new JPanel();
		JPanel usernamePanel = new JPanel();
		JPanel passwordPanel = new JPanel();

		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		loginDataPanel.setLayout(new BoxLayout(loginDataPanel, BoxLayout.Y_AXIS));
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel usernameLabel = new JLabel(" Username: ");
		JLabel passwordLabel = new JLabel("Password:  ");
		JTextField usernameTextBox = new JTextField();
		JTextField passwordTextBox = new JTextField();
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");

		usernameTextBox.setMaximumSize(new Dimension(200, 25));
		passwordTextBox.setMaximumSize(new Dimension(200, 25));

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
		frame.setTitle("Circle The Wagon");

		// 5. Show it.
		frame.setVisible(true);

	}

	public static void main(String[] args) {
//		/1. Create the frame.
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle("Circle The Wagon");

		createLoginPage(frame);
		
		
		frame.setVisible(true);

	}

}
