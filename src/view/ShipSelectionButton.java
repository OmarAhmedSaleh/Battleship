package view;

import javax.swing.JButton;

public class ShipSelectionButton extends JButton{
	private int shipType;
	
	public ShipSelectionButton(int shipType, String displayText) {
		super(displayText);
        this.shipType = shipType;
	}
	public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }
	
}
