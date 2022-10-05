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
    0,    1,    2,    2,    3,    3,    3,    4,    4,    4,
    6,    9,    9,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,   11,   11,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   14,   14,   16,   18,
   18,   17,   17,   21,   21,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   24,   24,   19,   10,    8,
   28,   28,    5,    5,    5,    5,    5,    5,   13,   27,
   27,   27,   27,   27,   27,   33,   33,   33,   35,   35,
   26,   26,   26,   26,   29,   29,   20,   37,   37,   37,
   37,   37,   36,   36,   15,   30,   32,   32,   38,   38,
   39,   39,   39,   39,   39,   39,   39,   39,   39,   25,
   25,   40,   40,   31,   31,   22,   22,   41,   41,   34,
   34,   34,   34,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    1,
    2,    3,    1,   12,   10,    9,   11,    9,    8,    5,
   11,    9,    8,    5,   11,    9,    8,    3,    3,   11,
    9,    8,    2,    1,    0,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,   13,    9,    8,   11,
   13,    7,    9,    0,    2,    1,    1,    1,    1,    1,
    1,    2,    1,    2,    1,   13,    9,    4,    2,    2,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    3,    1,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    2,    2,   13,    9,    3,    1,    1,    1,
    1,    1,    0,    2,    4,    8,    9,   11,    0,    4,
    1,    1,    1,    1,    1,    1,    2,    1,    2,    0,
    2,    9,    7,    5,    7,    6,    6,    1,    1,    6,
    4,    3,    7,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    5,    6,    8,    9,
   10,   73,   75,   74,   76,   77,   78,    0,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    0,   72,    0,
    4,    0,   91,   92,    0,    0,   90,    0,    0,    0,
    0,   83,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   93,   94,
    0,   98,   99,  100,  101,  102,    0,    0,    0,    0,
    0,    0,    0,  105,   69,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,   71,  129,
  132,  128,    0,    0,   89,    0,    0,    0,   84,   85,
   86,   87,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  124,    0,    0,  131,    0,
  103,   35,    0,   35,   35,    0,    0,    0,    0,   35,
    0,    0,    3,    0,    0,    0,    0,    0,    0,    0,
    0,   35,    0,    0,   35,    0,   35,    0,    0,   35,
    0,   35,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  111,  113,  114,  116,  115,    0,  112,    0,  125,
    0,  130,    0,  104,    0,    0,    0,    0,    0,    0,
   19,   37,   38,   39,    0,   40,   41,   42,   43,   44,
   45,   46,    0,   23,   27,    0,   35,    0,    0,    0,
    0,    0,   32,    0,    0,  106,    0,  126,  127,    0,
  119,    0,  117,  110,    0,  133,    0,   96,    0,    0,
    0,    0,    0,    0,   36,   16,   18,    0,   22,   35,
    0,   35,   35,   26,   31,   35,  107,    0,  121,    0,
  103,    0,    0,    0,    0,    0,    0,    0,   15,    0,
   35,    0,    0,    0,    0,    0,    0,    0,   68,    0,
    0,    0,    0,    0,   17,    0,   21,   25,   30,    0,
  108,    0,    0,    0,    0,   54,    0,    0,   14,    0,
   95,   35,   35,    0,    0,    0,    0,    0,  123,    0,
    0,    0,    0,    0,    0,    0,   52,   56,   58,   59,
   61,   60,   65,   55,   57,    0,   54,    0,    0,   49,
    0,    0,   64,   62,    0,    0,  122,    0,   48,   54,
    0,    0,   53,   35,    0,    0,    0,    0,   50,    0,
   54,    0,   54,    0,   47,    0,   51,    0,    0,   67,
   54,    0,    0,   66,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   17,   18,  192,  193,  194,   37,   58,
  151,  195,  196,  197,  198,  199,  200,  201,  202,   46,
  295,   62,  314,  315,  223,   47,   48,   40,   24,   25,
   49,   50,   51,   52,   53,  150,   79,  126,  177,  178,
  103,
};
final static short yysindex[] = {                      -239,
    0,    0, -242,    0,  -50,    0, -236, -213, -206, -125,
 -117, -112, -234,    0, -101,  -77,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -136, -100,  -94,
 -136, -136, -101,    0, -160, -136,  -58,  -78,    0,  -51,
    0, -190,    0,    0,  -66,  -45,    0,  565,  -26,  -17,
  -86,    0,    0,  -20,   -8, -111,   -1,  -98,   -2,    4,
    2,   21,   15,   29,  -69,   37, -101, -247,    0,    0,
   49,    0,    0,    0,    0,    0, -195, -195, -136, -214,
 -214, -195, -195,    0,    0, -219,   39,  -89,   40,   41,
   47,   56,   51, -136,   42, -136, -101,    0,    0,    0,
    0,    0,  -93,   48,    0,  -86,  -86,  -69,    0,    0,
    0,    0,   50, -216,   53, -141,   47,   60,   54,   64,
   61,   62,   71,   72,   42,    0,   84,   85,    0, -176,
    0,    0,   76,    0,    0,   77, -146, -150,   79,    0,
   81,   95,    0, -101,  -56,  598,   97,   42, -227,  558,
  -14,    0,  187,  198,    0,   91,    0, -115,  107,    0,
  219,    0,  108,  -35,  105, -214, -214,  110, -214,  114,
 -189,    0,    0,    0,    0,    0,  109,    0,  119,    0,
  122,    0,  -21,    0,  120,  125,  132,  134,  135, -192,
    0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
    0,    0,  230,    0,    0,  251,    0,  262,  126, -132,
  128,  283,    0,  294,  140,    0,   42,    0,    0, -136,
    0,  147,    0,    0, -101,    0,  141,    0, -136, -136,
 -136, -136, -101,   -9,    0,    0,    0,  315,    0,    0,
  142,    0,    0,    0,    0,    0,    0,  153,    0,  155,
    0,  157, -248,  164,  166,  167,  169,  170,    0,  326,
    0,  347,  358,  379,  193,   42,  571,  199,    0,  200,
  185,  161, -136, -101,    0,  390,    0,    0,    0,   42,
    0,  206,  184,  186,  196,    0,  197,  201,    0,    5,
    0,    0,    0, -101, -153,  202,  189,   42,    0,  411,
  422,  203,  217, -214,  114, -133,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  221,    0,  214,    7,    0,
  216, -136,    0,    0, -101,  -71,    0,  218,    0,    0,
  224,  227,    0,    0,  480,  245,  222,  443,    0,  223,
    0,  249,    0,  493,    0,  506,    0,   14,  238,    0,
    0,  519,  266,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  233,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  252,
    0,    0,    0,    0,    0,    0,  254,    0,    0,  255,
    0,   18,    0,    0,    0,    0,    0,    0,    0,    0,
   88,    0,    0,    0,    0,  256,    0,  257,    0,    0,
    0,    0,    0,    0,  457,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  121,  154, -105,    0,    0,
    0,    0,  259,  260,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  611,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  220,  258,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  279,    0,    0,
    0,    0,    0,  532,  545,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  398,    0,    0, -138,    6,   10,   13,    0,  -36,
 -121,    0,   -5,    0,   -4, -274, -262, -255, -226,  -29,
 -292,  -90,    0,    0,  253,  -64,  -31,    0,    0,  408,
    1,    3,  168,    0,  177,  311,    0, -116,    0,    0,
  434,
};
final static int YYTABLESIZE=880;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
   23,   59,   60,  102,   65,   26,  128,   27,  146,   39,
   19,  184,  153,  154,   20,  109,  110,   21,  161,   88,
  310,  100,   43,   44,  326,  269,  101,   61,  181,    1,
  203,  180,  311,  206,   34,  208,   28,  335,  212,  312,
  214,    4,   35,   77,   78,   45,  182,  108,  344,  113,
  346,  310,  133,   36,  121,   43,   44,  114,  352,   29,
  310,   99,   30,  311,  124,  102,  127,  134,  313,  310,
  312,  310,  311,  105,   43,   44,   34,  310,   45,  312,
  138,  311,   68,  311,  234,  238,   35,   35,  312,  311,
  312,   61,  100,   43,   44,   36,  312,   45,   36,  313,
  247,  218,  219,  303,  221,   63,   64,    8,  313,  186,
  304,  187,  188,  189,  305,  306,   45,  313,  260,  313,
  262,  263,  156,  158,  264,  313,  159,  136,  184,   11,
   12,  307,   42,   43,   44,  137,  241,  157,  165,  276,
  172,  173,  256,  234,   22,   23,  175,   31,  176,  281,
   26,  242,   27,  209,   36,   32,   45,   55,   22,   23,
   33,  210,   86,  290,   26,   87,   27,   38,   97,   19,
  300,  301,   97,   20,   55,   90,   21,   91,   56,   57,
  129,  318,  130,  288,  116,  303,  117,  118,   54,    8,
  248,  186,  304,  187,  188,  189,  305,  306,  253,  252,
   41,  254,  255,   69,   70,    6,    7,   82,   83,   36,
    8,    9,  338,  333,   10,   11,   12,   66,   13,  250,
    6,    7,   77,   78,   67,    8,    9,   61,   71,   10,
   11,   12,   80,   13,   14,  166,  167,  227,  228,  323,
   15,   81,  185,  287,  106,  107,    8,    9,  186,  216,
  187,  188,  189,   84,  190,   15,  257,  258,  111,  112,
   85,   22,   23,  298,  299,  328,  329,   26,   61,   27,
  191,   92,  349,  350,   89,   89,   15,   93,   89,   94,
   89,   89,   89,   89,   89,   89,   89,   96,  302,  308,
  309,   89,  331,   89,   95,   89,   89,   89,   89,   89,
   89,   97,   89,  109,  109,   98,  104,  115,  119,   89,
   89,   89,   89,  122,  109,   55,  109,  120,  109,  332,
  308,  309,  109,  109,  109,  125,  109,  123,  139,  308,
  309,  131,  141,  132,  142,  109,  135,  140,  308,  309,
  308,  309,  109,  144,   82,  143,  308,  309,   82,  145,
   82,   82,   82,   82,   82,   82,   82,  147,  148,  152,
  155,   82,  160,   82,  162,   82,   82,   82,   82,   82,
   82,  163,   82,  179,  207,  211,  215,   80,  217,   82,
   82,   80,  220,   80,   80,   80,   80,   80,   80,   80,
  222,  225,  229,  224,   80,  226,   80,  230,   80,   80,
   80,   80,   80,   80,  231,   80,  232,  233,  235,  240,
   81,  243,   80,   80,   81,  249,   81,   81,   81,   81,
   81,   81,   81,  246,  251,  261,  265,   81,  266,   81,
  268,   81,   81,   81,   81,   81,   81,  270,   81,  271,
  272,  273,  274,  185,  286,   81,   81,    8,    9,  186,
  280,  187,  188,  189,  185,  190,  283,  284,    8,    9,
  186,  285,  187,  188,  189,  291,  190,  292,  294,  293,
  296,  204,  317,  327,  297,  185,  321,   15,  316,    8,
    9,  186,  205,  187,  188,  189,  185,  190,   15,  322,
    8,    9,  186,  325,  187,  188,  189,  336,  190,  330,
  337,  334,  340,  213,  118,  341,  343,  185,  345,   15,
   34,    8,    9,  186,  236,  187,  188,  189,  185,  190,
   15,  351,    8,    9,  186,  354,  187,  188,  189,   33,
  190,   11,   70,   28,   29,  237,   20,   24,  109,  185,
  164,   15,  120,    8,    9,  186,  239,  187,  188,  189,
  185,  190,   15,  174,    8,    9,  186,  324,  187,  188,
  189,  267,  190,  149,    0,    0,    0,  244,    0,    0,
    0,  185,    0,   15,    0,    8,    9,  186,  245,  187,
  188,  189,  185,  190,   15,    0,    8,    9,  186,    0,
  187,  188,  189,    0,  190,    0,    0,    0,    0,  259,
    0,    0,    0,  185,    0,   15,    0,    8,    9,  186,
  275,  187,  188,  189,  185,  190,   15,    0,    8,    9,
  186,    0,  187,  188,  189,    0,  190,    0,    0,    0,
    0,  277,    0,    0,    0,  185,    0,   15,    0,    8,
    9,  186,  278,  187,  188,  189,  185,  190,   15,    0,
    8,    9,  186,    0,  187,  188,  189,    0,  190,    0,
    0,    0,    0,  279,    0,    0,    0,  185,    0,   15,
    0,    8,    9,  186,  289,  187,  188,  189,  185,  190,
   15,    0,    8,    9,  186,    0,  187,  188,  189,    0,
  190,    0,    0,    0,    0,  319,    0,    0,    0,  185,
    0,   15,    0,    8,    9,  186,  320,  187,  188,  189,
    0,  190,   15,   79,    0,    0,    0,   79,    0,   79,
   79,   79,   79,   79,   79,   79,    0,  342,    0,    0,
   79,    0,   79,   15,   79,    0,  303,    0,    0,    0,
    8,   79,  186,  304,  187,  188,  189,  305,  306,  303,
    0,    0,    0,    8,    0,  186,  304,  187,  188,  189,
  305,  306,  303,    0,  339,    0,    8,    0,  186,  304,
  187,  188,  189,  305,  306,  303,    0,  347,    0,    8,
    0,  186,  304,  187,  188,  189,  305,  306,   63,    0,
  348,    0,   63,    0,   63,   63,   63,   63,   63,   63,
   63,  120,    0,  353,    0,  120,    0,  120,  120,  120,
  120,  120,  120,  120,    7,    0,   63,    0,    8,    0,
    0,    0,   10,   11,   12,    0,  171,    7,    0,  120,
    0,    8,    0,    0,    0,   10,   11,   12,    0,  171,
    0,    0,  183,   72,   73,   74,   75,   76,    0,    0,
    0,    0,    0,    0,  168,  282,   77,   78,    8,    0,
    0,  169,   10,   11,   12,  170,  171,  109,    0,    0,
    0,  109,    0,    0,  109,  109,  109,  109,  109,  109,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   31,   32,   68,   36,    5,   97,    5,  125,   15,
    5,  150,  134,  135,    5,   80,   81,    5,  140,   56,
  295,  269,  270,  271,  317,  274,  274,   33,  256,  269,
  152,  148,  295,  155,  269,  157,  273,  330,  160,  295,
  162,  284,  277,  292,  293,  293,  274,   79,  341,  269,
  343,  326,  269,  288,   91,  270,  271,  277,  351,  273,
  335,   67,  269,  326,   94,  130,   96,  284,  295,  344,
  326,  346,  335,  269,  270,  271,  269,  352,  293,  335,
  117,  344,  273,  346,  277,  207,  277,  277,  344,  352,
  346,   97,  269,  270,  271,  288,  352,  293,  288,  326,
  217,  166,  167,  257,  169,  266,  267,  261,  335,  263,
  264,  265,  266,  267,  268,  269,  293,  344,  240,  346,
  242,  243,  269,  274,  246,  352,  277,  269,  267,  266,
  267,  285,  269,  270,  271,  277,  269,  284,  144,  261,
  146,  146,  233,  277,  150,  150,  146,  273,  146,  266,
  150,  284,  150,  269,  288,  273,  293,  269,  164,  164,
  273,  277,  274,  280,  164,  277,  164,  269,  274,  164,
  292,  293,  278,  164,  269,  274,  164,  276,  273,  274,
  274,  298,  276,  274,  274,  257,  276,  277,  289,  261,
  220,  263,  264,  265,  266,  267,  268,  269,  230,  229,
  278,  231,  232,  270,  271,  256,  257,  294,  295,  288,
  261,  262,  334,  285,  265,  266,  267,  276,  269,  225,
  256,  257,  292,  293,  276,  261,  262,  233,  274,  265,
  266,  267,  259,  269,  285,  292,  293,  259,  260,  304,
  291,  259,  257,  273,   77,   78,  261,  262,  263,  285,
  265,  266,  267,  274,  269,  291,  266,  267,   82,   83,
  269,  267,  267,  259,  260,  259,  260,  267,  274,  267,
  285,  274,  259,  260,  257,  277,  291,  274,  261,  278,
  263,  264,  265,  266,  267,  268,  269,  273,  294,  295,
  295,  274,  322,  276,  274,  278,  279,  280,  281,  282,
  283,  273,  285,  259,  260,  269,  258,  269,  269,  292,
  293,  294,  295,  258,  257,  269,  259,  277,  261,  325,
  326,  326,  265,  266,  267,  284,  269,  277,  269,  335,
  335,  284,  269,  284,  274,  278,  284,  284,  344,  344,
  346,  346,  285,  273,  257,  284,  352,  352,  261,  278,
  263,  264,  265,  266,  267,  268,  269,  274,  274,  284,
  284,  274,  284,  276,  284,  278,  279,  280,  281,  282,
  283,  277,  285,  277,  284,  269,  269,  257,  274,  292,
  293,  261,  273,  263,  264,  265,  266,  267,  268,  269,
  277,  273,  273,  285,  274,  274,  276,  273,  278,  279,
  280,  281,  282,  283,  273,  285,  273,  273,  278,  284,
  257,  284,  292,  293,  261,  269,  263,  264,  265,  266,
  267,  268,  269,  284,  284,  284,  274,  274,  274,  276,
  274,  278,  279,  280,  281,  282,  283,  274,  285,  274,
  274,  273,  273,  257,  284,  292,  293,  261,  262,  263,
  258,  265,  266,  267,  257,  269,  258,  258,  261,  262,
  263,  277,  265,  266,  267,  260,  269,  284,  273,  284,
  274,  285,  284,  260,  274,  257,  274,  291,  277,  261,
  262,  263,  285,  265,  266,  267,  257,  269,  291,  273,
  261,  262,  263,  273,  265,  266,  267,  274,  269,  284,
  274,  284,  258,  285,  285,  284,  284,  257,  260,  291,
  278,  261,  262,  263,  285,  265,  266,  267,  257,  269,
  291,  284,  261,  262,  263,  260,  265,  266,  267,  278,
  269,  278,  278,  278,  278,  285,  278,  278,  260,  257,
  143,  291,  285,  261,  262,  263,  285,  265,  266,  267,
  257,  269,  291,  146,  261,  262,  263,  305,  265,  266,
  267,  251,  269,  130,   -1,   -1,   -1,  285,   -1,   -1,
   -1,  257,   -1,  291,   -1,  261,  262,  263,  285,  265,
  266,  267,  257,  269,  291,   -1,  261,  262,  263,   -1,
  265,  266,  267,   -1,  269,   -1,   -1,   -1,   -1,  285,
   -1,   -1,   -1,  257,   -1,  291,   -1,  261,  262,  263,
  285,  265,  266,  267,  257,  269,  291,   -1,  261,  262,
  263,   -1,  265,  266,  267,   -1,  269,   -1,   -1,   -1,
   -1,  285,   -1,   -1,   -1,  257,   -1,  291,   -1,  261,
  262,  263,  285,  265,  266,  267,  257,  269,  291,   -1,
  261,  262,  263,   -1,  265,  266,  267,   -1,  269,   -1,
   -1,   -1,   -1,  285,   -1,   -1,   -1,  257,   -1,  291,
   -1,  261,  262,  263,  285,  265,  266,  267,  257,  269,
  291,   -1,  261,  262,  263,   -1,  265,  266,  267,   -1,
  269,   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,  257,
   -1,  291,   -1,  261,  262,  263,  285,  265,  266,  267,
   -1,  269,  291,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,  265,  266,  267,  268,  269,   -1,  285,   -1,   -1,
  274,   -1,  276,  291,  278,   -1,  257,   -1,   -1,   -1,
  261,  285,  263,  264,  265,  266,  267,  268,  269,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  257,   -1,  285,   -1,  261,   -1,  263,  264,
  265,  266,  267,  268,  269,  257,   -1,  285,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  257,   -1,
  285,   -1,  261,   -1,  263,  264,  265,  266,  267,  268,
  269,  257,   -1,  285,   -1,  261,   -1,  263,  264,  265,
  266,  267,  268,  269,  257,   -1,  285,   -1,  261,   -1,
   -1,   -1,  265,  266,  267,   -1,  269,  257,   -1,  285,
   -1,  261,   -1,   -1,   -1,  265,  266,  267,   -1,  269,
   -1,   -1,  285,  279,  280,  281,  282,  283,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  285,  292,  293,  261,   -1,
   -1,  264,  265,  266,  267,  268,  269,  257,   -1,   -1,
   -1,  261,   -1,   -1,  264,  265,  266,  267,  268,  269,
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
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C ID",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A",
"sentencia_decl_fun : FUN ID parametro",
"sentencia_decl_fun : FUN ID parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID",
"sentencia_decl_fun : FUN",
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
"factor : ID",
"factor : cte",
"cte : I32",
"cte : F32",
"cte : RESTA I32",
"cte : RESTA F32",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C ELSE LLAVE_A bloque_ejecutable LLAVE_C END_IF",
"sentencia_if : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_ejecutable LLAVE_C END_IF",
"condicion : expresion comparacion expresion",
"comparacion : IGUAL",
"comparacion : MAYOR",
"comparacion : MENOR",
"comparacion : MENORIGUAL",
"comparacion : MAYORIGUAL",
"bloque_ejecutable :",
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue",
"sentencia_while : ID DOSPUNTOS WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C bloque_break_continue",
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
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_break_continue",
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C bloque_break_continue",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA SUMA cte",
"encabezado_for : asignacion PUNTOCOMA condicion PUNTOCOMA RESTA cte",
"param_real : cte",
"param_real : ID",
"llamado_func : ID PARENT_A param_real COMA param_real PARENT_C",
"llamado_func : ID PARENT_A param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
"llamado_func : ID PARENT_A param_real COMA param_real error PARENT_C",
};

//#line 202 "gramatica.y"

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}
void chequearRangoI32(String sval){
  String s = "2147483648";
  long l = Long.valueOf(s);
  if(Long.valueOf(sval) >= l){
    System.out.println("La constante esta fuera de rango");
  }
}
void chequearRangoF32(String c){

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
//#line 666 "Parser.java"
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
case 7:
//#line 25 "gramatica.y"
{System.out.println("La sentencia no esta definida.");}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("Declaracion de datos");}
break;
case 14:
//#line 36 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
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
{System.out.println("Se esperaba : luego de los parametros");}
break;
case 18:
//#line 40 "gramatica.y"
{System.out.println("Se esperaba : luego de los parametros");}
break;
case 19:
//#line 41 "gramatica.y"
{System.out.println("Se esperaba : luego de los parametros");}
break;
case 20:
//#line 42 "gramatica.y"
{System.out.println("Se esperaba : luego de los parametros");}
break;
case 21:
//#line 43 "gramatica.y"
{System.out.println("Se esperaba ID luego de :");}
break;
case 22:
//#line 44 "gramatica.y"
{System.out.println("Se esperaba ID luego de :");}
break;
case 23:
//#line 45 "gramatica.y"
{System.out.println("Se esperaba ID luego de :");}
break;
case 24:
//#line 46 "gramatica.y"
{System.out.println("Se esperaba ID luego de :");}
break;
case 25:
//#line 47 "gramatica.y"
{System.out.println("Falta ) luego de los parametros");}
break;
case 26:
//#line 48 "gramatica.y"
{System.out.println("Falta ) luego de los parametros");}
break;
case 27:
//#line 49 "gramatica.y"
{System.out.println("Falta ) luego de los parametros");}
break;
case 28:
//#line 50 "gramatica.y"
{System.out.println("Falta ) luego de los parametros");}
break;
case 29:
//#line 51 "gramatica.y"
{System.out.println("Falta parentesis en la declaracion de la funcion.");}
break;
case 30:
//#line 52 "gramatica.y"
{System.out.println("Falta { luego del nombre de funcion");}
break;
case 31:
//#line 53 "gramatica.y"
{System.out.println("Falta { luego del nombre de funcion");}
break;
case 32:
//#line 54 "gramatica.y"
{System.out.println("Falta { luego del nombre de funcion");}
break;
case 33:
//#line 55 "gramatica.y"
{System.out.println("Falta ( luego del nombre de funcion");}
break;
case 34:
//#line 56 "gramatica.y"
{System.out.println("La funcion esta mal declarada");}
break;
case 47:
//#line 72 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 48:
//#line 73 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 49:
//#line 75 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 50:
//#line 77 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 51:
//#line 78 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 52:
//#line 80 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 53:
//#line 81 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 66:
//#line 98 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 67:
//#line 99 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 70:
//#line 106 "gramatica.y"
{System.out.println("Declaracion de Constante/s");}
break;
case 79:
//#line 118 "gramatica.y"
{System.out.println("Asignacion");}
break;
case 91:
//#line 135 "gramatica.y"
{   System.out.println("Valor de I32: " + val_peek(0).sval );
                        chequearRangoI32(val_peek(0).sval);
                }
break;
case 95:
//#line 144 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 96:
//#line 145 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 105:
//#line 158 "gramatica.y"
{System.out.println("Sentencia OUT");}
break;
case 106:
//#line 160 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 107:
//#line 162 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 108:
//#line 163 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 122:
//#line 183 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 123:
//#line 184 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 124:
//#line 186 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 125:
//#line 187 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 133:
//#line 199 "gramatica.y"
{System.out.println("Una funcion no puede tener mas de dos parametros");}
break;
//#line 1001 "Parser.java"
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
