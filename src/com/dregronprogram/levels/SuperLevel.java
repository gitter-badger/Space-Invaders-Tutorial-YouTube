package com.dregronprogram.levels;

import java.awt.Graphics2D;

import com.dregronprogram.game_screen.BasicBlocks;

public interface SuperLevel {

	void draw(Graphics2D g);
	void update(double delta, BasicBlocks blocks);
	void hasDirectionChange(double delta);
	void changeDurectionAllEnemys(double delta);
	
	boolean isGameOver();
	boolean isComplete();
	
	void destory();
	void reset();
}
