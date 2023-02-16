package Pop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class Main {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	public static ArrayList<CallableStatement> createCallableHasStat(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\HasStatData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		int quan = 0;
		String name = null;
		String username = null;
		String date = null;

		for (int j = 0; j < data.size(); j += 4) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			username = data.get(j + 1);
			date = data.get(j + 2);
			quan = Integer.parseInt(data.get(j + 3));

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddHasStat(?,?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, username);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date2 = sdf.parse(date);
				java.sql.Date d = new java.sql.Date(date2.getTime());
				stmt.setDate(4, d);

				stmt.setInt(5, quan);

				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableTargets(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\Targets.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		int id = 0;

		for (int j = 0; j < data.size() - 1; j += 2) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			id = Integer.parseInt(data.get(j + 1));

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddTargets(?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setInt(3, id);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableAddStat(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\StatData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		String desc = null;
		String type = null;

		for (int j = 0; j < data.size(); j += 3) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			desc = data.get(j + 1);
			type = data.get(j + 2);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddStat(?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, desc);
				stmt.setString(4, type);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallablePosition(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\PositionData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		String desc = null;

		for (int j = 0; j < data.size(); j += 2) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			desc = data.get(j + 1);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddPosition(?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, desc);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallablePlaysOn(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\PlaysOnData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		int teamID = 0;
		String name = null;
		String positionName = null;
		String startDate = null;
		String endDate = null;

		for (int j = 0; j < data.size(); j += 5) {

			teamID = Integer.parseInt(data.get(j));
			name = data.get(j + 1);
			positionName = data.get(j + 2);
			startDate = data.get(j + 3);
			endDate = data.get(j + 4);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddPlaysOn(?,?,?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setInt(2, teamID);
				stmt.setString(3, name);
				stmt.setString(4, positionName);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = sdf.parse(startDate);
				java.sql.Date d = new java.sql.Date(date.getTime());

				stmt.setDate(5, d);

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
//				java.util.Date date2 = sdf2.parse(endDate);
				java.util.Date date2 = sdf2.parse(endDate);

				java.sql.Date d2 = new java.sql.Date(date2.getTime());

				stmt.setDate(6, null);

				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableCoaches(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\Coaches.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		int teamID = 0;
		String startDate = null;
		String endDate = null;

		for (int j = 0; j < data.size(); j += 4) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			teamID = Integer.parseInt(data.get(j + 1));
			startDate = data.get(j + 2);
			endDate = data.get(j + 3);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddCoaches(?,?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setInt(3, teamID);

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = sdf.parse(startDate);
				java.sql.Date d = new java.sql.Date(date.getTime());

				stmt.setDate(4, d);

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
//				java.util.Date date2 = sdf2.parse(endDate);
				java.util.Date date2 = sdf2.parse(endDate);

				java.sql.Date d2 = new java.sql.Date(date2.getTime());

				stmt.setDate(5, null);

				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableCoach(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\CoachData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		String rank = null;

		for (int j = 0; j < data.size() - 1; j += 2) {

			name = data.get(j);
			if (name.indexOf("﻿") == 0) {
				name = name.replaceAll("﻿", "");
			}
			rank = data.get(j + 1);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddCoach(?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, rank);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableTeam(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\Team.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		String League = null;

		for (int j = 0; j < data.size() - 1; j += 2) {

			name = data.get(j);
			League = data.get(j + 1);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddTeam(?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, League);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallableDrill(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\Drill.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String name = null;
		String desc = null;

		for (int j = 0; j < data.size() - 1; j += 2) {

			name = data.get(j);
			desc = data.get(j + 1);

			if (name.indexOf("﻿") != -1) {
				name = name.replaceAll("﻿", "");
			}

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddDrill(?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, name);
				stmt.setString(3, desc);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static ArrayList<CallableStatement> createCallablePlayer(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\PlayerData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();

		String username = null;
		int height;
		int weight;

		for (int j = 0; j < data.size(); j += 3) {

			username = data.get(j);
			height = Integer.parseInt(data.get(j + 1));
			weight = Integer.parseInt(data.get(j + 2));

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddPlayer(?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				stmt.setString(2, username);
				stmt.setInt(3, height);
				stmt.setInt(4, weight);
				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public static String hashPassword(byte[] salt, String password) {

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

	public static String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public static ArrayList<CallableStatement> createCallablePerson(Connection connection) throws ParseException {
		ArrayList<String> data = importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\PersonData.csv");
		ArrayList<CallableStatement> stmts = new ArrayList<CallableStatement>();
		System.out.println(data.toString());
		System.out.println();
		String DOB = null;
		String FName = null;
		String LName = null;
		String Username = null;
		String Password = null;
		String Type = null;
		String Salt = null;

		for (int j = 0; j < data.size(); j += 7) {

			FName = data.get(j);
			LName = data.get(j + 1);
			DOB = data.get(j + 2);
			Username = data.get(j + 3);
			Password = data.get(j + 4);
			Salt = data.get(j + 5);
			Type = data.get(j + 6);

			byte[] newSalt = getNewSalt();
			String newPassHash = hashPassword(newSalt, Password);

			System.out.printf("Line 464: Fname %s\nLname %s\nDOB %s\nUsername %s\nPassword %s\nSalt %s\nType %s\n\n",
					FName, LName, DOB, Username, newPassHash, newSalt, Type);

			try {
				CallableStatement stmt = connection.prepareCall("{? = call AddPerson(?,?,?,?,?,?,?)}");
				stmt.registerOutParameter(1, Types.INTEGER);

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = sdf.parse(DOB);
				java.sql.Date d = new java.sql.Date(date.getTime());

				stmt.setDate(2, d);
				stmt.setString(3, FName);
				stmt.setString(4, LName);
				stmt.setString(5, Username);
				stmt.setString(6, newPassHash);
				stmt.setString(7, Type);
				stmt.setBytes(8, newSalt);

				stmts.add(stmt);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return stmts;

	}

	public static void connect(String serverName, String databaseName, String serverUsername, String serverPassword)
			throws ParseException {
		System.out.println("About to Connect");
		String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
		String fullUrl = url.replace("${dbServer}", serverName).replace("${dbName}", databaseName)
				.replace("${user}", serverUsername).replace("${pass}", serverPassword);
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(fullUrl);
			ArrayList<ArrayList<CallableStatement>> allStmts = new ArrayList<ArrayList<CallableStatement>>();

			allStmts.add(createCallablePerson(connection));
			allStmts.add(createCallableDrill(connection));
			allStmts.add(createCallablePlayer(connection));
			allStmts.add(createCallableCoach(connection));
			allStmts.add(createCallableTeam(connection));
			allStmts.add(createCallableCoaches(connection));
			allStmts.add(createCallablePosition(connection));
			allStmts.add(createCallablePlaysOn(connection));
			allStmts.add(createCallableAddStat(connection));
			allStmts.add(createCallableTargets(connection));
			allStmts.add(createCallableHasStat(connection));

			for (ArrayList<CallableStatement> stmts : allStmts) {
				for (CallableStatement stmt : stmts) {

					try {
						System.out.println("We are about to execute");
						stmt.execute();
						int returnCode = stmt.getInt(1);
						System.out.println("Executed ");
						System.out.println(returnCode);
						if (returnCode == 0) {
							System.out.println("Success!");
						} else {
							System.out.println("Procedure reported an error.");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (connection != null && !connection.isClosed()) {
//							connection.close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<String> importData(String path) {
		ArrayList<String> data = new ArrayList<String>();

		int i = 1;
		try {
			BufferedReader r = new BufferedReader(new FileReader(path));
			String line = r.readLine();
			while (line != null) {
				String[] data2 = line.split(",");
				for (String s : data2) {
					data.add(s);
				}
				line = r.readLine();
				i++;
			}
			r.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return data;
	}

	public static void main(String[] args) {
		System.out.println("Ready");
		// Loads values in from property file
		Properties prop = new Properties();
		String binDir = System.getProperty("user.dir") + "/bin/";
		String propFileName = binDir + "app.properties";

		try {
			InputStream inputStream = new FileInputStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Could not find app.properties file");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not read app.properties file, please check the formatting of the file.");
		}

		// get the property value and print it out
		String serverUsername = prop.getProperty("serverUsername");
		String serverPassword = prop.getProperty("serverPassword");
		String serverName = prop.getProperty("serverName");
		String databaseName = prop.getProperty("databaseName");

		// Connect will connect to the database and execute all of the callable
		// statements
		try {
			connect(serverName, databaseName, serverUsername, serverPassword);
//			importData("C:\\Users\\duvallar\\git\\CTW_Repo\\SampleData\\PersonData.csv");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
