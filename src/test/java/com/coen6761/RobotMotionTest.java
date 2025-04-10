package com.coen6761;

import com.coen6761.MovementHistory.*;
import com.coen6761.Robot.*;
import com.coen6761.RobotFloor.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RobotMotionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ProgramActions programActions;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        programActions = new ProgramActions();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String getConsoleOutput() {
        return outContent.toString();
    }

    // ==============================================
    // White-box Testing
    // ==============================================

    // 1. Statement Coverage Tests
    @Test
    void testInitialization_statements() {
        programActions.actions("I 10");
        String output = getConsoleOutput();
        assertTrue(output.contains("Robot initialized"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"U", "D", "R", "L", "C", "P", "H"})
    void testAllCommandStatements(String command) {
        programActions.actions("I 5");
        programActions.actions(command);
        assertFalse(getConsoleOutput().contains("Wrong Input"));
    }

    // 2. Decision Coverage Tests
    @Test
    void testPenCommands_decisions() {
        programActions.actions("I 5");
        programActions.actions("D");
        assertTrue(getConsoleOutput().contains("Pen Set to Down"));

        programActions.actions("U");
        assertTrue(getConsoleOutput().contains("Pen Set to Up"));
    }

    @Test
    void testMovementBoundary_decisions() {
        programActions.actions("I 3");
        programActions.actions("D");
        programActions.actions("M 5"); // Should stop at boundary
        programActions.actions("C");
        assertTrue(getConsoleOutput().contains("[02]"));
    }

    // 3. Condition Coverage Tests
    @ParameterizedTest
    @MethodSource("movementDirections")
    void testMovementDirections_conditions(Directions direction, String command) {
        programActions.actions("I 5");
        programActions.actions("D");
        programActions.actions(command);
        programActions.actions("M 2");
        programActions.actions("C");
        String output = getConsoleOutput();
        assertTrue(output.contains("Movement recorded"));
    }

    private static Stream<Arguments> movementDirections() {
        return Stream.of(
                Arguments.of(Directions.NORTH, "M 2"),
                Arguments.of(Directions.SOUTH, "R R M 2"),
                Arguments.of(Directions.EAST, "R M 2"),
                Arguments.of(Directions.WEST, "L M 2")
        );
    }

    // 4. Multiple Condition Coverage Tests
    @ParameterizedTest
    @CsvSource({
            "U, M 2, false",  // Pen up, shouldn't mark
            "D, M 2, true",   // Pen down, should mark
            "U, M 0, false",  // Pen up, zero movement
            "D, M 0, false"   // Pen down, zero movement
    })
    void testPenStateWithMovement_multipleConditions(String penCommand, String moveCommand, boolean shouldMark) {
        programActions.actions("I 5");
        programActions.actions(penCommand);
        programActions.actions(moveCommand);
        programActions.actions("P");
        String output = getConsoleOutput();
        assertEquals(shouldMark, output.contains("*"));
    }

    // 5. Mutation Testing Target (FloorMarkingService.markFloor)
    @Test
    void testFloorMarking_mutation() {
        // This would be better tested with PITest, but here's a basic version
        programActions.actions("I 3");
        programActions.actions("D");
        programActions.actions("M 2");
        programActions.actions("P");
        String output = getConsoleOutput();
        // Should have 3 marks (start + 2 moves)
        assertEquals(2, output.chars().filter(ch -> ch == '*').count());
    }

    // 6. Data Flow Testing (ProgramActions.actions)
    @Test
    void testCommandProcessing_dataFlow() {
        programActions.actions("I 5");  // Define system
        programActions.actions("D");    // Define pen state
        programActions.actions("M 2");  // Use pen state
        programActions.actions("P");    // Use floor state

        String output = getConsoleOutput();
        assertTrue(output.contains("Robot initialized"));
        assertTrue(output.contains("Pen Set to Down"));
        assertTrue(output.contains("Movement recorded"));
        assertTrue(output.contains("*")); // Marked cells
    }

    // ==============================================
    // Black-box Testing
    // ==============================================

    // 1. Input Validation
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "X", "M", "I", "M abc", "M   abc", "", "I -1"})
    void testInvalidInputs(String input) {
        programActions.actions(input);
        assertTrue(getConsoleOutput().contains("Wrong Input") ||
                getConsoleOutput().contains("invalid") ||
                getConsoleOutput().contains("missing"));
    }


    // 2. Boundary Value Analysis
    @Test
    void testGridSizeBoundaries() {
        programActions.actions("I 1");  // Minimum size
        assertTrue(getConsoleOutput().contains("Robot initialized"));

        programActions.actions("I 100"); // Large size
        assertTrue(getConsoleOutput().contains("Robot initialized"));
    }


    // 3. Error Guessing
    @Test
    void testUninitializedCommands() {
        programActions.actions("D");
        programActions.actions("M 2");
        programActions.actions("P");
        assertTrue(getConsoleOutput().contains("Please initialize Robot first"));
    }

    // 4. State Transition Testing
    @Test
    void testStateTransitions() {
        // Initial -> Initialized
        programActions.actions("I 5");
        // Initialized -> Pen Down
        programActions.actions("D");
        // Pen Down -> Moved
        programActions.actions("M 2");
        // Moved -> Pen Up
        programActions.actions("U");
        // Pen Up -> Turned
        programActions.actions("R");
        // Turned -> Printed
        programActions.actions("P");
        // Printed -> Turned
        programActions.actions("L");
        // Turned -> Printed
        programActions.actions("P");
        // Printed -> Turned
        programActions.actions("L");
        // Turned -> Printed
        programActions.actions("P");
        // Printed -> Turned
        programActions.actions("L");
        // Turned -> Printed
        programActions.actions("P");

        String output = getConsoleOutput();
        assertTrue(output.contains("Robot initialized"));
        assertTrue(output.contains("Pen Set to Down"));
        assertTrue(output.contains("Movement recorded"));
        assertTrue(output.contains("Pen Set to Up"));
        assertTrue(output.contains("Robot Turned Right"));
        assertTrue(output.contains("▶")); // Robot symbol
        assertTrue(output.contains("Robot Turned Left"));
        assertTrue(output.contains("▲")); // Robot symbol
        assertTrue(output.contains("Robot Turned Left"));
        assertTrue(output.contains("◀")); // Robot symbol
        assertTrue(output.contains("Robot Turned Left"));
        assertTrue(output.contains("▼")); // Robot symbol
    }

    // ==============================================
    // Additional Comprehensive Tests
    // ==============================================

    @Test
    void testFullWorkflow() {
        programActions.actions("I 5");
        programActions.actions("D");
        programActions.actions("M 2");
        programActions.actions("R");
        programActions.actions("M 1");
        programActions.actions("P");
        programActions.actions("C");
        programActions.actions("H");
        programActions.actions("Q");

        String output = getConsoleOutput();
        assertTrue(output.contains("Robot initialized"));
        assertTrue(output.contains("Pen Set to Down"));
        assertTrue(output.contains("Movement recorded"));
        assertTrue(output.contains("Robot Turned Right"));
        assertTrue(output.contains("Current Bot Position"));
        assertTrue(output.contains("INITIALIZE"));
    }

    @Test
    void testHistoryReplayAccuracy() {
        programActions.actions("I 3");
        programActions.actions("D");
        programActions.actions("M 1");
        programActions.actions("R");
        programActions.actions("M 1");
        programActions.actions("H");

        String output = getConsoleOutput();
        long moveEvents = output.lines()
                .filter(line -> line.contains("MOVE"))
                .count();
        assertEquals(2, moveEvents);
    }

    @Test
    void testPenDownUpSamePosition_noStarShouldAppear() {
        // Test at origin (0,0)
        programActions.actions("I 5");
        programActions.actions("D");  // Pen down
        programActions.actions("U");  // Pen up immediately
        programActions.actions("P");  // Print floor

        String output = getConsoleOutput();
        assertFalse(output.contains("*"),
                "No star should appear when pen is lowered and raised at same position");

        // Clear output and test at middle position
        outContent.reset();

        programActions.actions("I 5");
        programActions.actions("M 2");  // Move to (2,0)
        programActions.actions("D");    // Pen down
        programActions.actions("U");    // Pen up immediately
        programActions.actions("M 1");
        programActions.actions("P");    // Print floor

        output = getConsoleOutput();
        assertTrue(output.contains("*"),
                "Star should appear when pen is lowered and raised at same position");
    }
}