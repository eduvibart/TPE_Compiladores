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






//#line 2 "gramatica.y"
packege 
import 

//#line 21 "Parser.java"




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
    1,    2,    3,    4,    4,    5,    5,    6,    7,    7,
    8,    8,    8,    9,    9,    9,   10,   10,   10,   10,
   12,   13,   14,   14,   14,   14,   14,   15,   16,   17,
   17,   17,   17,   17,   21,   21,   19,   18,   18,   22,
   22,   23,   23,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   25,   26,   20,   20,   20,   20,
   27,   27,   28,   28,   28,   31,   31,   31,   31,   29,
   29,   29,   32,   32,   32,   32,    0,    0,   33,   33,
   33,   33,   11,   11,   34,   34,   30,
};
final static short yylen[] = {                            2,
    1,    1,    1,    3,    1,    4,    3,    2,    1,    1,
    2,    1,    1,    3,    3,    1,    3,    3,    1,    1,
    5,    3,    1,    1,    1,    1,    1,    3,    4,    2,
    2,    2,    2,    2,    2,    1,    6,    9,    7,    6,
    6,    3,    1,    2,    4,    3,    3,    1,    3,    2,
    4,    3,    2,    3,    5,   11,    3,    3,    1,    1,
    2,    1,    1,    1,    1,    2,    2,    1,    1,   11,
   13,   10,    2,    2,    1,    1,    5,    4,    3,    3,
    1,    1,    4,    3,    2,    1,    3,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   76,    0,
    0,    0,   59,   60,   75,   64,   65,    0,    0,    0,
    0,    0,    0,    0,    0,   78,    0,   86,    0,    0,
    5,    0,    3,    0,   30,   32,   31,   33,   34,    0,
   74,   73,    0,    9,   10,    0,   13,   16,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    0,    0,   87,
   85,   57,   58,    0,    7,    0,   77,    0,   11,    0,
    0,   23,   24,   25,   26,   27,    0,    0,    0,    0,
   29,    0,    0,    0,    0,    0,    0,    0,    4,    6,
   82,   84,   81,    0,   12,   14,   15,    0,    0,    0,
    0,    0,    8,    0,    0,    0,    0,    0,    0,    0,
    0,   36,    0,    0,    0,   83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   35,    0,
    0,    0,   79,   80,    0,   39,    0,    0,    0,    0,
    0,    0,    0,    0,    2,   42,   46,    0,   47,    0,
    0,    0,   68,   69,    0,    0,    0,    0,   40,   41,
    0,   45,   38,    0,   61,    0,   66,   67,    0,    0,
    0,    0,   72,    0,    0,    0,    0,   70,    0,   56,
    0,    0,   21,   71,
};
final static short yydgoto[] = {                          2,
    3,   14,   15,   42,   16,   94,   57,   58,   59,   60,
   61,  175,   17,   89,   62,   18,  122,   20,   21,   22,
  123,   68,  124,  125,   23,   24,  163,  164,   26,   27,
  165,   28,  104,   39,
};
final static short yysindex[] = {                      -254,
    0,    0, -247, -189, -233, -199, -168, -163, -145, -140,
 -150, -130, -105,  -95,  -97,  -83,  -82,  -65,    0,  -52,
  -43,  -32,    0,    0,    0,    0,    0, -176,  -26,  -90,
  -39,  -26,  -26, -105,  -26,    0, -150,    0, -256, -143,
    0, -267,    0,  -97,    0,    0,    0,    0,    0,  -20,
    0,    0,  -14,    0,    0, -110,    0,    0,   12,    8,
    0,  -13,  -11, -142,  -10,   -1,  -18,    1,   39,    0,
    0,    0,    0,   -4,    0,  -99,    0, -124,    0,  -21,
  -21,    0,    0,    0,    0,    0,  -21,  -21,  -26,   16,
    0,   20,    7, -120,   21,   22,  -58,  -63,    0,    0,
    0,    0,    0,  -79,    0,    0,    0,   12,   12,   39,
   53,  -83,    0,   28,  -83,   53,    5,   15, -209,   31,
 -150,    0,  -49,   38,   53,    0, -118,   35,   14,  -83,
    6,   53, -105,  164,   53,   43,   40, -183,    0,   45,
   53,   53,    0,    0,   53,    0,  -91,   33,   47,   51,
   57,   58,   53,   53,    0,    0,    0,   50,    0,   53,
   46,   66,    0,    0, -149,  -91,  -83,   48,    0,    0,
   53,    0,    0,   60,    0,   49,    0,    0, -149,   52,
  -63,  -26,    0,   54,  -91, -240,  -41,    0, -149,    0,
   59,   55,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -206,    0,    0,    0,    0, -126,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -42,    0,    0,    0,    0,    0,  -27,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -222,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -12,    3,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   63,
   61,    0, -243,    0,   64,    0,    0,    0,    0,    0,
    0,   65,    0,    0, -220,    0,    0,    0,    0,    0,
 -186, -181,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -180, -178,    0,    0,    0,    0,    0, -159,
    0,  -77,    0,    0,    0,    0,    0,    0,    0,    0,
 -156,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  194,   -9,  291,    0,  229,  -44,  130,  141,  -25,
    0, -151,   10,  248,  198,    0,    2,    0,    0,    0,
  -75,    0,  223,  166,  308,  309, -122,    4,    0,    0,
 -146,    0,    0,    0,
};
final static int YYTABLESIZE=349;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         12,
   12,   87,   12,   88,   12,   19,   44,   25,   74,   69,
   75,   79,   37,  176,    1,   19,    5,   19,   56,  179,
    6,   70,   38,   56,    8,    9,   10,  184,  121,   51,
   17,   52,   17,  103,   48,  128,    4,  192,  189,   29,
  132,   48,  177,   67,  190,   18,   22,   18,   71,  142,
   87,   22,   88,   80,   93,   22,  177,   53,   81,  153,
   54,   55,    3,  110,   53,  160,  177,    5,  135,  161,
    2,    6,    7,   30,  136,    8,    9,   10,  171,   11,
    5,   87,  144,   88,    6,    7,   54,   55,    8,    9,
   10,   50,   11,  158,  157,   12,   44,   52,   50,   54,
   31,   13,  129,   44,   52,   93,   54,    5,   50,   32,
  142,    6,    7,  174,   13,    8,    9,   10,   49,   11,
  148,   51,    9,   10,  139,   49,   43,   33,   51,  139,
   63,   92,   34,  139,   63,   63,   63,   35,   63,   63,
   63,   13,  150,  139,  101,   54,   55,   36,  162,  102,
  143,   54,   55,  114,  139,  115,  187,  180,   63,   54,
   55,  139,  139,   37,   63,    5,  162,  162,  178,    6,
    7,   41,  139,    8,    9,   10,   74,   11,  100,   62,
  162,   40,  178,   62,   62,   43,  162,   62,   62,   62,
  162,   62,  178,    5,  126,   45,  127,    6,   63,   13,
  119,    8,    9,   10,  120,  121,  151,    5,  152,  106,
  107,    6,   46,   62,  138,    8,    9,   10,  120,  121,
   82,   83,   84,   85,   86,   47,   12,  108,  109,   65,
   66,   12,  191,   64,   48,   12,   12,   12,   12,   12,
   12,   19,   53,   54,   55,   49,   19,  105,   54,   55,
   19,   19,   19,   19,   19,   19,   17,   77,   78,   97,
   90,   17,   91,   95,   99,   17,   17,   17,   17,   17,
   17,   18,   96,  111,   98,  113,   18,  133,  116,  149,
   18,   18,   18,   18,   18,   18,   82,   83,   84,   85,
   86,    5,  134,  145,  146,    6,  112,  147,  117,    8,
    9,   10,    5,  121,  130,  173,    6,  137,  155,    5,
    8,    9,   10,    6,  121,  141,  166,    8,    9,   10,
  154,  121,  159,  167,  168,  169,  170,  172,  174,   28,
  156,  181,  182,  183,   76,  185,  193,    2,  188,  194,
   43,   55,   37,  131,  118,  140,  186,   72,   73,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         42,
   43,   43,   45,   45,   47,    4,   16,    4,  276,   35,
  278,   56,  269,  165,  269,   43,  257,   45,   45,  166,
  261,  278,   13,   45,  265,  266,  267,  179,  269,   28,
   43,   28,   45,   78,  278,  111,  284,  189,  185,  273,
  116,  285,  165,   34,  285,   43,  269,   45,   39,  125,
   43,  274,   45,   42,   64,  278,  179,  278,   47,  135,
  270,  271,  269,   89,  285,  141,  189,  257,  278,  145,
  277,  261,  262,  273,  119,  265,  266,  267,  154,  269,
  257,   43,  127,   45,  261,  262,  270,  271,  265,  266,
  267,  278,  269,  138,  278,  285,  278,  278,  285,  278,
  269,  291,  112,  285,  285,  115,  285,  257,  285,  273,
  186,  261,  262,  263,  291,  265,  266,  267,  278,  269,
  130,  278,  266,  267,  123,  285,  269,  273,  285,  128,
  257,  274,  273,  132,  261,  262,  263,  288,  265,  266,
  267,  291,  133,  142,  269,  270,  271,  278,  147,  274,
  269,  270,  271,  274,  153,  276,  182,  167,  285,  270,
  271,  160,  161,  269,  291,  257,  165,  166,  165,  261,
  262,  269,  171,  265,  266,  267,  276,  269,  278,  257,
  179,  277,  179,  261,  262,  269,  185,  265,  266,  267,
  189,  269,  189,  257,  274,  278,  276,  261,  289,  291,
  264,  265,  266,  267,  268,  269,   43,  257,   45,   80,
   81,  261,  278,  291,  264,  265,  266,  267,  268,  269,
  279,  280,  281,  282,  283,  278,  269,   87,   88,   32,
   33,  274,  274,  273,  278,  278,  279,  280,  281,  282,
  283,  269,  269,  270,  271,  278,  274,  269,  270,  271,
  278,  279,  280,  281,  282,  283,  269,  278,  273,  278,
  274,  274,  274,  274,  269,  278,  279,  280,  281,  282,
  283,  269,  274,  258,  274,  269,  274,  273,  258,  274,
  278,  279,  280,  281,  282,  283,  279,  280,  281,  282,
  283,  257,  278,  259,  260,  261,  277,  284,  277,  265,
  266,  267,  257,  269,  277,  260,  261,  277,  269,  257,
  265,  266,  267,  261,  269,  278,  284,  265,  266,  267,
  278,  269,  278,  277,  274,  269,  269,  278,  263,  274,
  137,  284,  273,  285,   44,  284,  278,  277,  285,  285,
  278,  278,  278,  115,   97,  123,  181,   40,   40,
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
"etiqueta : ID",
"tipo : ID",
"list_var : list_var COMA ID",
"list_var : ID",
"sentencia_decl_datos : sentencia_decl_datos tipo list_var PUNTOCOMA",
"sentencia_decl_datos : tipo list_var PUNTOCOMA",
"parametro : tipo ID",
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
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
"bloque_ejecutable : sentencia_ejecutable",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_ejecutable",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable ELSE bloque_ejecutable END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID",
"sentencia_continue : CONTINUE DOSPUNTOS etiqueta",
"sentencia_continue : CONTINUE",
"bloque_while : bloque_while bloque_ejecutable",
"bloque_while : bloque_ejecutable BREAK cte PUNTOCOMA",
"bloque_while : bloque_ejecutable BREAK PUNTOCOMA",
"bloque_while : bloque_ejecutable sentencia_continue PUNTOCOMA",
"bloque_while : bloque_ejecutable",
"bloque_while : sentencia_continue PUNTOCOMA bloque_ejecutable",
"bloque_while : sentencia_continue PUNTOCOMA",
"bloque_while : BREAK cte PUNTOCOMA bloque_ejecutable",
"bloque_while : BREAK PUNTOCOMA bloque_ejecutable",
"bloque_while : BREAK PUNTOCOMA",
"bloque_while : BREAK cte PUNTOCOMA",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_while",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_while LLAVE_C",
"sentencia_control : etiqueta DOSPUNTOS sentencia_for",
"sentencia_control : etiqueta DOSPUNTOS sentencia_while",
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
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun retorno LLAVE_C",
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

//#line 178 "gramatica.y"

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
//#line 442 "Parser.java"
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
