package fr.iut.jeudego.commandeline;

import fr.iut.jeudego.commandeline.Quit;

public class CommandLine {
    public static void execCommande (String data) {
        switch (data){
            case "quit":
                Quit.exit(0);
            case "boardsize":

            default:
                return;
        }
    }
}