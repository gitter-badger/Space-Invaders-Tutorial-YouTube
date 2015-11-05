package com.dregronprogram.game_mode;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.dregronprogram.display.Display;
import com.dregronprogram.enemy_bullets.EnemyShootOne;
import com.dregronprogram.image_objects.ImageObject;

public class EnemyTypeAverage extends ImageObject {

	public double speed = 0.5d;
	private boolean pause = false;
	private long time;
	private boolean destroy;
	public byte health = 1;

	// Enemy Main Sprite
	public EnemyShootOne bullet;
	// Enemy Sprite
	private BufferedImage enemy;
	// Enemy DeathSprite
	private BufferedImage[] enemyDeath = new BufferedImage[3];

	public EnemyTypeAverage(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		this.setDestroy(false);

		this.enemy = this.getbImage().getSubimage(0, 0, 25, 25);
		for (int i = 0; i < 3; i++) {
			this.enemyDeath[i] = this.getbImage().getSubimage(25 + (i * 25), 0, 25, 25);
		}

		this.setRect(new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight()));
	}

	public void draw(Graphics2D g) {
		g.drawImage(enemy, (int) getxPos(), (int) getyPos(), getWidth(), getHeight(), null);
		if (bullet != null) {
			bullet.draw(g);
		}
	}

	public void update(double delta) {
		if (bullet != null) {
			bullet.update(delta);
			if (bullet.getyPos() > Display.HEIGHT) {
				bullet = null;
			}
		}

		if (pause == true)
			return;

		double shoot = Math.random();

		if (shoot < 0.0005 && bullet == null) {
			bullet = new EnemyShootOne(this.getxPos() + 21, this.getyPos() + 20, 5, 5, "/com/dregronprogram/game_mode/game_images/enemyBulletOne.png");
		}

		this.setxPos(this.getxPos() - speed);
		this.getRect().x = (int) this.getxPos();
	}

	public boolean deathScene(int timer) {
		if(!isDestroy())
			return false;
			
		if ((enemy == enemyDeath[1] || enemy == enemyDeath[2]) && System.currentTimeMillis() - time > timer) {
			if (enemy == enemyDeath[2]) {
				return true;
			} else {
				time = timer;
				enemy = enemyDeath[2];
				time = System.currentTimeMillis();
				return false;
			}
		} else if (enemy != enemyDeath[1] && enemy != enemyDeath[2]) {
			enemy = enemyDeath[1];
			pause = true;
			time = System.currentTimeMillis();
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
	
	public void changeDirection(){
		speed *= -1.05d;
		this.setxPos(getxPos() - speed);
		this.getRect().x = (int) getxPos();

		this.setyPos(getyPos() + 5);
		this.getRect().y = (int) getyPos();
	}
	
	public void changeSprite(int i) {
		this.enemy = enemyDeath[i];
	}

	public void enemyHit() {
		health--;
		if (health == 0) {
			changeSprite(0);
		}
	}
	
	public void playerCollide(int i, Player player, ArrayList<EnemyTypeAverage> enemyAverage) {
		if (!player.pbbBullets.isEmpty() && !enemyAverage.get(i).isDestroy()) {
			for (int b = 0; b < player.pbbBullets.size(); b++) {
				if (player.pbbBullets.get(b).getRect().intersects(enemyAverage.get(i).getRect())) {
					GameMode.Score += 10;
					player.pbbBullets.remove(b);
					if (enemyAverage.get(i).health != 0){
						enemyAverage.get(i).enemyHit();
					}else 
						enemyAverage.get(i).setDestroy(true);
					break;
				}
			}
		}
		if(!player.ebBullets.isEmpty() && !enemyAverage.get(i).isDestroy()){
			for (int b = 0; b < player.ebBullets.size(); b++) {
				if(player.ebBullets.get(b).bulletCollide(b, player.ebBullets, enemyAverage.get(i).getRect())){
					GameMode.Score += 10;
					if (enemyAverage.get(i).health != 0){
						enemyAverage.get(i).enemyHit();
					}else 
						enemyAverage.get(i).setDestroy(true);
				}
			}
		}
		
		if (player.lBeam !=null && !enemyAverage.get(i).isDestroy()) {
			if (player.lBeam.getRect().intersects(enemyAverage.get(i).getRect())) {
				GameMode.Score += 10;
				enemyAverage.get(i).setDestroy(true);
			}
		}
		
		if (player.tConstruction != null && !player.tConstruction.turret.isEmpty() && !enemyAverage.get(i).isDestroy()) {
			if(player.tConstruction.EnemyBulletCollide(enemyAverage.get(i).getRect())){
				GameMode.Score += 10;
				if (enemyAverage.get(i).health != 0)
					enemyAverage.get(i).enemyHit();
				else 
					enemyAverage.get(i).setDestroy(true);
			}
		}
	}

}
