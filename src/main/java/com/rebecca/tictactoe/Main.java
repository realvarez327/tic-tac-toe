package com.rebecca.tictactoe;

import java.util.Scanner;

public class Main {
    private static Scanner readNumberScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Player is X, Computer is O, you make the first move.");
        System.out.println("The key for playing is: \n1|2|3\n4|5|6\n7|8|9");
        int boardLength = readNumber("To start, input the length of the board you want in squares.");
        if (boardLength <= 2) {
            boardLength = readNumber("Not a valid length, it must be greater than. Try again.");
        }

        Board gameBoard = new Board(boardLength);
        gameBoard.draw();
        boolean gameWon = false;

        do {
            int[] playerNumbers = playUser();
            if (playerNumbers != null) {
                gameBoard.take(playerNumbers[0], playerNumbers[1], Player.Human);
            }
            // is it over
            gameWon = gameBoard.isGameOver();
            if (!gameWon) {
                // computer playing time but not yet
                playerNumbers = gameBoard.playComputer();
                if (playerNumbers != null) {
                    gameBoard.take(playerNumbers[0], playerNumbers[1], Player.Computer);
                    gameWon = gameBoard.isGameOver();
                }
            }
        } while (!gameWon);
        readNumberScanner.close();
    }

    public static int readNumber(String prompt) {
        System.out.println(prompt);

        int number = readNumberScanner.nextInt();

        return number;
    }

    public static int[] playUser() {

        int doOrDie = readNumber("Make a move [1] or quit [0]?");
        if (doOrDie == 1) {
            int a = readNumber("What is the row of the square you want to take?");
            int b = readNumber("What is the column of the square you want to take?");
            int[] playerInts = { a - 1, b - 1 };
            return playerInts;

        } else if (doOrDie == 0) {
            System.out.println("Sorry to see you go!");
            System.out.println("You played for " + System.currentTimeMillis() + " milliseconds.");
            return null;
        } else {
            System.out.println("Invalid input. Try again");
            playUser();
            return null;
        }

    }

}
