/*
 * Copyright (c) 2013 midgardabc.com
 */

package BattleField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @version 3.0
 */

public class BattleField2 extends JPanel {

	final boolean COLORDED_MODE = true;

	final int BF_WIDTH = 576;
	final int BF_HEIGHT = 576;

	// 1 - top, 2 - bottom, 3 - left, 4 - right
	int tankDirection = 1;

	int tankX = 128;
	int tankY = 512;

	int bulletX = -100;
	int bulletY = -100;

	int speed = 225;
	int bulletSpeed = 5;

	String[][] battleField = { { "B", "B", "B", "B", "B", "B", "B", "B", "B" },
			{ " ", " ", " ", "B", "B", "B", " ", " ", " " },
			{ " ", " ", "B", "B", " ", " ", " ", " ", " " },
			{ " ", " ", "B", "B", " ", " ", " ", " ", " " },
			{ " ", " ", " ", " ", "B", "B", "B", " ", " " },
			{ " ", " ", " ", " ", " ", " ", " ", "B", "B" },
			{ "B", "B", "B", " ", " ", " ", " ", "B", "B" },
			{ " ", " ", " ", "B", "B", "B", " ", " ", " " },
			{ " ", " ", " ", "B", " ", "B", " ", " ", " " } };

	void runTheGame() throws Exception {

		System.out.println(getQuadrant(64, 64));
		System.out.println(getQuadrant(0, 60));
		System.out.println(getQuadrant(0, 64));
		System.out.println(getQuadrant(68, 96));
		System.out.println(getQuadrant(245, 408));

//		while(true){
//			fire();	
//		}
		moveRandom();
		

		// moveToQuadrant(1, 2);
		// Thread.sleep(2000);
		// moveToQuadrant(3, 3);
		// Thread.sleep(2000);
		// moveToQuadrant(1, 4);
		// Thread.sleep(2000);
		// moveToQuadrant(9, 8);
	}

	boolean processInterception() {

		int x = (int) (bulletX / 64);
		int y = (int) (bulletY / 64);
		
		if( x < 0 || y < 0 || y > battleField.length-1 || x > battleField[y].length-1){
			return false;
		}
		
		String cell = battleField[y][x];
//		System.out.println(cell + i + t);
		if (cell.equals(" ")) {
			return false;
		} else {
			battleField[y][x] = " ";
			return true;
		}
	}

	// String getQuadrant(int x, int y) {
	// return null;
	// }

	void moveToSides() throws Exception {
		String moveTo = "right";

		while (true) {
			if (moveTo.equals("right")) {

				tankX += 64;

				if (tankX >= 8 * 64) {
					moveTo = "left";
				}
			} else {

				tankX -= 64;

				if (tankX <= 0) {
					moveTo = "right";
				}
			}

			repaint();
			Thread.sleep(speed);

		}
	}

	void fire() throws Exception {

		int bulletSpeed = 5;

		bulletX = tankX + 25;
		bulletY = tankY + 25;

		while (bulletX > -14 && bulletX < 590 && bulletY > -14 && bulletY < 590) {

			if (tankDirection == 1) {
				bulletY -= bulletSpeed;

			} else if (tankDirection == 2) {
				bulletY += bulletSpeed;

			} else if (tankDirection == 3) {
				bulletX -= bulletSpeed;

			} else {
				bulletX += bulletSpeed;
			}
			if (processInterception()){
				bulletY = -100;
				return;
			}
			repaint();
			Thread.sleep(10);

		}

		//
		// bulletX = tankX + 25;
		// bulletY = tankY + 25;
		//
		// int shot = 1;
		//
		// while ((bulletX > -14 && bulletX < 590)
		// && (bulletY > -14 && bulletY < 590)) {
		//
		// if (tankDirection == 1) {
		// bulletY -= shot;
		// }
		//
		// else if (tankDirection == 2) {
		// bulletY += shot;
		// }
		//
		// else if (tankDirection == 3) {
		// bulletX -= shot;
		//
		// } else {
		// bulletX += shot;
		// }
		// repaint();
		// Thread.sleep(bulletSpeed);
		// }
	}

	String getQuadrantXY(int v, int h) {
		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	void printCoordinates(int v, int h) {
		String result = getQuadrantXY(v, h);
		String x = result.substring(0, result.indexOf("_"));
		String y = result.substring(result.indexOf("_") + 1);
		System.out.println(v + h + ":(" + x + "px, " + y + "px)");

	}

	String getQuadrant(int x, int y) {

		int i = (int) (x / 64);
		int t = (int) (y / 64);

		return i + "_" + t;

	}


	void moveRandom() throws Exception {

		while (true) {
			int direction = (int) (System.currentTimeMillis() % 4) + 1;
			move(direction);
		}
	}

	void move(int direction) throws Exception {

		// check limits x: 0, 513; y: 0, 513

		if ((direction == 1 && tankY == 0) || (direction == 2 && tankY >= 512)
				|| (direction == 3 && tankX == 0)
				|| (direction == 4 && tankX >= 512)) {

			return;
		}

		tankDirection = direction;
		
		fire();

		for (int i = 0; i < 32; i++) {

			if (direction == 1) {

				tankY -= 2;

			} else if (direction == 2) {

				tankY += 2;

			} else if (direction == 3) {

				tankX -= 2;

			} else {

				tankX += 2;
			}
			repaint();
			Thread.sleep(speed / 32);
	
		}
		 
	}

	// void move(int direction) throws Exception {
	// int step = 1;
	// int covered = 0;
	//
	// // check limits x: 0, 513; y: 0, 513
	// if ((direction == 1 && tankY == 0) || (direction == 2 && tankY >= 512)
	// || (direction == 3 && tankX == 0)
	// || (direction == 4 && tankX >= 512)) {
	// System.out.println("[illegal move] direction: " + direction
	// + " tankX: " + tankX + ", tankY: " + tankY);
	// return;
	// }
	//
	// turn(direction);

	// while (covered < 64) {
	// if (direction == 1) {
	// tankY -= step;
	// System.out.println("[move up] direction: " + direction
	// + " tankX: " + tankX + ", tankY: " + tankY);
	// } else if (direction == 2) {
	// tankY += step;
	// System.out.println("[move down] direction: " + direction
	// + " tankX: " + tankX + ", tankY: " + tankY);
	// } else if (direction == 3) {
	// tankX -= step;
	// System.out.println("[move left] direction: " + direction
	// + " tankX: " + tankX + ", tankY: " + tankY);
	// } else {
	// tankX += step;
	// System.out.println("[move right] direction: " + direction
	// + " tankX: " + tankX + ", tankY: " + tankY);
	// }
	// covered += step;
	//
	// repaint();
	// Thread.sleep(speed);
	// }
	// }

	void turn(int direction) {
		if (tankDirection != direction) {
			tankDirection = direction;
		}
	}

	void moveRandom2() throws Exception {
		Random r = new Random();
		int i;
		while (true) {
			i = r.nextInt(5);
			if (i > 0) {
				move(i);
			}
		}
	}

	void moveToQuadrant(int v, int h) throws Exception {
		String coordinates = getQuadrantXY(v, h);
		int separator = coordinates.indexOf("_");
		int y = Integer.parseInt(coordinates.substring(0, separator));
		int x = Integer.parseInt(coordinates.substring(separator + 1));

		if (tankX < x) {
			while (tankX != x) {
				move(4);
			}
		} else {
			while (tankX != x) {
				move(3);
			}
		}

		if (tankY < y) {
			while (tankY != y) {
				move(2);
			}
		} else {
			while (tankY != y) {
				move(1);
			}
		}
	}


	public static void main(String[] args) throws Exception {
		BattleField2 bf = new BattleField2();
		bf.runTheGame();
	}

	public BattleField2() throws Exception {
		JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(BF_WIDTH + 22, BF_HEIGHT + 44));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	// @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int i = 0;
		Color cc;
		for (int v = 0; v < 9; v++) {
			for (int h = 0; h < 9; h++) {
				if (COLORDED_MODE) {
					if (i % 2 == 0) {
						cc = new Color(252, 241, 177);
					} else {
						cc = new Color(233, 243, 255);
					}
				} else {
					cc = new Color(180, 180, 180);
				}
				i++;
				g.setColor(cc);
				g.fillRect(h * 64, v * 64, 64, 64);
			}
		}

		for (int j = 0; j < battleField.length; j++) {
			for (int k = 0; k < battleField.length; k++) {
				if (battleField[j][k].equals("B")) {
					String coordinates = getQuadrantXY(j + 1, k + 1);
					int separator = coordinates.indexOf("_");
					int y = Integer.parseInt(coordinates
							.substring(0, separator));
					int x = Integer.parseInt(coordinates
							.substring(separator + 1));
					g.setColor(new Color(0, 125, 120));
					g.fillRect(x, y, 64, 64);
				}
			}
		}

		g.setColor(new Color(155, 10, 0));
		g.fillRect(tankX, tankY, 64, 64);

		g.setColor(new Color(0, 130, 0));
		if (tankDirection == 1) {
			g.fillRect(tankX + 20, tankY, 24, 34);
		} else if (tankDirection == 2) {
			g.fillRect(tankX + 20, tankY + 30, 24, 34);
		} else if (tankDirection == 3) {
			g.fillRect(tankX, tankY + 20, 34, 24);
		} else {
			g.fillRect(tankX + 30, tankY + 20, 34, 24);
		}

		g.setColor(new Color(255, 255, 0));
		g.fillOval(bulletX, bulletY, 14, 14);
	}

}
