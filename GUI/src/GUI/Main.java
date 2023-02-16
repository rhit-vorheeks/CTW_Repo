package GUI;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static final Dimension SCREEN_SIZE = new Dimension(1400, 800);
	public static final String TITLE = "Circle The Wagons";

	public static void main(String[] args) {
		Properties prop = new Properties();
		String binDir = System.getProperty("user.dir") + "/bin/";
		String propFileName = binDir + "app.properties";
		
		try {
			InputStream inputStream = new FileInputStream(propFileName);
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} 
		}
		catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Could not find app.properties file");
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Could not read app.properties file, please check the formatting of the file.");
		}


		// get the property value and print it out
		String serverUsername = prop.getProperty("serverUsername");
		String serverPassword = prop.getProperty("serverPassword");
		String serverName = prop.getProperty("serverName");
		String databaseName = prop.getProperty("databaseName");

		DatabaseConnectionService connection =  new DatabaseConnectionService(serverName, databaseName);
		connection.connect(serverUsername, serverPassword);
		
		AccountHandler acct = new AccountHandler();
		
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);
		
		CoachRegisterPage crpage = new CoachRegisterPage(frame, connection);
		PlayerRegisterPage prpage = new PlayerRegisterPage(frame, connection);
		RegisterPage regPage = new RegisterPage(frame);
		LoginPage lgpage = new LoginPage(frame, regPage, connection, acct);
		TeamPage teamPage = new TeamPage(frame, connection, acct);
		CoachHomePage coachHomePage = new CoachHomePage(frame, connection, acct);
		FindDrillPage findDrillPage = new FindDrillPage(frame, connection);
		FindPlayerStatPage findPlayerStatPage =  new FindPlayerStatPage(frame, connection, acct);
		
		// Program Pages
		teamPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage, lgpage);
		coachHomePage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage, lgpage);
		findDrillPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage, lgpage);
		findPlayerStatPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage, lgpage);
		
		//Player Pages 
		PlayerFindDrillPage playerfindDrillPage = new PlayerFindDrillPage(frame, connection);
		PlayerHomePage playerHome = new PlayerHomePage(frame, connection, acct);
		PlayerUserPage playeruserPage = new PlayerUserPage(frame, connection, acct);
		PlayerAddStatPage playerAddStatPage = new PlayerAddStatPage(frame, connection, acct);
		playerHome.savePages(playerHome, playerfindDrillPage, playeruserPage, playerAddStatPage, lgpage);
		playerfindDrillPage.savePages(playerHome, playerfindDrillPage, playeruserPage, playerAddStatPage, lgpage);
		playeruserPage.savePages(playerHome, playerfindDrillPage, playeruserPage, playerAddStatPage, lgpage);
		playerAddStatPage.savePages(playerHome, playerfindDrillPage, playeruserPage, playerAddStatPage, lgpage);
		
		
		// Registration Pages
		crpage.savePages(coachHomePage, regPage);
		lgpage.savePages(coachHomePage, playerHome);
		prpage.savePages(regPage, playerHome);
		regPage.savePages(prpage, crpage, lgpage);
		
		lgpage.show();

	}

}
