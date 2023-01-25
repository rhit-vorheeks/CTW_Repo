package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Page that is shown during registration process that lets the user choose between registering as a coach or player.
 *
 */
public class RegisterPage extends AbstractPage{
	
	// Page References
	private PlayerRegisterPage plrPage;
	private CoachRegisterPage coachPage;
	
	// Panels
	private JPanel textPanel;
	private JPanel dropDownPanel;
	private JPanel masterPanel;
	
	// Labels
	private JLabel promptLabel;
	
	// Buttons
	private JButton continueButton;
	
	// Dropdown Box
	private String[] options = {"", "Player", "Coach" };
	private JComboBox<String> drop;

	/**
	 * Constructs a RegisterPage
	 * @param frame, plrPage, coachPage
	 */
	public RegisterPage(JFrame frame, PlayerRegisterPage plrPage, CoachRegisterPage coachPage) {
		super(frame);
		
		this.coachPage = coachPage;
		this.plrPage = plrPage;
		
		// Panels
		this.textPanel = new JPanel();
		this.dropDownPanel = new JPanel();
		this.masterPanel = new JPanel();
		
		// Labels
		this.promptLabel = new JLabel("Are you a player or a coach?");
		
		// Buttons
		this.continueButton = new JButton("Continue");
		
		// Dropdown Box
		String[] options = {"", "Player", "Coach" };
		this.drop = new JComboBox<>(options);
		

		this.masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.onContinueButtonClick();
	}
	
	public void show() {
		JFrame frame = this.getFrame();
		
		textPanel.add(promptLabel, BorderLayout.CENTER);
		textPanel.add(drop, BorderLayout.CENTER);
		textPanel.setMaximumSize(new Dimension(900, 300));

		dropDownPanel.add(continueButton, BorderLayout.NORTH);

		masterPanel.add(textPanel, BorderLayout.SOUTH);
		masterPanel.add(dropDownPanel, BorderLayout.CENTER);

		frame.add(masterPanel);


		// 5. Show it.
		frame.setVisible(true);
	}
	
	public void onContinueButtonClick() {
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				
				String selection = (String) drop.getSelectedItem();
				
				if (selection.equals("Player")) {
					plrPage.show();
				} else if (selection.equals("Coach")) {
					coachPage.show();
				} else {
					System.out.println("Need to select either player or coach.");
				}
				
			} 			
			
		});
		
	}

}
