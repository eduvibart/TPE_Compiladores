%{
package Principal;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
%}

%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG CADENA COMENT CONST SUMA RESTA MULT DIV

%start program 

%left SUMA RESTA
%left MULT DIV

%% 
program : nombre_program LLAVE_A bloque_sentencias LLAVE_C
;
nombre_program : ID 
;
bloque_sentencias :
                  | bloque_sentencias sentencia PUNTOCOMA
;
sentencia : sentencia_declarativa
        | sentencia_ejecutable 
        | error {System.out.println("La sentencia no esta definida.");}
;
sentencia_declarativa : sentencia_decl_datos 
                        | sentencia_decl_fun 
                        | lista_const  
;
sentencia_decl_datos : ID list_var {System.out.println("Declaracion de datos");}
;
list_var : list_var COMA ID 
        |  ID
;
sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Declaracion de Funcion");}
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Declaracion de Funcion");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Declaracion de Funcion");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Se esperaba : luego de los parametros");}
                | FUN ID PARENT_A parametro PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Se esperaba : luego de los parametros");}
                | FUN ID PARENT_A PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Se esperaba : luego de los parametros");}
                | FUN ID PARENT_A PARENT_C ID  {System.out.println("Se esperaba : luego de los parametros");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Se esperaba ID luego de :");}
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Se esperaba ID luego de :");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Se esperaba ID luego de :");}
                | FUN ID PARENT_A PARENT_C DOSPUNTOS {System.out.println("Se esperaba ID luego de :");}
                | FUN ID PARENT_A parametro COMA parametro DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Falta ) luego de los parametros");}
                | FUN ID PARENT_A parametro DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Falta ) luego de los parametros");}
                | FUN ID PARENT_A DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Falta ) luego de los parametros");}
                | FUN ID PARENT_A {System.out.println("Falta ) luego de los parametros");}
                | FUN ID parametro {System.out.println("Falta parentesis en la declaracion de la funcion.");}
                | FUN ID parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C  {System.out.println("Falta { luego del nombre de funcion");}
                | FUN ID parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Falta { luego del nombre de funcion");}
                | FUN ID PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Falta { luego del nombre de funcion");}
                | FUN ID {System.out.println("Falta ( luego del nombre de funcion");}
                | FUN {System.out.println("La funcion esta mal declarada");}
;
cuerpo_fun : 
                | cuerpo_fun sentencias_fun PUNTOCOMA
;
sentencias_fun :  sentencia_decl_datos 
                | sentencia_decl_fun 
                | lista_const  
                | asignacion 
                | sentencia_if_fun 
                | sentencia_out 
                | sentencia_when_fun 
                | sentencia_for_fun 
                | sentencia_while_fun 
                | retorno 
;
sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");}
;
sentencia_when_fun: WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Sentencia WHEN");}
; 
sentencia_while_fun :  WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia WHILE");}
                | ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia WHILE");}
;
sentencia_for_fun: FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");}
                | ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");}
;
cuerpo_fun_break : 
                |cuerpo_fun_break sentencias_fun_break 
;
sentencias_fun_break :   asignacion 
                | sentencia_if_break_fun 
                | sentencia_out 
                | sentencia_when_fun 
                | sentencia_while_fun 
                | sentencia_for_fun 
                | CONTINUE tag 
                | BREAK 
                | BREAK cte 
                | retorno 

;
sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF {System.out.println("Sentencia IF");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C END_IF {System.out.println("Sentencia IF");}
;
retorno : RETURN PARENT_A expresion PARENT_C 
;
parametro : ID ID
;

lista_const : CONST lista_asignacion {System.out.println("Declaracion de Constante/s");}
;
lista_asignacion : lista_asignacion COMA asignacion
        | asignacion
;
sentencia_ejecutable : asignacion 
        | sentencia_if       
        | sentencia_out 
        | sentencia_when 
        | sentencia_for 
        | sentencia_while 
;
asignacion : ID ASIG expresion  {System.out.println("Asignacion");}
;
expresion: expresion SUMA termino
        | expresion RESTA termino
        | termino
        | llamado_func
        | sentencia_for ELSE cte
        | sentencia_while ELSE cte 
        
;
termino: termino MULT factor     
        | termino DIV factor    
        | factor
;
factor: ID
        | cte      
;
cte : I32 {   System.out.println("Valor de I32: " + $1.sval );
                        chequearRangoI32($1.sval);
                }
        | F32
        | RESTA I32 
        | RESTA F32 

;

sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF {System.out.println("Sentencia IF");}
        | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF{System.out.println("Sentencia IF");}
;
condicion : expresion comparacion expresion
;
comparacion: IGUAL
        | MAYOR 
        | MENOR 
        | MENORIGUAL 
        | MAYORIGUAL
;
bloque_ejecutable : 
                | bloque_ejecutable sentencia_ejecutable 
;
sentencia_out : OUT PARENT_A CADENA PARENT_C {System.out.println("Sentencia OUT");}
;
sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C {System.out.println("Sentencia WHEN");}
;
sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue {System.out.println("Sentencia WHILE");}
                | ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue {System.out.println("Sentencia WHILE");}
;
bloque_break_continue : 
        | LLAVE_A bloque_break_continue ejecutables_break_continue LLAVE_C
;

ejecutables_break_continue :  asignacion 
                | sentencia_if_break
                | sentencia_out 
                | sentencia_when
                | sentencia_while
                | sentencia_for
                | CONTINUE tag
                | BREAK 
                | BREAK cte
;
tag : 
        |DOSPUNTOS ID 
;

sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue ELSE bloque_break_continue END_IF {System.out.println("Sentencia IF");}
        | IF PARENT_A condicion PARENT_C THEN bloque_break_continue END_IF {System.out.println("Sentencia IF");}
;
sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_break_continue {System.out.println("Sentencia FOR");}
                | ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C bloque_break_continue {System.out.println("Sentencia FOR");}
;

encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte 
        | asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte 
;
param_real : cte
                | ID
;
llamado_func: ID PARENT_A param_real COMA param_real PARENT_C
        | ID PARENT_A param_real PARENT_C
        | ID PARENT_A PARENT_C
        |  ID PARENT_A param_real COMA param_real error PARENT_C {System.out.println("Una funcion no puede tener mas de dos parametros");}
;
%%

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}
void chequearRangoI32(String sval){
  String s = "2147483648";
  long l = Long.valueOf(s);
  System.out.println("S: " + s);
  System.out.println("sval: " + sval);
  if(Long.valueOf(sval) > l){
    System.out.println("La constante esta fuera de rango");
  }
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        this.yylval = new ParserVal(t.getLexema());
        if(t.getId() != -1){
          System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        }else
          System.out.println("TERMINO LA EJECUCION");
        return t.getId();
}
