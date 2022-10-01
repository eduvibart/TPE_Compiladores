%token IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN WHILE FOR CONTINUE ID I32 F32 PUNTO PARENT_A PARENT_C COMILLA COMA DOSPUNTOS PUNTOCOMA IGUAL MAYOR MENOR MENORIGUAL MAYORIGUAL LLAVE_A LLAVE_C EXCL DIST ASIG CADENA COMENT CONST

%start program 

%left '+' '-'
%left '*' '/'

%% 

nombre_program : ID 
;

list_var : list_var COMA ID 
        |  ID
;

sentencia_decl_datos : sentencia_decl_datos ID list_var PUNTOCOMA
                        |ID list_var PUNTOCOMA 
;

parametro : ID ID
;

cte : I32 
        | F32
;

factor: '-' cte
        | ID
        | cte
        
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

sentencia_ejecutable : asignacion PUNTOCOMA
        | sentencia_if PUNTOCOMA
        | sentencia_out PUNTOCOMA
        | sentencia_when PUNTOCOMA
        | sentencia_control PUNTOCOMA
;

bloque_ejecutable : 
                | bloque_ejecutable sentencia_ejecutable
                | sentencia_ejecutable 
;

sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_ejecutable
;

sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF
        | IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF
;

encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID 
        | asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID 
;

sentencia_continue : CONTINUE DOSPUNTOS ID
                | CONTINUE 
;

bloque_while : sentencia_continue
;

sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_while
;

sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_while LLAVE_C 
;

sentencia_control : ID DOSPUNTOS sentencia_for
                | ID DOSPUNTOS sentencia_while
                | sentencia_for
                | sentencia_while
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
                | bloque_ejecutable_funcion
                | sentencia_declarativa
;

sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C
                    | FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C
                    | FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C
;



sentencia : sentencia sentencia_declarativa
            | sentencia sentencia_ejecutable 
            | sentencia_declarativa 
            | sentencia_ejecutable 
;

program : nombre_program LLAVE_A sentencia LLAVE_C PUNTOCOMA
        |nombre_program LLAVE_A LLAVE_C PUNTOCOMA
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
%%

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}

int yylex() throws IOException{
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
        AnalizadorLexico a = new AnalizadorLexico(entrada);
        Token t = a.getToken();
        return t.getId();
}

public static void main(String[] args) {
        Parser parser = new Parser();
        parser.run();
}