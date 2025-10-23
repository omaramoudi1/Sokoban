package modele;

public class Directions {
    private int dLig;
    private int dCol;

    public Directions(char c) {
        switch (Character.toLowerCase(c)) {
            case 'z': this.dLig = -1; this.dCol = 0; break;  
            case 's': this.dLig = 1; this.dCol = 0; break;   
            case 'q': this.dLig = 0; this.dCol = -1; break; 
            case 'd': this.dLig = 0; this.dCol = 1; break;   
            default: this.dLig = 0; this.dCol = 0;          
        }
    }

    public Directions(int dLig, int dCol) {
        this.dLig = dLig;
        this.dCol = dCol;
    }

    public int getDLig() {
        return dLig;
    }

    public int getDCol() {
        return dCol;
    }
}

