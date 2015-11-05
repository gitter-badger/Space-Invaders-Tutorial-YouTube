package com.dregronprogram.menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.dregronprogram.display.Display;
import com.dregronprogram.state.StateMachine;

public class Menu implements KeyListener {

	private SplashScreen sScreen;
	private byte option = 0;

	public Menu() {
		sScreen = new SplashScreen(0, 0, Display.WIDTH, Display.HEIGHT, "menu_images/IntroMenu.png");
	}

	public void init(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	public void draw(Graphics2D g) {
		if (sScreen.getbImage() != null) {
			sScreen.draw(g);
		} else {
			g.setFont(new Font("", Font.BOLD | Font.ITALIC, 24));
			String ng = "New Game";
			g.drawString(ng, centerText(ng, g), Display.HEIGHT / 2 - 50);
			String code = "Enter Code";
			g.drawString(code, centerText(code, g), Display.HEIGHT / 2);

			switch (option) {
			case 0:
				g.drawString(">", 300, Display.HEIGHT / 2 - 50);
				break;
			case 1:
				g.drawString(">", 300, Display.HEIGHT / 2);
				break;
			}
		}
	}

	public void update(double delta) {
		if (sScreen.getbImage() != null) {
			sScreen.update(delta);
		} else {

		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (option == 1) {
				option = 0;
			} else {
				option = 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			if (option == 1) {
				option = 0;
			} else {
				option = 1;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!Display.getCurrentState().contains(StateMachine.MENU.toString())) {
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Changing to Game Mode");

			if (option == 0)
				Display.setCurrentState(StateMachine.GAMEMODE.toString());
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private int centerText(String s, Graphics2D g) {
		int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int start = Display.WIDTH / 2 - stringLen / 2;

		return start;
	}

}
