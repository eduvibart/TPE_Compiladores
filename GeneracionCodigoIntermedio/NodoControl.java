package GeneracionCodigoIntermedio;

import Principal.TablaSimbolos;

public class NodoControl extends ArbolSintactico{
    private String salida;
    private String label;
    private String variable;
    private NodoHoja hojaPropia;
    
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
            case "Break":
                label = pilaLabelsBreak.peek();
                
                if(!(getIzq().getLex().equals("Fin"))){
                    variable = pilaVariablesAuxiliares.peek();
                    if(getIzq().getTipo().equals("Entero")){
                        salida+= "MOV EAX , " + getIzq().getLex()+ "\n"; 
                        salida+= "MOV " + variable + ", " + "EAX" + "\n";
                    }
                    else
                    {
                        salida += "FLD " + getIzq().getLex() + "\n";
                        salida += "FST " + variable + "\n";
                    }
                }
                salida+= "JMP " + label + "\n";
                
                return salida;
            
            case "out":
                String variableOut = getVariableOut();
                data += variableOut + " db \"" + getIzq().getHojaPropia().getLex() + "\", 0\n	";
                salida += "invoke MessageBox, NULL, addr " + variableOut + ", addr outMens, MB_OK\n";
                
                return salida;
            
            case "Funcion":
                NodoHoja n = (new NodoHoja("@aux@"+getIzq().getLex() ));
                n.setTipo(getIzq().getTipo());
                n.setUso("variableAuxiliar");
                pilaVariablesAuxiliares.push("@aux@"+getIzq().getLex());
                salida+= getIzq().getLex() + ":\n";
                salida+= getIzq().getAssembler();       
                pilaVariablesAuxiliares.pop();
                salida += "JMP errorFun";
                return salida;
            
            case "Llamado Funcion":
                salida+= getIzq().getIzq().getAssembler()+ getIzq().getDer().getAssembler();
                variable = "@aux@"+getIzq().getLex();
                String varAux = getVariableAuxiliar();
                this.hojaPropia= new NodoHoja(varAux);
                this.hojaPropia.setTipo(getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");
                if(!pilaVariablesAuxiliares.isEmpty()){
                    salida+="MOV EAX, @tagAnt \n"; 
                    salida+="CMP EAX, " + getIzq().getLex() +"\n";
                    salida+="JE errorRecursionMutua \n";
                    String tagFun=pilaVariablesAuxiliares.peek().substring(5,pilaVariablesAuxiliares.peek().length());
                    salida+= "MOV EAX, " + tagFun + "\n";
                    salida+= "MOV @tagAnt, EAX \n";
                }
                salida+= "call "+getIzq().getLex()+"\n";
                if(TablaSimbolos.getAtributo(variable,"Tipo").equals("Entero")){
                    salida+= "MOV EAX, "+ variable + "\n";
                    salida+= "MOV " + varAux + ", EAX"+"\n";
                }else{
                    salida += "FLD " + variable + "\n";
                    salida += "FST " + varAux + "\n";
                }
                if(!pilaVariablesAuxiliares.isEmpty()){
                    salida += "XOR EAX, EAX \n";
                    salida += "MOV @tagAnt, EAX \n";
                }
                return salida;
            case "Retorno":
                salida+=getIzq().getAssembler();
                if(getIzq().getTipo().equals("Entero")){
                    salida+= "MOV EAX, "+ getIzq().getHojaPropia().getLex() + "\n";
                    salida+= "MOV " + pilaVariablesAuxiliares.peek() + ", EAX"+"\n";
                }else{
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FST " + pilaVariablesAuxiliares.peek() + "\n";
                }
                salida+="ret \n";
                return salida;
        }
        return getIzq().getAssembler();
    }
    
    @Override
    public NodoHoja getHojaPropia() {
        return this.hojaPropia;
    }
    
}
