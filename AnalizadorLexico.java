import java.util.*;
import java.io.IOException;
import java.io.Reader;

public class AnalizadorLexico{
	private static final String path = "TransicionDeEstados.txt";
	private static final int columnas=24;
	private static final int filas=14;
	
    private int[][] matrizEstados;
    private AccionSemantica[][] matrizAS;

    private Reader entrada;

    private int estadoAct;

    public AnalizadorLexico(Reader entrada){
        matrizEstados = GeneradorMatrices.getMatrizEstados(path, filas, columnas);
        matrizAS= GeneradorMatrices.getMatrizAS();
        this.entrada = entrada;
        estadoAct = 0;
    }
    public int getToken() throws IOException{
        int valor = getCaracter(Character.toChars(entrada.read())[0]);
        
        return 0;
    }

    private char obtenerCar(char c){
        if(Character.isDigit(c)){
            return '0';
        }else if (Character.isLowerCase(c) || (Character.isUpperCase(c) && c != 'F'))
            return 'a';
        return c;
    }
    private int getCaracter(char c){
        int valor;
        switch(obtenerCar(c)){
            case ' ':
                valor = 0;
                break;
            case '\t':
                valor = 1;
                break;
            case '\n':
                valor = 2;
                break;
            case 'a':
                valor = 3;
                break;
            case '0':
                valor = 4;
                break;
            case '_':
                valor = 5;
                break;
            case '.':
                valor = 6;
                break;
            case 'F':
                valor = 7;
                break;
            case '+':
                valor = 8;
                break;
            case '-':
                valor = 9;
                break;
            case '/':
                valor = 10;
                break;
            case '(':
                valor = 11;
                break;
            case ')':
                valor = 12;
                break;
            case '`':
                valor = 13;
                break;
            case ',':
                valor = 14;
                break;
            case ':':
                valor = 15;
                break;
            case ';':
                valor = 16;
                break;
            case '=':
                valor = 17;
                break;
            case '>':
                valor = 18;
                break;
            case '<':
                valor = 19;
                break;
            case '*':
                valor = 20;
                break;
            case '{':
                valor = 21;
                break;
            case '}':
                valor = 22;
                break;
            default:
                valor = 23;

        }
        return valor;
    }
}