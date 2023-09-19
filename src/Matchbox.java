import java.util.ArrayList;
import javafx.scene.paint.Color;


public class Matchbox {
    private ArrayList<Bead> beads = new ArrayList<Bead>();
    private Board board;
    private final String side;
    private ArrayList<Square> moves = new ArrayList<Square>();
    private final Color[] moveColors = {
        Color.RED, Color.GREEN,
        Color.YELLOW, Color.BLUE
    };

    public Matchbox(Board board, String side) {
        this.board = board;
        this.side = side;
        addBeads();
    }

    //Methods to add, remove, and access beads in the matchbox
    public void addBead(Bead bead) {
        beads.add(bead);
    }

    public void addBeads() {
         for(Square square : this.board.getSquares()) {
            //Determine which player gets matchboxes
            if(this.side == "white") {
                if(square.getPiece() == 'W') {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    moves = pawn.getMoves(board, square);
                }
            }
            else {
                if(square.getPiece() == 'B') {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    moves = pawn.getMoves(board, square);
                }
            }

            if(!moves.isEmpty()) {
                for(int i = 0; i < moves.size(); i++) {
                    addBead(new Bead(moveColors[i], square, moves.get(i)));
                }
                moves.clear();
            }
        }
        //else{
          //  System.out.println("No moves exist!");
        //}
    }

    //Bead Removal
    public Bead removeBead(Bead bead) {
        if (!beads.isEmpty()) {
            beads.remove(bead);
            return bead; // Remove and return the bad bead
        }
        return null; // Matchbox is empty
    }

    //Check for no beads
    public boolean isEmpty() {
        return beads.isEmpty();
    }

    //Bead visualization
    public void renderBeads() {
        for(Bead bead : beads) {
            System.out.println(bead.getMove().toString());
            bead.renderBead(bead.getMove());
        }
    }

    public char[][] getBoard() {
        return this.board.getPieces();
    }

    public ArrayList<Bead> getBeads() {
        return this.beads;
    }
}