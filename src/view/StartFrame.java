package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartFrame extends JFrame{
	private StartImagePanel imagePanel;
	private JButton startGameButton, exitButton;
	private JPanel buttonsContainerPanel;
	private static StartFrame instance ;
	
	private StartFrame() {
		super("Battle Ship");
		setup();		
	}
	public static synchronized StartFrame getInstance() {
		if(instance == null) {
			instance = new StartFrame();
		}
		return instance;
	}
	private void setup() {
		// display image
		this.setLayout(new BorderLayout());
		imagePanel = StartImagePanel.getInstance();
		this.add(imagePanel, BorderLayout.CENTER);
		
        //init the panel contains some buttons
		buttonsContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		startGameButton = new JButton("Start Game");
		exitButton = new JButton("Exit");
		
		
		addListenersToButtons();
		buttonsContainerPanel.add(startGameButton);
		buttonsContainerPanel.add(exitButton);
		this.add(buttonsContainerPanel, BorderLayout.SOUTH);
	}
	public void showFrame() {
		 //init the frame and show
//      this.setLocation(ScreenDisplay.getDisplayLocation(this));
        this.pack();
        this.setVisible(true);
	}
	private void addListenersToButtons() {
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}	
		});
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveToShipSelectionFrame();
			}	
		});
		
	}
	private void quit(){
		System.exit(-1);
	}
	private void moveToShipSelectionFrame() {
		this.setVisible(false);
		ShipSelectionFrame.getInstance().showFrame();
	}
}
