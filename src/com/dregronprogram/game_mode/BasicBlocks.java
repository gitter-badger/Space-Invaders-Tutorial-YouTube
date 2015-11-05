package com.dregronprogram.game_mode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BasicBlocks {

	public ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	
	public BasicBlocks(){
		basicBlock(75, 450);
		basicBlock(275, 450);
		basicBlock(475, 450);
		basicBlock(675, 450);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.GREEN);
		for (int w = 0; w < wall.size(); w++) {
			g.fill(wall.get(w));
		}
	}
	
	public void update(double delta){
		
	}
	
	public void basicBlock(int xPos, int yPos) {
		int wallWidth = 3;
		int x = 0;
		int y = 0;

		for (int i = 0; i < 13; i++) {
			if ((14 + (i * 2)) + wallWidth < 22 + wallWidth) {
				row((14 + (i * 2)) + wallWidth, xPos - (i * 3), yPos + (i * 3), wall);
				x = (i * 3) + 3;
			} else {
				row(22 + wallWidth, xPos - x, yPos + (i * 3), wall);
				y = (i * 3);
			}
		}

		// Left side
		for (int i = 0; i < 5; i++) {
			row((8 + wallWidth) - i, xPos - x, 3 +(yPos + y) + (i * 3), wall);
		}

		// Right side
		for (int i = 0; i < 5; i++) {
			row((8 + wallWidth) - i, (xPos - x + (14 * 3)) + (i * 3), 3+(yPos + y) + (i * 3), wall);
		}
	}

	public void row(int rows, int xPos, int yPos, ArrayList<Rectangle> wall) {
		for (int i = 0; i < rows; i++) {
			Rectangle brick = new Rectangle(xPos + (i * 3), yPos, 3, 3);
			wall.add(brick);
		}
	}
	
	
}
