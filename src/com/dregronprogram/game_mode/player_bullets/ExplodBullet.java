package com.dregronprogram.game_mode.player_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.dregronprogram.display.Display;

public class ExplodBullet {

	private Rectangle[] bullet = new Rectangle[16];
	private final float speed = 1.0f;
	public long time;

	public ExplodBullet(double xPos, double yPos, double width, double height) {
		for (int i = 0; i < bullet.length; i++) {
			if (i == 0) {
				this.bullet[i] = new Rectangle((int) xPos, (int) yPos, (int) width, (int) height);
			} else {
				this.bullet[i] = new Rectangle(0, 0, (int) (3), (int) (3));
			}
		}
	}

	public void draw(Graphics2D g) {
		Color c = new Color(0, 85, 0);
		if (getBullet()[0] != null) {
			
			g.setColor(c);
			g.fill(this.getBullet()[0]);
		} else {
			for (int i = 1; i < bullet.length; i++) {
				if (getBullet()[i] != null) {
					g.setColor(c);
					g.fill(this.getBullet()[i]);
				}
			}
		}
	}

	public void update(double delta) {
		if (getBullet()[0] != null) {
			this.getBullet()[0].y -= (delta * speed/2);
			Explode(1200);
		} else {
			float angle = 0.2f;
			for (int i = 1; i < bullet.length; i++) {
				if (getBullet()[i] != null) {
					this.getBullet()[i].x -= (int) 5 * Math.cos(angle);
					this.getBullet()[i].y -= (int) 5 * Math.sin(angle);
				}
				if(angle == 8){
					angle = 0.2f;
				}else
					angle += 0.2f;
			}
		}
	}

	public boolean bulletCollide(int eb, ArrayList<ExplodBullet> eBullet, Rectangle object) {
		for (int i = 1; i < getBullet().length; i++) {
			if (eBullet.get(eb).bullet[i] != null) {
				if (eBullet.get(eb).getBullet()[i].intersects(object)) {
					//eBullet.get(eb).bullet[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isOutofBounds(int b, ArrayList<ExplodBullet> ebBullets) {

		for (int i = 1; i < getBullet().length; i++) {
			if (getBullet()[i] != null) {
				if (getBullet()[i].y < -200 || getBullet()[i].y > Display.HEIGHT + 200 || getBullet()[i].x < -200 || getBullet()[i].x > Display.WIDTH + 200) {
					ebBullets.remove(b);
					return true;
				}
			}
		}
		return false;
	}

	public Rectangle[] getBullet() {
		return bullet;
	}

	public void setBullet(Rectangle[] bullet) {
		this.bullet = bullet;
	}

	private boolean Explode(int timer) {
		if (System.currentTimeMillis() - time > timer) {
			for (int i = 1; i < bullet.length; i++) {
				this.bullet[i].x = this.bullet[0].x;
				this.bullet[i].y = this.bullet[0].y;
			}
			this.bullet[0] = null;
			return true;
		}

		return false;
	}

}
