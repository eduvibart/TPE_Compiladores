package GeneracionCodigoIntermedio;
import Principal.*;

public abstract class ArbolSintactico extends ParserVal{
    private ArbolSintactico izq;
    private ArbolSintactico der; 
    private String lex;
    private String tipo;
    private Integer linea;

    public ArbolSintactico(String lex){
        izq = null;
        der = null;
        this.lex = lex;
        tipo = "";
        linea = null;
    }

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
}
