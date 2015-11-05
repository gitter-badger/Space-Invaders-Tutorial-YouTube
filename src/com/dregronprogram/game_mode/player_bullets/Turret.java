package com.dregronprogram.game_mode.player_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.dregronprogram.image_objects.ImageObject;

public class Turret extends ImageObject{

	private AffineTransform trans;
	private int rotation = 0;
	private long time;
	
	public TurretBullets tBullet;
	
	public Turret(double xPos, double yPos, int width, int height, String imgPath){
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		
		this.setRect(new Rectangle((int) getxPos(),(int) getyPos(), getWidth(), getHeight()));
		
		trans = new AffineTransform();
		trans.setToTranslation(getxPos(), getyPos());
		time = System.currentTimeMillis();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(getbImage(), getTrans(), null);
		if (tBullet != null) {
			tBullet.draw(g);
		}
	}

	public void update(double delta) {
		if (tBullet != null) {
			tBullet.update(delta);
			if (tBullet.getRect().y < 0) {
				tBullet = null;
			}
		}
		
		double shoot = Math.random();

		if (shoot < 0.005 && tBullet == null) {
			tBullet = new TurretBullets(this.getxPos() + 10, this.getyPos() + 20, 5, 5, "/com/dregronprogram/game_mode/game_images/turretBullet.png");
		}
	}
	
	public void Spaces(Graphics2D g, int ammo){
		if(ammo != 0){
			for (int i = 0; i < 14; i++) {
				g.setColor(new Color(.0f, .4f, .0f, .5f));
				g.fillRect(60 + (i * 50), 400, 25, 25);
			}
		}
	}
	
	public void rotateRight(int RotationSpeed){
		rotation += RotationSpeed;
		trans.rotate(Math.toRadians(rotation), getWidth()/2, getHeight()/2);
		setRect(new Rectangle((int) trans.getTranslateX(), (int) trans.getTranslateY(), getWidth(), getHeight()));
	}
	
	public void rotateLeft(int RotationSpeed){
		rotation -= RotationSpeed;
		trans.rotate(Math.toRadians(rotation), getWidth()/2, getHeight()/2);
		setRect(new Rectangle((int) trans.getTranslateX(), (int) trans.getTranslateY(), getWidth(), getHeight()));
	}

	public AffineTransform getTrans() {
		return trans;
	}

	public void setTrans(AffineTransform trans) {
		this.trans = trans;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

}
