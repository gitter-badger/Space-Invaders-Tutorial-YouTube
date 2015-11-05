package com.dregronprogram.game_mode;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.dregronprogram.display.Display;
import com.dregronprogram.enemy_bullets.EnemyShootOne;
import com.dregronprogram.image_objects.ImageObject;

public class EnemyTypeWeak extends ImageObject {

	public double speed = 0.5d;
	private boolean pause = false;
	private long time;
	private boolean destroy;

	// Enemy Main Sprite
	public EnemyShootOne bullet;
	// Enemy Sprite
	private BufferedImage enemy;
	// Enemy DeathSprite
	private BufferedImage[] enemyDeath = new BufferedImage[2];

	public EnemyTypeWeak(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		this.setDestroy(false);

		this.enemy = this.getbImage().getSubimage(0, 0, 25, 25);
		for (int i = 0; i < 2; i++) {
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
		if (!isDestroy())
			return false;

		if ((enemy == enemyDeath[0] || enemy == enemyDeath[1]) && System.currentTimeMillis() - time > timer) {
			if (enemy == enemyDeath[1]) {
				return true;
			} else {
				time = timer;
				enemy = enemyDeath[1];
				time = System.currentTimeMillis();
				return false;
			}
		} else if (enemy != enemyDeath[0] && enemy != enemyDeath[1]) {
			enemy = enemyDeath[0];
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

	public void changeDirection() {
		speed *= -1.05d;
		this.setxPos(getxPos() - speed);
		this.getRect().x = (int) getxPos();

		this.setyPos(getyPos() + 5);
		this.getRect().y = (int) getyPos();
	}

	public void playerCollide(int i, Player player, ArrayList<EnemyTypeWeak> enemyWeak) {
		if (!player.pbbBullets.isEmpty() && !enemyWeak.get(i).isDestroy()) {
			for (int b = 0; b < player.pbbBullets.size(); b++) {
				if (player.pbbBullets.get(b).getRect().intersects(enemyWeak.get(i).getRect())) {
					GameMode.Score += 10;
					player.pbbBullets.remove(b);
					enemyWeak.get(i).setDestroy(true);
					// break;
				}
			}
		}
		if (!player.ebBullets.isEmpty() && !enemyWeak.get(i).isDestroy()) {
			for (int b = 0; b < player.ebBullets.size(); b++) {
				if (player.ebBullets.get(b).bulletCollide(b, player.ebBullets, enemyWeak.get(i).getRect())) {
					GameMode.Score += 10;
					enemyWeak.get(i).setDestroy(true);
					// break;
				}
			}
		}
		if (player.lBeam != null && !enemyWeak.get(i).isDestroy()) {
			if (player.lBeam.getRect().intersects(enemyWeak.get(i).getRect())) {
				GameMode.Score += 10;
				enemyWeak.get(i).setDestroy(true);
				// break;
			}
		}
		if (player.tConstruction != null && !player.tConstruction.turret.isEmpty() && !enemyWeak.get(i).isDestroy()) {
			if(player.tConstruction.EnemyBulletCollide(enemyWeak.get(i).getRect())){
				GameMode.Score += 10;
				enemyWeak.get(i).setDestroy(true);
			}
		}
	}
}
