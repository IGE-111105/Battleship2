package battleship;

public class ScoreboardEntry {

    private String timestamp;
    private int moves;
    private int shots;
    private int hits;
    private int sunkShips;
    private String result;

    public ScoreboardEntry() {
    }

    public ScoreboardEntry(String timestamp, int moves, int shots, int hits, int sunkShips, String result) {
        this.timestamp = timestamp;
        this.moves = moves;
        this.shots = shots;
        this.hits = hits;
        this.sunkShips = sunkShips;
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getMoves() {
        return moves;
    }

    public int getShots() {
        return shots;
    }

    public int getHits() {
        return hits;
    }

    public int getSunkShips() {
        return sunkShips;
    }

    public String getResult() {
        return result;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setSunkShips(int sunkShips) {
        this.sunkShips = sunkShips;
    }

    public void setResult(String result) {
        this.result = result;
    }
}