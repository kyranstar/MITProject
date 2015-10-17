package main.calculations;

import java.awt.Point;
import java.util.stream.IntStream;

import main.model.Grid;

public class StructuralCalculations {
	private static final int GRAVITY_FORCE = 1;

	/**
	 * Returns the center of gravity of a list of deltas. Takes a list of one dimensional coordinates.
	 *
	 * @param originDelta
	 *            the delta to use as a reference
	 * @param deltas
	 *            all deltas (relative to the originDelta)
	 * @return the relative position of the center of gravity from the originDelta.
	 */
	public static int centerOfGravity(final int originDelta, final int... deltas) {
		final int numDeltas = deltas.length;

		final double sumDistances = IntStream.of(deltas).mapToDouble((i) -> GRAVITY_FORCE * (i - originDelta)).sum();
		final double result = sumDistances / numDeltas;
		return (int) Math.round(result) - originDelta;
	}

	public static Point centerOfGravity(final Grid grid) {
		final int x = StructuralCalculations.centerOfGravity(0, grid.getDeltas().stream().mapToInt((d) -> d.x).toArray());
		final int y = StructuralCalculations.centerOfGravity(0, grid.getDeltas().stream().mapToInt((d) -> d.y).toArray());
		return new Point(x, y);
	}

	public static Point supportLoadA(final Point anchorA, final Point anchorB, final Grid grid) {
		final Point cg = centerOfGravity(grid);
		final Point distA = new Point(Math.abs(anchorA.x - cg.x), Math.abs(anchorA.y - cg.y));
		final Point distB = new Point(Math.abs(anchorB.x - cg.x), Math.abs(anchorB.y - cg.y));

		// RFa = (mass of deltas * distB) / (distB + distA)
		final int RFaX = grid.getDeltas().size() * GRAVITY_FORCE * distB.x / (distB.x + distA.x);
		final int RFaY = grid.getDeltas().size() * GRAVITY_FORCE * distB.y / (distB.y + distA.y);
		return new Point(RFaX, RFaY);
	}

}
