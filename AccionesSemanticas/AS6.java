package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.Token;

public class AS6 extends AccionSemantica {

    @Override
    public void ejecutar(Token t, Reader entrada) {
        t.setId(FLOAT);
        
    }
    
}
