package com.dregronprogram.levels;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.dregronprogram.display.Display;
import com.dregronprogram.game_mode.BasicBlocks;
import com.dregronprogram.game_mode.EnemyTypeAverage;
import com.dregronprogram.game_mode.EnemyTypeWeak;
import com.dregronprogram.game_mode.GameMode;
import com.dregronprogram.game_mode.Player;

public class Level2 {

	private ArrayList<EnemyTypeWeak> enemyWeak = new ArrayList<EnemyTypeWeak>();
	private ArrayList<EnemyTypeAverage> enemyAverage = new ArrayList<EnemyTypeAverage>();
	private boolean levelCompleted = false;

	public Level2() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				EnemyTypeWeak e = new EnemyTypeWeak(150 + (x * 30), 50 + (y * 30), 25, 25, "game_images/AliensWeak.png");
				enemyWeak.add(e);
			}
		}
		for (int y = 0; y < 1; y++) {
			for (int x = 0; x < 10; x++) {
				EnemyTypeAverage e = new EnemyTypeAverage(150 + (x * 30), 175 + (y * 30), 25, 25, "game_images/AliensAverage.png");
				enemyAverage.add(e);
			}
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < enemyWeak.size(); i++) {
			enemyWeak.get(i).draw(g);
		}
		for (int i = 0; i < enemyAverage.size(); i++) {
			enemyAverage.get(i).draw(g);
		}
	}

	public void update(double delta, Player player, BasicBlocks blocks) {
		if (!enemyWeak.isEmpty()) {
			for (int i = 0; i < enemyWeak.size(); i++) {
				if (!(enemyWeak.get(i).getxPos() > 0 && enemyWeak.get(i).getxPos() < Display.WIDTH - enemyWeak.get(i).getWidth())) {
					changeDirectionAllEnemys();
					break;
				}
			}

			for (int i = 0; i < enemyWeak.size(); i++) {
				weakEnemy(i, delta, player, blocks);
			}
		} 

		if (!enemyAverage.isEmpty()) {
			for (int i = 0; i < enemyAverage.size(); i++) {
				if (!(enemyAverage.get(i).getxPos() > 0 && enemyAverage.get(i).getxPos() < Display.WIDTH - enemyAverage.get(i).getWidth())) {
					changeDirectionAllEnemys();
					break;
				}
			}

			for (int i = 0; i < enemyAverage.size(); i++) {
				averageEnemy(i, delta, player, blocks);
			}
		}

		if (enemyAverage.isEmpty() && enemyWeak.isEmpty()) {
			this.setLevelCompleted(true);
		}
	}

	public void changeDirectionAllEnemys() {
		for (int i = 0; i < enemyWeak.size(); i++) {
			enemyWeak.get(i).changeDirection();
		}
		for (int i = 0; i < enemyAverage.size(); i++) {
			enemyAverage.get(i).changeDirection();
		}
	}

	public boolean isLevelCompleted() {
		return levelCompleted;
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.levelCompleted = levelCompleted;
	}
	
	public void weakEnemy(int i, double delta, Player player, BasicBlocks blocks) {
		enemyWeak.get(i).update(delta);

		if (enemyWeak.get(i).bullet != null) {
			if (enemyWeak.get(i).bullet.getRect().intersects(player.getRect()) && !enemyWeak.get(i).bullet.isDestroy()) {
				enemyWeak.get(i).bullet.setDestroy(true);
				GameMode.Lives--;
			}
			if (enemyWeak.get(i).bullet.collideWall(i, blocks, enemyWeak.get(i).bullet.getRect()) && !enemyWeak.get(i).bullet.isDestroy()) {
				enemyWeak.get(i).bullet.setDestroy(true);
			}
			if(player.tConstruction.TurretHit(enemyWeak.get(i).bullet.getRect()) && !enemyWeak.get(i).bullet.isDestroy()){
				enemyWeak.get(i).bullet.setDestroy(true);
			}
			if(enemyWeak.get(i).bullet.deathScene(125)){
				enemyWeak.get(i).bullet = null;
			}
		}

		enemyWeak.get(i).playerCollide(i, player, enemyWeak);

		if (enemyWeak.get(i).deathScene(300)) {
			enemyWeak.remove(i);
		}
	}
	
	public void averageEnemy(int i, double delta, Player player, BasicBlocks blocks) {
		enemyAverage.get(i).update(delta);

		if (enemyAverage.get(i).bullet != null) {
			if (enemyAverage.get(i).bullet.getRect().intersects(player.getRect()) && !enemyAverage.get(i).bullet.isDestroy()) {
				enemyAverage.get(i).bullet.setDestroy(true);
				GameMode.Lives--;
			}
			if (enemyAverage.get(i).bullet.collideWall(i, blocks, enemyAverage.get(i).bullet.getRect()) && !enemyAverage.get(i).bullet.isDestroy()) {
				enemyAverage.get(i).bullet.setDestroy(true);
			}
			if(player.tConstruction.TurretHit(enemyAverage.get(i).bullet.getRect()) && !enemyAverage.get(i).bullet.isDestroy()){
				enemyAverage.get(i).bullet.setDestroy(true);
			}
			
			if(enemyAverage.get(i).bullet.deathScene(125)){
				enemyAverage.get(i).bullet = null;
			}
		}

		enemyAverage.get(i).playerCollide(i, player, enemyAverage);

		if (enemyAverage.get(i).deathScene(300)) {
			enemyAverage.remove(i);
		}
	}

}
