import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Minesweeper game in Java. The game consists of a board where the player must
 * find a treasure while avoiding mines. The game is played on a 4x4 board.
 *
 * The board is represented by a character matrix where:
 * - 'M' represents a mine.
 * - 'T' represents a treasure.
 * - '-' represents an empty cell with no mine or treasure.
 *
 * The player has 3 attempts to find the treasure. They can input coordinates
 * to reveal the content of a cell. The game indicates if there is a mine nearby
 * when the player selects an empty cell.
 *
 * @author Carlos
 */

public class Minesweeper {
    private static final int ROWS = 4;
    private static final int COLUMNS = 4;
    private static final char MINE = 'M';
    private static final char EMPTY = '-';
    private static final char TREASURE = 'T';

    public static void main(String[] args) {
        char[][] board = new char[ROWS][COLUMNS];
        initBoard(board);
        placeMineAndTreasure(board);
        play(board);
    }

    private static void initBoard(char[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void placeMineAndTreasure(char[][] board) {
        Random random = new Random();

        int mineX = random.nextInt(ROWS);
        int mineY = random.nextInt(COLUMNS);
        board[mineX][mineY] = MINE;

        int treasureX, treasureY;
        do {
            treasureX = random.nextInt(ROWS);
            treasureY = random.nextInt(COLUMNS);
        } while (mineX == treasureX && mineY == treasureY);

        board[treasureX][treasureY] = TREASURE;
    }

    private static void play(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 3;

        System.out.println("Welcome to Minesweeper. You have " + attempts + " attempts.");
        
        while (attempts > 0) {
            int x = -1, y = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Enter coordinates (row and column, Min 0 and Max 3):");
                try {
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Only valid numbers are allowed. Please try again.");
                    scanner.next();
                }
            }

            if (x < 0 || x >= ROWS || y < 0 || y >= COLUMNS) {
                System.out.println("Coordinates must be between 0 and 3. Please try again.");
                continue;
            }

            if (board[x][y] == TREASURE) {
                System.out.println("You've won!");
                scanner.close();
                return;
            } else if (board[x][y] == MINE) {
                System.out.println("MINE! You've lost!");
                scanner.close();
                return;
            } else if (isMineNearby(board, x, y)) {
                System.out.println("Warning! There is a mine nearby!");
            } else {
                System.out.println("There's nothing here.");
            }

            attempts--;
            System.out.println("You have " + attempts + " attempts left.");
        }

        System.out.println("Sorry, you've lost. Thanks for playing.");
        scanner.close();
    }

    private static boolean isMineNearby(char[][] board, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < ROWS && y + j >= 0 && y + j < COLUMNS && board[x + i][y + j] == MINE) {
                    return true;
                }
            }
        }
        return false;
    }
}
