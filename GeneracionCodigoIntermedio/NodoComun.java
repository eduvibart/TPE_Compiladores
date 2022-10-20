package GeneracionCodigoIntermedio;

public class NodoComun extends ArbolSintactico{

    
    public NodoComun(String lex,ArbolSintactico izq,ArbolSintactico der) {
        super(lex);
        setDer(der);
        setIzq(izq);
    }

    @Override
    public void recorrerArbol(String s) {
        System.out.print(s+"Lexama Nodo: " + super.getLex() + "\n");
        System.out.print(s+"Hijo Izquierdo: " + "\n");
        super.getIzq().recorrerArbol(s+"    ");
        System.out.print(s+"Hijo Derecho: "+ "\n");
        super.getDer().recorrerArbol(s+"    ");
        
    }

    
    
}
