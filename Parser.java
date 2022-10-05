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
    0,    1,    2,    2,    3,    3,    3,    4,    4,    4,
    6,    9,    9,    7,    7,    7,   11,   11,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   14,   14,
   16,   18,   18,   17,   17,   21,   21,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   24,   24,   19,
   10,    8,   28,   28,    5,    5,    5,    5,    5,    5,
   13,   27,   27,   27,   27,   27,   27,   33,   33,   33,
   35,   35,   35,   26,   26,   29,   29,   20,   37,   37,
   37,   37,   37,   36,   36,   15,   30,   32,   32,   38,
   38,   39,   39,   39,   39,   39,   39,   39,   39,   39,
   25,   25,   40,   40,   31,   31,   22,   22,   41,   41,
   41,   41,   34,   34,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    1,
    2,    3,    1,   12,   10,    9,    0,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,   13,    9,
    8,   11,   13,    7,    9,    0,    3,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    1,   13,    9,    4,
    2,    2,    3,    1,    1,    1,    1,    1,    1,    1,
    3,    3,    3,    1,    1,    3,    3,    3,    3,    1,
    2,    1,    1,    1,    1,    9,    7,    3,    1,    1,
    1,    1,    1,    0,    5,    4,    8,    9,   11,    0,
    5,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    0,    2,    9,    7,    5,    7,    6,    6,    3,    3,
    1,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    5,    6,    8,    9,
   10,   55,   57,   56,   58,   59,   60,    0,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    0,   54,    0,
    4,    0,   74,   75,    0,    0,   73,    0,    0,    0,
    0,   65,   70,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   71,    0,   79,   80,   81,
   82,   83,    0,    0,    0,    0,    0,    0,    0,   86,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   12,
   53,  112,  114,  111,    0,    0,   72,    0,    0,    0,
   66,   67,   68,   69,   51,    0,    0,    0,    0,    0,
    0,    0,  105,    0,    0,  113,    0,    0,    0,    0,
    0,    0,    3,    0,    0,    0,    0,    0,  109,  110,
    0,    0,   77,   17,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   92,   94,   95,   97,   96,    0,
   93,    0,  106,    0,    0,    0,   17,    0,   87,    0,
  107,  108,    0,  100,    0,   98,    0,    0,    0,   76,
    0,    0,    0,    0,    0,    0,   16,   19,   20,   21,
    0,   22,   23,   24,   25,   26,   27,   28,    0,    0,
   88,    0,  102,   91,    0,   85,    0,    0,    0,    0,
    0,    0,   18,   15,   17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   89,    0,   50,    0,
    0,    0,    0,    0,   14,    0,    0,    0,    0,   36,
    0,    0,    0,  104,   17,   17,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   34,   38,
   40,   41,   43,   42,   47,    0,   39,    0,   36,  103,
    0,   31,    0,    0,   46,   44,   37,    0,    0,    0,
   30,   36,    0,    0,   35,   17,    0,    0,    0,    0,
   32,    0,   36,    0,   36,    0,   29,    0,   33,    0,
    0,   49,   36,    0,    0,   48,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   17,   18,  178,  179,  180,   37,   83,
  156,  181,  182,  183,  184,  185,  186,  187,  188,   46,
  238,   59,  256,  257,  166,   47,   48,   40,   24,   25,
   49,   50,   51,   52,   53,  119,   75,  113,  150,  151,
   95,
};
final static short yysindex[] = {                      -240,
    0,    0, -249,    0, -107,    0, -200, -171, -161, -163,
 -156, -153, -256,    0, -144, -150,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -155, -158, -137,
 -155, -155, -144,    0, -218, -155, -131, -135,    0, -129,
    0, -182,    0,    0, -147, -113,    0,  -54,  -94,  -89,
 -206,    0,    0,  -99, -197,  -91,  -81,  -90,  -70,  -63,
  -58, -141,  -34, -144, -219,    0,  -35,    0,    0,    0,
    0,    0, -252, -252, -155, -147, -147, -252, -252,    0,
  -32,  -11, -173,    9,   -9, -155,  -15, -155, -144,    0,
    0,    0,    0,    0, -167,  -10,    0, -206, -206, -141,
    0,    0,    0,    0,    0,    1,   -2,   10,   -8,   12,
    8,  -15,    0,   14,   20,    0, -188,  -10,  -86,   16,
   32,   31,    0, -144, -136,  -67,   34,  -15,    0,    0,
  256,  -10,    0,    0,   25,   35,  -80,   39, -147, -147,
   43, -147,   37, -241,    0,    0,    0,    0,    0,   40,
    0,   44,    0,   42,   77,  -45,    0,   53,    0,  -15,
    0,    0, -155,    0,   64,    0,   56, -144,   58,    0,
   71,   72,   75,   76,   78, -246,    0,    0,    0,    0,
   68,    0,    0,    0,    0,    0,    0,    0,  -14,   66,
    0,   80,    0,    0,   81,    0, -155, -155, -155, -155,
 -144,  -87,    0,    0,    0,   22,  -15,   82, -254,   83,
   86,   87,   79,   89,   30,  -15,    0,  116,    0,  117,
   96,   93, -155, -144,    0,  -68,  104,  106,  113,    0,
  125,  127,  -15,    0,    0,    0, -144,   -7,  126,  130,
  152,   41,   62,  142,  155, -147,   37, -221,    0,    0,
    0,    0,    0,    0,    0,  146,    0,  162,    0,    0,
  -53,    0,  153, -155,    0,    0,    0, -144,  102,  156,
    0,    0,  164,  167,    0,    0,  115,  178,  158,   73,
    0,  174,    0,  188,    0,  128,    0,  141,    0,  -51,
  175,    0,    0,  154,  190,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  179,    0,    0,  182,
    0,  151,    0,    0,    0,    0,    0,    0,    0,    0,
  173,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -110,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -235,    0,    0,    0,
    0,    0,    0,    0,    0,  -46,    0,  193,  213, -180,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  243,    0,    0,    0,    0,    0,  263,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -235,    0,    0,
    0,  201,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  184,  185,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -235,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -235,    0,    0,    0,
    0,    0,    0,    0,    0,  -29,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  204,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  192,  185,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  345,    0,    0,  346,    2,    6,    7,    0,  370,
 -130,    0,   -5,    0,   -4, -224, -223, -208, -201,  -28,
 -193,  -83,    0,    0,  232,  -43,  -31,    0,    0,  354,
    3,   11,  168,    0,  166,  -92,    0, -103,    0,    0,
    0,
};
final static int YYTABLESIZE=532;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
   23,   66,   56,   57,   62,  115,   19,   26,  126,   39,
   20,   21,   34,  252,  253,   27,   97,   43,   44,  219,
   35,   94,   34,   90,  153,  131,  189,   58,    1,  254,
  202,   36,  101,  102,    4,   35,  255,   73,   74,  155,
   45,   36,   90,  100,  252,  253,   36,   60,   61,   92,
   43,   44,  252,  253,   93,  202,  191,  111,   91,  114,
  254,  252,  253,  252,  253,  269,   36,  255,  254,  252,
  253,   81,   28,  130,  215,  255,   82,  254,  277,  254,
  129,   43,   44,   58,  255,  254,  255,   78,   79,  286,
   65,  288,  255,   78,   35,  161,  162,   78,  164,  294,
  107,   29,  108,  217,  242,  243,  116,   30,  117,   31,
   11,   12,  226,   42,   43,   44,   32,  212,  138,   33,
  145,  146,   43,   44,   38,   22,   23,   41,  148,  241,
   54,   22,   23,   26,  192,   55,  149,   45,   19,   26,
  232,   27,   20,   21,   63,  280,   64,   27,    6,    7,
   73,   74,   36,    8,    9,  139,  140,   10,   11,   12,
   67,   13,  195,   61,   76,   61,  209,   61,  208,   77,
  210,  211,  132,  133,   80,    6,    7,   14,  213,  214,
    8,    9,   84,   15,   10,   11,   12,   86,   13,  141,
  233,  234,   85,    8,  231,   58,  142,   10,   11,   12,
  143,  144,  265,   87,  159,  270,  271,  291,  292,   88,
   15,  171,   84,   84,   89,    8,    9,  172,   58,  173,
  174,  175,   96,  176,   68,   69,   70,   71,   72,   90,
   90,  244,  250,  251,   90,  273,  105,   73,   74,  177,
   98,   99,  171,  103,  104,   15,    8,    9,  172,  245,
  173,  174,  175,    8,  176,  172,  246,  173,  174,  175,
  247,  248,  274,  250,  251,  106,  109,  110,  112,  120,
  204,  250,  251,  118,  121,  123,   15,  249,   81,  216,
  250,  251,  250,  251,  124,  125,  171,  127,  250,  251,
    8,    9,  172,  128,  173,  174,  175,  171,  176,  134,
  135,    8,    9,  172,  136,  173,  174,  175,  157,  176,
  152,  158,  160,  165,  225,  163,  168,  167,  171,  169,
   15,  190,    8,    9,  172,  261,  173,  174,  175,  171,
  176,   15,  193,    8,    9,  172,  170,  173,  174,  175,
  194,  176,  196,  197,  198,  203,  262,  199,  200,  205,
  201,  223,   15,  206,  207,  218,  220,  284,  245,  221,
  222,  224,    8,   15,  172,  246,  173,  174,  175,  247,
  248,  245,  229,  227,  228,    8,  230,  172,  246,  173,
  174,  175,  247,  248,  245,  237,  275,  235,    8,  236,
  172,  246,  173,  174,  175,  247,  248,  245,  239,  281,
  240,    8,  258,  172,  246,  173,  174,  175,  247,  248,
  245,  260,  289,  259,    8,  263,  172,  246,  173,  174,
  175,  247,  248,  267,   72,  290,   72,  264,   72,   72,
   72,   72,   72,   72,  268,  282,  272,  278,  295,  276,
  279,  283,   72,   72,   72,   72,   64,  287,   64,  296,
   64,   64,   64,   64,   64,   64,   11,  285,  293,   52,
   84,   99,  101,   90,   64,   64,   62,  137,   62,   45,
   62,   62,   62,   62,   62,   62,  154,  122,  266,  147,
    0,    0,    0,    0,   62,   62,   63,    0,   63,    0,
   63,   63,   63,   63,   63,   63,    0,    0,    0,   90,
    0,    0,    0,   90,   63,   63,   90,   90,   90,   90,
   90,   90,    7,    0,    0,    0,    8,    0,    0,   84,
   10,   11,   12,   84,  144,    0,    0,   84,   84,   84,
    0,   84,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   45,   31,   32,   36,   89,    5,    5,  112,   15,
    5,    5,  269,  238,  238,    5,  269,  270,  271,  274,
  277,   65,  269,  259,  128,  118,  157,   33,  269,  238,
  277,  288,   76,   77,  284,  277,  238,  292,  293,  132,
  293,  288,  278,   75,  269,  269,  288,  266,  267,  269,
  270,  271,  277,  277,  274,  277,  160,   86,   64,   88,
  269,  286,  286,  288,  288,  259,  288,  269,  277,  294,
  294,  269,  273,  117,  205,  277,  274,  286,  272,  288,
  269,  270,  271,   89,  286,  294,  288,  294,  295,  283,
  273,  285,  294,  274,  277,  139,  140,  278,  142,  293,
  274,  273,  276,  207,  235,  236,  274,  269,  276,  273,
  266,  267,  216,  269,  270,  271,  273,  201,  124,  273,
  126,  126,  270,  271,  269,  131,  131,  278,  126,  233,
  289,  137,  137,  131,  163,  273,  126,  293,  137,  137,
  224,  131,  137,  137,  276,  276,  276,  137,  256,  257,
  292,  293,  288,  261,  262,  292,  293,  265,  266,  267,
  274,  269,  168,  274,  259,  276,  198,  278,  197,  259,
  199,  200,  259,  260,  274,  256,  257,  285,  266,  267,
  261,  262,  274,  291,  265,  266,  267,  278,  269,  257,
  259,  260,  274,  261,  223,  201,  264,  265,  266,  267,
  268,  269,  246,  274,  285,  259,  260,  259,  260,  273,
  291,  257,  259,  260,  273,  261,  262,  263,  224,  265,
  266,  267,  258,  269,  279,  280,  281,  282,  283,  259,
  260,  237,  238,  238,  269,  264,  269,  292,  293,  285,
   73,   74,  257,   78,   79,  291,  261,  262,  263,  257,
  265,  266,  267,  261,  269,  263,  264,  265,  266,  267,
  268,  269,  268,  269,  269,  277,  258,  277,  284,  269,
  285,  277,  277,  284,  277,  284,  291,  285,  269,  258,
  286,  286,  288,  288,  273,  278,  257,  274,  294,  294,
  261,  262,  263,  274,  265,  266,  267,  257,  269,  284,
  269,  261,  262,  263,  274,  265,  266,  267,  284,  269,
  277,  277,  274,  277,  285,  273,  273,  278,  257,  278,
  291,  269,  261,  262,  263,  285,  265,  266,  267,  257,
  269,  291,  269,  261,  262,  263,  260,  265,  266,  267,
  285,  269,  285,  273,  273,  278,  285,  273,  273,  284,
  273,  273,  291,  274,  274,  274,  274,  285,  257,  274,
  274,  273,  261,  291,  263,  264,  265,  266,  267,  268,
  269,  257,  277,  258,  258,  261,  284,  263,  264,  265,
  266,  267,  268,  269,  257,  273,  285,  284,  261,  284,
  263,  264,  265,  266,  267,  268,  269,  257,  274,  285,
  274,  261,  277,  263,  264,  265,  266,  267,  268,  269,
  257,  260,  285,  284,  261,  274,  263,  264,  265,  266,
  267,  268,  269,  278,  274,  285,  276,  273,  278,  279,
  280,  281,  282,  283,  273,  258,  284,  274,  285,  284,
  274,  284,  292,  293,  294,  295,  274,  260,  276,  260,
  278,  279,  280,  281,  282,  283,  278,  284,  284,  278,
  260,  278,  278,  260,  292,  293,  274,  123,  276,  278,
  278,  279,  280,  281,  282,  283,  131,  108,  247,  126,
   -1,   -1,   -1,   -1,  292,  293,  274,   -1,  276,   -1,
  278,  279,  280,  281,  282,  283,   -1,   -1,   -1,  257,
   -1,   -1,   -1,  261,  292,  293,  264,  265,  266,  267,
  268,  269,  257,   -1,   -1,   -1,  261,   -1,   -1,  257,
  265,  266,  267,  261,  269,   -1,   -1,  265,  266,  267,
   -1,  269,
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
"sentencia : error",
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

//#line 174 "gramatica.y"

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
//#line 553 "Parser.java"
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
case 66:
//#line 100 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 67:
//#line 101 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
//#line 710 "Parser.java"
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
