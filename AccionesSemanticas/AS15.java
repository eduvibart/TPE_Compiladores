package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.Token;

public class AS15 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        t.delCarac();
        try {
            entrada.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
