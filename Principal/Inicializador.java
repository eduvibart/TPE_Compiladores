package Principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
        Parser parser = new Parser();
        parser.run();
        
        //Agrupar Errores sintacticos y lexicos
        HashMap<Integer,ArrayList<String>> erroresTotales = AnalizadorLexico.getErroresLexicos();
        HashMap<Integer,ArrayList<String>> erroresSintacticos = Parser.getErroresSintacticos();
        for (Integer i : erroresSintacticos.keySet()){
            if (erroresTotales.get(i)==null){
                erroresTotales.put(i,erroresSintacticos.get(i));
            }  
            else{
                erroresTotales.get(i).addAll(erroresSintacticos.get(i));
            }
        }

        //Mostrar Errores totales:
        System.out.println("\n\nErrores de Compilacion: ");
        for(Integer i : erroresTotales.keySet()){
            System.out.println("Linea"+i+":");
            for(String e : erroresTotales.get(i)){
                System.out.println("    "+e);
            }
        }
        
        
        
        //Mostramos la tabla de simbolos
        System.out.println("\nTabla de simbolos \n");
        TablaSimbolos.imprimirTabla();

        if (erroresTotales.keySet() == null){
            NodoControl raiz = parser.getRaiz();
            System.out.println("\nArbol Sintactico \n \n");
            raiz.recorrerArbol("");
            lector.close();

            System.out.println("FUNCIONES");
            for (Map.Entry<String,ArbolSintactico> entry : parser.getFuncion().entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " ); 
                entry.getValue().recorrerArbol("");    	
            }
        }
    }
}