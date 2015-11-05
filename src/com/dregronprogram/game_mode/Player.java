package com.dregronprogram.game_mode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.dregronprogram.display.Display;
import com.dregronprogram.game_mode.player_bullets.ExplodBullet;
import com.dregronprogram.game_mode.player_bullets.LaserBeam;
import com.dregronprogram.game_mode.player_bullets.PlayerBasicBullets;
import com.dregronprogram.game_mode.player_bullets.TurretConstruction;
import com.dregronprogram.image_objects.ImageObject;
import com.dregronprogram.state.StateMachine;

public class Player extends ImageObject implements KeyListener {

	private final double speed = 2.0d;
	private boolean right = false, left = false, shoot = false;
	private long time;
	private int currentWeapon = 0;

	private enum weapon {
		Bullet(999), BoomStick(1000), LaserGun(10), Turret(14);

		weapon(int i) {
			this.ammo = i;
		}

		private int ammo;

		public int getAmmo() {
			return ammo;
		}

		public void setAmmo(int ammo) {
			this.ammo = ammo;
		}
	};

	public ArrayList<PlayerBasicBullets> pbbBullets = new ArrayList<PlayerBasicBullets>();
	public ArrayList<ExplodBullet> ebBullets = new ArrayList<ExplodBullet>();
	public LaserBeam lBeam;
	public TurretConstruction tConstruction;
	//public Turret turret;

	public Player(double xPos, double yPos, int width, int height, String imgPath) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setWidth(width);
		this.setHeight(height);
		this.setbImage(imgPath);
		this.setRect(new Rectangle((int) this.getxPos(), (int) this.getyPos() + 25, this.getWidth(), this.getHeight() - 25));

		time = System.currentTimeMillis();
		tConstruction = new TurretConstruction();
	}

	public void draw(Graphics2D g) {
		g.setFont(new Font("Serif", Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("Weapon: " + weapon.values()[currentWeapon] + " x " + weapon.values()[currentWeapon].getAmmo(), 5, Display.HEIGHT);
		for (int b = 0; b < pbbBullets.size(); b++) {
			pbbBullets.get(b).draw(g);
		}
		for (int b = 0; b < ebBullets.size(); b++) {
			ebBullets.get(b).draw(g);
		}
		if(currentWeapon == 3 || !tConstruction.turret.isEmpty()){
			tConstruction.draw(g, weapon.Turret.getAmmo());
		}
		
		if (lBeam != null)
			lBeam.draw(g);
		g.drawImage(getbImage(), (int) getxPos(), (int) getyPos(), getWidth(), getHeight(), null);
	}

	public void update(double delta) {
		for (int b = 0; b < pbbBullets.size(); b++) {
			pbbBullets.get(b).update(delta);
			pbbBullets.get(b).isOutofBounds(b, pbbBullets);
		}
		for (int b = 0; b < ebBullets.size(); b++) {
			ebBullets.get(b).update(delta);
			ebBullets.get(b).isOutofBounds(b, ebBullets);
		}
		if(currentWeapon == 3 || !tConstruction.turret.isEmpty()){
			tConstruction.update(delta);
		}
		if (lBeam != null) {
			if (!lBeam.finish(400)) {
				lBeam.update(delta);
			} else
				lBeam = null;
		}

		if (right && this.getxPos() < (Display.WIDTH - getWidth() - 5) && !left) {
			this.setxPos(this.getxPos() + speed);
			this.getRect().x = (int) this.getxPos();
		}

		if (left && this.getxPos() > 10 && !right) {
			this.setxPos(this.getxPos() - speed);
			this.getRect().x = (int) this.getxPos();
		}

		if (shoot) {
			if (cooldown(250)) {
				time = System.currentTimeMillis();
				shoot();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (!Display.getCurrentState().contains(StateMachine.GAMEMODE.toString()) || lBeam != null) {
			return;
		}

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			for (int i = currentWeapon; i < weapon.values().length; i++) {
				if (weapon.values()[i].getAmmo() != 0 && weapon.values()[currentWeapon] != weapon.values()[i]) {
					currentWeapon = i;
					break;
				}
			}
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			for (int i = currentWeapon; i >= 0; i--) {
				if (weapon.values()[i].getAmmo() != 0 && weapon.values()[currentWeapon] != weapon.values()[i]) {
					currentWeapon = i;
					break;
				}
			}
		}

		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			if (weapon.Turret.getAmmo() != 0 && currentWeapon == 3) {
				tConstruction.xPosRight();
			} else
				right = true;
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			if (weapon.Turret.getAmmo() != 0 && currentWeapon == 3) {
				tConstruction.xPosLeft();
			} else
				left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!Display.getCurrentState().contains(StateMachine.GAMEMODE.toString())) {
			return;
		}

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			right = false;
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = false;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean cooldown(int timer) {
		if (System.currentTimeMillis() - (time) > timer) {
			time = timer;
			return true;
		}
		return false;
	}

	private void shoot() {
		switch (currentWeapon) {
		case 0:
			// if (ebBullets.isEmpty()) {
			PlayerBasicBullets pbbBullet = new PlayerBasicBullets(this.getxPos() + 21, this.getyPos() + 20, 5, 5, "/com/dregronprogram/game_mode/game_images/playerBullet.png");
			pbbBullets.add(pbbBullet);
			// }
			break;
		case 1:
			if (weapon.BoomStick.getAmmo() > 0) {
				weapon.BoomStick.setAmmo(weapon.BoomStick.ammo - 1);
				if(weapon.BoomStick.getAmmo() == 0)
					currentWeapon = 0;
				ExplodBullet ebBulet = new ExplodBullet(this.getxPos() + 21, this.getyPos() + 20, 5, 5);
				ebBulet.time = System.currentTimeMillis();
				ebBullets.add(ebBulet);
			}
			break;
		case 2:
			if (lBeam == null && right != true && left != true && weapon.LaserGun.getAmmo() > 0) {
				weapon.LaserGun.setAmmo(weapon.LaserGun.ammo - 1);
				if(weapon.LaserGun.getAmmo() == 0)
					currentWeapon = 0;
				lBeam = new LaserBeam(this.getxPos() + 22, this.getyPos() + 18, 7);
				lBeam.time = System.currentTimeMillis();
			}
			break;
		case 3:
			if(tConstruction.buildTurret() && weapon.Turret.getAmmo() != 0){
				weapon.Turret.setAmmo(weapon.Turret.ammo - 1);
				if(weapon.Turret.getAmmo() == 0){
					currentWeapon = 0;
				}
			} 
			break;
		}
	}

}
