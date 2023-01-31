package GUI;

import java.awt.Dimension;
import GUI.CoachRegisterPage;

import javax.swing.JFrame;

public class Main {
	public static final Dimension SCREEN_SIZE = new Dimension(750, 600);
	public static final String TITLE = "Circle The Wagons";

	public static void main(String[] args) {
		
		DatabaseConnectionService connection = new DatabaseConnectionService("titan.csse.rose-hulman.edu", "CTW_DB");
		connection.connect();
		
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
		FindPlayerStatPage findPlayerStatPage =  new FindPlayerStatPage(frame);
		
		// Program Pages
		teamPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		coachHomePage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findDrillPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findPlayerStatPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		
		// Registration Pages
		crpage.savePages(coachHomePage, regPage);
		lgpage.savePages(coachHomePage);
		prpage.savePages(regPage);
		regPage.savePages(prpage, crpage, lgpage);
		
//		teamPage.show();
		lgpage.show();
//		coachHomePage.show();
		

	}

}
