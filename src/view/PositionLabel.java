package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class PositionLabel extends JLabel{
	private int lblRow; //the row of the label
    private int lblCol; //the column of the label
    private boolean isEmpty; //true if there is no ship on this cell, false if there is ship or part of ship on this cell

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    
    public PositionLabel(int col, int row) {
        this.lblCol = col;
        this.lblRow = row;
        this.setPreferredSize(new Dimension(50, 50));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.isEmpty = true;
    }
    public int getLblCol() {
        return lblCol;
    }

    public void setLblCol(int col) {
        this.lblCol = col;
    }

    public int getLblRow() {
        return lblRow;
    }

    public void setLblRow(int row) {
        this.lblRow = row;
    }
}
