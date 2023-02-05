package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerAddStatPage extends PlayerDisplayPage {

	private JComboBox<String> statNameDropDown = null;
	private JLabel statNameLabel = new JLabel("Stat Name: ");
	private JLabel dateLabel = new JLabel("Date: ");
	private JLabel quantityLabel = new JLabel("Quantity: ");
	private JTextField dateTF = new JTextField("MM-DD-YYYY");
//	private JDatePicker dateTF = new JDatePicker();
	private JTextField quantityTF = new JTextField();
	
	private JButton addButton = new JButton("Add");
	private JButton updateButton = new JButton("Update");
	
	private JPanel dropDownPanel = new JPanel();
	private JPanel datePanel = new JPanel();
	private JPanel quantityPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JPanel masterPanel = null; 
	
	private JFrame frame = null;
	private DatabaseConnectionService connection;
	private AccountHandler acct;
	private String dateValue;
	private String quantityValue;
	private String statValue;
	
	public PlayerAddStatPage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		this.connection = connection;
		this.acct = acct;
		
		statNameDropDown = new JComboBox<String>(getAllStats());
		
		
		Dimension TFSize = new Dimension(150, 25);
		dateTF.setMaximumSize(TFSize);
		quantityTF.setMaximumSize(TFSize);
		statNameDropDown.setMaximumSize(TFSize);

		
		dropDownPanel.setLayout(new BoxLayout(dropDownPanel, BoxLayout.X_AXIS));
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		onAddButtonClick();
		
		
	}
	
	public JPanel show() {
		this.masterPanel = super.show();
		frame = this.getFrame();
		
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		
		dropDownPanel.add(statNameLabel);
		dropDownPanel.add(statNameDropDown);
		datePanel.add(dateLabel);
		datePanel.add(dateTF);
		quantityPanel.add(quantityLabel);
		quantityPanel.add(quantityTF);
		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		
		masterPanel.add(dropDownPanel, BorderLayout.NORTH);
		masterPanel.add(datePanel, BorderLayout.NORTH);
		masterPanel.add(quantityPanel, BorderLayout.NORTH);
		masterPanel.add(buttonPanel, BorderLayout.NORTH);
		
		frame.add(masterPanel);
		frame.setVisible(true);
		
		return null;
	}
	
	public String[] getAllStats() {
		ResultSet rs;
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("");
		try {

			String query = "SELECT Name FROM Stat";
			PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);

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
	
	public void getData() {
		dateValue = dateTF.getText();
		quantityValue = quantityTF.getText();
		statValue = (String) statNameDropDown.getSelectedItem();
//		typeValue = typeTF.getText();

	}
	
	public void onAddButtonClick() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked AddButton");
				getData();
				addHasStat();
				

			}

		});
	}
	
	public boolean addHasStat() {
		int returnValue = -1;
		String mess = "";
		
		int quantityInt = Integer.parseInt(quantityValue);
		
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call addHasStat(?, ?,?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, statValue);
			stmt.setString(3, acct.getName());
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date2 = null;
			try {
				date2 = sdf.parse(dateValue);
			} catch (ParseException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Please Enter a valid date");
				return false;
			}
			java.sql.Date d = new java.sql.Date(date2.getTime());

			stmt.setDate(4, d);
			
			stmt.setInt(5, quantityInt);

			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;

		}
		if (returnValue == 0) {
			JOptionPane.showMessageDialog(null, "Stat added.");

		} else if(returnValue == 8) {
			JOptionPane.showMessageDialog(null, "Stat exists on this date. Update to change.");
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Stat add failed.");

		}
		return true;
	}


}
