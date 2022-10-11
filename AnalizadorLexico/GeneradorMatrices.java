package AnalizadorLexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import AccionesSemanticas.*;

import AccionesSemanticas.AccionSemantica;

public class GeneradorMatrices {
    
    public AccionSemantica[][] getMatrizAS(String path, int rows, int columns){
        AccionSemantica[][] as_matrix = new AccionSemantica[rows][columns];
        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    as_matrix[i][j] = getAS(scanner.nextLine());
                }
            }

            scanner.close();
        } catch (FileNotFoundException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);  
            excepcion.printStackTrace();
        }

        return as_matrix;
    }
    private static AccionSemantica getAS(String AS) {
        switch(AS){
            case "0":
            	return new AS0();
            case "1":
            	return new AS1();
            case "2":
            	return new AS2();
            case "3":
            	return new AS3();
            case "4":
            	return new AS4();
            case "5":
            	return new AS5();
            case "6":
            	return new AS6();
            case "7":
            	return new AS7();
            case "8":
            	return new AS8();
            case "9":
            	return new AS9();
            case "10":
            	return new AS10();
            case "11":
            	return new AS11();
            case "12":
            	return new AS12();
            case "13":
            	return new AS13();
            case "14":
            	return new AS14();
            case "15":
            	return new AS15();
            case "16":
            	return new AS16();
            case "E":
            	return new ASE();
        }
        
        return null;
    }
    
    public int[][] getMatrizEstados(String path, int rows, int columns) {
        int[][] int_matrix = new int[rows][columns];

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    int_matrix[i][j] = Integer.parseInt(scanner.nextLine());
                }
            }

            scanner.close();
        } catch (FileNotFoundException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);  
            excepcion.printStackTrace();
        }

        return int_matrix;
    }
  
}
