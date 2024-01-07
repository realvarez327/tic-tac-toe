package com.rebecca.tictactoe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {
    @Test
    public void testFindDiagonalRightStrike() {
        // Setup the test
        Board testingBoard = new Board(3);
        testingBoard.take(0, 2, Player.Human);
        testingBoard.take(0, 0, Player.Computer);
        testingBoard.take(1, 1, Player.Human);
        // Do the thing
        int[] computerPlay = testingBoard.findDiagonalRightStrike(2);
        // verify the thing is correct
        assertEquals(2, computerPlay[0]);
        assertEquals(0, computerPlay[1]);
    }

    @Test
    public void testStopHumanStrike() {
        // Setup the test
        Board testingBoard = new Board(3);
        testingBoard.take(1, 1, Player.Human);
        testingBoard.take(0, 0, Player.Computer);
        testingBoard.take(0, 1, Player.Human);
        // Do the thing
        int[] computerPlay = testingBoard.playComputer();
        // verify the thing is correct
        assertEquals(2, computerPlay[0]);
        assertEquals(1, computerPlay[1]);
    }
}
