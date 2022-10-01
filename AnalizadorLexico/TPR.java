package AnalizadorLexico;

import java.util.HashMap;

public class TPR {
    private HashMap<String,Integer> palabrasRes = new HashMap<String,Integer>();

    public TPR(){
        palabrasRes.put("if",257);
        palabrasRes.put("then",258);
        palabrasRes.put("else",259);
        palabrasRes.put("end_if",260);
        palabrasRes.put("out",261);
        palabrasRes.put("fun",262);
        palabrasRes.put("return",263);
        palabrasRes.put("break",264);
        palabrasRes.put("when",265);
        palabrasRes.put("while",266);
        palabrasRes.put("for",267);
        palabrasRes.put("continue",268);
        palabrasRes.put("i32",270);
        palabrasRes.put("f32",271);
        palabrasRes.put("const",291);

    }

    public Integer get(String key){
        return palabrasRes.get(key);
    }

}
