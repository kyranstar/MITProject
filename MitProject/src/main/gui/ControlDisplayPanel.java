package main.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ItemEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import main.calculations.StructuralCalculations;
import main.model.Grid;

public class ControlDisplayPanel extends JPanel {

	private final Grid grid;

	public ControlDisplayPanel(final Grid grid) {
		super();
		this.grid = grid;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createIsRedButton());
		add(createIsUpButton());
		createToolChooser();
		add(createIsThermalInfo());
	}

	private Component createIsThermalInfo() {
		final JToggleButton button = new JToggleButton("Display thermal info?");
		button.addItemListener((e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				isThermalInfo = true;
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				isThermalInfo = false;
			}

		});
		return button;
	}

	private void createToolChooser() {
		final JRadioButton removeTri = new JRadioButton("Remove triangle tool");
		removeTri.addActionListener((e) -> tool = InterfaceTool.REMOVE_TRIANGLE);
		final JRadioButton addTri = new JRadioButton("Add triangle tool");
		addTri.addActionListener((e) -> tool = InterfaceTool.ADD_TRIANGLE);
		final JRadioButton removeAnchor = new JRadioButton("Remove anchor tool");
		removeAnchor.addActionListener((e) -> tool = InterfaceTool.REMOVE_ANCHOR);
		final JRadioButton addAnchor = new JRadioButton("Add anchor tool");
		addAnchor.addActionListener((e) -> tool = InterfaceTool.ADD_ANCHOR);

		final ButtonGroup group = new ButtonGroup();
		group.add(removeTri);
		group.add(addTri);
		group.add(removeAnchor);
		group.add(addAnchor);

		add(removeTri);
		add(addTri);
		add(removeAnchor);
		add(addAnchor);

		addTri.setSelected(true);
	}

	private Component createIsUpButton() {
		final JToggleButton button = new JToggleButton("Create upwards triangles?");
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
		final JToggleButton button = new JToggleButton("Create red triangles?");
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
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		final int TOP = 250;
		if (isThermalInfo()) {

		} else {
			final Point cg = StructuralCalculations.centerOfGravity(grid);
			g.drawString("Center of gravity: (" + cg.x + ", " + cg.y + ")", 10, TOP);
		}
	}

	private boolean isThermalInfo;
	private boolean isRed;
	private boolean isUp;
	private InterfaceTool tool = InterfaceTool.ADD_TRIANGLE;

	public boolean isThermalInfo() {
		return isThermalInfo;
	}

	public InterfaceTool getTool() {
		return tool;
	}

	public boolean isRed() {
		return isRed;
	}

	public boolean isUp() {
		return isUp;
	}

	public static enum InterfaceTool {
		ADD_TRIANGLE,
		REMOVE_TRIANGLE,
		ADD_ANCHOR,
		REMOVE_ANCHOR;
	}
}
