package main.model;

import java.awt.Color;

public enum DeltaType {
	RED_UP {
		@Override
		public Color getColor() {
			return Color.RED;
		}

		@Override
		public boolean isUp() {
			return true;
		}
	}, RED_DOWN {
		@Override
		public Color getColor() {
			return Color.RED;
		}

		@Override
		public boolean isUp() {
			return false;
		}
	}, BLUE_UP {
		@Override
		public Color getColor() {
			return Color.BLUE;
		}

		@Override
		public boolean isUp() {
			return true;
		}
	}, BLUE_DOWN {
		@Override
		public Color getColor() {
			return Color.BLUE;
		}

		@Override
		public boolean isUp() {
			return false;
		}
	};
	
	public abstract Color getColor();
	public abstract boolean isUp();
}
