package com.dregronprogram.menu;

import java.awt.Graphics2D;

import com.dregronprogram.image_objects.ImageObject;

public class SplashScreen extends ImageObject{

	private long time = 0;
	
	public SplashScreen(double xPos, double yPos, int width, int height, String imgPath){
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		
		this.setbImage(imgPath);
		time = System.currentTimeMillis();
	}

	public void draw(Graphics2D g) {
		g.drawImage(getbImage(),(int) getxPos(),(int) getyPos(), getWidth(), getHeight(), null);
	}

	public void update(double delta) {
		intro(1800);
	}
	

	private boolean intro(int timer){
		if(System.currentTimeMillis() - time > timer){
			setbImage(null);
			return true;
		}
		
		return false;
	}
}
