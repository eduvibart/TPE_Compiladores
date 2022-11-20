package GeneracionCodigoIntermedio;
import java.util.Stack;

import Principal.*;

public abstract class ArbolSintactico extends ParserVal{
    private ArbolSintactico izq;
    private ArbolSintactico der; 
    private String lex;
    private String tipo;
    private Integer linea;
    private String uso;
    public static int numeroVariable =0;
    protected static int numeroLabel=0;
    protected static int numeroOut=0;
    public static String data="";
    protected static Stack<String> pilaLabels = new Stack<String>();
    protected static Stack<String> pilaLabelsContinue = new Stack<String>();
    protected static Stack<String> pilaLabelsBreak = new Stack<String>();
    protected static Stack<String> pilaVariablesAuxiliares = new Stack<String>();
    protected static Stack<String> pilaEtiquetas = new Stack<String>();

    public ArbolSintactico(String lex){
        izq = null;
        der = null;
        this.lex = lex;
        tipo = "";
        uso = "";
        linea = null;
    }

    public void setUso(String u){this.uso = u;
        if(u.equals("variableAuxiliar")){
            TablaSimbolos.addNuevoSimbolo(this.lex);
            TablaSimbolos.addAtributo(this.lex,"Tipo",this.tipo);
            TablaSimbolos.addAtributo(this.lex,"Uso",u);
        }}
    public String getUso(){return this.uso;}
    public String getTipo(){return this.tipo;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public ArbolSintactico getIzq(){return this.izq;}
    public ArbolSintactico getDer(){return this.der;}
    public String getLex() {return this.lex;}
    public void setIzq(ArbolSintactico izq) { this.izq = izq; }
	public void setDer(ArbolSintactico der) { this.der = der; }
	public void setLex(String lex) { this.lex= lex; }
    public Integer getLinea(){return this.linea;}
    public void setLinea(Integer linea){this.linea = linea;}
    public abstract void recorrerArbol(String identado);
    public abstract String getAssembler();
    public abstract NodoHoja getHojaPropia();
    public String getLexemaWhen(){return this.lex;} 

    public static String getLabel(){
        numeroLabel++;
        return "label_"+numeroLabel;
    }
    public static String getVariableAuxiliar(){
        numeroVariable++;
        return "@aux" + numeroVariable;
    }
    public static String getVariableOut(){
        numeroOut++;
        return "@out" + numeroOut;
    }
}
