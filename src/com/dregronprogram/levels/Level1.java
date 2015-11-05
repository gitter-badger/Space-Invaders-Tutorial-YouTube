package com.dregronprogram.levels;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.dregronprogram.display.Display;
import com.dregronprogram.game_mode.BasicBlocks;
import com.dregronprogram.game_mode.EnemyTypeWeak;
import com.dregronprogram.game_mode.GameMode;
import com.dregronprogram.game_mode.Player;

public class Level1 {

	private ArrayList<EnemyTypeWeak> enemyWeak = new ArrayList<EnemyTypeWeak>();
	private boolean levelCompleted = false;
	private boolean gameOver = false;

	public Level1() {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 10; x++) {
				EnemyTypeWeak e = new EnemyTypeWeak(150 + (x * 30), 50 + (y * 30), 25, 25, "game_images/AliensWeak.png");
				enemyWeak.add(e);
			}
		}
	}

	public void draw(Graphics2D g) {
		if (isLevelCompleted())
			return;

		for (int i = 0; i < enemyWeak.size(); i++) {
			enemyWeak.get(i).draw(g);
		}
	}

	public void update(double delta, Player player, BasicBlocks blocks) {
		if (isLevelCompleted() || isGameOver())
			return;

		if (!enemyWeak.isEmpty()) {
			for (int i = 0; i < enemyWeak.size(); i++) {
				if (!(enemyWeak.get(i).getxPos() > 0 && enemyWeak.get(i).getxPos() < Display.WIDTH - enemyWeak.get(i).getWidth())) {
					changeDirectionAllEnemys();
					break;
				}else if(enemyWeak.get(i).getyPos() > 415)
					gameOver = true;
			}

			for (int i = 0; i < enemyWeak.size(); i++) {
				weakEnemy(i, delta, player, blocks);
			}
		} else {
			setLevelCompleted(true);
			return;
		}
	}

	public void changeDirectionAllEnemys() {
		for (int i = 0; i < enemyWeak.size(); i++) {
			enemyWeak.get(i).changeDirection();
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
			if (enemyWeak.get(i).bullet.collideWall(i, blocks, enemyWeak.get(i).bullet.getRect())&& !enemyWeak.get(i).bullet.isDestroy()) {
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

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
