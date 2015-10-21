package main.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import main.gui.UserInterface;
import main.model.DeltaType.Delta;

public class Grid {

	private final Optional<Delta>[][] grid;
	private final Set<Point> anchors = new HashSet<>();

	@SuppressWarnings("unchecked")
	public Grid(final int width, final int height) {
		grid = new Optional[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = Optional.empty();
			}
		}
	}

	public void draw(final Graphics g) {
		drawGrid(g);
	}

	private void drawGrid(final Graphics g) {
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
				final Point p = new Point(x, y);
				grid[x][y].ifPresent((d) -> {
					drawDelta(g, p, d, d.type.isRed() ? Color.RED : Color.BLUE);
				});
			}
		}
	}

	public void drawDelta(final Graphics g, final Point p, final Delta d, final Color c) {
		int[] xs;
		int[] ys;

		if (d.type.isUp()) {
			xs = new int[] { -UserInterface.GRID_SIZE, UserInterface.GRID_SIZE, 0 };
			ys = new int[] { UserInterface.GRID_SIZE * 2, UserInterface.GRID_SIZE * 2, 0 };
		} else {
			xs = new int[] { -UserInterface.GRID_SIZE, UserInterface.GRID_SIZE, 0 };
			ys = new int[] { 0, 0, UserInterface.GRID_SIZE * 2 };
		}

		final Polygon tri = new Polygon(xs, ys, 3);
		final Point isoCoord = orthoToIso(p);
		tri.translate(isoCoord.x, isoCoord.y);

		g.setColor(c);
		g.fillPolygon(tri);
		g.setColor(Color.BLACK);
		g.drawPolygon(tri);

		if (d.amountRadiating != 0) {
			g.setColor(Color.MAGENTA);
			FontMetrics fm = g.getFontMetrics();
			String radiatingString = String.valueOf(d.amountRadiating);
			g.drawString(radiatingString, (int) (isoCoord.getX() - fm.stringWidth(radiatingString) / 2),
					(int) (isoCoord.getY() + UserInterface.GRID_SIZE / 2) + fm.getHeight() / 2);

		}
	}

	public static Point orthoToIso(final Point p) {
		final int x = p.x * UserInterface.GRID_SIZE;
		final int y = p.y * UserInterface.GRID_SIZE;
		final int screenX = (x + y) / 2;
		final int screenY = x - y;

		return new Point(screenX + UserInterface.GRID_WIDTH / 4, screenY + UserInterface.HEIGHT / 2);
	}

	public static Point isoToOrtho(final Point p) {
		final Point iso = new Point(p.x - UserInterface.GRID_WIDTH / 4, p.y - UserInterface.HEIGHT / 2);
		final int cartY = (2 * iso.x - iso.y) / 2;
		final int cartX = (2 * iso.x + iso.y) / 2;

		return new Point(cartX / UserInterface.GRID_SIZE, cartY / UserInterface.GRID_SIZE);
	}

	public void setDelta(final int x, final int y, final Delta t) {
		if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
			return;
		}
		grid[x][y] = Optional.of(t);
	}

	public void removeDelta(final int x, final int y) {
		grid[x][y] = Optional.empty();
	}

	public Optional<Delta> getDelta(final int x, final int y) {
		return grid[x][y];
	}

	public List<DeltaSlot> getDeltas() {
		final List<DeltaSlot> deltas = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y].isPresent()) {
					deltas.add(new DeltaSlot(x, y, grid[x][y].get()));
				}
			}
		}
		return deltas;
	}

	public Set<Point> getAnchors() {
		return anchors;
	}

	public static class DeltaSlot {
		public final int x;
		public final int y;
		public final Delta delta;

		public DeltaSlot(final int x, final int y, final Delta type) {
			this.x = x;
			this.y = y;
			this.delta = type;
		}
	}

	public float percentBlue() {
		int numBlue = (int) getDeltas().stream().filter((d) -> !d.delta.type.isRed()).count();
		return (float) numBlue / getDeltas().size() * 100;
	}
}
