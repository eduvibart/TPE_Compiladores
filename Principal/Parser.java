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
public final static short ENTERO=296;
public final static short FLOAT=297;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    2,    2,    2,    3,    3,    4,    4,
    4,    9,    9,    6,    6,   10,   10,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,   12,   12,
   12,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   18,   18,   18,   18,   18,   18,
   18,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   19,   19,   19,   19,   19,   19,   23,   23,
   23,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   21,   11,   11,    8,   30,   30,    5,
    5,    5,    5,    5,    5,    5,   14,   29,   29,   29,
   29,   29,   29,   35,   35,   35,   36,   36,   28,   28,
   28,   28,   31,   31,   31,   31,   31,   31,   31,   31,
   31,   31,   31,   31,   22,   22,   22,   38,   38,   38,
   38,   38,   37,   37,   37,   17,   17,   17,   17,   32,
   32,   32,   32,   32,   32,   32,   32,   34,   34,   34,
   34,   34,   34,   34,   34,   34,   34,   34,   39,   39,
   39,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   27,   27,   41,   41,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   24,   24,   42,   42,   15,   15,
   15,   15,   15,   15,
};
final static short yylen[] = {                            2,
    4,    1,    1,    0,    3,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    2,    3,    1,   12,   10,    9,
   12,   10,    9,    8,    7,    5,    3,    2,    0,    3,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,   13,    9,   13,   12,   10,    9,    8,    6,
    5,    4,    3,    2,    8,    8,    6,    5,    4,    3,
    8,   13,   11,   11,    9,    8,    7,    6,    5,    4,
    3,    2,    9,    7,    7,    5,    4,    2,    0,    3,
    3,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    1,   13,    9,   13,   12,   10,    9,    8,    6,    5,
    4,    3,    2,    4,    2,    2,    2,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    3,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    2,    2,   13,    9,   13,   12,   10,    9,    8,    6,
    5,    4,    3,    2,    3,    3,    3,    1,    1,    1,
    1,    1,    0,    3,    2,    4,    4,    3,    2,    8,
    8,    8,    8,    8,    8,    7,    5,   13,   11,   11,
    9,    8,    7,    6,    5,    4,    3,    2,    0,    3,
    2,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    0,    2,   13,    9,    9,    9,    7,    6,    3,    7,
    7,    5,    4,    2,    6,    6,    1,    1,    6,    4,
    3,    6,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    4,    0,    0,    0,    0,    0,
    0,    0,    0,   12,   13,    1,    0,    0,    7,    8,
    9,   10,   11,    0,  110,  116,  112,  111,  113,  114,
  115,  144,    0,  159,    0,   28,    0,    0,    0,  178,
    0,  204,    0,   17,    0,    0,    0,    0,    0,  109,
    0,    5,    0,  143,    0,    0,  129,  130,  121,    0,
  128,    0,    0,    0,    0,  126,  158,    0,   27,    0,
    0,    0,    0,  177,    0,    0,    0,  214,  208,  211,
  207,    0,  199,    0,    0,    0,    0,    0,  131,  132,
  142,    0,    0,  148,  149,  150,  151,  152,    0,    0,
    0,    0,    0,    0,    0,  157,  156,    0,    0,    0,
    0,    0,    0,    0,    0,  176,    0,    0,  203,    0,
  213,  210,    0,    0,    0,   16,  108,  141,    0,    0,
  127,    0,    0,  146,    0,  122,  123,  124,  125,  106,
    0,  105,   26,    0,    0,    0,    0,    4,    0,    0,
  175,    0,    0,  202,  179,    0,    0,    0,  140,  153,
    0,    0,    0,    4,    4,    0,    4,    4,    4,  174,
    0,    0,    0,  212,  209,    0,  198,    0,    0,   29,
    0,   25,    0,    0,    0,  166,    0,    0,    0,  173,
    0,    0,    0,  201,    0,    0,    0,    0,  200,  182,
  184,  185,  187,  186,    0,  183,    0,  197,  179,  139,
    0,    0,    0,    0,   29,   24,    0,  165,  164,  163,
  162,  161,  160,  172,    0,  205,  206,    0,  190,    0,
  188,  180,    0,    0,  138,    0,  134,  154,    0,    0,
    0,    0,    0,    0,   20,   32,   33,   34,    0,   35,
   36,   37,   38,   39,   40,   41,   42,    0,   23,    0,
  171,  179,    0,  192,    0,  196,  195,  137,  153,   54,
    0,    0,    0,    0,   72,    0,   78,    0,    0,   30,
   19,   22,   29,    0,    0,    0,    0,   53,    0,    0,
    0,   60,    0,   71,    0,    0,    0,    0,    0,  170,
  169,    0,  179,  136,    0,   52,    0,  104,    0,   59,
    0,   70,    0,   77,    0,    0,    0,   21,   18,  179,
    0,  135,  133,   51,    0,    0,   58,    0,   69,    0,
   76,   79,    0,    0,    0,  168,   50,   29,   29,   57,
   29,   68,    0,    0,    0,    0,    0,    0,    0,    0,
   67,    0,   75,    0,    0,    0,    0,   74,   82,   84,
   85,   87,   86,   91,    0,   83,    0,   79,    0,  194,
   49,    0,   61,   56,   55,   66,    0,  103,    0,   90,
   88,   81,   80,    0,    0,  179,   48,    0,   44,   65,
   79,  102,    0,    0,   73,    0,   47,   29,    0,  101,
    0,    0,    0,    0,   64,   63,  100,    0,   79,  193,
   46,    0,   99,   79,    0,   45,   43,    0,   62,   98,
    0,   97,    0,   93,   96,   79,    0,   95,    0,   94,
   92,
};
final static short yydgoto[] = {                          3,
    4,    6,   18,   19,   20,   21,   22,   23,   24,   48,
  111,  214,  249,   25,   59,  252,   27,  254,  255,  256,
  257,   60,  344,   77,  365,  366,  231,   61,   62,   51,
   28,   29,   63,   64,   65,   66,  179,  101,  173,  205,
  206,   82,
};
final static short yysindex[] = {                        51,
    0,    0,    0, -256,    0,   32, -238,  -70,  149,    7,
   40,  118,   52,    0,    0,    0, -236, -212,    0,    0,
    0,    0,    0, -162,    0,    0,    0,    0,    0,    0,
    0,    0, -220,    0, -232,    0,  185, -167,  -31,    0,
   12,    0, -236,    0, -242,  276, -167, -191, -193,    0,
 -149,    0, -191,    0,  -68,  259,    0,    0,    0, -237,
    0,   79,  -96,  -60,  286,    0,    0, -173,    0,  364,
  -15,   38, -177,    0, -106,    8,  -16,    0,    0,    0,
    0,   60,    0,   42,  155,  301,  213, -236,    0,    0,
    0,   -9, -167,    0,    0,    0,    0,    0, -152, -152,
   97,  274,  274, -152, -152,    0,    0,  220,  215,  251,
   74,  271,  273,  312,   -6,    0, -248, -167,    0, -240,
    0,    0,  -40, -167, -236,    0,    0,    0, -234,  301,
    0,  286,  286,    0,  301,    0,    0,    0,    0,    0,
  356,    0,    0,  256,  388,  314,  323,    0,  345, -222,
    0,  191,  280,    0,    0,   17,  357,   83,    0,    0,
  378,  356,   99,    0,    0,  701,    0,    0,    0,    0,
  224,  344,  911,    0,    0,  371,    0, -217, -145,    0,
  387,    0, -184,  712,  739,    0,  750,  777,  598,    0,
  121,  274,  274,    0,  287,  274,  395, -246,    0,    0,
    0,    0,    0,    0,  402,    0,  413,    0,    0,    0,
  167,  313,  409,  636,    0,    0,   14,    0,    0,    0,
    0,    0,    0,    0, -216,    0,    0, -167,    0,  419,
    0,    0, -236,  926,    0, -178,    0,    0,  192,  416,
  221,  222,  249,  169,    0,    0,    0,    0,  414,    0,
    0,    0,    0,    0,    0,    0,    0,  663,    0, -151,
    0,    0,  417,    0,  420,    0,    0,    0,    0,    0,
  119, -167, -167,  140,    0,  157,    0, -236,  394,    0,
    0,    0,    0,  941,  435,  415,  971,    0,  128,  -80,
  424,    0,  147,    0,  148,  161,  434,  438,  493,    0,
    0,  428,    0,    0,   50,    0,   96,    0,  455,    0,
  125,    0,  -38,    0, -137, -167, -236,    0,    0,    0,
 1021,    0,    0,    0, -114,  430,    0, -113,    0,  261,
    0,    0,  441,  442, 1034,    0,    0,    0,    0,    0,
    0,    0,  235,  808,  443,  437,  405,  510,  674,  537,
    0,  171,    0,  262,  274,  395,  137,    0,    0,    0,
    0,    0,    0,    0, -233,    0,  445,    0,  450,    0,
    0,  349,    0,    0,    0,    0,  -63,    0,  163,    0,
    0,    0,    0, -236,  985,    0,    0,  -54,    0,    0,
    0,    0,  178,  451,    0, 1047,    0,    0,  822,    0,
  193,  454,  459,  554,    0,    0,    0,  -52,    0,    0,
    0,  120,    0,    0, 1008,    0,    0,  845,    0,    0,
  384,    0,  -50,    0,    0,    0,  859,    0,  183,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  625,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  412,    0,    0,
  439,    0,  466,    0,  205,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  245,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  354,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -14,
    0,  283,  321,    0,   91,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  385,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  881,  896,    0,    0,    0,
    0,    0,    0,    0,  956,    0,    0,    0,    0,    0,
    0,    0,  978,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  581,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -125,  -67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  107,    0,    0, -174, -189, -176, -171,  -53,  702,
  594, -203,    0,  -17,   -4,    0,   -7, -329, -324, -281,
 -260,  -28, -361, -121,    0,    0,  386,  -44,  -41,    0,
    0, -170,    3,   35,  567,  580,  471,    0, -188,    0,
    0,  618,
};
final static int YYTABLESIZE=1332;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   81,   26,  202,  158,  213,   86,  385,  151,   30,   71,
   73,  258,   75,   78,  361,  154,  110,   32,   91,  362,
  234,  159,  382,   67,  246,   76,   79,    5,  152,  399,
   46,   80,   49,  168,   33,   54,   92,  247,  208,  261,
   31,   47,  248,  155,  383,   11,   12,  415,   55,  160,
   56,  130,  418,   57,   58,  361,   68,  136,  137,  135,
  362,  169,  363,  202,  427,   52,  209,  262,  246,  361,
  127,  216,   56,  284,  362,   57,   58,  268,   81,  299,
  114,  247,  106,  364,   87,  361,  248,  161,  361,  153,
  362,  110,  217,  362,   47,  157,  115,  361,   11,   12,
  107,   55,  362,  363,  282,  269,   44,   76,  181,  246,
  210,    7,  213,  202,  321,    8,  131,  363,  331,   10,
   11,   12,  247,  211,  364,   56,   88,  248,   57,   58,
   89,  335,  283,  363,  348,  349,  363,  350,  364,  212,
   56,  337,  340,   57,   58,  363,  332,  226,  227,  116,
  202,  229,   89,  191,  364,  200,  296,  364,  246,  246,
  246,   26,  102,  260,  202,  201,  364,  117,   30,  338,
  341,  247,  247,  247,   26,  203,  248,  248,  248,   26,
   26,   30,   26,   26,   26,   34,   30,   30,  191,   30,
   30,   30,  390,  308,  404,  334,  250,  396,  103,  263,
   31,  397,   35,  413,   45,  425,  253,  204,   46,  251,
  191,   99,  100,   31,  246,  265,  200,  329,   31,   31,
  391,   31,   31,   31,   72,  202,  201,  247,   79,  398,
  290,  414,  248,  426,   11,   12,  203,   55,  330,  119,
  250,  147,  289,  147,  291,  293,  128,  295,  129,  149,
  253,  150,   56,  251,  166,   57,   58,  120,  112,  147,
   76,   56,   38,  147,   57,   58,  200,   74,  204,  259,
  184,  185,  174,  187,  188,  189,  201,   11,   12,   39,
   55,  250,   26,   14,   15,  118,  203,  333,    7,   30,
  175,  253,    8,    9,  251,   40,   10,   11,   12,   76,
   13,   14,   15,  200,   56,  322,    1,   57,   58,  323,
  380,  113,   41,  201,  124,  121,   16,  200,  204,    2,
   44,   31,   17,  203,   45,  352,  359,  201,   46,  143,
  250,  250,  250,  122,   93,  123,  360,  203,  177,   47,
  253,  253,  253,  251,  251,  251,  145,  144,  145,  145,
  393,  324,  134,  325,  182,  204,  178,   94,   95,   96,
   97,   98,   11,   12,  145,   55,  394,  359,  145,  204,
   99,  100,  183,   42,  288,  416,  224,  360,  200,  417,
  327,  359,  328,  306,   11,   12,  250,   55,  201,   56,
   43,  360,   57,   58,  225,  292,  253,  359,  203,  251,
  359,  307,  310,  312,   36,   11,   12,  360,   55,  359,
  360,   56,  294,  279,   57,   58,  314,   37,  392,  360,
  311,  313,   11,   12,   47,   55,  376,  125,   11,   12,
  204,   55,   56,  400,  315,   57,   58,   44,  430,   45,
   69,   45,  431,   46,  377,  279,  170,  270,  407,   56,
  408,  401,   57,   58,   47,   56,   47,   70,   57,   58,
  127,  127,  127,  171,  271,  127,  127,  127,  127,  127,
  127,  127,  127,  127,  127,  127,  273,  275,  127,  190,
  127,  126,  127,  127,  127,  127,  127,  127,  140,  127,
  351,  141,   49,  274,  276,  127,  127,  127,  127,  127,
  120,  120,  120,   49,  277,  120,  120,  120,  120,  120,
  120,  120,  120,  120,  120,  120,  342,  378,  120,  142,
  120,  278,  120,  120,  120,  120,  120,  120,  146,  120,
  147,   83,  162,  343,  379,  120,  120,  120,  118,  118,
  118,   84,   85,  118,  118,  118,  118,  118,  118,  118,
  118,  118,  118,  118,   89,   90,  118,  172,  118,  228,
  118,  118,  118,  118,  118,  118,   56,  118,  235,   57,
   58,  236,  237,  118,  118,  118,  119,  119,  119,  104,
  105,  119,  119,  119,  119,  119,  119,  119,  119,  119,
  119,  119,   99,  100,  119,  148,  119,  164,  119,  119,
  119,  119,  119,  119,  387,  119,  165,  388,  389,  117,
  117,  119,  119,  119,  117,  117,  117,  117,  117,  117,
  117,  117,  117,  117,  117,   14,   15,  117,  167,  117,
  176,  117,  108,   14,   15,  192,  193,  109,  117,  422,
  167,  167,  423,  424,  117,  167,  167,  207,  167,  167,
  167,  167,  167,  167,  167,  167,  108,   14,   15,  297,
  298,  180,  167,  369,  370,  132,  133,   15,   15,  167,
  215,  230,   15,   15,   15,  167,   15,   15,   15,  232,
   15,   15,   15,  138,  139,  233,  238,  264,  272,   15,
  285,  280,  302,  286,  107,  107,   15,  309,  303,  107,
  107,  107,   15,  107,  107,  107,  316,  107,  107,  107,
  317,  320,  326,  339,  345,  346,  107,  384,  410,  367,
  368,   14,   14,  107,  402,   53,   14,   14,   14,  107,
   14,   14,   14,  386,   14,   14,   14,  409,  163,  287,
  156,  381,    0,   14,    0,    0,    0,    0,  318,  239,
   14,    0,    0,    8,    9,  240,   14,  241,  242,  243,
    0,  244,   14,   15,    0,  371,  239,    0,    0,    0,
    8,    9,  240,    0,  241,  242,  243,  319,  244,   14,
   15,    0,    0,   17,    0,    0,    0,    0,    0,    0,
    0,    0,  374,  239,  372,    0,    0,    8,    9,  240,
   17,  241,  242,  243,    0,  244,   14,   15,    0,  411,
  239,    0,    0,    0,    8,    9,  240,    0,  241,  242,
  243,  375,  244,   14,   15,    0,    0,   17,    0,    0,
    0,    0,    0,    0,    0,    0,   31,   31,  412,    0,
    0,   31,   31,   31,   17,   31,   31,   31,    0,   31,
   31,   31,    0,  222,    7,    0,    0,    0,    8,    9,
    0,    0,   10,   11,   12,   31,   13,   14,   15,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    6,  223,    0,    0,    6,    6,    0,   17,    6,
    6,    6,  239,    6,    6,    6,    8,    9,  240,    0,
  241,  242,  243,    0,  244,   14,   15,    0,    0,    6,
    0,    0,    0,    0,    0,    6,    0,    0,    0,  239,
  245,    0,    0,    8,    9,  240,   17,  241,  242,  243,
  239,  244,   14,   15,    8,    9,  240,    0,  241,  242,
  243,    0,  244,   14,   15,    0,    0,  281,    0,    0,
    0,    0,    0,   17,    0,    0,    0,    7,  373,    0,
    0,    8,    9,    0,   17,   10,   11,   12,    7,   13,
   14,   15,    8,    9,    0,    0,   10,   11,   12,    0,
   13,   14,   15,    0,    0,  186,    0,    0,    0,    0,
    0,   17,    0,    0,    0,    7,  218,    0,    0,    8,
    9,    0,   17,   10,   11,   12,    7,   13,   14,   15,
    8,    9,    0,    0,   10,   11,   12,    0,   13,   14,
   15,    0,    0,  219,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    7,  220,    0,    0,    8,    9,    0,
   17,   10,   11,   12,    0,   13,   14,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  221,    0,  353,  354,    0,    0,   17,    8,    0,
  240,  355,  241,  242,  243,  356,  357,  405,  354,    0,
    0,    0,    8,    0,  240,  355,  241,  242,  243,  356,
  357,    0,  358,    0,    0,    0,    0,    0,    0,    0,
  420,  354,    0,    0,    0,    8,  406,  240,  355,  241,
  242,  243,  356,  357,  428,  354,    0,    0,    0,    8,
    0,  240,  355,  241,  242,  243,  356,  357,    0,  421,
    0,    0,    0,    0,    0,    0,  189,  189,    0,    0,
    0,  189,    0,  429,  189,  189,  189,  189,  189,  189,
    0,  191,  191,    0,    0,    0,  191,    0,  189,  191,
  191,  191,  191,  191,  191,  189,  194,  195,    0,    0,
    0,    8,    0,  191,  196,   10,   11,   12,  197,  198,
  191,  266,  195,    0,    0,    0,    8,    0,    0,  196,
   10,   11,   12,  197,  198,  199,  300,  195,    0,    0,
    0,    8,    0,    0,  196,   10,   11,   12,  197,  198,
  267,  181,  181,    0,    0,    0,  181,    0,    0,  181,
  181,  181,  181,  181,  181,  301,  304,    7,    0,    0,
    0,    8,    0,  155,  155,   10,   11,   12,  155,  211,
  181,  354,  155,  155,  155,    8,  155,  240,  355,  241,
  242,  243,  356,  357,    0,  305,    0,    0,    0,    0,
    0,    0,  155,    0,  354,    0,    0,    0,    8,  395,
  240,  355,  241,  242,  243,  356,  357,  195,    0,    0,
    0,    8,    0,    0,  196,   10,   11,   12,  197,  198,
  195,    0,  419,    0,    8,    0,    0,  196,   10,   11,
   12,  197,  198,  195,    0,  336,    0,    8,    0,    0,
  196,   10,   11,   12,  197,  198,    0,    0,  347,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  403,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         17,
   45,    6,  173,  125,  179,   47,  368,  256,    6,   38,
   39,  215,   41,  256,  344,  256,   70,  256,  256,  344,
  209,  256,  256,  256,  214,   43,  269,  284,  277,  391,
  277,  274,  269,  256,  273,  256,  274,  214,  256,  256,
    6,  288,  214,  284,  278,  266,  267,  409,  269,  284,
  293,   93,  414,  296,  297,  385,  289,  102,  103,  101,
  385,  284,  344,  234,  426,  278,  284,  284,  258,  399,
   88,  256,  293,  262,  399,  296,  297,  256,  123,  283,
  258,  258,  256,  344,  276,  415,  258,  141,  418,  118,
  415,  145,  277,  418,  288,  124,  274,  427,  266,  267,
  274,  269,  427,  385,  256,  284,  269,  125,  162,  299,
  256,  257,  287,  284,  303,  261,  269,  399,  256,  265,
  266,  267,  299,  269,  385,  293,  276,  299,  296,  297,
  256,  320,  284,  415,  338,  339,  418,  341,  399,  285,
  293,  256,  256,  296,  297,  427,  284,  192,  193,  256,
  321,  196,  278,  171,  415,  173,  278,  418,  348,  349,
  350,  166,  259,  217,  335,  173,  427,  274,  166,  284,
  284,  348,  349,  350,  179,  173,  348,  349,  350,  184,
  185,  179,  187,  188,  189,  256,  184,  185,  256,  187,
  188,  189,  256,  274,  398,  317,  214,  386,  259,  228,
  166,  256,  273,  256,  273,  256,  214,  173,  277,  214,
  278,  292,  293,  179,  404,  233,  234,  256,  184,  185,
  284,  187,  188,  189,  256,  396,  234,  404,  269,  284,
  272,  284,  404,  284,  266,  267,  234,  269,  277,  256,
  258,  256,  271,  258,  273,  274,  256,  276,  258,  256,
  258,  258,  293,  258,  148,  296,  297,  274,  274,  274,
  278,  293,  256,  278,  296,  297,  284,  256,  234,  256,
  164,  165,  256,  167,  168,  169,  284,  266,  267,  273,
  269,  299,  287,  270,  271,  278,  284,  316,  257,  287,
  274,  299,  261,  262,  299,  256,  265,  266,  267,  317,
  269,  270,  271,  321,  293,  256,  256,  296,  297,  260,
  355,  274,  273,  321,  273,  256,  285,  335,  284,  269,
  269,  287,  291,  321,  273,  343,  344,  335,  277,  256,
  348,  349,  350,  274,  256,  276,  344,  335,  256,  288,
  348,  349,  350,  348,  349,  350,  256,  274,  258,  276,
  379,  256,  256,  258,  256,  321,  274,  279,  280,  281,
  282,  283,  266,  267,  274,  269,  384,  385,  278,  335,
  292,  293,  274,  256,  256,  256,  256,  385,  396,  260,
  256,  399,  258,  256,  266,  267,  404,  269,  396,  293,
  273,  399,  296,  297,  274,  256,  404,  415,  396,  404,
  418,  274,  256,  256,  256,  266,  267,  415,  269,  427,
  418,  293,  256,  277,  296,  297,  256,  269,  256,  427,
  274,  274,  266,  267,  288,  269,  256,  273,  266,  267,
  396,  269,  293,  256,  274,  296,  297,  269,  256,  273,
  256,  273,  260,  277,  274,  277,  256,  256,  256,  293,
  258,  274,  296,  297,  288,  293,  288,  273,  296,  297,
  256,  257,  258,  273,  273,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  256,  256,  274,  256,
  276,  269,  278,  279,  280,  281,  282,  283,  269,  285,
  256,  277,  269,  273,  273,  291,  292,  293,  294,  295,
  256,  257,  258,  269,  256,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  256,  256,  274,  269,
  276,  273,  278,  279,  280,  281,  282,  283,  258,  285,
  258,  256,  277,  273,  273,  291,  292,  293,  256,  257,
  258,  266,  267,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  296,  297,  274,  278,  276,  273,
  278,  279,  280,  281,  282,  283,  293,  285,  256,  296,
  297,  259,  260,  291,  292,  293,  256,  257,  258,  294,
  295,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  292,  293,  274,  284,  276,  284,  278,  279,
  280,  281,  282,  283,  256,  285,  284,  259,  260,  256,
  257,  291,  292,  293,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  270,  271,  274,  284,  276,
  274,  278,  269,  270,  271,  292,  293,  274,  285,  256,
  256,  257,  259,  260,  291,  261,  262,  277,  264,  265,
  266,  267,  268,  269,  270,  271,  269,  270,  271,  266,
  267,  284,  278,  259,  260,   99,  100,  256,  257,  285,
  284,  277,  261,  262,  263,  291,  265,  266,  267,  278,
  269,  270,  271,  104,  105,  273,  278,  269,  273,  278,
  274,  278,  258,  274,  256,  257,  285,  274,  284,  261,
  262,  263,  291,  265,  266,  267,  273,  269,  270,  271,
  273,  284,  258,  284,  274,  274,  278,  273,  260,  277,
  284,  256,  257,  285,  274,   24,  261,  262,  263,  291,
  265,  266,  267,  284,  269,  270,  271,  284,  145,  269,
  123,  356,   -1,  278,   -1,   -1,   -1,   -1,  256,  257,
  285,   -1,   -1,  261,  262,  263,  291,  265,  266,  267,
   -1,  269,  270,  271,   -1,  256,  257,   -1,   -1,   -1,
  261,  262,  263,   -1,  265,  266,  267,  285,  269,  270,
  271,   -1,   -1,  291,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,  285,   -1,   -1,  261,  262,  263,
  291,  265,  266,  267,   -1,  269,  270,  271,   -1,  256,
  257,   -1,   -1,   -1,  261,  262,  263,   -1,  265,  266,
  267,  285,  269,  270,  271,   -1,   -1,  291,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,  285,   -1,
   -1,  261,  262,  263,  291,  265,  266,  267,   -1,  269,
  270,  271,   -1,  256,  257,   -1,   -1,   -1,  261,  262,
   -1,   -1,  265,  266,  267,  285,  269,  270,  271,   -1,
   -1,  291,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,  257,  269,  270,  271,  261,  262,  263,   -1,
  265,  266,  267,   -1,  269,  270,  271,   -1,   -1,  285,
   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,   -1,  257,
  285,   -1,   -1,  261,  262,  263,  291,  265,  266,  267,
  257,  269,  270,  271,  261,  262,  263,   -1,  265,  266,
  267,   -1,  269,  270,  271,   -1,   -1,  285,   -1,   -1,
   -1,   -1,   -1,  291,   -1,   -1,   -1,  257,  285,   -1,
   -1,  261,  262,   -1,  291,  265,  266,  267,  257,  269,
  270,  271,  261,  262,   -1,   -1,  265,  266,  267,   -1,
  269,  270,  271,   -1,   -1,  285,   -1,   -1,   -1,   -1,
   -1,  291,   -1,   -1,   -1,  257,  285,   -1,   -1,  261,
  262,   -1,  291,  265,  266,  267,  257,  269,  270,  271,
  261,  262,   -1,   -1,  265,  266,  267,   -1,  269,  270,
  271,   -1,   -1,  285,   -1,   -1,   -1,   -1,   -1,  291,
   -1,   -1,   -1,  257,  285,   -1,   -1,  261,  262,   -1,
  291,  265,  266,  267,   -1,  269,  270,  271,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  285,   -1,  256,  257,   -1,   -1,  291,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,  256,  257,   -1,
   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,  268,
  269,   -1,  285,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,   -1,   -1,   -1,  261,  285,  263,  264,  265,
  266,  267,  268,  269,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,   -1,  285,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,
   -1,  261,   -1,  285,  264,  265,  266,  267,  268,  269,
   -1,  256,  257,   -1,   -1,   -1,  261,   -1,  278,  264,
  265,  266,  267,  268,  269,  285,  256,  257,   -1,   -1,
   -1,  261,   -1,  278,  264,  265,  266,  267,  268,  269,
  285,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,  264,
  265,  266,  267,  268,  269,  285,  256,  257,   -1,   -1,
   -1,  261,   -1,   -1,  264,  265,  266,  267,  268,  269,
  285,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,  264,
  265,  266,  267,  268,  269,  285,  256,  257,   -1,   -1,
   -1,  261,   -1,  256,  257,  265,  266,  267,  261,  269,
  285,  257,  265,  266,  267,  261,  269,  263,  264,  265,
  266,  267,  268,  269,   -1,  285,   -1,   -1,   -1,   -1,
   -1,   -1,  285,   -1,  257,   -1,   -1,   -1,  261,  285,
  263,  264,  265,  266,  267,  268,  269,  257,   -1,   -1,
   -1,  261,   -1,   -1,  264,  265,  266,  267,  268,  269,
  257,   -1,  285,   -1,  261,   -1,   -1,  264,  265,  266,
  267,  268,  269,  257,   -1,  285,   -1,  261,   -1,   -1,
  264,  265,  266,  267,  268,  269,   -1,   -1,  285,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  285,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=297;
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
"SUMA","RESTA","MULT","DIV","ENTERO","FLOAT",
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
"tipo : I32",
"tipo : F32",
"sentencia_decl_datos : tipo list_var",
"sentencia_decl_datos : ID list_var",
"list_var : list_var COMA ID",
"list_var : ID",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo LLAVE_A cuerpo_fun error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS tipo error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C error",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro error",
"sentencia_decl_fun : FUN ID PARENT_A parametro error",
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
"cuerpo_fun_break : cuerpo_fun_break sentencias_fun_break PUNTOCOMA",
"cuerpo_fun_break : cuerpo_fun_break sentencias_fun_break error",
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
"parametro : tipo ID",
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
"cte : ENTERO",
"cte : FLOAT",
"cte : RESTA ENTERO",
"cte : RESTA FLOAT",
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
"condicion : expresion comparacion error",
"condicion : expresion error expresion",
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
"bloque_break_continue : bloque_break_continue ejecutables_break_continue PUNTOCOMA",
"bloque_break_continue : bloque_break_continue ejecutables_break_continue",
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
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C ELSE LLAVE_A bloque_break_continue LLAVE_C END_IF",
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN LLAVE_A bloque_break_continue LLAVE_C END_IF",
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue error",
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for PARENT_C error",
"sentencia_for : ID DOSPUNTOS FOR PARENT_A encabezado_for error",
"sentencia_for : ID DOSPUNTOS error",
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

//#line 282 "gramatica.y"

void yyerror(String mensaje){
        System.out.println("Linea"+ AnalizadorLexico.getLineaAct() +"| Error sintactico: " + mensaje);
}
void chequearRangoI32(String sval){
  String s = "2147483647";
  long l = Long.valueOf(s);
  if(Long.valueOf(sval) > l){
    yyerror("La constante esta fuera de rango");
  }
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        this.yylval = new ParserVal(t.getLexema());
        //if(t.getId() != -1){
        //  System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        //}else
        //  System.out.println("TERMINO LA EJECUCION");
        return t.getId();
}
//#line 879 "Parser.java"
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
{yyerror("Hay un error sintactico en la entrada que arrastra errores");}
break;
case 6:
//#line 23 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 14:
//#line 35 "gramatica.y"
{System.out.println("Declaracion de datos");}
break;
case 15:
//#line 36 "gramatica.y"
{yyerror("No esta permitido el tipo declarado");}
break;
case 18:
//#line 41 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 19:
//#line 42 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 20:
//#line 43 "gramatica.y"
{System.out.println("Declaracion de Funcion");}
break;
case 21:
//#line 44 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 22:
//#line 45 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 23:
//#line 46 "gramatica.y"
{yyerror("El tipo declarado no esta permitido");}
break;
case 24:
//#line 47 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 25:
//#line 48 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 26:
//#line 49 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 27:
//#line 50 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 28:
//#line 51 "gramatica.y"
{yyerror("Se esperaba un nombre de funcion");}
break;
case 31:
//#line 55 "gramatica.y"
{System.out.println("Se esperaba ;");}
break;
case 43:
//#line 69 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 44:
//#line 70 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 45:
//#line 71 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 46:
//#line 72 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 47:
//#line 73 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 48:
//#line 74 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 49:
//#line 75 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 50:
//#line 76 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 51:
//#line 77 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 52:
//#line 78 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 53:
//#line 79 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 54:
//#line 80 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 55:
//#line 82 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 56:
//#line 83 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 57:
//#line 84 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 58:
//#line 85 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 59:
//#line 86 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 60:
//#line 87 "gramatica.y"
{yyerror("Se esperaba condicion");}
break;
case 61:
//#line 88 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 62:
//#line 90 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 63:
//#line 91 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 64:
//#line 92 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 65:
//#line 93 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 66:
//#line 94 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 67:
//#line 95 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 68:
//#line 96 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 69:
//#line 97 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 70:
//#line 98 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 71:
//#line 99 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 72:
//#line 100 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 73:
//#line 102 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 74:
//#line 103 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 75:
//#line 104 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 76:
//#line 105 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 77:
//#line 106 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 78:
//#line 107 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 81:
//#line 111 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 92:
//#line 125 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 93:
//#line 126 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 94:
//#line 127 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 95:
//#line 128 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 96:
//#line 129 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 97:
//#line 130 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 98:
//#line 131 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 99:
//#line 132 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 100:
//#line 133 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 101:
//#line 134 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 102:
//#line 135 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 103:
//#line 136 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 106:
//#line 141 "gramatica.y"
{yyerror("No esta permitido el tipo declarado");}
break;
case 107:
//#line 144 "gramatica.y"
{System.out.println("Declaracion de Constante/s");}
break;
case 117:
//#line 157 "gramatica.y"
{System.out.println("Asignacion");}
break;
case 129:
//#line 174 "gramatica.y"
{  chequearRangoI32(val_peek(0).sval);}
break;
case 133:
//#line 181 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 134:
//#line 182 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 135:
//#line 183 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 136:
//#line 184 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 137:
//#line 185 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 138:
//#line 186 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 139:
//#line 187 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 140:
//#line 188 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 141:
//#line 189 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 142:
//#line 190 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 143:
//#line 191 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 144:
//#line 192 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 146:
//#line 195 "gramatica.y"
{yyerror("Se esperaba otra expresion para comparar.");}
break;
case 147:
//#line 196 "gramatica.y"
{yyerror("Se esperaba un tipo de comparacion.");}
break;
case 155:
//#line 206 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 156:
//#line 208 "gramatica.y"
{System.out.println("Sentencia OUT");}
break;
case 157:
//#line 209 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 158:
//#line 210 "gramatica.y"
{yyerror("Se esperaba una CADENA");}
break;
case 159:
//#line 211 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 160:
//#line 213 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 161:
//#line 214 "gramatica.y"
{yyerror("Se esperaba } en el when");}
break;
case 162:
//#line 215 "gramatica.y"
{yyerror("Se esperaba { en el when");}
break;
case 163:
//#line 216 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 164:
//#line 217 "gramatica.y"
{yyerror("Se esperaba condicion en el when");}
break;
case 165:
//#line 218 "gramatica.y"
{yyerror("Se esperaba ( en el when");}
break;
case 166:
//#line 219 "gramatica.y"
{yyerror("Se esperaba ) en el when");}
break;
case 167:
//#line 220 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 168:
//#line 222 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 169:
//#line 223 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 170:
//#line 224 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 171:
//#line 225 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 172:
//#line 226 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 173:
//#line 227 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 174:
//#line 228 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 175:
//#line 229 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 176:
//#line 230 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 177:
//#line 231 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 178:
//#line 232 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 181:
//#line 236 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 193:
//#line 253 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 194:
//#line 254 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 195:
//#line 256 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 196:
//#line 257 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 197:
//#line 258 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 198:
//#line 259 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 199:
//#line 260 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 200:
//#line 261 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 201:
//#line 262 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 202:
//#line 263 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 203:
//#line 264 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 204:
//#line 265 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 212:
//#line 277 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 213:
//#line 278 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 214:
//#line 279 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
//#line 1520 "Parser.java"
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
