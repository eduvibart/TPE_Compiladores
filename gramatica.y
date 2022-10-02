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
;
sentencia_declarativa : sentencia_decl_datos 
                        | sentencia_decl_fun
                        | lista_const
;
sentencia_decl_datos : ID list_var 
;
list_var : list_var COMA ID 
        |  ID
;
sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C
                | FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C
                | FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C
;
retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA
;
parametro : ID ID
;
cuerpo_fun : 
        | cuerpo_fun bloque_sentencias retorno
;
lista_const : CONST lista_asignacion 
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
asignacion : ID ASIG expresion
;
expresion: expresion SUMA termino
        | expresion RESTA termino
        | termino
        | llamado_func
        | sentencia_for {/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
        | sentencia_while {/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
        
;
termino: termino MULT factor     
        | termino DIV factor    
        | factor
;
factor: RESTA cte
        | ID
        | cte      
;
cte : I32 
        | F32
;

sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF
        | IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF
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
                | LLAVE_A bloque_ejecutable sentencia_ejecutable PUNTOCOMA LLAVE_C
;
sentencia_out : OUT PARENT_A CADENA PARENT_C
;
sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C
;
sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C 
;
bloque_break_continue : 
        | bloque_break_continue ejecutables_break_continue PUNTOCOMA 
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

sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue ELSE bloque_break_continue END_IF
        | IF PARENT_A condicion PARENT_C THEN bloque_break_continue END_IF
;
sentencia_for : FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C
;

encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA SUMA ID 
        | asignacion PUNTOCOMA comparacion PUNTOCOMA RESTA ID 
;
list_param_real : list_param_real COMA ID 
        | list_param_real COMA cte
        | cte
        | ID
;
llamado_func: ID PARENT_A list_param_real PARENT_C
        | ID PARENT_A PARENT_C
;
%%
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        System.out.println("NUEVO TOKEN");
        System.out.println(t.getId());
        System.out.println(t.getLexema());
        return t.getId();
}

public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
        AnalizadorLexico.setEntrada(entrada);
        Parser parser = new Parser();
        parser.run();
}