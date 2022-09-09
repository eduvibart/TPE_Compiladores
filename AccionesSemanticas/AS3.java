package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public class AS3 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        t.setId(ID);
        
    }
    
}
