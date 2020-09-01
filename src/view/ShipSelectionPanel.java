package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class ShipSelectionPanel extends JPanel{
	private ShipLabel[] shipLabels;
	private ShipSelectionButton[] shipButtons;
	
	public ShipSelectionButton[] getBtnShip() {
        return shipButtons;
    }
	public ShipSelectionPanel() {    
        //init the panel
        this.setLayout(new GridLayout(5, 2));
        
        //init the label and the button and add to the panel
        shipLabels = new ShipLabel[5];
        shipButtons = new ShipSelectionButton[5];
        for (int i = 0; i < shipLabels.length; i++) {
            if(i==0){
            	shipLabels[i] = new ShipLabel(i+1);
                shipButtons[i] = new ShipSelectionButton(i+1, "Select Ship: "+(i+2)+" - Size: 1x" + (i+2));
            } else {
            	shipLabels[i] = new ShipLabel(i);
            	shipButtons[i] = new ShipSelectionButton(i, "Select Ship: "+(i+1)+" - Size: 1x" + (i+1));
            	}
            this.add(shipLabels[i]);
            this.add(shipButtons[i]);
        }
        
    }
}
