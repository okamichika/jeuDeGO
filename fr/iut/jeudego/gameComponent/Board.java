package fr.iut.jeudego.gameComponent;

import fr.iut.jeudego.exception.IllegalMoveException;
import fr.iut.jeudego.exception.InvalidCoordException;

import java.util.*;

public class Board {

    public static final String ALPHABET = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    public static final int MIN_SIZE = 2;
    public static final int MAX_SIZE = 19;
    public static final int EMPTY_POSITION = 2;
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    private final int size;
    private final int[][] board;
    private List<Coord> lastKilled;

    public Board() {
        this(MAX_SIZE);
    }

    public Board(int size) {
        this.size = size;
        this.board = new int[this.size][this.size];
        this.lastKilled = new ArrayList<>();
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

    public int getPoint(Coord c) {
        return board[c.x][c.y];
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

    public void putIn(Coord c, String color) throws IllegalMoveException {
        if (this.board[c.x][c.y] != EMPTY_POSITION) {
            throw new IllegalMoveException();
        } else if (lastKilled.contains(c)) {
            throw new IllegalMoveException();
        } else {
            if (Objects.equals(color, "white")) {
                this.board[c.x][c.y] = WHITE;
            } else {
                this.board[c.x][c.y] = BLACK;
            }
        }
        lastKilled = new ArrayList<>();
    }

    private void getGroup(Coord c, int value, List<Coord> seen, List<Coord> group) {
        int x = c.x;
        int y = c.y;
        if (x < 0 || x >= size || y < 0 || y >= size || board[x][y] != value || seen.contains(c)) {
            return;
        }
        seen.add(c);
        group.add(c);

        getGroup(new Coord(x, y + 1), value, seen, group);
        getGroup(new Coord(x + 1, y), value, seen, group);
        getGroup(new Coord(x, y - 1), value, seen, group);
        getGroup(new Coord(x - 1, y), value, seen, group);
    }

    public int getLiberties(Coord c) {
        List<Coord> seen = new ArrayList<>();
        List<Coord> group = new ArrayList<>();
        getGroup(c, getPoint(c), seen, group);
        int nbLiberties = 0;
        for (Coord member : group) {
            if (member.x - 1 >= 0 && board[member.x - 1][member.y] == EMPTY_POSITION) nbLiberties += 1;
            if (member.x + 1 < this.size && board[member.x + 1][member.y] == EMPTY_POSITION) nbLiberties += 1;
            if (member.y - 1 >= 0 && board[member.x][member.y - 1] == EMPTY_POSITION) nbLiberties += 1;
            if (member.y + 1 < this.size && board[member.x][member.y + 1] == EMPTY_POSITION) nbLiberties += 1;
        }
        return nbLiberties;
    }

    public void kill(List<Coord> killable) {
        for (Coord c : killable) {
            board[c.x][c.y] = EMPTY_POSITION;
            lastKilled.add(c);
            System.out.println(lastKilled.size());
        }
    }

    public boolean isValidSize(int size) {
        return size >= MIN_SIZE && size <= MAX_SIZE;
    }
}
