package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.ShipCellList;
import util.Lib;
import util.Position;
import util.SharedObject;
import view.PositionLabel;

public class GameController extends MouseAdapter{
	private SharedObject turn;
    private ShipCellList shipCellList;
    
    public GameController(SharedObject turn, ShipCellList shipCellList){
        this.turn = turn;
        this.shipCellList = shipCellList;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        PositionLabel lblFire = (PositionLabel) e.getSource();
        //if the current turn is human, let they go
        if(turn.getCurrentTurn().equals(Lib.PLAYER_HUMAN)){
            Position firePosition = new Position(lblFire.getLblRow(), lblFire.getLblCol());
            shipCellList.humanFireComputer(firePosition);
        }
        lblFire.removeMouseListener(lblFire.getMouseListeners()[0]);
    }
}
