package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import util.ErrorLogger;
import util.Lib;
import util.Position;

//  it stores all the cells that contains ship of both computer and human player

public class ShipCellList extends Observable{
	 private List<Position> computerShipCellList; // store all the cells of computer player that have ship
	    private List<Position> humanShipCellList; // store all the cells of human player that have ship
	    private String lastFirePlayer; //the last player has just fired
	    private boolean lastFireResult; //the last fire result of the player has just fired
	    private Position lastFirePosition;

	    //receive the array of ship and then convert them into cells
	    public ShipCellList(Ship[] computerShips, Ship[] humanShips) {
	        computerShipCellList = this.getShipCellList(computerShips);
	        humanShipCellList = this.getShipCellList(humanShips);
	    }
	    
	  //get all the cells that contain ship
	    private List<Position> getShipCellList(Ship[] shipList) {
	        List<Position> cellList = new ArrayList<Position>();
	        for (int i = 0; i < shipList.length; i++) {
	            for (int j = 0; j < shipList[i].getLength(); j++) {
	                Position p = new Position(shipList[i].getStartCol() + j, shipList[i].getStartRow());
	                cellList.add(p);
	            }
	        }
	        return cellList;
	    }
	  //get the number of remaining cells
	    public int getRemainingCell(String player){
	        if(player.equals(Lib.PLAYER_COMPUTER)){
	            return computerShipCellList.size();
	        } else {
	            return humanShipCellList.size();
	        }
	    }
	    public String getLastFirePlayer() {
	        return lastFirePlayer;
	    }

	    public boolean getLastFireResult() {
	        return lastFireResult;
	    }

	    public Position getLastFirePosition() {
	        return lastFirePosition;
	    }
	  //check if one of the player has the shipList with no element, the game end
	    public boolean isGameEnd() {
	        boolean result = false;
	        if (computerShipCellList.isEmpty() || humanShipCellList.isEmpty()) {
	            result = true;
	        }
	        return result;
	    }
	    public String getWinTeam() {
	        String result = "";
	        if (isGameEnd()) {
	            result = computerShipCellList.isEmpty() ? Lib.PLAYER_HUMAN : Lib.PLAYER_COMPUTER;
	        }
	        return result;
	    }
	    public boolean computerFireHuman(Position firePosition) {
	        this.lastFireResult = this.fire(humanShipCellList, firePosition);
	        this.lastFirePlayer = Lib.PLAYER_COMPUTER;
	        this.lastFirePosition = firePosition;
	        setChanged();
	        notifyObservers(this);
	        return this.lastFireResult;
	    }
	    public void humanFireComputer(Position firePosition) {
	        //System.out.println(firePosition.getX() + "" + firePosition.getY());
	        this.lastFireResult = this.fire(computerShipCellList, firePosition);
	        this.lastFirePlayer = Lib.PLAYER_HUMAN;
	        this.lastFirePosition = firePosition;
	        setChanged();
	        notifyObservers(this);
	    }
	  //return the fire result, false is miss
	    private boolean fire(List<Position> shipCellList, Position firePosition) {
	        boolean fireResult = false;
	        try{
	            for(Position p : shipCellList){
	                    if(p.getX()==firePosition.getX() && p.getY()==firePosition.getY()){
	                        fireResult=true;
	                        shipCellList.remove(p);
	                        break;
	                    }
	                }
	        } catch (Exception ex){
	            System.out.println(ex.getMessage());
	            ErrorLogger.log(ex.getStackTrace());
	        }
	                
	        return fireResult;
	    }


}
