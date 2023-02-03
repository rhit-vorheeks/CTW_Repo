package GUI;

import java.awt.Dimension;
import GUI.CoachRegisterPage;

import javax.swing.JFrame;

public class Main {
	public static final Dimension SCREEN_SIZE = new Dimension(1400, 800);
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
		FindPlayerStatPage findPlayerStatPage =  new FindPlayerStatPage(frame, connection, acct);
		
		// Program Pages
		teamPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		coachHomePage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findDrillPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findPlayerStatPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		
		//Player Pages 
		PlayerFindDrillPage playerfindDrillPage = new PlayerFindDrillPage(frame, connection);
		PlayerHomePage playerHome = new PlayerHomePage(frame, connection, acct);
		playerHome.savePages(playerHome, playerfindDrillPage);
		playerfindDrillPage.savePages(playerHome, playerfindDrillPage);
		
		// Registration Pages
		crpage.savePages(coachHomePage, regPage);
		lgpage.savePages(coachHomePage, playerHome);
		prpage.savePages(regPage, playerHome);
		regPage.savePages(prpage, crpage, lgpage);
		
		
//		playerHome.show();
		
//		teamPage.show();
		lgpage.show();
//		coachHomePage.show();
		

	}

}
