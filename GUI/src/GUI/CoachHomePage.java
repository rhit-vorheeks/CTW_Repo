package GUI;

import java.awt.BorderLayout;
import java.beans.Statement;
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
	private DatabaseConnectionService dbService = null;

//	private AccountHandler acct;

	public CoachHomePage(JFrame frame, DatabaseConnectionService connection) {
		super(frame);
		this.frame = frame;
		this.dbService = connection;

		rosterPanel.setLayout(new BoxLayout(rosterPanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
	}

	public JPanel show() {
		masterPanel = super.show();
		rosterPanel.add(rosterLabel);
		rosterPanel.add(rosterTable);
		rosterPanel.add(getTable());
		masterPanel.add(rosterPanel, BorderLayout.NORTH);
		frame.setVisible(true);
		return null;

	}

	public JTable getTable() {

		ResultSet rs;
		JTable table = null;
		try {
			java.sql.Statement stmt = this.dbService.getConnection().createStatement();
			String query = "";
			rs = stmt.executeQuery(query);
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

}
