package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public class AS1 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        AnalizadorLexico.sumLinea();
    }
    
}
