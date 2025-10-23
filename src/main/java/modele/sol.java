package modele;

public class sol extends block{

private boolean joueur_disponible;
private boolean caisse_disponible;

public sol(){
  this.joueur_disponible=false;
  this.caisse_disponible=false;
}

    @Override
    public boolean caisse() {
        return caisse_disponible;
    }

    @Override
    public char getSymbol() {
      if(joueur_disponible){
        return '@';
      }
      else if(caisse_disponible){
        return '$';
      }
      else{return ' ';}
    }

    @Override
    public boolean joueur() {
       return joueur_disponible;
    }

@Override
public void setJoueur(boolean x) {
   this.joueur_disponible=x;
}

@Override
public void setCaisse(boolean x) {
   this.caisse_disponible=x;
}
}
