package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.Token;
import Principal.TablaSimbolos;

public class AS16 extends AccionSemantica{

	@Override
	public void ejecutar(Token t, Reader entrada) {
        t.delCarac();
        if (TablaSimbolos.existeSimbolo(t.getLexema())){
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, t.getLexema());
        	TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
        } else{
            TablaSimbolos.addNuevoSimbolo(t.getLexema());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, t.getLexema());
            TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
        }
	}

}
