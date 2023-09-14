import java.util.ArrayList;
import java.util.List;

public class Matchbox {
    private List<Bead> beads;

    public Matchbox() {
        beads = new ArrayList<>();
    }

    // Add methods to add, remove, and access beads in the matchbox
    public void addBead(Bead bead) {
        beads.add(bead);
    }

    public Bead removeBead(Bead bead) {
        if (!beads.isEmpty()) {
            beads.remove(bead);
            return bead; // Remove and return the bad bead
        }
        return null; // Matchbox is empty
    }

    public boolean isEmpty() {
        return beads.isEmpty();
    }
}