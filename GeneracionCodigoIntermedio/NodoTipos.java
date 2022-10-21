package GeneracionCodigoIntermedio;

import java.util.ArrayList;
import java.util.List;

import Principal.ParserVal;

public class NodoTipos extends ParserVal{
    
    private List<String> identificadores;
    public NodoTipos(String s){
        this.identificadores = new ArrayList<String>();
        identificadores.add(s);
    }
    public void add(String s){
        this.identificadores.add(s);
    }
    public List<String> getList(){
        return this.identificadores;
    }
}


