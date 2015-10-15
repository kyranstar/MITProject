package main;

import org.junit.Assert;
import org.junit.Test;

import main.calculations.StructuralCalculations;

public class StructuralCalculationsTest {

	@Test
	public void centerOfGravity() {
		Assert.assertEquals(3, StructuralCalculations.centerOfGravity(0, 1,2,2,3,4,4,5));

		Assert.assertEquals(4, StructuralCalculations.centerOfGravity(0, 0,-2,-2,-2,0,0,2,2,1,3,4,4,5,6,7,8,8,10,10,10,10,10,8));
		Assert.assertEquals(5, StructuralCalculations.centerOfGravity(-1, -1,-3,-3,-3,-1,-1,1,1,0,2,3,3,4,5,6,7,7,9,9,9,9,9,7));
	}

}
