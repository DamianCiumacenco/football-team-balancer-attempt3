# ⚽ Football Team Balancer
# Attempt 3, added features and improvment to code ( goal tracking and others)

A Java console application for fairly splitting players into balanced football teams, with full player stat tracking.

Built by Damian Ciumacenco — Maynooth University

---

## ✨ Features
- Add, remove and edit players with ratings (1–10)
- Variable team sizes — pick who's playing each session
- Greedy balancing algorithm — teams differ by max 1–2 rating points
- Random kit colours — one team gets black, the other gets a random bright colour
- Stat tracking per player: Goals, Appearances, Man of the Match, Late arrivals
- All player data saved to `players.txt` and loaded automatically on startup

---

## 🚀 How to Run

**Requirements:** Java JDK installed

```bash
# Compile
javac *.java

# Run
java Main
```

---

## 📋 Menu Options
```
1. Generate Teams      — Select players and split into balanced teams
2. Add Player          — Add a new player with a rating
3. Remove Player       — Remove a player from the roster
4. Edit Player Rating  — Update a player's rating
5. View All Players    — List all players and ratings
6. View Player Stats   — Goals, apps, MOTM, late arrivals leaderboard
7. Exit
```

---

## 📁 Project Structure
```
├── Main.java           # Entry point and menu loop
├── Player.java         # Player model — stats and file format
├── Team.java           # Team model — display and total rating
├── PlayerManager.java  # Add/remove/edit players, save/load file, update stats
├── TeamBalancer.java   # Balancing algorithm and colour assignment
└── players.txt         # Auto-generated — stores all player data
```

---

## 🎓 Module Info
**Course:** BSc Software Engineering — Maynooth University
**Language:** Java

---

## 👨‍💻 Author
**Damian Ciumacenco** — [GitHub](https://github.com/DamianCiumacenco) · [LinkedIn](https://www.linkedin.com/in/damian-ciumacenco-71b15a283/)
