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

%}

%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG CADENA COMENT CONST SUMA RESTA MULT DIV ENTERO FLOAT

%start program 

%left SUMA RESTA
%left MULT DIV

%% 
program : nombre_program LLAVE_A bloque_sentencias LLAVE_C {
                                                                raiz = new NodoControl("PROGRAMA",(ArbolSintactico)$3);  
                                                                TablaSimbolos.removeAtributo($1.sval);
                                                        }
                         
        | nombre_program LLAVE_A bloque_sentencias error {
                $$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba }");}
        | nombre_program error bloque_sentencias LLAVE_C{
                $$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba {");}
        | error LLAVE_A bloque_sentencias LLAVE_C {
                $$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba el nombre del programa");}
;
nombre_program : ID 
;
bloque_sentencias :{$$=new NodoHoja("Fin");}
                | bloque_sentencias sentencia PUNTOCOMA {
                                                        $$=new NodoComun("Sentencia", (ArbolSintactico) $1, (ArbolSintactico) $2);
                                                        }
                | bloque_sentencias sentencia error{
                                $$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba ;");
                        }
;
sentencia : sentencia_declarativa {$$=new NodoHoja("Sentencia Declarativa");}
        | sentencia_ejecutable {$$ = $1;}
;
sentencia_declarativa : sentencia_decl_datos 
                        | sentencia_decl_fun {funciones.put((String)((ArbolSintactico)$1).getLex(),(ArbolSintactico)$1);}
                        | lista_const  
;
tipo : I32 {
            $$ = new NodoHoja("Entero");
            ((NodoHoja)$$).setTipo("Entero");
           }
     | F32 {
            $$ = new NodoHoja("Float");
            ((NodoHoja)$$).setTipo("Float");
           }
;
constante_for:    ENTERO
                | RESTA ENTERO
                | ID {
                        String ambito=buscarAmbito(ambitoActual, $1.sval);
                        if (!TablaSimbolos.existeSimbolo($1.sval+ "@" + ambito)){
                                yyerror("La constante '" + $1.sval + "' no fue declarada");
                        }
                        else{ 
                                if(!((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Constante")){
                                        yyerror($1.sval+" debe ser una constante");
                                }
                                if(!((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo")).equals("Entero")){
                                        yyerror($1.sval+" debe ser de tipo entero");
                                }   
                        }
                }
;
sentencia_decl_datos : tipo list_var {  System.out.println("Declaracion de datos");
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
        | tipo error {$$=new NodoHoja("Error sintactico");
                yyerror("se esperaba lista de identificadores entre comas");}
;
list_var : list_var COMA ID {
                            $$=$1;
                            ((NodoTipos)$$).add((String)$3.sval);
                            }
        |  ID {
               $$=new NodoTipos((String)$1.sval);
              }
;
encabezado_fun  : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo {       

                                if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                        $$ = new ParserVal($2.sval);
                                        TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                        TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                        TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                        TablaSimbolos.removeAtributo($2.sval);
                                        TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$9).getTipo());
                                        TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro1",((ArbolSintactico)$4).getLex() + "@"+ ambitoActual +"@"+ $2.sval);
                                        TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro2",((ArbolSintactico)$6).getLex()+ "@"+ ambitoActual +"@"+ $2.sval);
                                        ambitoActual += "@"+$2.sval;
                                        cambiarTipoActual(((ArbolSintactico)$9).getTipo());
                                        //Agrego los parametros en la tabla de simbolos
                                        TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual);
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$4).getTipo());
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Uso","Variable");
                                        TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual);
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$6).getTipo());
                                        TablaSimbolos.addAtributo(((ArbolSintactico)$6).getLex() + "@"+ ambitoActual,"Uso","Variable");

                                }else{
                                        yyerror("La funcion " + $2.sval + " ya existe en el ambito " + ambitoActual);
                                        ambitoActual += "@"+$2.sval;
                                }
                        
                        }
                | FUN ID PARENT_A parametro  PARENT_C DOSPUNTOS tipo {
                        if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                $$ = new ParserVal($2.sval);
                                TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                TablaSimbolos.removeAtributo($2.sval);
                                TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$7).getTipo());
                                TablaSimbolos.addAtributo($2.sval +"@"+ambitoActual,"Parametro1", ((ArbolSintactico)$4).getLex()+ "@"+ ambitoActual +"@"+ $2.sval);
                                ambitoActual += "@"+$2.sval;
                                cambiarTipoActual(((ArbolSintactico)$7).getTipo());
                                //Agrego el parametro en la tabla de simbolos
                                TablaSimbolos.addNuevoSimbolo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual);
                                TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Tipo",((ArbolSintactico)$4).getTipo());
                                TablaSimbolos.addAtributo(((ArbolSintactico)$4).getLex() + "@"+ ambitoActual,"Uso","Variable");

                        }else{
                                yyerror("La funcion " + $2.sval + " ya existe en el ambito " + ambitoActual);
                                ambitoActual += "@"+$2.sval;
                        }
                }
                | FUN ID PARENT_A PARENT_C DOSPUNTOS tipo {
                        if(!TablaSimbolos.existeSimbolo($2.sval+ "@" + ambitoActual)){
                                $$ = new ParserVal($2.sval);
                                TablaSimbolos.addNuevoSimbolo($2.sval+ "@" + ambitoActual);
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Uso","Funcion");
                                TablaSimbolos.addAtributo($2.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($2.sval,"Id"));
                                TablaSimbolos.removeAtributo($2.sval);
                                TablaSimbolos.addAtributo($2.sval+"@"+ambitoActual,"Tipo",((ArbolSintactico)$6).getTipo());
                                ambitoActual += "@"+$2.sval;
                                cambiarTipoActual(((ArbolSintactico)$6).getTipo());

                        }else{
                                yyerror("La funcion " + $2.sval + " ya existe en el ambito " + ambitoActual);
                                ambitoActual += "@"+$2.sval;
                        }
                }
                
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error  { $$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS error  {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS error  { $$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro PARENT_C error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba :");}
                | FUN ID PARENT_A PARENT_C error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro COMA parametro error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba )");}
                | FUN ID PARENT_A error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro COMA error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba otro parametro");}
                | FUN ID error {$$=new NodoHoja("Error sintactico");
                        ambitoActual += "@"+"Error";
                        yyerror("Se esperaba (");}
                | FUN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba un nombre de funcion");}


;
sentencia_decl_fun : encabezado_fun LLAVE_A cuerpo_fun LLAVE_C  {
                                System.out.println("Declaracion de Funcion");
                                $$ = new NodoControl("Funcion@"+$1.sval,(ArbolSintactico)$3);
                                char [] a = ambitoActual.toCharArray();
                                for (int i = a.length;i>=0;i--){
                                        if(a[i-1] == '@'){
                                                ambitoActual = ambitoActual.substring(0,i-1);
                                                break;
                                        }
                                }
                                removeTipoActual();
                        }
                | encabezado_fun LLAVE_A cuerpo_fun error{$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | encabezado_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
;
cuerpo_fun :    {$$=new NodoHoja("Fin");}
                | cuerpo_fun sentencias_fun PUNTOCOMA {$$=new NodoComun("Sentencia_Funcion", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | cuerpo_fun sentencias_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ;");}
;
sentencias_fun :  sentencia_decl_datos {$$=new NodoHoja("Sentencia Declarativa Datos");}
                | sentencia_decl_fun {$$=new NodoHoja("Sentencia Declarativa Funcion");}
                | lista_const  {$$ = $1;}
                | asignacion {$$ = $1;}
                | llamado_func {$$=$1;}
                | sentencia_if_fun {$$=$1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_fun {$$ = $1;}
                | sentencia_for_fun {$$=$1;}
                | sentencia_while_fun {$$=$1;}
                | retorno {$$=$1;}
;
sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun PUNTOCOMA END_IF {System.out.println("Sentencia IF");
                                $$= new NodoComun("IF_FUN",(ArbolSintactico)$3,(ArbolSintactico) new NodoComun("Cuerpo if fun",new NodoControl("Then fun",(ArbolSintactico) $6),new NodoControl("else fun", (ArbolSintactico)$9)));
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C PUNTOCOMA ELSE sentencias_fun PUNTOCOMA END_IF {System.out.println("Sentencia IF");
                        $$= new NodoComun("IF_FUN",(ArbolSintactico)$3,(ArbolSintactico) new NodoComun("Cuerpo if fun",new NodoControl("Then fun",(ArbolSintactico) $7),new NodoControl("else fun", (ArbolSintactico)$11)));}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun LLAVE_C PUNTOCOMA END_IF {System.out.println("Sentencia IF");
                        $$= new NodoComun("IF_FUN",(ArbolSintactico)$3,(ArbolSintactico) new NodoComun("Cuerpo if fun",new NodoControl("Then fun",(ArbolSintactico) $6),new NodoControl("else fun", (ArbolSintactico)$10)));}
                        
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA END_IF {System.out.println("Sentencia IF");
                        $$ = new NodoComun("IF FUN", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");
                        $$= new NodoComun("IF_FUN",(ArbolSintactico)$3,(ArbolSintactico) new NodoComun("Cuerpo if fun",new NodoControl("Then fun",(ArbolSintactico) $7),new NodoControl("else fun", (ArbolSintactico)$11)));}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");
                        $$ = new NodoComun("IF FUN", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$7) );}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}

                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun PUNTOCOMA ELSE sentencias_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE sentencias_fun PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE sentencias_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}


; 
sentencia_when_fun: WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $7);
                        System.out.println("Sentencia WHEN con llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN sentencias_fun {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $6);
                        System.out.println("Sentencia WHEN sin llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | WHEN PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | WHEN PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | WHEN PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
; 
etiqueta : ID DOSPUNTOS {
                                $$ = new ParserVal($1.sval);
                                if(!TablaSimbolos.existeSimbolo($1.sval+ "@" + ambitoActual)){
                                        TablaSimbolos.addNuevoSimbolo($1.sval+ "@" + ambitoActual);
                                        TablaSimbolos.addAtributo($1.sval+ "@" + ambitoActual,"Uso","Etiqueta");
                                        TablaSimbolos.addAtributo($1.sval+ "@" + ambitoActual,"Id",TablaSimbolos.getAtributo($1.sval,"Id"));
                                        TablaSimbolos.removeAtributo($1.sval);
                                }else{
                                        yyerror("La etiqueta '" + $1.sval + "' ya existe en el ambito " + ambitoActual);
                                }
                        }
;
sentencia_while_fun : etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C 
                        {
                                $$ = new NodoComun("While con Etiqueta Funcion",new NodoControl("Etiqueta", new NodoHoja($1.sval)) , new NodoComun("While", (ArbolSintactico) $4, new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $11 , (ArbolSintactico) $8)) );
                                System.out.println("Sentencia WHILE con etiqueta y con llaves");
                        
                                
                        }
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C sentencias_fun_break 
                        {
                                System.out.println("Sentencia WHILE con etiqueta y sin llaves");
                                $$ = new NodoComun("While con Etiqueta Funcion",new NodoControl("Etiqueta", new NodoHoja($1.sval)) , new NodoComun("While", (ArbolSintactico) $4, new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $8)) );
                        
                        }
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE con llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C sentencias_fun_break {
                    $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $9 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE sin llaves");
                } 
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una asignacion");}
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                | etiqueta WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba :");}
                | etiqueta WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | etiqueta WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion");}
                | etiqueta WHILE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion");}
                | WHILE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                
;

sentencia_for_fun : etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
                               String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval, id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$16),asignacion ))) );
                                
					if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
        
                        }
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C 
                        {
						String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");                               
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$16), asignacion ))) );
                                
                                if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                               
                        }
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C sentencias_fun_break
                        {
						String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion ))) );
                                
						if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                               
                        }
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C sentencias_fun_break
                        {
					String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable"); 
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15),asignacion ))) );
                                
                                if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
  
                
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
                               String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                System.out.println((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                }
                                                                                             
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
                               String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                 
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C sentencias_fun_break {
                               String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C sentencias_fun_break {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval),"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A cuerpo_fun_break error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}

                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A cuerpo_fun_break error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}

                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador + o -");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la comparacion");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba expresion para comparar");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador de comparacion");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                | etiqueta FOR PARENT_A ID ASIG  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la asignacion");}
                | etiqueta FOR PARENT_A ID ASIG error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba numero entero para asignar");}
                | etiqueta FOR PARENT_A ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba =:");}
                | etiqueta FOR PARENT_A error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                | etiqueta FOR error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A cuerpo_fun_break error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}

                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A cuerpo_fun_break error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}

                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador + o -");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la comparacion");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba expresion para comparar");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador de comparacion");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR PARENT_A ID ASIG  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la asignacion");}
                |   FOR PARENT_A ID ASIG error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba numero entero para asignar");}
                |   FOR PARENT_A ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba =:");}
                |   FOR PARENT_A error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}                
;
cuerpo_fun_break : {$$=new NodoHoja("Fin");}
                | cuerpo_fun_break sentencias_fun_break PUNTOCOMA {$$=new NodoComun("Sentencia_Funcion_Break", (ArbolSintactico) $1, (ArbolSintactico) $2);}
                | cuerpo_fun_break sentencias_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ;");}
;
sentencias_fun_break :   asignacion  {$$ = $1;}
                | sentencia_if_break_fun  {$$ = $1;}
                | sentencia_out  {$$ = $1;}
                | sentencia_when_break_fun  {$$ = $1;}
                | sentencia_while_fun  {$$ = $1;}
                | sentencia_for_fun  {$$ = $1;}
                | CONTINUE tag {$$ = new NodoControl("Continue",(ArbolSintactico)$2);}
                | BREAK {$$ = new NodoHoja("Break");}
                | BREAK cte {$$ = new NodoControl("Break", new NodoHoja($2.sval));}
                | retorno {$$=$1;}

;
sentencia_when_break_fun : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $7);
                        System.out.println("Sentencia WHEN con llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN sentencias_fun_break {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $6);
                        System.out.println("Sentencia WHEN sin llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | WHEN PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | WHEN PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | WHEN PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
;
sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                        System.out.println("Sentencia IF sin corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                        System.out.println("Sentencia IF con corchetes y else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                        System.out.println("Sentencia IF con corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}

                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN  sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( ");}
;
retorno : RETURN PARENT_A expresion PARENT_C {$$ = new NodoControl("Retorno", (ArbolSintactico)$3);
                                                String tipoRet = ((ArbolSintactico)$3).getTipo();
                                                if(!ambitoActual.equals("Global")){
                                                        String tipoFun = getTipoActual();
                                                        if(!tipoRet.equals(tipoFun)){
                                                                yyerror("El retorno debe ser del mismo tipo que el retorno de la funcion.");
                                                        }
                                                }else{
                                                        yyerror("El retorno puede estar solo dentro de una funcion.");
                                                }
                                        }
                        | RETURN PARENT_A expresion error {$$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba )");}
                        | RETURN PARENT_A error {$$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba expresion");}
                        | RETURN error {$$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba expresion entre ( )");}

;
parametro : tipo ID{    $$ = new NodoHoja($2.sval);
                        ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
}
        | tipo error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba identificador");}
;

lista_const : CONST lista_asignacion {System.out.println("Declaracion de Constante/s");}
;

lista_asignacion : lista_asignacion COMA asignacion_const 
        | asignacion_const
;
asignacion_const : ID ASIG cte { 
                                if(TablaSimbolos.existeSimbolo($1.sval+"@"+ambitoActual)){
                                        yyerror("La variable " + $1.sval + " se encuentra declarada en el ambito " + ambitoActual);
                                }else{
                                        TablaSimbolos.addNuevoSimbolo($1.sval+"@"+ambitoActual);
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Id",TablaSimbolos.getAtributo($1.sval,"Id"));
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Tipo",TablaSimbolos.getAtributo($3.sval,"Tipo"));
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Linea",AnalizadorLexico.getLineaAct());
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Uso","Constante");
                                        TablaSimbolos.addAtributo($1.sval+"@"+ambitoActual,"Valor", $3);
                                }
                        }
        | ID ASIG error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba una constante");}
        | ID error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba una asignacion =:");}
;
sentencia_ejecutable : asignacion {$$ = $1;}
        | sentencia_if   {$$ = $1; }
        | sentencia_out {$$ = $1;}
        | sentencia_when {$$ = $1;}
        | sentencia_for {$$ = $1;}
        | sentencia_while {$$ = $1;}
        | llamado_func{$$=$1;}
;
asignacion : ID ASIG expresion  {
                                        System.out.println("Asignacion");
                                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                                        NodoHoja hoja = new NodoHoja($1.sval+"@"+ambito);
                                        hoja.setUso((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso"));
                                        hoja.setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                        $$ = (ArbolSintactico) new NodoComun($2.sval, hoja , (ArbolSintactico) $3);

                                        String tipoS1 = "";
                                        if(!ambito.equals("")){
                                                if(((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Variable")){
                                                        tipoS1 = (String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo");
                                                        ((ArbolSintactico)$$).setTipo(tipoS1);
                                                }
                                                else {
                                                        yyerror($1.sval+" no es una variable.");
                                                }
                                        }
                                        String tipoS3 = ((ArbolSintactico)$3).getTipo();
                                        if(!(tipoS1.equals(tipoS3))){
                                                yyerror("No se puede realizar una asignacion con tipos diferentes.");
                                        }
                                        
                                }
                | ID error {$$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba =:");}
                | ID ASIG error {$$=new NodoHoja("Error sintactico");
                                yyerror("Se esperaba expresion");}                  
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
        | sentencia_for_asig ELSE cte {
                                        NodoHoja cte = new NodoHoja($3.sval);
                                        cte.setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo"));
                                        $$ = new NodoComun("For como expresion",(ArbolSintactico)$1,cte);
                                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo"));     
                                       
                                        }
        | sentencia_while_asig ELSE cte {
                                        NodoHoja cte = new NodoHoja($3.sval);
                                        cte.setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo"));
                                        $$ = new NodoComun("While como expresion",(ArbolSintactico)$1,cte);
                                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($3.sval,"Tipo")); 
                                        
                                        }
        | expresion SUMA error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un termino");}
        | expresion RESTA error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un termino");}
        | sentencia_for_asig ELSE error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un constante");}
        | sentencia_while_asig ELSE error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un constante");}
        
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
        | factor 
                {
                  $$ = $1;
                 }  
        | termino MULT error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un factor");}
        | termino DIV error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un factor");}
;
factor: ID {
                String ambito = buscarAmbito(ambitoActual,$1.sval);
                if((!ambito.equals(""))){
                        if(((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Variable")
                        || ((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso")).equals("Constante")){
                                $$ = new NodoHoja($1.sval+"@"+ambito);
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                ((ArbolSintactico)$$).setUso((String)TablaSimbolos.getAtributo($1.sval+"@"+ambito, "Uso"));
                        }
                        else{
                                yyerror($1.sval+" no es una variable");
                        }
                }else{
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
cte : ENTERO {  chequearRangoI32($1.sval);}
        | FLOAT {  }
        | RESTA ENTERO 
        | RESTA FLOAT 

;
sentencia_for_asig: FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A bloque_sent_eje_asig LLAVE_C {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                System.out.println((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For en asignacion",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }

                }
                                                                                             
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_sent_eje_asig LLAVE_C {
                               String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For en asignacion",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }

                        }
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C sent_eje_asig {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For en asignacion",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }

                        }
                | FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C sent_eje_asig {
					String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For en asignacion",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
                               if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }

                        }
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_sent_eje_asig error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}

                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A bloque_sent_eje_asig error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}

                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador + o -");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion expresion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la comparacion");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID comparacion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba expresion para comparar");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador de comparacion");}
                |   FOR PARENT_A ID ASIG  constante_for PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR PARENT_A ID ASIG  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la asignacion");}
                |   FOR PARENT_A ID ASIG error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba numero entero para asignar");}
                |   FOR PARENT_A ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba =:");}
                |   FOR PARENT_A error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}                
;

sentencia_while_asig: WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_sent_eje_asig LLAVE_C {
                        $$ = new NodoComun("While Asignacion", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE con llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C sent_eje_asig {
                        $$ = new NodoComun("While Asignacion", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $9 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE sin llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion");}
                | WHILE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
;
sentencia_if_asig:IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF{
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                                                                                                                                }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig PUNTOCOMA END_IF{
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig PUNTOCOMA END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                                                                                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA END_IF {$$ = new NodoComun("IF",new NodoControl("Condicion", (ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                                                                                        System.out.println("Sentencia IF sin corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF {
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                                                                                                                                System.out.println("Sentencia IF con corchetes y else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C END_IF {
                                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                                        System.out.println("Sentencia IF con corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE error{$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}

                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE error{$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sent_eje_asig PUNTOCOMA ELSE sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C ELSE sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( ");}
;
sentencia_when_asig : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig LLAVE_C {
                        $$ = (ArbolSintactico) new NodoComun("When Asignacion",(ArbolSintactico) $3, (ArbolSintactico) $7);
                        System.out.println("Sentencia WHEN con llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN sent_eje_asig {
                        $$ = (ArbolSintactico) new NodoComun("When Asignacion",(ArbolSintactico) $3, (ArbolSintactico) $6);
                        System.out.println("Sentencia WHEN sin llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sent_eje_asig error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } en el when");}
                | WHEN PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) en el when");}
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion en el when");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( en el when");}
;
bloque_sent_eje_asig: {$$=new NodoHoja("Fin");}
                        | bloque_sent_eje_asig sent_eje_asig PUNTOCOMA {$$=new NodoComun("Bloque Ejecutable Asignacion", (ArbolSintactico) $1, (ArbolSintactico) $2);}
;
sent_eje_asig:  asignacion {$$ = $1;}
                | sentencia_if_asig {$$ = $1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_asig {$$ = $1;}
                | sentencia_while_asig {$$ = $1;}
                | sentencia_for_asig {$$ = $1;}
                | BREAK cte {NodoHoja cte = new NodoHoja($2.sval);
                        cte.setTipo((String)TablaSimbolos.getAtributo($2.sval,"Tipo"));
                        $$ = new NodoControl("Break", cte);}
;
sentencia_if : IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF{
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                                                                                                                                }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable PUNTOCOMA END_IF{
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                                                                                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF",new NodoControl("Condicion", (ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                                                                                        System.out.println("Sentencia IF sin corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF {
                                                                                                                                $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                                                                                                                                System.out.println("Sentencia IF con corchetes y else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF {
                                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                                        System.out.println("Sentencia IF con corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE error{$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}

                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE error{$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( ");}
;


condicion : expresion comparacion expresion 
                {
                        $$= new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                        ((ArbolSintactico)$$).setTipo(((ArbolSintactico)$1).getTipo());
                        ((ArbolSintactico)$$).setUso("Condicion");
                        if (!((((ArbolSintactico)$1).getTipo()).equals(((ArbolSintactico)$3).getTipo()))){
                               yyerror("error en la comparacion entre expresiones de distintos tipos");
                        }
                }
        | expresion comparacion error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba otra expresion para comparar.");}
        | expresion error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un tipo de comparacion.");}
;
comparacion: IGUAL {$$= $1;}
        | MAYOR {$$= $1;}
        | MENOR {$$= $1;}
        | MENORIGUAL {$$= $1;}
        | MAYORIGUAL {$$= $1;}
        | DIST {$$=$1;}
;
bloque_ejecutable : {$$=new NodoHoja("Fin");}
                | bloque_ejecutable sentencia_ejecutable PUNTOCOMA {
                                                                $$=new NodoComun("Bloque Ejecutable", (ArbolSintactico) $1, (ArbolSintactico) $2);
                                                                }
                | bloque_ejecutable sentencia_ejecutable {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ;");}
;
sentencia_out : OUT PARENT_A CADENA PARENT_C {
                        $$ = new NodoControl($1.sval, (ArbolSintactico) new NodoHoja($3.sval));
                        System.out.println("Sentencia OUT");}
                |  OUT PARENT_A CADENA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                |  OUT PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una CADENA");}
                | OUT error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
;
sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $7);
                        System.out.println("Sentencia WHEN con llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN sentencia_ejecutable {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $6);
                        System.out.println("Sentencia WHEN sin llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } en el when");}
                | WHEN PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) en el when");}
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion en el when");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( en el when");}
;
sentencia_while :  etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C 
                        {
                                $$ = new NodoComun("While con Etiqueta",(ArbolSintactico) new NodoControl("Etiqueta", (ArbolSintactico) new NodoHoja($1.sval)) , (ArbolSintactico) new NodoComun("While", (ArbolSintactico) $4, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $11 , (ArbolSintactico) $8)) );
                        
                        }
                | etiqueta WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C ejecutables_break_continue {
                        $$ = new NodoComun("While con Etiqueta",(ArbolSintactico) new NodoControl("Etiqueta", (ArbolSintactico) new NodoHoja($1.sval)) , (ArbolSintactico) new NodoComun("While", (ArbolSintactico) $4, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $8)) );
                        
                        }
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE con llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C ejecutables_break_continue {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $9 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE sin llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | WHILE PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion");}
                | WHILE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
;
bloque_break_continue : {$$=new NodoHoja("Fin");}
        | bloque_break_continue ejecutables_break_continue PUNTOCOMA {
                $$ = new NodoComun("Bloque Break con Continue",(ArbolSintactico) $1, (ArbolSintactico) $2);
                }
        | bloque_break_continue ejecutables_break_continue {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba ;");}
;

ejecutables_break_continue :  asignacion {$$ = $1;}
                | sentencia_if_break {$$ = $1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_break {$$ = $1;}
                | sentencia_while {$$ = $1;}
                | sentencia_for {$$ = $1;}
                | CONTINUE tag {$$ = new NodoControl("Continue",(ArbolSintactico)$2);}
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
        | DOSPUNTOS error{$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba un identificador");}

;
sentencia_when_break :  WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN ejecutables_break_continue {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } en el when");}
                | WHEN PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) en el when");}
                | WHEN PARENT_A error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba condicion en el when");}
                | WHEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( en el when");}
;
sentencia_if_break : IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                        System.out.println("Sentencia IF sin corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF
                        {
                        $$= new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3),(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                        System.out.println("Sentencia IF con corchetes y else");
                        } 
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C END_IF 
                        {
                        $$ = new NodoComun("IF", new NodoControl("Condicion",(ArbolSintactico) $3), (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                        System.out.println("Sentencia IF con corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}

                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue LLAVE_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE  ejecutables_break_continue PUNTOCOMA error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE  ejecutables_break_continue error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la sentencia");}

                | IF PARENT_A condicion PARENT_C THEN error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba una condicion ");}
                | IF error {$$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ( ");}
;

sentencia_for : etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA constante_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                                String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval, id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$16),asignacion ))) );
                                
					if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C 
                        {
                                String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");                               
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$16), asignacion ))) );
                                
					if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                     
                        }
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C ejecutables_break_continue
                        {
                                String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion ))) );
                                
					if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C ejecutables_break_continue
                        {
                                String ambito = buscarAmbito(ambitoActual,$4.sval);
                                NodoHoja operando1 = new NodoHoja($4.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($13.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($13.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable"); 
                                NodoComun iteracion = new NodoComun($12.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($4.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($4.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($4.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($8.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($8.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("For con Etiqueta", new NodoControl("Etiqueta",new NodoHoja($1.sval)), new NodoComun("FOR",new NodoComun("Asignacion FOR", new NodoComun($5.sval,id1, new NodoHoja($6.sval)),null) ,new NodoComun("Condicion-Cuerpo", new NodoControl("Condicion",new NodoComun($9.sval,id2, (ArbolSintactico)$10)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15),asignacion ))) );
                            
					if (!TablaSimbolos.existeSimbolo($4.sval+ "@" + buscarAmbito(ambitoActual, $4.sval))){
                                        yyerror("La variable '" + $4.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($4.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $4.sval + "' debe ser de tipo entero");
                                }
                                if (!$8.sval.equals($4.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
  
                
                | FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                System.out.println((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }

                }
                                                                                             
                | FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$15), asignacion )) );
                                
						if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C ejecutables_break_continue {
                                String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
					if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C ejecutables_break_continue {
						String ambito = buscarAmbito(ambitoActual,$3.sval);
                                NodoHoja operando1 = new NodoHoja($3.sval+"@"+ambito);
                                operando1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja operando2 = new NodoHoja($12.sval);
                                operando2.setTipo((String)TablaSimbolos.getAtributo($12.sval +"@"+ ambito,"Tipo"));
                                operando1.setUso("Variable");
                                operando2.setUso("Variable");
                                NodoComun iteracion = new NodoComun($11.sval,operando1,operando2);
                                iteracion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja iterador = new NodoHoja($3.sval+"@"+ambito);
                                iterador.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoComun asignacion = new NodoComun("=:",iterador, iteracion);
                                asignacion.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                asignacion.setUso("Entero");
                                NodoHoja id1 = new NodoHoja($3.sval+"@"+ambito);
                                id1.setUso("Variable");
                                id1.setTipo((String)TablaSimbolos.getAtributo($3.sval +"@"+ ambito,"Tipo"));
                                NodoHoja id2 = new NodoHoja($7.sval+"@"+ambito);
                                id2.setUso("Variable");
                                id2.setTipo((String)TablaSimbolos.getAtributo($7.sval +"@"+ ambito,"Tipo"));
                                $$ = new NodoComun("FOR",new NodoComun("Asignacion FOR",new NodoComun($4.sval,id1,new NodoHoja($5.sval)),null),new NodoComun("Condicion-Cuerpo",new NodoControl("Condicion",new NodoComun($8.sval,id2,(ArbolSintactico)$9)),new NodoComun("Cuerpo", new NodoControl("Cuerpo For", (ArbolSintactico)$14), asignacion)) );
                                
                                if (!TablaSimbolos.existeSimbolo($3.sval+ "@" + buscarAmbito(ambitoActual, $3.sval))){
                                        yyerror("La variable '" + $3.sval + "' no fue declarada");
                                }
                                else if (!TablaSimbolos.getAtributo($3.sval+ "@" + ambitoActual,"Tipo").equals("Entero")){
                                        yyerror("La variable '" + $3.sval + "' debe ser de tipo entero");
                                }
                                if (!$7.sval.equals($3.sval)){
                                        yyerror("La variable de la condicion del for debe ser la misma que la variable de la asignacion del for");
                                }
                                
                        }
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_break_continue error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}

                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A bloque_break_continue error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}

                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador + o -");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la comparacion");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba expresion para comparar");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador de comparacion");}
                | etiqueta FOR PARENT_A ID ASIG constante_for PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                | etiqueta FOR PARENT_A ID ASIG constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la asignacion");}
                | etiqueta FOR PARENT_A ID ASIG error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba numero entero para asignar");}
                | etiqueta FOR PARENT_A ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba =:");}
                | etiqueta FOR PARENT_A error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                | etiqueta FOR error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C LLAVE_A bloque_break_continue error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}

                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C LLAVE_A bloque_break_continue error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba }");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for PARENT_C error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba {");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA  constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba )");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA SUMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA RESTA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba constante");}

                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador + o -");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion expresion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la comparacion");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID comparacion error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba expresion para comparar");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba operador de comparacion");}
                |   FOR PARENT_A ID ASIG constante_for PUNTOCOMA error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR PARENT_A ID ASIG constante_for error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba ; luego de la asignacion");}
                |   FOR PARENT_A ID ASIG error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba numero entero para asignar");}
                |   FOR PARENT_A ID error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba =:");}
                |   FOR PARENT_A error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba nombre de variable");}
                |   FOR error{ $$=new NodoHoja("Error sintactico");
                        yyerror("Se esperaba (");}                
;

param_real : cte{
                        $$ = new NodoHoja($1.sval);
                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval,"Tipo"));
                }

                | ID {$$=new NodoHoja($1.sval);
                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                        if(!ambito.equals("")){
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                        }
                     }
;
llamado_func: ID PARENT_A param_real COMA param_real PARENT_C {
                                                        $$=new NodoComun("llamado funcion",(ArbolSintactico)$3,(ArbolSintactico)$5);
                                                        String ambito = buscarAmbito(ambitoActual,$1.sval);
                                                        if (!ambito.equals("") ){
                                                                if( !TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion") ){
                                                                        yyerror("La funcion "+$1.sval+" no fue declarada");
                                                                }else{
                                                                        String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                                                                        String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                                                                        if(par1 != null)
                                                                                if(par2 != null){
                                                                                        String tipoS3 = (String) ((ArbolSintactico) $3 ).getTipo();
                                                                                        if( !(tipoS3.equals((String)TablaSimbolos.getAtributo(par1,"Tipo") ) )){
                                                                                                String nombreS3 = ((ArbolSintactico) $3).getLex();
                                                                                                yyerror("El tipo del parametro "+ nombreS3+" no coincide con el tipo declarado en la funcion.");
                                                                                        }
                                                                                        String tipoS5 = (String) ((ArbolSintactico) $5).getTipo();
                                                                                        if( !(tipoS5.equals((String)TablaSimbolos.getAtributo(par2,"Tipo") ))){
                                                                                                String nombreS5 = ((ArbolSintactico) $5).getLex();
                                                                                                yyerror("El tipo del parametro "+ nombreS5+" no coincide con el tipo declarado en la funcion.");
                                                                                        }
                                                                                }else{
                                                                                        yyerror("La declaracion de la funcion no cuenta con dos parametros.");
                                                                                }
                                                                        else{
                                                                                yyerror("La declaracion de la funcion no cuenta con dos parametros.");
                                                                        }
                                                                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                                                                }
                                                        }
                                                }
        | ID PARENT_A param_real PARENT_C {System.out.println("Llamado fun 1 parametro");
                $$=new NodoComun("llamado funcion",(ArbolSintactico)$3,new NodoHoja("Un solo parametro"));
            String ambito = buscarAmbito(ambitoActual,$1.sval);
            if (!ambito.equals("")){
                if (!TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion")){
                        yyerror("La funcion "+$1.sval+" no fue declarada");
                }else{
                        String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                        String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                        if(par2 ==null){
                                if(par1!=null){
                                        String tipoS3 = (String) ((ArbolSintactico) $3 ).getTipo();
                                        if( !(tipoS3.equals((String)TablaSimbolos.getAtributo(par1,"Tipo") )) ){
                                                String nombreS3 = ((ArbolSintactico) $3).getLex();
                                                yyerror("El tipo del parametro "+ nombreS3+" no coincide con el tipo declarado en la funcion.");
                                        }
                                }else{
                                        yyerror("La funcion esta declarada sin parametros.");
                                }
                        }else{
                                yyerror("La funcion esta declarada con dos parametros.");
                        }
                        ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                }
            }
        }
        | ID PARENT_A PARENT_C {$$=new NodoHoja("llamado funcion sin parametros");
                String ambito = buscarAmbito(ambitoActual,$1.sval);
                if (!ambito.equals("") ){
                        if (!TablaSimbolos.getAtributo($1.sval+"@"+ambito,"Uso").equals("Funcion")){
                                yyerror("La funcion "+$1.sval+" no fue declarada");
                                
                        }else{
                                String par1 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro1");
                                String par2 = (String) TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Parametro2");
                                if(par2 == null){
                                        if(par1 != null){
                                                yyerror("La funcion esta declarada con un parametro.");
                                        }
                                }else{
                                        yyerror("La funcion esta declarada con dos parametros.");
                                }
                                ((ArbolSintactico)$$).setTipo((String)TablaSimbolos.getAtributo($1.sval +"@"+ ambito,"Tipo"));
                        }
                }       
        }
        | ID PARENT_A param_real COMA param_real error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba )");}
        | ID PARENT_A param_real error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba )");}
        | ID PARENT_A error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba )");}
        | ID PARENT_A param_real COMA error {$$=new NodoHoja("Error sintactico");
                yyerror("Se esperaba otro parametro");}
; 

%%
private NodoControl raiz;
private Map<String,ArbolSintactico> funciones = new HashMap<String,ArbolSintactico>();
private static HashMap<Integer,ArrayList<String>> erroresSintacticos = new HashMap<Integer,ArrayList<String>>();
public String ambitoActual = "Global";
private List<String> tipoActual = new ArrayList<String>();
private boolean hayReturn = false;

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

void chequearRangoI32Neg(String sval){
       String s = "2147483648";
        long l = Long.valueOf(s);
        if(Long.valueOf(sval) > l){
                yyerror("La constante esta fuera de rango");
  } 
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        this.yylval = new ParserVal(t.getLexema());
        //if(t.getId() != -1){
        //  System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        //}else
        //  System.out.println("TERMINO LA EJECUCION");
        return t.getId();
}
public NodoControl getRaiz(){
	return raiz;
}
public Map<String,ArbolSintactico> getFuncion(){
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
                        yyerror("La variable " + lexema + " no se encuentra declarada en el ambito " + ambitoActual);
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