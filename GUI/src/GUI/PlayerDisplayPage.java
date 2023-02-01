package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
//import java.swing.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableColumn;

public abstract class PlayerDisplayPage extends AbstractPage{

	
		// Panels
		private JPanel textPanel;
		private JPanel buttonPanel;
		private JPanel masterPanel;

		// Buttons
		private JButton HomeButton;
		private JButton FindDrillButton;
		private JButton AddPlayerStatButton;
//		private JButton TeamButton;
		private JButton UserButton;

		
		//Pages 
		private CoachHomePage homePage = null;
		private FindDrillPage findDrillPage = null;
		private FindPlayerStatPage findPlayerStatPage = null;
		private TeamPage teamPage = null;
		
		
	public PlayerDisplayPage(JFrame frame){
		super(frame);

		// Panels
		this.textPanel = new JPanel();
		this.buttonPanel = new JPanel();
		this.masterPanel = new JPanel();

		// Buttons
		HomeButton = new JButton("Home");
		UserButton = new JButton("User");
		AddPlayerStatButton = new JButton("Add Stats");
		FindDrillButton = new JButton("Find a Drill");
//		TeamButton = new JButton("Team");
		
		this.buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));		
		this.masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		onUserButtonClick();
		onFindDrillButtonClick();
		onAddPlayerStatButtonClick();
		onHomeButtonClick();
		
		
	}

	public JPanel show() {
		JFrame frame = this.getFrame();

		buttonPanel.add(HomeButton, BorderLayout.NORTH);
		buttonPanel.add(FindDrillButton, BorderLayout.NORTH);
		buttonPanel.add(AddPlayerStatButton, BorderLayout.NORTH);
		buttonPanel.add(UserButton, BorderLayout.NORTH);

		buttonPanel.setMaximumSize(new Dimension(700, 100));
//		textPanel.setMaximumSize(new Dimension(1400, 100));
//		buttonPanel.setBackground(Color.red);
		masterPanel.add(buttonPanel, BorderLayout.CENTER);
//		masterPanel.add(textPanel, BorderLayout.NORTH);
//		textPanel.setBackground(Color.CYAN);
//		masterPanel.setBackground(Color.green);
		

		frame.add(masterPanel);

		frame.setVisible(true);
		return masterPanel;
	}
	
	public void savePages(TeamPage teamPage, FindDrillPage findDrillPage, FindPlayerStatPage findPlayerStatPage, CoachHomePage coachHomePage) {
		this.teamPage = teamPage;
		this.findDrillPage = findDrillPage;
		this.findPlayerStatPage = findPlayerStatPage;
		this.homePage = coachHomePage;
	
	}
	
	
	public void onUserButtonClick() {
		UserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				clear();
//				teamPage.show();
				System.out.println("Clicked User");
								
			}

		});
	}

	public void onFindDrillButtonClick() {
		FindDrillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Find Drill");
//				clear();
//				findDrillPage.show();
			}

		});
	}
	
	public void onAddPlayerStatButtonClick() {
		AddPlayerStatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Add a Player Stat");
//				clear();
//				findPlayerStatPage.show();
			}

		});
	}
	
	public void onHomeButtonClick() {
		HomeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Home");
//				clear();
//				homePage.refreshTable();
//				homePage.show();
			}

		});
	}
	

	
}

