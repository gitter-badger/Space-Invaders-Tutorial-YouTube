package com.dregronprogram.enemy_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.dregronprogram.game_mode.BasicBlocks;
import com.dregronprogram.image_objects.ImageObject;

public class EnemyShootOne extends ImageObject {

	private final double speed = 1.0d;
	private BufferedImage mainSprite;
	private BufferedImage[] spriteDeath = new BufferedImage[3];
	private long time;
	
	private boolean pause = false;
	private boolean destroy;

	public EnemyShootOne(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		mainSprite = getbImage().getSubimage(0, 0, 5, 5);
		
		for (int i = 0; i < 3; i++) {
			this.spriteDeath[i] = this.getbImage().getSubimage(50 + (i * 50), 0, 50, 50);
		}

		this.setRect(new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight()));
	}

	public void draw(Graphics2D g) {
		g.drawImage(mainSprite, (int) getxPos(), (int) getyPos(), mainSprite.getWidth(), mainSprite.getHeight(), null);
		g.setColor(Color.red);
	}

	public void update(double delta) {
		if (pause == true)
			return;
		
		this.setyPos(this.getyPos() + (delta * speed));
		this.getRect().y = (int) this.getyPos();
	}
	
	public boolean deathScene(int timer) {
		if (!isDestroy())
			return false;

		if ((mainSprite == spriteDeath[0] || mainSprite == spriteDeath[1] || mainSprite == spriteDeath[2]) && System.currentTimeMillis() - time > timer) {
			if(mainSprite == spriteDeath[2]){
				return true;
			}else if (mainSprite == spriteDeath[1]) {
				time = timer;
				mainSprite = spriteDeath[2];
				time = System.currentTimeMillis();
				return false;
			} else if (mainSprite == spriteDeath[0]){
				time = timer;
				mainSprite = spriteDeath[1];
				time = System.currentTimeMillis();
				return false;
			}
		} else if (mainSprite != spriteDeath[0] && mainSprite != spriteDeath[1] && mainSprite != spriteDeath[2]) {
			mainSprite = spriteDeath[0];
			pause = true;
			time = System.currentTimeMillis();
			setxPos(getxPos()-25);
			setyPos(getyPos()-25);
			return false;
		}
		return false;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
	
	public boolean collideWall(int b, BasicBlocks blocks, Rectangle ewBullet){
		for(int w = 0; w < blocks.wall.size(); w++){
			if(ewBullet.intersects(blocks.wall.get(w))){
				blocks.wall.remove(w);
				return true;
			}
		}
		return false;
	}
}
