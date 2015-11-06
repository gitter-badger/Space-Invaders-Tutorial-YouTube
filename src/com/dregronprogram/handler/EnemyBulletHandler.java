package com.dregronprogram.handler;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.dregronprogram.enemy_bullets.EnemyWeaponType;
import com.dregronprogram.game_screen.BasicBlocks;
import com.dregronprogram.game_screen.Player;

public class EnemyBulletHandler {
	
	private List<EnemyWeaponType> weaponTypes = new ArrayList<>();
	
	public void addBullet(EnemyWeaponType weaponType) {
		this.weaponTypes.add(weaponType);
	}

	public void draw(Graphics2D g) {
		for (EnemyWeaponType enemyWeaponType : weaponTypes) {
			enemyWeaponType.draw(g);
		}
	}
	
	public void update(double delta, BasicBlocks blocks, Player player) {
		for (EnemyWeaponType enemyWeaponType : weaponTypes) {
			enemyWeaponType.update(delta, blocks, player);
		}
	}
	
}
