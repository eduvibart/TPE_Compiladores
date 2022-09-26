package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public abstract class AccionSemantica {

    protected static final int ID = 1;
    protected static final int DIGITO = 2;
    protected static final int DECIMAL = 3;
    protected static final int PUNTO = 4;
    protected static final int SUMA = 6;
    protected static final int RESTA = 7;
    protected static final int DIV = 8;
    protected static final int MULT = 9;
    protected static final int PARENT_A = 10;
    protected static final int PARENT_C = 11;
    protected static final int COMILLA = 12;
    protected static final int COMA = 13;
    protected static final int DOSPUNTOS = 14;
    protected static final int PUNTOCOMA = 15;
    protected static final int IGUAL = 16;
    protected static final int MAYOR = 17;
    protected static final int MENOR = 18;
    protected static final int LLAVE_A = 19;
    protected static final int LLAVE_C = 20;
    protected static final int EXCL = 21;
    protected static final int DIST = 22;
    protected static final int ASIG = 23;
    protected static final int CADENA = 24;
    protected static final int MENORIGUAL = 25;
    protected static final int MAYORIGUAL = 26;
    protected static final int TAMANIO_VAR=25;




    public abstract void ejecutar(Token t, Reader entrada);
}
