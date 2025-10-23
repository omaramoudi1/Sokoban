package modele;

public class destination extends sol {

public char getSymbol(){
if(joueur()){return '+';}
else if(caisse()){return '*';}
else{return '.';}
}

}
