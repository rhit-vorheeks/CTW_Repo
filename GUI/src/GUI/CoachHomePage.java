package GUI;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CoachHomePage extends CoachDisplayPage {

	JPanel rosterPanel = new JPanel();
	JPanel masterPanel = new JPanel();

	JLabel rosterLabel = new JLabel("Roster: ");
	JTable rosterTable = new JTable();

	JFrame frame = null;
	JTable table = null;
	private DatabaseConnectionService dbService = null;

	private AccountHandler acct;

	public CoachHomePage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.frame = frame;
		this.dbService = connection;
		this.acct = acct;

		rosterPanel.setLayout(new BoxLayout(rosterPanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
	}

	public JPanel show() {
		masterPanel = super.show();
		rosterPanel.add(rosterLabel);
		rosterPanel.add(rosterTable);
		if (table == null) {
			getTable();
		}
		rosterPanel.add(table);
		masterPanel.add(rosterPanel, BorderLayout.NORTH);
		frame.setVisible(true);
		return null;

	}

	public void getTable() {

		ResultSet rs;
		try {
			String query = "SELECT T.[Name] AS 'Team Name', P.FName AS 'First Name', P.Lname AS 'Last Name', P.Username as 'Username', PO.PositionName as 'Position'\r\n"
					+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID\r\n" + "JOIN Person P on P.ID = PO.PlayerID\r\n"
					+ "JOIN Coaches C on C.TeamID = T.ID \r\n" + "JOIN Person P2 on P2.ID = C.CoachID\r\n"
					+ "WHERE P2.Username = ?\r\n"
					+ "GROUP BY T.[Name], P.Lname, P.FName, P.Username, PO.PositionName";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, this.acct.getName());
			rs = stmt.executeQuery();
			System.out.println(rs.toString());
			table = new JTable(buildTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}


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

	public void refreshTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		rosterPanel.remove(table);
		getTable();
	}

}
