package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import GeneracionCodigoIntermedio.NodoFuncion;
import Principal.Parser;
import Principal.TablaSimbolos;

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
            AnalizadorLexico.addWarning("Warning: la variable "+t.getLexema()+" supera la longitud maxima: "+ AccionSemantica.TAMANIO_VAR);
        }
        Integer idPR = AnalizadorLexico.isPR(t.getLexema());
        
        if(idPR != null){
            if(t.getLexema().equals("fun")){
                Parser.addLinFun((Integer)AnalizadorLexico.getLineaAct());
                NodoFuncion n = new NodoFuncion((Integer)AnalizadorLexico.getLineaAct());
                Parser.addFuncionPila(n);
            }
            t.setId(idPR);
        }else{
            if (TablaSimbolos.existeSimbolo(t.getLexema())){
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID,t.getId() );
            } else{
                TablaSimbolos.addNuevoSimbolo(t.getLexema());
                TablaSimbolos.addAtributo(t.getLexema(), TablaSimbolos.ID,t.getId());

            }
        }

        


    }
    
} 
