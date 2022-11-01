package Principal;


import java.util.HashMap;
import java.util.Map;


public class TablaSimbolos {
    
    private static Map<String,Map<String,Object>> simbolos = new HashMap<>(); //Lexema, y atributos del mismo.

    public static final String VALOR="Valor";
    public static final String ID="Id";

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
        if(simbolos.get(lexema) != null)
            return simbolos.get(lexema).get(atributo);
        return null;
    }

    public static void imprimirTabla(){
    	for (String lex: simbolos.keySet()) {
    		System.out.print("Lexema : "+ lex +". Atributos ->");
    		Map<String, Object> atributos = simbolos.get(lex);
    		for(String atributo : atributos.keySet()) {
    			System.out.print(atributo+" "+ atributos.get(atributo)+ ", ");
    		}
    		System.out.println("");
    	}
    }

}
