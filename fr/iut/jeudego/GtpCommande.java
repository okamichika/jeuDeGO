package fr.iut.jeudego;

import fr.iut.jeudego.exception.InvalidSizeException;
import fr.iut.jeudego.gameComponent.Board;

import java.util.Arrays;

public class GtpCommande {
    private static final String[] commands = {
            "boardsize",
            "clear_board",
            "genmove",
            "list_commands",
            "pass",
            "play",
            "player",
            "quit",
            "showboard"
    };


    public void displayAllCommand() {
        for (String s: commands) {
            System.out.println(s);
        }
    }

    public boolean isValidCommand(String command) {
        return Arrays.asList(commands).contains(command);
    }

    public Board Boardsize(int size) throws InvalidSizeException {
        if (size < 2 || size > 19) {
            throw new InvalidSizeException();
        }
        return new Board(size);
    }
}