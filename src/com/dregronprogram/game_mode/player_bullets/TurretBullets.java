package com.dregronprogram.game_mode.player_bullets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.dregronprogram.image_objects.ImageObject;

public class TurretBullets extends ImageObject{

	public AffineTransform trans;
	private final float speed = -2.0f;
	
	public TurretBullets(double xPos, double yPos, int width, int height, String imgPath){
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		
		this.setRect(new Rectangle((int) getxPos(), (int) getyPos(), getWidth(), getHeight()));
		
		trans = new AffineTransform();
		trans.setToTranslation(getxPos(), getyPos());
	}

	
	public void draw(Graphics2D g) {
		g.drawImage(getbImage(), getTrans(), null);
	}

	public void update(double delta) {
		trans.translate(0, speed);
		setRect(new Rectangle((int) trans.getTranslateX(), (int) trans.getTranslateY(), getWidth(), getHeight()));
	}

	public AffineTransform getTrans() {
		return trans;
	}

	public void setTrans(AffineTransform trans) {
		this.trans = trans;
	}

	public void rotateRight(int rotation){
		trans.rotate(Math.toRadians(rotation), getWidth() / 2, getHeight() / 2);
	}
}
