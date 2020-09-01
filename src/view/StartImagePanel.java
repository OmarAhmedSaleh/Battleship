package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import util.ErrorLogger;
import util.Lib;

public class StartImagePanel extends JPanel{
	
	private BufferedImage backGroundImage;
	private String imagePath = Lib.IMAGES_FOLDER_PATH + "start_back.jpg";
	private static StartImagePanel instance; 
	
	private StartImagePanel() {
		try {
			backGroundImage = ImageIO.read(new File(imagePath));
			this.setPreferredSize(new Dimension(backGroundImage.getWidth(), backGroundImage.getHeight()));
		}catch(IOException e) {
			System.out.println(e.getMessage());
			ErrorLogger.log(e.getStackTrace());
		}
	}
	
	public synchronized static StartImagePanel getInstance() {
		if(instance == null) {
			instance = new StartImagePanel();
		}
		return instance;
	}
	@Override
    public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(backGroundImage, 0, 0, null);
    }
}
