package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CoachHomePage extends CoachDisplayPage {

	JPanel rosterPanel = new JPanel();
	JPanel masterPanel = new JPanel();

	JLabel rosterLabel = new JLabel("Roster: ");
	JTable rosterTable = new JTable();

	JFrame frame = null;
	JTable table = null;
	JTable coachTable = null;

	String rosterquery = "SELECT T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', PO.PositionName as 'Position', P.Type as 'Type'\r\n"
			+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID\r\n" + "JOIN Person P on P.ID = PO.PlayerID\r\n"
			+ "JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Person P2 on P2.ID = C.CoachID\r\n"
			+ "WHERE P2.Username = ? and PO.EndDate is null\r\n"
			+ "GROUP BY P.Type, T.[Name], P.Lname, P.FName, P.Username, PO.PositionName";

	String coachquery = "SELECT T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', Coach.Rank as 'Rank' , P.Type as 'Type'\r\n"
			+ "FROM Team T JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Coaches C2 on C2.TeamID = T.ID \r\n"
			+ "JOIN Person P on P.ID = C.CoachID\r\n" + "JOIN Person P2 on P2.ID = C2.CoachID\r\n"
			+ "JOIN Coach on Coach.ID = P.ID\r\n" + "WHERE P2.Username = ? and C.EndDate is null\r\n"
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

	}

	public JPanel show() {
		rosterPanel.removeAll();
		
		masterPanel = super.show();
		rosterPanel.add(rosterLabel, BorderLayout.WEST);
		rosterPanel.add(rosterTable);

		table = getTable(rosterquery, table);
		table.setMaximumSize(new Dimension(800, 600));


		coachTable = getTable(coachquery, coachTable);
		coachTable.setMaximumSize(new Dimension(800, 600));



		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}

		for (int i = 0; i < coachTable.getColumnCount(); i++) {
			wrapCol(i, coachTable);
		}


		rosterPanel.add(new JScrollPane(table));
		rosterPanel.add(new JScrollPane(coachTable));
		masterPanel.add(rosterPanel, BorderLayout.NORTH);
		frame.setVisible(true);

		return null;

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

}
