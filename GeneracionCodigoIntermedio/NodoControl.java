package GeneracionCodigoIntermedio;

public class NodoControl extends ArbolSintactico{

    public NodoControl(String lex,ArbolSintactico nodo) {
        super(lex);
        setIzq(nodo);
    }

    @Override
    public void recorrerArbol(String identado) {
        System.out.print("Nodo de control: " +super.getLex()+ "\n");
        String s = "    ";
        super.getIzq().recorrerArbol(s);
    }
    
    
}
