
public class Bead {

    private int beadNumber;
    private String player;

    public Bead(String player, int beadNumber) {
        this.beadNumber = beadNumber;
        this.player = player;
    }

    public int getBead() {
        return beadNumber;
    }
}
