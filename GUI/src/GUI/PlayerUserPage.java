package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerUserPage extends PlayerDisplayPage {

	// frame
	JFrame frame = this.getFrame();

	// panel
	JPanel textPanel = new JPanel();
	JPanel masterPanel = new JPanel();
	JPanel FNamePanel = new JPanel();
	JPanel LNamePanel = new JPanel();
	JPanel DOBPanel = new JPanel();
	JPanel heightPanel = new JPanel();
	JPanel weightPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	// Label
	JLabel promptLabel = new JLabel(" Enter Player Weight and Height to Update:");
	JLabel firstName = new JLabel("First Name:\u00A0	");
	JLabel firstNameValueLabel = new JLabel();

	JLabel lastName = new JLabel("Last Name:\u00A0	");
	JLabel lastNameValueLabel = new JLabel();

	JLabel DOB = new JLabel("DOB:\u00A0	");
	JLabel DOBValueLabel = new JLabel();

	JLabel height = new JLabel("Height:	\u00A0");
	JLabel weight = new JLabel("Weight:	\u00A0");

	// Text Field
	JTextField heightTF = new JTextField();
	JTextField weightTF = new JTextField();

	// Button
	JButton SubmitButton = new JButton("Update");
//		JButton backButton = new JButton("Back");

	// Data
	String FNameValue = null;
	String LNameValue = null;
	String DOBValue = null;
	int heightValue = -1;
	int weightValue = -1;
	AccountHandler acct = null;

	private DatabaseConnectionService connection;

	/**
	 * Constructs a PlayerRegister page
	 * 
	 * @param frame
	 */

	public PlayerUserPage(JFrame frame, DatabaseConnectionService connection, AccountHandler acct) {
		super(frame);
		Dimension TFSize = new Dimension(150, 25);
		firstNameValueLabel.setMaximumSize(TFSize);
		lastNameValueLabel.setMaximumSize(TFSize);
		DOBValueLabel.setMaximumSize(TFSize);
		heightTF.setMaximumSize(TFSize);
		weightTF.setMaximumSize(TFSize);

		this.connection = connection;
		this.acct = acct;

		onSubmitButtonClick();
//		this.onBackButtonClick();
	}

	public JPanel show() {
		masterPanel= super.show();
		getPlayerData();
		firstNameValueLabel.setText(FNameValue);
		lastNameValueLabel.setText(LNameValue);
		DOBValueLabel.setText(DOBValue);
		heightTF.setText(heightValue + "");
		weightTF.setText(weightValue + "");

		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

		FNamePanel.add(firstName);
		FNamePanel.add(firstNameValueLabel);
		FNamePanel.setLayout(new BoxLayout(FNamePanel, BoxLayout.X_AXIS));

		LNamePanel.add(lastName);
		LNamePanel.add(lastNameValueLabel);
		LNamePanel.setLayout(new BoxLayout(LNamePanel, BoxLayout.X_AXIS));

		DOBPanel.add(DOB);
		DOBPanel.add(DOBValueLabel);
		DOBPanel.setLayout(new BoxLayout(DOBPanel, BoxLayout.X_AXIS));

		heightPanel.add(height);
		heightPanel.add(heightTF);
		heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));

//			heightPanel.add(height);
//			heightPanel.add(heightTF);
//			heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));

		weightPanel.add(weight);
		weightPanel.add(weightTF);
		weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.X_AXIS));

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
//			buttonPanel.add(backButton);
		buttonPanel.add(SubmitButton);

		textPanel.add(promptLabel, BorderLayout.WEST);
		textPanel.setMaximumSize(new Dimension(1100, 50));

		masterPanel.add(textPanel, BorderLayout.CENTER);
		masterPanel.add(FNamePanel, BorderLayout.CENTER);
		masterPanel.add(LNamePanel, BorderLayout.CENTER);
		masterPanel.add(DOBPanel, BorderLayout.CENTER);
		masterPanel.add(heightPanel, BorderLayout.CENTER);
		masterPanel.add(weightPanel, BorderLayout.CENTER);

		masterPanel.add(buttonPanel, BorderLayout.EAST);
//		FNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		LNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		DOBPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		heightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		weightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		frame.add(masterPanel);

		frame.setVisible(true);
		return null;

	}

	public void getDataFromTF() {
		try {
			heightValue = Integer.parseInt(heightTF.getText());
			weightValue = Integer.parseInt(weightTF.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Not a valid height or weight.");
		}
	}

	public void onSubmitButtonClick() {
		SubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getDataFromTF();
				updatePlayer(acct.getName(), heightValue, weightValue);
				clear();
				show();

			}

		});

	}

	public void getPlayerData() {
		ResultSet rs;
		String query = "Select P.FName as 'FName', P.Lname as 'LName', P.DOB as 'DOB', PL.Height as 'Height', PL.Weight as 'Weight'"
				+ " from Person P join Player PL on P.ID = PL.ID where P.Username = ?";
		try {
			PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
			stmt.setString(1, this.acct.getName());
			rs = stmt.executeQuery();
			System.out.println(rs.toString());
			parseResults(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseResults(ResultSet rs) {

		try {
			while (rs.next()) {
				this.FNameValue = rs.getString(rs.findColumn("FName"));
				this.LNameValue = rs.getString(rs.findColumn("LName"));
				this.DOBValue = rs.getString(rs.findColumn("DOB"));
				this.heightValue = rs.getInt(rs.findColumn("Height"));
				this.weightValue = rs.getInt(rs.findColumn("Weight"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;

	}

	public boolean updatePlayer(String username, int height, int weight) {
		int returnValue = -1;
		String mess = "";
		try {
			CallableStatement stmt = connection.getConnection().prepareCall("{? = call UpdatePlayerInfo(?, ?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, acct.getName());
			stmt.setInt(3, height);
			stmt.setInt(4, weight);

			stmt.execute();
			returnValue = stmt.getInt(1);
			System.out.println(returnValue);

		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;

		}
		if (returnValue == 0) {
			JOptionPane.showMessageDialog(null, "Height and Weight updated.");

		} else {
			JOptionPane.showMessageDialog(null, "Height and Weight update failed.");

		}
		return true;
	}

}
