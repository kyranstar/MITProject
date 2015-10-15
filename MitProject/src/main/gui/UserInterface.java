package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import main.model.DeltaType;
import main.model.Grid;

@SuppressWarnings("serial")
public class UserInterface extends JPanel implements MouseListener, KeyListener {

	public static final int INFO_WIDTH = 200;
	public static final int GRID_WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int GRID_SIZE = 20;
	
	private ControlDisplayPanel controlsAndDisplay = new ControlDisplayPanel();

	private Grid grid = new Grid(GRID_WIDTH / GRID_SIZE, HEIGHT / GRID_SIZE);

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		UserInterface panel = new UserInterface();

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
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
		setFocusable(true);
		requestFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, getWidth(), getHeight());
		grid.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point ortho = Grid.isoToOrtho(e.getPoint());
		if (controlsAndDisplay.isRemoving()) {
			grid.removeDelta(ortho.x, ortho.y);
		} else {
			DeltaType delta;
			if (controlsAndDisplay.isRed()) {
				if (controlsAndDisplay.isUp()) {
					delta = DeltaType.RED_UP;
				} else {
					delta = DeltaType.RED_DOWN;
				}
			} else {
				if (controlsAndDisplay.isUp()) {
					delta = DeltaType.BLUE_UP;
				} else {
					delta = DeltaType.BLUE_DOWN;
				}
			}
			grid.setDelta(ortho.x, ortho.y, delta);
		}
		repaint();
		controlsAndDisplay.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
