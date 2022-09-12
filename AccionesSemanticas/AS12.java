package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS12 extends AccionSemantica {

    @Override
    public void ejecutar(Token t, Reader entrada) {
        AnalizadorLexico.sumLinea();
        t.delCarac();
        t.delCarac();
        
    }
    
}
