package GeneracionCodigoIntermedio;

public class NodoComun extends ArbolSintactico{

    private NodoHoja hojaPropia=null;
    private String variable;
    private String label;
    private String labelFin;
    private String salidaDer;


    public NodoComun(String lex,ArbolSintactico izq,ArbolSintactico der) {
        super(lex);
        setDer(der);
        setIzq(izq);
    }

    @Override
    public void recorrerArbol(String s) {
        System.out.print(s+"Lexama Nodo: " + super.getLex() + "\n");
        
        if (!(super.getIzq() == null)){
            System.out.print(s+"Hijo Izquierdo: " + "\n");
            super.getIzq().recorrerArbol(s+"    ");
        }
        
        if (!(super.getDer() == null)){
            System.out.print(s+"Hijo Derecho: "+ "\n");
            super.getDer().recorrerArbol(s+"    ");
        }
        
    }


    @Override
    public String getAssembler() {
        String salida = "";
        switch(super.getLex()){
            
            //Asignacion
            case "=:":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                if(getIzq().getTipo().equals("Entero")){
                    salida+= "MOV EAX , " + getDer().getHojaPropia().getLex() + "\n"; 
                    salida+= "MOV " + getIzq().getHojaPropia().getLex() + ", " + "EAX" + "\n";
                }else {
                    salida += "FLD " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "FST " + getIzq().getHojaPropia().getLex() + "\n";
                } 
                break;
            
            //Suma
            case "+":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                
                //se crea hoja propia para el nodo intermedio la cual va a tener de lexema la variable, el tipo que se esta intentando sumar y el uso es "variableAuxiliar"
                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if(getIzq().getHojaPropia().getTipo().equals("Entero")){ //si el tipo de la hoja propia del hijo izquierdo es entero (o sea la operacion entre hijo izq y der es entera)
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n"; //entonces aca el get lexema de la hoja va a ser la variable que tiene datos anteriores por lado izq
                    salida += "ADD EAX, " + getDer().getHojaPropia().getLex() + "\n";

                    salida += "MOV " + variable + ", EAX" + "\n";
                }
                else
                {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n"; //carga primer operando
                    salida += "FADD " + getDer().getHojaPropia().getLex() + "\n"; //suma st0 y st1
                    
                    salida += "FST "+ variable + "\n"; //copia st a la variable y no afecta la pila.
                }
                break;
            
            //Resta
            case "-":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "SUB EAX, " + getDer().getHojaPropia().getLex() + "\n";

                    salida += "MOV " + variable + ", EAX" + "\n";
                    
                } else 
                {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FSUB " + getDer().getHojaPropia().getLex() + "\n";
                
                    salida += "FST " + variable + "\n";
                }
                break;
            
            //Multiplicacion
            case "*":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                
                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");
    
                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "IMUL EAX, " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JO errorProductoEnteros\n";
                    salida += "MOV " + variable + ", EAX" + "\n";

                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FMUL " + getDer().getHojaPropia().getLex() + "\n";
                    
                    salida += "FST " + variable + "\n";
                }
                break;
            
            //Division
            case "/":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "CMP " + getDer().getHojaPropia().getLex() + ", 0\n";
                    salida += "JE errorDivisionPorCero\n";  //Si es igual a 0 la comparacion salta a la etiqueta errorDivisionPorCero
                    
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "DIV " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "MOV " + variable + ", EAX" + "\n";           //Cociente en EAX y resto en EDX
                    
                } else {
                    salida += "FLD " + getDer().getHojaPropia().getLex() + "\n"; //Pongo el divisor en ST
                    salida += "FSUB " + getDer().getHojaPropia().getLex() + "\n"; //Resto ST - divisor
                    salida += "FTST ";  // Comparo ST y 0
                    salida += "JE errorDivisionPorCero\n"; //Si FTST = 0 salto a error Division por cerow
                    
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FDIV " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "FST " + variable + "\n";
                }
                break;

            //Comparadores
            case "=":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JNE " + label + "\n"; //Si la comparacion no es igual salto a la etiqueta
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JNE " + label + "\n";
                }
                break;
                
            case "=!":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JE " + label + "\n"; //Si la comparacion es igual salto a la etiqueta
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JE " + label + "\n";
                }
                break;

            case ">":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n"; //Compara AEX cpm regder
                    salida += "JLE " + label + "\n"; //JLE es por menor igual (contrario a >)
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JLE " + label + "\n";
                }
                break; 
            
            case ">=":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n"; 
                    salida += "JL " + label + "\n"; //JL es por menor (contrario de >=)
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JL " + label + "\n";
                }
                break; 
            
            case "<":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n"; 
                    salida += "JGE " + label + "\n"; //JGE es por MAYOR O IGUAL (contrario de <)
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JGE " + label + "\n";
                }
                break; 
            
            case "<=":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n"; 
                    salida += "JG " + label + "\n"; //JG es por MAYOR (contrario de <=)
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JG " + label + "\n";
                }
                break; 

            //Sentencia
            case "Sentencia":
                salida+= getIzq().getAssembler() + getDer().getAssembler();
                break;

            case "IF":
                salidaDer =  getDer().getAssembler();
                if(!(getDer().getLex().equals("Cuerpo_IF"))){
                    label = getLabel();   //Caso en el que no hay else
                    pilaLabels.push(label);
                    salida+=getIzq().getAssembler() + salidaDer + label+":\n";  //Como no hya else, genero una etiqueta al final del assembler, entonces si la condicion da falsa saltas ahi.
                    break;
                }
                
                salida+= getIzq().getAssembler() + salidaDer; //cuerpo if se encargara de hacer las etiquetas
                
                break;

            case "Cuerpo_IF": //si hay cuerpo if quiere decir que hay un else -> loq ue se hace es agregar un label previo al cuerpo del else, para poder saltar en caos de condicion negativa
                labelFin = getLabel();
                pilaLabels.push(labelFin);
                String labelElse = getLabel();
                pilaLabels.push(labelElse); 

                salida+= getIzq().getAssembler();
                salida+= "JMP " + labelFin + "\n"; //salto incondicional al final si es que entro al then
                salida += labelElse + ":\n"; //salto al else si la condicion da falsa
                salida+= getDer().getAssembler()+ labelFin+":\n";
                
                break;
            
            case "Bloque Ejecutable":
                salida+= getIzq().getAssembler() + getDer().getAssembler();
                break;

            case "While":
                label = getLabel();
                pilaLabels.push(label);

                labelFin = getLabel();
                pilaLabels.push(labelFin); //lo chupa la condicion.
                pilaLabelsBreak.push(labelFin); //lo lee un posible break y lo popea abajo el mismo while
                
                salida += label + ":\n";
                salida += getIzq().getAssembler() + getDer().getAssembler();
                salida += "JMP "+ label + "\n";
                salida += labelFin + ":\n"; 

                pilaLabelsBreak.pop();
                pilaLabels.pop();

                break;
                
            case "While con Etiqueta":
                label = getIzq().getIzq().getLex(); //siempre que hay etiqueta se genera un nodo de control y debajo un nodo hoja con el nombre de la etiqueta a saltar
                salida+= label+":\n" ;
                salida+= getDer().getAssembler();
                break;
            
            case "While Asignacion":
                label = getLabel();
                
                salida += label + ":\n";
                salida += getIzq().getAssembler();
                salida += getDer().getAssembler();
                salida += "JMP "+ label + "\n";

                break;

            case "While como expresion":
                variable = getVariableAuxiliar(); //variable en donde va a volver el valor del break
                pilaVariablesAuxiliares.push(variable);
                
                labelFin = getLabel(); //label a donde saltar si existe un break
                pilaLabels.push(labelFin);
                pilaLabelsBreak.push(labelFin);

                labelElse = getLabel();
                pilaLabels.push(labelElse);
                
                salida+= getIzq().getAssembler();
                salida += labelElse + ":\n";
                salida += getDer().getAssembler();

                if(getDer().getHojaPropia().getTipo().equals("Entero")){
                    salida+= "MOV EAX , " + getDer().getHojaPropia().getLex() + "\n"; 
                    salida+= "MOV " + variable + ", " + "EAX" + "\n";
                }else{
                    salida += "FLD " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "FST " + variable + "\n";
                }

                salida += labelFin + ":\n";

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(getDer().getHojaPropia().getTipo()); //como siempre hay else, se toma el tipo de la variable derecha
                this.hojaPropia.setUso("variableAuxiliar"); 

                pilaVariablesAuxiliares.pop();
                break;

            case "Cuerpo - Asignacion":
                label = getLabel();
                pilaLabelsContinue.push(label);
                
                salida += getIzq().getAssembler();
                salida += label + ":\n"; //etiqueta del continue
                salida += getDer().getAssembler();
                
                pilaLabelsContinue.pop();
                break;

            case "Bloque Break con Continue":
                salida+=getIzq().getAssembler() + getDer().getAssembler();

                break;
            
            case "Bloque Ejecutable Asignacion":
                salida+=getIzq().getAssembler() + getDer().getAssembler();

                break;
            
            case "When":
                label = getLabel();
                pilaLabels.push(label);
                labelFin = getLabel();
                pilaLabels.push(labelFin);
                
                salida+= label + ":\n" + getIzq().getAssembler() + getDer().getAssembler()+ "JMP "+ pilaLabels.pop() + "\n" + labelFin +":\n";
                break;
                
            case "Continue":
                label = pilaLabelsContinue.peek();

                if(getDer().getLex().equals("Tag"))
                {
                    salida += getIzq().getAssembler();
                    salida += "JMP "+ getDer().getIzq().getLex()+"\n";
                }
                else
                {
                    salida += "JMP "+ label +"\n";
                }

                break;

        }
        return salida;
    }

    
    @Override
    public NodoHoja getHojaPropia() {
        return hojaPropia;
    }
}
