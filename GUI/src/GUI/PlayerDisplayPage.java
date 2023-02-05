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
		private PlayerFindDrillPage findDrillPage = null;
		private PlayerHomePage playerHomePage;
		private PlayerUserPage playerUserPage;
		
		
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

		buttonPanel.add(HomeButton, BorderLayout.CENTER);
		buttonPanel.add(FindDrillButton, BorderLayout.CENTER);
		buttonPanel.add(AddPlayerStatButton, BorderLayout.CENTER);
		buttonPanel.add(UserButton, BorderLayout.CENTER);

		buttonPanel.setMaximumSize(new Dimension(1400, 100));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		masterPanel.add(buttonPanel, BorderLayout.CENTER);

		frame.add(masterPanel);
		frame.setVisible(true);
		return masterPanel;
	}
	
	public void savePages(PlayerHomePage playerHomePage, PlayerFindDrillPage findDrillPage, PlayerUserPage playerUserPage) {
		this.playerHomePage = playerHomePage;
		this.findDrillPage = findDrillPage;
		this.playerUserPage = playerUserPage;
	
	}
	
	
	public void onUserButtonClick() {
		UserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				playerUserPage.show();
				System.out.println("Clicked User");
								
			}

		});
	}

	public void onFindDrillButtonClick() {
		FindDrillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Find Drill");
				clear();
				findDrillPage.show();
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
				clear();
//				playerHomePage.refreshTable();
				playerHomePage.show();
			}

		});
	}
	

	
}

