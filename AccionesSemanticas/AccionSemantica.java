package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public abstract class AccionSemantica {

    protected static final int ID = 269;
    protected static final int ENTERO = 296;
    protected static final int FLOAT = 297 ;
    protected static final int PUNTO = 272;
    protected static final int PARENT_A = 273;
    protected static final int PARENT_C = 274;
    protected static final int COMILLA = 275;
    protected static final int COMA = 276;
    protected static final int DOSPUNTOS = 277;
    protected static final int PUNTOCOMA = 278;
    protected static final int IGUAL = 279;
    protected static final int MAYOR = 280;
    protected static final int MENOR = 281;
    protected static final int MENORIGUAL = 282;
    protected static final int MAYORIGUAL = 283;
    protected static final int LLAVE_A = 284;
    protected static final int LLAVE_C = 285;
    protected static final int EXCL = 286;
    protected static final int DIST = 287;
    protected static final int ASIG = 288;
    protected static final int CADENA = 289;
    protected static final int TAMANIO_VAR=25;
    protected static final int SUMA = 292;
    protected static final int RESTA = 293;
    protected static final int MULT = 294;
    protected static final int DIV = 295;



    public abstract void ejecutar(Token t, Reader entrada);
}
