package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;

public class Main {
	public static final Dimension SCREEN_SIZE = new Dimension(1400, 800);
	public static final String TITLE = "Circle The Wagons";

	public static void main(String[] args) {
		
		DatabaseConnectionService connection = new DatabaseConnectionService("titan.csse.rose-hulman.edu", "CTW_DB");
		connection.connect();
		
		AccountHandler acct = new AccountHandler();
		
		Image img = Toolkit.getDefaultToolkit().getImage("/CTW__GUI/src/basketball-ball-wooden-background-vector-illustration_64749-4313.jpg");
		

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
		
		
//		playerHome.show();
		
//		teamPage.show();
		lgpage.show();
//		coachHomePage.show();
		

	}

}
