package com.dregronprogram.game_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.dregronprogram.display.Display;
import com.dregronprogram.handler.EnemyBulletHandler;
import com.dregronprogram.levels.Level1;
import com.dregronprogram.state.StateMachine;
import com.dregronprogram.state.SuperStateMachine;
import com.dregronprogram.timer.TickTimer;

public class GameScreen extends SuperStateMachine {
	
	private Player player;
	private BasicBlocks blocks;
	private Level1 level;
	private EnemyBulletHandler bulletHandler;
	
	public static int SCORE = 0;
	
	private Font gameScreen = new Font("Arial", Font.PLAIN, 48);
	private TickTimer gameOverTimer = new TickTimer(180);
	private TickTimer completeTimer = new TickTimer(180);
	
	public GameScreen(StateMachine stateMachine){
		super(stateMachine);
		blocks = new BasicBlocks();
		bulletHandler = new EnemyBulletHandler();
		player = new Player(Display.WIDTH/2-50, Display.HEIGHT-75, 50, 50, blocks);
		level = new Level1(player, bulletHandler);
	}
	
	@Override
	public void update(double delta) {
		player.update(delta);
		level.update(delta, blocks);
		
		if (level.isGameOver()) {
			gameOverTimer.tick(delta);
			if (gameOverTimer.isEventReady()) {
				level.reset();
				blocks.reset();
				getStateMachine().setState((byte) 0);
				SCORE = 0;
			}
		}
		
		if (level.isComplete()) {
			completeTimer.tick(delta);
			if (completeTimer.isEventReady()) {
				level.reset();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.drawString("Score: " + SCORE, 5, 15);
		
		g.setColor(Color.red);
		g.drawString("Health: " + player.getHealth(), 5, 35);
		
		blocks.draw(g);
		player.draw(g);
		level.draw(g);
		
		if (level.isGameOver()) {
			g.setColor(Color.red);
			g.setFont(gameScreen);
			String gameOver = "GAME OVER!";
			int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2), Display.HEIGHT/2);
		}
		
		if (level.isComplete()) {
			g.setColor(Color.green);
			g.setFont(gameScreen);
			String complete = "LEVEL COMPLETE!";
			int completeWidth = g.getFontMetrics().stringWidth(complete);
			g.drawString(complete, (Display.WIDTH/2)-(completeWidth/2), Display.HEIGHT/2);
		}
	}

	@Override
	public void init(Canvas canvas) {
		canvas.addKeyListener(player);
	}

}
