package GeneracionCodigoIntermedio;

public class NodoVariable {
    private Integer linea = null;
    private String variable = "";
    private String ambito = "";
    public NodoVariable(String variable, Integer linea){
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
