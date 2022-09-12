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


public void truncarLexema(int i){
    lexema = lexema.substring(0, i);
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

public void delCarac(){
    String auxiliarnuevo = "";
    for (int i=0;i<this.lexema.length()-1;i++){
        auxiliarnuevo += lexema.charAt(i);
    }
    this.lexema = auxiliarnuevo;
}

public void resetLexema(){
    this.lexema = "";
}
}
