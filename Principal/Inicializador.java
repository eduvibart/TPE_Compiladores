package Principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import AnalizadorLexico.AnalizadorLexico;
import GeneracionCodigoIntermedio.*;

public class Inicializador{
    
	public static void main(String[] args) throws IOException, InterruptedException{
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

        //Mostrar Warnings (permiten la ejecucion)
        System.out.println("\nWarnings: ");
        HashMap<Integer,ArrayList<String>> warnings = AnalizadorLexico.getWarningsLexicos();
        for(Integer j : warnings.keySet()){
            System.out.println("Linea"+j+":");
            for(String w : warnings.get(j)){
                System.out.println("    "+w);
            }
        }
        
        
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
        ArrayList<Integer> keys = new ArrayList<>(erroresTotales.keySet());
        Collections.sort(keys);

        for(int i : keys){
            System.out.println("Linea"+i+":");
            for(String e : erroresTotales.get(i)){
                System.out.println("    "+e);
            }
        }
        
        
        
        //Mostramos la tabla de simbolos
        System.out.println("\nTabla de simbolos:");
        TablaSimbolos.imprimirTabla();
        if (erroresTotales.keySet().isEmpty()){
            NodoControl raiz = parser.getRaiz();
            System.out.println("\nArbol Sintactico \n \n");
            raiz.recorrerArbol("");
            lector.close();

            System.out.println("FUNCIONES");
            for (ArbolSintactico a : parser.getFuncion()) {
                a.recorrerArbol("");    	
            }
            
            //System.out.println("\nAssembler: \n" );
            GeneradorAssembler generador = new GeneradorAssembler(parser);
            //System.out.println(generador.getAssembler());

            File f = new File(nombreArchivo.substring(0,nombreArchivo.length()-4)+".asm");
            f.createNewFile();
            PrintWriter pw;
		    try {
			    pw = new PrintWriter(nombreArchivo.substring(0,nombreArchivo.length()-4)+".asm");
			    pw.print(generador.getAssembler());
			    pw.close();
		    } catch (FileNotFoundException e) {
			     //TODO Auto-generated catch block
			    e.printStackTrace();
		    }
            // Esto es para crear el .obj y luego el .exe 
            // NO TOCAR GRACIAS LOS QUIERO
            /* 
            Process p = Runtime.getRuntime().exec("cmd.exe /c D:\\masm32\\bin\\ml /c /Zd /coff D:\\Documents\\4to\\TPE_Compiladores\\salida.asm",null,new File("D:\\Documents\\4to\\TPE_Compiladores"));
            if(p.waitFor()==0){
                Process p1 = Runtime.getRuntime().exec("cmd.exe /c D:\\masm32\\bin\\link /c /SUBSYSTEM:CONSOLE D:\\Documents\\4to\\TPE_Compiladores\\salida.obj",null,new File("D:\\Documents\\4to\\TPE_Compiladores"));
                if(p1.waitFor()==0){
                    Runtime.getRuntime().exec("cmd.exe /c D:\\Documents\\4to\\TPE_Compiladores\\Extensiones\\OLLYDBG.exe D:\\Documents\\4to\\TPE_Compiladores\\salida.exe",null,new File("D:\\Documents\\4to\\TPE_Compiladores\\"));
                }
            }
            
            */
        }
    }
}