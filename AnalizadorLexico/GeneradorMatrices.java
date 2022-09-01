package AnalizadorLexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import AccionesSemanticas.*;

import AccionesSemanticas.AccionSemantica;

public class GeneradorMatrices {
    
    public static AccionSemantica[][] getMatrizAS(String path, int rows, int columns){
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
            case "AS0":
            return new AS0();
        }
        
        return null;
    }
    
    public static int[][] getMatrizEstados(String path, int rows, int columns) {
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
