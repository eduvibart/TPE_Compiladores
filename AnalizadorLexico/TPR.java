package AnalizadorLexico;

import java.util.HashMap;

public class TPR {
    private HashMap<String,Integer> palabrasRes = new HashMap<String,Integer>();

    public TPR(){
        palabrasRes.put("if",40);
        palabrasRes.put("then",41);
        palabrasRes.put("else",42);
        palabrasRes.put("end_if",43);
        palabrasRes.put("out",44);
        palabrasRes.put("fun",45);
        palabrasRes.put("return",46);
        palabrasRes.put("when",47);
        palabrasRes.put("while",48);
        palabrasRes.put("for",49);
        palabrasRes.put("continue",50);
        palabrasRes.put("i32",51);
        palabrasRes.put("f32",52);
        palabrasRes.put("const",53);
        palabrasRes.put("break",54);
    }

    public Integer get(String key){
        return palabrasRes.get(key);
    }

}
