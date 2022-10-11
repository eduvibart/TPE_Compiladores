package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.Token;
import Principal.TablaSimbolos;

public class AS16 extends AccionSemantica{

	@Override
	public void ejecutar(Token t, Reader entrada) {
        t.delCarac();
        try {
            entrada.reset();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TablaSimbolos.existeSimbolo(t.getLexema())){
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, t.getLexema());
        	TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
        } else{
        	System.out.println("hola me estoy ejecutando");
            TablaSimbolos.addNuevoSimbolo(t.getLexema());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, t.getLexema());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
        }
	}

}
