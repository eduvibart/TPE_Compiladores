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
    0,    1,    2,    2,    2,    3,    3,    4,    4,    4,
    6,    9,    9,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,   11,   11,   11,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   17,   17,   17,   17,   17,   17,   17,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   18,
   18,   18,   18,   18,   18,   22,   22,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   20,
   10,    8,   29,   29,    5,    5,    5,    5,    5,    5,
    5,   13,   28,   28,   28,   28,   28,   28,   34,   34,
   34,   35,   35,   27,   27,   27,   27,   30,   30,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   21,
   37,   37,   37,   37,   37,   36,   36,   36,   16,   16,
   16,   16,   31,   31,   31,   31,   31,   31,   31,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   33,   33,
   38,   38,   39,   39,   39,   39,   39,   39,   39,   39,
   39,   26,   26,   40,   40,   32,   32,   32,   32,   32,
   32,   23,   23,   41,   41,   14,   14,   14,   14,   14,
   14,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    2,    1,    1,    1,    1,    1,
    2,    3,    1,   12,   10,    9,   12,   10,    9,    8,
    7,    5,    4,    3,    2,    0,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,   13,
    9,   13,   12,   10,    9,    8,    6,    5,    4,    3,
    2,    8,    8,    6,    5,    4,    3,    2,   13,   11,
   11,    9,    8,    7,    6,    5,    4,    3,    2,    9,
    7,    7,    5,    4,    2,    0,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    1,   13,    9,   13,
   12,   10,    9,    8,    6,    5,    4,    3,    2,    4,
    2,    2,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    1,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    1,    2,    2,   13,    9,   13,
   12,   10,    9,    8,    6,    5,    4,    3,    2,    3,
    1,    1,    1,    1,    1,    0,    3,    2,    4,    4,
    3,    2,    8,    8,    6,    5,    4,    3,    2,   13,
   11,   11,    9,    8,    7,    6,    5,    4,    3,    2,
    0,    4,    1,    1,    1,    1,    1,    1,    2,    1,
    2,    0,    2,    9,    7,    9,    7,    7,    5,    4,
    2,    6,    6,    1,    1,    6,    4,    3,    6,    4,
    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    6,    7,    8,    9,   10,
  105,  111,  107,  106,  108,  109,  110,  139,    0,  152,
    0,   25,    0,  159,    0,  170,    0,  191,    0,   13,
    0,    0,    0,    0,    0,  104,    0,    4,  138,    0,
  124,  125,    0,  116,    0,  123,    0,    0,    0,    0,
  121,  151,    0,   24,    0,  158,    0,  169,    0,    0,
    0,  201,  195,  198,  194,    0,    0,    0,    0,    0,
    0,  126,  127,  137,    0,  141,  142,  143,  144,  145,
    0,    0,    0,    0,    0,    0,    0,  150,  149,   23,
    0,    0,    0,  157,    0,  168,    0,    0,  190,    0,
  200,  197,    0,    0,    0,   12,  103,  136,    0,  122,
    0,    0,    0,  117,  118,  119,  120,  101,    0,   22,
    0,    0,  156,    0,  167,    0,    0,  189,    0,    0,
    0,    0,  135,  146,    0,    0,    0,  155,    3,  166,
    0,    0,    0,    0,  199,  196,    0,    0,    0,   26,
    0,   21,    0,    0,  165,    0,    0,    0,    0,  188,
  187,    0,    0,  134,    0,    0,    0,    0,   26,   20,
    0,  154,  153,  164,    0,  192,  193,    0,    0,    0,
    0,  173,  175,  176,  178,  177,    0,  174,    0,    0,
  133,    0,  129,  147,    0,    0,    0,    0,    0,    0,
   16,   29,   30,   31,    0,   32,   33,   34,   35,   36,
   37,   38,   39,    0,   19,    0,  163,    0,    0,  181,
    0,  179,  172,    0,  186,  132,  146,   51,    0,    0,
   58,    0,   69,    0,   75,    0,    0,   27,   15,   18,
   26,    0,    0,  183,    0,    0,   50,    0,    0,   57,
    0,   68,    0,    0,    0,    0,    0,  162,  161,    0,
    0,  131,    0,   49,    0,  100,   56,    0,   67,    0,
   74,    0,    0,    0,   17,   14,    0,    0,  130,  128,
   48,    0,   55,    0,   66,    0,   73,   76,    0,    0,
    0,  160,   47,   26,   54,   26,   65,    0,    0,    0,
    0,    0,  185,    0,    0,   64,    0,   72,    0,    0,
    0,    0,   71,   78,   80,   81,   83,   82,   87,   77,
   79,    0,   76,    0,   46,    0,   53,   52,   63,    0,
   99,    0,   86,   84,    0,    0,  184,   45,    0,   41,
   62,   76,   98,    0,    0,   70,   44,   26,    0,   97,
    0,    0,    0,   61,   60,   96,    0,   76,   43,    0,
   95,   76,    0,   42,   40,    0,   59,   94,    0,   93,
    0,   89,   92,   76,    0,   91,    0,   90,   88,
};
final static short yydgoto[] = {                          2,
    3,    5,   15,   16,   17,  212,  213,  214,   44,  103,
  178,  215,  216,   54,  218,  219,  220,  221,  222,  223,
   55,  309,   71,  330,  331,  232,   56,   57,   47,   24,
   25,   58,   59,   60,   61,  159,   93,  154,  197,  198,
   76,
};
final static short yysindex[] = {                      -239,
    0,    0, -251,    0,  422, -106,  -84, -225,  -61,  -40,
   -8,  -43,    0, -206, -208,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -153,    0,
 -240,    0,   12,    0, -145,    0,  -68,    0, -206,    0,
 -231,  -30,   31, -194, -160,    0,  -58,    0,    0,   10,
    0,    0,  155,    0, -162,    0,  609,  -32,   19,  163,
    0,    0, -117,    0,  -77,    0,  -87,    0,  -63,   59,
  -60,    0,    0,    0,    0, -133,  -82,  125,  167,  138,
 -206,    0,    0,    0,   34,    0,    0,    0,    0,    0,
  -85,  -85,   31, -151, -151,  -85,  -85,    0,    0,    0,
  158,  117, -129,    0,   67,    0, -224,   31,    0, -236,
    0,    0,  -65,   31, -206,    0,    0,    0, -229,    0,
  163,  163,  167,    0,    0,    0,    0,    0,  160,    0,
  147,  162,    0, -220,    0,   35,  184,    0,  156,  -39,
  190,  199,    0,    0,  211,  228,  -25,    0,    0,    0,
  -86,  198,  156, -235,    0,    0,  238,  237,  558,    0,
  245,    0, -125,  364,    0,  -24, -151, -151,  437,    0,
    0,  258,  156,    0,   85,  149,  255,  395,    0,    0,
   24,    0,    0,    0, -215,    0,    0,  262, -151,  257,
   75,    0,    0,    0,    0,    0,  251,    0, -206,  252,
    0, -211,    0,    0,   70,  267,   83,   93,   94,   -2,
    0,    0,    0,    0,  263,    0,    0,    0,    0,    0,
    0,    0,    0,  408,    0, -188,    0,  156,   31,    0,
  273,    0,    0,  269,    0,    0,    0,    0,  -47,   31,
    0,  -27,    0,   -9,    0, -206,  226,    0,    0,    0,
    0, -233,  274,    0,  272,  572,    0,  -18, -112,    0,
   20,    0,   21,   25,  287,  289,  288,    0,    0,  303,
  156,    0, -159,    0,   72,    0,    0,  103,    0, -124,
    0, -185,   31, -206,    0,    0,  156,  281,    0,    0,
    0, -179,    0, -176,    0,   97,    0,    0,  296,  298,
  240,    0,    0,    0,    0,    0,    0,   95,  453,  270,
  290,  156,    0,  302,  319,    0,   32,    0,  101, -151,
  257,   88,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  304,    0,  318,    0,  182,    0,    0,    0, -169,
    0,    3,    0,    0, -206,  602,    0,    0, -150,    0,
    0,    0,    0,   33,  309,    0,    0,    0,  467,    0,
  135,  307,  333,    0,    0,    0, -149,    0,    0,  119,
    0,    0,  615,    0,    0,  490,    0,    0,  215,    0,
 -147,    0,    0,    0,  504,    0,  136,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  381,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  247,    0,    0,  261,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  121,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  220,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  154,  187, -227,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -219,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  642,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  312,    0,    0,    0,  579,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  316,  320,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  350,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -219,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  312,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  242,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  332,    0,    0,    0,    0,    0,    0,    0,  527,
  541,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  454,    0,    0, -154,    9,   13,   14,    0,  476,
 -168,    0,   -5,   -3,    0,   -4, -292, -287, -285, -281,
  -29, -326, -102,    0,    0,  293,  -38,  -33,    0,    0,
  440,   -1,    7,  415,  423,  385,    0, -138,    0,    0,
  510,
};
final static int YYTABLESIZE=911;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         21,
   23,   22,   75,   26,  177,   67,  346,   69,   46,   79,
  224,   27,  142,   18,  169,   62,  326,   19,   20,  138,
  170,  327,  268,  328,   72,  359,  143,  329,  140,    1,
   32,  135,    4,   70,  200,  148,  171,   73,   51,   52,
  227,  373,   74,   33,  236,  376,  140,  139,   63,  171,
  140,  269,  136,  326,  144,  124,  125,  385,  327,  123,
  328,   53,   45,  149,  329,  171,  326,  250,  228,   48,
  297,  327,  237,  328,   75,  117,  303,  329,  137,  305,
  326,   80,  267,  326,  141,  327,  351,  328,  327,  252,
  328,  329,  326,   84,  329,  251,  289,  327,  298,  328,
  290,  177,   49,  329,  304,  357,  371,  306,  383,   70,
   66,   85,   10,   11,  352,   50,   51,   52,   51,   52,
   10,   11,  111,   50,   51,   52,  130,   43,  186,  187,
  180,  295,  288,  358,  372,  314,  384,  315,   98,   53,
  112,   53,  113,  264,  131,  166,  132,   53,  301,   28,
  230,  181,  296,   21,   23,   22,   99,   26,   21,   23,
   22,  276,   26,  192,  193,   27,   29,  195,  104,  165,
   27,   30,   18,  334,  217,  196,   19,   20,  100,   91,
   92,  300,   45,  120,   51,   52,  105,   68,   31,  363,
  114,  101,  106,  234,   34,  109,  102,   10,   11,  253,
   50,   51,   52,   73,   51,   52,  259,   53,  257,  258,
  107,   35,  261,  110,  263,   36,  155,   81,   10,   11,
  217,   50,   51,   52,   53,   40,   94,   53,  260,   41,
  162,  184,   37,   42,  156,   77,   78,  274,   10,   11,
   70,   50,   51,   52,   43,   53,  262,   38,  163,  185,
   21,   23,   22,  299,   26,  275,   10,   11,  353,   50,
   51,   52,   27,  217,   39,   53,   40,   64,   10,   11,
   41,   50,   51,   52,  247,  277,  279,   95,   70,  225,
  281,  343,   41,   53,   65,   43,   42,  339,  360,  118,
  150,  119,  226,  278,  280,   53,   10,   11,  282,   50,
   51,   52,  317,  324,  325,  340,  361,  151,  122,  122,
  217,  217,  354,  122,  122,  122,  122,  122,  122,  122,
  122,  122,  133,   53,  134,  238,  122,  291,  122,  292,
  122,  122,  122,  122,  122,  122,  108,  122,  241,  355,
  324,  325,  239,  122,  122,  122,  122,  122,  243,  245,
  316,   42,  307,  324,  325,  242,  341,   41,  293,  217,
  294,   42,   43,   45,  247,  244,  246,  324,  325,  308,
  324,  325,   43,  342,  374,   43,  115,  115,  375,  324,
  325,  115,  115,  115,  115,  115,  115,  115,  115,  115,
  366,  388,  367,  129,  115,  389,  115,  115,  115,  115,
  115,  115,  115,  115,  201,  115,  116,  202,  203,  113,
  113,  115,  115,  115,  113,  113,  113,  113,  113,  113,
  113,  113,  113,  146,   82,   83,  128,  113,  145,  113,
  101,  113,  113,  113,  113,  113,  113,  348,  113,  153,
  349,  350,  114,  114,  113,  113,  113,  114,  114,  114,
  114,  114,  114,  114,  114,  114,   96,   97,   91,   92,
  114,  152,  114,  157,  114,  114,  114,  114,  114,  114,
  380,  114,  158,  381,  382,  112,  112,  114,  114,  114,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  167,
  168,  265,  266,  112,  160,  112,  161,  112,  312,  313,
  171,  171,   11,   11,  112,  121,  122,   11,   11,   11,
  112,   11,   11,   11,  172,   11,  102,  102,  126,  127,
  173,  102,  102,  102,   11,  102,  102,  102,  179,  102,
  199,   11,  204,  231,  229,  233,  235,   11,  102,  240,
  248,  254,  255,  285,  205,  102,  332,  270,    7,    8,
  206,  102,  207,  208,  209,  271,  210,  335,  205,  283,
  287,  284,    7,    8,  206,  302,  207,  208,  209,  310,
  210,  311,  286,  333,  337,  205,  345,  347,   14,    7,
    8,  206,  362,  207,  208,  209,  336,  210,  369,  205,
  368,  171,   14,    7,    8,  206,  171,  207,  208,  209,
  180,  210,  164,  338,  182,   28,   28,  147,  194,   14,
   28,   28,   28,  344,   28,   28,   28,  370,   28,  182,
    6,  256,  140,   14,    7,    8,    0,    0,    9,   10,
   11,    0,   12,    0,   28,    0,    5,    5,    0,    0,
   28,    5,    5,    0,    0,    5,    5,    5,  183,    5,
    0,  205,    0,    0,   14,    7,    8,  206,    0,  207,
  208,  209,    0,  210,  205,    5,    0,    0,    7,    8,
  206,    5,  207,  208,  209,    0,  210,    0,    6,  211,
    0,    0,    7,    8,    0,   14,    9,   10,   11,    0,
   12,    0,  249,  188,    0,    0,    0,    7,   14,    0,
  189,    9,   10,   11,  190,  191,   13,    0,  318,  319,
    0,    0,   14,    7,    0,  206,  320,  207,  208,  209,
  321,  322,  364,  319,    0,    0,    0,    7,    0,  206,
  320,  207,  208,  209,  321,  322,    0,  323,    0,    0,
    0,    0,    0,    0,    0,  378,  319,    0,    0,    0,
    7,  365,  206,  320,  207,  208,  209,  321,  322,  386,
  319,    0,    0,    0,    7,    0,  206,  320,  207,  208,
  209,  321,  322,    0,  379,    0,    0,    0,    0,    0,
    0,    0,   85,   85,    0,    0,    0,   85,  387,   85,
   85,   85,   85,   85,   85,   85,  182,  182,    0,    0,
    0,  182,    0,  182,  182,  182,  182,  182,  182,  182,
    0,   85,    0,  174,    6,    0,    0,    0,    7,    0,
    0,    0,    9,   10,   11,  182,  175,  272,    6,    0,
    0,    0,    7,    0,  148,  148,    9,   10,   11,  148,
  175,    0,  176,  148,  148,  148,    0,  148,    0,    0,
    0,    0,    0,    0,    0,    0,  273,    0,  319,    0,
    0,    0,    7,  148,  206,  320,  207,  208,  209,  321,
  322,  319,    0,    0,    0,    7,    0,  206,  320,  207,
  208,  209,  321,  322,    0,    0,  356,   86,   87,   88,
   89,   90,    0,    0,    0,    0,    0,    0,  171,  377,
   91,   92,  171,    0,    0,  171,  171,  171,  171,  171,
  171,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,    5,   41,    5,  159,   35,  333,   37,   14,   43,
  179,    5,  115,    5,  153,  256,  309,    5,    5,  256,
  256,  309,  256,  309,  256,  352,  256,  309,  256,  269,
  256,  256,  284,   39,  173,  256,  256,  269,  270,  271,
  256,  368,  274,  269,  256,  372,  274,  284,  289,  285,
  278,  285,  277,  346,  284,   94,   95,  384,  346,   93,
  346,  293,  269,  284,  346,  285,  359,  256,  284,  278,
  256,  359,  284,  359,  113,   81,  256,  359,  108,  256,
  373,  276,  251,  376,  114,  373,  256,  373,  376,  228,
  376,  373,  385,  256,  376,  284,  256,  385,  284,  385,
  260,  256,  256,  385,  284,  256,  256,  284,  256,  115,
  256,  274,  266,  267,  284,  269,  270,  271,  270,  271,
  266,  267,  256,  269,  270,  271,  256,  288,  167,  168,
  256,  256,  271,  284,  284,  304,  284,  306,  256,  293,
  274,  293,  276,  246,  274,  151,  276,  293,  287,  256,
  189,  277,  277,  159,  159,  159,  274,  159,  164,  164,
  164,  274,  164,  169,  169,  159,  273,  169,  256,  256,
  164,  256,  164,  312,  178,  169,  164,  164,  256,  292,
  293,  284,  269,  269,  270,  271,  274,  256,  273,  358,
  273,  269,  256,  199,  256,  256,  274,  266,  267,  229,
  269,  270,  271,  269,  270,  271,  240,  293,  256,  239,
  274,  273,  242,  274,  244,  256,  256,  276,  266,  267,
  224,  269,  270,  271,  293,  269,  259,  293,  256,  273,
  256,  256,  273,  277,  274,  266,  267,  256,  266,  267,
  246,  269,  270,  271,  288,  293,  256,  256,  274,  274,
  256,  256,  256,  283,  256,  274,  266,  267,  256,  269,
  270,  271,  256,  267,  273,  293,  269,  256,  266,  267,
  273,  269,  270,  271,  277,  256,  256,  259,  284,  256,
  256,  320,  273,  293,  273,  288,  277,  256,  256,  256,
  256,  258,  269,  274,  274,  293,  266,  267,  274,  269,
  270,  271,  308,  309,  309,  274,  274,  273,  256,  257,
  314,  315,  342,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  293,  258,  256,  274,  256,  276,  258,
  278,  279,  280,  281,  282,  283,  278,  285,  256,  345,
  346,  346,  273,  291,  292,  293,  294,  295,  256,  256,
  256,  277,  256,  359,  359,  273,  256,  273,  256,  363,
  258,  277,  288,  269,  277,  273,  273,  373,  373,  273,
  376,  376,  288,  273,  256,  288,  256,  257,  260,  385,
  385,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  256,  256,  258,  277,  274,  260,  276,  273,  278,  279,
  280,  281,  282,  283,  256,  285,  269,  259,  260,  256,
  257,  291,  292,  293,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  277,  270,  271,  269,  274,  269,  276,
  269,  278,  279,  280,  281,  282,  283,  256,  285,  284,
  259,  260,  256,  257,  291,  292,  293,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  294,  295,  292,  293,
  274,  278,  276,  274,  278,  279,  280,  281,  282,  283,
  256,  285,  274,  259,  260,  256,  257,  291,  292,  293,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  292,
  293,  266,  267,  274,  284,  276,  269,  278,  259,  260,
  259,  260,  256,  257,  285,   91,   92,  261,  262,  263,
  291,  265,  266,  267,  277,  269,  256,  257,   96,   97,
  284,  261,  262,  263,  278,  265,  266,  267,  284,  269,
  273,  285,  278,  277,  273,  285,  285,  291,  278,  273,
  278,  269,  274,  256,  257,  285,  277,  274,  261,  262,
  263,  291,  265,  266,  267,  284,  269,  256,  257,  273,
  258,  273,  261,  262,  263,  285,  265,  266,  267,  274,
  269,  274,  285,  284,  256,  257,  273,  260,  291,  261,
  262,  263,  274,  265,  266,  267,  285,  269,  256,  257,
  284,  260,  291,  261,  262,  263,  285,  265,  266,  267,
  285,  269,  149,  285,  285,  256,  257,  132,  169,  291,
  261,  262,  263,  321,  265,  266,  267,  285,  269,  256,
  257,  237,  113,  291,  261,  262,   -1,   -1,  265,  266,
  267,   -1,  269,   -1,  285,   -1,  256,  257,   -1,   -1,
  291,  261,  262,   -1,   -1,  265,  266,  267,  285,  269,
   -1,  257,   -1,   -1,  291,  261,  262,  263,   -1,  265,
  266,  267,   -1,  269,  257,  285,   -1,   -1,  261,  262,
  263,  291,  265,  266,  267,   -1,  269,   -1,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,   -1,  285,  257,   -1,   -1,   -1,  261,  291,   -1,
  264,  265,  266,  267,  268,  269,  285,   -1,  256,  257,
   -1,   -1,  291,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  256,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,  265,  266,  267,  268,  269,   -1,  285,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,   -1,
  261,  285,  263,  264,  265,  266,  267,  268,  269,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,   -1,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,   -1,   -1,  261,  285,  263,
  264,  265,  266,  267,  268,  269,  256,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
   -1,  285,   -1,  256,  257,   -1,   -1,   -1,  261,   -1,
   -1,   -1,  265,  266,  267,  285,  269,  256,  257,   -1,
   -1,   -1,  261,   -1,  256,  257,  265,  266,  267,  261,
  269,   -1,  285,  265,  266,  267,   -1,  269,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  285,   -1,  257,   -1,
   -1,   -1,  261,  285,  263,  264,  265,  266,  267,  268,
  269,  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,
  266,  267,  268,  269,   -1,   -1,  285,  279,  280,  281,
  282,  283,   -1,   -1,   -1,   -1,   -1,   -1,  257,  285,
  292,  293,  261,   -1,   -1,  264,  265,  266,  267,  268,
  269,
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
"sentencia_when_fun : WHEN error",
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
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN error",
"sentencia_when : WHEN PARENT_A condicion PARENT_C error",
"sentencia_when : WHEN PARENT_A condicion error",
"sentencia_when : WHEN PARENT_A error",
"sentencia_when : WHEN error",
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

//#line 268 "gramatica.y"

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
//#line 763 "Parser.java"
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
case 5:
//#line 22 "gramatica.y"
{yyerror("Se esperaba ;");}
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
{yyerror("Se esperaba } ");}
break;
case 18:
//#line 40 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 19:
//#line 41 "gramatica.y"
{yyerror("Se esperaba ID");}
break;
case 20:
//#line 42 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 21:
//#line 43 "gramatica.y"
{yyerror("Se esperaba )");}
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
{System.out.println("Falta declaracion de parametro en la funcion");}
break;
case 25:
//#line 47 "gramatica.y"
{System.out.println("Si declaras una funcion hacelo bien!");}
break;
case 28:
//#line 51 "gramatica.y"
{System.out.println("Se esperaba ;");}
break;
case 40:
//#line 65 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 41:
//#line 66 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 42:
//#line 67 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 43:
//#line 68 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 44:
//#line 69 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 45:
//#line 70 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 46:
//#line 71 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 47:
//#line 72 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 48:
//#line 73 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 49:
//#line 74 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 50:
//#line 75 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 51:
//#line 76 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 52:
//#line 78 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 53:
//#line 79 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 54:
//#line 80 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 55:
//#line 81 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 56:
//#line 82 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 57:
//#line 83 "gramatica.y"
{yyerror("Se esperaba condicion");}
break;
case 58:
//#line 84 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 59:
//#line 86 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 60:
//#line 87 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 61:
//#line 88 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 62:
//#line 89 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 63:
//#line 90 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 64:
//#line 91 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 65:
//#line 92 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 66:
//#line 93 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 67:
//#line 94 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 68:
//#line 95 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 69:
//#line 96 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 70:
//#line 98 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 71:
//#line 99 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 72:
//#line 100 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 73:
//#line 101 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 74:
//#line 102 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 75:
//#line 103 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 88:
//#line 120 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 89:
//#line 121 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 90:
//#line 122 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 91:
//#line 123 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 92:
//#line 124 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 93:
//#line 125 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 94:
//#line 126 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 95:
//#line 127 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 96:
//#line 128 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 97:
//#line 129 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 98:
//#line 130 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 99:
//#line 131 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 102:
//#line 138 "gramatica.y"
{System.out.println("Declaracion de Constante/s");}
break;
case 112:
//#line 151 "gramatica.y"
{System.out.println("Asignacion");}
break;
case 124:
//#line 168 "gramatica.y"
{  chequearRangoI32(val_peek(0).sval);}
break;
case 128:
//#line 175 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 129:
//#line 176 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 130:
//#line 177 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 131:
//#line 178 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 132:
//#line 179 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 133:
//#line 180 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 134:
//#line 181 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 135:
//#line 182 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 136:
//#line 183 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 137:
//#line 184 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 138:
//#line 185 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 139:
//#line 186 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 148:
//#line 198 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 149:
//#line 200 "gramatica.y"
{System.out.println("Sentencia OUT");}
break;
case 150:
//#line 201 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 151:
//#line 202 "gramatica.y"
{yyerror("Se esperaba una CADENA");}
break;
case 152:
//#line 203 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 153:
//#line 205 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 154:
//#line 206 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 155:
//#line 207 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 156:
//#line 208 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 157:
//#line 209 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 158:
//#line 210 "gramatica.y"
{yyerror("Se esperaba condicion");}
break;
case 159:
//#line 211 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 160:
//#line 213 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 161:
//#line 214 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 162:
//#line 215 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 163:
//#line 216 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 164:
//#line 217 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 165:
//#line 218 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 166:
//#line 219 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 167:
//#line 220 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 168:
//#line 221 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 169:
//#line 222 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 170:
//#line 223 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 184:
//#line 243 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 185:
//#line 244 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 186:
//#line 246 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 187:
//#line 247 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 188:
//#line 248 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 189:
//#line 249 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 190:
//#line 250 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 191:
//#line 251 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 199:
//#line 263 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 200:
//#line 264 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 201:
//#line 265 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
//#line 1360 "Parser.java"
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
