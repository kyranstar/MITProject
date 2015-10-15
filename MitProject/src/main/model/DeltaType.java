package main.model;

import java.util.stream.Stream;

public enum DeltaType {
	RED_UP {

		@Override
		public boolean isUp() {
			return true;
		}

		@Override
		public boolean isRed() {
			return true;
		}
	}, RED_DOWN {

		@Override
		public boolean isUp() {
			return false;
		}

		@Override
		public boolean isRed() {
			return true;
		}
	}, BLUE_UP {

		@Override
		public boolean isUp() {
			return true;
		}

		@Override
		public boolean isRed() {
			return false;
		}
	}, BLUE_DOWN {

		@Override
		public boolean isUp() {
			return false;
		}

		@Override
		public boolean isRed() {
			return false;
		}
	};
	
	public abstract boolean isUp();
	public abstract boolean isRed();
	
	public static DeltaType getDelta(boolean isRed, boolean isUp){
		return Stream.of(DeltaType.values())
				.filter((d) -> d.isUp() == isUp && d.isRed() == isRed)
				.findFirst().get();
	}
}
