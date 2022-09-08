package AnalizadorLexico;
import AccionesSemanticas.AccionSemantica;

import java.io.IOException;
import java.io.Reader;

public class AnalizadorLexico{
	private static final String pathTE = "archivos/TransicionDeEstados.txt";
    private static final String pathAS = "archivos/MatrisAS.txt";
	private static final int columnas=25;
	private static final int filas=14;
	private GeneradorMatrices generadorMatrices;
    private int[][] matrizEstados;
    private AccionSemantica[][] matrizAS;
    private Reader entrada;
    private int estadoAct;
    private static int lineaAct;
    private static String errores = "";

    public AnalizadorLexico(Reader entrada){
    	this.generadorMatrices= new GeneradorMatrices();
        matrizEstados = generadorMatrices.getMatrizEstados(pathTE, filas, columnas);
        matrizAS= generadorMatrices.getMatrizAS(pathAS,filas,columnas);
        this.entrada = entrada;
        estadoAct = 0;
        lineaAct = 1;
    }

    public static void sumLinea(){
        lineaAct++;
    }
    
    public static void addError(String s){
        errores += "\n" + s + " Error en la linea " + lineaAct;
    }
    public Token getToken() throws IOException{
        Token t = new Token();
        while(estadoAct != -1 || estadoAct != -2){
            entrada.mark(1);
            char c = Character.toChars(entrada.read())[0];
            String s =""+ c;
            t.addCarac(s);
            int valor = getCaracter(c);
            matrizAS[estadoAct][valor].ejecutar(t,entrada);
            estadoAct = matrizEstados[estadoAct][valor]; 
        }
        return t;
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
            case '!':
                valor = 23;
                break;
            default:
                valor = 24;

        }
        return valor;
    }
}