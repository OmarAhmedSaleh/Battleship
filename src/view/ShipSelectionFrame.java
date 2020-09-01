package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.ShipSelectButtonController;
/*
* this frame's layout is Border Layout, the center displays the grid to place ships, the
* right shows the images of ships to select. there are 5 buttons in the right
* for 5 types of ship. in the beginning, all the label in the center grid have
* no mouse listener, so the user can not click on them when the user click on
* one of the 5 buttons on the right to select the ship, assign mouse listener
* for all labels in the center grid after finish placing ship, remove all mouse
* listener of those label
* 
* */
import model.Ship;
import util.Lib;

public class ShipSelectionFrame extends JFrame{
	private ShipSelectionPanel selectionPanel;
	private ShipSelectionGrid grid;
	//the panel of the left to difficulty selection
    private JPanel pnlLeft; //the panel on the left to display some text, help text
    private JLabel lblHelp; //just to display some help text
    private ButtonGroup difficultyButtonGroup;
    private JRadioButton btnEasy;
    private JRadioButton btnHard;
    private JLabel lblDifficulty;
    //the panel on the bottom to show some buttons
    private JPanel pnlSouth;
    private JButton btnPlayGame;
    private JButton btnBack;
    private JButton btnQuit;
    private JButton btnRandom;
    private static ShipSelectionFrame instance;
   
    public synchronized static ShipSelectionFrame getInstance() {
		instance = new ShipSelectionFrame();
    	return instance;
    }

    private ShipSelectionFrame() {
        //set the layout
        this.setLayout(new BorderLayout());
        grid = new ShipSelectionGrid();
        selectionPanel = new ShipSelectionPanel();
        
      //init the center grid panel and add it to the frame
        this.add(grid, BorderLayout.CENTER);

        //init the right panel to show list of ship and add it to the frame
        this.add(selectionPanel, BorderLayout.EAST);
        
        //init the panel on the left to display some text
        pnlLeft = new JPanel(new GridLayout(1, 1));
        lblDifficulty = new JLabel("Difficulty");
        pnlLeft.add(lblDifficulty);
        btnEasy = new JRadioButton("Easy");
        btnEasy.setSelected(true);
        btnHard = new JRadioButton("Hard");
        difficultyButtonGroup = new ButtonGroup();
        difficultyButtonGroup.add(btnEasy);
        difficultyButtonGroup.add(btnHard);
        pnlLeft.add(btnEasy);
        pnlLeft.add(btnHard);

        //init the panel on the south to display some buttons
        pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnBack = new JButton("Go Back");
        btnPlayGame = new JButton("Play Game");
        pnlSouth.add(btnBack);
        pnlSouth.add(btnPlayGame);
        btnRandom = new JButton("Play Using Random Ship Location");
        pnlSouth.add(btnRandom);
        btnQuit = new JButton("Quit Game");
        pnlSouth.add(btnQuit);
//      add action listener for the buttons in pnlRight
      ShipSelectButtonController controller = new ShipSelectButtonController(grid);
      ShipSelectionButton[] btnShip = selectionPanel.getBtnShip();
      for (int i = 0; i < btnShip.length; i++) {
          btnShip[i].addActionListener(controller);
      }
      this.add(pnlLeft, BorderLayout.WEST);
      this.add(pnlSouth, BorderLayout.SOUTH);

        //add action listener for the play game button
        //when click this button, the game will start
        btnPlayGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showGameScreen();
            }
        });
//
        //add action listener for the Go Back Button
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        
        //action listener for quit button
        btnQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        //action listener for random button
        btnRandom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startGameRandom();
            }
        });


        //show the frames
        this.setTitle("Select Your Ships");
        this.setResizable(false);

    }
    public void showFrame() {
    	this.pack();
        this.setVisible(true);
    }
    private void quit() {
        System.exit(-1);
    }
    private void goBack() {
    	this.setVisible(false);
    	StartFrame.getInstance().showFrame();
    }
    //generate random location for the computer's ships
    private Ship[] getGeneratedComputerShips() {
        Ship[] computerShips = new Ship[5];
        boolean[][] computerShipGrid = new boolean[10][10]; //store the grid, true if that cell already has a ship, false otherwise
        for (int i = 0; i < computerShipGrid.length; i++) {
            for (int j = 0; j < computerShipGrid[i].length; j++) {
                computerShipGrid[i][j] = false;
            }
        }
        //generate random location for the ship
        Random random = new Random();
        for (int i = 0; i < computerShips.length; i++) {
            int shipType, shipLength;
            shipType = shipLength = Math.max(i + 1, 2);
            boolean isCellsAvailable = true;
            //check whether the cells is available
            int startCol;
            int startRow;
            do {
                isCellsAvailable = true;
                //first randomize the start postion
                startCol = random.nextInt(10);
                startRow = random.nextInt(10);
                //check all the cell
                if (startCol + shipLength > 9) { //out of the array
                    isCellsAvailable = false;
                } else {
                    for (int j = 0; j <= shipLength; j++) {
                        if (computerShipGrid[startRow][startCol + j]) {
                            isCellsAvailable = false;
                        }
                    }
                }
            } while (!isCellsAvailable);

            //create new ship new ship
            computerShips[i] = new Ship(shipType, startCol, startRow, shipLength);

            //set the cell to true
            for (int j = 0; j <= shipLength; j++) {
                computerShipGrid[startRow][startCol + j] = true;
            }
        }
        return computerShips;
    }
    private String getDifficulty(){
        String difficulty;
        
        if(btnEasy.isSelected()){
            difficulty = Lib.DIFFICULTY_EASY;
        } else {
            difficulty = Lib.DIFFICULTY_HARD;
        }
        
        return difficulty;
    }

    public void startGameRandom() {
        this.setVisible(false);
        Ship[] playerShips = this.getGeneratedComputerShips();
        Ship[] computerShips = this.getGeneratedComputerShips();

        GameFrame gameFrame = new GameFrame(playerShips, computerShips, this.getDifficulty());
    }

    private void showGameScreen() {
        //check if all ships are placed on the grid, then start game
        if (isAllShipSelected()) {
            this.setVisible(false);
            //start game
            Ship[] playerShips = grid.getShips(); //get the list of player's ships
            Ship[] computerShips = this.getGeneratedComputerShips();
            GameFrame gameFrame = new GameFrame(playerShips, computerShips, this.getDifficulty());
        } else {
            //else display a message for the user
            JOptionPane.showMessageDialog(null, "You have not selected all ships!");
        }
    }

    //check whether all ships are selected
    private boolean isAllShipSelected() {
        boolean result = true;
        ShipSelectionButton[] btnShip = selectionPanel.getBtnShip();
        for (int i = 0; i < btnShip.length; i++) {
            if (btnShip[i].isEnabled()) {
                result = false;
            }
        }
        return result;
    }

}
