package GeneracionCodigoIntermedio;

public class NodoComun extends ArbolSintactico{

    private NodoHoja hojaPropia=null;
    private String variable;
    private String label;
    private String labelFin;
    private String salidaDer;
    private String saltoBreak;


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
                    salida += "MUL EAX, " + getDer().getHojaPropia().getLex() + "\n";

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
                
                salidaDer =  getDer().getAssembler();
                
                saltoBreak="";
                if (!(pilaLabelsBreak.isEmpty())){
                    saltoBreak = pilaLabelsBreak.pop();
                }
                
                salida += label + ":\n";
                salida += getIzq().getAssembler() + salidaDer;

                if(!(saltoBreak.equals(""))){
                    salida+= saltoBreak + ":\n";
                }

                
                break;
                
            case "While con Etiqueta":
                label = getIzq().getIzq().getLex(); //siempre que hay etiqueta se genera un nodo de control y debajo un nodo hoja con el nombre de la etiqueta a saltar
                salida+= label+":\n" + getDer().getAssembler();
                break;
            
            case "While Asignacion":
                label = getLabel();
                pilaLabels.push(label);
                
                salidaDer =  getDer().getAssembler();
                
                
                salida += label + ":\n";
                salida += getIzq().getAssembler() + salidaDer;

                if(!(pilaLabelsBreak.isEmpty())){

                    this.hojaPropia= new NodoHoja(pilaLabelsBreak.pop());
                    this.hojaPropia.setTipo(pilaLabelsBreak.pop());
                    this.hojaPropia.setUso("variableAuxiliar");
                }

                break;

            case "While como expresion":
                salida+= getIzq().getAssembler() + getDer().getAssembler();
                
                if(getIzq().getHojaPropia() == null){

                    this.hojaPropia = new NodoHoja(getDer().getHojaPropia().getLex());
                    this.hojaPropia.setTipo(this.getDer().getTipo());
                    this.hojaPropia.setUso("variableAuxiliar");
                }
                else{
                    this.hojaPropia = new NodoHoja(getIzq().getHojaPropia().getLex());
                    this.hojaPropia.setTipo(this.getIzq().getTipo());
                    this.hojaPropia.setUso("variableAuxiliar");
                    saltoBreak = pilaLabelsBreak.pop();
                    salida+= saltoBreak + ":\n";

                }
                
                
                break;

            case "Cuerpo - Asignacion":
                salida += getIzq().getAssembler();

                String saltoAsignacion="";
                String tag="";
                if(!(pilaLabelsTags.isEmpty())){
                    saltoAsignacion = pilaLabelsTags.pop();
                    if(!(pilaLabelsTags.isEmpty())){
                        String aux = pilaLabelsTags.pop();
                        tag = saltoAsignacion;
                        saltoAsignacion=aux;
                    }
                    salida+= saltoAsignacion+":\n";
                } 

                salida += getDer().getAssembler();
                
                if(!(tag.equals(""))){
                    salida+= "JMP " +tag+"\n";
                }
                
                salida += "JMP " + pilaLabels.pop() + "\n"; 
                
                label = getLabel();
                pilaLabels.push(label);
                salida += label +":\n";
                
                break;

            case "Bloque Break con Continue":
                salida+=getIzq().getAssembler() + getDer().getAssembler();

                break;
            
            case "Bloque Ejecutable Asignacion":
                salida+=getIzq().getAssembler() + getDer().getAssembler();

                break;

            case "FOR":
                salida+= getIzq().getAssembler()+ getDer().getAssembler();

                saltoBreak="";
                if (!(pilaLabelsBreak.isEmpty())){
                    saltoBreak = pilaLabelsBreak.pop();
                }

                if(!(saltoBreak.equals(""))){
                    salida+= saltoBreak + ":\n";
                }
                break;
            case "For como expresion":
                salida+=getIzq().getAssembler() + getDer().getAssembler();

                this.hojaPropia = new NodoHoja(getDer().getHojaPropia().getLex());
                this.hojaPropia.setTipo(this.getDer().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                break;
            case "For con Etiqueta":
                label = getIzq().getIzq().getLex(); //siempre que hay etiqueta se genera un nodo de control y debajo un nodo hoja con el nombre de la etiqueta a saltar
                salida+= label+":\n" + getDer().getAssembler();
                break;

            case "Condicion-Cuerpo":
                labelFin = getLabel();
                pilaLabels.push(labelFin);
                label = getLabel();
                pilaLabels.push(label);

                salidaDer = getDer().getAssembler();
                salida+= label + ":\n" + getIzq().getAssembler() + salidaDer;
                salida+= labelFin + ":\n";
                break;
            
            case "Cuerpo":
                salida+= getIzq().getAssembler()+getDer().getAssembler();
                salida += "JMP "+ pilaLabels.pop()+"\n";

                break;

            case "Asignacion FOR":
                salida+= getIzq().getAssembler();
                break;
            
            case "When":
                label = getLabel();
                pilaLabels.push(label);
                labelFin = getLabel();
                pilaLabels.push(labelFin);
                
                salida+= label + ":\n" + getIzq().getAssembler() + getDer().getAssembler()+ "JMP "+ pilaLabels.pop() + "\n" + labelFin +":\n";
                break;

        }
        return salida;
    }

    
    @Override
    public NodoHoja getHojaPropia() {
        return hojaPropia;
    }
}
