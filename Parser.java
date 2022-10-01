//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";










public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short END_IF=260;
public final static short OUT=261;
public final static short FUN=262;
public final static short RETURN=263;
public final static short BREAK=264;
public final static short WHEN=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short CONTINUE=268;
public final static short ID=269;
public final static short I32=270;
public final static short F32=271;
public final static short PUNTO=272;
public final static short PARENT_A=273;
public final static short PARENT_C=274;
public final static short COMILLA=275;
public final static short COMA=276;
public final static short DOSPUNTOS=277;
public final static short PUNTOCOMA=278;
public final static short IGUAL=279;
public final static short MAYOR=280;
public final static short MENOR=281;
public final static short MENORIGUAL=282;
public final static short MAYORIGUAL=283;
public final static short LLAVE_A=284;
public final static short LLAVE_C=285;
public final static short EXCL=286;
public final static short DIST=287;
public final static short ASIG=288;
public final static short CADENA=289;
public final static short COMENT=290;
public final static short CONST=291;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    1,    2,    2,    3,    3,    4,    5,    5,    6,    6,
    6,    7,    7,    7,    8,    8,    8,    8,   10,   11,
   12,   12,   12,   12,   12,   13,   14,   15,   15,   15,
   15,   15,   19,   19,   19,   17,   16,   16,   20,   20,
   21,   21,   22,   23,   24,   18,   18,   18,   18,   25,
   25,   26,   26,   26,   29,   29,   29,   29,   27,   27,
   27,   30,   30,   30,   30,    0,    0,   31,   31,   31,
   31,    9,    9,   32,   32,   28,
};
final static short yylen[] = {                            2,
    1,    3,    1,    4,    3,    2,    1,    1,    2,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    5,    3,
    1,    1,    1,    1,    1,    3,    4,    2,    2,    2,
    2,    2,    0,    2,    1,    6,    9,    7,    6,    6,
    3,    1,    1,    5,   11,    3,    3,    1,    1,    2,
    1,    1,    1,    1,    2,    2,    1,    1,   11,   13,
   10,    2,    2,    1,    1,    5,    4,    3,    3,    1,
    1,    4,    3,    2,    1,    3,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,    0,    0,    0,
   48,   49,   64,   53,   54,    0,    0,    0,    0,    0,
    0,    0,    3,    0,    0,    0,   67,    0,   75,    0,
    0,   28,   30,   29,   31,   32,    0,   63,   62,    0,
    7,    8,    0,   11,   14,    0,    0,   18,    0,    0,
    0,    0,    0,    0,    0,   46,   47,    0,    0,    5,
   76,   74,    0,   66,    0,    9,    0,    0,   21,   22,
   23,   24,   25,    0,    0,    0,    0,   27,    0,    0,
    0,    0,    0,    0,    0,    2,    4,   71,   73,   70,
    0,   10,   12,   13,    0,    0,    0,    0,    6,    0,
    0,    0,    0,    0,    0,    0,   43,   44,   72,    0,
    0,   35,    0,    0,    0,    0,    0,    0,    0,    0,
   68,   69,    0,   38,   34,    0,    0,    0,    0,    0,
    0,   41,    0,    0,   57,   58,    0,    0,    0,    0,
   39,   40,   37,    0,   50,    0,   55,   56,    0,    0,
    0,    0,   61,    0,    0,    0,    0,   59,    0,   45,
    0,    0,   19,   60,
};
final static short yydgoto[] = {                          2,
    3,   36,   14,   91,   54,   55,   56,   57,   58,  155,
   15,   86,   59,   16,  144,   18,   19,   20,  123,   65,
  117,  118,   21,   22,  145,  146,   24,   25,  147,   26,
  101,   40,
};
final static short yysindex[] = {                      -258,
    0,    0, -267, -202, -232, -185, -153, -120, -113, -111,
 -187, -158,  -85,  -84,  -89,  -88,    0,  -87,  -86,  -83,
    0,    0,    0,    0,    0, -191,  -26, -112,  -80,  -26,
  -26,  -85,    0, -182,  -26, -255,    0,  -94,    0, -217,
  -82,    0,    0,    0,    0,    0,  -81,    0,    0,  -77,
    0,    0, -166,    0,    0,   30,    8,    0,  -76,  -75,
 -188,  -74,  -73,  -72,  -71,    0,    0,  -18,  -67,    0,
    0,    0, -229,    0, -141,    0,  -21,  -21,    0,    0,
    0,    0,    0,  -21,  -21,  -26,  -70,    0,  -65,  -69,
 -165,  -53,  -68, -100,  -61,    0,    0,    0,    0,    0,
 -161,    0,    0,    0,   30,   30,  -18,  -91,    0,  -59,
  -66,  -57,  -91,  -60,  -64,  -62,    0,    0,    0, -131,
 -268,    0, -115,  -98,  -52,  -58,  -91,  -85,   98,  -51,
    0,    0,  -91,    0,    0, -159,  -63,  -55,  -54,  -50,
  -46,    0, -102,  -39,    0,    0, -170, -159,  -44,  -56,
    0,    0,    0,  -47,    0,  -25,    0,    0, -170,  -49,
  -61,  -26,    0,  -24, -159,  -22,  -41,    0, -170,    0,
  -48,  -20,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -223,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -42,
    0,    0,    0,    0,    0,  -27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -224,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -12,    3,  -45, -124,    0,    0,
    0,    0,  -32,    0,    0, -263,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -19,    0,    0,    0,
    0,    0,  -29,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -143,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  193,    0,  146,  -40,   79,   84,  -28,    0, -133,
   -3,  170,  142,    0,    4,    0,    0,    0, -101,    0,
    0,  112,  240,  241,  -90,    2,    0,    0,  -92,    0,
    0,    0,
};
final static int YYTABLESIZE=291;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         10,
   10,   84,   10,   85,   10,   23,   68,   17,   34,   39,
    1,  127,   76,  156,   42,   17,    4,   17,   53,   35,
   69,   42,   70,   53,   84,  164,   85,   49,   64,   48,
   15,  143,   15,   52,  100,  172,   72,   52,   52,   52,
   27,   52,   52,   52,   20,   16,   69,   16,   97,   20,
   84,   38,   85,   20,    5,  159,  157,  107,    6,    7,
   71,   52,    8,    9,   10,    5,   11,   52,  157,    6,
    7,   77,  169,    8,    9,   10,   78,   11,  157,  132,
   89,   33,   12,    9,   10,   90,    5,   28,   13,   34,
    6,    7,  154,   47,    8,    9,   10,    5,   11,   13,
   35,    6,    7,   51,   52,    8,    9,   10,  111,   11,
  112,  122,  119,   51,  120,   29,  122,   51,   51,   37,
   13,   51,   51,   51,  139,   51,  135,   98,   51,   52,
  135,   13,   99,  167,   33,   33,  122,  131,   51,   52,
  140,    5,  141,  133,  134,    6,  135,   51,  158,    8,
    9,   10,   30,  121,    5,  103,  104,  153,    6,   31,
  158,   32,    8,    9,   10,    5,  121,  105,  106,    6,
  158,   62,   63,    8,    9,   10,   60,  121,   79,   80,
   81,   82,   83,   38,   41,  136,   33,  108,   42,   43,
   44,   45,   61,   35,   46,   75,   74,   87,   88,   92,
   93,   96,   95,  109,  113,   94,  116,  110,  114,  124,
  125,   89,  128,  129,  130,  138,  137,  142,  151,  150,
  148,  149,  152,  154,  160,  162,   10,  161,   26,  173,
   33,   10,  171,   73,  165,   10,   10,   10,   10,   10,
   10,   17,   50,   51,   52,   33,   17,  102,   51,   52,
   17,   17,   17,   17,   17,   17,   15,  126,   36,  163,
  168,   15,  170,  115,  174,   15,   15,   15,   15,   15,
   15,   16,  166,   66,   67,    0,   16,    0,    0,    0,
   16,   16,   16,   16,   16,   16,   79,   80,   81,   82,
   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         42,
   43,   43,   45,   45,   47,    4,   35,    4,  277,   13,
  269,  113,   53,  147,  278,   43,  284,   45,   45,  288,
  276,  285,  278,   45,   43,  159,   45,   26,   32,   26,
   43,  133,   45,  257,   75,  169,   40,  261,  262,  263,
  273,  265,  266,  267,  269,   43,  276,   45,  278,  274,
   43,  269,   45,  278,  257,  148,  147,   86,  261,  262,
  278,  285,  265,  266,  267,  257,  269,  291,  159,  261,
  262,   42,  165,  265,  266,  267,   47,  269,  169,  120,
  269,  269,  285,  266,  267,  274,  257,  273,  291,  277,
  261,  262,  263,  285,  265,  266,  267,  257,  269,  291,
  288,  261,  262,  270,  271,  265,  266,  267,  274,  269,
  276,  108,  274,  257,  276,  269,  113,  261,  262,  278,
  291,  265,  266,  267,  128,  269,  123,  269,  270,  271,
  127,  291,  274,  162,  259,  260,  133,  269,  270,  271,
   43,  257,   45,  259,  260,  261,  143,  291,  147,  265,
  266,  267,  273,  269,  257,   77,   78,  260,  261,  273,
  159,  273,  265,  266,  267,  257,  269,   84,   85,  261,
  169,   30,   31,  265,  266,  267,  289,  269,  279,  280,
  281,  282,  283,  269,  269,  284,  269,  258,  278,  278,
  278,  278,  273,  288,  278,  273,  278,  274,  274,  274,
  274,  269,  274,  269,  258,  278,  268,  277,  277,  269,
  277,  269,  273,  278,  277,  274,  269,  269,  269,  274,
  284,  277,  269,  263,  269,  273,  269,  284,  274,  278,
  260,  274,  274,   41,  284,  278,  279,  280,  281,  282,
  283,  269,  269,  270,  271,  278,  274,  269,  270,  271,
  278,  279,  280,  281,  282,  283,  269,  112,  278,  285,
  285,  274,  285,   94,  285,  278,  279,  280,  281,  282,
  283,  269,  161,   34,   34,   -1,  274,   -1,   -1,   -1,
  278,  279,  280,  281,  282,  283,  279,  280,  281,  282,
  283,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=291;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,"'*'","'+'",null,
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"IF","THEN","ELSE","END_IF","OUT","FUN","RETURN",
"BREAK","WHEN","WHILE","FOR","CONTINUE","ID","I32","F32","PUNTO","PARENT_A",
"PARENT_C","COMILLA","COMA","DOSPUNTOS","PUNTOCOMA","IGUAL","MAYOR","MENOR",
"MENORIGUAL","MAYORIGUAL","LLAVE_A","LLAVE_C","EXCL","DIST","ASIG","CADENA",
"COMENT","CONST",
};
final static String yyrule[] = {
"$accept : program",
"nombre_program : ID",
"list_var : list_var COMA ID",
"list_var : ID",
"sentencia_decl_datos : sentencia_decl_datos ID list_var PUNTOCOMA",
"sentencia_decl_datos : ID list_var PUNTOCOMA",
"parametro : ID ID",
"cte : I32",
"cte : F32",
"factor : '-' cte",
"factor : ID",
"factor : cte",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : llamado_func",
"retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA",
"asignacion : ID ASIG expresion",
"comparacion : IGUAL",
"comparacion : MAYOR",
"comparacion : MENOR",
"comparacion : MENORIGUAL",
"comparacion : MAYORIGUAL",
"condicion : expresion comparacion expresion",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_ejecutable : asignacion PUNTOCOMA",
"sentencia_ejecutable : sentencia_if PUNTOCOMA",
"sentencia_ejecutable : sentencia_out PUNTOCOMA",
"sentencia_ejecutable : sentencia_when PUNTOCOMA",
"sentencia_ejecutable : sentencia_control PUNTOCOMA",
"bloque_ejecutable :",
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
"bloque_ejecutable : sentencia_ejecutable",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_ejecutable",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID",
"sentencia_continue : CONTINUE DOSPUNTOS ID",
"sentencia_continue : CONTINUE",
"bloque_while : sentencia_continue",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_while",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_while LLAVE_C",
"sentencia_control : ID DOSPUNTOS sentencia_for",
"sentencia_control : ID DOSPUNTOS sentencia_while",
"sentencia_control : sentencia_for",
"sentencia_control : sentencia_while",
"bloque_ejecutable_funcion : sentencia_ejecutable retorno",
"bloque_ejecutable_funcion : sentencia_ejecutable",
"sentencia_declarativa : sentencia_decl_datos",
"sentencia_declarativa : sentencia_decl_fun",
"sentencia_declarativa : lista_const",
"cuerpo_fun : cuerpo_fun bloque_ejecutable_funcion",
"cuerpo_fun : cuerpo_fun sentencia_declarativa",
"cuerpo_fun : bloque_ejecutable_funcion",
"cuerpo_fun : sentencia_declarativa",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia : sentencia sentencia_declarativa",
"sentencia : sentencia sentencia_ejecutable",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"program : nombre_program LLAVE_A sentencia LLAVE_C PUNTOCOMA",
"program : nombre_program LLAVE_A LLAVE_C PUNTOCOMA",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
"lista_asignacion : lista_asignacion asignacion",
"lista_asignacion : asignacion",
"lista_const : CONST lista_asignacion PUNTOCOMA",
};

//#line 155 "gramatica.y"

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
//#line 408 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
