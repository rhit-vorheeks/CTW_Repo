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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class PlayerFindDrillPage extends PlayerDisplayPage {

	private JPanel drillPanel = new JPanel();
	private JPanel masterPanel = new JPanel();
	private JPanel selectPanel = new JPanel();

	private JLabel targetStatLabel = new JLabel("Target Stat: ");
	private JTable table = null;
	private JComboBox<String> dropDown = null;
	private JButton submitButton = new JButton("Submit");

	private JFrame frame = null;
	private DatabaseConnectionService dbService = null;


	public PlayerFindDrillPage(JFrame frame, DatabaseConnectionService connection) {
		super(frame);
		this.frame = frame;
		this.dbService = connection;

		drillPanel.setLayout(new BoxLayout(drillPanel, BoxLayout.X_AXIS));
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
		
		dropDown = new JComboBox<String>(getAllStats());
		dropDown.setMaximumSize(new Dimension(150, 25));
		onSubmitButtonClick();
	}

	public JPanel show() {
		drillPanel.removeAll();
		
		masterPanel = super.show();
		selectPanel.add(targetStatLabel);
		selectPanel.add(dropDown);
		selectPanel.add(submitButton);


		masterPanel.add(selectPanel, BorderLayout.CENTER);
		masterPanel.add(drillPanel, BorderLayout.CENTER);
		
		if(table == null) {
			getTable("All");
		}
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}

		drillPanel.add(new JScrollPane(table));
		frame.add(masterPanel);
		frame.setVisible(true);
		
		return null;
	}
	
	
	public String[] getAllStats() {
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("All");
		try {

			String query = "SELECT Name FROM Stat";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			int statName = rs.findColumn("Name");
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
				System.out.println("Clicked Submit");
				String selection = (String) dropDown.getSelectedItem();
				
				refreshTable(selection);
				

			}

		});
	}
	
	
	
	public void getTable(String selection) {

		ResultSet rs;
		try {
			CallableStatement stmt = dbService.getConnection().prepareCall("{? = call getDrill(?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, selection);
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
	

	public void refreshTable(String selection) {
//		drillPanel.remove(table);
		drillPanel.removeAll();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		getTable(selection);
		for (int i = 0; i < table.getColumnCount(); i++) {
			wrapCol(i, table);
		}
		drillPanel.add(new JScrollPane(table));
		frame.setVisible(true);
	}
		
}
