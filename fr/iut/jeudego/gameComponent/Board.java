package fr.iut.jeudego.gameComponent;

import fr.iut.jeudego.exception.IllegalMoveException;
import fr.iut.jeudego.exception.InvalidCoordException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Board {

    public static final String ALPHABET = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    public static final int MIN_SIZE = 2;
    public static final int MAX_SIZE = 19;
    public static final int EMPTY_POSITION = 2;
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    private final int size;
    private final int[][] board;

    public Board() {
        this(MAX_SIZE);
    }

    public Board(int size) {
        this.size = size;
        this.board = new int[this.size][this.size];
        createEmptyBoard();
    }

    private void createEmptyBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = EMPTY_POSITION;
            }
        }
    }

    private boolean isFull () {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (board[i][j] == EMPTY_POSITION) return false;
            }
        }
        return true;
    }

    public int getSize() {
        return this.size;
    }

    public int getPoint(int i, int j) {
        return board[i][j];
    }

    public Coord getCoord (String s) throws InvalidCoordException {
        if (s.length() < 2 || s.length() > 3) {
            throw new InvalidCoordException();
        }
        try {
            int x = Integer.parseInt(s.substring(1)) - 1;
            int y = ALPHABET.indexOf(s.charAt(0));
            if (x < 0 || x > this.size - 1 || y < 0 || y > this.size - 1) {
                throw new InvalidCoordException();
            }
            return new Coord(x, y);
        } catch (Exception e) {
            throw new InvalidCoordException();
        }
    }

    public void play(Coord playCoord, String color) throws IllegalMoveException {
        if (this.board[playCoord.getX()][playCoord.getY()] != EMPTY_POSITION) {
            throw new IllegalMoveException();
        }
        if (Objects.equals(color, "white")) {
            this.board[playCoord.getX()][playCoord.getY()] = WHITE;
        } else {
            this.board[playCoord.getX()][playCoord.getY()] = BLACK;
        }
    }

    public void getLiberties(Coord coord) {
        int nbLiberties = 0;
        if (coord.getX() > 0 && this.board[coord.getX() - 1][coord.getY()] == EMPTY_POSITION) {
            nbLiberties += 1;
        } else if (coord.getX() > 0 && this.board[coord.getX() - 1][coord.getY()] == this.board[coord.getX()][coord.getY()]) {
            HashSet<Coord> seen = new HashSet<>();
            seen.add(coord);
            getLiberties(new Coord(coord.getX() - 1, coord.getY()), seen);
        }
    }

    public void getLiberties(Coord coord, Set<Coord> seen) {

    }
}
