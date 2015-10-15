package main.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Optional;

import main.gui.UserInterface;

public class Grid {

	private Optional<DeltaType>[][] grid;

	@SuppressWarnings("unchecked")
	public Grid(int width, int height) {
		grid = (Optional<DeltaType>[][]) new Optional[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = Optional.empty();
			}
		}
	}

	public void draw(Graphics g) {
		drawGrid(g);
	}

	private void drawGrid(Graphics g) {		
		g.setColor(Color.BLACK);
		g.drawString("X", 40, 350);
		g.drawString("Y", 40, 150);
		for (int x = 0; x < UserInterface.GRID_WIDTH; x += UserInterface.GRID_SIZE) {
			g.drawLine(x, 0, (int) (x + Math.cos(Math.PI / 3) * UserInterface.GRID_WIDTH), UserInterface.HEIGHT);
		}
		for (int y = 0; y < UserInterface.HEIGHT; y += UserInterface.GRID_SIZE) {
			g.drawLine(y, UserInterface.HEIGHT, (int) (y + Math.cos(Math.PI / 3) * UserInterface.HEIGHT), 0);
		}
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				Point p = new Point(x, y);
				grid[x][y].ifPresent((d) -> {
					int[] xs;
					int[] ys;

					if (d.isUp()) {
						xs = new int[] { -UserInterface.GRID_SIZE, UserInterface.GRID_SIZE, 0 };
						ys = new int[] { UserInterface.GRID_SIZE * 2, UserInterface.GRID_SIZE * 2, 0 };
					} else {
						xs = new int[] { -UserInterface.GRID_SIZE, UserInterface.GRID_SIZE, 0 };
						ys = new int[] { 0, 0, UserInterface.GRID_SIZE * 2 };
					}

					Polygon tri = new Polygon(xs, ys, 3);
					Point isoCoord = orthoToIso(p);
					tri.translate(isoCoord.x, isoCoord.y);

					g.setColor(d.getColor());
					g.fillPolygon(tri);
					g.setColor(Color.BLACK);
					g.drawPolygon(tri);
				});
			}
		}
	}

	public static Point orthoToIso(Point p) {
		int x = p.x * UserInterface.GRID_SIZE;
		int y = p.y * UserInterface.GRID_SIZE;
		int screenX = (x + y) / 2;
		int screenY = (x - y);

		return new Point(screenX + UserInterface.GRID_WIDTH / 4, screenY + UserInterface.HEIGHT / 2);
	}

	public static Point isoToOrtho(Point p) {
		Point iso = new Point(p.x - UserInterface.GRID_WIDTH / 4, p.y - UserInterface.HEIGHT / 2);
		int cartY = (2 * iso.x - iso.y) / 2;
		int cartX = (2 * iso.x + iso.y) / 2;

		return new Point(cartX / UserInterface.GRID_SIZE, cartY / UserInterface.GRID_SIZE);
	}

	public void setDelta(int x, int y, DeltaType t) {
		if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
			return;
		grid[x][y] = Optional.of(t);
	}

	public void removeDelta(int x, int y) {
		grid[x][y] = Optional.empty();
	}

	public Optional<DeltaType> getDelta(int x, int y) {
		return grid[x][y];
	}
}
