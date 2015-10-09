package main;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Stream;

public class UserInterface {
	private PrintStream output = System.out;
	private Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		UserInterface userInterface = new UserInterface();
		userInterface.run();
		userInterface.cleanUp();
	}

	private void cleanUp() {
		input.close();
		output.close();
	}

	private void run() {
		output.println("Type 1 or 0 to answer questions. ");
		output.println("Structural (0) or Thermal calculations (1)?");
		switch (input.nextInt()) {
		case 0:
			structuralCalculations();
			break;
		case 1:
			thermalCalculations();
			break;
		}
	}

	private void structuralCalculations() {
		output.println("Calculate center of gravity (0)");
		switch (input.nextInt()) {
		case 0:
			calculateCenterOfGravity();
			break;
		}
	}

	private void calculateCenterOfGravity() {
		output.print("Coordinate of reference point (any delta or origin): ");
		int referenceDelta = input.nextInt();
		input.nextLine();
		output.println("Comma seperated coordinates of remaining deltas (relative to the reference): ");
		int[] otherDeltas = readList();
		
		int centerOfGravity = StructuralCalculations.centerOfGravity(referenceDelta, otherDeltas);
		
		output.println("The center of gravity is: " + centerOfGravity);
	}

	private void thermalCalculations() {
		// TODO Auto-generated method stub

	}
	private int[] readList(){
		String line = input.nextLine().replace(')', ' ').replace('(', ' ');
		return Stream.of(line.split(",")).filter((s) -> !s.isEmpty()).mapToInt((s) -> Integer.parseInt(s)).toArray();
	}
}
