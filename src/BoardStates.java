import java.util.ArrayList;

public class BoardStates {

    private ArrayList<char[][]> possibleStates; 

    public BoardStates() {
      possibleStates = new ArrayList<char[][]>();
      setStates();
    } 

    public static char[][] init() {
        return new char[][] { 
            {'B','B','B'},
            {'E','E','E'},
            {'W','W','W'}};
    }
    
    private void setStates() {
      //Turn 0
         possibleStates.add(new char[][] {
            {'B','B','B'},
            {'E','E','E'},
            {'W','W','W'}}
         );
    //Turn 2
         possibleStates.add(new char[][] {
            {'B','B','B'},
            {'W','E','E'},
            {'E','W','W'}}
         );
         possibleStates.add(new char[][] {
            {'B','B','B'},
            {'E','E','W'},
            {'W','W','E'}}
         );
         possibleStates.add(new char[][] {
            {'B','B','B'},
            {'E','W','E'},
            {'W','E','W'}}
         );

         //Turn 4
         possibleStates.add(new char[][] {
            {'B','E','B'},
            {'B','W','E'},
            {'E','E','W'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','B'},
            {'W','B','E'},
            {'E','E','W'}}
         );
         possibleStates.add(new char[][] {
            {'B','E','B'},
            {'W','W','E'},
            {'E','W','E'}}
         );

         possibleStates.add(new char[][] {
            {'B','B','E'},
            {'W','E','W'},
            {'E','E','W'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','B'},
            {'E','B','W'},
            {'W','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','B'},
            {'B','W','W'},
            {'W','E','E'}}
         );

        possibleStates.add(new char[][] {
            {'B','E','B'},
            {'B','E','W'},
            {'E','W','E'}}
         );
         possibleStates.add(new char[][] {
            {'B','B','E'},
            {'W','W','B'},
            {'E','E','W'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','B'},
            {'E','W','E'},
            {'E','E','W'}}
         );

        possibleStates.add(new char[][] {
            {'E','B','B'},
            {'E','W','E'},
            {'W','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'B','E','B'},
            {'W','E','E'},
            {'E','E','W'}}
         );

         //Turn 6
         possibleStates.add(new char[][] {
            {'E','E','B'},
            {'B','B','W'},
            {'E','E','E'}}
         );
          possibleStates.add(new char[][] {
            {'B','E','E'},
            {'W','W','W'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','E'},
            {'B','W','W'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','E'},
            {'W','W','B'},
            {'E','E','E'}}
         );
          possibleStates.add(new char[][] {
            {'B','E','E'},
            {'B','B','W'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','E','B'},
            {'W','B','B'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','E','B'},
            {'B','W','E'},
            {'E','E','E'}}
         );
          possibleStates.add(new char[][] {
            {'E','B','E'},
            {'W','B','E'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','B','E'},
            {'E','B','W'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'B','E','E'},
            {'B','W','E'},
            {'E','E','E'}}
         );
         possibleStates.add(new char[][] {
            {'E','E','B'},
            {'E','W','B'},
            {'E','E','E'}}
         );
    }
    public ArrayList<char[][]> getStates() {
         return possibleStates;
    }
}
