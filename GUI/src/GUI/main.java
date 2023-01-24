package GUI;

import java.awt.Dimension;
import GUI.CoachRegisterPage;

import javax.swing.JFrame;

public class main {
	public static final Dimension SCREEN_SIZE = new Dimension(750, 600);
	public static final String title = "Circle The Wagons";

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(title);
		
		RegisterPage regPage = new RegisterPage(frame);
		LoginPage lgpage = new LoginPage(frame);
		PlayerRegisterPage prpage = new PlayerRegisterPage(frame);
		CoachRegisterPage crpage = new CoachRegisterPage(frame);

		regPage.show();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		regPage.clear();
		lgpage.show();
		
//		frame.repaint();

		
		

	}

}
