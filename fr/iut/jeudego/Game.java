package fr.iut.jeudego;

import fr.iut.jeudego.gameComponent.Board;

import java.util.Scanner;
public class Game {
    private static boolean running;
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
        System.out.println("oui");
        if (size>1 && size<19) {
            board = new Board(size);
        } else System.out.println("Illegal Size");;
    }

    private void showboard() {
        StringBuilder map = new StringBuilder();
        char c = 65;
        map.append("O - white stones").append(System.lineSeparator());
        map.append("X - black stones").append(System.lineSeparator());
        map.append(System.lineSeparator()).append("   ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append((char) (c + i)).append(" ");
        }
        map.append(System.lineSeparator());
        for (int i = 0; i < board.getSize(); i++) {
            if (i > 8) map.append(i + 1).append(" ");
            else map.append(i + 1).append("  ");
            for (int j = 0; j < board.getSize(); j++) {
                switch (board.getPoint(i,j)) {
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
            map.append(i + 1).append(System.lineSeparator());
        }
        map.append("   ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append((char) (c + i)).append(" ");
        }
        System.out.println(map);
    }
}
