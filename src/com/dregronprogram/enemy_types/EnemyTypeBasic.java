package com.dregronprogram.enemy_types;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.dregronprogram.display.Display;
import com.dregronprogram.enemy_bullets.EnemyBasicBullet;
import com.dregronprogram.game_screen.BasicBlocks;
import com.dregronprogram.game_screen.GameScreen;
import com.dregronprogram.game_screen.Player;
import com.dregronprogram.handler.EnemyBulletHandler;
import com.dregronprogram.sound.Sound;
import com.dregronprogram.sprite.SpriteAnimation;
import com.dregronprogram.timer.Timer;

public class EnemyTypeBasic extends EnemyType{

	private double speed = 1.0d; 
	
	private Rectangle rect;
	private SpriteAnimation enemySprite;
	
	private int shootTime;
	private Timer shootTimer;
	
	private Sound explosionSound;
	
	public EnemyTypeBasic(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler){
		super(bulletHandler);
		
		enemySprite = new SpriteAnimation(xPos, yPos, rows, columns, 300, "/com/dregronprogram/images/Invaders.png");
		enemySprite.setWidth(25);
		enemySprite.setHeight(25);
		enemySprite.setLimit(2);
		
		this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
		enemySprite.setLoop(true);
		
		shootTimer = new Timer();
		shootTime = new Random().nextInt(12000);
		
		explosionSound = new Sound("/com/dregronprogram/sounds/explosion.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		enemySprite.draw(g);
	}

	@Override
	public void update(double delta, Player player, BasicBlocks blocks) {
		enemySprite.update(delta);
		
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		if (shootTimer.timerEvent(shootTime)) {
			getBulletHandler().addBullet(new EnemyBasicBullet(getRect().x, getRect().y));
			shootTime = new Random().nextInt(12000);
		}
	}

	@Override
	public void changeDirection(double delta) {
		speed *= -1.15d;
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		enemySprite.setyPos(enemySprite.getyPos() + (delta * 15));
		this.getRect().y = (int) enemySprite.getyPos();
	}

	@Override
	public boolean deathScene() {
		if(!enemySprite.isPlay())
			return false;
		
		if(enemySprite.isSpriteAnimDestroyed()) {
			if (!explosionSound.isPlaying()) {
				explosionSound.play();
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys) {
		if(enemySprite.isPlay()) {
			if(enemys.get(i).deathScene()) {
				enemys.remove(i);
			}
			return false;
		}
		
		for(int w = 0; w < player.playerWeapons.weapons.size(); w++) {
			if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((EnemyTypeBasic) enemys.get(i)).getRect())) {
				enemySprite.resetLimit();
				enemySprite.setAnimationSpeed(120);
				enemySprite.setPlay(true, true);
				GameScreen.SCORE += 8;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isOutOfBounds() {
		if(rect.x > 0 && rect.x < Display.WIDTH - rect.width)
			return false;
		return true;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
