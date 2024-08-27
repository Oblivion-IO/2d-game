package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
	
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
		screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		
		setDefaultPositions();
		getPlayerImage();
	}

	public void setDefaultPositions() {
		worldX = gamePanel.tileSize * 22;
		worldY = gamePanel.tileSize * 23;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-down1.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-up1.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-left1.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-right1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-down2.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-up2.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-left2.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/jinx-right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		
		if (keyHandler.upPressed == true || keyHandler.downPressed == true || keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
			
			if (keyHandler.upPressed == true) {
				direction = "up";
			} 
			else if (keyHandler.downPressed == true) {
				direction = "down";
			}
			else if (keyHandler.leftPressed == true) {
				direction = "left";
			}
			else if (keyHandler.rightPressed == true) {
				direction = "right";
			}
			
			collisionOn = false;
			gamePanel.collisionManager.checkTile(this);
			
			if (collisionOn == false) {
				
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;	
				case "right": worldX += speed; break;
				}
			
			}
			
			spriteCounter++;
			
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} 
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		
//		g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up": 
			if (spriteNum == 1) {
				image = up1;
			}
			
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			
			if (spriteNum == 2) { 
				image = down2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		default:
			if (spriteNum == 1) {
				image = down1;
			}
			
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
	}
}
