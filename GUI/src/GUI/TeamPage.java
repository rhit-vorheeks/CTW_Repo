package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.jar.Attributes.Name;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.*;

public class TeamPage extends CoachDisplayPage {

	JPanel createPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JPanel leaguePanel =  new JPanel();
	JPanel masterPanel = new JPanel();
	
	
	// Panels for adding a player to a team
	JPanel addPlayerPanel = new JPanel();
	JPanel selectTeamPanelForPlayer = new JPanel();
	JPanel playerUsernamePanel = new JPanel();
	JPanel playerPositionPanel = new JPanel();
	JPanel editPlayerButtonsPanel = new JPanel();
	
	// Panels for adding coach to a team
	JPanel addCoachPanel = new JPanel();
	JPanel selectTeamPanelForCoach = new JPanel();
	JPanel coachUsernamePanel = new JPanel();
	JPanel editCoachButtonsPanel = new JPanel();
	
	// Labels for adding player to a team
	JLabel addPlayerToTeamLabel = new JLabel("Add Player To Team:");
	JLabel teamLabelForPlayer = new JLabel("Team: ");
	JLabel playerUsernameLabel = new JLabel("Player Username: ");
	JLabel positionLabel = new JLabel("Position: ");
	
	// Labels for adding coach to team
	JLabel addCoachToTeamLabel = new JLabel("Add Coach To Team:");
	JLabel teamLabelForCoach = new JLabel("Team: ");
	JLabel coachUsernameLabel = new JLabel("Coach Username: ");
	
	// Textfields for adding a player to a team
	JTextField playerUsernameField = new JTextField();

	// Textfields for adding a coach to a team
	JTextField coachUsernameField = new JTextField();

	// Dropdowns for adding a player to a team
	JComboBox<String> teamSelectDropDownPlayer = new JComboBox<String>();
	JComboBox<String> PositionSelectDropDownPlayer = new JComboBox<String>();

	// Dropdowns for adding a coach to a team
	JComboBox<String> teamSelectDropDownCoach = new JComboBox<String>();
	
	// Buttons for adding a player to a team
	JButton addPlayerButton = new JButton("Add Player");
	JButton editPlayerButton = new JButton("Update Position");
	JButton deletePlayerButton = new JButton("Delete Player");
	
	// Buttons for adding a player to a team
	JButton addCoachButton = new JButton("Add Coach");
	JButton deleteCoachButton = new JButton("Delete Coach");
		
	
	
	JLabel createLabel = new JLabel("Add Team: ");
	JLabel teamNameLabel = new JLabel("Team Name: ");
	JLabel leagueLabel = new JLabel("League: ");
	
	JTextField nameTF = new JTextField();
	JTextField leagueTF = new JTextField();
	
	JButton submitButton = new JButton("Submit");	
	
	JFrame frame =  null;
	private DatabaseConnectionService connection = null;

	
	private AccountHandler acct;
	
	public TeamPage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.acct = acct;
		this.frame = frame;
		this.connection  = connection;
		nameTF.setMaximumSize(new Dimension(100,50));
		leagueTF.setMaximumSize(new Dimension(100,50));
		playerUsernameField.setMaximumSize(new Dimension(100,50));
		coachUsernameField.setMaximumSize(new Dimension(100,50));
		
		addPlayerPanel.setLayout(new BoxLayout(addPlayerPanel, BoxLayout.Y_AXIS));
		addCoachPanel.setLayout(new BoxLayout(addCoachPanel, BoxLayout.Y_AXIS));
		
		teamSelectDropDownPlayer.setMaximumSize(new Dimension(100,50));
		PositionSelectDropDownPlayer.setMaximumSize(new Dimension(100,50));
		teamSelectDropDownCoach.setMaximumSize(new Dimension(100,50));
		
		
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		leaguePanel.setLayout(new BoxLayout(leaguePanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		
		onSubmitButtonClick();
	}
	
	public void showAddPlayer(JPanel masterPanel) {
		addPlayerPanel.add(addPlayerToTeamLabel);
		addPlayerPanel.setMaximumSize(new Dimension(400, 100));
		
		selectTeamPanelForPlayer.add(teamLabelForPlayer);
		selectTeamPanelForPlayer.add(teamSelectDropDownPlayer);
		selectTeamPanelForPlayer.setLayout(new BoxLayout(selectTeamPanelForPlayer, BoxLayout.X_AXIS));
		addPlayerPanel.add(selectTeamPanelForPlayer);
		
		playerUsernamePanel.add(playerUsernameLabel);
		playerUsernamePanel.add(playerUsernameField);
		playerUsernamePanel.setLayout(new BoxLayout(playerUsernamePanel, BoxLayout.X_AXIS));
		addPlayerPanel.add(playerUsernamePanel);
		
		playerPositionPanel.add(positionLabel);
		playerPositionPanel.add(PositionSelectDropDownPlayer);
		playerPositionPanel.setLayout(new BoxLayout(playerPositionPanel, BoxLayout.X_AXIS));
		addPlayerPanel.add(playerPositionPanel);
		
		editPlayerButtonsPanel.add(addPlayerButton);
		editPlayerButtonsPanel.add(editPlayerButton);
		editPlayerButtonsPanel.add(deletePlayerButton);
		editPlayerButtonsPanel.setLayout(new BoxLayout(editPlayerButtonsPanel, BoxLayout.X_AXIS));
		addPlayerPanel.add(editPlayerButtonsPanel);
		
		masterPanel.add(addPlayerPanel);
	}
	
	public void showAddCoach(JPanel masterPanel) {
		addCoachPanel.add(addCoachToTeamLabel);
		addCoachPanel.setMaximumSize(new Dimension(400, 100));
		
		selectTeamPanelForCoach.add(teamLabelForCoach);
		selectTeamPanelForCoach.add(teamSelectDropDownCoach);
		selectTeamPanelForCoach.setLayout(new BoxLayout(selectTeamPanelForCoach, BoxLayout.X_AXIS));
		addCoachPanel.add(selectTeamPanelForCoach);
		
		coachUsernamePanel.add(coachUsernameLabel);
		coachUsernamePanel.add(coachUsernameField);
		coachUsernamePanel.setLayout(new BoxLayout(coachUsernamePanel, BoxLayout.X_AXIS));
		addCoachPanel.add(coachUsernamePanel);
		
		
		editCoachButtonsPanel.add(addCoachButton);
		editCoachButtonsPanel.add(deleteCoachButton);
		editCoachButtonsPanel.setLayout(new BoxLayout(editCoachButtonsPanel, BoxLayout.X_AXIS));
		addCoachPanel.add(editCoachButtonsPanel);
		
		masterPanel.add(addCoachPanel);
	}
	
	public JPanel show(){
		masterPanel = super.show();
		namePanel.add(teamNameLabel);
		namePanel.add(nameTF);
		
		leaguePanel.add(leagueLabel);
		leaguePanel.add(leagueTF);
		
		masterPanel.add(createLabel, BorderLayout.NORTH);
		masterPanel.add(namePanel, BorderLayout.NORTH);
		masterPanel.add(leaguePanel, BorderLayout.NORTH);
		masterPanel.add(submitButton, BorderLayout.NORTH);

		this.showAddPlayer(masterPanel);
		this.showAddCoach(masterPanel);
		
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
				int id = addTeam(nameValue,leagueValue);	
				addCoaches(acct.getName(), id);
								
			}

		});
	}
	
	
	public int addTeam(String TeamName, String League) {
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
		if (returnValue > 0) {
			System.out.println("Success");
			JOptionPane.showMessageDialog(null, "Team Added");
			return returnValue;
		} else {
			if (returnValue <0) {
				mess = "ERROR: Team name cannot be empty";
			} 
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, mess);
			return returnValue;
		}
	}
	
	public boolean addCoaches(String username, int teamID) {
		System.out.println(teamID);
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call AddCoaches(?, ?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setInt(3, teamID);
			
			long millis = System.currentTimeMillis();
			java.sql.Date date= new java.sql.Date(millis);
		
			stmt.setDate(4,date);
			stmt.setDate(5,null);
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
