%{
packege 
import 

%}

%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C 
 COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG 
 CADENA COMENT CONST

%left '+' '-'
%left '*' '/'

%start program 

%% 

nombre_program : ID 
;

etiqueta: ID 
;

tipo : I32 | F32
;

list_var : ID 
        | list_var COMA ID 
;

sentencia_decl_datos : tipo list_var PUNTOCOMA
                        | sentencia_decl_datos tipo list_var PUNTOCOMA
;

parametro : tipo ID 
            | tipo {agregarError("Se esperaba tipo del parametro")}
            | ID {agregarError("Se esperaba nombre del parametro")}
;

cte : I32 
        | F32
;

factor: ID
        | cte
        | '-' cte {chequear rango}
;

termino: termino '*' factor     
        | termino '/' factor    
        | factor
;

expresion: expresion '+' termino
        | expresion '-' termino
        | termino
        | llamado_func
;

retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA
;

asignacion : ID ASIG expresion
;

comparacion: IGUAL
        | MAYOR 
        | MENOR 
        | MENORIGUAL 
        | MAYORIGUAL
;

condicion : expresion comparacion expresion
;

sentencia_out : OUT PARENT_A CADENA PARENT_C
;

bloque_ejecutable : bloque_ejecutable sentencia_ejecutable
                | sentencia_ejecutable 
; 
//PROBLEMON con sentencia_ejecutable bloque_ejecutable sentencia_if cual pongo antes??

sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_ejecutable
;

sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF
        | IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF
;

encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID 
        | asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID 
;

sentencia_continue : CONTINUE   
                | CONTINUE DOSPUNTOS etiqueta
;

bloque_while : bloque_ejecutable BREAK CTE PUNTOCOMA
        | bloque_ejecutable BREAK PUNTOCOMA
        | bloque_ejecutable
        | BREAK CTE PUNTOCOMA bloque_ejecutable
        | BREAK PUNTOCOMA bloque_ejecutable
        | bloque_while bloque_ejecutable
        | BREAK PUNTOCOMA
        | BREAK CTE PUNTOCOMA
        | sentencia_continue PUNTOCOMA
        | bloque_ejecutable sentencia_continue PUNTOCOMA
        | sentencia_continue PUNTOCOMA bloque_ejecutable 
;

sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_while
;

sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_while LLAVE_C 
;

sentencia_control : etiqueta DOSPUNTOS sentencia_for
                | etiqueta DOSPUNTOS sentencia_while
                | sentencia_for
                | sentencia_while
;

sentencia_ejecutable : asignacion PUNTOCOMA
        | sentencia_if PUNTOCOMA
        | sentencia_out PUNTOCOMA
        | sentencia_when PUNTOCOMA
        | sentencia_control PUNTOCOMA
;

bloque_ejecutable_funcion : sentencia_ejecutable retorno
                | sentencia_ejecutable
;

sentencia_declarativa : sentencia_decl_datos 
                        | sentencia_decl_fun
                        | lista_const
;

cuerpo_fun : cuerpo_fun bloque_ejecutable_funcion
                | cuerpo_fun sentencia_declarativa
                | bloque bloque_ejecutable_funcion
                | sentencia_declarativa
;

sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C
                    | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C
                    | FUN ID PARENT_A PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C
;



sentencia : sentencia sentencia_declarativa
            | sentencia sentencia_ejecutable 
            | sentencia_declarativa 
            | sentencia_ejecutable 
;

program : nombre_program LLAVE_A sentencia LLAVE_C PUNTOCOMA 
        | nombre_program LLAVE_A  LLAVE_C PUNTOCOMA {agregarError("Faltan declarar sentencia/s en el programa")}
        | nombre_program LLAVE_A sentencia {agregarError("Falta llave de cierre")}
        | LLAVE_A  {agregarError("Se espera nombre de programa")}
        | PUNTOCOMA {agregarError("Se espera programa")}
;

list_param_real : list_param_real COMA ID 
        | list_param_real COMA cte
        | cte
        | ID
;

llamado_func: ID PARENT_A list_param_real PARENT_C
        | ID PARENT_A PARENT_C
;

lista_asignacion : lista_asignacion, asignacion
        | asignacion
;

lista_const : CONST lista_asignacion PUNTOCOMA
;
