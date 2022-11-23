%{
package Principal;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import GeneracionCodigoIntermedio.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

%}

%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE CONTINUE ID I32 F32 PARENT_A PARENT_C COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C DIST ASIG CADENA CONST SUMA RESTA MULT DIV ENTERO FLOAT

%start program 

%left SUMA RESTA
%left MULT DIV

%% 
program : nombre_program LLAVE_A bloque_sentencias LLAVE_C {raiz = new NodoControl("PROGRAMA",(ArbolSintactico)$3);  TablaSimbolos.removeAtributo($1.sval);}
        | nombre_program LLAVE_A bloque_sentencias error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba }");}
        | nombre_program error bloque_sentencias LLAVE_C{ $$=new NodoHoja("Error sintactico"); yyerror("Se esperaba {");}
        | error LLAVE_A bloque_sentencias LLAVE_C {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba el nombre del programa");}
;
nombre_program : ID 
;
bloque_sentencias :{$$=new NodoHoja("Fin");}
                | bloque_sentencias sentencia PUNTOCOMA {$$=new NodoComun("Sentencia", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | bloque_sentencias sentencia error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ;"); }
;
sentencia : sentencia_declarativa {$$=$1;}
        | sentencia_ejecutable {$$ = $1;}
;
sentencia_declarativa : sentencia_decl_datos {$$= new NodoHoja("Sentencia Declarativa");}
                        | sentencia_decl_fun {$$= new NodoHoja("Sentencia Declarativa");}
                        | lista_const {$$= new NodoHoja("Sentencia Declarativa");} 
                        | sentencia_when {$$=$1;}
;
tipo : I32 {$$ = new NodoHoja("Entero"); ((NodoHoja)$$).setTipo("Entero");}
     | F32 {$$ = new NodoHoja("Float");((NodoHoja)$$).setTipo("Float");}
;

sentencia_decl_datos : tipo list_var { 
                                        for (String s : ((NodoTipos)$2).getList()){
                                                String ambito = ambitoActual;
                                                while(TablaSimbolos.existeSimbolo(s+"@"+ambito)){
                                                        if(ambito.equals("Global")){
                                                                yyerror("La variable " + s + " ya se encuentra declarada en el ambito " + ambitoActual);
                                                                ambito = "";
                                                                break;
                                                        }else{
                                                                char [] a = ambito.toCharArray();
                                                                for (int i = a.length;i>=0;i--){
                                                                        if(a[i-1] == ':'){
                                                                        ambito = ambito.substring(0,i-1);
                                                                        break;
                                                                        }
                                                                }
                                                        }
                                                }  
                                                if(ambito.equals(ambitoActual)){
                                                        if (!stackWhen.empty()){
                                                                List<String> tope=stackWhen.pop();
                                                                tope.add(s+"@"+ambito);
                                                                tope.add(s);
                                                                stackWhen.push(tope);
                                                        }
                                                        TablaSimbolos.addNuevoSimbolo(s+"@"+ambito);
                                                        TablaSimbolos.addAtributo(s+"@"+ambito,"Id",TablaSimbolos.getAtributo(s,"Id"));
                                                        TablaSimbolos.addAtributo(s+"@"+ambito,"Tipo",((ArbolSintactico) $1).getTipo());
                                                        TablaSimbolos.addAtributo(s+"@"+ambito,"Linea",AnalizadorLexico.getLineaAct());
                                                        TablaSimbolos.addAtributo(s+"@"+ambito,"Uso","Variable");
                                                        TablaSimbolos.removeAtributo(s);
                                                }
                                        }
                                        $$ = $2;
                                }
        | tipo error {$$=new NodoHoja("Error sintactico"); yyerror("se esperaba lista de identificadores entre comas");}
;
list_var : list_var COMA ID {$$=$1;((NodoTipos)$$).add((String)$3.sval);}
        |  ID {$$=new NodoTipos((String)$1.sval);}
;
encabezado_fun  : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo {      
                                $$ = new NodoHoja($2.sval);
                                if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                        ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$9).getTipo());
                                        TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                        if (!stackWhen.empty()){
                                                List<String> tope=stackWhen.pop();
                                                tope.add($2.sval+ "@" + ambitoActual);
                                                stackWhen.push(tope);
                                        }   
                                        TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                        TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                        TablaSimbolos.removeAtributo($2.sval);
                                        TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$9).getTipo());
                                        TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro1",((ArbolSintactico)$4).getLex() + "@"+ ambitoActual +"@"+ $2.sval);
                                        TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro2",((ArbolSintactico)$6).getLex()+ "@"+ ambitoActual +"@"+ $2.sval);
                                        ambitoActual += "@"+$2.sval;
                                        if (!stackWhen.empty()){
                                                List<String> tope=stackWhen.pop();
                                                tope.add(((ArbolSintactico)$4).getLexemaWhen() + "@"+ ambitoActual);
                                                tope.add(((ArbolSintactico)$6).getLexemaWhen() + "@"+ ambitoActual);
                                                stackWhen.push(tope);
                                        }
                                        cambiarTipoActual(((ArbolSintactico)$9).getTipo());
                                        //Agrego los parametros en la tabla de simbolos
                                        TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual);
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$4).getTipo());
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Uso","Variable");
                                        TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual);
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$6).getTipo());
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual,"Uso","Variable");
                                        hayReturn.push(false);
                                }else{
                                        yyerror("El identificador " + $2.sval + " ya esta usado en el ambito " + ambitoActual);
                                        ambitoActual += "@"+$2.sval;
                                }
                        }
                | FUN ID PARENT_A parametro  PARENT_C DOSPUNTOS tipo {
                        $$ = new NodoHoja($2.sval);
                        if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$7).getTipo());
                                TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                TablaSimbolos.removeAtributo($2.sval);
                                TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$7).getTipo());
                                TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro1", ((ArbolSintactico)$4).getLex()+ "@"+ ambitoActual +"@"+ $2.sval);
                                if (!stackWhen.empty()){
                                        List<String> tope=stackWhen.pop();
                                        tope.add($2.sval+ "@" + ambitoActual);
                                        stackWhen.push(tope);
                                }
                                ambitoActual += "@"+$2.sval;
                                if (!stackWhen.empty()){
                                        List<String> tope=stackWhen.pop();
                                        tope.add(((ArbolSintactico)$4).getLexemaWhen() + "@"+ ambitoActual);
                                        stackWhen.push(tope);
                                }
                                cambiarTipoActual(((ArbolSintactico)$7).getTipo());
                                //Agrego el parametro en la tabla de simbolos
                                TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual);
                                TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$4).getTipo());
                                TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Uso","Variable");
                                hayReturn.push(false);

                        }else{
                                yyerror("El identificador " + $2.sval + " ya esta usado en el ambito " + ambitoActual);
                                ambitoActual += "@"+$2.sval;
                        }
                }
                | FUN ID PARENT_A PARENT_C DOSPUNTOS tipo {
                        $$ = new NodoHoja($2.sval);
                        if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$6).getTipo());
                                TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                TablaSimbolos.removeAtributo($2.sval);
                                TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$6).getTipo());
                                if (!stackWhen.empty()){
                                        List<String> tope=stackWhen.pop();
                                        tope.add($2.sval+ "@" + ambitoActual);
                                        stackWhen.push(tope);
                                }
                                ambitoActual += "@"+$2.sval;
                                cambiarTipoActual(((ArbolSintactico)$6).getTipo());
                                hayReturn.push(false);
                        }else{
                                yyerror("El identificador " + $2.sval + " ya esta usado en el ambito " + ambitoActual);
                                ambitoActual += "@"+$2.sval;
                        }
                }
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error  { $$=new NodoHoja("Error sintactico");  ambitoActual += "@"+"Error";yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS error  {$$=new NodoHoja("Error sintactico");  ambitoActual += "@"+"Error";yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS error  { $$=new NodoHoja("Error sintactico");   ambitoActual += "@"+"Error"; yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C error {$$=new NodoHoja("Error sintactico");  ambitoActual += "@"+"Error"; yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro PARENT_C error {$$=new NodoHoja("Error sintactico"); ambitoActual += "@"+"Error";yyerror("Se esperaba :");}
                | FUN ID PARENT_A PARENT_C error {$$=new NodoHoja("Error sintactico"); ambitoActual += "@"+"Error";  yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro COMA parametro error {$$=new NodoHoja("Error sintactico"); ambitoActual += "@"+"Error"; yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro error {$$=new NodoHoja("Error sintactico");  ambitoActual += "@"+"Error"; yyerror("Se esperaba )");}
                | FUN ID PARENT_A error {$$=new NodoHoja("Error sintactico");  ambitoActual += "@"+"Error"; yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro COMA error {$$=new NodoHoja("Error sintactico"); ambitoActual += "@"+"Error";yyerror("Se esperaba otro parametro");}
                | FUN ID error {$$=new NodoHoja("Error sintactico"); ambitoActual += "@"+"Error"; yyerror("Se esperaba (");}
                | FUN error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba un nombre de funcion");}
;
sentencia_decl_fun : encabezado_fun LLAVE_A cuerpo_fun LLAVE_C  {
                                if(!hayReturn.empty() && hayReturn.pop() == true){
                                        char [] a = ambitoActual.toCharArray();
                                        for (int i = a.length;i>=0;i--){
                                                if(a[i-1] == '@'){
                                                        ambitoActual = ambitoActual.substring(0,i-1);
                                                        break;
                                                }
                                        }
                                        NodoControl n = new NodoControl(((ArbolSintactico)$1).getLex()+"@"+ambitoActual,(ArbolSintactico)$3);
                                        n.setTipo(((ArbolSintactico)$1).getTipo());
                                        $$ = new NodoControl("Funcion",n);
                                        removeTipoActual();
                                        funciones.add((ArbolSintactico)$$);
                                        if (!stackWhen.empty()){
                                                List<String> tope=stackWhen.pop();
                                                tope.add("@aux@"+((ArbolSintactico)$1).getLex()+"@"+ambitoActual);
                                                stackWhen.push(tope);
                                        }
                                        NodoHoja varAux = (new NodoHoja("@aux@"+((ArbolSintactico)$1).getLex()+"@"+ambitoActual));
                                        varAux.setTipo(n.getTipo());
                                        varAux.setUso("variableAuxiliar");
                                }else{
                                        yyerror("La funcion " + ((ArbolSintactico)$1).getLex() + " no cuenta con ningun retorno." );
                                }
}
                | encabezado_fun LLAVE_A cuerpo_fun error{$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | encabezado_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba {");}
;
cuerpo_fun :    {$$=new NodoHoja("Fin");}
                | cuerpo_fun sentencias_fun PUNTOCOMA {$$=new NodoComun("Sentencia", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | cuerpo_fun sentencias_fun error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ;");}
;
sentencias_fun :  sentencia_decl_datos {$$=new NodoHoja("Sentencia Declarativa Datos");}
                | sentencia_decl_fun {$$=new NodoHoja("Sentencia Declarativa Funcion");}
                | lista_const  {$$= new NodoHoja("Sentencia Declarativa Constante");}
                | asignacion {$$ = $1;}
                | llamado_func {$$=$1;}
                | sentencia_if_fun {$$=$1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_fun {$$ = $1;}
                | sentencia_while_fun {$$=$1;}
                | retorno {$$=$1;}
;
sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun PUNTOCOMA END_IF { $$= new NodoComun("IF",new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then",(ArbolSintactico) $6),new NodoControl("Else", (ArbolSintactico)$9)));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C PUNTOCOMA ELSE sentencias_fun PUNTOCOMA END_IF {$$= new NodoComun("IF",new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then",(ArbolSintactico) $7),new NodoControl("Else", (ArbolSintactico)$11)));}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun LLAVE_C PUNTOCOMA END_IF {$$= new NodoComun("IF",new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then",(ArbolSintactico) $6),new NodoControl("Else", (ArbolSintactico)$10)));}       
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA END_IF { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$6));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF { $$= new NodoComun("IF",new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then",(ArbolSintactico) $7),new NodoControl("Else", (ArbolSintactico)$11)));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba (");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE sentencias_fun error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ; luego de la sentencia");}
; 
sentencia_when_fun: encabezado_when THEN LLAVE_A cuerpo_fun LLAVE_C {
        $$=$1;
        if (!((ArbolSintactico)$1).getLex().equals("No cumple condicion when")){
                ((ArbolSintactico)$1).setIzq((ArbolSintactico)$4);
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        if (!stackWhen.empty()){
                                List<String> whenSuperior=stackWhen.pop();
                                for(String cadena :tope){
                                        whenSuperior.add(cadena);
                                }
                                stackWhen.push(whenSuperior);
                        }
                }

        } else if (!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        for(String cadena :tope){
                                TablaSimbolos.removeAtributo(cadena);
                        }
                }
        }
}
        
| encabezado_when THEN sentencias_fun {
        $$=$1;
        if (!((ArbolSintactico)$1).getLex().equals("No cumple condicion when")){
                ((ArbolSintactico)$1).setIzq((ArbolSintactico)$3);
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        if (!stackWhen.empty()){
                                List<String> whenSuperior=stackWhen.pop();
                                for(String cadena :tope){
                                        whenSuperior.add(cadena);
                                }
                                stackWhen.push(whenSuperior);
                        }
                }
        }else if (!((ArbolSintactico)$1).getLex().equals("Error sintactico")){ 
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        for(String cadena :tope){
                                TablaSimbolos.removeAtributo(cadena);
                        }
                }
        }
}
                | encabezado_when THEN LLAVE_A cuerpo_fun error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba }");}
                | encabezado_when THEN  error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba {");}
                | encabezado_when error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba then");}
; 
etiqueta : ID DOSPUNTOS {
                                $$ = new ParserVal($1.sval);
                                if(!TablaSimbolos.existeSimbolo($1.sval+ "@" + ambitoActual)){
                                        TablaSimbolos.addNuevoSimbolo($1.sval+ "@" + ambitoActual);
                                        TablaSimbolos.addAtributo($1.sval+ "@" + ambitoActual,"Uso","Etiqueta");
                                        TablaSimbolos.addAtributo($1.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($1.sval,"Id"));
                                        TablaSimbolos.removeAtributo($1.sval);
                                        etiquetasAct.add($1.sval + "@" + ambitoActual);
                                }else{
                                        yyerror("El identificador " + $1.sval + " ya esta usado en el ambito " + ambitoActual);
                                }
                        }
;
sentencia_while_fun : encabezado_while_etiqueta LLAVE_A cuerpo_fun_break LLAVE_C {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().getDer().setIzq((ArbolSintactico)$3);
                                String tag = ((ArbolSintactico)$1).getIzq().getIzq().getLex();
                                List<ArbolSintactico> l = mapEtiquetas.get(tag);
                                if(l!=null){
                                        for(ArbolSintactico a : l){
                                                a.setIzq(((ArbolSintactico)$1).getDer().getDer().getDer());
                                        }
                                }
                        }
                        $$ = $1;
                }
                | encabezado_while_etiqueta sentencias_fun_break {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().getDer().setIzq((ArbolSintactico)$2);
                                String tag = ((ArbolSintactico)$1).getIzq().getIzq().getLex();
                                List<ArbolSintactico> l = mapEtiquetas.get(tag);
                                if(l!=null){
                                        for(ArbolSintactico a : l){
                                                a.setIzq(((ArbolSintactico)$1).getDer().getDer().getDer());
                                        }
                                }
                        }
                        $$ = $1;
                        
                }
                | encabezado_while LLAVE_A cuerpo_fun_break LLAVE_C {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$3);
                        }
                        $$ = $1;
                }
                | encabezado_while sentencias_fun_break {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$2);
                        }
                        $$ = $1;
                }
;

cuerpo_fun_break : {$$=new NodoHoja("Fin");}
                | cuerpo_fun_break sentencias_fun_break PUNTOCOMA {$$=new NodoComun("Sentencia_Break", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | cuerpo_fun_break sentencias_fun_break error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ;");}
;
sentencias_fun_break :   asignacion  {$$ = $1;}
                | sentencia_if_break_fun  {$$ = $1;}
                | sentencia_out  {$$ = $1;}
                | sentencia_while_fun  {$$ = $1;}
                | CONTINUE tag {        boolean b = false;
                                        if(((ArbolSintactico)$2).getIzq()!=null){
                                                String tag = ((ArbolSintactico)$2).getIzq().getLex() + "@" + ambitoActual;
                                                for(String s : etiquetasAct){
                                                        if( tag.equals(s)){
                                                                b = true;
                                                                break;
                                                        }
                                                }
                                                if(!b){
                                                        yyerror("No se puede saltar al tag " + ((ArbolSintactico)$2).getIzq().getLex());
                                                        $$ = new NodoHoja("Error");
                                                }else{
                                                        $$ = new NodoComun("Continue",new NodoHoja("Fin"),(ArbolSintactico)$2);
                                                        if(mapEtiquetas.containsKey(((ArbolSintactico)$2).getIzq().getLex())){
                                                                mapEtiquetas.get(((ArbolSintactico)$2).getIzq().getLex()).add((ArbolSintactico)$$);
                                                        }
                                                }
                                        }else{
                                                $$ = new NodoComun("Continue",new NodoHoja("Fin"),(ArbolSintactico)$2);
                                        }
                                }
                | BREAK {$$ = new NodoHoja("Break");}
                | BREAK cte {$$ = new NodoControl("Break", new NodoHoja($2.sval));}
                | retorno {$$=$1;}
;
sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break PUNTOCOMA END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break PUNTOCOMA END_IF {$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF {$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA END_IF  {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF  {$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C END_IF  {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ( ");}
;
retorno : RETURN PARENT_A expresion PARENT_C {$$ = new NodoControl("Retorno", (ArbolSintactico)$3);
                                                String tipoRet = ((ArbolSintactico)$3).getTipo();
                                                if(!ambitoActual.equals("Global")){
                                                        String tipoFun = getTipoActual();
                                                        if(!tipoRet.equals(tipoFun)){
                                                                yyerror("El retorno debe ser del mismo tipo que el retorno de la funcion.");
                                                        }else{
                                                                if (!hayReturn.empty()){
                                                                        hayReturn.pop();
                                                                        hayReturn.push(true);
                                                                }
                                                        }
                                                }else{
                                                        yyerror("El retorno puede estar solo dentro de una funcion.");
                                                }
                                        }
                        | RETURN PARENT_A expresion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba )");}
                        | RETURN PARENT_A error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba expresion");}
                        | RETURN error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba expresion entre ( )");}
;
parametro : tipo ID{    $$ = new NodoHoja($2.sval);
                        ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                        TablaSimbolos.removeAtributo($2.sval);}
        | tipo error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba identificador");}
;
lista_const : CONST lista_asignacion 
;
lista_asignacion : lista_asignacion COMA asignacion_const 
        | asignacion_const
;
asignacion_const : ID ASIG cte { 
                                if(TablaSimbolos.existeSimbolo($1.sval+"@"+ambitoActual)){
                                        yyerror("La variable " + $1.sval + " ya se encuentra declarada en el ambito " + ambitoActual);
                                }else{
                                        TablaSimbolos.addNuevoSimbolo($1.sval+"@"+ambitoActual);
                                        if (!stackWhen.empty()){
                                                List<String> tope=stackWhen.pop();
                                                tope.add($1.sval+ "@" + ambitoActual);
                                                tope.add($3.sval);
                                                stackWhen.push(tope);
                                        }
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Id",TablaSimbolos.getAtributo($1.sval,"Id"));
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Tipo",TablaSimbolos.getAtributo($3.sval,"Tipo"));
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Linea",AnalizadorLexico.getLineaAct());
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Uso","Constante");
                                        if (TablaSimbolos.getAtributo($3.sval,"Tipo").equals("Entero")){
                                                TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Valor", Long.valueOf($3.sval));
                                        }else{
                                                TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Valor", Double.parseDouble((String)TablaSimbolos.getAtributo($3.sval,"Valor")));
                                        }
                                        TablaSimbolos.removeAtributo($1.sval);
                                }
                        }
        | ID ASIG error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una constante");}
        | ID error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba una asignacion =:");}
;
sentencia_ejecutable : asignacion {$$ = $1;}
        | sentencia_if   {$$ = $1; }
        | sentencia_out {$$ = $1;}
        | sentencia_while {$$ = $1;}
        | llamado_func{$$=$1;}
;
asignacion : ID ASIG expresion  {
                                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                                        NodoHoja hoja = new NodoHoja($1.sval+"@"+ambito);
                                        $$ = (ArbolSintactico) new NodoComun($2.sval, hoja , (ArbolSintactico) $3);
                                        String tipoS1 = (String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo");
                                        String tipoS3 = ((ArbolSintactico)$3).getTipo();
                                        if(!ambito.equals("")){
                                                if(!(tipoS1.equals(tipoS3))){
                                                        yyerror("No se puede realizar una asignacion con tipos diferentes.");
                                                }
                                                else if(((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Variable")){
                                                        ((ArbolSintactico)$$).setTipo(tipoS1);
                                                        hoja.setUso((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso"));
                                                        hoja.setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                                }
                                                else {
                                                        yyerror($1.sval+" no es una variable.");
                                                }
                                        
                                        }else {
                                                yyerror($1.sval+" no esta declarada");
                                        }
                }
                | ID error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba =:");}
                | ID ASIG error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba expresion");}                  
;
expresion: expresion SUMA termino {     
                                        $$ = (ArbolSintactico) new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);

                                        if(!(((ArbolSintactico)$1).getTipo().equals(((ArbolSintactico)$3).getTipo()))){
                                                yyerror("No se puede realizar una suma con diferentes tipos.");
                                        }else{
                                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                                        }
                                 }
        | expresion RESTA termino {
                                        $$ = (ArbolSintactico) new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                        
                                        if(!(((ArbolSintactico)$1).getTipo().equals(((ArbolSintactico)$3).getTipo()))){
                                                yyerror("No se puede realizar una resta con diferentes tipos.");
                                        }else{
                                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                                        }
                                 }
        | termino {$$ = $1;} 
        | sentencia_while_asig ELSE cte {
                                        NodoHoja cte = new NodoHoja($3.sval);
                                        cte.setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo"));
                                        $$ = new NodoComun("While como expresion",(ArbolSintactico)$1,cte);
                                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo")); 
                                        }
        | expresion SUMA error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba un termino");}
        | expresion RESTA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba un termino");}
        | sentencia_while_asig ELSE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba un constante");}
;
termino: termino MULT factor  { 
                                        $$ = (ArbolSintactico) new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                        if(!(((ArbolSintactico)$1).getTipo().equals(((ArbolSintactico)$3).getTipo()))){
                                                yyerror("No se puede realizar una multiplicacion con diferentes tipos.");
                                        }else{
                                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                                        }
                                }   
        | termino DIV factor 
                                {
                                        $$ = new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                        if(!(((ArbolSintactico)$1).getTipo().equals(((ArbolSintactico)$3).getTipo()))){
                                                yyerror("No se puede realizar una division con diferentes tipos.");
                                        }else{
                                                ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                                        }
                                }   
        | factor {$$ = $1;}  
        | termino MULT error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba un factor");}
        | termino DIV error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba un factor");}
;
factor: ID {
                String ambito = buscarAmbito(ambitoActual,$1.sval);
                if((!ambito.equals(""))){
                        if(((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Variable")
                        || ((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Constante")){
                                $$ = new NodoHoja($1.sval+"@"+ambito);
                                TablaSimbolos.removeAtributo($1.sval);
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                ((ArbolSintactico)$$).setUso((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso"));
                        }
                        else{
                                yyerror($1.sval+" no es una variable");
                                $$ = new NodoHoja("Error");
                        }
                }else{
                        yyerror($1.sval+" no fue declarada");
                        $$ = new NodoHoja("Error");
                }
           }                                                          
        | cte {
                $$ = new NodoHoja($1.sval);
                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval,"Tipo"));
                ((ArbolSintactico)$$).setUso("Constante");
                TablaSimbolos.addAtributo($1.sval, "Uso","Constante");
              }  
        | llamado_func {$$=$1;}
;
cte : ENTERO {  
                chequearRangoI32($1.sval);
                TablaSimbolos.addNuevoSimbolo((String)$1.sval);
                TablaSimbolos.addAtributo($1.sval, "Uso", "Constante");
                TablaSimbolos.addAtributo($1.sval, "Tipo", "Entero");
                TablaSimbolos.addAtributo($1.sval, "Valor", (String)$1.sval);
                if (!stackWhen.empty()){
                        List<String> lista=stackWhen.pop();
                        lista.add($1.sval);
                        stackWhen.push(lista);
                }

        }
        | FLOAT {  
                TablaSimbolos.addNuevoSimbolo((String)$1.sval);
                TablaSimbolos.addAtributo($1.sval, "Uso", "Constante");
                TablaSimbolos.addAtributo($1.sval, "Tipo", "Float");
                TablaSimbolos.addAtributo($1.sval, "Valor", calcularFloat($1.sval));
                if (!stackWhen.empty()){
                        List<String> lista=stackWhen.pop();
                        lista.add($1.sval);
                        stackWhen.push(lista);
                }
        }
        | RESTA ENTERO {
                $$=new ParserVal($1.sval+$2.sval);
                TablaSimbolos.addNuevoSimbolo((String)$1.sval+$2.sval);
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Uso", "Constante");
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Tipo", "Entero");
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Valor", (String)$1.sval+$2.sval);
                if (!stackWhen.empty()){
                        List<String> lista=stackWhen.pop();
                        lista.add($1.sval+$2.sval);
                        stackWhen.push(lista);
                }
        }
        | RESTA FLOAT {
                $$=new ParserVal($1.sval+$2.sval);
                TablaSimbolos.addNuevoSimbolo((String)$1.sval+$2.sval);
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Uso", "Constante");
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Tipo", "Float");
                TablaSimbolos.addAtributo($1.sval+$2.sval, "Valor", $1.sval+calcularFloat($2.sval));
                if (!stackWhen.empty()){
                        List<String> lista=stackWhen.pop();
                        lista.add($1.sval+$2.sval);
                        stackWhen.push(lista);
                }
        }
;

sentencia_while_asig: encabezado_while LLAVE_A bloque_sent_eje_asig LLAVE_C {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$3);
                        }
                        ((ArbolSintactico)$1).setLex("While Asignacion");
                        $$=$1;
                } 
                | encabezado_while sent_eje_asig {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$2);
                        }
                        ((ArbolSintactico)$1).setLex("While Asignacion");
                        $$=$1;
                } 
                | encabezado_while LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba }");}
;
sentencia_if_asig:IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF{$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig PUNTOCOMA END_IF{$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig PUNTOCOMA END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA END_IF {$$ = new NodoComun("IF",new NodoControl("Condicion", (ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF {$$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE error{$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ( ");}
;
bloque_sent_eje_asig: {$$=new NodoHoja("Fin");}
                        | bloque_sent_eje_asig sent_eje_asig PUNTOCOMA {$$=new NodoComun("Bloque Ejecutable Asignacion", (ArbolSintactico) $1, (ArbolSintactico) $2);}
;
sent_eje_asig:  asignacion {$$ = $1;}
                | sentencia_if_asig {$$ = $1;}
                | sentencia_out {$$ = $1;}
                | sentencia_while_asig {$$ = $1;}
                | BREAK cte {NodoHoja cte = new NodoHoja($2.sval);
                        cte.setTipo((String)TablaSimbolos.getAtributo($2.sval,"Tipo"));
                        $$ = new NodoControl("Break", cte);}
;
sentencia_if : IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF{ $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10)));  }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable PUNTOCOMA END_IF{ $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF",new NodoControl("Condicion", (ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF {                                              $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ( ");}
;
condicion : expresion comparacion expresion {
                        $$= new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                        ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                        ((ArbolSintactico)$$).setUso("Condicion");
                        if (!((((ArbolSintactico)$1).getTipo()).equals(((ArbolSintactico)$3).getTipo()))){
                               yyerror("error en la comparacion entre expresiones de distintos tipos");
                        }
                }
        | expresion comparacion error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba otra expresion para comparar.");}
        | expresion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba un tipo de comparacion.");}
;
comparacion: IGUAL {$$= $1;}
        | MAYOR {$$= $1;}
        | MENOR {$$= $1;}
        | MENORIGUAL {$$= $1;}
        | MAYORIGUAL {$$= $1;}
        | DIST {$$=$1;}
;
bloque_ejecutable : {$$=new NodoHoja("Fin");}
                | bloque_ejecutable sentencia_ejecutable PUNTOCOMA {$$=new NodoComun("Bloque Ejecutable", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | bloque_ejecutable sentencia_ejecutable {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba ;");}
;
sentencia_out : OUT PARENT_A CADENA PARENT_C {  $$ = new NodoControl($1.sval, (ArbolSintactico) new NodoHoja($3.sval));}
                |  OUT PARENT_A CADENA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba )");}
                |  OUT PARENT_A error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba una CADENA");}
                | OUT error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba (");}
;
encabezado_when : WHEN PARENT_A factor comparacion factor PARENT_C{
                String atributoIzq=((ArbolSintactico)$3).getLexemaWhen();
                String atributoDer=((ArbolSintactico)$5).getLexemaWhen();
                if (!TablaSimbolos.existeSimbolo(atributoIzq)||!TablaSimbolos.existeSimbolo(atributoDer)){
                        yyerror("Identificador en la condicion no declarado");
                        $$ = new NodoHoja("Error en el when");
                }
                else if (!TablaSimbolos.getAtributo(atributoIzq, "Uso" ).equals("Constante")){
                        yyerror(atributoIzq+" no es una constante");
                        $$ = new NodoHoja("Error en el when");

                }else if (!TablaSimbolos.getAtributo(atributoDer, "Uso" ).equals("Constante")){
                        yyerror(atributoDer+" no es una constante");
                        $$ = new NodoHoja("Error en el when");
                }
                else if (!TablaSimbolos.getAtributo(atributoIzq, "Tipo" ).equals(TablaSimbolos.getAtributo(atributoDer, "Tipo" ))){
                        yyerror("Los valores de la condicion del when son de tipos diferentes");
                        $$ = new NodoHoja("Error en el when");
                }else{
                        String s1 =TablaSimbolos.getAtributo(atributoIzq, "Valor")+"";
                        String s2 =TablaSimbolos.getAtributo(atributoDer, "Valor")+"";
                        double valorIzq = Double.parseDouble(s1);
                        double valorDer = Double.parseDouble(s2);
                        switch ($4.sval){
                                case "=":
                                        if (valorIzq==valorDer){
                                                
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when"); 
                                               
                                        }
                                        break;
                                case "<":
                                        if (valorIzq < valorDer){
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when");
                                        }
                                        break;
                                case ">":
                                        if (valorIzq > valorDer){
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when");
                                               }
                                        break;
                                case "=!":
                                        if (valorIzq != valorDer){
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when");
                                               }
                                        break;
                                case "<=":
                                        if (valorIzq <= valorDer){
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when");
                                        }
                                        break;
                                case ">=":
                                        if (valorIzq >= valorDer){
                                                $$ = new NodoControl("When", new NodoHoja("When sin sentencias"));
                                        }else{
                                               $$ = new NodoHoja("No cumple condicion when");
                                               }
                                        break;
                        
                        } 
                        List<String> whenActual = new ArrayList<String>();
                        stackWhen.push(whenActual);
                }
        }
                | WHEN PARENT_A factor comparacion factor  error{
                        $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");
                }
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion en el when");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( en el when");}
;
sentencia_when : encabezado_when THEN LLAVE_A bloque_sentencias LLAVE_C {
        $$=$1;
        if (!((ArbolSintactico)$1).getLex().equals("No cumple condicion when"))
        {
                ((ArbolSintactico)$1).setIzq((ArbolSintactico)$4);
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        if (!stackWhen.empty()){
                                List<String> whenSuperior=stackWhen.pop();
                                for(String cadena :tope){
                                        whenSuperior.add(cadena);
                                }
                                stackWhen.push(whenSuperior);
                        }
                }
        }else if (!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                if (!stackWhen.empty()){
                        List<String> tope=stackWhen.pop();
                        for(String cadena :tope){
                                TablaSimbolos.removeAtributo(cadena);
                        }
                }
        }
}
        | encabezado_when THEN sentencia {
                $$=$1;
                if (!((ArbolSintactico)$1).getLex().equals("No cumple condicion when"))
                {
                        ((ArbolSintactico)$1).setIzq((ArbolSintactico)$3);
                        if (!stackWhen.empty()){
                                List<String> tope=stackWhen.pop();
                                if (!stackWhen.empty()){
                                        List<String> whenSuperior=stackWhen.pop();
                                        for(String cadena :tope){
                                                whenSuperior.add(cadena);
                                        }
                                        stackWhen.push(whenSuperior);
                                }
                        }
                }else  if (!((ArbolSintactico)$1).getLex().equals("Error sintactico")){   
                        if (!stackWhen.empty()){
                                List<String> tope=stackWhen.pop();
                                for(String cadena :tope){
                                        TablaSimbolos.removeAtributo(cadena);
                                }
                        }
                        
                }
        }
                | encabezado_when THEN LLAVE_A bloque_sentencias error{
                        $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");
                }
                | encabezado_when THEN  error{
                        $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");
                }
                | encabezado_when error{
                        $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then");
                }
;
encabezado_while_etiqueta:  etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C { 
                        $$ = new NodoComun("While con Etiqueta",(ArbolSintactico) new NodoControl("Etiqueta", (ArbolSintactico) new NodoHoja($1.sval)) , (ArbolSintactico) new NodoComun("While", (ArbolSintactico) $4, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", new NodoHoja("Fin"), (ArbolSintactico) $8)) );
                        mapEtiquetas.put($1.sval,new ArrayList<ArbolSintactico>());
                        }
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba )");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba una asignacion");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba (");}
                | etiqueta WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba :");}
                | etiqueta WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba )");}
                | etiqueta WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una condicion");}
                | etiqueta WHILE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba (");}
;
encabezado_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C{
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", new NodoHoja("Fin"), (ArbolSintactico) $7) );      
                        }
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba )");}
                | WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una condicion");}
                | WHILE error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba (");}
;
sentencia_while : encabezado_while_etiqueta LLAVE_A bloque_break_continue LLAVE_C {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                String tag = ((ArbolSintactico)$1).getIzq().getIzq().getLex();
                                List<ArbolSintactico> l = mapEtiquetas.get(tag);
                                ((ArbolSintactico)$1).getDer().getDer().setIzq((ArbolSintactico)$3);
                                if(l!=null){
                                        for(ArbolSintactico a : l){
                                                a.setIzq(((ArbolSintactico)$1).getDer().getDer().getDer());
                                        }
                                }
                        }
                        $$=$1;
                }
                | encabezado_while_etiqueta ejecutables_break_continue {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                String tag = ((ArbolSintactico)$1).getIzq().getIzq().getLex();
                                List<ArbolSintactico> l = mapEtiquetas.get(tag);
                                ((ArbolSintactico)$1).getDer().getDer().setIzq((ArbolSintactico)$2);
                                if(l!=null){
                                        for(ArbolSintactico a : l){
                                                a.setIzq(((ArbolSintactico)$1).getDer().getDer().getDer());
                                        }
                                }
                        }
                        $$ = $1;
                }
                |  encabezado_while LLAVE_A bloque_break_continue LLAVE_C{
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$3);
                        }
                        $$ = $1;
                }
                | encabezado_while ejecutables_break_continue {
                        if(!((ArbolSintactico)$1).getLex().equals("Error sintactico")){
                                ((ArbolSintactico)$1).getDer().setIzq((ArbolSintactico)$2);
                        }
                        $$ = $1;
                }
                |encabezado_while LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba }");}
                | encabezado_while error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba {");}
;
bloque_break_continue : {$$=new NodoHoja("Fin");}
        | bloque_break_continue ejecutables_break_continue PUNTOCOMA { $$ = new NodoComun("Bloque Break con Continue",(ArbolSintactico) $1, (ArbolSintactico) $2);}
        | bloque_break_continue ejecutables_break_continue {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba ;");}
;

ejecutables_break_continue :  asignacion {$$ = $1;}
                | sentencia_if_break {$$ = $1;}
                | sentencia_out {$$ = $1;}
                | sentencia_while {$$ = $1;}
                | CONTINUE tag {boolean b = false;
                                        if(((ArbolSintactico)$2).getIzq()!=null){
                                                String tag = ((ArbolSintactico)$2).getIzq().getLex() + "@" + ambitoActual;
                                                for(String s : etiquetasAct){
                                                        if(tag.equals(s)){
                                                                b = true;
                                                                break;
                                                        }
                                                }
                                                if(!b){
                                                        yyerror("No se puede saltar al tag " + ((ArbolSintactico)$2).getIzq().getLex());
                                                        $$ = new NodoHoja("Error");
                                                }else{
                                                        $$ = new NodoComun("Continue",new NodoHoja("Fin"),(ArbolSintactico)$2);
                                                        if(mapEtiquetas.containsKey(((ArbolSintactico)$2).getIzq().getLex())){
                                                                mapEtiquetas.get(((ArbolSintactico)$2).getIzq().getLex()).add((ArbolSintactico)$$);
                                                        }

                                                }
                                        }else{
                                                $$ = new NodoComun("Continue",new NodoHoja("Fin"),(ArbolSintactico)$2);
                                        }
                                }
                | BREAK {$$ = new NodoControl("Break",(ArbolSintactico)new NodoHoja("Fin"));}
;
tag : {$$ = new NodoHoja("Fin");}
        | DOSPUNTOS ID {String ambito = buscarAmbito(ambitoActual,$2.sval);
                        $$ = new NodoControl("Tag", new NodoHoja($2.sval) );
                        if(!ambito.equals("")){
                                if(!TablaSimbolos.getAtributo($2.sval +"@"+ ambito,"Uso").equals("Etiqueta")){
                                        yyerror($2.sval + " no es una etiqueta.");
                                }
                        }else{
                                yyerror("La etiqueta " + $2.sval + " no esta declarada.");
                        }
                        }
        | DOSPUNTOS error{$$=new NodoHoja("Error sintactico");yyerror("Se esperaba un identificador");}
;
sentencia_if_break : IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF  { $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10)));  }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE ejecutables_break_continue PUNTOCOMA END_IF { $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue PUNTOCOMA END_IF { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9))); }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA END_IF { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) ); }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF { $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); } 
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C END_IF  { $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico"); yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE  ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE  ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");   yyerror("Se esperaba ; luego de la sentencia");}
                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");  yyerror("Se esperaba ( ");}
;

param_real : cte{
                        $$ = new NodoHoja($1.sval);
                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval,"Tipo"));
                        ((ArbolSintactico)$$).setUso("Variable");}
                | ID {
                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                        if(!ambito.equals("")){
                                 $$=new NodoHoja($1.sval+"@"+ambito);
                                ((ArbolSintactico)$$).setUso("Variable");
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                if( TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Variable") 
                                || TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Constante") ){
                                
                                        $$=new NodoHoja($1.sval+"@"+ambito);
                                        ((ArbolSintactico)$$).setUso("Variable");
                                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                }else{
                                        yyerror("El parametro " + $1.sval + " no es una variable.");
                                        $$ = new NodoHoja("Error");
                                }
                        }else{
                                $$=new NodoHoja("Error");
                                yyerror("El parametro "+ $1.sval +" no se encuentra declarado en el ambito "+ambitoActual);
                        }
                        
                }
;
llamado_func: ID PARENT_A param_real COMA param_real PARENT_C {
                                                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                                                        NodoComun parametro1=null;
                                                        NodoComun parametro2=null;
                                                        if (!ambito.equals("") ){
                                                                if( !TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion") ){
                                                                        yyerror("La funcion "+$1.sval+" no fue declarada");
                                                                        $$ = new NodoHoja("Error");
                                                                }else{
                                                                        if(!((ArbolSintactico)$3).getLex().equals("Error") && !((ArbolSintactico)$5).getLex().equals("Error") ){
                                                                                String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                                                                                String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                                                                                if(par1 != null)
                                                                                        if(par2 != null){
                                                                                                String tipoS3 = (String) ((ArbolSintactico) $3 ).getTipo();
                                                                                                if( !(tipoS3.equals((String)TablaSimbolos.getAtributo(par1,"Tipo") ) )){
                                                                                                        String nombreS3 = ((ArbolSintactico) $3).getLex();
                                                                                                        yyerror("El tipo del parametro "+ nombreS3+" no coincide con el tipo declarado en la funcion.");
                                                                                                        $$ = new NodoHoja("Error");
                                                                                                        break;
                                                                                                }else{
                                                                                                        NodoHoja n =new NodoHoja(par1);
                                                                                                        n.setTipo(tipoS3);
                                                                                                        n.setUso("Variable");
                                                                                                        parametro1 = new NodoComun("=:",n , (ArbolSintactico)$3);
                                                                                                }
                                                                                                String tipoS5 = (String) ((ArbolSintactico) $5).getTipo();
                                                                                                if( !(tipoS5.equals((String)TablaSimbolos.getAtributo(par2,"Tipo") ))){
                                                                                                        String nombreS5 = ((ArbolSintactico) $5).getLex();
                                                                                                        yyerror("El tipo del parametro "+ nombreS5+" no coincide con el tipo declarado en la funcion.");
                                                                                                        $$ = new NodoHoja("Error");
                                                                                                        break;
                                                                                                }else{
                                                                                                        NodoHoja n =new NodoHoja(par2);
                                                                                                        n.setTipo(tipoS5);
                                                                                                        n.setUso("Variable");
                                                                                                        parametro2 = new NodoComun("=:",n, (ArbolSintactico)$5);
                                                                                                }
                                                                                        }else{
                                                                                                yyerror("La declaracion de la funcion no cuenta con dos parametros.");
                                                                                                $$ = new NodoHoja("Error");
                                                                                        }
                                                                                else{
                                                                                        yyerror("La declaracion de la funcion no cuenta con dos parametros.");
                                                                                        $$ = new NodoHoja("Error");
                                                                                }
                                                                                $$=new NodoControl("Llamado Funcion" ,new NodoComun($1.sval+"@"+ambito,(ArbolSintactico)parametro1,(ArbolSintactico)parametro2));
                                                                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                                                        }else{
                                                                                $$ = new NodoHoja("Error");
                                                                        }
                                                                }
                                                        }else{
                                                                yyerror("La funcion " + $1.sval + " no se encuentra declarada");
                                                                $$ = new NodoHoja("Error");
                                                        }
                                                }
        | ID PARENT_A param_real PARENT_C {
            String ambito = buscarAmbito(ambitoActual,$1.sval);
            NodoComun parametro1=null;
            if (!ambito.equals("")){
                if (!TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion")){
                        yyerror("La funcion "+$1.sval+" no fue declarada");
                        $$ = new NodoHoja("Error");
                }else{
                        if(!((ArbolSintactico)$3).getLex().equals("Error")){
                                String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                                String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                                if(par2 ==null){
                                        if(par1!=null){
                                                String tipoS3 = (String) ((ArbolSintactico) $3 ).getTipo();
                                                if( !(tipoS3.equals((String)TablaSimbolos.getAtributo(par1,"Tipo") )) ){
                                                        String nombreS3 = ((ArbolSintactico) $3).getLex();
                                                        yyerror("El tipo del parametro "+ nombreS3+" no coincide con el tipo declarado en la funcion.");
                                                        $$ = new NodoHoja("Error");
                                                }else{
                                                        NodoHoja n =new NodoHoja(par1);
                                                        n.setTipo(tipoS3);
                                                        n.setUso("Variable");
                                                        parametro1 = new NodoComun("=:",n , (ArbolSintactico)$3);
                                                }
                                        }else{
                                                yyerror("La funcion esta declarada sin parametros.");
                                                $$ = new NodoHoja("Error");
                                        }
                                }else{
                                        yyerror("La funcion esta declarada con dos parametros.");
                                        $$ = new NodoHoja("Error");
                                }
                                $$=new NodoControl("Llamado Funcion" ,new NodoComun($1.sval+"@"+ambito,(ArbolSintactico)parametro1,new NodoHoja("Un solo parametro")));
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                        }else{
                                $$=$3;
                        }
                }
            }else{
                        yyerror("La funcion " + $1.sval + " no se encuentra declarada");
                        $$ = new NodoHoja("Error");
            }
        }
        | ID PARENT_A PARENT_C {
                String ambito = buscarAmbito(ambitoActual,$1.sval);
                if (!ambito.equals("") ){
                        if (!TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion")){
                                yyerror("La funcion "+$1.sval+" no fue declarada");
                                $$ = new NodoHoja("Error");
                        }else{
                                String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                                String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                                if(par2 == null){
                                        if(par1 != null){
                                                yyerror("La funcion esta declarada con un parametro.");
                                                $$ = new NodoHoja("Error");
                                        }
                                }else{
                                        yyerror("La funcion esta declarada con dos parametros.");
                                        $$ = new NodoHoja("Error");
                                }
                                $$=new NodoControl("Llamado Funcion", new NodoComun($1.sval+"@"+ambito,new NodoHoja("Fin"),new NodoHoja("Fin")));
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                        }
                }else{
                        yyerror("La funcion " + $1.sval + " no se encuentra declarada");
                        $$ = new NodoHoja("Error");
                }
        }
        | ID PARENT_A param_real COMA param_real error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba )");}
        | ID PARENT_A param_real error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba )");}
        | ID PARENT_A error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba )");}
        | ID PARENT_A param_real COMA error {$$=new NodoHoja("Error sintactico");yyerror("Se esperaba otro parametro");}
; 
%%
private NodoControl raiz;
private List<ArbolSintactico> funciones = new ArrayList<ArbolSintactico>();
private static HashMap<Integer,ArrayList<String>> erroresSintacticos = new HashMap<Integer,ArrayList<String>>();
public String ambitoActual = "Global";
private List<String> tipoActual = new ArrayList<String>();
private List<String> etiquetasAct = new ArrayList<String>();
private Stack<Boolean> hayReturn = new Stack<Boolean>();
private Map<String,List<ArbolSintactico>> mapEtiquetas = new HashMap<String,List<ArbolSintactico>>();
private Stack<List<String>> stackWhen = new Stack<List<String>>();

void yyerror(String mensaje){
        if (erroresSintacticos.get(AnalizadorLexico.getLineaAct())== null){
                ArrayList<String> mnsj = new ArrayList<String>();
                mnsj.add(mensaje); 
                erroresSintacticos.put(AnalizadorLexico.getLineaAct(), mnsj);
        }
        else{
                erroresSintacticos.get(AnalizadorLexico.getLineaAct()).add(mensaje);
        }
}
void yyerror(String mensaje,int linea){
        if (erroresSintacticos.get(linea)== null){
                ArrayList<String> mnsj = new ArrayList<String>();
                mnsj.add(mensaje); 
                erroresSintacticos.put(linea, mnsj);
        }
        else{
                erroresSintacticos.get(linea).add(mensaje);
        }
}
static HashMap<Integer,ArrayList<String>> getErroresSintacticos(){
        return erroresSintacticos;
}
void chequearRangoI32(String sval){
  String s = "2147483647";
  long l = Long.valueOf(s);
  if(Long.valueOf(sval) > l){
    yyerror("La constante esta fuera de rango");
  }
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        this.yylval = new ParserVal(t.getLexema());
        return t.getId();
}
public NodoControl getRaiz(){
	return raiz;
}
public List<ArbolSintactico> getFuncion(){
        return funciones;
}

void actualizarAmbito(String lex, String amb){
        TablaSimbolos.addAtributo(lex,"Ambito",amb);
}
public String getTipoActual(){
        if(!this.tipoActual.isEmpty()){
                return (this.tipoActual.get(tipoActual.size()-1));
        }
        return null;
}
public void cambiarTipoActual(String f){
        tipoActual.add(f);
}
public void removeTipoActual(){
        if(!this.tipoActual.isEmpty()){
                this.tipoActual.remove(tipoActual.size()-1);
        }
}
public String buscarAmbito(String ambitoActual,String lexema){
        String ambito = ambitoActual;
        while(!TablaSimbolos.existeSimbolo(lexema+"@"+ambito)){
                if(ambito.equals("Global")){
                        //yyerror("La variable " + lexema + " no se encuentra declarada en el ambito " + ambitoActual);
                        ambito = "";
                        break;
                }else{
                        char [] a = ambito.toCharArray();
                        for (int i = a.length;i>=0;i--){
                                if(a[i-1] == '@'){
                                        ambito = ambito.substring(0,i-1);
                                        break;
                                }
                        }
                }
        }
        return ambito;
}
public String calcularFloat(String f){
        int i =0;
	char caracter=' ';
	String digito=""; //parte numerica
	String exponente=""; //parte exponencial	
	while (caracter != 'F') {
                if(i<f.length()){
                      caracter = f.charAt(i);
		        digito+= caracter;
		        i++; 
                }else{
                        return f;
                }
	}
        Double d = Double.parseDouble(digito);
	for (int j=i ; j < (f.length()); j++) {
		caracter = f.charAt(j);
		exponente += caracter;
	}
	Double e = Double.parseDouble(exponente);
	Double numero = Math.pow(d, e);
        return numero.toString();	
}