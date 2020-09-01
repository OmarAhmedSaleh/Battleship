package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import util.Lib;
import util.Position;

public class AIController {
	private String difficulty;
	private Position leftFirePosition;
	private Position rightFirePostion;
	/*
     * store the grid =0: this has not been fire >0: hit target <0: miss target
     */
	
	private int[][] gridPosition;
	private ArrayList<Position> possiablePositions;

    public AIController(String difficulty) {
        this.difficulty = difficulty;
        gridPosition = new int[10][10];
        for (int i = 0; i < gridPosition.length; i++) {
            for (int j = 0; j < gridPosition.length; j++) {
                gridPosition[i][j] = 0;
            }
        }
        possiablePositions = new ArrayList<Position>();
        for(int i = 0; i < 10; i++) {
    		for(int j = 0;j < 10; j++) {
    			possiablePositions.add(new Position(i , j));
    		}
    	}
    	Collections.shuffle(possiablePositions);
        leftFirePosition = null;
        rightFirePostion = null;
    }

    public Position getAttackPoint() {
        if (difficulty.equals(Lib.DIFFICULTY_EASY)) {
            return getAttackPointEasy();
        } else {
            return getAttackPointHard();
        }
    }
    private Position getAttackPointEasy() {
        Position p = new Position(0 ,0);
        	Collections.shuffle(this.possiablePositions);
        	for(int i = 0;i < possiablePositions.size(); i++) {
        		if(isFired(possiablePositions.get(i)) == 0) {
        			p = possiablePositions.get(i);
        			possiablePositions.remove(i);
        			break;
        		}
        	}
        return p;
    }
    //check whether this position is fired before or not
    private int isFired(Position p) {
        int result = 0;
        result = gridPosition[p.getX()][p.getY()];
        return result;
    }
    private Position getAttackPointHard() {
        Position firePosition = null;

        if (leftFirePosition == null && rightFirePostion == null) { //pick random position
            firePosition = getAttackPointEasy();
        } else if (leftFirePosition != null) { //continue fire to the left
            firePosition = leftFirePosition;
        } else { //continue fire to the right
            firePosition = rightFirePostion;
        }

        return firePosition;
    }
    public void setFireResult(Position p, boolean result) {
        if (result) { //HIT
            gridPosition[p.getX()][p.getY()] = 1;
            
            //this is for hard level
            if(leftFirePosition == null) {
            	if (p.getX() != 0) { //ensure that the value is not out of the grid
                    leftFirePosition = new Position(p.getX() - 1, p.getY()); //go to the left 1 cell
                    if (isFired(leftFirePosition) != 0) { //if this was fired before, set to null
                        leftFirePosition = null;
                    }
                }
            }else {
            	if (leftFirePosition.getX() == p.getX()) { //last time, fire to the left hit, continue go to the left
            		if (p.getX() != 0) { //ensure that the value is not out of the grid
            			leftFirePosition = new Position(p.getX() - 1, p.getY());
            			if (isFired(leftFirePosition) != 0) { //if this was fired before, set to null
            				leftFirePosition = null;
            				}
            			} else { //go to the edge of the grid, stop, just set the fire postion to null
            				leftFirePosition = null;
            			}
            		}
            	}
            if (rightFirePostion == null) { //first time hit
                if (p.getX() < 9) { //ensure that the value is not out of the grid
                    rightFirePostion = new Position(p.getX() + 1, p.getY()); //go to the right 1 cell
                    if (isFired(rightFirePostion) != 0) {//if this was fired before, set to null
                        rightFirePostion = null;
                    }
                }
            } else {
                if (rightFirePostion.getX() == p.getX()) { //last time, fire to the right hit, continue go to the right
                    if (p.getX() < 9) { //ensure that the value is not out of the grid
                        rightFirePostion = new Position(p.getX() + 1, p.getY());
                        if (isFired(rightFirePostion) != 0) {//if this was fired before, set to null
                            rightFirePostion = null;
                        }
                    } else { //go to the edge of the grid, stop, just set the fire postion to null
                        rightFirePostion = null;
                    }
                }
            }
        } else { //MISS
            gridPosition[p.getX()][p.getY()] = -1;
             if (leftFirePosition != null) { 
                if (leftFirePosition.getX() == p.getX()) { //firing to the left does not hit anymore, stop moving to the left, just set to null
                    leftFirePosition = null;
                }
             }
             if (rightFirePostion != null) {
                if (rightFirePostion.getX() == p.getX()) { //firing to the right does not hit anymore, stop moving to the right, just set to null
                    rightFirePostion = null;
                }
            }
        }
    }

}
