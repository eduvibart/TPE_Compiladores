package AnalizadorLexico;

import java.util.HashMap;
import java.util.Map;


public class TablaSimbolos {
    
    private static Map<String,Map<String,Object>> simbolos = new HashMap<>();

    public static final String VALOR="valor";
    public static final String ID="ID";

    public static void addNuevoSimbolo(String lexema) {
        Map<String,Object> aux=new HashMap<>();
        simbolos.put(lexema,aux);
    }

    public static boolean existeSimbolo(String lexema){
        return simbolos.containsKey(lexema);
    }

    public static void addAtributo(String lexema, String atributo, Object valor ) {
        simbolos.get(lexema).put(atributo, valor);
    }

    public static Object getAtributo(String lexema, String atributo ) {
        return simbolos.get(lexema).get(atributo);
    }


}
