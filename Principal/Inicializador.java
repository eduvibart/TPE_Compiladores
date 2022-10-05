package Principal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import AnalizadorLexico.AnalizadorLexico;

public class Inicializador{
    
	public static void main(String[] args) throws IOException{
        Scanner lector = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo del cual se quiere leer seguido de '.' y la extención del archivo.");
        String nombreArchivo = lector.nextLine();
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entradas/" + nombreArchivo)); //en nuestro caso gramatica.txt
        AnalizadorLexico.setEntrada(entrada);
        Parser parser = new Parser();
        parser.run();
        System.out.println(AnalizadorLexico.getErrores());
        
        //Mostramos la tabla de simbolos
        System.out.println("Tabla de simbolos \n \n");
        TablaSimbolos.imprimirTabla();
        	
    }
}