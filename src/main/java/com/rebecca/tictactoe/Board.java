package com.rebecca.tictactoe;

public class Board {
    static int boardSize;
    int[][] boardState;
    String[][] charactersForBoardState;

    public Board(int boardSizeGiven) {
        boardSize = boardSizeGiven;
        boardState = new int[boardSize][boardSize];
    }

    // row then column, 1 is computer, 2 is player
    public void take(int rowNumber, int columnNumber, Player taker) {
        if (taker == Player.Computer) {
            boardState[rowNumber][columnNumber] = 1;
        } else {
            boardState[rowNumber][columnNumber] = 2;
        }
        draw();
    }

    public void draw() {
        for (int i = 0; i < boardSize; i++) {
            String rowToPrint = "";
            for (int j = 0; j < boardSize; j++) {
                int v = boardState[i][j];
                char cellChar = v == 1 ? 'O' : v == 2 ? 'X' : ' ';
                if (j + 1 == boardSize) {
                    rowToPrint += cellChar;
                } else {
                    rowToPrint += cellChar + "|";
                }
            }
            System.out.println(rowToPrint);
            if (i + 1 != boardSize) {
                System.out.println("-".repeat(boardSize + (boardSize - 1)));
            }
        }
        System.out.println("");
    }

    public int[] findHorizontalStrike(int player) {
        for (int r = 0; r < boardSize; r++) {
            int emptyCount = 0;
            int playerCount = 0;
            for (int c = 0; c < boardSize; c++) {
                if (boardState[r][c] == 0) {
                    emptyCount++;
                } else if (boardState[r][c] == player) {
                    playerCount++;
                }
            }
            if ((emptyCount != 0) && ((playerCount == boardSize - 1))) {
                for (int d = 0; d < boardSize; d++) {
                    if (boardState[r][d] == 0) {
                        int[] bestPlay = { r, d };
                        return bestPlay;
                    }
                }
            }
        }
        return null;
    }

    public int[] findDiagonalLeftStrike(int player) {
        int streak = 0;
        int[] emptySpot = new int[2];
        for (int i = 0; i < boardSize; i++) {
            if (boardState[i][i] == player) {
                streak++;
            } else {
                emptySpot[0] = emptySpot[1] = i;
            }
        }
        if (streak == boardSize - 1) {
            return emptySpot;
        }
        return null;
    }

    public int[] findDiagonalRightStrike(int player) {
        int streak = 0;
        int[] emptySpot = new int[2];
        for (int i = 0; i < boardSize; i++) {
            if (boardState[i][boardSize - i - 1] == player) {
                streak++;
            } else {
                emptySpot[0] = i;
                emptySpot[1] = boardSize - i - 1;
            }
        }
        if (streak == boardSize - 1) {
            return emptySpot;
        }
        return null;
    }

    public int[] findVerticalStrike(int player) {
        int playerCount;
        int emptyCount;
        for (int column = 0; column < boardSize; column++) {
            playerCount = 0;
            emptyCount = 0;
            for (int row = 0; row < boardSize; row++) {
                if (boardState[row][column] == player) {
                    playerCount++;
                } else if (boardState[row][column] == 0) {
                    emptyCount++;
                }
            }
            if ((emptyCount != 0) && (playerCount == boardSize - 1)) {
                for (int d = 0; d < boardSize; d++) {
                    if (boardState[d][column] == 0) {
                        int[] bestPlay = { d, column };
                        return bestPlay;
                    }
                }
            }
        }
        return null;
    }

    private boolean isVerticalWinBy(int player) {
        for (int column = 0; column < boardSize; column++) {
            int streakVert = 0;
            for (int currentpoint = 0; currentpoint < boardSize; currentpoint++) {
                if (boardState[currentpoint][column] == player) {
                    streakVert++;
                }
            }
            if (streakVert == boardSize) {
                return true;
            }
        }
        return false;
    }

    public boolean isHorizontalWinBy(int player) {
        for (int row = 0; row < boardSize; row++) {
            int streakHori = 0;
            for (int currentSpot = 0; currentSpot < boardSize; currentSpot++) {
                if (boardState[row][currentSpot] == player) {
                    streakHori++;
                }
            }
            if (streakHori == boardSize) {
                return true;
            }
        }
        return false;
    }

    public int didSomebodyWin() {

        // check for horizontal wins on all rows, if there is a 'streak' of boardSize on
        // the row
        if (isHorizontalWinBy(1)) {
            return 1;
        }
        if (isHorizontalWinBy(2)) {
            return 2;
        }
        if (isVerticalWinBy(1)) {
            return 1;
        }
        if (isVerticalWinBy(2)) {
            return 2;
        }

        // find diagonal-left
        int streakDiagL = 0;
        int streakOwnerL = 1;
        for (int i = 0; i < boardSize; i++) {
            if (boardState[i][i] == streakOwnerL) {
                streakDiagL++;
            }
        }
        if (streakDiagL == boardSize) {
            return streakOwnerL;
        } else if (streakDiagL == 0) {
            streakOwnerL = 2;
            for (int i = 0; i < boardSize; i++) {
                if (boardState[i][i] == streakOwnerL) {
                    streakDiagL++;
                }
            }
            if (streakDiagL == boardSize) {
                return streakOwnerL;
            }
        }
        // find diagonal-right
        int streakDiagR = 0;
        int streakOwnerR = 1;
        for (int i = 0; i < boardSize; i++) {
            if (boardState[i][boardSize - i - 1] == streakOwnerR) {
                streakDiagR++;
            }
        }
        if (streakDiagR == boardSize) {
            return streakOwnerR;
        } else if (streakDiagR > 0) {
            return 0;
            // computer has at least one token on this path, player cant have a win this
            // way.
        } else {
            streakOwnerR = 2;
            for (int i = 0; i < boardSize; i++) {
                if (boardState[i][boardSize - i - 1] == streakOwnerR) {
                    streakDiagR++;
                }
            }
            if (streakDiagR == boardSize) {
                return streakOwnerR;
            }
        }
        return 0;
    }

    public boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (boardState[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        int playerWon = didSomebodyWin();
        if (playerWon == 1) {
            System.out.println("Computer won, game over");
            return true;
        } else if (playerWon == 2) {
            System.out.println("Player won, game over");
            return true;
        } else if (isBoardFull()) {
            System.out.println("Its a draw, game over");
            return true;
        }
        return false;
    }

    public int[] playComputer() {
        int[] horizontalPlaysComp = findHorizontalStrike(1);
        if (horizontalPlaysComp != null) {
            return horizontalPlaysComp;
        }
        int[] verticalPlaysComp = findVerticalStrike(1);
        if (verticalPlaysComp != null) {
            return verticalPlaysComp;
        }
        int[] diagonalRComp = findDiagonalRightStrike(1);
        if (diagonalRComp != null) {
            return diagonalRComp;
        }
        int[] diagonalLComp = findDiagonalLeftStrike(1);
        if (diagonalLComp != null) {
            return diagonalLComp;
        }
        int[] horizontalPlaysPlay = findHorizontalStrike(2);
        if (horizontalPlaysPlay != null) {
            return horizontalPlaysPlay;
        }
        int[] verticalPlaysPlay = findVerticalStrike(2);
        if (verticalPlaysPlay != null) {
            return verticalPlaysPlay;
        }
        int[] diagonalRPlay = findDiagonalRightStrike(2);
        if (diagonalRPlay != null) {
            return diagonalRPlay;
        }
        int[] diagonalLPlay = findDiagonalLeftStrike(2);
        if (diagonalLPlay != null) {
            return diagonalLPlay;
        }

        for (int i = 0; i < boardState[0].length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (boardState[i][j] == 0) {
                    int[] results = { i, j };
                    return results;
                }

            }
        }

        // if we get to this point, the board is full, we should return null
        return null;
    }
}
