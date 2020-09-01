/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ShipCellList;
import util.Lib;
import util.Position;
import util.SharedObject;

public class ComputerPlayer{

    private SharedObject turn;
    private AIController aiController;
    private ShipCellList shipCellList;
    private String difficulty;
    
    public ComputerPlayer(SharedObject turn, ShipCellList shipCellList, String difficulty) {
        this.turn = turn;
        this.shipCellList = shipCellList;
        this.difficulty = difficulty;
        aiController = new AIController(this.difficulty);
    }
    
    public synchronized void fire() {
        if(!shipCellList.isGameEnd() && !turn.getCurrentTurn().equals(Lib.PLAYER_HUMAN)){
        	Position firePoint = aiController.getAttackPoint();
        	boolean fireResult = shipCellList.computerFireHuman(firePoint);
        	aiController.setFireResult(firePoint, fireResult);
        }
    }
    
}
