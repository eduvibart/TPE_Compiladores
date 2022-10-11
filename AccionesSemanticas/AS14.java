package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS14 extends AccionSemantica {

    @Override
    public void ejecutar(Token t, Reader entrada) {
        t.delCarac();
        AnalizadorLexico.addError("Falta ` en el final de la cadena.");
        
    }
    
}
