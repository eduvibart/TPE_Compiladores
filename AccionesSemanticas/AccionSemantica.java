package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public abstract class AccionSemantica {

    protected static final int ID = 268;
    protected static final int ENTERO = 291;
    protected static final int FLOAT = 292 ;
    protected static final int PARENT_A = 271;
    protected static final int PARENT_C = 272;
    protected static final int COMA = 273;
    protected static final int DOSPUNTOS = 274;
    protected static final int PUNTOCOMA = 275;
    protected static final int IGUAL = 276;
    protected static final int MAYOR = 277;
    protected static final int MENOR = 278;
    protected static final int MENORIGUAL = 279;
    protected static final int MAYORIGUAL = 280;
    protected static final int LLAVE_A = 281;
    protected static final int LLAVE_C = 282;
    protected static final int DIST = 283;
    protected static final int ASIG = 284;
    protected static final int CADENA = 285;
    protected static final int TAMANIO_VAR=25;
    protected static final int SUMA = 287;
    protected static final int RESTA = 288;
    protected static final int MULT = 289;
    protected static final int DIV = 290;



    public abstract void ejecutar(Token t, Reader entrada);
}
