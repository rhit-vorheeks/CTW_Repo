package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Page shown to gather more information about the player during the registration process.
 *
 */
public class PlayerRegisterPage extends AbstractPage {

	/**
	 * Constructs a PlayerRegister page
	 * @param frame
	 */
	public PlayerRegisterPage(JFrame frame) {
		super(frame);
	}
	
	public void show() {
		JFrame frame = this.getFrame();
		
		JPanel textPanel = new JPanel();
		JPanel masterPanel = new JPanel();

		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		JLabel promptLabel = new JLabel("Please enter Player Information:");
		JPanel FNamePanel = new JPanel();
		JPanel LNamePanel = new JPanel();
		JPanel DOBPanel = new JPanel();
		JPanel heightPanel = new JPanel();
		JPanel weightPanel = new JPanel();
		
		
		JLabel firstName = new JLabel("First Name:	");
		JLabel lastName = new JLabel("Last Name:	");
		JLabel DOB = new JLabel("DOB:	");
		JLabel height = new JLabel("Height:	");
		JLabel weight = new JLabel("Weight:	");
		
		JTextField firstNameTF = new JTextField();
		JTextField lastNameTF = new JTextField();
		JTextField DOBTF = new JTextField();
		JTextField heightTF = new JTextField();
		JTextField weightTF = new JTextField();
		
		Dimension TFSize= new Dimension(150,25);
		firstNameTF.setMaximumSize(TFSize);
		lastNameTF.setMaximumSize(TFSize);
		DOBTF.setMaximumSize(TFSize);
		heightTF.setMaximumSize(TFSize);
		weightTF.setMaximumSize(TFSize);
		
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
		
		JButton registerButton = new JButton("Register");
		
		textPanel.add(promptLabel, BorderLayout.WEST);
		textPanel.setMaximumSize(new Dimension(200,50));
		
		
		masterPanel.add(textPanel, BorderLayout.CENTER);
		masterPanel.add(FNamePanel, BorderLayout.CENTER);
		masterPanel.add(LNamePanel, BorderLayout.CENTER);
		masterPanel.add(DOBPanel, BorderLayout.CENTER);
		masterPanel.add(heightPanel, BorderLayout.CENTER);
		masterPanel.add(weightPanel, BorderLayout.CENTER);

		masterPanel.add(registerButton, BorderLayout.EAST);
		
		frame.add(masterPanel);

		// 5. Show it.
		frame.setVisible(true);

	}
	
	
}
