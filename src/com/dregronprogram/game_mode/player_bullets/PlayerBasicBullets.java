package com.dregronprogram.game_mode.player_bullets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.dregronprogram.image_objects.ImageObject;

public class PlayerBasicBullets extends ImageObject {

	private final double speed = 1.0d;

	public PlayerBasicBullets(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);

		this.setRect(new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight()));
	}

	public void draw(Graphics2D g) {
		g.drawImage(getbImage(), (int) getxPos(), (int) getyPos(), getWidth(), getHeight(), null);
	}

	public void update(double delta) {
		this.setyPos(this.getyPos() - (delta * speed));
		this.getRect().y = (int) this.getyPos();
	}

	public boolean isOutofBounds(int b, ArrayList<PlayerBasicBullets> pbbBullet) {
		if (pbbBullet.get(b).getyPos() < 0) {
			pbbBullet.remove(b);
			return true;
		}

		return false;
	}

}
