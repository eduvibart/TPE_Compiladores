package GeneracionCodigoIntermedio;

import java.util.ArrayList;
import java.util.List;

public class NodoFuncion {

    private List<NodoVariableUsada> varUsadas = null;
    private Integer lineaDeclaracion = null;
    private String nombre = "";
    
    public NodoFuncion(Integer lineaDeclaracion ){
        varUsadas = new ArrayList<NodoVariableUsada>();
        this.lineaDeclaracion = lineaDeclaracion;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        this.nombre = n;
    }
    public Integer getLineaDecl(){
        return lineaDeclaracion;
    }
    public void addVariable(NodoVariableUsada n ){
        varUsadas.add(n);
    }
    public void removeVariable(NodoVariableUsada n){
        varUsadas.remove(n);
    }
    public NodoVariableUsada getVariable(String v){
        for(NodoVariableUsada n : varUsadas){
            if(n.equals(v)){
                return n;
            }
        }
        return null;
    }
    public List<NodoVariableUsada> getListaVariables(){
        return this.varUsadas;
    }
}


