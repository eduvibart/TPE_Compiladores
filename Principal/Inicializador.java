package Principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


import AnalizadorLexico.AnalizadorLexico;
import GeneracionCodigoIntermedio.*;

public class Inicializador{
    
	public static void main(String[] args) throws IOException{
        Scanner lector = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo del cual se quiere leer seguido de '.' y la extención del archivo.");
        
        String nombreArchivo = lector.nextLine();
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entradas/" + nombreArchivo)); //en nuestro caso gramatica.txt
        
        BufferedReader entradaAMostrar = new BufferedReader(new FileReader("archivos/entradas/" + nombreArchivo));
        System.out.println("\nEntrada: \n");
        ImpresorEntrada impresora = new ImpresorEntrada(entradaAMostrar);
        impresora.imprimirEntrada();
        System.out.println("\n\n");
        
        AnalizadorLexico.setEntrada(entrada);
        Parser parser = new Parser(true);
        parser.run();

        System.out.println("\nErrores lexicos : \n");
        System.out.println(AnalizadorLexico.getErrores());
        
        //Mostramos la tabla de simbolos
        System.out.println("Tabla de simbolos \n \n");
        TablaSimbolos.imprimirTabla();
        NodoControl raiz = parser.getRaiz();
        System.out.println("Arbol Sintactico \n \n");
        raiz.recorrerArbol("");
        lector.close();

        System.out.println("FUNCIONES");
        for (Map.Entry<String,ArbolSintactico> entry : parser.getFuncion().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " ); 
            entry.getValue().recorrerArbol("");    	
        }
    }
}