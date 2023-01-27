package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	// values
	private String usernameValue;
	private String passwordValue;

	// connection

	UserService userService = null;
	DatabaseConnectionService connection = null;
	private CoachHomePage homePage = null;

	
	// account handler
	private AccountHandler acct;
	
	/**
	 * Creates a LoginPage so the user can log in
	 * 
	 * @param frame
	 * @param regPage
	 */
	public LoginPage(JFrame frame, RegisterPage regPage, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		
		this.acct = acct;

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

		// Setup Button
		registerButton = new JButton("Register");
		this.onRegisterButtonClick();

		loginButton = new JButton("Login");
		this.onLoginButtonClick();

		usernameTextBox.setMaximumSize(new Dimension(200, 25));
		passwordTextBox.setMaximumSize(new Dimension(200, 25));

		this.connection = connection;
		userService = new UserService(this.connection);

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
	
	public void savePages(CoachHomePage coachHomePage) {
		this.homePage   = coachHomePage;
	
	}

	public void onRegisterButtonClick() {
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Register");
				getData();
				System.out.printf("%s %s ", usernameValue, passwordValue);
				
				if(!validateSubmit(usernameValue)) {
					return;
				}
				
				regPage.saveUserPass(usernameValue, passwordValue);
				acct.setName(usernameValue);
				clear();
				regPage.show();

			}

		});
	}

	public void onLoginButtonClick() {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Clicked Login");
				getData();
				System.out.printf("%s %s \n", usernameValue, passwordValue);

				// Determine if coach or player and then direct to appropriate home screen
				String typeValue = userService.login(usernameValue, passwordValue);
				System.out.println(typeValue);
				acct.setName(usernameValue);
				
				clear();

				if (typeValue.equals("Player")) {
					//switch the page
				} else if (typeValue.equals("Coach")) {
					//switch the page
					homePage.show();
				}

			}

		});

	}
	
	public boolean validateSubmit(String username) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call CheckRegister(?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
		
		}
		if (returnValue == 0) {
			System.out.println("Success");
			return true;
		} else {
			if (returnValue == 1) {
				mess = "ERROR: Username name cannot be empty";
			} else if (returnValue == 2) {
				mess = "ERROR: Username name already exists.";
			}
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, mess);
			return false;
		}
	}

	public void getData() {
		usernameValue = usernameTextBox.getText();
		passwordValue = passwordTextBox.getText();
	}

}
