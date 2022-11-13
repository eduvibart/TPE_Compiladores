package GeneracionCodigoIntermedio;

public class NodoControl extends ArbolSintactico{
    
    public NodoControl(String lex,ArbolSintactico nodo) {
        super(lex);
        setIzq(nodo);
    }

    @Override
    public void recorrerArbol(String identado) {
        
        System.out.print(identado+"Nodo de control: " +super.getLex()+ "\n");
        identado += "    ";
        super.getIzq().recorrerArbol(identado);
    }

    @Override
    public String getAssembler() {
        
        return getIzq().getAssembler();
    }
    
    @Override
    public NodoHoja getHojaPropia() {
        return getIzq().getHojaPropia();
    }
    
}
