package GeneracionCodigoIntermedio;

public class NodoVariableUsada {
    private Integer linea = null;
    private String variable = "";
    private String ambito = "";
    public NodoVariableUsada(String variable, Integer linea){
        this.variable = variable;
        this.linea = linea;
    }
    public Integer getLinea(){
        return this.linea;
    }
    public String getVariable(){
        return this.variable;
    }
    public void setAmbito(String ambito){
        this.ambito = ambito;
    }
    public String getAmbito(){
        return ambito;
    }
}
