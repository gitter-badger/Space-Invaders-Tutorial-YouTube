package com.dregronprogram.game_mode.player_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dregronprogram.display.Display;

public class LaserBeam {

	private double xPos, yPos;
	private int width;
	private final double speed = 10d;
	private Rectangle rect;
	private boolean shoot = true;

	public long time;
	

	public LaserBeam(double xPos, double yPos, int width) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);

		rect = new Rectangle((int) getxPos() - 1, (int) getyPos() - 10, getWidth(), 35);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(rect.x - 2, rect.y, rect.width + 4, rect.height);
		g.setColor(Color.cyan);
		g.fill(rect);
	}

	public void update(double delta) {
		if (!isShoot())
			return;

		if (rect.height < 500) {
			rect.y -= delta * speed - 0.7;
			rect.height += delta * speed;
		}
	}

	public boolean finish(int timer) {
		if (System.currentTimeMillis() - time > timer) {
			return true;
		}
		return false;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public boolean isShoot() {
		return shoot;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

}
