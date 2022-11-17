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
            
            case "Continue":
                label = getLabel();
                pilaLabelsTags.push(label);

                salida+= getIzq().getAssembler()+ "JM "+ label +"\n";
                if(getIzq().getLex().equals("Tag")){
                    pilaLabelsTags.push(getIzq().getIzq().getLex());
                }
                return salida;
            case "Break":
                label = getLabel();
                pilaLabelsBreak.push(label);
                if(!(getIzq().getLex().equals("Fin"))){
                    variable = getVariableAuxiliar();
                    pilaLabelsBreak.push(getIzq().getTipo());
                    pilaLabelsBreak.push(variable);
                    salida += "MOV "+ variable + ", "+ getIzq().getLex()+ "\n";
                }
                salida+= "JMP " + label + "\n";
                return salida;
            
            case "out":
                String variableOut = getVariableOut();
                data += variableOut + " db \"" + getIzq().getHojaPropia().getLex() + "\", 0\n	";
                salida += "invoke MessageBox, NULL, addr " + variableOut + ", addr " + variableOut + ", MB_OK\n";
                
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
                salida+= "call "+getIzq().getLex()+"\n";
                if(TablaSimbolos.getAtributo(variable,"Tipo").equals("Entero")){
                    salida+= "MOV EAX, "+ variable + "\n";
                    salida+= "MOV " + varAux + ", EAX"+"\n";
                }else{
                    salida += "FLD " + variable + "\n";
                    salida += "FST " + varAux + "\n";
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
