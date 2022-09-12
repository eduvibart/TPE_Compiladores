package AnalizadorLexico;

import java.util.HashMap;

public class TPR {
    private HashMap<String,Integer> palabrasRes = new HashMap<String,Integer>();

    public TPR(){
        palabrasRes.put("if",1);
        palabrasRes.put("then",1);
        palabrasRes.put("else",1);
        palabrasRes.put("end-if",1);
        palabrasRes.put("out",1);
        palabrasRes.put("fun",1);
        palabrasRes.put("return",1);
        palabrasRes.put("break",1);
        palabrasRes.put("discard",1);
        palabrasRes.put("for",1);
        palabrasRes.put("continue",1);
        palabrasRes.put("f32",1);
    }

    public Integer get(String key){
        return palabrasRes.get(key);
    }

}
