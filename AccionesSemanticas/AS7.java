package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import Principal.TablaSimbolos;

public class AS7 extends AccionSemantica{
	private final Double maximo = Math.pow(3.40282347, 38.0);
	private final Double minimo = Math.pow(1.17549435, -38.0);
	private final Double cero = 0.0;
    
	@Override
    public void ejecutar(Token t, Reader entrada) {
		      
		t.delCarac();		
		String lexema = t.getLexema();
		int i =0;
		char caracter=' ';
		String digito=""; //parte numerica
		String exponente=""; //parte exponencial
		
		while (caracter != 'F') {
			caracter = lexema.charAt(i);
			digito+= caracter;
			i++;
		}
		
		Double d = Double.parseDouble(digito); //d va a tener la parte numerica
		
		for (int j=i ; j < (lexema.length()); j++) {
			caracter = lexema.charAt(j);
			exponente += caracter;
		}
		
		Double e = Double.parseDouble(exponente);
		
		Double numero = Math.pow(d, e); //numero del lexema convertido a double
		if (((numero > maximo)|| (numero < minimo)) && numero != cero) {
			//Fuera de rango
			AnalizadorLexico.addError("Numero fuera de rango");
		}else{
			if (TablaSimbolos.existeSimbolo(t.getLexema())){
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, numero);
				TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
                TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Float");

            } else{
                TablaSimbolos.addNuevoSimbolo(t.getLexema());
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.VALOR, numero);
				TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID, t.getId());
                TablaSimbolos.addAtributo(t.getLexema(), "Tipo", "Float");

            }
		}
		try {
			entrada.reset();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
    
}
