package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import main.calculations.StructuralCalculations;
import main.gui.ControlDisplayPanel.InterfaceTool;
import main.model.DeltaType;
import main.model.DeltaType.Delta;
import main.model.Grid;

@SuppressWarnings("serial")
public class UserInterface extends JPanel implements MouseListener, KeyListener, MouseMotionListener {

	private Point mousePosition = new Point(0, 0);
	public static final int INFO_WIDTH = 200;
	public static final int GRID_WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int GRID_SIZE = 20;

	private final Grid grid = new Grid(GRID_WIDTH / GRID_SIZE, HEIGHT / GRID_SIZE);

	private final ControlDisplayPanel controlsAndDisplay = new ControlDisplayPanel(grid);

	public static void main(final String[] args) {
		final JFrame frame = new JFrame();
		final UserInterface panel = new UserInterface();

		final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setLeftComponent(panel);
		split.setRightComponent(panel.controlsAndDisplay);

		frame.add(split);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public UserInterface() {
		super();
		setPreferredSize(new Dimension(GRID_WIDTH + INFO_WIDTH, HEIGHT));
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();
	}

	@Override
	protected void paintComponent(final Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		grid.draw(g);

		final DeltaType d = DeltaType.getDelta(controlsAndDisplay.isRed(), controlsAndDisplay.isUp());
		final Point p = Grid.isoToOrtho(mousePosition);
		grid.drawDelta(g, p, new Delta(d, 0), d.isRed() ? new Color(255, 0, 0, 100) : new Color(0, 0, 255, 100));

		if (controlsAndDisplay.isThermalInfo()) {
		} else {
			g.setColor(Color.DARK_GRAY);
			final Point cg = Grid.orthoToIso(StructuralCalculations.centerOfGravity(grid));
			g.fillOval(cg.x - 5, cg.y - 5, 10, 10);

			// sorry for writing this mess
			// for each anchor in screen c
			grid.getAnchors().stream().forEach((anchor) -> {
				final Point screenAnchorA = Grid.orthoToIso(anchor);

				g.setColor(new Color(0, 255, 100));
				g.fillOval(screenAnchorA.x - 5, screenAnchorA.y - 5 + GRID_SIZE, 10, 10);
				final Optional<Point> anchorB = grid.getAnchors().stream().filter((b) -> !b.equals(anchor)).findFirst();
				if (anchorB.isPresent()) {
					final Point anchorForce = StructuralCalculations.supportLoadA(anchor, anchorB.get(), grid);
					g.drawString("(" + anchorForce.x + ", " + anchorForce.y + ")", screenAnchorA.x - 10,
							screenAnchorA.y + 15 + GRID_SIZE);
				}
			});
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		final Point ortho = Grid.isoToOrtho(e.getPoint());
		if(SwingUtilities.isRightMouseButton(e)) return;
		
		if (e.getClickCount() >= 2) {
			if (grid.getDelta(ortho.x, ortho.y).isPresent()) {
				String radiatingNum = JOptionPane.showInputDialog("Radiating length: ");
				if (radiatingNum != null && !radiatingNum.isEmpty()) {
					grid.setDelta(ortho.x, ortho.y,
							new Delta(grid.getDelta(ortho.x, ortho.y).get().type, Float.parseFloat(radiatingNum)));
				}
			}
		} else if (controlsAndDisplay.getTool() == InterfaceTool.REMOVE_TRIANGLE) {
			grid.removeDelta(ortho.x, ortho.y);
		} else if (controlsAndDisplay.getTool() == InterfaceTool.ADD_TRIANGLE) {
			if (grid.getDelta(ortho.x, ortho.y).isPresent())
				return;

			final DeltaType delta = DeltaType.getDelta(controlsAndDisplay.isRed(), controlsAndDisplay.isUp());
			grid.setDelta(ortho.x, ortho.y, new Delta(delta, 0));
		} else if (controlsAndDisplay.getTool() == InterfaceTool.ADD_ANCHOR) {
			if (grid.getAnchors().size() < 2) {
				grid.getAnchors().add(ortho);
			}
		} else if (controlsAndDisplay.getTool() == InterfaceTool.REMOVE_ANCHOR) {
			grid.getAnchors().remove(ortho);
		}
		repaint();
		controlsAndDisplay.repaint();
	}

	@Override
	public void mouseEntered(final MouseEvent e) {

	}

	@Override
	public void mouseExited(final MouseEvent e) {

	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			controlsAndDisplay.isUp = !controlsAndDisplay.isUp;
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {

	}

	@Override
	public void keyPressed(final KeyEvent e) {

	}

	@Override
	public void keyReleased(final KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(final KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		mousePosition = e.getPoint();
		repaint();
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		mousePosition = e.getPoint();
		repaint();
	}
}
