package GUI;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import java.util.*;

public class TeamPage extends CoachDisplayPage {

	JPanel createPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JPanel leaguePanel = new JPanel();
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
	JComboBox<String> positionSelectDropDownPlayer = new JComboBox<String>();

	// Dropdowns for adding a coach to a team
	JComboBox<String> teamSelectDropDownCoach = new JComboBox<String>();

	// Buttons for adding a player to a team
	JButton addPlayerButton = new JButton("Add Player");
//	JButton editPlayerButton = new JButton("Update Position");
	JButton deletePlayerButton = new JButton("Retire Player");

	// Buttons for adding a player to a team
	JButton addCoachButton = new JButton("Add Coach");
	JButton deleteCoachButton = new JButton("Retire Coach");

	JLabel createLabel = new JLabel("Add Team: ");
	JLabel teamNameLabel = new JLabel("Team Name: ");
	JLabel leagueLabel = new JLabel("League: ");

	JTextField nameTF = new JTextField();
	JTextField leagueTF = new JTextField();

	JButton submitButton = new JButton("Submit");

	JFrame frame = null;
	private DatabaseConnectionService connection = null;

	private AccountHandler acct;

	public TeamPage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.acct = acct;
		this.frame = frame;
		this.connection = connection;
		createPanel.setMaximumSize(new Dimension(200, 300));
		nameTF.setMaximumSize(new Dimension(150, 25));
		leagueTF.setMaximumSize(new Dimension(150, 25));
		playerUsernameField.setMaximumSize(new Dimension(150, 25));
		coachUsernameField.setMaximumSize(new Dimension(150, 25));
		teamSelectDropDownCoach.setMaximumSize(new Dimension(100,25));

		addPlayerPanel.setLayout(new BoxLayout(addPlayerPanel, BoxLayout.Y_AXIS));
		addCoachPanel.setLayout(new BoxLayout(addCoachPanel, BoxLayout.Y_AXIS));

//		teamSelectDropDownPlayer.setPreferredSize(new Dimension(150,25));
//		positionSelectDropDownPlayer.setMaximumSize(new Dimension(150,25));

		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		leaguePanel.setLayout(new BoxLayout(leaguePanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

		onSubmitButtonClick();
		
		onPlayerAddButtonClick();
		onPlayerDeleteButtonClick();
		
		onCoachAddButtonClick();
		onCoachDeleteButtonClick();
	}

	public String[] getAllPositions() {
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("");
		try {

			String query = "SELECT Name FROM Position";
			PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			int positionName = rs.findColumn("Name");
			while (rs.next()) {
				arrList.add(rs.getString(positionName));
			}
//			System.out.println(rs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] returnArray = new String[arrList.size()];

		for (int i = 0; i < arrList.size(); i++) {
			returnArray[i] = arrList.get(i);
		}

		return returnArray;
	}

	public String[] getAllTeams() {
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("");
		try {

			String query = "SELECT T.[Name] AS 'Team Name' FROM Team T JOIN Coaches C on C.TeamID = T.ID JOIN Person P2 on P2.ID = C.CoachID WHERE P2.Username = ? AND C.EndDate is NULL";
			PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);

			stmt.setString(1, this.acct.getName());

			rs = stmt.executeQuery();
			int positionName = rs.findColumn("Team Name");
			while (rs.next()) {
				arrList.add(rs.getString(positionName));
			}
//			System.out.println(rs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] returnArray = new String[arrList.size()];

		for (int i = 0; i < arrList.size(); i++) {
			returnArray[i] = arrList.get(i);
		}

		return returnArray;
	}

	public void showAddPlayer(JPanel masterPanel) {
		this.positionSelectDropDownPlayer = new JComboBox<>(this.getAllPositions());
		this.teamSelectDropDownPlayer = new JComboBox<>(this.getAllTeams());
//		positionSelectDropDownPlayer.setMaximumSize(new Dimension(100,50));

		addPlayerPanel.add(addPlayerToTeamLabel);
		addPlayerPanel.setMaximumSize(new Dimension(400, 100));

		selectTeamPanelForPlayer.add(teamLabelForPlayer);
		selectTeamPanelForPlayer.add(teamSelectDropDownPlayer);
		selectTeamPanelForPlayer.setMaximumSize(new Dimension(700,25));
		selectTeamPanelForPlayer.setLayout(new BoxLayout(selectTeamPanelForPlayer, BoxLayout.X_AXIS));
//		selectTeamPanelForPlayer.setBackground(Color.red);

		addPlayerPanel.add(selectTeamPanelForPlayer);

		playerUsernamePanel.add(playerUsernameLabel);
		playerUsernamePanel.add(playerUsernameField);
		playerUsernamePanel.setLayout(new BoxLayout(playerUsernamePanel, BoxLayout.X_AXIS));
		addPlayerPanel.add(playerUsernamePanel);

		playerPositionPanel.add(positionLabel);
		playerPositionPanel.add(positionSelectDropDownPlayer);
//		playerPositionPanel.setBackground(Color.GREEN);
		playerPositionPanel.setMaximumSize(new Dimension(700,25));
		playerPositionPanel.setLayout(new BoxLayout(playerPositionPanel, BoxLayout.X_AXIS));
		
		addPlayerPanel.add(playerPositionPanel);

		editPlayerButtonsPanel.add(addPlayerButton);
//		editPlayerButtonsPanel.add(editPlayerButton);
		editPlayerButtonsPanel.add(deletePlayerButton);
		editPlayerButtonsPanel.setMaximumSize(new Dimension(700,25));
		editPlayerButtonsPanel.setLayout(new BoxLayout(editPlayerButtonsPanel, BoxLayout.X_AXIS));
		addPlayerPanel.add(editPlayerButtonsPanel);
		addPlayerPanel.setMaximumSize(new Dimension(700,200));

		masterPanel.add(addPlayerPanel);
	}

	public void showAddCoach(JPanel masterPanel) {
		this.teamSelectDropDownCoach = new JComboBox<>(this.getAllTeams());
		addCoachPanel.add(addCoachToTeamLabel);
		addCoachPanel.setMaximumSize(new Dimension(400, 100));

		selectTeamPanelForCoach.add(teamLabelForCoach);
		selectTeamPanelForCoach.add(teamSelectDropDownCoach);
		selectTeamPanelForCoach.setMaximumSize(new Dimension(700,25));
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

	public JPanel show() {
		masterPanel = super.show();
		namePanel.add(teamNameLabel);
		namePanel.add(nameTF);

		leaguePanel.add(leagueLabel);
		leaguePanel.add(leagueTF);
		
//		namePanel.setBackground(Color.red);
//		createPanel.setBackground(Color.GREEN);
//		selectTeamPanelForCoach.setBackground(Color.BLUE);
		selectTeamPanelForCoach.setMaximumSize(new Dimension(700, 200));
		
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

				if (nameValue.equals("") || nameValue.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team Name");
					return;
				}

				System.out.println(nameValue);
				System.out.println(leagueValue);
				
				addTeam(nameValue, leagueValue);
				
				clear();
				show();
				
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}
	
	public void onPlayerAddButtonClick() {
		addPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Add Player");
				
				String playerUsername = playerUsernameField.getText();
				if (playerUsername.equals("") || playerUsername.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Player Username");
					return;
				}
				

				String teamSelection = (String) teamSelectDropDownPlayer.getSelectedItem();
				if (teamSelection.equals("") || teamSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team");
					return;
				}
				
				String positionSelection = (String) positionSelectDropDownPlayer.getSelectedItem();
				if (positionSelection.equals("") || positionSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Position");
					return;
				}

				
				addPlaysOn(teamSelection, playerUsername, positionSelection);
				
				clear();
				show();
//				System.out.println(nameValue);
//				System.out.println(leagueValue);
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}
	
	
	//deletePlayerButton
	
	public void onPlayerDeleteButtonClick() {
		deletePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Delete Player");
				
				String playerUsername = playerUsernameField.getText();
				if (playerUsername.equals("") || playerUsername.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Player Username");
					return;
				}
				

				String teamSelection = (String) teamSelectDropDownPlayer.getSelectedItem();
				if (teamSelection.equals("") || teamSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team");
					return;
				}
				
				String positionSelection = (String) positionSelectDropDownPlayer.getSelectedItem();
				if (positionSelection.equals("") || positionSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Position");
					return;
				}

				
				deletePlaysOn(teamSelection, playerUsername, positionSelection);
				
				clear();
				show();
//				System.out.println(nameValue);
//				System.out.println(leagueValue);
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}
	
	public void onCoachAddButtonClick() {
		addCoachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Add Coach");
				
				String playerUsername = coachUsernameField.getText();
				if (playerUsername.equals("") || playerUsername.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Coach Username");
					return;
				}
				

				String teamSelection = (String) teamSelectDropDownCoach.getSelectedItem();
				if (teamSelection.equals("") || teamSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team");
					return;
				}
				
				int teamId = getTeamNameId(teamSelection);
				
				addCoaches(playerUsername, teamId);
				
				clear();
				show();
//				System.out.println(nameValue);
//				System.out.println(leagueValue);
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}


	public void onCoachDeleteButtonClick() {
		deleteCoachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Delete Coach");
				
				String playerUsername = coachUsernameField.getText();
				if (playerUsername.equals("") || playerUsername.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Coach Username");
					return;
				}
				

				String teamSelection = (String) teamSelectDropDownCoach.getSelectedItem();
				if (teamSelection.equals("") || teamSelection.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Team");
					return;
				}
				
				int teamId = getTeamNameId(teamSelection);
				
				removeCoaches(playerUsername, teamId);
				
				clear();
				show();
//				System.out.println(nameValue);
//				System.out.println(leagueValue);
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}

	
	public int addTeam(String TeamName, String League) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call CoachAddsTeam(?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, this.acct.getName());
			stmt.setString(3, TeamName);
			stmt.setString(4, League);
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());

		}
		if (returnValue == 0) {
			System.out.println("Success");
			JOptionPane.showMessageDialog(null, "Team Added");
			return returnValue;
		} else {
			mess = "ERROR: Invalid team";
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, mess);
			return returnValue;
		}
	}
	
	public int getTeamNameId(String teamName) {
		ResultSet rs;
		try {

			String query = "SELECT Team.Id FROM Team Join Coaches ch on Team.Id = Ch.TeamId JOIN Person ON Person.Id = ch.CoachId WHERE Name = ?";
			PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);

			stmt.setString(1, teamName);

			rs = stmt.executeQuery();
			int teamId = rs.findColumn("Id");
			while (rs.next()) {
				return rs.getInt(teamId);
			}
//			System.out.println(rs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public int deletePlaysOn(String teamName, String playerUsername, String positionName) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call SetPlaysOnEndDate(?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, getTeamNameId(teamName));
			stmt.setString(3, playerUsername);
			stmt.setString(4, positionName);		
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());

		}
		if (returnValue == 0) {
			System.out.println("Success");
			JOptionPane.showMessageDialog(null, playerUsername+" quit "+ positionName +" on " + teamName);
			return returnValue;
		} else {
			mess = "ERROR: Enter Valid Player Data";
			
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, mess);
			return returnValue;
		}
	}
	
	
	public int addPlaysOn(String teamName, String playerUsername, String positionName) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call AddPlaysOn(?, ?, ?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, getTeamNameId(teamName));
			stmt.setString(3, playerUsername);
			stmt.setString(4, positionName);

			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);

			stmt.setDate(5, date);
			stmt.setDate(6, null);
			
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());

		}
		if (returnValue == 0) {
			System.out.println("Success");
			JOptionPane.showMessageDialog(null, playerUsername+" Added To " + teamName);
			return returnValue;
		} else {
			mess = "ERROR: Enter Valid Player Data";
			
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
			java.sql.Date date = new java.sql.Date(millis);

			stmt.setDate(4, date);
			stmt.setDate(5, null);
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;

		}
		return true;
	}
	
	public boolean removeCoaches(String username, int teamID) {
		System.out.println(teamID);
		int returnValue = -1;
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call SetCoachesEndDate(?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setInt(3, teamID);
			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);
			JOptionPane.showMessageDialog(null, username+" quit the team.");


		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;

		}
		return true;
	}
	
	public void clear() {
		this.selectTeamPanelForCoach.remove(teamSelectDropDownCoach);
		this.selectTeamPanelForPlayer.remove(teamSelectDropDownPlayer);
		this.playerPositionPanel.remove(positionSelectDropDownPlayer);
		
		frame.getContentPane().removeAll();
		frame.repaint();
	}

}
