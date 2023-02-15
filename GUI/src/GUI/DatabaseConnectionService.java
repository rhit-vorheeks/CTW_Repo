package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String username, String password) {
		
		String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
		String fullUrl = url.replace("${dbServer}", this.serverName)
				.replace("${dbName}", this.databaseName).replace("${user}", username)
				.replace("${pass}", password);

		try {
			connection = DriverManager.getConnection(fullUrl);
			System.out.println("You are connected");
			return true;
		}
		// Handle any errors that may have occurred.
		catch (

		SQLException e) {
			e.printStackTrace();
			return false;
		}

		// BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			System.out.println("Error Closing Connection");
			e.printStackTrace();
		}

	}

}
