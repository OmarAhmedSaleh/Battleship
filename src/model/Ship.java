package model;

public class Ship {
	private int shipType; //the type of the ship
    private int startRow; //the row where the ship start (for paiting)
    private int startCol; //the column where the ship start (for painting)
    private int length;//length of the ship

    public Ship(int shipType, int startCol,int startRow, int length) {
        this.shipType = shipType;
        this.startRow = startRow;
        this.startCol = startCol;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int lenght) {
        this.length = lenght;
    }

    public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
}
