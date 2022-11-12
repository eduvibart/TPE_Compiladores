package GeneracionCodigoIntermedio;

public class NodoComun extends ArbolSintactico{

    private NodoHoja hojaPropia=null;
    public static int numeroVariable=0;
    private String variable;
    private String label;


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

    public static String getVariableAuxiliar(){
        numeroVariable++;
        return "@aux"+numeroVariable;
    }


    @Override
    public String getAssembler() {
        String salida = "";
        switch(super.getLex()){
            
            //Asignacion
            case "=:":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                if(getIzq().getTipo().equals("Entero")){
                    salida+= "MOV " + getIzq().getHojaPropia().getLex() + ", " + getDer().getHojaPropia().getLex() + "\n";
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
            //Comparador igual
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

            case ">":
                salida += getDer().getAssembler() + getIzq().getAssembler();
                variable = getVariableAuxiliar();
                label = pilaLabels.pop();

                this.hojaPropia = new NodoHoja(variable);
                this.hojaPropia.setTipo(this.getIzq().getTipo());
                this.hojaPropia.setUso("variableAuxiliar");

                if (getIzq().getHojaPropia().getTipo().equals("Entero")) {
                    salida += "MOV EAX, " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "CMP EAX, " + getDer().getHojaPropia().getLex() + "\n"; //Compara el lexema del derecho con el eax
                    salida += "JLE " + label + "\n"; //JLE es por menor igual (contrario a >)
                } else {
                    salida += "FLD " + getIzq().getHojaPropia().getLex() + "\n";
                    salida += "FCOM " + getDer().getHojaPropia().getLex() + "\n";
                    salida += "JLE " + label + "\n";
                }
                break;   

            //Sentencia
            case "Sentencia":
                salida+= getDer().getAssembler() + getIzq().getAssembler();
                break;

            case "IF":
                String salidaDer =  getDer().getAssembler();
                if(!(getDer().getLex().equals("Cuerpo_IF"))){
                    label = getLabel();   //Caso en el que no hay else
                    pilaLabels.push(label);
                    salida+=getIzq().getAssembler() + salidaDer + label+":\n";
                    break;
                }
                
                salida+= getIzq().getAssembler() + salidaDer;
                
                break;
            case "Cuerpo_IF": //si hay cuerpo if quiere decir que hay un else -> loq ue se hace es agregar un label previo al cuerpo del else, para poder saltar en caos de condicion negativa
                String labelFin = getLabel();
                pilaLabels.push(labelFin);
                String labelElse = getLabel();
                pilaLabels.push(labelElse); 

                salida+= getIzq().getAssembler()+ "JMP "+ labelFin+"\n" +labelElse+":\n"+ getDer().getAssembler()+ labelFin+":\n";
                
                break;

        }
        return salida;
    }

    
    @Override
    public NodoHoja getHojaPropia() {
        return hojaPropia;
    }
}
