package fr.iut.jeudego;

import fr.iut.jeudego.gameComponent.Board;

import java.util.Scanner;
public class Game {
    private static boolean running;
    public final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private static Scanner sc = new Scanner(System.in);
    private Board board;
    public Game() {
        board = new Board(19);
        running = true;
    }
    public void run() {

        String input;
        String[] tabInput;
        while (running) {
            input = sc.nextLine();
            tabInput = input.split(" ");
            switch (tabInput[0]) {
                case "quit":
                    quit();
                    break;
                case "boardsize":
                    if (tabInput.length >= 2){
                        int sizeTemp = Integer.parseInt(tabInput[1]);
                        boardsize(sizeTemp);
                    }
                    break;
                case "showboard":
                    showboard();
                default:
                    break;
            }
        }
    }

    private static void quit() {
        running = false;
    }

    private void boardsize(int size) {
        if (size>1 && size<19) {
            board = new Board(size);
        } else System.out.println("Illegal Size");;
    }

    private void showboard() {
        StringBuilder map = new StringBuilder();
        map.append("O - white stones").append(System.lineSeparator());
        map.append("X - black stones").append(System.lineSeparator());
        if (board.getSize() > 9) map.append(System.lineSeparator()).append("   ");
        else map.append(System.lineSeparator()).append("  ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append(alphabet.charAt(i)).append(" ");
        }
        map.append(System.lineSeparator());
        for (int i = board.getSize(); i > 0; i--) {
            if (i > 9 || board.getSize() < 10) map.append(i).append(" ");
            else map.append(i).append("  ");
            for (int j = 0; j < board.getSize(); j++) {
                switch (board.getPoint(i-1,j)) {
                    case Board.EMPTY:
                        map.append(". ");
                        break;
                    case Board.WHITE:
                        map.append("O ");
                        break;
                    case Board.BLACK:
                        map.append("X ");
                        break;
                    default:
                        break;
                }
            }
            map.append(i).append(System.lineSeparator());
        }
        if (board.getSize() > 9) map.append("   ");
        else map.append("  ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append(alphabet.charAt(i)).append(" ");
        }
        System.out.println(map);
    }
}
