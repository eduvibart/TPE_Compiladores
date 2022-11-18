package GeneracionCodigoIntermedio;

public class LlamadoFun {
    
    private String nombre;
    private String ambito;
    private ArbolSintactico par1;
    private ArbolSintactico par2;
    private ArbolSintactico arbol;
    private int linea;
    public LlamadoFun(String nombre,String ambito,ArbolSintactico par1,ArbolSintactico par2,ArbolSintactico arbol,int linea){
        this.nombre = nombre;
        this.ambito = ambito;
        this.par1 = par1;
        this.par2 = par2;
        this.arbol = arbol;
        this.linea =linea;
    }
    public String getNombre(){return this.nombre;}
    public String getAmbito(){return this.ambito;}
    public ArbolSintactico getPar1(){return this.par1;}
    public ArbolSintactico getPar2(){return this.par2;}
    public ArbolSintactico getArbol(){return this.arbol;}
    public int getLinea(){return this.linea;}
}
