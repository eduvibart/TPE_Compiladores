package AnalizadorLexico;
import AccionesSemanticas.AccionSemantica;

import java.io.IOException;
import java.io.Reader;

public class AnalizadorLexico{
	private static final String pathTE = "archivos/TransicionDeEstados.txt";
    private static final String pathAS = "archivos/AccionesSemanticas.txt";
	private static final int columnas=25;
	private static final int filas=14;
	private static GeneradorMatrices generadorMatrices = new GeneradorMatrices();;
    private static int[][] matrizEstados  = generadorMatrices.getMatrizEstados(pathTE, filas, columnas);;
    private static AccionSemantica[][] matrizAS = generadorMatrices.getMatrizAS(pathAS,filas,columnas);;
    private static Reader entrada;
    private static int estadoAct = 0;
    private static int lineaAct = 1;
    public static String errores = "";
    private static TPR tablaPalabrasReservadas = new TPR();


    public static void setEntrada(Reader entrada){
        AnalizadorLexico.entrada = entrada;
    }
    public static void sumLinea(){
        lineaAct++;
    }
    
    public static void addError(String s){
        errores += "\n" + s + " Error en la linea " + lineaAct;
    }

    public static String getErrores(){
        return errores;
    }
    public static Token getToken() throws IOException{
        
        Token t = new Token();
        int r = 0;
        entrada.mark(1);
        while(estadoAct != -1 && estadoAct != -2 && (-1 != (r=entrada.read())) ){
            //System.out.println(r);
            char c = Character.toChars(r)[0];
            String s = Character.toString(c);
            t.addCarac(s);
            int valor = getCaracter(c);
            //System.out.println("El estado actual es: " + estadoAct);
            //System.out.println("El lexema del token antes: -" + t.getLexema()+"-");
            //System.out.println("El id del token antes: " + t.getId());
            //System.out.println("El valor de la columna es: " + valor);
            matrizAS[estadoAct][valor].ejecutar(t,entrada);
            //System.out.println("El lexema del token despues: -" + t.getLexema()+"-");
            //System.out.println("El id del token despues: " + t.getId());
            estadoAct = matrizEstados[estadoAct][valor]; 
            entrada.mark(1);
        }
        estadoAct = 0;
        return t;
    }

    public static void resetEstado(){
        estadoAct = 0; 
    }

    public static Integer isPR(String key){
        return tablaPalabrasReservadas.get(key);
    }
    private static char obtenerCar(char c){
        if(Character.isDigit(c)){
            return '0';
        }else if (Character.isLowerCase(c) || (Character.isUpperCase(c) && c != 'F'))
            return 'a';
        return c;
    }
    //Devuelve la columna correspondiente a la matriz de Estados
    private static int getCaracter(char c){
        int valor;
        switch(obtenerCar(c)){
            case ' ':
                valor = 0;
                break;
            case '\t':
                valor = 1;
                break;
            case '\n':
            case '\r':
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