// Team.java

import java.util.ArrayList;

public class Team {
    private String name;
    private String colour;
    private ArrayList<Player> players;

    public Team(String name, String colour) {
        this.name    = name;
        this.colour  = colour;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player p) { players.add(p); }

    public int getTotalRating() {
        int total = 0;
        for (Player p : players) total += p.getRating();
        return total;
    }

    public ArrayList<Player> getPlayers() { return players; }
    public String getName()   { return name; }
    public String getColour() { return colour; }

    public void display() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf( "║  %-20s [%s]         ║%n", name, colour);
        System.out.printf( "║  Total Rating: %-4d                   ║%n", getTotalRating());
        System.out.println("╠══════════════════════════════════════╣");
        for (Player p : players) {
            System.out.printf("║  %-20s  Rating: %2d      ║%n", p.getName(), p.getRating());
        }
        System.out.println("╚══════════════════════════════════════╝");
    }
}
