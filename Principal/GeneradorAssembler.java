package Principal;

import java.util.HashMap;
import java.util.Map;

import GeneracionCodigoIntermedio.ArbolSintactico;
import GeneracionCodigoIntermedio.NodoControl;

public class GeneradorAssembler {
    private String data, code, codigoArbol, bibliotecas, codigoFunciones;
    private ArbolSintactico arbol;
    private Parser parser;
    

    public GeneradorAssembler(Parser parser){
        this.data="";
        this.code="";
        this.codigoFunciones="";
        this.bibliotecas = ".386 \n.model flat, stdcall \noption casemap :none  \n" +
        "include \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\masm32.inc  \n" +
        "includelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\masm32.lib\n" +
        "include \\masm32\\include\\user32.inc \n" +
        "includelib \\masm32\\lib\\user32.lib \n";
        this.codigoArbol="";
        this.parser=parser;
        this.arbol=parser.getRaiz();

        codigoArbol =this.arbol.getAssembler();
        
        for (ArbolSintactico a : parser.getFuncion()) {
            codigoFunciones += a.getAssembler()+"\n";  	
        }

        generarCode();
        
    }


    private void generarData(){
        data = "\n.data\n";
        for (String k : TablaSimbolos.getKeySet()){
            HashMap<String, Object> atributos = TablaSimbolos.getAtributos(k);
            String uso = (String) atributos.get("Uso");
            String tipo = (String) atributos.get("Tipo");
            if(uso != null){
                if(uso.equals("Constante")){
                    String aux = k.replace('.','_');
                    String prefix = "_";
                    if(tipo.equals("Float")){
                        k = atributos.get("Valor").toString();
                    }
                    data += prefix + aux + " dd " + k + "\n";
                }
                if( ( !uso.equals("Funcion") ) && ( (uso.equals("Variable") ) || ( uso.equals("variableAuxiliar")  ) ) ) {
                    String prefix = "";
                    if(uso.equals("Variable")) prefix = "_";
                    data += prefix + k + " dd " + " ? " + "\n";
                }
            }
            data+= NodoControl.data;
        }
    }

    private void generarCode() {
        code = "\n.code\n";
        
        code+= codigoFunciones;
        code+= "main:\n";
        
        code+= this.codigoArbol;
        code+= "end main";
    }

    public String getAssembler(){
        return (this.bibliotecas + this.data + this.code);
        
    }
}
