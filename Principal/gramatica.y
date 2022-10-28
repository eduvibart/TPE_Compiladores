%{
package Principal;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import GeneracionCodigoIntermedio.*;
%}

%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG CADENA COMENT CONST SUMA RESTA MULT DIV ENTERO FLOAT

%start program 

%left SUMA RESTA
%left MULT DIV

%% 
program : nombre_program LLAVE_A bloque_sentencias LLAVE_C {raiz = new NodoControl("PROGRAMA",(ArbolSintactico)$3);
                                                            System.out.println("Raiz---$$ : " + $$ + " $1 :" + $1);
                                                            System.out.println("Raiz : " + raiz);
                                                           }
        | error {yyerror("Hay un error sintactico en la entrada que arrastra errores");}
;
nombre_program : ID 
;
bloque_sentencias :{$$=new NodoHoja("Fin");}
                | bloque_sentencias sentencia PUNTOCOMA {
                                                        $$=new NodoComun("Sentencia", (ArbolSintactico) $2, (ArbolSintactico) $1);
                                                        System.out.println("BloqueSentencia---$$ : " + $$ + " $1 :" + $1);
                                                        }
                | bloque_sentencias sentencia {yyerror("Se esperaba ;");}
;
sentencia : sentencia_declarativa {$$=new NodoHoja("Sentencia Declarativa");}
        | sentencia_ejecutable {$$ = $1;
                                System.out.println("Sentencia---$$ : " + $$ + " $1 :" + $1);
                                }
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
sentencia_decl_datos : tipo list_var {System.out.println("Declaracion de datos");
                                      for (String s : ((NodoTipos)$2).getList()){
                                        TablaSimbolos.addAtributo(s,"Tipo",((ArbolSintactico) $1).getTipo());
                                        TablaSimbolos.addAtributo(s,"Ambito",ambitoActual);
                                      }
                                     }
                        | ID list_var {yyerror("No esta permitido el tipo declarado");}
;
list_var : list_var COMA ID {
                            $$=$1;
                            ((NodoTipos)$$).add((String)$3.sval);
                            }
        |  ID {
               $$=new NodoTipos((String)$1.sval);
              }
;
sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Declaracion de Funcion");
                                                                                                                   $$ = new NodoControl("Funcion:"+$2.sval,(ArbolSintactico)$11);
                                                                                                                  }
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Declaracion de Funcion");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Declaracion de Funcion");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo error {yyerror("Se esperaba {");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error  {yyerror("El tipo declarado no esta permitido");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C error {yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro COMA parametro error {yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro error {yyerror("Se esperaba )");}
                | FUN ID error {yyerror("Se esperaba (");}
                | FUN error {yyerror("Se esperaba un nombre de funcion");}
;
cuerpo_fun :    {$$=new NodoHoja("Fin");}
                | cuerpo_fun sentencias_fun PUNTOCOMA {$$=new NodoComun("Sentencia_Funcion", (ArbolSintactico) $2, (ArbolSintactico) $1);}
                | cuerpo_fun sentencias_fun error {yyerror("Se esperaba ;");}
;
sentencias_fun :  sentencia_decl_datos {$$=new NodoHoja("Sentencia Declarativa Datos");}
                | sentencia_decl_fun {$$=new NodoHoja("Sentencia Declarativa Funcion");}
                | lista_const  {$$ = $1;}
                | asignacion {$$ = $1;}
                | llamado_func {$$=$1;}
                | sentencia_if_fun {$$=$1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_fun 
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
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {yyerror("Se esperaba una condicion ");}
  
sentencia_when_fun: WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN sentencias_fun {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {yyerror("Se esperaba }");}
                | WHEN PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba {");}
                | WHEN PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | WHEN PARENT_A condicion error {yyerror("Se esperaba )");}
                | WHEN PARENT_A error {yyerror("Se esperaba condicion");}
                | WHEN error condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {yyerror("Se esperaba (");}
; 
sentencia_while_fun : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
    $$ = new NodoComun("While con Etiqueta Funcion",new NodoControl("Etiqueta", new NodoHoja($1.sval)) , new NodoComun("While", (ArbolSintactico) $5, new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $12 , (ArbolSintactico) $9)) );
                        System.out.println("Sentencia WHILE con etiqueta y con llaves");}
                | ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C sentencias_fun_break {System.out.println("Sentencia WHILE con etiqueta y sin llaves");
                    $$ = new NodoComun("While con Etiqueta Funcion",new NodoControl("Etiqueta", new NodoHoja($1.sval)) , new NodoComun("While", (ArbolSintactico) $5, new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $11 , (ArbolSintactico) $9)) );}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE con llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C sentencias_fun_break {
                    $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $9 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE sin llaves");
                } 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {yyerror("Se esperaba {");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {yyerror("Se esperaba )");}
                | WHILE PARENT_A error {yyerror("Se esperaba una condicion");}
                | WHILE error {yyerror("Se esperaba (");}
;
sentencia_for_fun :ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");
                                                                                                        $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$13,new NodoHoja($9.sval + $10.sval))));
                                                                                                       }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");
                                                                                                        $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$13,new NodoHoja($9.sval + $10.sval))));
                                                                                                       }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C sentencias_fun_break{System.out.println("Sentencia FOR");
                                                                                                                                $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$12,new NodoHoja($9.sval + $10.sval))));
                                                                                                                                }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C sentencias_fun_break{System.out.println("Sentencia FOR");
                                                                                                                                $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$12,new NodoHoja($9.sval + $10.sval))));
                                                                                                                                }
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C error {yyerror("Se esperaba {");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C error {yyerror("Se esperaba {");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte error {yyerror("Se esperaba )");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte error {yyerror("Se esperaba )");}
                | ID DOSPUNTOS error {yyerror("Se esperaba (");}
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");
                                                                                             $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$11,new NodoHoja($7.sval + $8.sval)));
                                                                                             }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");
                                                                                             $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$11,new NodoHoja($7.sval + $8.sval)));
                                                                                             }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C sentencias_fun_break {System.out.println("Sentencia FOR");
                                                                                    $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$10,new NodoHoja($7.sval + $8.sval)));
                                                                                  }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C sentencias_fun_break {System.out.println("Sentencia FOR");
                                                                                    $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$10,new NodoHoja($7.sval + $8.sval)));
                                                                                  }
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte error {yyerror("Se esperaba )");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte error {yyerror("Se esperaba )");}
                | FOR error {yyerror("Se esperaba (");}
;
cuerpo_fun_break : 
                | cuerpo_fun_break sentencias_fun_break PUNTOCOMA
                | cuerpo_fun_break sentencias_fun_break error {yyerror("Se esperaba ;");}
;
sentencias_fun_break :   asignacion 
                | sentencia_if_break_fun 
                | sentencia_out 
                | sentencia_when_break_fun 
                | sentencia_while_fun 
                | sentencia_for_fun 
                | CONTINUE tag 
                | BREAK 
                | BREAK cte 
                | retorno 

;
sentencia_when_break_fun : WHEN PARENT_A condicion PARENT_C THEN sentencias_fun_break {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | WHEN PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba {");}
                | WHEN PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | WHEN PARENT_A condicion error {yyerror("Se esperaba )");}
                | WHEN PARENT_A error {yyerror("Se esperaba condicion");}
                | WHEN error condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C {yyerror("Se esperaba (");}
;
sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN sentencias_fun_break PUNTOCOMA END_IF 
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                        System.out.println("Sentencia IF sin corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                        System.out.println("Sentencia IF con corchetes y else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C END_IF 
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                        System.out.println("Sentencia IF con corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {yyerror("Se esperaba una condicion ");}
                | IF error {yyerror("Se esperaba ( ");}
;
retorno : RETURN PARENT_A expresion PARENT_C {$$ = new NodoControl("Retorno", (ArbolSintactico)$3);}
;
parametro : tipo ID
        |  ID ID {yyerror("No esta permitido el tipo declarado");}
;

lista_const : CONST lista_asignacion {System.out.println("Declaracion de Constante/s");}
;
lista_asignacion : lista_asignacion COMA asignacion
        | asignacion
;
sentencia_ejecutable : asignacion {$$ = $1;
                                   System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);
                                  }
        | sentencia_if   {$$ = $1;
                                System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);}    
        | sentencia_out {$$ = $1;
                                System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);}
        | sentencia_when {$$ = $1;
                                System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);}
        | sentencia_for {$$ = $1;
                                System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);}
        | sentencia_while {$$ = $1;
                                System.out.println("SentenciaEjecutable---$$ : " + $$ + " $1 :" + $1);}
        | llamado_func
;
asignacion : ID ASIG expresion  {
                                 System.out.println("Asignacion");
                                 $$ = new NodoComun($2.sval,new NodoHoja($1.sval), (ArbolSintactico) $3);
                                 System.out.println("Asignacino---$$ : " + $$ + " $1 :" + $1+" $3 :" + $3);
                                }
;
expresion: expresion SUMA termino {
                                   $$ = new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                   System.out.println("ExpresionSuma---$$ : " + $$ + " $1 :" + $1+" $3 :" + $3);
                                  }
        | expresion RESTA termino {
                                   $$ = new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                   System.out.println("ExpresionResta---$$ : " + $$ + " $1 :" + $1+" $3 :" + $3);
                                  }
        | termino {
                   $$ = $1;
                   System.out.println("ExpresionTermino---$$ : " + $$ + " $1 :" + $1);
                  } 
        | llamado_func
        | sentencia_for ELSE cte
        | sentencia_while ELSE cte 
        
;
termino: termino MULT factor  {
                                $$ = new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                System.out.println("TerminoMult---$$ : " + $$ + " $1 :" + $1+" $3 :" + $3);
                                }   
        | termino DIV factor 
                                {
                                 $$ = new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);
                                 System.out.println("TerminoDiv---$$ : " + $$ + " $1 :" + $1+" $3 :" + $3);
                                }   
        | factor 
                {
                  $$ = $1;
                  System.out.println("TerminoFactor---$$ : " + $$ + " $1 :" + $1);
                 }  
;
factor: ID {
            $$ = new NodoHoja($1.sval);
            System.out.println("FactorID----$$ : " + $$ + " $1 :" + $1);                                                             
           }
        | cte {
               $$ = new NodoHoja($1.sval);
               System.out.println("factorCTE---$$ : " + $$ + " $1 :" + $1);
              }  
;
cte : ENTERO {  chequearRangoI32($1.sval);}
        | FLOAT {}
        | RESTA ENTERO {chequearRangoI32Neg($2.sval);}
        | RESTA FLOAT 

;
sentencia_if : IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF{
                                                                                                                                $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE sentencia_ejecutable PUNTOCOMA END_IF{
                                                                                                                                $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                                                                                                                                System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA ELSE sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF", (ArbolSintactico) $3, new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                                                                                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");}
                | IF PARENT_A condicion PARENT_C THEN sentencia_ejecutable PUNTOCOMA END_IF {$$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                                                                                        System.out.println("Sentencia IF sin corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF {
                                                                                                                                $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                                                                                                                                System.out.println("Sentencia IF con corchetes y else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF {
                                        $$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                                        System.out.println("Sentencia IF con corchetes y sin else");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {yyerror("Se esperaba una condicion ");}
                | IF error {yyerror("Se esperaba ( ");}
;


condicion : expresion comparacion expresion {$$= new NodoComun($2.sval,(ArbolSintactico)$1,(ArbolSintactico)$3);}
        | expresion comparacion error {yyerror("Se esperaba otra expresion para comparar.");}
        | expresion error expresion {yyerror("Se esperaba un tipo de comparacion.");}
;
comparacion: IGUAL {$$= $1.sval;}
        | MAYOR {$$= $1.sval;}
        | MENOR {$$= $1.sval;}
        | MENORIGUAL {$$= $1.sval;}
        | MAYORIGUAL {$$= $1.sval;}
;
bloque_ejecutable : {$$=new NodoHoja("Fin");}
                | bloque_ejecutable sentencia_ejecutable PUNTOCOMA {
                                                                $$=new NodoComun("Bloque Ejecutable", (ArbolSintactico) $2, (ArbolSintactico) $1);
                                                                }
                | bloque_ejecutable sentencia_ejecutable {yyerror("Se esperaba ;");}
;
sentencia_out : OUT PARENT_A CADENA PARENT_C {
                        $$ = new NodoControl($1.sval, (ArbolSintactico) new NodoHoja($3.sval));
                        System.out.println("Sentencia OUT");}
                |  OUT PARENT_A CADENA error {yyerror("Se esperaba )");}
                |  OUT PARENT_A error {yyerror("Se esperaba una CADENA");}
                | OUT error {yyerror("Se esperaba (");}
;
sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C {
                        $$ = new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $7);
                        System.out.println("Sentencia WHEN con llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN sentencia_ejecutable {
                        $$ = (ArbolSintactico) new NodoComun("When",(ArbolSintactico) $3, (ArbolSintactico) $6);
                        System.out.println("Sentencia WHEN sin llaves");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error {yyerror("Se esperaba } en el when");}
                | WHEN PARENT_A condicion PARENT_C THEN error bloque_ejecutable LLAVE_C {yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error LLAVE_A bloque_ejecutable LLAVE_C {yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A error PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C {yyerror("Se esperaba condicion en el when");}
                | WHEN error condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C {yyerror("Se esperaba ( en el when");}
                | WHEN PARENT_A condicion THEN LLAVE_A bloque_ejecutable LLAVE_C{yyerror("Se esperaba ) en el when");}
                | WHEN PARENT_A condicion PARENT_C error {yyerror("Se esperaba then en el when");}
;
sentencia_while :  ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                        $$ = new NodoComun("While con Etiqueta",(ArbolSintactico) new NodoControl("Etiqueta", (ArbolSintactico) new NodoHoja($1.sval)) , (ArbolSintactico) new NodoComun("While", (ArbolSintactico) $5, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $12 , (ArbolSintactico) $9)) );
                        System.out.println("Sentencia WHILE con etiqueta y con llaves");}
                | ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C ejecutables_break_continue {
                        $$ = new NodoComun("While con Etiqueta",(ArbolSintactico) new NodoControl("Etiqueta", (ArbolSintactico) new NodoHoja($1.sval)) , (ArbolSintactico) new NodoComun("While", (ArbolSintactico) $5, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $11 , (ArbolSintactico) $9)) );
                        System.out.println("Sentencia WHILE con etiqueta y sin llaves");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $10 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE con llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C ejecutables_break_continue {
                        $$ = new NodoComun("While", (ArbolSintactico) $3, (ArbolSintactico) new NodoComun("Cuerpo - Asignacion", (ArbolSintactico) $9 , (ArbolSintactico) $7) );
                        System.out.println("Sentencia WHILE sin llaves");} 
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error {yyerror("Se esperaba {");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error {yyerror("Se esperaba )");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error {yyerror("Se esperaba una asignacion");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS error {yyerror("Se esperaba (");}
                | WHILE PARENT_A condicion PARENT_C error {yyerror("Se esperaba :");}
                | WHILE PARENT_A condicion error {yyerror("Se esperaba )");}
                | WHILE PARENT_A error {yyerror("Se esperaba una condicion");}
                | WHILE error {yyerror("Se esperaba (");}
;
bloque_break_continue : {$$=new NodoHoja("Fin");}
        | bloque_break_continue ejecutables_break_continue PUNTOCOMA {
                $$ = new NodoComun("Bloque Break con Continue",(ArbolSintactico) $2, (ArbolSintactico) $1);
                }
        | bloque_break_continue ejecutables_break_continue {yyerror("Se esperaba ;");}
;

ejecutables_break_continue :  asignacion {$$ = $1;}
                | sentencia_if_break {$$ = $1;}
                | sentencia_out {$$ = $1;}
                | sentencia_when_break {$$ = $1;}
                | sentencia_while {$$ = $1;}
                | sentencia_for {$$ = $1;}
                | CONTINUE tag {$$ = new NodoControl("Continue",(ArbolSintactico)$2);}
                | BREAK {$$ = new NodoHoja("Break");}
                | BREAK cte {$$ = new NodoControl("Break", new NodoHoja($2.sval));}
;
tag : {$$ = new NodoHoja("Fin");}
        | DOSPUNTOS ID {$$ = new NodoControl("Tag", new NodoHoja($2.sval) );}
;
sentencia_when_break :  WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN ejecutables_break_continue {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue error {yyerror("Se esperaba } en el when");}
                | WHEN PARENT_A condicion PARENT_C THEN error bloque_break_continue LLAVE_C {yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error LLAVE_A bloque_break_continue LLAVE_C {yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A error PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C {yyerror("Se esperaba condicion en el when");}
                | WHEN error condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C {yyerror("Se esperaba ( en el when");}
                | WHEN PARENT_A condicion THEN LLAVE_A bloque_break_continue LLAVE_C{yyerror("Se esperaba ) en el when");}
                | WHEN PARENT_A condicion PARENT_C error {yyerror("Se esperaba then en el when");}
;
sentencia_if_break : IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $6), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then sin corchetes y else con corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $10))); 
                        System.out.println("Sentencia IF -> then con corchetes y else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA ELSE ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, new NodoComun("Cuerpo_IF",(ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6), (ArbolSintactico) new NodoControl("Else", (ArbolSintactico)$9)));
                        System.out.println("Sentencia IF sin corchetes y con else sin corchetes");
                        }
                | IF PARENT_A condicion PARENT_C THEN ejecutables_break_continue PUNTOCOMA END_IF
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then",(ArbolSintactico)$6) );
                        System.out.println("Sentencia IF sin corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF
                        {
                        $$= new NodoComun("IF", (ArbolSintactico) $3,(ArbolSintactico) new NodoComun("Cuerpo_IF",new NodoControl("Then", (ArbolSintactico) $7), new NodoControl("Else",(ArbolSintactico) $11))); 
                        System.out.println("Sentencia IF con corchetes y else");
                        } 
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C END_IF 
                        {
                        $$ = new NodoComun("IF", (ArbolSintactico) $3, (ArbolSintactico) new NodoControl("Then", (ArbolSintactico)$7));
                        System.out.println("Sentencia IF con corchetes y sin else");
                        }
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {yyerror("Se esperaba una condicion ");}
                | IF error {yyerror("Se esperaba ( ");}
;

sentencia_for :ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");
                                                                                                        $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$13,new NodoHoja($9.sval + $10.sval))));
                                                                                                       }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");
                                                                                                        $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$13,new NodoHoja($9.sval + $10.sval))));
                                                                                                       }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C ejecutables_break_continue{System.out.println("Sentencia FOR");
                                                                                                                                $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$12,new NodoHoja($9.sval + $10.sval))));
                                                                                                                                }
                |ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C ejecutables_break_continue{System.out.println("Sentencia FOR");
                                                                                                                                $$ = new NodoComun("For con Etiqueta",new NodoControl("Etiqueta",new NodoHoja($1.sval)),new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$5,(ArbolSintactico)$7),new NodoComun("Cuerpo FOR",(ArbolSintactico)$12,new NodoHoja($9.sval + $10.sval))));
                                                                                                                                }
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C error {yyerror("Se esperaba {");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C error {yyerror("Se esperaba {");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte error {yyerror("Se esperaba )");}
                | ID DOSPUNTOS FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte error {yyerror("Se esperaba )");}
                | ID DOSPUNTOS error {yyerror("Se esperaba (");}
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");
                                                                                             $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$11,new NodoHoja($7.sval + $8.sval)));
                                                                                             }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");
                                                                                             $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$11,new NodoHoja($7.sval + $8.sval)));
                                                                                             }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C ejecutables_break_continue {System.out.println("Sentencia FOR");
                                                                                    $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$10,new NodoHoja($7.sval + $8.sval)));
                                                                                  }
                | FOR PARENT_A  asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C ejecutables_break_continue {System.out.println("Sentencia FOR");
                                                                                    $$ = new NodoComun("FOR",new NodoComun("Encabezado FOR",(ArbolSintactico)$3,(ArbolSintactico)$5),new NodoComun("Cuerpo FOR",(ArbolSintactico)$10,new NodoHoja($7.sval + $8.sval)));
                                                                                  }
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte error {yyerror("Se esperaba )");}
                | FOR PARENT_A asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte error {yyerror("Se esperaba )");}
                | FOR error {yyerror("Se esperaba (");}
;

param_real : cte{$$ = new NodoHoja($1.sval);}
                | ID {$$=new NodoHoja($1.sval);}
;
llamado_func: ID PARENT_A param_real COMA param_real PARENT_C {$$=new NodoComun("llamado funcion",(ArbolSintactico)$3,(ArbolSintactico)$5);}
        | ID PARENT_A param_real PARENT_C {$$=new NodoComun("llamado funcion",(ArbolSintactico)$3,new NodoHoja("Un solo parametro"));}
        | ID PARENT_A PARENT_C {$$=new NodoHoja("llamado funcion sin parametros");}
        | ID PARENT_A param_real COMA param_real error {yyerror("Se esperaba )");}
        | ID PARENT_A param_real error {yyerror("Se esperaba )");}
        | ID PARENT_A error {yyerror("Se esperaba )");}
;
%%
private NodoControl raiz;
private String ambitoActual = "Global";
private Map<String,ArbolSintactico> funciones = new HashMap<String,ArbolSintactico>();

void yyerror(String mensaje){
        System.out.println("Linea"+ AnalizadorLexico.getLineaAct() +"| Error sintactico: " + mensaje);
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