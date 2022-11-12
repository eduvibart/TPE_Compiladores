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
        salida = "";
        /*switch(super.getLex()){
            case "Then":
                salida = getIzq().getAssembler();

            case "Else":
                //poner etiqueta else
                salida = getIzq().getAssembler();
            case "Condicion":
                label = super.getLabel();

            default:
                salida = getIzq().getAssembler();
                break;
        }*/
        salida = getIzq().getAssembler();
        return salida;
    }
    
    @Override
    public NodoHoja getHojaPropia() {
        return getIzq().getHojaPropia();
    }
    
}
