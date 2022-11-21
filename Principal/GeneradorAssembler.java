package Principal;

import java.util.HashMap;

import GeneracionCodigoIntermedio.ArbolSintactico;
import GeneracionCodigoIntermedio.NodoControl;

public class GeneradorAssembler {
    private String data, code, codigoArbol, bibliotecas, codigoFunciones;
    private ArbolSintactico arbol;
    

    public GeneradorAssembler(Parser parser){
        this.data= "\n.data\n"+"errorMensFun db \"El camino tomado por la funcion no tiene retorno.\", 0 \n"
                    + "errorMensDivisionPorCero db \"No se puede dividir por cero.\", 0 \n"
                    + "errorMensProductoEnteros db \"Se produjo un overflow en el producto de enteros.\", 0 \n"
                    + "errorMensRecursionMutua db \"Se produjo un llamado recursivo mutuo.\", 0 \n"
                    + "outMens db \"Out\", 0 \n"
                    + "error db \"Error de ejecucion!!!\", 0 \n"
                    + "@tagAnt dd ? \n";
        this.code="";
        this.codigoFunciones="errorFun: \n"
                            + "invoke MessageBox, NULL, addr errorMensFun, addr error, MB_OK \n" 
                            + "invoke ExitProcess, 1 \n"
                            + "errorDivisionPorCero: \n"
                            + "invoke MessageBox, NULL, addr errorMensDivisionPorCero, addr error, MB_OK \n"
                            + "invoke ExitProcess, 1 \n"
                            + "errorProductoEnteros: \n"
                            + "invoke MessageBox, NULL, addr errorMensProductoEnteros, addr error, MB_OK \n"
                            + "invoke ExitProcess, 1 \n"
                            + "errorRecursionMutua: \n"
                            + "invoke MessageBox, NULL, addr errorMensRecursionMutua, addr error, MB_OK \n"
                            + "invoke ExitProcess, 1 \n";
        this.bibliotecas = ".386 \n.model flat, stdcall \noption casemap :none  \n" +
        "include \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\masm32.inc  \n" +
        "includelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\masm32.lib\n" +
        "include \\masm32\\include\\user32.inc \n" +
        "includelib \\masm32\\lib\\user32.lib \n";
        this.codigoArbol="";
        this.arbol=parser.getRaiz();

        
        
        for (ArbolSintactico a : parser.getFuncion()) {
            codigoFunciones += a.getAssembler()+"\n";  	
        }
        codigoArbol +=this.arbol.getAssembler();
        generarCode();
        generarData();
        
    }


    private void generarData(){
       
        for (String k : TablaSimbolos.getKeySet()){
            HashMap<String, Object> atributos = TablaSimbolos.getAtributos(k);
            String uso = (String) atributos.get("Uso");
            if(uso != null){
                if(uso.equals("Constante")){
                    String aux = k.replace('.','_').replace('-','r');
                    String prefix = "_";
                    k = atributos.get("Valor").toString();
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
        code+= "invoke ExitProcess, 0 \n";
        code+= "end main";
    }

    public String getAssembler(){
        return (this.bibliotecas + this.data + this.code);
        
    }
}
