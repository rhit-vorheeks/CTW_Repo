package GUI;

import java.awt.BorderLayout;
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

/**
 * Page shown to gather more information about the player during the
 * registration process.
 *
 */
public class PlayerRegisterPage extends AbstractPage {
	// frame
	JFrame frame = this.getFrame();

	// panel
	JPanel textPanel = new JPanel();
	JPanel masterPanel = new JPanel();
	JPanel FNamePanel = new JPanel();
	JPanel LNamePanel = new JPanel();
	JPanel DOBPanel = new JPanel();
	JPanel heightPanel = new JPanel();
	JPanel weightPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	// Label
	JLabel promptLabel = new JLabel("Please enter Player Information:");
	JLabel firstName = new JLabel("First Name:	");
	JLabel lastName = new JLabel("Last Name:	");
	JLabel DOB = new JLabel("DOB:	");
	JLabel height = new JLabel("Height:	");
	JLabel weight = new JLabel("Weight:	");

	// Text Field
	JTextField firstNameTF = new JTextField();
	JTextField lastNameTF = new JTextField();
	JTextField DOBTF = new JTextField("MM-DD-YYYY");
	JTextField heightTF = new JTextField();
	JTextField weightTF = new JTextField();

	// Button
	JButton registerButton = new JButton("Register");
	JButton backButton = new JButton("Back");

	// Data
	String FNameValue = null;
	String LNameValue = null;
	String DOBValue = null;
	int heightValue = -1;
	int weightValue = -1;
	private String usernameValue;
	private String passwordValue;

	private DatabaseConnectionService connection;
	UserService userService = null;
	
	// Pages
	private RegisterPage registerPage;

	/**
	 * Constructs a PlayerRegister page
	 * 
	 * @param frame
	 */
	public PlayerRegisterPage(JFrame frame, DatabaseConnectionService connection) {
		super(frame);

		Dimension TFSize = new Dimension(150, 25);
		firstNameTF.setMaximumSize(TFSize);
		lastNameTF.setMaximumSize(TFSize);
		DOBTF.setMaximumSize(TFSize);
		heightTF.setMaximumSize(TFSize);
		weightTF.setMaximumSize(TFSize);

		this.connection = connection;
		userService = new UserService(this.connection);

		onRegisterButtonClick();
		this.onBackButtonClick();
	}

	public void savePages(RegisterPage regPage) {
		this.registerPage = regPage;
	}
	
	public void show() {
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

		FNamePanel.add(firstName);
		FNamePanel.add(firstNameTF);
		FNamePanel.setLayout(new BoxLayout(FNamePanel, BoxLayout.X_AXIS));

		LNamePanel.add(lastName);
		LNamePanel.add(lastNameTF);
		LNamePanel.setLayout(new BoxLayout(LNamePanel, BoxLayout.X_AXIS));

		DOBPanel.add(DOB);
		DOBPanel.add(DOBTF);
		DOBPanel.setLayout(new BoxLayout(DOBPanel, BoxLayout.X_AXIS));

		heightPanel.add(height);
		heightPanel.add(heightTF);
		heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));

		heightPanel.add(height);
		heightPanel.add(heightTF);
		heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));

		weightPanel.add(weight);
		weightPanel.add(weightTF);
		weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.X_AXIS));

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(backButton);
		buttonPanel.add(registerButton);

		textPanel.add(promptLabel, BorderLayout.WEST);
		textPanel.setMaximumSize(new Dimension(200, 50));

		masterPanel.add(textPanel, BorderLayout.CENTER);
		masterPanel.add(FNamePanel, BorderLayout.CENTER);
		masterPanel.add(LNamePanel, BorderLayout.CENTER);
		masterPanel.add(DOBPanel, BorderLayout.CENTER);
		masterPanel.add(heightPanel, BorderLayout.CENTER);
		masterPanel.add(weightPanel, BorderLayout.CENTER);

		masterPanel.add(buttonPanel, BorderLayout.EAST);

		frame.add(masterPanel);

		frame.setVisible(true);

	}

	public void getDataFromTF() {
		FNameValue = firstNameTF.getText();
		LNameValue = lastNameTF.getText();
		DOBValue = DOBTF.getText();
		try {
		heightValue = Integer.parseInt(heightTF.getText());
		weightValue = Integer.parseInt(weightTF.getText());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Not a valid height or weight.");
		}
	}

	public void onRegisterButtonClick() {
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getDataFromTF();
//				System.out.printf("%s %s %s %d %d %s %s", FNameValue, LNameValue, DOBValue, heightValue, weightValue,
//						usernameValue, passwordValue);

				if (userService.register(usernameValue, passwordValue, FNameValue, LNameValue, DOBValue, usernameValue,
						"Player")) {
					addPlayer(usernameValue, heightValue, weightValue);
					clear();
					
				}

			}

		});

	}
	
	public void onBackButtonClick() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("GO BACK");
				clear();
				registerPage.show();
			
			}

		});

	}


	public void saveUserPass(String name, String pass) {
		this.usernameValue = name;
		this.passwordValue = pass;
	}
	
	public boolean addPlayer(String username, int height, int weight) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call AddPlayer(?, ?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setInt(3, height);
			stmt.setInt(4, weight);

			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		
		}
		return true;
	}

}
