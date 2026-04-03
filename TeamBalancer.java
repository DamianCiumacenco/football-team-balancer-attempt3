// TeamBalancer.java

import java.util.*;

public class TeamBalancer {

    // Bright colours only — no dark shades
    private static final String[] COLOURS = {
        "Red", "Blue", "Yellow", "Green", "Orange",
        "Purple", "Pink", "Cyan", "Lime", "White"
    };

    public void generateTeams(Scanner sc, PlayerManager pm) {
        ArrayList<Player> all = pm.getPlayers();

        if (all.size() < 2) {
            System.out.println("⚠  Need at least 2 players to generate teams.");
            return;
        }

        // Select players for this game
        ArrayList<Player> selected = selectPlayers(sc, all);
        if (selected == null || selected.size() < 2) return;

        int total    = selected.size();
        int teamSize = total / 2;
        int leftover = total % 2;

        System.out.printf("%nSplitting %d players into two teams (%d vs %d)%n",
            total, teamSize + (leftover > 0 ? 1 : 0), teamSize);

        // Balance teams using a greedy algorithm
        ArrayList<Player> teamAPlayers = new ArrayList<>();
        ArrayList<Player> teamBPlayers = new ArrayList<>();
        balanceTeams(selected, teamAPlayers, teamBPlayers);

        // Assign colours — random which team gets black
        String[] colours  = assignColours();
        Team teamA = new Team("TEAM A", colours[0]);
        Team teamB = new Team("TEAM B", colours[1]);
        for (Player p : teamAPlayers) teamA.addPlayer(p);
        for (Player p : teamBPlayers) teamB.addPlayer(p);

        // Display
        System.out.println("\n════════════════════════════════════════");
        System.out.println("           ⚽  TEAMS GENERATED  ⚽");
        System.out.println("════════════════════════════════════════");
        teamA.display();
        teamB.display();

        int diff = Math.abs(teamA.getTotalRating() - teamB.getTotalRating());
        System.out.println("\n── Balance ─────────────────────────────");
        System.out.printf("  Team A total: %d  |  Team B total: %d  |  Difference: %d%n",
            teamA.getTotalRating(), teamB.getTotalRating(), diff);
        if (diff <= 2) System.out.println("  ✅ Teams are well balanced!");
        else           System.out.println("  ⚠  Teams may be slightly unbalanced.");

        // Ask to update stats after game
        System.out.print("\nUpdate stats after the game? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();
        if (ans.equals("y")) pm.updateStats(sc, selected);
    }

    // ── Let user pick who's playing today ──
    private ArrayList<Player> selectPlayers(Scanner sc, ArrayList<Player> all) {
        System.out.println("\n── Select players for today's game ─────");
        System.out.println("  Options:");
        System.out.println("  1. Use ALL players");
        System.out.println("  2. Select players manually");
        System.out.print("Choice: ");
        String choice = sc.nextLine().trim();

        if (choice.equals("1")) return new ArrayList<>(all);

        // Manual selection
        System.out.println("\nEnter player names one by one (Enter blank when done):");
        ArrayList<Player> selected = new ArrayList<>();
        while (true) {
            System.out.print("  Player name (or Enter to finish): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) break;
            boolean found = false;
            for (Player p : all) {
                if (p.getName().equalsIgnoreCase(name)) {
                    selected.add(p);
                    System.out.println("  ✅ Added: " + p.getName() + " (" + p.getRating() + ")");
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("  ⚠  Not found. Try again.");
        }

        if (selected.size() < 2) {
            System.out.println("⚠  Need at least 2 players.");
            return null;
        }
        return selected;
    }

    // ── Greedy balancing algorithm ──
    // Sort players by rating descending, alternate assigning to keep totals close
    private void balanceTeams(ArrayList<Player> players,
                               ArrayList<Player> teamA,
                               ArrayList<Player> teamB) {
        // Sort descending by rating
        ArrayList<Player> sorted = new ArrayList<>(players);
        sorted.sort((a, b) -> b.getRating() - a.getRating());

        int sumA = 0, sumB = 0;
        for (Player p : sorted) {
            if (sumA <= sumB) {
                teamA.add(p);
                sumA += p.getRating();
            } else {
                teamB.add(p);
                sumB += p.getRating();
            }
        }
    }

    // ── Randomly assign black and a bright colour ──
    private String[] assignColours() {
        Random rand   = new Random();
        String bright = COLOURS[rand.nextInt(COLOURS.length)];
        // Randomly decide which team gets black
        if (rand.nextBoolean()) return new String[]{"Black", bright};
        else                    return new String[]{bright, "Black"};
    }
}
