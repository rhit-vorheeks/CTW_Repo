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

		JFrame frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);

		CoachRegisterPage crpage = new CoachRegisterPage(frame, connection);
		PlayerRegisterPage prpage = new PlayerRegisterPage(frame, connection);
		RegisterPage regPage = new RegisterPage(frame, prpage, crpage);
		LoginPage lgpage = new LoginPage(frame, regPage, connection);
		TeamPage teamPage = new TeamPage(frame, connection);
		CoachHomePage coachHomePage = new CoachHomePage(frame);
		FindDrillPage findDrillPage = new FindDrillPage(frame);
		FindPlayerStatPage findPlayerStatPage =  new FindPlayerStatPage(frame);
		
		teamPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		coachHomePage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findDrillPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		findPlayerStatPage.savePages(teamPage, findDrillPage, findPlayerStatPage, coachHomePage);
		crpage.savePages(coachHomePage);
		lgpage.savePages(coachHomePage);
		
//		teamPage.show();
		lgpage.show();
//		coachHomePage.show();
		

	}

}
