%{
packege 
import 

%}

%token IF THEN ELSE END-IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C 
 COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG 
 CADENA COMENT

%left '+' '-'
%left '*' '/'

%start program 

%% 
program : nombre_program LLAVE_A sentencia LLAVE_C ';' 
        | nombre_program LLAVE_A  LLAVE_C ';' {agregarError("Faltan declarar sentencia/s en el programa")}
        | nombre_program LLAVE_A sentencia {agregarError("Falta llave de cierre")}
        | LLAVE_A  {agregarError("Se espera nombre de programa")}
        | ';' {agregarError("Se espera programa")}

nombre_program : ID 

sentencia : sentencia sentencia_declarativa
            | sentencia sentencia_ejecutable 
            | sentencia_declarativa 
            | sentencia_ejecutable 

sentencia_declarativa : sentencia_declarativa sentencia_decl_datos
                        | sentencia_declarativa sentencia_decl_fun
                        | sentencia_decl_datos 
                        | sentencia_decl_fun

sentencia_decl_datos : tipo list_var ';'
                        | sentencia_decl_datos tipo list_var ';'

tipo : I32 | F32

list_var : ID 
        | list_var ',' ID 

sentencia_decl_fun : FUN ID '(' parametro ')' DOSPUNTOS tipo cuerpo_fun
                    | FUN ID '(' parametro COMA parametro ')' DOSPUNTOS tipo cuerpo_fun
                    | FUN ID '(' ')' DOSPUNTOS tipo cuerpo_fun

parametro : tipo ID 
            | tipo {agregarError("Se esperaba tipo del parametro")}
            | ID {agregarError("Se esperaba nombre del parametro")}

cuerpo_fun : 