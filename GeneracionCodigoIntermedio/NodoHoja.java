package GeneracionCodigoIntermedio;

public class NodoHoja extends ArbolSintactico {

    public NodoHoja(String lex) {
        super(lex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void recorrerArbol(String s) {
        System.out.print(s);
        System.out.print("Lexema Nodo Hoja: " + super.getLex()+ "\n");
        
    }
    
}
