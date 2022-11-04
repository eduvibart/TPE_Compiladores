package GeneracionCodigoIntermedio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodoFuncion {

    private List<NodoVariable> varUsadas = null;
    private List<NodoVariable> varDecl = null;
    private Map<String,NodoVariable> varDeclAmbito = null;
    private Integer lineaDeclaracion = null;
    private String nombre = "";
    
    public NodoFuncion(Integer lineaDeclaracion ){
        varUsadas = new ArrayList<NodoVariable>();
        varDecl = new ArrayList<NodoVariable>();
        varDeclAmbito = new HashMap<String,NodoVariable>();
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
    public void addUsada(NodoVariable n ){
        varUsadas.add(n);
    }
    public void removeUsada(NodoVariable n){
        varUsadas.remove(n);
    }
    public void addDecl(NodoVariable n){
        varDecl.add(n);
    }
    public void addDeclAmbito(NodoVariable n){
        varDeclAmbito.put(n.getVariable(),n);
    }
    public List<NodoVariable> getListaUsadas(){
        return this.varUsadas;
    }
    public Map<String, NodoVariable> getMapDecl(){
        return  this.varDeclAmbito;
    }
    public List<NodoVariable> getListaDecl(){
        return this.varDecl;
    }
    public boolean isDecl(String n){
        String n1 = n;
        for(int i =0 ; i<50;i++){
            n1 = n1 + "@";
            if (this.varDeclAmbito.containsKey(n1)){
                return true;
            }
        }
        return this.varDeclAmbito.containsKey(n);
    }
}


