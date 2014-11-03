/*
 * Copyright (c) 2014 kademika.com
 */
package BattleField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class BattleField extends JPanel {

	boolean COLORDED_MODE = true;

	int tankX = 0;
	int tankY = 0;

	long speed = 225;

	void runTheGame() throws Exception {
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

	/**
	 * 
	 * @param v
	 *            String in range "a..i"
	 * @param h
	 *            Numeric string in range "1..9"
	 * @return
	 */

	String getQuadrant(String v, String h) {
		String literals = "abcdefghi";
		int x, y;

		x = literals.indexOf(v); // 0..8
		y = Integer.parseInt(h) - 1; // 0..8

		if (x == -1) {
			return "Invalid Input";
		}

		if (y < 0 || y > 8) {
			return "Invalid Input";
		}

		return (x * 64) + "_" + (y * 64);
	}

	void printCoordinates(String v, String h) {
		String result = getQuadrant(v, h);
		String x = result.substring(0, result.indexOf("_"));
		String y = result.substring(result.indexOf("_") + 1);
		System.out.println(v + h + ":(" + x + "px, " + y + "px)");

	}

	void move(int direction) throws Exception {

		if (direction == 1) {

			tankY -= 64;

		} else if (direction == 2) {

			tankY += 64;

		} else if (direction == 3) {

			tankX -= 64;

		} else {

			tankX += 64;
		}
		repaint();

		Thread.sleep(speed);
	}

	void moveRandom() throws Exception {

		while (true) {
			int direction = (int) (System.currentTimeMillis() % 4) + 1;
			move(direction);
		}
	}

	final int BF_WIDTH = 576;
	final int BF_HEIGHT = 576;

	public static void main(String[] args) throws Exception {
		BattleField bf = new BattleField();
		// bf.runTheGame();
		// bf.move(2);
		// bf.move(2);
		// bf.move(4);
		// bf.move(4);
		// bf.move(1);
		// bf.move(3);

		bf.move(2);
		bf.move(2);
		bf.move(2);
		bf.move(4);
		bf.move(4);
		bf.move(4);

		bf.moveRandom();

	}

	public BattleField() throws Exception {
		JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
		frame.setLocation(500, 150);
		frame.setMinimumSize(new Dimension(BF_WIDTH, BF_HEIGHT + 22));
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

		g.setColor(new Color(255, 0, 0));
		g.fillRect(tankX, tankY, 64, 64);
	}

}
