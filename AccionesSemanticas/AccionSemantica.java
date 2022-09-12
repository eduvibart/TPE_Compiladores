package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public abstract class AccionSemantica {

    protected static final int ID = 0;
    protected static final int DIGITO = 0;
    protected static final int DECIMAL = 0;
    protected static final int PUNTO = 0;
    protected static final int F = 0;
    protected static final int SUMA = 0;
    protected static final int RESTA = 0;
    protected static final int DIV = 0;
    protected static final int MULT = 0;
    protected static final int PARENT_A = 0;
    protected static final int PARENT_C = 0;
    protected static final int COMILLA = 0;
    protected static final int COMA = 0;
    protected static final int DOSPUNTOS = 0;
    protected static final int PUNTOCOMA = 0;
    protected static final int IGUAL = 0;
    protected static final int MAYOR = 0;
    protected static final int MENOR = 0;
    protected static final int LLAVE_A = 0;
    protected static final int LLAVE_C = 0;
    protected static final int EXCL = 0;
    protected static final int DIST = 0;
    protected static final int ASIG = 0;
    protected static final int CADENA = 0; 
    protected static final int TAMANIO_VAR=25;




    public abstract void ejecutar(Token t, Reader entrada);
}
