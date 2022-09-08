import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import AnalizadorLexico.AnalizadorLexico;

public  class clasePrincipal {

    public static void main(String[] args) throws IOException{

    BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
    AnalizadorLexico a = new AnalizadorLexico(entrada);

   
    }
}
