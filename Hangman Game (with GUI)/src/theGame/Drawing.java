package theGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawing extends JPanel {

	int errors;
	/**
	 * Create the panel.
	 */
	public Drawing() {
		this.setSize(428, 309);
		this.setBounds(0, 220, 428, 309);
	}
	
	public void setNum(int num) {
		errors = num;
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
					
		switch (errors) {
			case 0 :
				// The left leg
				g.drawLine(243, 210, 266, 250);
			case 1 :
				// The right leg
				g.drawLine(243, 210, 220, 250);
			case 2 :
				// The left hand
				g.drawLine(243, 155, 266, 190);
			case 3 :
				// The right hand
				g.drawLine(243, 155, 220, 190);
			case 4 :
				// The body
				g.drawLine(243, 140, 243, 210);
			case 5 : 
				// The head
				g.drawOval(223, 100, 40, 40);
			case 6 :
				// The hanger (small vertical)
				g.drawLine(243, 50, 243, 100);
			case 7 :
				// The hanger (horizontal)
				g.drawLine(143, 50, 243, 50);
			case 8 :
				// The hanger (long vertical)
				g.drawLine(143, 50, 143, 250);
		}
	}

}
