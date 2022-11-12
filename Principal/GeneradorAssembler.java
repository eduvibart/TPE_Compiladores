package Principal;

import java.util.HashMap;

import GeneracionCodigoIntermedio.ArbolSintactico;

public class GeneradorAssembler {
    private String data, code, codigoArbol,bibliotecas;
    private ArbolSintactico arbol;
    private Parser parser;
    

    public GeneradorAssembler(Parser parser){
        this.data="";
        this.code="";
        this.bibliotecas = ".386 \n.model flat, stdcall \noption casemap :none  \n" +
        "include \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\masm32.inc  \n" +
        "includelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\masm32.lib\n" +
        "include \\masm32\\include\\user32.inc \n" +
        "includelib \\masm32\\lib\\user32.lib \n";
        this.codigoArbol="";
        this.parser=parser;
        this.arbol=parser.getRaiz();

        codigoArbol =this.arbol.getAssembler();

        generarData();
        generarCode();
        
    }


    private void generarData(){
        data = "\n.data\n";
        /*for (String k : TablaSimbolos.getKeySet()){
            HashMap<String, Object> atributos = TablaSimbolos.getAtributos(k);
            String uso = (String) atributos.get("Uso");
            if(uso.equals("Variable")){

            }
        }*/
    }

    private void generarCode() {
        code = "\n.code\n";
        
        code+= this.codigoArbol;
    }

    public String getAssembler(){
        return (this.bibliotecas + this.data + this.code);
        
    }
}
