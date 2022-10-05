package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public class ASE extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        
        AnalizadorLexico.addError("Error Lexico");
    }
    
}
