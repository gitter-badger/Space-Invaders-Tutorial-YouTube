package com.dregronprogram.game_mode;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.dregronprogram.display.Display;

public class HealthSprites {

	private double xPos;
	private double yPos;
	private int width;
	private int height;
	private BufferedImage bImage;

	public HealthSprites(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.BOLD, 14));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		String title = "Health: ";
		int titleWidth = fm.stringWidth(title);

		g.drawString(title + "", (Display.WIDTH - titleWidth)-75, (int) (getyPos()));
		
		//g.drawString("Health: ", (int) getxPos()+getWidth()-65,(int) getyPos()+15);
		for(int i = 0; i < GameMode.Lives; i++){
			g.drawImage(getbImage(), (Display.WIDTH - titleWidth-25)+(25*i),(int) getyPos()-15, getWidth(), getHeight(), null);
		}
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getbImage() {
		return bImage;
	}

	public void setbImage(String imgPath) {
		try {
			if (imgPath != null) {
				URL url = this.getClass().getResource(imgPath);
				this.bImage = ImageIO.read(url);
			} else
				this.bImage = null;
		} catch (IOException e) {
			System.out.println("URL Error!");
			e.printStackTrace();
		}
	}

}
