package main.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import main.calculations.StructuralCalculations;
import main.calculations.ThermalCalculations;
import main.model.Grid;

@SuppressWarnings("serial")
public class ControlDisplayPanel extends JPanel {

	private final Grid grid;

	public ControlDisplayPanel(final Grid grid) {
		super();
		this.grid = grid;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createIsRedButton());
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
			ControlDisplayPanel.super.repaint();
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
			int aveTemp;
			try {
				aveTemp = ThermalCalculations.aveTemp(grid);
			} catch (ArithmeticException e) {
				aveTemp = -1;
			}
			g.drawString("Average temperature: " + (aveTemp == -1 ? "Undefined" : String.valueOf(aveTemp)), 10, TOP);
		} else {
			final Point cg = StructuralCalculations.centerOfGravity(grid);
			g.drawString("Center of gravity: (" + cg.x + ", " + cg.y + ")", 10, TOP);
		}
		g.drawString(new DecimalFormat("Percent blue: #.##").format(grid.percentBlue()), 10, TOP + 15);
	}

	private boolean isThermalInfo;
	private boolean isRed;
	boolean isUp;
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
		ADD_TRIANGLE, REMOVE_TRIANGLE, ADD_ANCHOR, REMOVE_ANCHOR;
	}
}
