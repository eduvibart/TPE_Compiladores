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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;







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
public final static short SUMA=292;
public final static short RESTA=293;
public final static short MULT=294;
public final static short DIV=295;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    4,    4,    4,    6,
    9,    9,    7,    7,    7,   12,   10,   11,   11,    8,
   14,   14,    5,    5,    5,    5,    5,    5,   15,   13,
   13,   13,   13,   13,   13,   21,   21,   21,   24,   24,
   24,   23,   23,   16,   16,   25,   27,   27,   27,   27,
   27,   26,   26,   17,   18,   20,   20,   28,   28,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   31,   31,
   30,   30,   19,   19,   32,   32,   33,   33,   33,   33,
   22,   22,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    2,
    3,    1,   12,   10,    9,    5,    2,    0,    3,    2,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    3,    1,    1,    3,    3,    3,    3,    1,    2,    1,
    1,    1,    1,    9,    7,    3,    1,    1,    1,    1,
    1,    0,    5,    4,    8,    9,   11,    0,    5,    1,
    1,    1,    1,    1,    1,    2,    1,    2,    0,    2,
    9,    7,    5,    7,    6,    6,    3,    3,    1,    1,
    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    5,    6,    7,    8,    9,
   23,   24,   25,   26,   27,   28,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,    0,   22,    4,
    0,   42,   43,    0,    0,    0,    0,    0,   33,   41,
   38,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,   47,   48,   49,   50,   51,
    0,    0,    0,    0,    0,    0,    0,    0,   54,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   11,   21,
   80,   82,   79,    0,   40,    0,    0,    0,   34,   35,
   36,   37,    0,   17,    0,    0,    0,    0,    0,    0,
    0,   73,    0,    0,   81,    0,    0,    0,    0,    0,
    0,    3,    0,    0,    0,    0,    0,   77,   78,    0,
    0,   45,   18,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,   62,   63,   65,   64,    0,   61,
    0,   74,    0,    0,    0,   18,    0,   55,    0,   75,
   76,    0,   68,    0,   66,    0,    0,    0,   44,   15,
    0,    0,    0,   56,    0,   70,   59,    0,   53,    0,
   19,   14,   18,    0,    0,    0,    0,    0,   57,    0,
   13,    0,    0,    0,   72,   16,    0,   71,
};
final static short yydgoto[] = {                          2,
    3,  171,   15,   16,   17,   18,   19,   20,   36,   82,
  155,  181,   45,   38,   21,   22,   23,   24,   46,   47,
   48,   49,   50,   51,   52,  118,   73,  112,  149,  150,
  165,   58,   94,
};
final static short yysindex[] = {                      -212,
    0,    0, -265,    0, -236, -200, -198, -191, -178, -170,
 -158, -223,    0, -152, -148,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -257, -146, -111, -257,
 -257, -152,    0, -250, -257, -107, -117,  -92,    0,    0,
 -213,    0,    0, -181, -174,  -74,  -70, -172,    0,    0,
    0,  -83,  -76, -246,  -75,  -73,  -78,  -72,  -69,  -68,
 -165,  -60, -152, -177,    0,    0,    0,    0,    0,    0,
 -183, -183, -257, -181, -181, -183, -183,  -55,    0,  -58,
  -59, -226,  -39,  -57, -257,  -62, -257, -152,    0,    0,
    0,    0,    0, -193,    0, -172, -172, -165,    0,    0,
    0,    0,  -61,    0,  -45,  -42,  -33,  -47,  -34,  -38,
  -62,    0,  -25,  -24,    0, -171,  -61, -109,  -31,  -21,
  -22,    0, -152, -140,  -36,  -17,  -62,    0,    0,  -10,
  -61,    0,    0,  -19,  -16, -222,   -7, -181, -181,   -2,
 -181,   -9, -266,    0,    0,    0,    0,    0,   -8,    0,
   -1,    0,    1,    9,  -11,    0,    4,    0,  -62,    0,
    0, -257,    0,    6,    0,    3, -152,    5,    0,    0,
 -195,    7,   -6,    0,    8,    0,    0,   10,    0,   12,
    0,    0,    0,   18,  -62, -257,   11,  -62,    0, -240,
    0,  -95,   13,  -62,    0,    0,   17,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   15,    0,   16,    0,    0,
 -134,    0,    0,    0,    0,    0,    0, -106,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -162,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -201,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -86,  -66, -194,    0,    0,
    0,    0,  -81,    0,    0,    0,    0,    0,    0,    0,
  -23,    0,    0,    0,    0,    0,   -3,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -201,    0,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   19,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -128,    0,    0,    0, -201,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -128,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -201,    0, -128,  -79,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,    0,    0,  151,    0,    0,    0,    0,  179,
 -141,    0,  -32,    0,  -12,    0,  162,  164,   -5,   -4,
   85,    0,  -37,  106,  -26,  -99,    0, -103,    0,    0,
    0,  207,    0,
};
final static int YYTABLESIZE=299;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   26,   39,   61,   55,   56,    5,   65,  125,   10,   11,
   34,   41,   42,   43,  172,   59,   60,  130,    4,   57,
    6,   35,   80,  152,    7,    8,   93,   81,    9,   10,
   11,  154,   12,  193,    6,   44,   99,  100,    7,    8,
   98,  187,    9,   10,   11,   33,   12,  106,   13,  107,
   90,   71,   72,   34,   14,  174,    1,   58,  110,   64,
  113,    6,  158,   34,   35,    7,    8,  180,   14,    9,
   10,   11,   27,   12,   28,   57,   58,   29,  129,   46,
  115,  189,  116,   46,  192,   95,   42,   43,   42,   43,
  197,   91,   42,   43,   30,   14,   92,  128,   42,   43,
  160,  161,   31,  163,   66,   67,   68,   69,   70,   44,
  137,   29,  144,   29,   32,   29,   37,   71,   72,  147,
  148,   76,   77,  136,   25,   26,   71,   72,    3,   40,
   25,   26,    3,    3,    3,  175,    3,    3,    3,   40,
    3,   40,   53,   40,   40,   40,   40,   40,   40,  131,
  132,  138,  139,  190,  178,   96,   97,   40,   40,   40,
   40,   54,    3,  194,  195,   25,   26,   32,   62,   32,
   35,   32,   32,   32,   32,   32,   32,   52,   52,   58,
   58,  101,  102,   63,   74,   32,   32,   30,   75,   30,
   78,   30,   30,   30,   30,   30,   30,   79,   83,   85,
   84,   86,  103,   87,   88,   30,   30,   31,   89,   31,
  104,   31,   31,   31,   31,   31,   31,  105,  108,  109,
  140,  111,  117,  119,    7,   31,   31,  141,    9,   10,
   11,  142,  143,   58,  120,   80,  122,   58,  123,  124,
   58,   58,   58,   58,   58,   58,    6,  134,  126,  127,
    7,  135,  133,   52,    9,   10,   11,   52,  143,  151,
  157,   52,   52,   52,  156,   52,  159,  164,  169,  166,
  162,  167,  173,  170,  176,  188,  198,  183,  168,   52,
  153,  184,   58,  185,  186,  121,  145,  177,  146,  179,
  196,  182,   10,   20,  114,  191,   67,    0,   69,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   14,   35,   30,   31,    4,   44,  111,  266,  267,
  277,  269,  270,  271,  156,  266,  267,  117,  284,   32,
  257,  288,  269,  127,  261,  262,   64,  274,  265,  266,
  267,  131,  269,  274,  257,  293,   74,   75,  261,  262,
   73,  183,  265,  266,  267,  269,  269,  274,  285,  276,
   63,  292,  293,  277,  291,  159,  269,  259,   85,  273,
   87,  257,  285,  277,  288,  261,  262,  263,  291,  265,
  266,  267,  273,  269,  273,   88,  278,  269,  116,  274,
  274,  185,  276,  278,  188,  269,  270,  271,  270,  271,
  194,  269,  270,  271,  273,  291,  274,  269,  270,  271,
  138,  139,  273,  141,  279,  280,  281,  282,  283,  293,
  123,  274,  125,  276,  273,  278,  269,  292,  293,  125,
  125,  294,  295,  122,  130,  130,  292,  293,  257,  278,
  136,  136,  261,  262,  263,  162,  265,  266,  267,  274,
  269,  276,  289,  278,  279,  280,  281,  282,  283,  259,
  260,  292,  293,  186,  167,   71,   72,  292,  293,  294,
  295,  273,  291,  259,  260,  171,  171,  274,  276,  276,
  288,  278,  279,  280,  281,  282,  283,  259,  260,  259,
  260,   76,   77,  276,  259,  292,  293,  274,  259,  276,
  274,  278,  279,  280,  281,  282,  283,  274,  274,  278,
  274,  274,  258,  273,  273,  292,  293,  274,  269,  276,
  269,  278,  279,  280,  281,  282,  283,  277,  258,  277,
  257,  284,  284,  269,  261,  292,  293,  264,  265,  266,
  267,  268,  269,  257,  277,  269,  284,  261,  273,  278,
  264,  265,  266,  267,  268,  269,  257,  269,  274,  274,
  261,  274,  284,  257,  265,  266,  267,  261,  269,  277,
  277,  265,  266,  267,  284,  269,  274,  277,  260,  278,
  273,  273,  269,  285,  269,  258,  260,  284,  278,  260,
  130,  274,  260,  274,  273,  107,  125,  285,  125,  285,
  278,  285,  278,  278,   88,  285,  278,   -1,  278,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=295;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"IF","THEN","ELSE","END_IF","OUT","FUN","RETURN","BREAK","WHEN",
"WHILE","FOR","CONTINUE","ID","I32","F32","PUNTO","PARENT_A","PARENT_C",
"COMILLA","COMA","DOSPUNTOS","PUNTOCOMA","IGUAL","MAYOR","MENOR","MENORIGUAL",
"MAYORIGUAL","LLAVE_A","LLAVE_C","EXCL","DIST","ASIG","CADENA","COMENT","CONST",
"SUMA","RESTA","MULT","DIV",
};
final static String yyrule[] = {
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
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA",
"parametro : ID ID",
"cuerpo_fun :",
"cuerpo_fun : cuerpo_fun bloque_sentencias retorno",
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
"expresion : expresion SUMA termino",
"expresion : expresion RESTA termino",
"expresion : termino",
"expresion : llamado_func",
"expresion : sentencia_for ELSE cte",
"expresion : sentencia_while ELSE cte",
"termino : termino MULT factor",
"termino : termino DIV factor",
"termino : factor",
"factor : RESTA cte",
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
"bloque_ejecutable : LLAVE_A bloque_ejecutable sentencia_ejecutable PUNTOCOMA LLAVE_C",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue",
"sentencia_while : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue",
"bloque_break_continue :",
"bloque_break_continue : LLAVE_A bloque_break_continue ejecutables_break_continue PUNTOCOMA LLAVE_C",
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
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C bloque_break_continue",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
};

//#line 131 "gramatica.y"


void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        if(t.getId() != -1){
          System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        }else
          System.out.println("TERMINO LA EJECUCION");
        return t.getId();
}
public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entradas/entrada_sent_decl.txt"));
        AnalizadorLexico.setEntrada(entrada);
        Parser parser = new Parser();
        parser.run();
}
//#line 434 "Parser.java"
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
int yyparse() throws IOException
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
case 34:
//#line 57 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 35:
//#line 58 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
//#line 591 "Parser.java"
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
 * @throws IOException
 */
public void run() throws IOException
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
