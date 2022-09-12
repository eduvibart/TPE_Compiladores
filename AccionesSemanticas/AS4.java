package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class AS4 extends AccionSemantica {
	private final int maximo = (int) Math.pow(2,31);
	private final int minimo = 0;
	
    @Override
    public void ejecutar(Token t, Reader entrada) {
    	t.delCarac(); //se saca el ultimo caracter
    	int valor = Integer.parseInt(t.getLexema()); //convierto el lexema del token a un entero para chekear el rango
    	
    	if ((valor <= maximo)&&(valor >= minimo)) {
            try {
                entrada.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}else {
    		AnalizadorLexico.addError("Constante fuera de rango");
    	}
    	
        
    }
    
}
