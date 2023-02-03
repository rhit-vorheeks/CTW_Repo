package GUI;

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

public class PlayerHomePage extends PlayerDisplayPage {

	JPanel statPanel = new JPanel();
	JPanel masterPanel = new JPanel();

	JLabel statLabel = new JLabel("Stats: ");
	JTable statTable = new JTable();

	String statQuery = "SELECT hs.StatName 'Stat Name', hs.Quantity as 'Quantity', hs.[Date] as 'Date', p.FName as 'First Name', p.Lname as 'Last Name', p.Username as 'Username' \r\n"
			+ "FROM HasStat hs JOIN Person p ON p.ID = hs.PlayerID\r\n"
			+ "WHERE p.Username = ? \r\n"
			+ "ORDER BY hs.[Date] desc";

	private DatabaseConnectionService dbService = null;
	private AccountHandler acct;
	private JFrame frame;

	public PlayerHomePage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.frame = frame;
		this.dbService = connection;
		this.acct = acct;
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

	}

	public JPanel show() {
		statPanel.removeAll();
		masterPanel.removeAll();
		
		masterPanel = super.show();
		statPanel.add(statLabel, BorderLayout.WEST);

		statTable = getTable(statQuery, statTable);
		statTable.setMaximumSize(new Dimension(800, 600));

		for (int i = 0; i < statTable.getColumnCount(); i++) {
			wrapCol(i, statTable);
		}

		statPanel.add(new JScrollPane(statTable));
		masterPanel.add(statPanel, BorderLayout.NORTH);

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

//	public void refreshTable(String query, JTable table) {
//		statPanel.removeAll();
//		DefaultTableModel model = (DefaultTableModel) table.getModel();
//		model.setRowCount(0);
//		getTable(query, table);
//		for (int i = 0; i < table.getColumnCount(); i++) {
//			wrapCol(i, table);
//		}
//		statPanel.add(new JScrollPane(table));
//		frame.setVisible(true);
//	}

}
