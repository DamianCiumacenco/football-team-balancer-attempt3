// Main.java
// Football Team Balancer
// Damian Ciumacenco — Maynooth University

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc             = new Scanner(System.in);
        PlayerManager manager  = new PlayerManager();
        TeamBalancer  balancer = new TeamBalancer();

        printBanner();

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    balancer.generateTeams(sc, manager);
                    break;
                case "2":
                    manager.addPlayer(sc);
                    break;
                case "3":
                    manager.removePlayer(sc);
                    break;
                case "4":
                    manager.editRating(sc);
                    break;
                case "5":
                    manager.listPlayers();
                    break;
                case "6":
                    manager.showStats();
                    break;
                case "7":
                    System.out.println("\nGoodbye! See you on the pitch ⚽");
                    sc.close();
                    return;
                default:
                    System.out.println("⚠  Invalid option. Choose 1-7.");
            }
        }
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       ⚽  FOOTBALL TEAM BALANCER     ║");
        System.out.println("║          Damian Ciumacenco           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void printMenu() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("  1.  Generate Teams");
        System.out.println("  2.  Add Player");
        System.out.println("  3.  Remove Player");
        System.out.println("  4.  Edit Player Rating");
        System.out.println("  5.  View All Players");
        System.out.println("  6.  View Player Stats");
        System.out.println("  7.  Exit");
        System.out.println("════════════════════════════════════════");
    }
}
