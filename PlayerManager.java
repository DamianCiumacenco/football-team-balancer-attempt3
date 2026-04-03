// PlayerManager.java

import java.util.*;
import java.io.*;

public class PlayerManager {
    private ArrayList<Player> players;
    private static final String FILE = "players.txt";

    public PlayerManager() {
        players = new ArrayList<>();
        loadFromFile();
    }

    // ── Add a new player ──
    public void addPlayer(Scanner sc) {
        System.out.print("Enter player name: ");
        String name = sc.nextLine().trim();

        // Check for duplicate
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("⚠  Player already exists!");
                return;
            }
        }

        int rating = 0;
        while (rating < 1 || rating > 10) {
            System.out.print("Enter rating (1-10): ");
            try { rating = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { rating = 0; }
            if (rating < 1 || rating > 10) System.out.println("⚠  Rating must be between 1 and 10.");
        }

        players.add(new Player(name, rating));
        saveToFile();
        System.out.println("✅ " + name + " added with rating " + rating);
    }

    // ── Remove a player ──
    public void removePlayer(Scanner sc) {
        listPlayers();
        System.out.print("Enter player name to remove: ");
        String name = sc.nextLine().trim();
        Player found = findPlayer(name);
        if (found != null) {
            players.remove(found);
            saveToFile();
            System.out.println("✅ " + name + " removed.");
        } else {
            System.out.println("⚠  Player not found.");
        }
    }

    // ── Edit a player's rating ──
    public void editRating(Scanner sc) {
        listPlayers();
        System.out.print("Enter player name to edit: ");
        String name = sc.nextLine().trim();
        Player found = findPlayer(name);
        if (found == null) { System.out.println("⚠  Player not found."); return; }

        int rating = 0;
        while (rating < 1 || rating > 10) {
            System.out.print("New rating (1-10): ");
            try { rating = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { rating = 0; }
        }
        found.setRating(rating);
        saveToFile();
        System.out.println("✅ " + name + "'s rating updated to " + rating);
    }

    // ── Update stats after a game ──
    public void updateStats(Scanner sc, ArrayList<Player> gamePlayers) {
        System.out.println("\n─── UPDATE STATS ───────────────────────");
        System.out.println("Players in today's game:");
        for (int i = 0; i < gamePlayers.size(); i++) {
            System.out.printf("  %2d. %s%n", i+1, gamePlayers.get(i).getName());
        }

        // Appearances — all players who played
        for (Player p : gamePlayers) p.addAppearance();
        System.out.println("✅ Appearances updated for all " + gamePlayers.size() + " players.");

        // Goals
        System.out.println("\nEnter goals (press Enter with no name when done):");
        while (true) {
            System.out.print("  Goalscorer name (or Enter to skip): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) break;
            Player found = findInList(name, gamePlayers);
            if (found != null) {
                System.out.print("  How many goals? ");
                try {
                    int g = Integer.parseInt(sc.nextLine().trim());
                    found.addGoals(g);
                    System.out.println("  ✅ +" + g + " goals for " + name);
                } catch (NumberFormatException e) {
                    System.out.println("  ⚠  Invalid number, skipping.");
                }
            } else {
                System.out.println("  ⚠  Player not found in today's game.");
            }
        }

        // MOTM
        System.out.print("\nMan of the Match (name): ");
        String motmName = sc.nextLine().trim();
        if (!motmName.isEmpty()) {
            Player motm = findInList(motmName, gamePlayers);
            if (motm != null) { motm.addMotm(); System.out.println("✅ MOTM: " + motmName); }
            else System.out.println("⚠  Player not found.");
        }

        // Late arrivals
        System.out.println("\nWho was late? (press Enter with no name when done):");
        while (true) {
            System.out.print("  Late player name (or Enter to skip): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) break;
            Player found = findInList(name, gamePlayers);
            if (found != null) { found.addLate(); System.out.println("  ✅ Marked " + name + " as late."); }
            else System.out.println("  ⚠  Player not found.");
        }

        saveToFile();
        System.out.println("\n✅ All stats saved!");
    }

    // ── Show all player stats ──
    public void showStats() {
        if (players.isEmpty()) { System.out.println("No players found."); return; }
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        PLAYER STATISTICS                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        // Sort by goals descending
        ArrayList<Player> sorted = new ArrayList<>(players);
        sorted.sort((a, b) -> b.getGoals() - a.getGoals());
        for (Player p : sorted) System.out.println(p.getStatsLine());
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
    }

    // ── List all players ──
    public void listPlayers() {
        if (players.isEmpty()) { System.out.println("No players added yet."); return; }
        System.out.println("\n── All Players ─────────────────────────");
        for (Player p : players) {
            System.out.printf("  %-20s  Rating: %d%n", p.getName(), p.getRating());
        }
        System.out.println("  Total: " + players.size() + " players");
    }

    // ── Save to file ──
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Player p : players) pw.println(p.toFileString());
        } catch (IOException e) {
            System.out.println("⚠  Error saving: " + e.getMessage());
        }
    }

    // ── Load from file ──
    private void loadFromFile() {
        File f = new File(FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    players.add(new Player(
                        parts[0],
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5])
                    ));
                }
            }
            if (!players.isEmpty())
                System.out.println("✅ Loaded " + players.size() + " players from file.");
        } catch (IOException e) {
            System.out.println("⚠  Error loading players: " + e.getMessage());
        }
    }

    // ── Helpers ──
    public Player findPlayer(String name) {
        for (Player p : players) if (p.getName().equalsIgnoreCase(name)) return p;
        return null;
    }
    private Player findInList(String name, ArrayList<Player> list) {
        for (Player p : list) if (p.getName().equalsIgnoreCase(name)) return p;
        return null;
    }
    public ArrayList<Player> getPlayers() { return players; }
}
