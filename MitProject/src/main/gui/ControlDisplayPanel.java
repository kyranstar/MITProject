package main.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ItemEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import main.calculations.StructuralCalculations;
import main.model.Grid;

public class ControlDisplayPanel extends JPanel {

	private Grid grid;

	public ControlDisplayPanel(Grid grid) {
		super();
		this.grid = grid;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createIsRedButton());
		add(createIsUpButton());
		add(createRemoveButton());
	}

	private Component createRemoveButton() {
		JToggleButton button = new JToggleButton("Remove triangles?");
		button.addItemListener((e) -> {			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				isRemoving = true;
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				isRemoving = false;
			}

		});
		return button;
	}

	private Component createIsUpButton() {
		JToggleButton button = new JToggleButton("Create upwards triangles?");
		button.addItemListener((e) -> {			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				isUp = true;
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				isUp = false;
			}

		});
		return button;
	}

	private Component createIsRedButton() {
		JToggleButton button = new JToggleButton("Create red triangles?");
		button.addItemListener((e) -> {			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				isRed = true;
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				isRed = false;
			}

		});
		return button;
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		final int TOP = 100;
		Point cg = StructuralCalculations.centerOfGravity(grid);
		g.drawString("Center of gravity: (" + cg.x + ", " + cg.y + ")", 10, TOP);
	}

	private boolean isRed;
	private boolean isUp;
	private boolean isRemoving;

	public boolean isRemoving() {
		return isRemoving;
	}

	public boolean isRed() {
		return isRed;
	}

	public boolean isUp() {
		return isUp;
	}
}
