package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ComputerPlayer;
import model.Ship;
import model.ShipCellList;
import util.Lib;
import util.SharedObject;
import util.SoundUtil;

/**
*
* this frame is designed with Border Layout
* the left is the panel to display the computer space, all ships are hidden
* the right is the panel to display the player space with ships
* the center if to display some information
* the south is to display some buttons
*/
public class GameFrame extends JFrame implements Observer{
	private Ship[] playerShips; //player's ships
    private Ship[] computerShips; //computer's ships
    
    private SeaPanel computerPanel; //the panel to display the computer's space
    private SeaPanel humanPanel; //the panel to display the human player's space
    
    private SharedObject turn; //store the current turn
    private ShipCellList shipCellList;
    private JPanel pnlCenter; //the panel in the center to display some game information and game control buttons
    private JLabel lblPlayerDirection; //show which grid is computer's or player's
    private JPanel pnlComputerInformation; //this show the computer player's info like Score, Last fire info (missed/hit)
    private JLabel lblComputerRemaining; //show the number of remaining cells of computer player
    private JLabel lblComputerLastFireResult; //show the last fire result of computer
    private JPanel pnlHumanInformation; //this show the computer player's info like Score, Last fire info (missed/hit)
    private JLabel lblHumanRemaining; //show the number of remaining cells of computer player
    private JLabel lblHumanLastFireResult; //show the last fire result of human
    private JLabel lblGameStatus; //display game status
    
    private JPanel pnlSouth; //other buttons
    private JButton btnQuit; //show quit button
    private ComputerPlayer computerPlayer;
    
    public ShipCellList getShipCellList() {
        return shipCellList;
    }

    public void setShipCellList(ShipCellList shipCellList) {
        this.shipCellList = shipCellList;
    }

    public SharedObject getTurn() {
        return turn;
    }

    public void setTurn(SharedObject turn) {
        this.turn = turn;
    }
    public GameFrame(Ship[] playerShips, Ship[] computerShips, String difficulty) {
        
        //init the player's ship
        this.playerShips = playerShips;
        this.computerShips = computerShips;
        
        //generate the first turn randomly
        Random r = new Random();
        String firstTurn = "";
        if(r.nextInt(2)==1){
            firstTurn = Lib.PLAYER_HUMAN;
            JOptionPane.showMessageDialog(null, "You go first");
        } else {
            firstTurn = Lib.PLAYER_COMPUTER;
            JOptionPane.showMessageDialog(null, "Computer go first");
        }
        turn = new SharedObject(firstTurn);
        
        //init the model
        this.shipCellList = new ShipCellList(computerShips, playerShips);
        this.shipCellList.addObserver(this);
        
        
        //init the two grid panel
        computerPanel = new SeaPanel(Lib.PLAYER_COMPUTER, computerShips, shipCellList, turn);
        humanPanel = new SeaPanel(Lib.PLAYER_HUMAN, playerShips, shipCellList, turn);
        
        //init the center panel to display some info
        pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setPreferredSize(new Dimension(300, 500));
        lblPlayerDirection = new JLabel("<====== Computer || You ======>");
        pnlCenter.add(lblPlayerDirection,BorderLayout.NORTH);
        pnlComputerInformation = new JPanel(new GridLayout(3, 1));
        lblComputerRemaining = new JLabel("Remaining cells: "+shipCellList.getRemainingCell(Lib.PLAYER_COMPUTER));
        pnlComputerInformation.add(lblComputerRemaining);
        lblComputerLastFireResult = new JLabel("Not fire yet");
        pnlComputerInformation.add(lblComputerLastFireResult);
        pnlHumanInformation = new JPanel(new GridLayout(3, 1));
        lblHumanRemaining = new JLabel("Remaining Cells: "+shipCellList.getRemainingCell(Lib.PLAYER_HUMAN));
        pnlHumanInformation.add(lblHumanRemaining);
        lblHumanLastFireResult = new JLabel("Not fire yet");
        pnlHumanInformation.add(lblHumanLastFireResult);
        pnlCenter.add(pnlComputerInformation,BorderLayout.WEST);
        pnlCenter.add(pnlHumanInformation,BorderLayout.EAST);
        lblGameStatus = new JLabel("Game status: Playing");
        lblGameStatus.setHorizontalAlignment(SwingConstants.CENTER);
        pnlCenter.add(lblGameStatus,BorderLayout.SOUTH);
        
        //init the panel on the south to show some util buttons
        pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnQuit = new JButton("Quit game");
        pnlSouth.add(btnQuit);
        
        //add action listener for the button
        btnQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
        
        //add components to frame
        this.setLayout(new BorderLayout());
        this.add(computerPanel,BorderLayout.WEST);
        this.add(humanPanel,BorderLayout.EAST);
        this.add(pnlCenter,BorderLayout.CENTER);
        this.add(pnlSouth,BorderLayout.SOUTH);
        
        this.computerPlayer = new ComputerPlayer(this.getTurn(), this.getShipCellList(), difficulty);

        //show the frame
        this.pack();
        this.setVisible(true);
        this.computerPlayer.fire();

    }
	private void quitGame(){
		System.exit(-1);
	}
	@Override
    public synchronized void update(Observable o, Object arg) {
        //play the sound
        SoundUtil.playFireSound();
        
        //
        ShipCellList shipCellList = (ShipCellList) arg;
        String lastFireBegin = "<html>Last Fire Result<br/>";
        String lastFireEnd = "</html>";
        String hitTarget = "<font color='green'>Hit Target</font>";
        String missTarget = "<font color='red'>Miss Target</font>";
        if(shipCellList.getLastFirePlayer().equals(Lib.PLAYER_COMPUTER)){
            lblHumanRemaining.setText("Remaining Cells: "+shipCellList.getRemainingCell(Lib.PLAYER_HUMAN));
            lblComputerLastFireResult.setText(shipCellList.getLastFireResult()?lastFireBegin+hitTarget+lastFireEnd:lastFireBegin+missTarget+lastFireEnd);
            humanPanel.paintFirePosition(shipCellList.getLastFirePosition(), this.shipCellList);
            turn.setCurrentTurn(Lib.PLAYER_HUMAN);
        } else {
            lblComputerRemaining.setText("Remaining Cells: "+shipCellList.getRemainingCell(Lib.PLAYER_COMPUTER));
            lblHumanLastFireResult.setText(shipCellList.getLastFireResult()?lastFireBegin+hitTarget+lastFireEnd:lastFireBegin+missTarget+lastFireEnd);
            computerPanel.paintFirePosition(shipCellList.getLastFirePosition(), this.shipCellList);
            turn.setCurrentTurn(Lib.PLAYER_COMPUTER);
            this.computerPlayer.fire();
        }

        //if game ends, show a message to the player and then change the label game status
        if(shipCellList.isGameEnd()){
            String message = "<html>Game Ends<br/>";
            if(shipCellList.getWinTeam().equals(Lib.PLAYER_COMPUTER)){
                message += "You lose. Computer win";
            } else {
                message += "You win. Computer lose";
            }
            lblGameStatus.setText("Game status: End");
            JOptionPane.showMessageDialog(null, message);
            this.dispose();
        }
    }
	
}
