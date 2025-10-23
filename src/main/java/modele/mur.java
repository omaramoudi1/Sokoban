package modele;

public class mur extends block{ 
    public boolean joueur=false;
    public boolean caisse=false;
@Override
public boolean joueur(){return joueur;}

@Override
public boolean caisse(){return caisse;}

@Override
public char getSymbol(){return '#';}

@Override
public void setJoueur(boolean x) {
   
}

@Override
public void setCaisse(boolean x) {
  
}



}
