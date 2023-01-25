package GUI;

import java.awt.Dimension;
import GUI.CoachRegisterPage;

import javax.swing.JFrame;

public class Main {
	public static final Dimension SCREEN_SIZE = new Dimension(750, 600);
	public static final String TITLE = "Circle The Wagons";

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);

		CoachRegisterPage crpage = new CoachRegisterPage(frame);
		PlayerRegisterPage prpage = new PlayerRegisterPage(frame);
		RegisterPage regPage = new RegisterPage(frame, prpage, crpage);
		LoginPage lgpage = new LoginPage(frame, regPage);

		lgpage.show();
		

	}

}
