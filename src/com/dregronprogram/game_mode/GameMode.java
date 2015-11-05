package com.dregronprogram.game_mode;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.dregronprogram.display.Display;
import com.dregronprogram.levels.Level1;
import com.dregronprogram.levels.Level2;
import com.dregronprogram.levels.Level3;

public class GameMode {

	private long time = 0;
	private int countDown = 5;
	public static int Score = 0000;
	public static int Lives = 3;

	private Player player;
	private BasicBlocks blocks;
	private HealthSprites hSprite;
	
	private Level1 level1;
	private Level2 level2;
	private Level3 level3;
	private boolean nextLevel = false;

	private byte currentLevel = 1;

	public GameMode() {
		player = new Player(Display.WIDTH / 2, Display.HEIGHT - 75, 50, 50, "game_images/Player.png");
		hSprite = new HealthSprites(Display.WIDTH-105, Display.HEIGHT-10, 15, 15, "game_images/health.png");
		level1 = new Level1();
		//level2 = new Level2();
		blocks = new BasicBlocks();
	}

	public void init(Canvas canvas) {
		canvas.addKeyListener(player);
		canvas.setFocusTraversalKeysEnabled(false);
	}

	public void draw(Graphics2D g) {
		blocks.draw(g);
		player.draw(g);
		hSprite.draw(g);
		currentLevelDraw(g);
		
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		g.drawString("Score: " + String.valueOf(Score), 5, Display.HEIGHT-13);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		g.setColor(Color.white);
		String movement = "D, A :Movement --- Weapon Switch: W, S";
		g.drawString(movement, centerText(movement, g), Display.HEIGHT);
		//g.setColor(Color.blue);
		
		//g.drawString("The Dregron Program ©", Display.WIDTH-160, Display.HEIGHT);
	}

	public void update(double delta) {
		if (nextLevel)
			return;

		currentLevelUpdate(delta);

		if ((!player.pbbBullets.isEmpty() || !player.ebBullets.isEmpty() || player.lBeam != null) && !blocks.wall.isEmpty()) {
			for (int w = 0; w < blocks.wall.size(); w++) {
				for (int b = 0; b < player.pbbBullets.size(); b++) {
					if (player.pbbBullets.get(b).getRect().intersects(blocks.wall.get(w))) {
						player.pbbBullets.remove(b);
						blocks.wall.remove(w);
						break;
					}
				}
				for (int b = 0; b < player.ebBullets.size(); b++) {
					if (player.ebBullets.get(b).getBullet()[0] != null) {
						if (player.ebBullets.get(b).getBullet()[0].intersects(blocks.wall.get(w))) {
							player.ebBullets.remove(b);
							blocks.wall.remove(w);
							break;
						}
					}
				}
				
				if (player.lBeam != null) {
					if (player.lBeam.getRect().intersects(blocks.wall.get(w))) {
						blocks.wall.remove(w);
						break;
					}
				}
			}
		}

		player.update(delta);
	}

	public void setCurrentLevelObject(byte level, Object currentLevel) {
		currentLevel = null;
		this.currentLevel = level;
	}

	// returns current level.
	public Object getCurrentLevelObject() {
		switch (currentLevel) {
		case 1:
			return level1;
		case 2:
			return level2;
		case 3:
			return level3;
		}

		return null;
	}

	public void currentLevelUpdate(double delta) {
		try {
			switch (currentLevel) {
			case 1:
				level1.update(delta, player, blocks);
				break;
			case 2:
				level2.update(delta, player, blocks);
				break;
			case 3:
				level3.update(delta, player, blocks);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Current Level Update Error");
		}
	}

	public void currentLevelDraw(Graphics2D g) {
		try {
			switch (currentLevel) {
			case 1:
				level1.draw(g);
				if (level1.isLevelCompleted()) {
					changeLevel(g, level1);
					// Doesn't change until the count = 0;
					if (countDown == 0) {
						level2 = new Level2();
						countDown = 5;
						nextLevel = false;
					}
				} else {
					time = System.currentTimeMillis();
				}
				break;
			case 2:
				level2.draw(g);
				if (level2.isLevelCompleted()) {
					changeLevel(g, level2);
					// Doesn't change until the count = 0;
					if (countDown == 0) {
						level3 = new Level3();
						countDown = 5;
						nextLevel = false;
					}
				} else {
					time = System.currentTimeMillis();
				}
				break;
			case 3:
				level3.draw(g);
				if (level3.isLevelCompleted()) {
					
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Current Level Draw Error");
		}
	}

	public void changeLevel(Graphics2D g, Object lastLevelObject) {
		// Count down code.
		if (cooldown(1200)) {
			time = System.currentTimeMillis();
			countDown -= 1;
		}

		// Display depending on the current countDown time.
		if (countDown > 3) {
			nextLevel = true;
			if (!player.pbbBullets.isEmpty()) {
				for (int i = 0; i < player.pbbBullets.size(); i++) {
					player.pbbBullets.remove(i);
				}
			}
			if (!player.ebBullets.isEmpty()) {
				for (int i = 0; i < player.ebBullets.size(); i++) {
					player.ebBullets.remove(i);
				}
			}
			player.tConstruction.removeBullets();
			if(player.lBeam != null){
				player.lBeam = null;
			}
			g.setFont(new Font("Serif", Font.PLAIN, 48));
			g.drawString("Level Completed!\n ", (Display.WIDTH / 2) - 175, Display.HEIGHT / 2);
		} else if (countDown > 0) {
			g.setFont(new Font("Serif", Font.PLAIN, 48));
			g.drawString(countDown + "", (Display.WIDTH / 2) - 25, Display.HEIGHT / 2);
		} else {
			currentLevel += 1;
			setCurrentLevelObject((byte) currentLevel, this.getCurrentLevelObject());
			lastLevelObject = null;
		}
	}

	public boolean cooldown(int timer) {
		if (System.currentTimeMillis() - time > timer) {
			time = timer;
			return true;
		}
		return false;
	}

	private int centerText(String s, Graphics2D g) {
		int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int start = Display.WIDTH / 2 - stringLen / 2;

		return start;
	}
	
}
