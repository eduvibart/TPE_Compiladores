package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS10 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
       t.setId(-1);
       t.resetLexema();
       AnalizadorLexico.resetEstado();
       AnalizadorLexico.sumLinea();
    }
}
