package fr.iut.jeudego;

import fr.iut.jeudego.exception.IllegalMoveException;
import fr.iut.jeudego.exception.InvalidCoordException;
import fr.iut.jeudego.exception.InvalidSizeException;
import fr.iut.jeudego.gameComponent.Board;
import fr.iut.jeudego.gameComponent.Coord;
import fr.iut.jeudego.player.Human;
import fr.iut.jeudego.player.Robot;

import java.util.*;

public class Game {
    private boolean engineRunning;
    private boolean gameStarted;
    private IPlayer white;
    private IPlayer black;
    private Scanner sc;
    private Board board;
    private IPlayer currentPlayer;
    private final GtpCommande GTP;

    public Game() {
        this.engineRunning = true;
        this.gameStarted = false;
        this.sc = new Scanner(System.in);
        this.board = new Board();
        this.white = new Human('O', "white");
        this.black = new Human('X', "black");
        this.currentPlayer = black;
        this.GTP = new GtpCommande();
    }

    private boolean areBothPlayerRobot() {
        return !white.isHuman() && !black.isHuman();
    }

    private boolean isNum(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void run() {
        while (this.engineRunning) {
            if (areBothPlayerRobot()) {
                this.gameStarted = true;
            }
            if (this.gameStarted && !this.currentPlayer.isHuman()) {
                //todo call the Robot play method to get his play and then display the board
            } else {
                String input;
                input = this.sc.nextLine();
                callGTPCommand(input.trim());
            }
        }
    }

    private void callGTPCommand (String input) {
        StringBuilder output = new StringBuilder();
        String[] tabInput = input.split(" ");
        int tabIndex = 0;
        if (tabInput.length == 1 && isNum(tabInput[tabIndex])) {
            return;
        }
        output.append("=");
        if (isNum(tabInput[tabIndex])) {
            output.append(tabInput[tabIndex]);
            tabIndex++;
        }
        if (!GTP.isValidCommand(tabInput[tabIndex])) {
            output.append(" unknown command");
            output.setCharAt(0, '?');
            System.out.println(output);
        } else {
            switch (tabInput[tabIndex]) {
                case "boardsize":
                    if (tabInput.length - 1 == tabIndex || !isNum(tabInput[tabIndex + 1])) {
                        output.append(" boardsize not an integer");
                        output.setCharAt(0, '?');
                    } else {
                        try {
                            int size = Integer.parseInt(tabInput[tabIndex + 1]);
                            if (board.isValidSize(size)) {
                                this.board = GTP.Boardsize(Integer.parseInt(tabInput[tabIndex + 1]));
                            } else {
                                output.append(" unacceptable size");
                                output.setCharAt(0, '?');
                            }
                        } catch (InvalidSizeException e) {
                            output.append(" unacceptable size");
                            output.setCharAt(0, '?');
                        }
                    }
                    System.out.println(output);
                    break;
                case "list_commands":
                    System.out.println(output);
                    GTP.displayAllCommand();
                    break;
                case "play":
                    this.gameStarted = true;
                    if (tabInput.length - 1 <= tabIndex + 1 || !isValidPlayer(tabInput[tabIndex + 1])) {
                        output.setCharAt(0, '?');
                        output.append(" invalid color or coordinate");
                    } else {
                        if (!Objects.equals(this.currentPlayer.getColor(), tabInput[tabIndex + 1])) {
                            output.setCharAt(0, '?');
                            output.append(" its not the turn of this player");
                        } else {
                            try {
                                Coord playCoord = board.getCoord(tabInput[tabIndex + 2].toUpperCase());
                                this.board.putIn(playCoord, this.currentPlayer.getColor());
                                verifyIfPawnKillable();
                                changePlayerTurn();
                            } catch (InvalidCoordException e) {
                                output.setCharAt(0, '?');
                                output.append(" invalid color or coordinate");
                            } catch (IllegalMoveException e) {
                                output.setCharAt(0, '?');
                                output.append(" illegal move");
                            }
                        }
                    }
                    System.out.println(output);
                    break;
                case "clear_board":
                    this.board = new Board(this.board.getSize());
                    this.gameStarted = false;
                    System.out.println(output);
                    break;
                case "quit":
                    this.engineRunning = false;
                    this.gameStarted = false;
                    System.out.println(output);
                    break;
                case "genmove":
                    // Code à exécuter pour "genmove"
                    break;
                case "showboard":
                    System.out.println(output);
                    showboard();
                    break;
                case "player":
                    if (this.gameStarted) {
                        output.setCharAt(0, '?');
                        output.append(" the game already started");
                    } else if (tabInput.length - 1 <= tabIndex + 1 || !isValidPlayer(tabInput[tabIndex + 1]) || !isValidTypeOfPlayer(tabInput[tabIndex + 1])) {
                        output.setCharAt(0, '?');
                        output.append(" invalid color or type");
                    } else {
                        tabIndex++;
                        if (tabInput[tabIndex + 1].equalsIgnoreCase("robot")) {
                            if (tabInput[tabIndex].equalsIgnoreCase("white")) {
                                white = new Robot(white.getPawnRepresentation(), "white");
                            } else {
                                black = new Robot(black.getPawnRepresentation(), "black");
                            }
                        } else {
                            if (tabInput[tabIndex].equalsIgnoreCase("white")) {
                                white = new Human(white.getPawnRepresentation(), "white");
                            } else {
                                black = new Human(black.getPawnRepresentation(), "black");
                            }
                        }
                    }
                    System.out.println(output);
                    break;
            }
        }
    }

    private void verifyIfPawnKillable() {
        List<Coord> killable = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getLiberties(new Coord(i, j)) == 0) {
                    killable.add(new Coord(i, j));
                }
            }
        }
        board.kill(killable);
        currentPlayer.addScore(killable.size());
    }

    private boolean isValidPlayer (String s) {
        return s.equalsIgnoreCase("white") || s.equalsIgnoreCase("black");
    }

    private boolean isValidTypeOfPlayer (String s) {
        return s.equalsIgnoreCase("robot") || s.equalsIgnoreCase("human");
    }

    public void showboard() {
        StringBuilder map = new StringBuilder();
        if (board.getSize() > 9) map.append(System.lineSeparator()).append("   ");
        else map.append(System.lineSeparator()).append("  ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append(Board.ALPHABET.charAt(i)).append(" ");
        }

        map.append(System.lineSeparator());
        for (int i = board.getSize(); i > 0; i--) {
            if (i > 9 || board.getSize() < 10) map.append(i).append(" ");
            else map.append(i).append("  ");
            for (int j = 0; j < board.getSize(); j++) {
                switch (board.getPoint(new Coord(i - 1, j))) {
                    case Board.EMPTY_POSITION:
                        map.append(". ");
                        break;
                    case Board.WHITE:
                        map.append(white.getPawnRepresentation()).append(" ");
                        break;
                    case Board.BLACK:
                        map.append(black.getPawnRepresentation()).append(" ");
                        break;
                    default:
                        break;
                }
            }
            map.append(i);
            if (board.getSize() < 10) {
                if (i == 2 || i == 1) {
                    map.append("       ").append(i == 2 ? "WHITE (" + white.getPawnRepresentation() + ") " + white.getScore() + " stones"
                            : "BLACK (" + black.getPawnRepresentation() + ") " + black.getScore() +" stones");
                }
            } else if (i == board.getSize() - 8 || i == board.getSize() - 9) {
                if (i > 9 || board.getSize() < 10)
                    map.append("      ").append(i == board.getSize() - 8 ? "WHITE (" + white.getPawnRepresentation() + ") " + white.getScore() + " stones"
                            : "BLACK (" + black.getPawnRepresentation() + ") " + black.getScore() +" stones");
                else
                    map.append("       ").append(i == board.getSize() - 8 ? "WHITE (" + white.getPawnRepresentation() + ") " + white.getScore() + " stones"
                            : "BLACK (" + black.getPawnRepresentation() + ") " + black.getScore() +" stones");
            }
            map.append(System.lineSeparator());
        }

        if (board.getSize() > 9) map.append("   ");
        else map.append("  ");
        for (int i = 0; i < board.getSize(); i++) {
            map.append(Board.ALPHABET.charAt(i)).append(" ");
        }

        System.out.println(map);
    }

    private void changePlayerTurn () {
        if (this.currentPlayer == this.black) {
            this.currentPlayer = this.white;
        } else {
            this.currentPlayer = this.black;
        }
    }
}
