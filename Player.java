// Player.java
// CS211 - Football Team Balancer
// Damian Ciumacenco — Maynooth University

public class Player {
    private String name;
    private int rating;
    private int goals;
    private int appearances;
    private int motm;       // Man of the Match awards
    private int lateTimes;  // Times arrived late

    public Player(String name, int rating) {
        this.name       = name;
        this.rating     = rating;
        this.goals      = 0;
        this.appearances = 0;
        this.motm       = 0;
        this.lateTimes  = 0;
    }

    // Constructor for loading from file
    public Player(String name, int rating, int goals, int appearances, int motm, int lateTimes) {
        this.name        = name;
        this.rating      = rating;
        this.goals       = goals;
        this.appearances = appearances;
        this.motm        = motm;
        this.lateTimes   = lateTimes;
    }

    // ── Getters ──
    public String getName()       { return name; }
    public int    getRating()     { return rating; }
    public int    getGoals()      { return goals; }
    public int    getAppearances(){ return appearances; }
    public int    getMotm()       { return motm; }
    public int    getLateTimes()  { return lateTimes; }

    // ── Setters ──
    public void setRating(int rating)  { this.rating = rating; }
    public void addGoals(int g)        { this.goals += g; }
    public void addAppearance()        { this.appearances++; }
    public void addMotm()              { this.motm++; }
    public void addLate()              { this.lateTimes++; }

    // ── Display ──
    public String getStatsLine() {
        return String.format("  %-18s | Rating: %2d | Goals: %2d | Apps: %2d | MOTM: %2d | Late: %2d",
            name, rating, goals, appearances, motm, lateTimes);
    }

    // ── Save to file format ──
    public String toFileString() {
        return name + "," + rating + "," + goals + "," + appearances + "," + motm + "," + lateTimes;
    }

    @Override
    public String toString() {
        return name + " (" + rating + ")";
    }
}
