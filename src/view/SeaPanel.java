package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.GameController;
import model.Ship;
import model.ShipCellList;
import util.ErrorLogger;
import util.Lib;
import util.Position;
import util.SharedObject;

public class SeaPanel extends JPanel{
	private GameController controller;
    private PositionLabel[][] lblPosition; //the label to display and also store the information of the position
    private String displaySide; //explain in constructor
    private Ship[] ships; //the ships contain their location for painting
    private ShipCellList shipCellList;
    private SharedObject turn;
    private List<Position> hitPositionList;
    private List<Position> missPositionList;
    private boolean isSelecting;
    
  //the String displaySide to show that which this panel display for
    //if display for human player, the labels need no mouse listener
    //if display for computer player, they need the mouse listener to listen which button the player click on to attack
    //get the string in the util.Lib
    public SeaPanel(String displaySide, Ship[] ships, ShipCellList shipCellList, SharedObject turn) {
        //init some variable
        this.displaySide = displaySide;
        this.ships = ships;
        this.shipCellList = shipCellList;
        this.turn = turn;
        this.hitPositionList = new ArrayList<Position>();
        this.missPositionList = new ArrayList<Position>();
        isSelecting = false;
        
        

        //set layout for the panel
        this.setLayout(new GridLayout(10, 10));

        //init and add the button to the grid
        lblPosition = new PositionLabel[10][10];
        for (int i = 0; i < lblPosition.length; i++) {
            for (int j = 0; j < lblPosition[i].length; j++) {
                lblPosition[i][j] = new PositionLabel(i,j);
                this.add(lblPosition[i][j]);
            }
        }

        //if the panel is displaying for the computer side, add listener so that it can listen to the click
        if (displaySide.equals(Lib.PLAYER_COMPUTER)) {
            //init the controller
            controller = new GameController(turn, shipCellList);
            //add controler
            for (int i = 0; i < lblPosition.length; i++) {
                for (int j = 0; j < lblPosition[i].length; j++) {
                    lblPosition[i][j].addMouseListener(controller);
                }
            }
        }
    }
    
    
    public void paintFirePosition(Position firePosition, ShipCellList model){
        
        if(model.getLastFireResult()==true){ //hit
            hitPositionList.add(firePosition);
        } else { //miss
            missPositionList.add(firePosition);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            BufferedImage backgroundImage = ImageIO.read(new File(Lib.IMAGES_FOLDER_PATH + "space.jpg"));
            g.drawImage(backgroundImage, 0, 0, null);
            
            
            //check if the panel is displaying for human side then draw the ship images
            if (this.displaySide.equals(Lib.PLAYER_HUMAN)) {
                BufferedImage shipImage;
                for (int i = 0; i < ships.length; i++) {
                        shipImage = ImageIO.read(new File(Lib.IMAGES_FOLDER_PATH + "right" + ships[i].getShipType() + ".png"));
                        g.drawImage(shipImage, ships[i].getStartCol() * 50, ships[i].getStartRow()*50, ships[i].getLength()*50, 50, null);
                }
            }
            //if the hit fire position list is not empty then draw
            if(!hitPositionList.isEmpty()){
                BufferedImage fireImage = ImageIO.read(new File(Lib.IMAGES_FOLDER_PATH + "hit.png"));
                for (Position p : hitPositionList){
                    g.drawImage(fireImage, (int)p.getX()*50, (int)p.getY()*50, 50, 50, this);
                }
            }
            
            //if the miss fire position list is not empty then draw
            if(!missPositionList.isEmpty()){
                BufferedImage fireImage = ImageIO.read(new File(Lib.IMAGES_FOLDER_PATH + "miss.png"));
                for (Position p : missPositionList){
                    g.drawImage(fireImage, (int)p.getX()*50, (int)p.getY()*50, 50, 50, this);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SeaPanel.class.getName()).log(Level.SEVERE, null, ex);
            ErrorLogger.log(ex.getStackTrace());
        }

    }

    public PositionLabel[][] getLblPosition() {
        return lblPosition;
    }
}
