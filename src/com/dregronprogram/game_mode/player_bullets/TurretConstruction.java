package com.dregronprogram.game_mode.player_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TurretConstruction {

	public ArrayList<Turret> turret = new ArrayList<Turret>();
	private byte location = 0;

	public TurretConstruction() {

	}

	public void draw(Graphics2D g, int ammo) {
		if (ammo != 0) {
			for (int i = 0; i < 14; i++) {
				if (location == i)
					g.setColor(new Color(1.0f, 1.0f, .0f, .5f));
				else
					g.setColor(new Color(.0f, .4f, .0f, .5f));
				g.fillRect(60 + (i * 50), 400, 25, 25);
			}
		}

		for (int b = 0; b < turret.size(); b++) {
			turret.get(b).draw(g);
		}
	}

	public void update(double delta) {
		for (int b = 0; b < turret.size(); b++) {
			turret.get(b).update(delta);
		}
	}
	
	public void removeBullets(){
		if (!turret.isEmpty()) {
			for (int i = 0; i < turret.size(); i++) {
				if (turret.get(i).tBullet != null) {
					turret.get(i).tBullet = null;
				}
			}
		}
	}

	public boolean TurretHit(Rectangle r){
		if (!turret.isEmpty()) {
			for (int i = 0; i < turret.size(); i++) {
				if (turret.get(i).getRect().intersects(r)) {
					turret.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean EnemyBulletCollide(Rectangle r) {
		if (!turret.isEmpty()) {
			for (int i = 0; i < turret.size(); i++) {
				if (turret.get(i).tBullet != null) {
					if (turret.get(i).tBullet.getRect().intersects(r)) {
						turret.get(i).tBullet = null;
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean buildTurret() {
		boolean build = true;
		Turret t = new Turret(60 + (location * 50), 400, 25, 25, "/com/dregronprogram/game_mode/game_images/turretSprite.png");
		if (!turret.isEmpty()) {
			for (int i = 0; i < turret.size(); i++) {
				if (t.getRect().intersects(turret.get(i).getRect()))
					build = false;
			}
		}

		if (build) {
			turret.add(t);
			return true;
		}

		return false;
	}

	public void xPosRight() {
		if (location < 13) {
			location += 1;
		}
	}

	public void xPosLeft() {
		if (location > 0) {
			location -= 1;
		}
	}

}
