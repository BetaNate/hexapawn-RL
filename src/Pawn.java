import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Pawn {

    private String color;
    private int posX, posY;
    private final int radius;
    ArrayList<String> possibleMoves;

    public Pawn(String color, int posX, int posY, int radius) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        addEventHandler();
        setShape(this.radius);
        
    }

    public String getColor() {
        return this.color;
    }

    public Circle setShape(int radius) {
        Circle bead = new Circle(radius, Color.valueOf(this.getColor()));
        return bead;
    }

    public void addEventHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAllMoves();
            }
        });
    }

    public void getAllMoves() {
        int x = this.posX;
        int y = this.posY;
        ArrayList<Bead> moves = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();

        if(color.equals("black")){
            moves.add(new Bead(Color.YELLOW,"Square" + (x+1) + (y+1)));
            moves.add(new Bead(Color.GREEN,"Square" + (x) + (y+1)));
            moves.add(new Bead(Color.RED,"Square" + (x-1) + (y+1)));
        }
        else{
            moves.add(new Bead(Color.GREEN,"Square" + (x) + (y-1)));
            moves.add(new Bead(Color.RED,"Square" + (x+1) + (y-1)));
            moves.add(new Bead(Color.YELLOW,"Square" + (x-1) + (y-1)));
        }

        for(String move : moves){
            if(getSquare(move.getMoveType()) != null){
                if(getSquare(move.getMoveType()).hasPiece && getColor().equals(Game.currentPlayer)) {
                     continue;
                }

                if(!getSquare(move.getMoveType()).hasPiece && getSquare(move.getMoveType()).x != posX) {
                    continue;
                }
                possibleMoves.add(move);
            }
        }
    }

    public void showAllMoves(boolean possible) {
        if(possible) {
            for(String move : possibleMoves) {
                 Pawn pawn = getPawn(move.getMoveType());
                 if(pawn == null) {
                    continue;
                }
                else {
                    MatchboxSquare square = getSquare(move.getMoveType());
                    Line beadLine = move.renderBead(pawn, square);
                }
            }
        }
    }

    public MatchboxSquare getSquare(String name) {
        for(MatchboxSquare square : Game.matchbox.squares) {
            if(square.name.equals(name)) {
                return square;
            }
        }
        return null;
    }

    public Pawn getPawn(String name) {
        for(MatchboxSquare square : Game.matchbox.squares){
            if(square.getChildren().size() == 0){
                 continue;
            }

            if(square.name.equals(name)) {
                return (Pawn) square.getChildren().get(0);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
