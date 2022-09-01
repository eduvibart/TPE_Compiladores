import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import AnalizadorLexico.AnalizadorLexico;

public  class clasePrincipal {

    public static void main(String[] args) throws IOException{

    BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
    AnalizadorLexico a = new AnalizadorLexico(entrada);

    //a.getToken();
    for(int i=0; i<14; i++) {
    	for (int j=0; j<25; j++) {
    		System.out.print(a.getME()[i][j]);
    	}
    	System.out.println("\n");
    }

    }
}
