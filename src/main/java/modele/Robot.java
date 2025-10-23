package modele;

public class Robot {
private int ligne;
private int colonne;
private Directions d;


public Robot(int i,int j){
    this.colonne=j;
    this.ligne=i;

}
public void deplacerRobot(Directions d){
ligne+=d.getDLig();
colonne+=d.getDCol();
this.d=d;
}

public int posLigne(){
    return ligne;
}
public int posColonne(){
    return colonne;
}
public Directions getDirection(){
    return d;
}
}
