package AnalizadorLexico;

public class Token {
    private int id;
    private String lexema;

public Token(){
    id=-1;
    lexema="";
}

public int getId() {
    return id;
}

public String getLexema() {
    return lexema;
}

public void setId(int id) {
    this.id = id;
}

public void addCarac(String s) {
    this.lexema = lexema + s;
}


}
