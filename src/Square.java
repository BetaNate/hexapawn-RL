import javafx.scene.layout.StackPane;

public class Square extends StackPane {
   int x, y;
   boolean hasPiece;
   char pieceType;
   String name;

   public Square(int x, int y) {
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

   public void setPiece(char piece) {
      this.pieceType = piece;
   }

   public char getPiece() {
      return this.pieceType;
   }
}
