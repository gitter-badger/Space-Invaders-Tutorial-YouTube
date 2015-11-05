package com.dregronprogram.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.dregronprogram.game_mode.GameMode;
import com.dregronprogram.menu.Menu;
import com.dregronprogram.state.StateMachine;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Display display = new Display();
		JFrame frame = new JFrame();
		frame.add(display);
		frame.pack();
		frame.setTitle("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		display.start();
	}

	public boolean running = false;
	public Thread thread;

	public synchronized void start() {
		if (running)
			return;

		running = true;

		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Thread resume error!");
		}
	}

	public static void setCurrentState(String state) {
		for (int i = 0; i < map.size(); i++) {
			map.get(map.keySet().toArray()[i]);
			map.put(map.keySet().toArray()[i].toString(), Boolean.FALSE);
			if (map.keySet().toArray()[i] == state) {
				map.get(map.keySet().toArray()[i]);
				map.put(map.keySet().toArray()[i].toString(), Boolean.TRUE);
			}
		}
	}

	public static String getCurrentState() {
		for (int i = 0; i < map.size(); i++) {
			if (map.get(map.keySet().toArray()[i]) == true) {
				return map.keySet().toArray()[i].toString();
			}
		}
		return null;
	}

	public static int WIDTH = 800, HEIGHT = 600;
	public static Map<String, Boolean> map = new HashMap<String, Boolean>();

	private Object[] AllStates;
	private int frameRate;
	private int Ups;

	public Display() {
		this.setSize(WIDTH, HEIGHT);
		StateMachine.values();
		setCurrentState(StateMachine.GAMEMODE.toString());
		AllStates = new Object[map.size()];
		AllStates[0] = new GameMode();
		AllStates[1] = new Menu();

//		for (int i = 0; i < map.size(); i++) {
//			System.out.println(map.keySet().toArray()[i] + ": " + map.get(map.keySet().toArray()[i]));
//		}
//		System.out.println(map.size());
	}

	public void run() {
		initState();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 140.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		this.createBufferStrategy(3);
		BufferStrategy strategy = this.getBufferStrategy();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(delta);
				frames++;
				delta--;
			}
			draw(strategy);
			updates++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frameRate = frames;
				this.Ups = updates;
				// System.out.println("Updates: " + updates + ", " + "Frames: "
				// + frames);
				updates = 0;
				frames = 0;
			}

		}
	}
	
	
	public void draw(BufferStrategy strategy) {
		do {
			do {
				Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, WIDTH + 50, HEIGHT + 50);
				g.setColor(Color.white);
				g.setFont(new Font("Serif", Font.PLAIN, 14));
				g.drawString("Ups: " + this.Ups, 4, 12);
				g.drawString("FPS: " + this.frameRate, 4, 24);
				
				//draw method class from the current state.
				for (int i = 0; i < AllStates.length; i++) {
					if (getCurrentState().toLowerCase().contains(AllStates[i].getClass().getSimpleName().toLowerCase())) {
						try {
							Method m = AllStates[i].getClass().getDeclaredMethod("draw", Graphics2D.class);
							m.invoke(AllStates[i], g);
						} catch (NoSuchMethodException e) {
							System.out.println("graphics Error!");
							System.out.println(e.toString());
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				g.dispose();
			} while (strategy.contentsRestored());
			strategy.show();
		} while (strategy.contentsLost());

	}

	public void update(double delta) {
		for (int i = 0; i < AllStates.length; i++) {
		
			//update method class from the current state.
			if (getCurrentState().toLowerCase().contains(AllStates[i].getClass().getSimpleName().toLowerCase())) {
				try {
					Method m = AllStates[i].getClass().getDeclaredMethod("update", double.class);
					m.invoke(AllStates[i], delta);
				} catch (NoSuchMethodException e) {
					System.out.println("delta Error!");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void initState() {
		//Initialise each state.
		for (int i = 0; i < AllStates.length; i++) {
			try {
				Method m = AllStates[i].getClass().getDeclaredMethod("init", Canvas.class);
				m.invoke(AllStates[i], this);
			} catch (NoSuchMethodException e) {
				System.out.println("init Error!");
				System.out.println(e.toString());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
