import java.util.ArrayList;

public class MatchboxRow {
   private ArrayList<Bead> beads;

   public MatchboxRow() {
    beads = new ArrayList<>();  
   }

   public void addBead(Bead bead) {
    beads.add(bead);
   }

   public void removeBead(Bead bead) {
    beads.remove(bead);
   }
}
