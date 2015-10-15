package main.calculations;

import java.awt.Point;
import java.util.stream.IntStream;

import main.model.Grid;

public class StructuralCalculations {
	private static final int GRAVITY_FORCE = 1;

	/**
	 * Returns the center of gravity of a list of deltas. Takes a list of one
	 * dimensional coordinates.
	 * 
	 * @param originDelta
	 *            the delta to use as a reference
	 * @param deltas
	 *            all deltas (relative to the originDelta)
	 * @return the relative position of the center of gravity from the
	 *         originDelta.
	 */
	public static int centerOfGravity(int originDelta, int... deltas) {
		int numDeltas = deltas.length;

		double sumDistances = IntStream.of(deltas).mapToDouble((i) -> GRAVITY_FORCE * (i - originDelta)).sum();
		double result = sumDistances / numDeltas;
		return (int) Math.round(result) - originDelta;
	}
	public static Point centerOfGravity(Grid grid){
		int x = StructuralCalculations.centerOfGravity(0, grid.getDeltas().stream().mapToInt((d) -> d.x).toArray());
		int y = StructuralCalculations.centerOfGravity(0, grid.getDeltas().stream().mapToInt((d) -> d.y).toArray());
		return new Point(x, y);
	}

}
