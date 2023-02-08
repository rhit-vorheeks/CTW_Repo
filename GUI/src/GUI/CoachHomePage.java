package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CoachHomePage extends CoachDisplayPage {

	JPanel rosterPanel = new JPanel();
	JPanel masterPanel = new JPanel();
	
	JPanel teamSearchPanel = new JPanel();

	JLabel rosterLabel = new JLabel("Player Roster: ");
	JLabel coachRosterLabel = new JLabel("Coach Roster: ");
	
	JButton submitButton = new JButton("Submit");

	private JComboBox<String> teamSelectDropdown = null;

	JTable rosterTable = new JTable();
	
	String currentSelection = "All";

	JFrame frame = null;
	JTable table = null;
	JTable coachTable = null;

	String rosterquery = "SELECT P.Username as 'Username', T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name',  PO.PositionName as 'Position', P.Type as 'Type'\r\n"
			+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID\r\n" + "JOIN Person P on P.ID = PO.PlayerID\r\n"
			+ "JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Person P2 on P2.ID = C.CoachID\r\n"
			+ "WHERE P2.Username = ? and PO.EndDate is null\r\n"
			+ "GROUP BY P.Type, T.[Name], P.Lname, P.FName, P.Username, PO.PositionName";
	
	String rosterqueryforteam = "SELECT P.Username as 'Username', T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name',  PO.PositionName as 'Position', P.Type as 'Type'\r\n"
			+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID\r\n" + "JOIN Person P on P.ID = PO.PlayerID\r\n"
			+ "JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Person P2 on P2.ID = C.CoachID\r\n"
			+ "WHERE P2.Username = ? and PO.EndDate is null and T.[Name] = ?\r\n"
			+ "GROUP BY P.Type, T.[Name], P.Lname, P.FName, P.Username, PO.PositionName";

	String coachquery = "SELECT  P.Username as 'Username', T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', Coach.Rank as 'Rank' , P.Type as 'Type'\r\n"
			+ "FROM Team T JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Coaches C2 on C2.TeamID = T.ID \r\n"
			+ "JOIN Person P on P.ID = C.CoachID\r\n" + "JOIN Person P2 on P2.ID = C2.CoachID\r\n"
			+ "JOIN Coach on Coach.ID = P.ID\r\n" + "WHERE P2.Username = ? and C.EndDate is null\r\n"
			+ "GROUP BY P.Type, T.[Name], P.Lname, P.FName, P.Username, Coach.Rank";
	
	String coachqueryforteam = "SELECT  P.Username as 'Username', T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', Coach.Rank as 'Rank' , P.Type as 'Type'\r\n"
			+ "FROM Team T JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Coaches C2 on C2.TeamID = T.ID \r\n"
			+ "JOIN Person P on P.ID = C.CoachID\r\n" + "JOIN Person P2 on P2.ID = C2.CoachID\r\n"
			+ "JOIN Coach on Coach.ID = P.ID\r\n" + "WHERE P2.Username = ? and C.EndDate is null and T.[Name] = ?\r\n"
			+ "GROUP BY P.Type, T.[Name], P.Lname, P.FName, P.Username, Coach.Rank";
	
	private DatabaseConnectionService dbService = null;

	private AccountHandler acct;

	public CoachHomePage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.frame = frame;
		this.dbService = connection;
		this.acct = acct;
		rosterPanel.setLayout(new BoxLayout(rosterPanel, BoxLayout.Y_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		onSubmitButtonClick();
	}

	public JPanel show() {
		rosterPanel.removeAll();
		teamSearchPanel.removeAll();
	
		String[] arr = getAllTeams();
		teamSelectDropdown = new JComboBox<String>(arr);
		
		for (int i = 0; i < arr.length; i++) {
			if (this.currentSelection.equals(arr[i])) {
				teamSelectDropdown.setSelectedIndex(i);
			}
		}
		teamSelectDropdown.setMaximumSize(new Dimension(150, 25));
		
		masterPanel = super.show();
		teamSearchPanel.add(teamSelectDropdown);
		teamSearchPanel.add(submitButton);
		
		rosterPanel.add(teamSearchPanel);
		rosterPanel.add(rosterLabel, BorderLayout.WEST);
		rosterPanel.add(rosterTable);

		String query1 = null;
		String query2 = null;
		if (this.currentSelection.equals("All")) {
			query1 = rosterquery;
			query2 = coachquery;
			table = getTable(query1, table);
			coachTable = getTable(query2, coachTable);
		} else {
			query1 = rosterqueryforteam;
			query2 = coachqueryforteam;
			table = getTableForTeam(query1, table);
			coachTable = getTableForTeam(query2, coachTable);
		}
		table.setMaximumSize(new Dimension(800, 600));

		coachTable.setMaximumSize(new Dimension(800, 600));



		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}

		for (int i = 0; i < coachTable.getColumnCount(); i++) {
			wrapCol(i, coachTable);
		}


		rosterPanel.add(new JScrollPane(table));
		rosterPanel.add(coachRosterLabel);
		rosterPanel.add(new JScrollPane(coachTable));
		masterPanel.add(rosterPanel, BorderLayout.NORTH);
		frame.setVisible(true);

		return null;

	}
	
	public String[] getAllTeams() {
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("All");
		try {
			String query = "SELECT T.[Name] AS 'Team Name' FROM Team T JOIN Coaches C on C.TeamID = T.ID JOIN Person P2 on P2.ID = C.CoachID WHERE P2.Username = ? AND C.EndDate is NULL";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);

			stmt.setString(1, this.acct.getName());
			rs = stmt.executeQuery();
			int statName = rs.findColumn("Team Name");
			while (rs.next()) {
				arrList.add(rs.getString(statName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] returnArray = new String[arrList.size()];

		for (int i = 0; i < arrList.size(); i++) {
			returnArray[i] = arrList.get(i);
		}

		return returnArray;
	}

	public JTable getTable(String query, JTable table) {

		ResultSet rs;
		try {
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, this.acct.getName());
			rs = stmt.executeQuery();
			System.out.println(rs.toString());
			table = new JTable(buildTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	public JTable getTableForTeam(String query, JTable table) {

		ResultSet rs;
		try {
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, this.acct.getName());
			stmt.setString(2, currentSelection);
			rs = stmt.executeQuery();
			System.out.println(rs.toString());
			table = new JTable(buildTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

	}

	public void clear() {
		
		frame.getContentPane().removeAll();
		frame.repaint();
	}

	public void refreshTable(String query, JTable table) {
		rosterPanel.removeAll();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
//		statPanel.remove(table);
		getTable(query, table);
	}

	
	public void onSubmitButtonClick() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Submit");
				currentSelection = (String) teamSelectDropdown.getSelectedItem();
				
				
				clear();
				show();
//				System.out.println(nameValue);
//				System.out.println(leagueValue);
//				int id = addTeam(nameValue, leagueValue);
//				addCoaches(acct.getName(), id);

			}

		});
	}
}
