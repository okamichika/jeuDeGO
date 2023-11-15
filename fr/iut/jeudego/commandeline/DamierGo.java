package commandeline;
import java.util.Scanner;

public class DamierGO {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int taille;
        do {
            System.out.print("Entrez la taille du damier (entre 2 et 25) : ");
            taille = scanner.nextInt();

            if (taille < 2 || taille > 25) {
                System.out.println("Taille invalide. La taille doit être comprise entre 2 et 25. Veuillez réessayer.");
            }
        } while (taille < 2 || taille > 25);

        afficherDamier(taille);
    }

    public static void afficherDamier(int taille) {
        System.out.print("  ");
        for (int i = 0; i < taille; i++) {
            System.out.print((char) ('A' + i));
        }
        System.out.println();

        for (int i = 0; i < taille; i++) {
            System.out.print(String.format("%02d", i + 1));
            for (int j = 0; j < taille; j++) {
                System.out.print(".");
            }

            System.out.print(String.format("%02d", i + 1));
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < taille; i++) {
            System.out.print((char) ('A' + i));
        }
        System.out.println();
    }
}
