package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FindPlayerStatPage extends CoachDisplayPage {

	private JPanel statPanel = new JPanel();
	private JPanel masterPanel = new JPanel();
	private JPanel selectPanel = new JPanel();

	private JLabel targetPlayerLabel = new JLabel("Target Player: ");
	private JTable table = null;
	private JComboBox<String> dropDown = null;
	private JButton submitButton = new JButton("SubmitButton");

	private JFrame frame = null;
	private DatabaseConnectionService dbService = null;
	
	private AccountHandler acct;
	
	public FindPlayerStatPage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.acct = acct;
		this.frame = frame;
		this.dbService = connection;

		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
		
		onSubmitButtonClick();
		
	}
	public JPanel show() {
		masterPanel.removeAll();
		statPanel.removeAll();
		selectPanel.removeAll();
		
		dropDown = new JComboBox<String>(getAllPlayers());
		dropDown.setMaximumSize(new Dimension(150, 25));
		
		masterPanel = super.show();
		selectPanel.add(targetPlayerLabel);
		selectPanel.add(dropDown);
		selectPanel.add(submitButton);


		masterPanel.add(selectPanel, BorderLayout.CENTER);
		masterPanel.add(statPanel, BorderLayout.CENTER);
		
//		if(table == null) {
			getTable("All");
//		}
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}

		statPanel.add(new JScrollPane(table));
		frame.add(masterPanel);
		frame.setVisible(true);
		
		return null;
	}
	
	
	public String[] getAllPlayers() {
		System.out.println("getAllPlayers");
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("All");
		try {

			String query = "SELECT DISTINCT P.Username as 'Username'\r\n"
					+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID JOIN Person P on P.ID = PO.PlayerID\r\n"
					+ "JOIN Coaches C on C.TeamID = T.ID  JOIN Person P2 on P2.ID = C.CoachID\r\n"
					+ "WHERE P2.Username = ? and PO.EndDate is null";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, this.acct.getName());

			rs = stmt.executeQuery();
			int statName = rs.findColumn("Username");
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
	
	public void onSubmitButtonClick() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked SubmitButton");
				String selection = (String) dropDown.getSelectedItem();
				
				refreshTable(selection);
				

			}

		});
	}
	
	
	
	public void getTable(String selection) {

		ResultSet rs;
		
		String query = null;
		try {
			if (selection.equals("All")) {
				query = "SELECT DISTINCT p.Username as 'Username', hs.StatName 'Stat Name', hs.Quantity as 'Quantity', hs.[Date] as 'Date', p.FName as 'First Name', p.Lname as 'Last Name' \r\n"
						+ "FROM Team T Join PlaysOn PO on T.ID = PO.TeamID JOIN Person P on P.ID = PO.PlayerID\r\n"
						+ "JOIN Coaches C on C.TeamID = T.ID  JOIN Person P2 on P2.ID = C.CoachID\r\n"
						+ "JOIN HasStat hs on hs.PlayerID = po.PlayerID\r\n"
						+ "WHERE P2.Username = ? and PO.EndDate is null\r\n"
						+ "ORDER BY p.Lname asc, hs.[Date] desc";
				PreparedStatement stmt = dbService.getConnection().prepareStatement(query);
				stmt.setString(1, this.acct.getName());
				rs = stmt.executeQuery();
				System.out.println(rs.toString());
				table = new JTable(buildTableModel(rs));
			}  else {
				query = "SELECT p.Username as 'Username', hs.StatName 'Stat Name', hs.Quantity as 'Quantity', hs.[Date] as 'Date', p.FName as 'First Name', p.Lname as 'Last Name' \r\n"
						+ "FROM HasStat hs JOIN Person p ON p.ID = hs.PlayerID\r\n"
						+ "WHERE p.Username = ? \r\n"
						+ "ORDER BY hs.[Date] desc";
				 
				PreparedStatement stmt = dbService.getConnection().prepareStatement(query);
				stmt.setString(1, selection);
				rs = stmt.executeQuery();
				System.out.println(rs.toString());
				table = new JTable(buildTableModel(rs));
			}
			
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
	

	public void refreshTable(String selection) {
//		drillPanel.remove(table);
		statPanel.removeAll();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		getTable(selection);
		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}
		statPanel.add(new JScrollPane(table));
		frame.setVisible(true);
	}
	
}
