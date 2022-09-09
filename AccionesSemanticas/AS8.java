package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS8 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        try {
            entrada.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.delCarac();

        if (t.getLexema().length()>AccionSemantica.TAMANIO_VAR){
            t.truncarLexema(AccionSemantica.TAMANIO_VAR);
            AnalizadorLexico.addError("Warning: la variable "+t.getLexema()+" supera la longitud maxima: "+ AccionSemantica.TAMANIO_VAR);
        }
        Integer idPR = AnalizadorLexico.isPR(t.getLexema());
        if(idPR != null){
            t.setId(idPR);
        }
    }
    
} 
