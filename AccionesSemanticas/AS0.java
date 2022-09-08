package AccionesSemanticas;

import java.io.Reader;

import AnalizadorLexico.*;

public class AS0 extends AccionSemantica {
    public AS0(){
        
    }

    @Override
    public void ejecutar(Token t, Reader entrada) {
        switch(t.getLexema()){
            case "+":
                t.setId(SUMA);
                break;
            case "-":
                t.setId(RESTA);
                break; 
            case "*":
                t.setId(MULT);
                break;
            case "/":
                t.setId(DIV);
                break;
            case "(":
                t.setId(PARENT_A);
                break;
            case ")":
                t.setId(PARENT_C);
                break;
            case "{":
                t.setId(LLAVE_A);
                break;
            case "}":
                t.setId(LLAVE_C);
                break;
            case ".":
                t.setId(PUNTO);
                break;
            case ",":
                t.setId(COMA);
                break;
            case "!":
                t.setId(EXCL);
                break;
        }
    }
    
}
