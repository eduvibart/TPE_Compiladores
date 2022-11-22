package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public abstract class AccionSemantica {

    protected static final int ID = 268;
    protected static final int ENTERO = 295;
    protected static final int FLOAT = 296 ;
    protected static final int PUNTO = 271;
    protected static final int PARENT_A = 272;
    protected static final int PARENT_C = 273;
    protected static final int COMILLA = 274;
    protected static final int COMA = 275;
    protected static final int DOSPUNTOS = 276;
    protected static final int PUNTOCOMA = 277;
    protected static final int IGUAL = 278;
    protected static final int MAYOR = 279;
    protected static final int MENOR = 280;
    protected static final int MENORIGUAL = 281;
    protected static final int MAYORIGUAL = 282;
    protected static final int LLAVE_A = 283;
    protected static final int LLAVE_C = 284;
    protected static final int EXCL = 285;
    protected static final int DIST = 286;
    protected static final int ASIG = 287;
    protected static final int CADENA = 288;
    protected static final int TAMANIO_VAR=25;
    protected static final int SUMA = 291;
    protected static final int RESTA = 292;
    protected static final int MULT = 293;
    protected static final int DIV = 294;



    public abstract void ejecutar(Token t, Reader entrada);
}
