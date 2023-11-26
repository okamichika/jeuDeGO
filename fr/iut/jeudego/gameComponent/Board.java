package fr.iut.jeudego.gameComponent;

public class Board {
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    private int[][] board;
    private int size;

    public Board(int size) {
        this.board = new int[size][size];
        this.size = size;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = EMPTY;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] != EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getPoint(int i, int j) {
        return board[i][j];
    }
}
