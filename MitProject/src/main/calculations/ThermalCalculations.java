package main.calculations;

import main.model.Grid;

public class ThermalCalculations {
	private static final int q0 = 160;
	private static final int kr = 1;

	public static int maxTemp(Grid grid) {
		return -1;
	}

	public static int minTemp(Grid grid) {
		return -1;
	}

	public static int aveTemp(Grid grid) {
		return (numRedDeltas(grid) * q0) / (numRadiatingDeltas(grid) * averageRadiatingLength(grid) * kr);
	}

	private static int averageRadiatingLength(Grid grid) {
		return grid.getDeltas().stream().mapToInt((d) -> radiantLength(grid, d.x, d.y)).sum() / grid.getDeltas().size();
	}

	private static int numRedDeltas(Grid grid) {
		return (int) grid.getDeltas().stream().filter((d) -> d.type.isRed()).count();
	}

	private static int numRadiatingDeltas(Grid grid) {
		return 0;
	}

	private static int radiantLength(Grid grid, int x, int y) {
		return 0;
	}
}
