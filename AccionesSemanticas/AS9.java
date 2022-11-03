package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.Token;
import Principal.TablaSimbolos;

public class AS9 extends AccionSemantica{

    @Override
    public void ejecutar(Token t, Reader entrada) {
        t.delCarac();
        try {
            entrada.reset();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (TablaSimbolos.existeSimbolo(t.getLexema())){
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
            TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Float");
        } else{
            TablaSimbolos.addNuevoSimbolo(t.getLexema());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
            TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Float");

        }
    }
    
}
