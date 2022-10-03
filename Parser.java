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
    9,    9,    7,    7,    7,   11,   11,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   14,   14,   16,
   18,   18,   17,   17,   21,   21,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   24,   24,   19,   10,
    8,   28,   28,    5,    5,    5,    5,    5,    5,   13,
   27,   27,   27,   27,   27,   27,   33,   33,   33,   35,
   35,   35,   26,   26,   29,   29,   20,   37,   37,   37,
   37,   37,   36,   36,   15,   30,   32,   32,   38,   38,
   39,   39,   39,   39,   39,   39,   39,   39,   39,   25,
   25,   40,   40,   31,   31,   22,   22,   41,   41,   41,
   41,   34,   34,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    2,
    3,    1,   12,   10,    9,    0,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,   13,    9,    8,
   11,   13,    7,    9,    0,    3,    1,    1,    1,    1,
    1,    1,    2,    1,    2,    1,   13,    9,    4,    2,
    2,    3,    1,    1,    1,    1,    1,    1,    1,    3,
    3,    3,    1,    1,    3,    3,    3,    3,    1,    2,
    1,    1,    1,    1,    9,    7,    3,    1,    1,    1,
    1,    1,    0,    5,    4,    8,    9,   11,    0,    5,
    1,    1,    1,    1,    1,    1,    2,    1,    2,    0,
    2,    9,    7,    5,    7,    6,    6,    3,    3,    1,
    1,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    5,    6,    7,    8,    9,
   54,   56,   55,   57,   58,   59,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,   53,    0,    4,
    0,   73,   74,    0,    0,   72,    0,    0,    0,    0,
   64,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   70,    0,   78,   79,   80,   81,
   82,    0,    0,    0,    0,    0,    0,    0,   85,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   11,   52,
  111,  113,  110,    0,    0,   71,    0,    0,    0,   65,
   66,   67,   68,   50,    0,    0,    0,    0,    0,    0,
    0,  104,    0,    0,  112,    0,    0,    0,    0,    0,
    0,    3,    0,    0,    0,    0,    0,  108,  109,    0,
    0,   76,   16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   91,   93,   94,   96,   95,    0,   92,
    0,  105,    0,    0,    0,   16,    0,   86,    0,  106,
  107,    0,   99,    0,   97,    0,    0,    0,   75,    0,
    0,    0,    0,    0,    0,   15,   18,   19,   20,    0,
   21,   22,   23,   24,   25,   26,   27,    0,    0,   87,
    0,  101,   90,    0,   84,    0,    0,    0,    0,    0,
    0,   17,   14,   16,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   88,    0,   49,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    0,   35,    0,
    0,    0,  103,   16,   16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   33,   37,   39,
   40,   42,   41,   46,    0,   38,    0,   35,  102,    0,
   30,    0,    0,   45,   43,   36,    0,    0,    0,   29,
   35,    0,    0,   34,   16,    0,    0,    0,    0,   31,
    0,   35,    0,   35,    0,   28,    0,   32,    0,    0,
   48,   35,    0,    0,   47,
};
final static short yydgoto[] = {                          2,
    3,    5,   15,   16,   17,  177,  178,  179,   36,   82,
  155,  180,  181,  182,  183,  184,  185,  186,  187,   45,
  237,   58,  255,  256,  165,   46,   47,   39,   23,   24,
   48,   49,   50,   51,   52,  118,   74,  112,  149,  150,
   94,
};
final static short yysindex[] = {                      -247,
    0,    0, -256,    0,   61, -233, -212, -197, -198, -149,
 -144, -238,    0, -176, -140,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -179, -132, -133, -179,
 -179, -176,    0, -200, -179, -113, -139,    0, -107,    0,
 -204,    0,    0, -163, -102,    0, -170,  -72,  -53, -147,
    0,    0,  -49, -253,  -36,   -7,  -44,   -6,    2,   11,
 -127,    4, -176, -110,    0,   16,    0,    0,    0,    0,
    0, -251, -251, -179, -163, -163, -251, -251,    0,    9,
   10, -192,   21,   22, -179,   20, -179, -176,    0,    0,
    0,    0,    0, -180,   24,    0, -147, -147, -127,    0,
    0,    0,    0,    0,   31,   33,   42,   28,   12,   -9,
   20,    0,   19,   39,    0, -154,   24,  -63,   32,   46,
   43,    0, -176, -117,  222,   44,   20,    0,    0,  -57,
   24,    0,    0,   35,   47,   72,   58, -163, -163,   62,
 -163,   59, -254,    0,    0,    0,    0,    0,   64,    0,
   67,    0,   65,   84, -111,    0,   76,    0,   20,    0,
    0, -179,    0,   78,    0,   63, -176,   66,    0,   77,
   80,   81,   82,   83, -188,    0,    0,    0,    0,   71,
    0,    0,    0,    0,    0,    0,    0,  -84,   75,    0,
   86,    0,    0,   87,    0, -179, -179, -179, -179, -176,
  -68,    0,    0,    0,  115,   20,   98, -237,  100,  102,
  111,  116,  125,  -46,   20,    0,  129,    0,  142,  134,
  118, -179, -176,    0,  -32,  131,  139,  154,    0,  160,
  161,   20,    0,    0,    0, -176,   -8,  136,  155,  176,
  -15,   29,  163,  167, -163,   59, -239,    0,    0,    0,
    0,    0,    0,    0,  169,    0,  168,    0,    0,  -23,
    0,  165, -179,    0,    0,    0, -176,  101,  173,    0,
    0,  182,  184,    0,    0,  114,  201,  177,   40,    0,
  178,    0,  200,    0,  127,    0,  140,    0,  -19,  179,
    0,    0,  153,  207,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  191,    0,    0,  198,    0,
  150,    0,    0,    0,    0,    0,    0,    0,    0,  -90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -52,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -226,    0,    0,    0,    0,
    0,    0,    0,    0,  -16,    0,  172,  192, -172,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  235,    0,    0,    0,    0,    0,  248,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -226,    0,    0,    0,
  217,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  202,  203,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -226,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -226,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  218,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  204,  203,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  371,    0,    0,  364,    1,    7,    8,    0,  388,
 -130,    0,   -5,    0,   -4, -223, -222, -208, -190,  -28,
 -241,  -81,    0,    0,  251,  -40,  -30,    0,    0,  373,
    3,    5,  141,    0,  152, -106,    0,  -79,    0,    0,
    0,
};
final static int YYTABLESIZE=517;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         21,
   22,   55,   56,   65,   61,   18,  114,   25,   38,   26,
  130,   19,   20,  251,  252,   80,  268,   96,   42,   43,
   81,    1,   34,   93,  154,  188,   57,    4,  253,  276,
   33,  125,   89,   35,  100,  101,  218,  201,   34,   27,
  285,   44,  287,   99,  251,  252,  254,  152,   35,   35,
  293,   89,  251,  252,   72,   73,  110,   90,  113,  253,
   28,  251,  252,  251,  252,   59,   60,  253,   64,  251,
  252,   29,   34,  214,   30,  129,  253,  254,  253,  190,
   33,  106,   57,  107,  253,  254,   10,   11,  201,   41,
   42,   43,   37,  115,  254,  116,  254,  160,  161,   35,
  163,   77,  254,  241,  242,   77,   42,   43,   67,   68,
   69,   70,   71,   44,  128,   42,   43,  137,  211,  144,
  145,   72,   73,   31,   21,   22,  216,  147,   32,  148,
   21,   22,   25,  191,   26,  225,   18,   40,   25,   54,
   26,  231,   19,   20,  279,  170,   77,   78,   35,    7,
    8,  171,  240,  172,  173,  174,   53,  175,   91,   42,
   43,  194,   62,   92,   72,   73,  208,  207,   63,  209,
  210,   66,  170,  176,  138,  139,    7,    8,  171,   14,
  172,  173,  174,   63,  175,   63,   75,   63,   63,   63,
   63,   63,   63,  230,   57,  131,  132,  212,  213,    6,
  203,   63,   63,    7,  264,   76,   14,    9,   10,   11,
  170,  143,   97,   98,    7,    8,  171,   57,  172,  173,
  174,   60,  175,   60,   79,   60,  232,  233,  102,  103,
  243,  249,  250,   85,  272,  269,  270,   83,  224,  290,
  291,  170,   83,   83,   14,    7,    8,  171,  244,  172,
  173,  174,    7,  175,  171,  245,  172,  173,  174,  246,
  247,  273,  249,  250,   89,   89,   84,   86,  124,  260,
  249,  250,   89,   95,   87,   14,  248,  104,  108,  249,
  250,  249,  250,   88,  123,  170,  105,  249,  250,    7,
    8,  171,  126,  172,  173,  174,  170,  175,  109,  119,
    7,    8,  171,  111,  172,  173,  174,  117,  175,  120,
   80,  122,  127,  261,  134,  133,  135,    6,  156,   14,
  151,    7,    8,  157,  283,    9,   10,   11,    6,   12,
   14,  159,    7,    8,  162,  164,    9,   10,   11,  167,
   12,  166,  168,  169,  189,   13,  192,  193,  202,  196,
  195,   14,  197,  198,  199,  200,  158,  244,  204,  205,
  206,    7,   14,  171,  245,  172,  173,  174,  246,  247,
  244,  217,  215,  219,    7,  220,  171,  245,  172,  173,
  174,  246,  247,  244,  221,  274,  226,    7,  222,  171,
  245,  172,  173,  174,  246,  247,  244,  223,  280,  227,
    7,  229,  171,  245,  172,  173,  174,  246,  247,  244,
  228,  288,  257,    7,  234,  171,  245,  172,  173,  174,
  246,  247,  235,   71,  289,   71,  236,   71,   71,   71,
   71,   71,   71,  238,  239,  259,  262,  294,  258,  263,
  267,   71,   71,   71,   71,   61,  266,   61,  271,   61,
   61,   61,   61,   61,   61,  277,  275,  278,  281,  286,
  282,  284,  292,   61,   61,   62,  295,   62,   10,   62,
   62,   62,   62,   62,   62,   51,   83,   89,  140,   98,
  100,   44,    7,   62,   62,  141,    9,   10,   11,  142,
  143,   89,  136,  153,  121,   89,  265,  146,   89,   89,
   89,   89,   89,   89,   83,    0,    0,    0,   83,    0,
    0,    0,   83,   83,   83,    0,   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   30,   31,   44,   35,    5,   88,    5,   14,    5,
  117,    5,    5,  237,  237,  269,  258,  269,  270,  271,
  274,  269,  277,   64,  131,  156,   32,  284,  237,  271,
  269,  111,  259,  288,   75,   76,  274,  277,  277,  273,
  282,  293,  284,   74,  268,  268,  237,  127,  288,  288,
  292,  278,  276,  276,  292,  293,   85,   63,   87,  268,
  273,  285,  285,  287,  287,  266,  267,  276,  273,  293,
  293,  269,  277,  204,  273,  116,  285,  268,  287,  159,
  269,  274,   88,  276,  293,  276,  266,  267,  277,  269,
  270,  271,  269,  274,  285,  276,  287,  138,  139,  288,
  141,  274,  293,  234,  235,  278,  270,  271,  279,  280,
  281,  282,  283,  293,  269,  270,  271,  123,  200,  125,
  125,  292,  293,  273,  130,  130,  206,  125,  273,  125,
  136,  136,  130,  162,  130,  215,  136,  278,  136,  273,
  136,  223,  136,  136,  275,  257,  294,  295,  288,  261,
  262,  263,  232,  265,  266,  267,  289,  269,  269,  270,
  271,  167,  276,  274,  292,  293,  197,  196,  276,  198,
  199,  274,  257,  285,  292,  293,  261,  262,  263,  291,
  265,  266,  267,  274,  269,  276,  259,  278,  279,  280,
  281,  282,  283,  222,  200,  259,  260,  266,  267,  257,
  285,  292,  293,  261,  245,  259,  291,  265,  266,  267,
  257,  269,   72,   73,  261,  262,  263,  223,  265,  266,
  267,  274,  269,  276,  274,  278,  259,  260,   77,   78,
  236,  237,  237,  278,  263,  259,  260,  274,  285,  259,
  260,  257,  259,  260,  291,  261,  262,  263,  257,  265,
  266,  267,  261,  269,  263,  264,  265,  266,  267,  268,
  269,  267,  268,  268,  259,  260,  274,  274,  278,  285,
  276,  276,  269,  258,  273,  291,  285,  269,  258,  285,
  285,  287,  287,  273,  273,  257,  277,  293,  293,  261,
  262,  263,  274,  265,  266,  267,  257,  269,  277,  269,
  261,  262,  263,  284,  265,  266,  267,  284,  269,  277,
  269,  284,  274,  285,  269,  284,  274,  257,  284,  291,
  277,  261,  262,  277,  285,  265,  266,  267,  257,  269,
  291,  274,  261,  262,  273,  277,  265,  266,  267,  273,
  269,  278,  278,  260,  269,  285,  269,  285,  278,  273,
  285,  291,  273,  273,  273,  273,  285,  257,  284,  274,
  274,  261,  291,  263,  264,  265,  266,  267,  268,  269,
  257,  274,  258,  274,  261,  274,  263,  264,  265,  266,
  267,  268,  269,  257,  274,  285,  258,  261,  273,  263,
  264,  265,  266,  267,  268,  269,  257,  273,  285,  258,
  261,  284,  263,  264,  265,  266,  267,  268,  269,  257,
  277,  285,  277,  261,  284,  263,  264,  265,  266,  267,
  268,  269,  284,  274,  285,  276,  273,  278,  279,  280,
  281,  282,  283,  274,  274,  260,  274,  285,  284,  273,
  273,  292,  293,  294,  295,  274,  278,  276,  284,  278,
  279,  280,  281,  282,  283,  274,  284,  274,  258,  260,
  284,  284,  284,  292,  293,  274,  260,  276,  278,  278,
  279,  280,  281,  282,  283,  278,  260,  260,  257,  278,
  278,  278,  261,  292,  293,  264,  265,  266,  267,  268,
  269,  257,  122,  130,  107,  261,  246,  125,  264,  265,
  266,  267,  268,  269,  257,   -1,   -1,   -1,  261,   -1,
   -1,   -1,  265,  266,  267,   -1,  269,
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
"cuerpo_fun :",
"cuerpo_fun : cuerpo_fun sentencias_fun PUNTOCOMA",
"sentencias_fun : sentencia_decl_datos",
"sentencias_fun : sentencia_decl_fun",
"sentencias_fun : lista_const",
"sentencias_fun : asignacion",
"sentencias_fun : sentencia_if_fun",
"sentencias_fun : sentencia_out",
"sentencias_fun : sentencia_when_fun",
"sentencias_fun : sentencia_for_fun",
"sentencias_fun : sentencia_while_fun",
"sentencias_fun : retorno",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF",
"sentencia_when_fun : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_while_fun : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_for_fun : FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_for_fun : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"cuerpo_fun_break :",
"cuerpo_fun_break : cuerpo_fun_break sentencias_fun_break PUNTOCOMA",
"sentencias_fun_break : asignacion",
"sentencias_fun_break : sentencia_if_break_fun",
"sentencias_fun_break : sentencia_out",
"sentencias_fun_break : sentencia_when_fun",
"sentencias_fun_break : sentencia_while_fun",
"sentencias_fun_break : sentencia_for_fun",
"sentencias_fun_break : CONTINUE tag",
"sentencias_fun_break : BREAK",
"sentencias_fun_break : BREAK cte",
"sentencias_fun_break : retorno",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C END_IF",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C END_IF",
"retorno : RETURN PARENT_A expresion PARENT_C",
"parametro : ID ID",
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

//#line 172 "gramatica.y"


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
//#line 547 "Parser.java"
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
case 65:
//#line 98 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 66:
//#line 99 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
//#line 704 "Parser.java"
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
