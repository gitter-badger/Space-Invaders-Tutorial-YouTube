package com.dregronprogram.game_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;

import com.dregronprogram.display.Display;
import com.dregronprogram.handler.EnemyBulletHandler;
import com.dregronprogram.levels.Level1;
import com.dregronprogram.state.SuperStateMachine;

public class GameScreen implements SuperStateMachine{
	
	private Player player;
	private BasicBlocks blocks;
	private Level1 level;
	private EnemyBulletHandler bulletHandler;
	
	public static int SCORE = 0;
	
	public GameScreen(){
		blocks = new BasicBlocks();
		bulletHandler = new EnemyBulletHandler();
		player = new Player(Display.WIDTH/2-50, Display.HEIGHT-75, 50, 50, blocks);
		level = new Level1(player, bulletHandler);
	}
	
	@Override
	public void update(double delta) {
		player.update(delta);
		level.update(delta, blocks);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.drawString("Score: " + SCORE, 5, 15);
		
		blocks.draw(g);
		player.draw(g);
		level.draw(g);
	}

	@Override
	public void init(Canvas canvas) {
		canvas.addKeyListener(player);
	}

}
