import javafx.scene.layout.Stackpane;

public class MatchboxSquare extends StackPane {
   private ArrayList<Bead> beads;
   private int x, y;
   private boolean hasPiece;
   private String name;

   public MatchboxSquare(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasPiece = false;
   }

   @Override
   public String toString() {
      String status;
      if(this.hasPiece) {
         status = "Full";
      }
      else {
         status = "Empty";
      }

      return "Square" + this.x + this.y + "is" + status;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getXPos() {
      return this.x;
   }

   public int getYPos() {
      return this.y;
   }
}
