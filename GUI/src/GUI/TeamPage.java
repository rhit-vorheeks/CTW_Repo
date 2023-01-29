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
		
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		leaguePanel.setLayout(new BoxLayout(leaguePanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		
		onSubmitButtonClick();
	}
	
	
	public JPanel show(){
		masterPanel = super.show();
		namePanel.add(teamNameLabel);
		namePanel.add(nameTF);
		
		leaguePanel.add( leagueLabel);
		leaguePanel.add(leagueTF);
		
		masterPanel.add(createLabel, BorderLayout.NORTH);
		masterPanel.add(namePanel, BorderLayout.NORTH);
		masterPanel.add(leaguePanel, BorderLayout.NORTH);
		masterPanel.add(submitButton, BorderLayout.NORTH);

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
