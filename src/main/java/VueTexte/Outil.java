package VueTexte;

import java.io.IOException;

public class Outil {

public static char lireCaractere(){
    int rep= ' ';
    int buf;
    try{
        rep = System.in.read();
        buf = rep;
        while (buf != '\n')
            buf = System.in.read();
    } 
    catch (IOException e) {};
    return (char) rep;
}

}
