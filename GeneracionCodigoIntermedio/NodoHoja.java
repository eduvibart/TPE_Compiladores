package GeneracionCodigoIntermedio;

public class NodoHoja extends ArbolSintactico {

    public NodoHoja(String lex) {
        super(lex);
    }

    @Override
    public void recorrerArbol(String s) {
        System.out.print(s);
        System.out.print("Lexema Nodo Hoja: " + super.getLex()+ "\n");
        
    }

    @Override
    public String getAssembler() {
        return "";
    }

    @Override
    public String getLex(){
        if((getUso().equals("Variable"))|| (getUso().equals("Constante"))){
            if(getUso().equals("Constante")){
                return "_" + super.getLex().replace('.', '_');
            }
            return "_"+ super.getLex();
        }
        else return super.getLex();
    } 

    @Override
    public NodoHoja getHojaPropia() {
        return this;
    }
    
}
