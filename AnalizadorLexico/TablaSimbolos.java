package AnalizadorLexico;

import java.util.HashMap;
import java.util.Map;


public class TablaSimbolos {
    
    private static final Map<String,Map<String,Object>> simbolos = new HashMap<>();//contiene el token y el lexema de clave

    public void addNuevoSimbolo(String lexema) {
        Map<String,Object> aux=new HashMap<>();
        simbolos.put(lexema,aux);
    }

    public void addAtributo(String lexema, String atributo, Object valor ) {
        simbolos.get(lexema).put(atributo, valor);
    }

    public Object getAtributo(String lexema, String atributo ) {
        return simbolos.get(lexema).get(atributo);
    }


}
