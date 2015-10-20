package main.calculations;

import main.model.DeltaType.Delta;
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
		return (int) Math.round((numRedDeltas(grid) * q0) / (numRadiatingDeltas(grid) * averageRadiatingLength(grid) * kr));
	}

	private static double averageRadiatingLength(Grid grid) {
		return grid.getDeltas().stream().mapToDouble((d) -> d.delta.amountRadiating).sum() / numRadiatingDeltas(grid);
	}

	private static int numRedDeltas(Grid grid) {
		return (int) grid.getDeltas().stream().filter((d) -> d.delta.type.isRed()).count();
	}

	private static int numRadiatingDeltas(Grid grid) {
		return (int) grid.getDeltas().stream().filter((d) -> d.delta.amountRadiating != 0).count();
	}
}
