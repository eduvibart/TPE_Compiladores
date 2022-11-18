package GeneracionCodigoIntermedio;

public class LlamadoFun {
    
    private String nombre;
    private String ambito;
    private String par1;
    private String par2;
    public LlamadoFun(String nombre,String ambito,String par1,String par2){
        this.nombre = nombre;
        this.ambito = ambito;
        this.par1 = par1;
        this.par2 = par2;
    }
    public String getNombre(){return this.nombre;}
    public String getAmbito(){return this.ambito;}
    public String getPar1(){return this.par1;}
    public String getPar2(){return this.par2;}
}
