package fr.iut.jeudego;

import java.util.Scanner;
import fr.iut.jeudego.commandeline.CommandLine;
public class main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String data = in.nextLine();
        CommandLine.execCommande(data);
        for (int i = 0; i < 10; i++) {
            System.out.println(i+1);
        }
    }
}
