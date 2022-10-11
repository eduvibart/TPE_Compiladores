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
package Principal;
import java.io.IOException;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
//#line 22 "Parser.java"




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
    0,    0,    1,    2,    2,    2,    3,    3,    4,    4,
    4,    6,    9,    9,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,   11,   11,   11,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   17,   17,   17,   17,   17,   17,   17,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   18,   18,   18,   18,   18,   18,   22,   22,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   20,   10,    8,   29,   29,    5,    5,    5,    5,    5,
    5,    5,   13,   28,   28,   28,   28,   28,   28,   34,
   34,   34,   35,   35,   27,   27,   27,   27,   30,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   21,   37,   37,   37,   37,   37,   36,   36,   36,   16,
   16,   16,   16,   31,   31,   31,   31,   31,   31,   31,
   31,   33,   33,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   38,   38,   39,   39,   39,   39,   39,   39,
   39,   39,   39,   26,   26,   40,   40,   32,   32,   32,
   32,   32,   32,   23,   23,   41,   41,   14,   14,   14,
   14,   14,   14,
};
final static short yylen[] = {                            2,
    4,    1,    1,    0,    3,    2,    1,    1,    1,    1,
    1,    2,    3,    1,   12,   10,    9,   12,   10,    9,
    8,    7,    5,    4,    3,    2,    0,    3,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
   13,    9,   13,   12,   10,    9,    8,    6,    5,    4,
    3,    2,    8,    8,    6,    5,    4,    3,    8,   13,
   11,   11,    9,    8,    7,    6,    5,    4,    3,    2,
    9,    7,    7,    5,    4,    2,    0,    2,    1,    1,
    1,    1,    1,    1,    2,    1,    2,    1,   13,    9,
   13,   12,   10,    9,    8,    6,    5,    4,    3,    2,
    4,    2,    2,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    1,    1,    3,    3,    3,
    3,    1,    1,    1,    1,    1,    2,    2,   13,    9,
   13,   12,   10,    9,    8,    6,    5,    4,    3,    2,
    3,    1,    1,    1,    1,    1,    0,    3,    2,    4,
    4,    3,    2,    8,    8,    8,    8,    8,    8,    7,
    5,   13,   11,   11,    9,    8,    7,    6,    5,    4,
    3,    2,    0,    4,    1,    1,    1,    1,    1,    1,
    2,    1,    2,    0,    2,    9,    7,    9,    7,    7,
    5,    4,    2,    6,    6,    1,    1,    6,    4,    3,
    6,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    4,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    7,    8,    9,   10,
   11,  106,  112,  108,  107,  109,  110,  111,  140,    0,
  153,    0,   26,    0,    0,    0,  172,    0,  193,    0,
   14,    0,    0,    0,    0,    0,  105,    0,    5,  139,
    0,  125,  126,    0,  117,    0,  124,    0,    0,    0,
    0,  122,  152,    0,   25,    0,    0,    0,    0,  171,
    0,    0,    0,  203,  197,  200,  196,    0,    0,    0,
    0,    0,    0,  127,  128,  138,    0,  142,  143,  144,
  145,  146,    0,    0,    0,    0,    0,    0,    0,  151,
  150,   24,    0,    0,    0,    0,    0,    0,    0,  170,
    0,    0,  192,    0,  202,  199,    0,    0,    0,   13,
  104,  137,    0,  123,    0,    0,    0,  118,  119,  120,
  121,  102,    0,   23,    0,    0,    0,    0,    4,    0,
    0,  169,    0,    0,  191,    0,    0,    0,    0,  136,
  147,    0,    0,    0,    4,    4,    0,    4,    4,    4,
  168,    0,    0,    0,    0,  201,  198,    0,    0,    0,
   27,    0,   22,    0,    0,    0,  160,    0,    0,    0,
  167,    0,    0,    0,    0,  190,  189,    0,    0,  135,
    0,    0,    0,    0,   27,   21,    0,  159,  158,  157,
  156,  155,  154,  166,    0,  194,  195,    0,    0,    0,
    0,  175,  177,  178,  180,  179,    0,  176,    0,    0,
  134,    0,  130,  148,    0,    0,    0,    0,    0,    0,
   17,   30,   31,   32,    0,   33,   34,   35,   36,   37,
   38,   39,   40,    0,   20,    0,  165,    0,    0,  183,
    0,  181,  174,    0,  188,  133,  147,   52,    0,    0,
    0,    0,   70,    0,   76,    0,    0,   28,   16,   19,
   27,    0,    0,  185,    0,    0,   51,    0,    0,    0,
   58,    0,   69,    0,    0,    0,    0,    0,  164,  163,
    0,    0,  132,    0,   50,    0,  101,    0,   57,    0,
   68,    0,   75,    0,    0,    0,   18,   15,    0,    0,
  131,  129,   49,    0,    0,   56,    0,   67,    0,   74,
   77,    0,    0,    0,  162,   48,   27,   27,   55,   27,
   66,    0,    0,    0,    0,    0,  187,    0,    0,    0,
   65,    0,   73,    0,    0,    0,    0,   72,   79,   81,
   82,   84,   83,   88,   78,   80,    0,   77,    0,   47,
    0,   59,   54,   53,   64,    0,  100,    0,   87,   85,
    0,    0,  186,   46,    0,   42,   63,   77,   99,    0,
    0,   71,   45,   27,    0,   98,    0,    0,    0,   62,
   61,   97,    0,   77,   44,    0,   96,   77,    0,   43,
   41,    0,   60,   95,    0,   94,    0,   90,   93,   77,
    0,   92,    0,   91,   89,
};
final static short yydgoto[] = {                          3,
    4,    6,   16,   17,   18,   19,   20,   21,   45,  105,
  194,  235,   22,   55,  238,   24,  240,  241,  242,  243,
   56,  333,   73,  355,  356,  252,   57,   58,   48,   25,
   26,   59,   60,   61,   62,  170,   95,  165,  217,  218,
   78,
};
final static short yysindex[] = {                        58,
    0,    0,    0, -256,    0,  485, -194, -191,  126,  -46,
  -32,   15,  296,    0, -229, -171,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -234,
    0, -250,    0,  115,  562,  -24,    0,  -17,    0, -229,
    0, -138,  -78,  562,  -82,  -65,    0,  -11,    0,    0,
   56,    0,    0,   28,    0, -232,    0,  721,   63,  100,
  169,    0,    0, -117,    0,   12,  -39,  -30, -128,    0,
  -76,  -23,  -72,    0,    0,    0,    0,  -63,   14,  107,
  179,  144, -229,    0,    0,    0, -157,    0,    0,    0,
    0,    0, -143, -143,  562, -240, -240, -143, -143,    0,
    0,    0,  177,  145,    1,  200,  224,  165, -118,    0,
 -230,  562,    0, -241,    0,    0,  -33,  562, -229,    0,
    0,    0, -235,    0,  169,  169,  179,    0,    0,    0,
    0,    0,  215,    0,  231,  247,  242,  253,    0,  259,
 -227,    0,  133,  273,    0,  288,    8,  302,  303,    0,
    0,  294,  310,   45,    0,    0,  498,    0,    0,    0,
    0,  155,  207,  288, -244,    0,    0,  280,  299,   46,
    0,  308,    0, -172,  512,  525,    0,  539,  552,  417,
    0,   64, -240, -240,  754,    0,    0,  323,  288,    0,
  210, -115,  319,  444,    0,    0,  164,    0,    0,    0,
    0,    0,    0,    0, -211,    0,    0,  325, -240,  329,
  -74,    0,    0,    0,    0,    0,  317,    0, -229,  334,
    0, -196,    0,    0,  134,  335,  135,  153,  178,  426,
    0,    0,    0,    0,  349,    0,    0,    0,    0,    0,
    0,    0,    0,  458,    0, -190,    0,  288,  562,    0,
  359,    0,    0,  363,    0,    0,    0,    0,   -8,  562,
  562,   23,    0,   39,    0, -229,  235,    0,    0,    0,
    0, -237,  365,    0,  357,  688,    0,   65, -158,  370,
    0,   70,    0,   87,   98,  360,  372,  324,    0,    0,
  391,  288,    0,  229,    0,   72,    0,  392,    0,  125,
    0, -104,    0, -187,  562, -229,    0,    0,  288,  373,
    0,    0,    0, -184,  375,    0, -182,    0,  188,    0,
    0,  394,  396,  255,    0,    0,    0,    0,    0,    0,
    0,  191,  583,  395,  397,  288,    0,  338,  471,  355,
    0,   99,    0,  189, -240,  329,  -21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  402,    0,  416,    0,
  311,    0,    0,    0,    0, -178,    0,  240,    0,    0,
 -229,  713,    0,    0, -170,    0,    0,    0,    0,  112,
  406,    0,    0,    0,  597,    0,  167,  405,  369,    0,
    0,    0, -165,    0,    0,  271,    0,    0,  727,    0,
    0,  620,    0,    0,  354,    0, -161,    0,    0,    0,
  634,    0,  282,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  431,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  283,    0,    0,  297,    0,    0,
   84,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  136,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  256,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  174,  212,   22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  386,
    0,    0,    0,    0,    0, -233,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  767,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  409,    0,
    0,    0,  702,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  419,  427,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  400,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -233,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  409,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  269,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  430,    0,    0,    0,    0,
    0,    0,    0,    0,  657,  671,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  792,    0,    0, -167, -180, -123, -119,    0,  581,
 -179,    0,  -15,   -4,    0, -177, -322, -289, -287, -282,
  -31, -340,  -99,    0,    0,  380,  -41,  -34,    0,    0,
  533,    7,   21,  442,  457,  473,    0, -155,    0,    0,
  614,
};
final static int YYTABLESIZE=1036;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   77,   23,  193,   67,   69,   63,   71,  213,  185,   81,
  351,  186,   27,  232,  145,  244,  239,  372,  289,  149,
  150,   50,  173,   86,   72,  142,   28,    5,  159,   52,
   53,   11,   12,  220,   51,   52,   53,  385,   64,   46,
  187,   87,  146,  352,  247,  353,  143,  290,  151,  351,
  354,  173,   54,  399,  128,  129,  160,  402,   54,  256,
  127,   29,  351,  232,   31,  270,  239,  121,  320,  411,
  233,  326,  248,  329,  234,   77,  351,  377,   30,  351,
  144,   32,  352,  196,  353,  383,  148,  257,  351,  354,
  397,  288,  272,  271,  409,  352,  321,  353,  122,  327,
  123,  330,  354,   72,  197,  378,   49,  232,  193,  352,
  239,  353,  352,  384,  353,  297,  354,   74,  398,  354,
  233,  352,  410,  353,  234,  124,   52,   53,  354,  108,
   75,   52,   53,   93,   94,   76,  310,  140,  100,  141,
  221,  206,  207,  222,  223,  109,  182,  338,  339,   54,
  340,  318,   23,  324,   54,  350,  101,  232,  232,  232,
  239,  239,  239,   27,  233,   23,  285,  250,  234,  212,
   23,   23,  319,   23,   23,   23,   27,   28,  236,  110,
  359,   27,   27,  113,   27,   27,   27,   79,   80,  237,
   28,  215,  115,   82,  350,   28,   28,  111,   28,   28,
   28,  114,   43,  254,  389,  216,  323,  350,  232,   35,
  116,  239,  117,   44,  233,  233,  233,  273,  234,  234,
  234,  350,   44,   37,  350,  279,   36,  278,  236,  280,
  282,   68,  284,  350,  106,   75,   52,   53,   70,  237,
   38,   11,   12,  107,   51,   52,   53,  277,   11,   12,
   72,   51,   52,   53,  112,  267,  134,   11,   12,   54,
   51,   52,   53,  166,   83,  233,   44,  102,   54,  234,
   39,   23,  236,  322,  135,   54,  136,  141,  281,  141,
  103,  167,   27,  237,   54,  104,  118,   40,   11,   12,
   72,   51,   52,   53,  283,  141,   28,   84,   85,  141,
  173,  190,    7,  369,   11,   12,    8,   51,   52,   53,
   10,   11,   12,    1,  191,   54,  342,  349,  174,  204,
  295,   96,  236,  236,  236,  299,    2,  313,   42,  314,
  192,   54,   43,  237,  237,  237,  380,  205,  296,  123,
  123,  123,  301,  300,  123,  123,  123,  123,  123,  123,
  123,  123,  123,  303,  365,  381,  349,  123,   97,  123,
  302,  123,  123,  123,  123,  123,  123,  386,  123,  349,
   65,  304,  366,  236,  123,  123,  123,  123,  123,  119,
  316,   33,  317,  349,  237,  387,  349,   66,  161,  258,
  261,  116,  116,  116,   34,  349,  116,  116,  116,  116,
  116,  116,  116,  116,  116,  162,  259,  262,  263,  116,
  181,  116,  120,  116,  116,  116,  116,  116,  116,  245,
  116,  133,  392,   46,  393,  264,  116,  116,  116,  114,
  114,  114,  246,  265,  114,  114,  114,  114,  114,  114,
  114,  114,  114,  331,  367,  132,  341,  114,  139,  114,
  266,  114,  114,  114,  114,  114,  114,  137,  114,   46,
  332,  368,   98,   99,  114,  114,  114,  115,  115,  115,
   93,   94,  115,  115,  115,  115,  115,  115,  115,  115,
  115,  138,   42,  152,  311,  115,   43,  115,  312,  115,
  115,  115,  115,  115,  115,  379,  115,   44,  183,  184,
  286,  287,  115,  115,  115,   11,   12,  153,   51,   52,
   53,  113,  113,  336,  337,  103,  113,  113,  113,  113,
  113,  113,  113,  113,  113,  155,  400,  173,  173,  113,
  401,  113,   54,  113,  125,  126,  156,  414,   12,   12,
  113,  415,  158,   12,   12,   12,  113,   12,   12,   12,
  163,   12,  103,  103,  130,  131,  188,  103,  103,  103,
   12,  103,  103,  103,   41,  103,  374,   12,   42,  375,
  376,  164,   43,   12,  103,  168,  169,  171,  172,  307,
  225,  103,  189,   44,    8,    9,  226,  103,  227,  228,
  229,  195,  230,  360,  225,  219,  224,  249,    8,    9,
  226,  253,  227,  228,  229,  251,  230,  260,  308,  406,
  363,  225,  407,  408,   15,    8,    9,  226,  255,  227,
  228,  229,  361,  230,  395,  225,  268,  274,   15,    8,
    9,  226,  305,  227,  228,  229,  275,  230,  291,  364,
  292,  161,  161,  298,  306,   15,  161,  161,  309,  315,
  161,  161,  161,  396,  161,   29,   29,  325,  328,   15,
   29,   29,   29,  161,   29,   29,   29,  334,   29,  335,
  161,  357,  202,    7,  371,  373,  161,    8,    9,  388,
  358,   10,   11,   12,   29,   13,    6,    6,  394,  173,
   29,    6,    6,  173,   41,    6,    6,    6,   42,    6,
  225,  203,  267,  182,    8,    9,  226,   15,  227,  228,
  229,  184,  230,   44,  225,    6,  154,  214,    8,    9,
  226,    6,  227,  228,  229,  370,  230,  225,  231,  276,
  147,    8,    9,  226,   15,  227,  228,  229,    0,  230,
    0,    7,  269,    0,    0,    8,    9,    0,   15,   10,
   11,   12,    0,   13,    7,  362,    0,    0,    8,    9,
    0,   15,   10,   11,   12,    0,   13,    0,    7,   14,
    0,    0,    8,    9,    0,   15,   10,   11,   12,    0,
   13,    7,  177,    0,    0,    8,    9,    0,   15,   10,
   11,   12,    0,   13,    0,    7,  198,    0,    0,    8,
    9,    0,   15,   10,   11,   12,    0,   13,    7,  199,
    0,    0,    8,    9,    0,   15,   10,   11,   12,    0,
   13,    0,    0,  200,    0,    0,    0,   11,   12,   15,
   51,   52,   53,    0,    0,    0,  201,    0,  343,  344,
    0,    0,   15,    8,    0,  226,  345,  227,  228,  229,
  346,  347,  390,  344,   54,    0,    0,    8,    0,  226,
  345,  227,  228,  229,  346,  347,    0,  348,    0,    0,
    0,    0,    0,    0,    0,  404,  344,    0,    0,    0,
    8,  391,  226,  345,  227,  228,  229,  346,  347,  412,
  344,    0,    0,    0,    8,    0,  226,  345,  227,  228,
  229,  346,  347,    0,  405,    0,    0,    0,    0,    0,
    0,    0,   86,   86,    0,    0,    0,   86,  413,   86,
   86,   86,   86,   86,   86,   86,  184,  184,    0,    0,
  157,  184,    0,  184,  184,  184,  184,  184,  184,  184,
    0,   86,    0,  293,    7,    0,  175,  176,    8,  178,
  179,  180,   10,   11,   12,  184,  191,  149,  149,    0,
    0,    0,  149,    0,    0,    0,  149,  149,  149,  344,
  149,    0,  294,    8,    0,  226,  345,  227,  228,  229,
  346,  347,    0,  344,    0,    0,  149,    8,    0,  226,
  345,  227,  228,  229,  346,  347,    0,  382,    0,   88,
   89,   90,   91,   92,    0,    0,    0,    0,    0,    0,
  208,  403,   93,   94,    8,    0,    0,  209,   10,   11,
   12,  210,  211,  173,    0,    0,    0,  173,    0,    0,
  173,  173,  173,  173,  173,  173,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   42,    6,  170,   35,   36,  256,   38,  185,  164,   44,
  333,  256,    6,  194,  256,  195,  194,  358,  256,  119,
  256,  256,  256,  256,   40,  256,    6,  284,  256,  270,
  271,  266,  267,  189,  269,  270,  271,  378,  289,  269,
  285,  274,  284,  333,  256,  333,  277,  285,  284,  372,
  333,  285,  293,  394,   96,   97,  284,  398,  293,  256,
   95,  256,  385,  244,  256,  256,  244,   83,  256,  410,
  194,  256,  284,  256,  194,  117,  399,  256,  273,  402,
  112,  273,  372,  256,  372,  256,  118,  284,  411,  372,
  256,  271,  248,  284,  256,  385,  284,  385,  256,  284,
  258,  284,  385,  119,  277,  284,  278,  288,  276,  399,
  288,  399,  402,  284,  402,  274,  399,  256,  284,  402,
  244,  411,  284,  411,  244,  269,  270,  271,  411,  258,
  269,  270,  271,  292,  293,  274,  292,  256,  256,  258,
  256,  183,  184,  259,  260,  274,  162,  327,  328,  293,
  330,  256,  157,  309,  293,  333,  274,  338,  339,  340,
  338,  339,  340,  157,  288,  170,  266,  209,  288,  185,
  175,  176,  277,  178,  179,  180,  170,  157,  194,  256,
  336,  175,  176,  256,  178,  179,  180,  266,  267,  194,
  170,  185,  256,  276,  372,  175,  176,  274,  178,  179,
  180,  274,  277,  219,  384,  185,  306,  385,  389,  256,
  274,  389,  276,  288,  338,  339,  340,  249,  338,  339,
  340,  399,  288,  256,  402,  260,  273,  259,  244,  261,
  262,  256,  264,  411,  274,  269,  270,  271,  256,  244,
  273,  266,  267,  274,  269,  270,  271,  256,  266,  267,
  266,  269,  270,  271,  278,  277,  256,  266,  267,  293,
  269,  270,  271,  256,  276,  389,  288,  256,  293,  389,
  256,  276,  288,  305,  274,  293,  276,  256,  256,  258,
  269,  274,  276,  288,  293,  274,  273,  273,  266,  267,
  306,  269,  270,  271,  256,  274,  276,  270,  271,  278,
  256,  256,  257,  345,  266,  267,  261,  269,  270,  271,
  265,  266,  267,  256,  269,  293,  332,  333,  274,  256,
  256,  259,  338,  339,  340,  256,  269,  256,  273,  258,
  285,  293,  277,  338,  339,  340,  368,  274,  274,  256,
  257,  258,  256,  274,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  256,  256,  371,  372,  274,  259,  276,
  274,  278,  279,  280,  281,  282,  283,  256,  285,  385,
  256,  274,  274,  389,  291,  292,  293,  294,  295,  273,
  256,  256,  258,  399,  389,  274,  402,  273,  256,  256,
  256,  256,  257,  258,  269,  411,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  273,  273,  273,  256,  274,
  256,  276,  269,  278,  279,  280,  281,  282,  283,  256,
  285,  277,  256,  269,  258,  273,  291,  292,  293,  256,
  257,  258,  269,  256,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  256,  256,  269,  256,  274,  284,  276,
  273,  278,  279,  280,  281,  282,  283,  258,  285,  269,
  273,  273,  294,  295,  291,  292,  293,  256,  257,  258,
  292,  293,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  258,  273,  269,  256,  274,  277,  276,  260,  278,
  279,  280,  281,  282,  283,  256,  285,  288,  292,  293,
  266,  267,  291,  292,  293,  266,  267,  277,  269,  270,
  271,  256,  257,  259,  260,  269,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  284,  256,  259,  260,  274,
  260,  276,  293,  278,   93,   94,  284,  256,  256,  257,
  285,  260,  284,  261,  262,  263,  291,  265,  266,  267,
  278,  269,  256,  257,   98,   99,  277,  261,  262,  263,
  278,  265,  266,  267,  269,  269,  256,  285,  273,  259,
  260,  284,  277,  291,  278,  274,  274,  284,  269,  256,
  257,  285,  284,  288,  261,  262,  263,  291,  265,  266,
  267,  284,  269,  256,  257,  273,  278,  273,  261,  262,
  263,  285,  265,  266,  267,  277,  269,  273,  285,  256,
  256,  257,  259,  260,  291,  261,  262,  263,  285,  265,
  266,  267,  285,  269,  256,  257,  278,  269,  291,  261,
  262,  263,  273,  265,  266,  267,  274,  269,  274,  285,
  284,  256,  257,  274,  273,  291,  261,  262,  258,  258,
  265,  266,  267,  285,  269,  256,  257,  285,  284,  291,
  261,  262,  263,  278,  265,  266,  267,  274,  269,  274,
  285,  277,  256,  257,  273,  260,  291,  261,  262,  274,
  284,  265,  266,  267,  285,  269,  256,  257,  284,  260,
  291,  261,  262,  285,  269,  265,  266,  267,  273,  269,
  257,  285,  277,  285,  261,  262,  263,  291,  265,  266,
  267,  285,  269,  288,  257,  285,  136,  185,  261,  262,
  263,  291,  265,  266,  267,  346,  269,  257,  285,  257,
  117,  261,  262,  263,  291,  265,  266,  267,   -1,  269,
   -1,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,   -1,  269,  257,  285,   -1,   -1,  261,  262,
   -1,  291,  265,  266,  267,   -1,  269,   -1,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,   -1,  269,   -1,  257,  285,   -1,   -1,  261,
  262,   -1,  291,  265,  266,  267,   -1,  269,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,   -1,   -1,  285,   -1,   -1,   -1,  266,  267,  291,
  269,  270,  271,   -1,   -1,   -1,  285,   -1,  256,  257,
   -1,   -1,  291,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  256,  257,  293,   -1,   -1,  261,   -1,  263,
  264,  265,  266,  267,  268,  269,   -1,  285,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,   -1,
  261,  285,  263,  264,  265,  266,  267,  268,  269,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,   -1,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,   -1,   -1,  261,  285,  263,
  264,  265,  266,  267,  268,  269,  256,  257,   -1,   -1,
  139,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
   -1,  285,   -1,  256,  257,   -1,  155,  156,  261,  158,
  159,  160,  265,  266,  267,  285,  269,  256,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,  265,  266,  267,  257,
  269,   -1,  285,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,   -1,  257,   -1,   -1,  285,  261,   -1,  263,
  264,  265,  266,  267,  268,  269,   -1,  285,   -1,  279,
  280,  281,  282,  283,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  285,  292,  293,  261,   -1,   -1,  264,  265,  266,
  267,  268,  269,  257,   -1,   -1,   -1,  261,   -1,   -1,
  264,  265,  266,  267,  268,  269,
};
}
final static short YYFINAL=3;
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
"program : error",
"nombre_program : ID",
"bloque_sentencias :",
"bloque_sentencias : bloque_sentencias sentencia PUNTOCOMA",
"bloque_sentencias : bloque_sentencias sentencia",
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
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro error",
"sentencia_decl_fun : FUN ID PARENT_A parametro error",
"sentencia_decl_fun : FUN ID PARENT_A error",
"sentencia_decl_fun : FUN ID error",
"sentencia_decl_fun : FUN error",
"cuerpo_fun :",
"cuerpo_fun : cuerpo_fun sentencias_fun PUNTOCOMA",
"cuerpo_fun : cuerpo_fun sentencias_fun",
"sentencias_fun : sentencia_decl_datos",
"sentencias_fun : sentencia_decl_fun",
"sentencias_fun : lista_const",
"sentencias_fun : asignacion",
"sentencias_fun : llamado_func",
"sentencias_fun : sentencia_if_fun",
"sentencias_fun : sentencia_out",
"sentencias_fun : sentencia_when_fun",
"sentencias_fun : sentencia_for_fun",
"sentencias_fun : sentencia_while_fun",
"sentencias_fun : retorno",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C END_IF",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C END_IF",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun LLAVE_C error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE LLAVE_A cuerpo_fun error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C ELSE error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C THEN error",
"sentencia_if_fun : IF PARENT_A condicion PARENT_C error",
"sentencia_if_fun : IF PARENT_A condicion error",
"sentencia_if_fun : IF PARENT_A error",
"sentencia_if_fun : IF error",
"sentencia_when_fun : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_when_fun : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun error",
"sentencia_when_fun : WHEN PARENT_A condicion PARENT_C THEN error",
"sentencia_when_fun : WHEN PARENT_A condicion PARENT_C error",
"sentencia_when_fun : WHEN PARENT_A condicion error",
"sentencia_when_fun : WHEN PARENT_A error",
"sentencia_when_fun : WHEN error condicion PARENT_C THEN LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_while_fun : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A cuerpo_fun_break error",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C DOSPUNTOS error",
"sentencia_while_fun : WHILE PARENT_A condicion PARENT_C error",
"sentencia_while_fun : WHILE PARENT_A condicion error",
"sentencia_while_fun : WHILE PARENT_A error",
"sentencia_while_fun : WHILE error",
"sentencia_for_fun : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_for_fun : FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break LLAVE_C",
"sentencia_for_fun : FOR PARENT_A encabezado_for PARENT_C LLAVE_A cuerpo_fun_break error",
"sentencia_for_fun : FOR PARENT_A encabezado_for PARENT_C error",
"sentencia_for_fun : FOR PARENT_A encabezado_for error",
"sentencia_for_fun : FOR error",
"cuerpo_fun_break :",
"cuerpo_fun_break : cuerpo_fun_break sentencias_fun_break",
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
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break LLAVE_C error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE LLAVE_A cuerpo_fun_break error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C ELSE error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break LLAVE_C error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN LLAVE_A cuerpo_fun_break error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C THEN error",
"sentencia_if_break_fun : IF PARENT_A condicion PARENT_C error",
"sentencia_if_break_fun : IF PARENT_A condicion error",
"sentencia_if_break_fun : IF PARENT_A error",
"sentencia_if_break_fun : IF error",
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
"sentencia_ejecutable : llamado_func",
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
"factor : ID",
"factor : cte",
"cte : I32",
"cte : F32",
"cte : RESTA I32",
"cte : RESTA F32",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C error",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable error",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE error",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C error",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable error",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN error",
"sentencia_if : IF PARENT_A condicion PARENT_C error",
"sentencia_if : IF PARENT_A condicion error",
"sentencia_if : IF PARENT_A error",
"sentencia_if : IF error",
"condicion : expresion comparacion expresion",
"comparacion : IGUAL",
"comparacion : MAYOR",
"comparacion : MENOR",
"comparacion : MENORIGUAL",
"comparacion : MAYORIGUAL",
"bloque_ejecutable :",
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable PUNTOCOMA",
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_out : OUT PARENT_A CADENA error",
"sentencia_out : OUT PARENT_A error",
"sentencia_out : OUT error",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias error",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN error bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C error LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A error PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN error condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C error",
"sentencia_while : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue error",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C error",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion error",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A error",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS error",
"sentencia_while : WHILE PARENT_A condicion PARENT_C error",
"sentencia_while : WHILE PARENT_A condicion error",
"sentencia_while : WHILE PARENT_A error",
"sentencia_while : WHILE error",
"bloque_break_continue :",
"bloque_break_continue : LLAVE_A bloque_break_continue ejecutables_break_continue LLAVE_C",
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
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue error",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C error",
"sentencia_for : FOR PARENT_A encabezado_for error",
"sentencia_for : FOR error",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte",
"param_real : cte",
"param_real : ID",
"llamado_func : ID PARENT_A param_real COMA param_real PARENT_C",
"llamado_func : ID PARENT_A param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
"llamado_func : ID PARENT_A param_real COMA param_real error",
"llamado_func : ID PARENT_A param_real error",
"llamado_func : ID PARENT_A error",
};

//#line 270 "gramatica.y"

void yyerror(String mensaje){
        System.out.println("Linea"+ AnalizadorLexico.getLineaAct() +"| Error sintactico: " + mensaje);
}
void chequearRangoI32(String sval){
  String s = "2147483648";
  long l = Long.valueOf(s);
  if(Long.valueOf(sval) > l){
    System.out.println("La constante esta fuera de rango");
  }
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        this.yylval = new ParserVal(t.getLexema());
        if(t.getId() != -1){
          System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        }else
          System.out.println("TERMINO LA EJECUCION");
        return t.getId();
}
//#line 798 "Parser.java"
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
case 2:
//#line 17 "gramatica.y"
{yyerror("Hay un error sintactico en la entrada que arrastra errores.a");}
break;
case 6:
//#line 23 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 12:
//#line 32 "gramatica.y"
{System.out.println("Declaracion de datos");}
break;
case 15:
//#line 37 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 16:
//#line 38 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 17:
//#line 39 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 18:
//#line 40 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 19:
//#line 41 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 20:
//#line 42 "gramatica.y"
{yyerror("Se esperaba ID");}
break;
case 21:
//#line 43 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 22:
//#line 44 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 23:
//#line 45 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 24:
//#line 46 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 25:
//#line 47 "gramatica.y"
{System.out.println("Falta declaracion de parametro en la funcion");}
break;
case 26:
//#line 48 "gramatica.y"
{System.out.println("Si declaras una funcion hacelo bien!");}
break;
case 29:
//#line 52 "gramatica.y"
{System.out.println("Se esperaba ;");}
break;
case 41:
//#line 66 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 42:
//#line 67 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 43:
//#line 68 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 44:
//#line 69 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 45:
//#line 70 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 46:
//#line 71 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 47:
//#line 72 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 48:
//#line 73 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 49:
//#line 74 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 50:
//#line 75 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 51:
//#line 76 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 52:
//#line 77 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 53:
//#line 79 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 54:
//#line 80 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 55:
//#line 81 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 56:
//#line 82 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 57:
//#line 83 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 58:
//#line 84 "gramatica.y"
{yyerror("Se esperaba condicion");}
break;
case 59:
//#line 85 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 60:
//#line 87 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 61:
//#line 88 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 62:
//#line 89 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 63:
//#line 90 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 64:
//#line 91 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 65:
//#line 92 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 66:
//#line 93 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 67:
//#line 94 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 68:
//#line 95 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 69:
//#line 96 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 70:
//#line 97 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 71:
//#line 99 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 72:
//#line 100 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 73:
//#line 101 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 74:
//#line 102 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 75:
//#line 103 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 76:
//#line 104 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 89:
//#line 121 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 90:
//#line 122 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 91:
//#line 123 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 92:
//#line 124 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 93:
//#line 125 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 94:
//#line 126 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 95:
//#line 127 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 96:
//#line 128 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 97:
//#line 129 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 98:
//#line 130 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 99:
//#line 131 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 100:
//#line 132 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 103:
//#line 139 "gramatica.y"
{System.out.println("Declaracion de Constante/s");}
break;
case 113:
//#line 152 "gramatica.y"
{System.out.println("Asignacion");}
break;
case 125:
//#line 169 "gramatica.y"
{  chequearRangoI32(val_peek(0).sval);}
break;
case 129:
//#line 176 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 130:
//#line 177 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 131:
//#line 178 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 132:
//#line 179 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 133:
//#line 180 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 134:
//#line 181 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 135:
//#line 182 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 136:
//#line 183 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 137:
//#line 184 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 138:
//#line 185 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 139:
//#line 186 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 140:
//#line 187 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 149:
//#line 199 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 150:
//#line 201 "gramatica.y"
{System.out.println("Sentencia OUT");}
break;
case 151:
//#line 202 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 152:
//#line 203 "gramatica.y"
{yyerror("Se esperaba una CADENA");}
break;
case 153:
//#line 204 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 154:
//#line 206 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 155:
//#line 207 "gramatica.y"
{yyerror("Se esperaba } en el when");}
break;
case 156:
//#line 208 "gramatica.y"
{yyerror("Se esperaba { en el when");}
break;
case 157:
//#line 209 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 158:
//#line 210 "gramatica.y"
{yyerror("Se esperaba condicion en el when");}
break;
case 159:
//#line 211 "gramatica.y"
{yyerror("Se esperaba ( en el when");}
break;
case 160:
//#line 212 "gramatica.y"
{yyerror("Se esperaba ) en el when");}
break;
case 161:
//#line 213 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 162:
//#line 215 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 163:
//#line 216 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 164:
//#line 217 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 165:
//#line 218 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 166:
//#line 219 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 167:
//#line 220 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 168:
//#line 221 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 169:
//#line 222 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 170:
//#line 223 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 171:
//#line 224 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 172:
//#line 225 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 186:
//#line 245 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 187:
//#line 246 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 188:
//#line 248 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 189:
//#line 249 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 190:
//#line 250 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 191:
//#line 251 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 192:
//#line 252 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 193:
//#line 253 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 201:
//#line 265 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 202:
//#line 266 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 203:
//#line 267 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
//#line 1403 "Parser.java"
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
