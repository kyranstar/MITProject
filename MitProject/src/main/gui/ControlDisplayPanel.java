package main.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ItemEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ControlDisplayPanel extends JPanel {

	public ControlDisplayPanel() {
		super();
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
