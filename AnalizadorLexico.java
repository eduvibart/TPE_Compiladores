import java.util.*;

public class AnalizadorLexico{
    private int[][] matrizEstados;
    private AccionSemantica[][] matrizAS;

    public AnalizadorLexico(){
        matrizEstados = GeneradorMatrices.getMatrizEstados();
        matrizAS= GeneradorMatrices.getMatrizAS();
    }

    public int getToken(){
        return 0;
    }

}