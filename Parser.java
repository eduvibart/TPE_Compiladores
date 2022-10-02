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
   13,   13,   13,   13,   13,   21,   21,   21,   23,   23,
   23,   24,   24,   16,   16,   25,   27,   27,   27,   27,
   27,   26,   26,   17,   18,   20,   28,   28,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   31,   31,   30,
   30,   19,   32,   32,   33,   33,   33,   33,   22,   22,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    2,
    3,    1,   12,   10,    9,    5,    2,    0,    3,    2,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    3,    1,    1,    1,    1,    3,    3,    1,    2,    1,
    1,    1,    1,    9,    7,    3,    1,    1,    1,    1,
    1,    0,    5,    4,    8,   11,    0,    3,    1,    1,
    1,    1,    1,    1,    2,    1,    2,    0,    2,    9,
    7,    7,    6,    6,    3,    3,    1,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    5,    6,    7,    8,    9,
   23,   24,   25,   26,   27,   28,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,   22,    4,    0,
   42,   43,    0,    0,   34,   35,    0,   33,   38,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   39,   47,   48,   49,   50,   51,    0,    0,    0,
    0,    0,    0,   54,    0,    0,    0,    0,    0,    0,
    0,   11,   21,   78,   80,   77,    0,   40,    0,    0,
    0,   36,   37,    0,   17,    0,    0,    0,    0,    0,
    0,   57,   79,    0,    0,    0,    0,    0,    0,    3,
    0,    0,    0,   75,   76,    0,    0,   45,   18,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   72,   59,
   61,   62,   64,   63,    0,   60,    0,    0,    0,   18,
    0,   55,    0,   73,   74,    0,   67,    0,   65,   58,
    0,   44,   15,    0,    0,    0,   57,    0,   69,   53,
    0,   19,   14,   18,    0,    0,    0,    0,   56,   57,
    0,   13,    0,    0,   57,   71,   16,    0,   70,
};
final static short yydgoto[] = {                          2,
    3,  154,   15,   16,   17,   18,   19,   20,   35,   77,
  139,  162,   44,   37,   21,   22,   23,   24,   45,   46,
   47,   48,   49,   50,   51,  106,   70,  113,  135,  136,
  149,   57,   87,
};
final static short yysindex[] = {                      -253,
    0,    0, -265,    0, -239, -248, -207, -219, -195, -187,
 -179, -224,    0, -168, -174,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -256, -175, -163, -256,
 -256, -168,    0, -256, -160, -154, -134,    0,    0, -145,
    0,    0, -213,  -31,    0,    0, -232,    0,    0,    0,
 -116, -115, -257, -109, -108, -107, -102, -148,  -93, -168,
 -123,    0,    0,    0,    0,    0,    0, -237, -237, -256,
 -237, -237,  -88,    0,  -91,  -87, -204,  -67,  -85,  -94,
  -86,    0,    0,    0,    0,    0, -192,    0, -232, -232,
 -148,    0,    0,  -84,    0,  -62,  -69,  -60,  -74,  -61,
  -65,    0,    0, -139,  -84, -171,  -73,  -55,  -56,    0,
 -168, -140, -142,    0,    0,   32,  -84,    0,    0,  -64,
  -50, -226,  -46,  -40,  -39,  -42, -213,  -45,    0,    0,
    0,    0,    0,    0,  -44,    0,  -41,  -27,  -30,    0,
  -28,    0,  -25,    0,    0, -256,    0,  -16,    0,    0,
  -21,    0,    0, -186,  -17,  -24,    0,  -35,    0,    0,
  -19,    0,    0,    0, -128,   11, -256,   -8,    0,    0,
 -245,    0,    6,  -20,    0,    0,    0,   19,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,    0,   12,    0,    0,  -99,
    0,    0,    0,    0,    0,    0,  -77,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -223,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -57,  -36,
    7,    0,    0, -153,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   13,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -170,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -170,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -170,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    3,    0,    0,  178,    0,    0,    0,    0,  197,
 -120,    0,  -32,    0,  -11,    0, -105, -104,   -5,   -4,
   87,    0,   92,  -37,  -26,  -63,  222,  -72,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=308;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   26,   58,   38,   54,   55,   62,    5,  131,  132,   10,
   11,   75,   40,   41,   42,    1,   76,    6,    4,  155,
   56,    7,    8,   86,   27,    9,   10,   11,  174,   12,
    6,   88,   41,   42,    7,    8,   43,   91,    9,   10,
   11,  116,   12,  168,   33,   13,   68,   69,   83,   29,
   29,   14,   29,  138,   29,   43,   41,   42,  142,  131,
  132,   71,   72,   34,   14,   28,  115,  131,  132,   97,
    6,   98,  131,  132,    7,    8,  161,   30,    9,   10,
   11,  103,   12,  104,  165,   31,    3,  117,  118,  147,
    3,    3,    3,   32,    3,    3,    3,  173,    3,  123,
   36,  130,  178,   39,   14,   52,   52,  133,  134,   53,
   25,   26,  122,   52,  126,   59,   25,   26,    7,  158,
    3,  127,    9,   10,   11,  128,   36,   61,  126,  114,
   41,   42,    7,   34,  171,  127,    9,   10,   11,  128,
   36,   60,  129,   68,   69,   84,   41,   42,   25,   26,
   85,  124,  125,  130,   89,   90,  169,   73,   74,  133,
  134,  130,   92,   93,   78,   79,  130,  133,  134,   94,
   80,   81,  133,  134,   40,   82,   40,   95,   40,   40,
   40,   40,   40,   40,   63,   64,   65,   66,   67,   96,
   99,  100,   40,   40,   40,   40,   32,  102,   32,  105,
   32,   32,   32,   32,   32,   32,  107,  108,   75,  110,
  119,  111,  112,  120,   32,   32,   30,  121,   30,  140,
   30,   30,   30,   30,   30,   30,  141,  143,  144,  145,
  146,  148,  152,  150,   30,   30,  151,   31,  166,   31,
  156,   31,   31,   31,   31,   31,   31,   63,   64,   65,
   66,   67,  159,  167,  153,   31,   31,  177,  157,  164,
   68,   69,  126,  160,  175,  176,    7,  163,  170,  127,
    9,   10,   11,  128,   36,  126,  172,   52,  179,    7,
   46,   10,  127,    9,   10,   11,  128,   36,    6,   20,
   66,   68,    7,  137,  109,   52,    9,   10,   11,   52,
   36,  101,    0,   52,   52,   52,    0,   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   34,   14,   30,   31,   43,    4,  113,  113,  266,
  267,  269,  269,  270,  271,  269,  274,  257,  284,  140,
   32,  261,  262,   61,  273,  265,  266,  267,  274,  269,
  257,  269,  270,  271,  261,  262,  293,   70,  265,  266,
  267,  105,  269,  164,  269,  285,  292,  293,   60,  269,
  274,  291,  276,  117,  278,  293,  270,  271,  285,  165,
  165,  294,  295,  288,  291,  273,  104,  173,  173,  274,
  257,  276,  178,  178,  261,  262,  263,  273,  265,  266,
  267,  274,  269,  276,  157,  273,  257,  259,  260,  127,
  261,  262,  263,  273,  265,  266,  267,  170,  269,  111,
  269,  113,  175,  278,  291,  259,  260,  113,  113,  273,
  116,  116,  110,  289,  257,  276,  122,  122,  261,  146,
  291,  264,  265,  266,  267,  268,  269,  273,  257,  269,
  270,  271,  261,  288,  167,  264,  265,  266,  267,  268,
  269,  276,  285,  292,  293,  269,  270,  271,  154,  154,
  274,  292,  293,  165,   68,   69,  285,  274,  274,  165,
  165,  173,   71,   72,  274,  274,  178,  173,  173,  258,
  278,  274,  178,  178,  274,  269,  276,  269,  278,  279,
  280,  281,  282,  283,  279,  280,  281,  282,  283,  277,
  258,  277,  292,  293,  294,  295,  274,  284,  276,  284,
  278,  279,  280,  281,  282,  283,  269,  277,  269,  284,
  284,  273,  278,  269,  292,  293,  274,  274,  276,  284,
  278,  279,  280,  281,  282,  283,  277,  274,  269,  269,
  273,  277,  260,  278,  292,  293,  278,  274,  274,  276,
  269,  278,  279,  280,  281,  282,  283,  279,  280,  281,
  282,  283,  269,  273,  285,  292,  293,  278,  284,  284,
  292,  293,  257,  285,  259,  260,  261,  285,  258,  264,
  265,  266,  267,  268,  269,  257,  285,  260,  260,  261,
  274,  278,  264,  265,  266,  267,  268,  269,  257,  278,
  278,  278,  261,  116,   98,  257,  265,  266,  267,  261,
  269,   80,   -1,  265,  266,  267,   -1,  269,
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
"expresion : sentencia_for",
"expresion : sentencia_while",
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
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA SUMA ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA RESTA ID",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
};

//#line 129 "gramatica.y"

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
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entradas/entrada_sent_eje.txt"));
        AnalizadorLexico.setEntrada(entrada);
        Parser parser = new Parser();
        parser.run();
}
//#line 426 "Parser.java"
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
//#line 583 "Parser.java"
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
