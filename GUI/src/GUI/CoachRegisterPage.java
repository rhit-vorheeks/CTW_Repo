package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Page shown to gather more information about the coach during the registration
 * process.
 *
 */
public class CoachRegisterPage extends AbstractPage {

	// Panels
	private JPanel textPanel = new JPanel();
	private JPanel masterPanel = new JPanel();
	private JPanel FNamePanel = new JPanel();
	private JPanel LNamePanel = new JPanel();
	private JPanel DOBPanel = new JPanel();
	private JPanel typePanel = new JPanel();

	// Labels
	private JLabel promptLabel = new JLabel("Please enter Coach Information:");
	private JLabel firstName = new JLabel("First Name:	");
	private JLabel lastName = new JLabel("Last Name:	");
	private JLabel DOB = new JLabel("DOB:	");
	private JLabel type = new JLabel("Type:	");

	// Text Fields
	private JTextField firstNameTF = new JTextField();
	private JTextField lastNameTF = new JTextField();
	private JTextField DOBTF = new JTextField("MM-DD-YYYY");
	private JTextField typeTF = new JTextField();

	// Button
	JButton registerButton = new JButton("Register");

	// Data
	String FNameValue = null;
	String LNameValue = null;
	String DOBValue = null;
	String typeValue = null;
	private String usernameValue;
	private String passwordValue;

	private DatabaseConnectionService connection;
	UserService userService = null;

	/**
	 * Constructs a page that is used to register a coach.
	 * 
	 * @param frame
	 */
	public CoachRegisterPage(JFrame frame, DatabaseConnectionService connection) {
		super(frame);
		this.connection = connection;
		userService = new UserService(this.connection);
		onRegisterButtonClick();

		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

		Dimension TFSize = new Dimension(150, 25);
		firstNameTF.setMaximumSize(TFSize);
		lastNameTF.setMaximumSize(TFSize);
		DOBTF.setMaximumSize(TFSize);
		typeTF.setMaximumSize(TFSize);

	}

	public void show() {
		JFrame frame = this.getFrame();

		FNamePanel.add(firstName);
		FNamePanel.add(firstNameTF);
		FNamePanel.setLayout(new BoxLayout(FNamePanel, BoxLayout.X_AXIS));

		LNamePanel.add(lastName);
		LNamePanel.add(lastNameTF);
		LNamePanel.setLayout(new BoxLayout(LNamePanel, BoxLayout.X_AXIS));

		DOBPanel.add(DOB);
		DOBPanel.add(DOBTF);
		DOBPanel.setLayout(new BoxLayout(DOBPanel, BoxLayout.X_AXIS));

		typePanel.add(type);
		typePanel.add(typeTF);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));

		textPanel.add(promptLabel, BorderLayout.WEST);
		textPanel.setMaximumSize(new Dimension(200, 50));

		masterPanel.add(textPanel, BorderLayout.CENTER);
		masterPanel.add(FNamePanel, BorderLayout.CENTER);
		masterPanel.add(LNamePanel, BorderLayout.CENTER);
		masterPanel.add(DOBPanel, BorderLayout.CENTER);
		masterPanel.add(typePanel, BorderLayout.CENTER);

		masterPanel.add(registerButton, BorderLayout.EAST);

		frame.add(masterPanel);
		frame.setVisible(true);
	}

	public void getDataFromTF() {
		FNameValue = firstNameTF.getText();
		LNameValue = lastNameTF.getText();
		DOBValue = DOBTF.getText();
		typeValue = typeTF.getText();

	}

	public void onRegisterButtonClick() {
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getDataFromTF();
				System.out.printf("%s %s %s %s %s %s", FNameValue, LNameValue, DOBValue, typeValue, usernameValue,
						passwordValue);

				if (userService.register(usernameValue, passwordValue, FNameValue, LNameValue, DOBValue, usernameValue,
						"Coach")) {
					clear();
				}

			}

		});

	}

	public void saveUserPass(String name, String pass) {
		this.usernameValue = name;
		this.passwordValue = pass;
	}

}
