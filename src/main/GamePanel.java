package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48px
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = maxScreenCol * tileSize; // 768px
	public final int screenHeight = maxScreenRow * tileSize; // 576px
	
	// World Parameters	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxWorldWidth = maxWorldCol * tileSize;
	public final int maxWorldHeight = maxWorldRow * tileSize;
	
	// frames per second
	int fps = 60;
	
	TileManager tileManager = new TileManager(this);
	KeyHandler keyHandler = new KeyHandler();
	Thread gameThread;
	public CollisionManager collisionManager = new CollisionManager(this);
	public Player player = new Player(this, keyHandler);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
//	GAME LOOP - SLEEP METHOD
//	public void run() {
//		
//		double drawInterval = 1000000000 / fps;
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while (gameThread != null) {
//			
//			update();
//			
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime / 1000000;
//				
//				if (remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//			
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
		
	public void run() {
		double drawInterval = 1000000000 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		Graphics2D g2 = (Graphics2D)g;
		
		tileManager.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}
}
