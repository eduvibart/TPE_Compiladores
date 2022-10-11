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
                | bloque_sentencias sentencia {yyerror("Se esperaba ;");}
;
sentencia : sentencia_declarativa
        | sentencia_ejecutable
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
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID error {yyerror("Se esperaba {");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error  {yyerror("Se esperaba ID");}
                | FUN ID PARENT_A parametro COMA parametro PARENT_C error {yyerror("Se esperaba :");}
                | FUN ID PARENT_A parametro COMA parametro error {yyerror("Se esperaba )");}
                | FUN ID PARENT_A parametro error {yyerror("Se esperaba )");}
                | FUN ID PARENT_A error  {yyerror("Se esperaba )");}
                | FUN ID error {System.out.println("Falta declaracion de parametro en la funcion");}
                | FUN error {System.out.println("Si declaras una funcion hacelo bien!");}
;
cuerpo_fun : 
                | cuerpo_fun sentencias_fun PUNTOCOMA
                | cuerpo_fun sentencias_fun {System.out.println("Se esperaba ;");}
;
sentencias_fun :  sentencia_decl_datos 
                | sentencia_decl_fun 
                | lista_const  
                | asignacion 
                | llamado_func
                | sentencia_if_fun 
                | sentencia_out 
                | sentencia_when_fun 
                | sentencia_for_fun 
                | sentencia_while_fun 
                | retorno 
;
sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF {System.out.println("Sentencia IF");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C error {yyerror("Se esperaba end_if ");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {yyerror("Se esperaba } ");}
                | IF PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba { ");}
                | IF PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | IF PARENT_A condicion error {yyerror("Se esperaba ) ");}
                | IF PARENT_A  error {yyerror("Se esperaba una condicion ");}
                | IF error {yyerror("Se esperaba ( ");}
;
sentencia_when_fun: WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error {yyerror("Se esperaba }");}
                | WHEN PARENT_A condicion PARENT_C THEN error {yyerror("Se esperaba {");}
                | WHEN PARENT_A condicion PARENT_C error {yyerror("Se esperaba then ");}
                | WHEN PARENT_A condicion error {yyerror("Se esperaba )");}
                | WHEN PARENT_A error {yyerror("Se esperaba condicion");}
                | WHEN error condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C {yyerror("Se esperaba (");}
; 
sentencia_while_fun : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia WHILE");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia WHILE");} 
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
sentencia_for_fun:  ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");}
                | FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C {System.out.println("Sentencia FOR");}
                | FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break error {yyerror("Se esperaba }");}
                | FOR PARENT_A encabezado_for PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A encabezado_for error {yyerror("Se esperaba )");}
                | FOR error {yyerror("Se esperaba (");}
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
        | llamado_func
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
cte : I32 {  chequearRangoI32($1.sval);}
        | F32
        | RESTA I32 
        | RESTA F32 

;

sentencia_if :IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF {System.out.println("Sentencia IF");}
                | IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF {System.out.println("Sentencia IF");}
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
condicion : expresion comparacion expresion
;
comparacion: IGUAL
        | MAYOR 
        | MENOR 
        | MENORIGUAL 
        | MAYORIGUAL
;
bloque_ejecutable : 
                | bloque_ejecutable sentencia_ejecutable PUNTOCOMA
                | bloque_ejecutable sentencia_ejecutable {yyerror("Se esperaba ;");}
;
sentencia_out : OUT PARENT_A CADENA PARENT_C {System.out.println("Sentencia OUT");}
                |  OUT PARENT_A CADENA error {yyerror("Se esperaba )");}
                |  OUT PARENT_A error {yyerror("Se esperaba una CADENA");}
                | OUT error {yyerror("Se esperaba (");}
;
sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C {System.out.println("Sentencia WHEN");}
                | WHEN PARENT_A condicion PARENT_C THEN error bloque_sentencias LLAVE_C {yyerror("Se esperaba { en el when");}
                | WHEN PARENT_A condicion PARENT_C error LLAVE_A bloque_sentencias LLAVE_C {yyerror("Se esperaba then en el when");}
                | WHEN PARENT_A error PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C {yyerror("Se esperaba condicion en el when");}
                | WHEN error condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C {yyerror("Se esperaba ( en el when");}
                | WHEN PARENT_A condicion THEN LLAVE_A bloque_sentencias LLAVE_C{yyerror("Se esperaba ) en el when");}
;
sentencia_while :  ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia WHILE");}
                | WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia WHILE");} 
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
sentencia_for :ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");}
                | FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C {System.out.println("Sentencia FOR");}
                | FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue error {yyerror("Se esperaba }");}
                | FOR PARENT_A encabezado_for PARENT_C error {yyerror("Se esperaba {");}
                | FOR PARENT_A encabezado_for error {yyerror("Se esperaba )");}
                | FOR error {yyerror("Se esperaba (");}
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
        | ID PARENT_A param_real COMA param_real error {System.out.println("Se esperaba )");}
        | ID PARENT_A param_real error {System.out.println("Se esperaba )");}
        | ID PARENT_A error {System.out.println("Se esperaba )");}
;
%%

void yyerror(String mensaje){
        System.out.println("Linea"+ AnalizadorLexico.getLineaAct() +"| Error sintactico: " + mensaje);
}
void chequearRangoI32(String sval){
  String s = "2147483648";
  long l = Long.valueOf(s);
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
