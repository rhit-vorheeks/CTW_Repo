package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.jar.Attributes.Name;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TeamPage extends CoachDisplayPage {

	JPanel createPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JPanel leaguePanel =  new JPanel();
	JPanel masterPanel = new JPanel();
		
	JLabel createLabel = new JLabel("Add Team: ");
	JLabel teamNameLabel = new JLabel("Team Name: ");
	JLabel leagueLabel = new JLabel("League: ");
	
	JTextField nameTF = new JTextField();
	JTextField leagueTF = new JTextField();
	
	JButton submitButton = new JButton("Submit");	
	
	JFrame frame =  null;
	private DatabaseConnectionService connection = null;
	private String coachUsername = null;
	
	public TeamPage(JFrame frame, DatabaseConnectionService connection) {
		super(frame);
		this.frame = frame;
		this.connection  = connection;
		nameTF.setMaximumSize(new Dimension(100,50));
		leagueTF.setMaximumSize(new Dimension(100,50));
		
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		leaguePanel.setLayout(new BoxLayout(leaguePanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		
		onSubmitButtonClick();
	}
	
	public void setUserName(String CoachUsername) {
		this.coachUsername  = CoachUsername;

	}
	
	public JPanel show(){
		masterPanel = super.show();
		namePanel.add(teamNameLabel);
		namePanel.add(nameTF);
		
		leaguePanel.add( leagueLabel);
		leaguePanel.add(leagueTF);
		
		masterPanel.add(createLabel, BorderLayout.SOUTH);
		masterPanel.add(namePanel, BorderLayout.SOUTH);
		masterPanel.add(leaguePanel, BorderLayout.SOUTH);
		masterPanel.add(submitButton, BorderLayout.SOUTH);

		frame.setVisible(true);
		return null;
		
	}
	
	public void onSubmitButtonClick() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Submit");
				String nameValue = nameTF.getText();
				String leagueValue = leagueTF.getText();
				
				if(nameValue.equals("") || nameValue.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team Name");
					return;
				}
				
				System.out.println(nameValue);
				System.out.println(leagueValue);
				addTeam(nameValue,leagueValue);		
								
			}

		});
	}
	
	
	public boolean addTeam(String TeamName, String League) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call AddTeam(?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, TeamName);
			stmt.setString(3, League);
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
		
		}
		if (returnValue == 0) {
			System.out.println("Success");
			JOptionPane.showMessageDialog(null, "Team Added");
			return true;
		} else {
			if (returnValue == 1) {
				mess = "ERROR: Team name cannot be empty";
			} 
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, mess);
			return false;
		}
	}
	

}
