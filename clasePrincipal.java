import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public  class clasePrincipal {

    public static void main(String[] args) throws IOException{

    BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
    AnalizadorLexico a = new AnalizadorLexico(entrada);
    int id = 0;
    while((id) != -1 ){
        Token prueba = a.getToken();
        id = prueba.getId();
        System.out.println(id);
        System.out.println(prueba.getLexema());
        System.out.println("");
    }

   
    }
}
