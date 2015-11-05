package com.dregronprogram.image_objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class ImageObject {

	private double xPos;
	private double yPos;
	private int width;
	private int height;
	private BufferedImage bImage;
	private Rectangle rect;
	
	public abstract void draw(Graphics2D g);
	
	public abstract void update(double delta);

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
			if(imgPath != null){
				URL url = this.getClass().getResource(imgPath);
				this.bImage = ImageIO.read(url);
			}else
				this.bImage = null;
		} catch (IOException e) {
			System.out.println("URL Error!");
			e.printStackTrace();
		}
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
