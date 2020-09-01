package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import model.Ship;
import view.PositionLabel;
import view.ShipSelectionGrid;

public class LabelMouseListener extends MouseAdapter{

    private ShipSelectionGrid grid;
    private int shipType;
    private PositionLabel label;

    public LabelMouseListener(ShipSelectionGrid grid, int shipType) {
        this.grid = grid;
        this.shipType = shipType;
    }

    private boolean isCellCanPlaceShip() {
        boolean result = true;
        int shipLength = shipType;
        //check whether the length is  out of the grid
        if (label.getLblRow() + shipLength > 9) {
            result = false;
        } else {
            PositionLabel[][] lblPosition = grid.getLblPosition();
            for (int i = 0; i <= shipLength; i++) {
                if (!lblPosition[label.getLblCol()][label.getLblRow() + i].isEmpty()) {
                    result = false;
                }
            }
        }
        return result;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //this is the clicked label
        this.label = (PositionLabel) e.getSource();
        
        //remove the color of the label
        this.label.setOpaque(false);
        this.label.setBackground(null);

        //check if if all the cell (label) which place the ship is available
        //then paint the ship, add it to the Ship[] array, set the label to not available
        //else display an alert for the user that can not place the ship at the cell
        if (isCellCanPlaceShip()) {
            //get the old ships array
            //then create new array with 1 more elements
            //add the new ship to the end of the new array
            Ship[] oldShips = grid.getShips();
            Ship[] newShip;
            if (oldShips == null) {
                newShip = new Ship[1];
            } else {
                newShip = new Ship[oldShips.length + 1];
                for (int i = 0; i < newShip.length - 1; i++) {
                    newShip[i] = oldShips[i];
                }
            }
            newShip[newShip.length - 1] = new Ship(shipType + 1, label.getLblRow(), label.getLblCol(), shipType + 1);
            //set all the labels that contain the ship to not empty
            PositionLabel[][] lblPosition = grid.getLblPosition();
            for (int i = 0; i < shipType + 1; i++) {
                PositionLabel lbl = lblPosition[label.getLblCol()][label.getLblRow() + i];
                lbl.setIsEmpty(false);
            }

            //paint the ship
            grid.paintShips(newShip);

            //after painting, remove all mouse listeners of the label[][]
            for (int i = 0; i < lblPosition.length; i++) {
                for (int j = 0; j < lblPosition[i].length; j++) {
                    lblPosition[i][j].removeMouseListener(lblPosition[i][j].getMouseListeners()[0]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You can not place the ship here! Please chose other place!");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        PositionLabel currentLabel = (PositionLabel) e.getSource();
        currentLabel.setOpaque(true);
        currentLabel.setBackground(Color.RED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        PositionLabel currentLabel = (PositionLabel) e.getSource();
        currentLabel.setOpaque(false);
        currentLabel.setBackground(null);
    }
}
