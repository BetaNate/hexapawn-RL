public class Matchbox {
    private MatchboxRow[] matchbox;


    public Matchbox() {
        this.matchbox = new MatchboxRow[3];
    }

    public void generateBox(int beadsAmt, User player1, User player2) {
        int rows = 3;
        matchbox = new MatchboxRow[rows];

        for(int i = 0; i < rows; i++) {
            matchbox[i] = new MatchboxRow();

            if(i == 0) {
                for (int j=0; j < beadsAmt; j++) {
                    Bead bead = new Bead(player1.toString(), j+1);
                    matchbox[i].addBead(bead);
                }
            }
            else if(i == rows - 1) {
                for(int j = 0; j < beadsAmt; j++) {
                    Bead bead = new Bead(player2.toString(), j+1);
                    matchbox[i].addBead(bead);
                }
            }
        }
    }
}
