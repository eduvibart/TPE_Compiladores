package GeneracionCodigoIntermedio;

public class NodoControl extends ArbolSintactico{
    private String salida;
    private String label;
    
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
        salida="";
        if (getLex().equals("Continue")){
            label = getLabel();
            pilaLabelsTags.push(label);

            salida+= getIzq().getAssembler()+ "JM "+ label +"\n";
            if(getIzq().getLex().equals("Tag")){
                pilaLabelsTags.push(getIzq().getIzq().getLex());
            }
            return salida;
        }
        return getIzq().getAssembler();
    }
    
    @Override
    public NodoHoja getHojaPropia() {
        return getIzq().getHojaPropia();
    }
    
}
