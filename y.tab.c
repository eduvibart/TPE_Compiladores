#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#define IF 257
#define THEN 258
#define ELSE 259
#define END_IF 260
#define OUT 261
#define FUN 262
#define RETURN 263
#define BREAK 264
#define WHEN 265
#define WHILE 266
#define FOR 267
#define CONTINUE 268
#define ID 269
#define I32 270
#define F32 271
#define PUNTO 272
#define PARENT_A 273
#define PARENT_C 274
#define COMILLA 275
#define COMA 276
#define DOSPUNTOS 277
#define PUNTOCOMA 278
#define IGUAL 279
#define MAYOR 280
#define MENOR 281
#define MENORIGUAL 282
#define MAYORIGUAL 283
#define LLAVE_A 284
#define LLAVE_C 285
#define EXCL 286
#define DIST 287
#define ASIG 288
#define CADENA 289
#define COMENT 290
#define CONST 291
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    2,    2,    3,    3,    4,    4,    4,    6,
    9,    9,    7,    7,    7,   12,   10,   11,   11,   11,
   11,   14,   14,    8,   15,   15,    5,    5,    5,    5,
    5,    5,   16,   13,   13,   13,   13,   13,   13,   22,
   22,   22,   24,   24,   24,   25,   25,   17,   17,   26,
   28,   28,   28,   28,   28,   27,   27,   18,   19,   21,
   29,   29,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   32,   32,   31,   31,   20,   33,   33,   34,   34,
   34,   34,   23,   23,
};
short yylen[] = {                                         2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    2,
    3,    1,   13,   11,   10,    5,    2,    2,    2,    1,
    1,    2,    1,    2,    3,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    1,    1,    1,    1,    3,
    3,    1,    2,    1,    1,    1,    1,    9,    7,    3,
    1,    1,    1,    1,    1,    0,    3,    4,    6,   11,
    0,    3,    1,    1,    1,    1,    1,    1,    2,    1,
    2,    0,    2,    9,    7,    5,    6,    6,    3,    3,
    1,    1,    4,    3,
};
short yydefred[] = {                                      0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    5,    6,    7,    8,    9,
   27,   28,   29,   30,   31,   32,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,   26,    4,    0,
   46,   47,    0,    0,   38,   39,    0,   37,   42,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   43,   51,   52,   53,   54,   55,    0,    0,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,    0,
   61,   11,   25,   82,   84,   81,    0,   44,    0,    0,
    0,   40,   41,   56,   17,    0,    0,    0,    3,    0,
    0,    0,   83,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,   65,   66,   68,   67,    0,
   64,   79,   80,   56,   49,    0,    0,    0,    0,    0,
    0,    0,    0,   71,    0,   69,   62,    0,   57,   21,
    0,    0,   20,    0,    0,    0,   77,   78,    0,   73,
   48,    0,   22,   19,    0,   18,    0,    0,   61,    0,
    0,   15,    0,    0,    0,   61,    0,   14,    0,   60,
    0,    0,    0,   61,   75,   16,   13,    0,   74,
};
short yydgoto[] = {                                       2,
    3,    5,   15,  140,  141,   18,   19,   20,   35,   77,
  142,  153,   44,  143,   37,   21,   22,   23,   24,   25,
   26,   47,   48,   49,   50,   51,  105,   70,  102,  120,
  121,  136,   57,   87,
};
short yysindex[] = {                                   -259,
    0,    0, -261,    0, -127, -220, -217, -209, -206, -164,
 -157, -249,    0, -205, -156,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   79, -202, -152,   79,
   79, -205,    0,   79, -147, -155, -145,    0,    0, -141,
    0,    0, -190,   46,    0,    0,   26,    0,    0,    0,
 -137, -131, -186, -128, -126, -117, -109,  -12, -122, -205,
 -151,    0,    0,    0,    0,    0,    0,  -26,  -26,   79,
  -26,  -26,  -91,    0, -101, -103, -239,  -89, -102, -129,
    0,    0,    0,    0,    0,    0, -181,    0,   26,   26,
  -12,    0,    0,    0,    0,  -90,  -94,  -84,    0,  -87,
  -88,  160,    0, -144,  173,  -96,  -82,  -80, -191, -205,
   62,  -78, -190,  -74,    0,    0,    0,    0,    0,  -79,
    0,    0,    0,    0,    0,  -73, -191,  -77,  -69,  -64,
  -60,  -58,   79,    0,  -57,    0,    0,  184,    0,    0,
  -49,   94,    0, -191,  -53,  -67,    0,    0,  -56,    0,
    0,  -51,    0,    0,  -55,    0,   94,  -31,    0,  -30,
   79,    0,  -54, -191,  112,    0,  -41,    0,   94,    0,
  134,  -43,  -29,    0,    0,    0,    0,  147,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -219,    0,  -65,    0,    0,  -42,
    0,    0,    0,    0,    0,    0,  -15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,   39,
  -45,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,    0, -246,    0,
    0,    0,  -23,  -21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  105,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
    0,  159,    0,    2,    3,    0,    0,    0,    0,  162,
 -123, -108,  -25, -106,    0,   -8,    0,  -86,  -75,  -16,
   -5,   30,    0,   42,   -3,  -18,  138,  190,  -97,    0,
    0,    0,    0,    0,
};
#define YYTABLESIZE 453
short yytable[] = {                                      44,
   44,   68,   44,   69,   44,   38,   16,   17,   58,    1,
   45,   54,   55,   45,   45,  116,   59,   45,   43,   33,
  157,   46,    4,   56,   46,   46,  117,   36,   46,   36,
   68,   59,   69,  155,   97,  156,   98,   10,   34,   62,
  169,   10,   10,   10,   91,   10,   10,   10,  163,   10,
  156,   83,   27,   45,   34,   28,   34,   86,   10,   29,
  173,  165,  156,   36,   46,    6,   30,   71,  171,    7,
    8,   10,   72,    9,   10,   11,  178,   12,  116,   41,
   42,   35,   75,   35,  116,  118,   52,   76,   68,  117,
   69,  116,  103,  115,  104,  117,  119,   89,   90,   14,
  123,  130,  117,   76,  131,   76,  132,  126,   31,  134,
   16,   17,   92,   93,  149,   32,   45,   84,   41,   42,
   53,   39,   85,   43,  122,   41,   42,   46,   59,    6,
   60,   61,   34,    7,    8,  167,   73,    9,   10,   11,
  126,   12,   74,  154,   45,   78,   82,   79,  118,   63,
   64,   65,   66,   67,  118,   46,  115,   13,  154,  119,
   80,  118,  115,   14,   81,  119,   94,   95,   99,  115,
  154,   33,  119,   96,  100,   33,   33,   33,  106,   33,
   33,   33,  107,   33,   75,  110,  128,  127,   33,  111,
   33,   24,   33,  129,  133,   24,   24,   24,  137,   24,
   24,   24,  135,   24,  139,   33,  144,  145,  147,  146,
  148,  150,   24,  152,   44,  158,  159,  160,   44,   44,
   44,  161,   44,   44,   44,   24,   44,  166,   50,  162,
  168,   44,  172,   44,  176,   44,   44,   44,   44,   44,
   44,   36,   88,   41,   42,   36,   36,   36,   44,   36,
   36,   36,  164,   36,   70,  177,   72,  109,   36,  108,
   36,  138,   36,   36,   36,   36,   36,   36,   34,  101,
    0,    0,   34,   34,   34,   36,   34,   34,   34,    0,
   34,    0,    0,    0,    0,   34,    0,   34,    0,   34,
   34,   34,   34,   34,   34,   35,    0,    0,    0,   35,
   35,   35,   34,   35,   35,   35,    0,   35,    0,    0,
    0,    0,   35,    0,   35,    0,   35,   35,   35,   35,
   35,   35,   76,   76,   63,   64,   65,   66,   67,   35,
    0,    0,    0,    0,   76,    0,   76,    0,   76,   76,
   76,   76,   76,   76,   10,   11,    0,   40,   41,   42,
    6,   76,    0,    0,    7,    8,  152,    0,    9,   10,
   11,   23,   12,    0,    0,   23,   23,    0,  112,   23,
   23,   23,    7,   23,    0,  113,    9,   10,   11,  114,
   36,    0,    0,    0,   14,    0,    0,    0,    0,    0,
  112,    0,  174,  175,    7,   23,  170,  113,    9,   10,
   11,  114,   36,  112,    0,    0,  179,    7,    0,    0,
  113,    9,   10,   11,  114,   36,  112,    0,    0,    0,
    7,    0,    0,  113,    9,   10,   11,  114,   36,    6,
    0,  124,  125,    7,    0,    0,    0,    9,   10,   11,
    6,   36,    0,  151,    7,    0,    0,    0,    9,   10,
   11,    0,   36,
};
short yycheck[] = {                                      42,
   43,   43,   45,   45,   47,   14,    5,    5,   34,  269,
   27,   30,   31,   30,   31,  102,  263,   34,   45,  269,
  144,   27,  284,   32,   30,   31,  102,   43,   34,   45,
   43,  278,   45,  142,  274,  142,  276,  257,  288,   43,
  164,  261,  262,  263,   70,  265,  266,  267,  157,  269,
  157,   60,  273,   70,   43,  273,   45,   61,  278,  269,
  169,  159,  169,  269,   70,  257,  273,   42,  166,  261,
  262,  291,   47,  265,  266,  267,  174,  269,  165,  270,
  271,   43,  269,   45,  171,  102,  289,  274,   43,  165,
   45,  178,  274,  102,  276,  171,  102,   68,   69,  291,
  104,  110,  178,   43,   43,   45,   45,  105,  273,  113,
  109,  109,   71,   72,  133,  273,  133,  269,  270,  271,
  273,  278,  274,   45,  269,  270,  271,  133,  276,  257,
  276,  273,  288,  261,  262,  161,  274,  265,  266,  267,
  138,  269,  274,  142,  161,  274,  269,  274,  165,  279,
  280,  281,  282,  283,  171,  161,  165,  285,  157,  165,
  278,  178,  171,  291,  274,  171,  258,  269,  258,  178,
  169,  257,  178,  277,  277,  261,  262,  263,  269,  265,
  266,  267,  277,  269,  269,  273,  269,  284,  274,  278,
  276,  257,  278,  274,  273,  261,  262,  263,  278,  265,
  266,  267,  277,  269,  278,  291,  284,  277,  269,  274,
  269,  269,  278,  263,  257,  269,  284,  274,  261,  262,
  263,  273,  265,  266,  267,  291,  269,  258,  274,  285,
  285,  274,  274,  276,  278,  278,  279,  280,  281,  282,
  283,  257,  269,  270,  271,  261,  262,  263,  291,  265,
  266,  267,  284,  269,  278,  285,  278,   99,  274,   98,
  276,  124,  278,  279,  280,  281,  282,  283,  257,   80,
   -1,   -1,  261,  262,  263,  291,  265,  266,  267,   -1,
  269,   -1,   -1,   -1,   -1,  274,   -1,  276,   -1,  278,
  279,  280,  281,  282,  283,  257,   -1,   -1,   -1,  261,
  262,  263,  291,  265,  266,  267,   -1,  269,   -1,   -1,
   -1,   -1,  274,   -1,  276,   -1,  278,  279,  280,  281,
  282,  283,  262,  263,  279,  280,  281,  282,  283,  291,
   -1,   -1,   -1,   -1,  274,   -1,  276,   -1,  278,  279,
  280,  281,  282,  283,  266,  267,   -1,  269,  270,  271,
  257,  291,   -1,   -1,  261,  262,  263,   -1,  265,  266,
  267,  257,  269,   -1,   -1,  261,  262,   -1,  257,  265,
  266,  267,  261,  269,   -1,  264,  265,  266,  267,  268,
  269,   -1,   -1,   -1,  291,   -1,   -1,   -1,   -1,   -1,
  257,   -1,  259,  260,  261,  291,  285,  264,  265,  266,
  267,  268,  269,  257,   -1,   -1,  260,  261,   -1,   -1,
  264,  265,  266,  267,  268,  269,  257,   -1,   -1,   -1,
  261,   -1,   -1,  264,  265,  266,  267,  268,  269,  257,
   -1,  259,  260,  261,   -1,   -1,   -1,  265,  266,  267,
  257,  269,   -1,  260,  261,   -1,   -1,   -1,  265,  266,
  267,   -1,  269,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 1
#endif
#define YYMAXTOKEN 291
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,"'*'","'+'",0,"'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"IF","THEN",
"ELSE","END_IF","OUT","FUN","RETURN","BREAK","WHEN","WHILE","FOR","CONTINUE",
"ID","I32","F32","PUNTO","PARENT_A","PARENT_C","COMILLA","COMA","DOSPUNTOS",
"PUNTOCOMA","IGUAL","MAYOR","MENOR","MENORIGUAL","MAYORIGUAL","LLAVE_A",
"LLAVE_C","EXCL","DIST","ASIG","CADENA","COMENT","CONST",
};
char *yyrule[] = {
"$accept : program",
"program : nombre_program LLAVE_A bloque_sentencias LLAVE_C",
"nombre_program : ID",
"bloque_sentencias :",
"bloque_sentencias : bloque_sentencias sentencia PUNTOCOMA",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : sentencia_decl_datos",
"sentencia_declarativa : sentencia_decl_fun",
"sentencia_declarativa : lista_const",
"sentencia_decl_datos : ID list_var",
"list_var : list_var COMA ID",
"list_var : ID",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA",
"parametro : ID ID",
"cuerpo_fun : cuerpo_fun bloque_ejecutable_funcion",
"cuerpo_fun : cuerpo_fun sentencia_declarativa",
"cuerpo_fun : bloque_ejecutable_funcion",
"cuerpo_fun : sentencia_declarativa",
"bloque_ejecutable_funcion : sentencia_ejecutable retorno",
"bloque_ejecutable_funcion : sentencia_ejecutable",
"lista_const : CONST lista_asignacion",
"lista_asignacion : lista_asignacion COMA asignacion",
"lista_asignacion : asignacion",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_if",
"sentencia_ejecutable : sentencia_out",
"sentencia_ejecutable : sentencia_when",
"sentencia_ejecutable : sentencia_for",
"sentencia_ejecutable : sentencia_while",
"asignacion : ID ASIG expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : llamado_func",
"expresion : sentencia_for",
"expresion : sentencia_while",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : '-' cte",
"factor : ID",
"factor : cte",
"cte : I32",
"cte : F32",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF",
"condicion : expresion comparacion expresion",
"comparacion : IGUAL",
"comparacion : MAYOR",
"comparacion : MENOR",
"comparacion : MENORIGUAL",
"comparacion : MAYORIGUAL",
"bloque_ejecutable :",
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable PUNTOCOMA",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_sentencias",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"bloque_break_continue :",
"bloque_break_continue : bloque_break_continue ejecutables_break_continue PUNTOCOMA",
"ejecutables_break_continue : asignacion",
"ejecutables_break_continue : sentencia_if_break",
"ejecutables_break_continue : sentencia_out",
"ejecutables_break_continue : sentencia_when",
"ejecutables_break_continue : sentencia_while",
"ejecutables_break_continue : sentencia_for",
"ejecutables_break_continue : CONTINUE tag",
"ejecutables_break_continue : BREAK",
"ejecutables_break_continue : BREAK cte",
"tag :",
"tag : DOSPUNTOS ID",
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue ELSE bloque_break_continue END_IF",
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue END_IF",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_break_continue",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 134 "gramatica.y"

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
#line 385 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 38:
#line 62 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 39:
#line 63 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
#line 533 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
