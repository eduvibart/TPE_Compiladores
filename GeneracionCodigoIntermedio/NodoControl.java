package GeneracionCodigoIntermedio;

public class NodoControl extends ArbolSintactico{
    private String salida;
    private String label;
    private String variable;
    
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
        switch(getLex()){
            
            case "Continue":
                label = getLabel();
                pilaLabelsTags.push(label);

                salida+= getIzq().getAssembler()+ "JM "+ label +"\n";
                if(getIzq().getLex().equals("Tag")){
                    pilaLabelsTags.push(getIzq().getIzq().getLex());
                }
                return salida;
            case "Break":
                label = getLabel();
                pilaLabelsBreak.push(label);
                if(!(getIzq().getLex().equals("Fin"))){
                    variable = getVariableAuxiliar();
                    pilaLabelsBreak.push(getIzq().getTipo());
                    pilaLabelsBreak.push(variable);
                    salida += "MOV "+ variable + ", "+ getIzq().getLex()+ "\n";
                }
                salida+= "JM " + label + "\n";
                return salida;
        
        }
        return getIzq().getAssembler();
    }
    
    @Override
    public NodoHoja getHojaPropia() {
        return getIzq().getHojaPropia();
    }
    
}
