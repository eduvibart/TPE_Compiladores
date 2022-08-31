import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneradorMatrices {
    
    public static AccionSemantica[][] getMatrizAS(){
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
