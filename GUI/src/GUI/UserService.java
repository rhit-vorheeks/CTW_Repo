package GUI;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}

	public String login(String username, String password) {
		byte[] passsalt = null;
		String passHash = "";
		String passType="";

		try {
			PreparedStatement myStmt = dbService.getConnection().prepareStatement(
					"SELECT Salt, Password, Type\nFROM [Person]\nWhere Username = ?");
			myStmt.setString(1, username);
			ResultSet rs = myStmt.executeQuery();
						
			while (rs.next()) {
				passsalt = rs.getBytes(rs.findColumn("Salt"));
				passHash = rs.getString(rs.findColumn("Password"));
				passType = rs.getString(rs.findColumn("Type"));
				String newHash = hashPassword(passsalt, password);
				if (newHash.equals(passHash)) {
					System.out.println("You Logged in");
					return passType;
				} else {
					JOptionPane.showMessageDialog(null, "Login Failed");
					return "";
				}
			}
			
			JOptionPane.showMessageDialog(null, "Please Register.");
			//test

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	public boolean register(String username, String password,String FName, String LName,String DOB, String Username, String Type) {
		// Task 7
		if (password == null || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Registration Failed");
			return false;
		}
		byte[] newSalt = getNewSalt();
		String newPassHash = hashPassword(newSalt, password);
		int returnValue = -1;
		try {
		
			CallableStatement stmt = dbService.getConnection().prepareCall("{? = call AddPerson(?,?,?,?,?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = null;
			try {
				date = sdf.parse(DOB);
			} catch (ParseException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Please Enter a valid DOB");
				return false;
			}
			java.sql.Date d = new java.sql.Date(date.getTime());

			stmt.setDate(2, d);
			stmt.setString(3, FName);
			stmt.setString(4, LName);
			stmt.setString(5, Username);
			stmt.setString(6, newPassHash);
			stmt.setString(7, Type);
			stmt.setBytes(8, newSalt);
			
			
			stmt.execute();
			returnValue = stmt.getInt(1);
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		if (returnValue == 0) {
			System.out.println("Success");
			return true;
		} else if (returnValue ==1) { // catch all error return values
			JOptionPane.showMessageDialog(null, "First or Last name cannot be empty.");
		}else if (returnValue ==2) { // catch all error return values
			JOptionPane.showMessageDialog(null, "DOB cannot be empty.");
		}else if (returnValue ==3) { // catch all error return values
			JOptionPane.showMessageDialog(null, "Username or Password cannot be empty.");
		}else if (returnValue ==5) { // catch all error return values
			JOptionPane.showMessageDialog(null, "Username is already taken");
		}
		
		
		
		return false;	

	}

	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}
	
		


}
