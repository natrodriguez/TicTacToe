import java.util.ArrayList;
import java.util.Scanner;


/*
 * A simple program that lets two humans play a game of Tic Tac Toe in a terminal.
 * The players take turns to input their moves.
 * The program reports the outcome of the game.
 *
 * The game board is composed of a grid of 3 rows and 3 columns, for a total of 9 boxes.
 * To select a box, players must enter a number from 1 to 9 corresponding to a box.
 */
public class TicTacToe {
    /*
     * Variables
     */
    private char[] board;
    private int currentPlayer;
    private String[] playersName;
    int movesCounter;
    final static private char[] pieces = {'X', 'O'};
    private int rows[][] = {{0, 2}, {3, 5}, {6, 8}, {0, 6}, {1, 7}, {2, 8}, {0, 8}, {2, 6}};

    static Scanner scanner = new Scanner(System.in);

    private static String instructionsMessage = ", welcome to the Tic Tac Toe Game!\n" +
            "To select a box, you must enter a number from 1 to 9.\n" +
            "Each number corresponds to a box as shown below:";

    private String prompt = "\nPlayer " + currentPlayer + ", please select a box: ";


    /*
     * Game constructor
     */
    public TicTacToe(String player1, String player2) {
        board = new char[9];
        for (int i = 0; i < 9; i++){
            board[i] = '_';
        }

        currentPlayer = 0;
        movesCounter = 0;
        playersName = new String[2];
        playersName[0] = player1;
        playersName[1] = player2;
    }

    /*
     * Display the current game board
     */
    private void displayBoard() {
        System.out.println();
        String line = "  " + board[0] + " | " + board[1] + " | " + board[2];
        System.out.println(line.replaceAll("_"," "));
        System.out.println(" -----------");
        line = "  " + board[3] + " | " + board[4] + " | " + board[5];
        System.out.println(line.replaceAll("_"," "));
        System.out.println(" -----------");
        line = "  " + board[6] + " | " + board[7] + " | " + board[8];
        System.out.println(line.replaceAll("_"," "));
        System.out.println();
    }

    /*
     * Display instructions
     */
    private void displayInstructions() {
        System.out.println();
        System.out.println(playersName[0] + ", " + playersName[1] + instructionsMessage);
        System.out.println();
        String line = "  1 | 2 | 3";
        System.out.println(line);
        System.out.println(" -----------");
        line = "  4 | 5 | 6";
        System.out.println(line);
        System.out.println(" -----------");
        line = "  7 | 8 | 9";
        System.out.println(line);
        System.out.println();
    }

    /*
     * Get the current player
     */
    private String getCurrentPlayer() {
        return playersName[currentPlayer];
    }

    /*
     * Get the list of available boxes (content is '_')
     */
    private ArrayList<Integer> getAvailableBoxes() {
        ArrayList<Integer> availableBoxes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == '_') {
                availableBoxes.add(i);
            }
        }
        return availableBoxes;
    }

    /*
     * Check if a box is available (content is '_')
     */
    private boolean isAvailable(int boxNumber) {
        return (board[boxNumber - 1] == '_') ? true : false;
    }

    /*
     * Check if the user input is valid (number 1-9)
     */
    private boolean isValid(int boxNumber) {
        return ((boxNumber >= 1) && (boxNumber <= 9)) ? true : false;
    }

    /*
     * Make a move
     */
    private void move(int boxNumber) {
        board[boxNumber] = pieces[currentPlayer];
        movesCounter++;
    }

    /*
     * Switch players
     */
    private void switchPlayers() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    /*
     * Get the name of the player playing with a given character
     */
    private String getPlayerName(char playingPiece) {
        String name = "-";
        for (int i = 0; i < 2; i++)
            if (playingPiece == pieces[i])
                name = playersName[i];
        return name;
    }

    /*
     * Check if there is a winner
     */
    private char winner() {

        for (int i = 0; i < 8; i++) {
            int a = rows[i][0];
            int b = rows[i][1];
            if (board[a] == board[b] && board[b] == board[(a + b)/2] && board[a] != '_') {
                return board[a];
            }
        }

        // First, check for a draw
        if (movesCounter == 9)
            return 'd';

        return '_';
    }


    public static void main(String[] args) {
        // Read the players name
        System.out.print("Player 1, please enter your name: ");
        String player1 = scanner.next();

        System.out.print("Player 2, please enter your name: ");
        String player2 = scanner.next();

        // Init game and show instructions
        TicTacToe ttt = new TicTacToe(player1, player2);
        ttt.displayInstructions();

        // Play until there is a winner
        while (ttt.winner() == '_') {

            int boxNumber;
            boolean done = false;

            // Attempt to make a move
            do {
                if(ttt.movesCounter > 0)
                    ttt.displayBoard();

                System.out.print(ttt.getCurrentPlayer());
                System.out.print(", you play " + pieces[ttt.currentPlayer]+ ". Enter a box number for your next move: ");
                boxNumber = scanner.nextInt();

                if (!ttt.isValid(boxNumber))
                    System.out.println("Sorry, that box number is invalid.");
                else {
                    if (!ttt.isAvailable(boxNumber))
                        System.out.println("Sorry, that box is taken.");
                    else {
                        ttt.move(boxNumber -1);
                        done = true;
                    }
                }
            } while (!done);

            // Switch players
            ttt.switchPlayers();
        }

        // Report the outcome of the game
        ttt.displayBoard();
        char winnerPiece = ttt.winner();

        if (winnerPiece == 'd')
            System.out.println("It's a draw!");
        else {
            System.out.println(ttt.getPlayerName(winnerPiece) + " won!");
        }
    }
}

