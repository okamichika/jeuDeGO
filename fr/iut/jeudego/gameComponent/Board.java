package fr.iut.jeudego.gameComponent;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Integer>> board;

    public Board(int width) {
        board = new ArrayList<>(width);
        for (ArrayList<Integer> elt: board) {
            elt = new ArrayList<>(width);
        }
    }

    public String getBoardSize() {
        return board.size() + "x" + board.size();
    }
}
