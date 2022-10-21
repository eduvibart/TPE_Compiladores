package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;
import Principal.TablaSimbolos;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS4 extends AccionSemantica {
	private final long maximo = (long) Math.pow(2,31);
	private final long minimo = 0;
	
    @Override
    public void ejecutar(Token t, Reader entrada) {
    	t.delCarac(); //se saca el ultimo caracter
    	long valor = Long.valueOf(t.getLexema()); //convierto el lexema del token a un entero para checkear el rango
    	try {
            entrada.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	if ((valor <= maximo)&&(valor >= minimo)) {
            
            if (TablaSimbolos.existeSimbolo(t.getLexema())){
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, valor);
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
                TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Entero");
            } else{
                TablaSimbolos.addNuevoSimbolo(t.getLexema());
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, valor);
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
                TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Entero");

            }
    	}else {
    		AnalizadorLexico.addError("Entero fuera de rango");
    	}

    	
        
    }
    
}
