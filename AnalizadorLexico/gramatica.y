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
program : header_program sentencias ';' 


sentencias : sentencias sentencia_declarativa sentencia_ejecutable 
            |  