package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.PositionLabel;
import view.ShipSelectionButton;
import view.ShipSelectionGrid;

public class ShipSelectButtonController implements ActionListener{
	 private ShipSelectionGrid grid;
	 private ShipSelectionButton selectedButton;
	 public ShipSelectButtonController(ShipSelectionGrid grid) {
	        this.grid = grid;
	    }
	    
	    //add mouse listener for all label
	    private void addMouseListenerForLabel(){
	        
	        LabelMouseListener controller = new LabelMouseListener(grid, selectedButton.getShipType());
	        PositionLabel[][] lblPosition = grid.getLblPosition();
	        
	        for (int i = 0; i < lblPosition.length; i++) {
	            for (int j = 0; j < lblPosition.length; j++) {
	                lblPosition[i][j].addMouseListener(controller);
	            }
	        }
	    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//when pressed, add mouse listener for all label on the grid so that the user can click on the label to place the ships
        selectedButton = (ShipSelectionButton) e.getSource();
        this.addMouseListenerForLabel();
        //disable the button so that the user can not add this type of ship anymore
        selectedButton.setEnabled(false);
		
	}
	

}
