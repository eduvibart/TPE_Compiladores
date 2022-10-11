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
   21,   21,   21,   37,   37,   37,   37,   37,   36,   36,
   36,   16,   16,   16,   16,   31,   31,   31,   31,   31,
   31,   31,   31,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   38,   38,   38,   39,   39,   39,
   39,   39,   39,   39,   39,   39,   26,   26,   40,   40,
   32,   32,   32,   32,   32,   32,   32,   32,   32,   32,
   23,   23,   41,   41,   14,   14,   14,   14,   14,   14,
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
    3,    3,    3,    1,    1,    1,    1,    1,    0,    3,
    2,    4,    4,    3,    2,    8,    8,    8,    8,    8,
    8,    7,    5,   13,   11,   11,    9,    8,    7,    6,
    5,    4,    3,    2,    0,    3,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    0,    2,    9,    7,
    9,    9,    7,    6,    3,    7,    7,    5,    4,    2,
    6,    6,    1,    1,    6,    4,    3,    6,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    4,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    7,    8,    9,   10,
   11,  106,  112,  108,  107,  109,  110,  111,  140,    0,
  155,    0,   26,    0,    0,    0,  174,    0,  200,    0,
   14,    0,    0,    0,    0,    0,  105,    0,    5,  139,
    0,  125,  126,    0,  117,    0,  124,    0,    0,    0,
    0,  122,  154,    0,   25,    0,    0,    0,    0,  173,
    0,    0,    0,  210,  204,  207,  203,    0,  195,    0,
    0,    0,    0,    0,  127,  128,  138,    0,    0,  144,
  145,  146,  147,  148,    0,    0,    0,    0,    0,    0,
    0,  153,  152,   24,    0,    0,    0,    0,    0,    0,
    0,  172,    0,    0,  199,    0,  209,  206,    0,    0,
    0,   13,  104,  137,    0,    0,  123,    0,    0,  142,
    0,  118,  119,  120,  121,  102,    0,   23,    0,    0,
    0,    0,    4,    0,    0,  171,    0,    0,  198,  175,
    0,    0,    0,  136,  149,    0,    0,    0,    4,    4,
    0,    4,    4,    4,  170,    0,    0,    0,  208,  205,
    0,  194,    0,    0,   27,    0,   22,    0,    0,    0,
  162,    0,    0,    0,  169,    0,    0,    0,  197,    0,
    0,    0,    0,  196,  178,  180,  181,  183,  182,    0,
  179,    0,  193,  175,  135,    0,    0,    0,    0,   27,
   21,    0,  161,  160,  159,  158,  157,  156,  168,    0,
  201,  202,    0,  186,    0,  184,  176,    0,    0,  134,
    0,  130,  150,    0,    0,    0,    0,    0,    0,   17,
   30,   31,   32,    0,   33,   34,   35,   36,   37,   38,
   39,   40,    0,   20,    0,  167,  175,    0,  188,    0,
  192,  191,  133,  149,   52,    0,    0,    0,    0,   70,
    0,   76,    0,    0,   28,   16,   19,   27,    0,    0,
    0,    0,   51,    0,    0,    0,   58,    0,   69,    0,
    0,    0,    0,    0,  166,  165,  175,  175,  132,    0,
   50,    0,  101,    0,   57,    0,   68,    0,   75,    0,
    0,    0,   18,   15,    0,    0,  131,  129,   49,    0,
    0,   56,    0,   67,    0,   74,   77,    0,    0,  175,
  190,  164,   48,   27,   27,   55,   27,   66,    0,    0,
    0,    0,    0,    0,    0,    0,   65,    0,   73,    0,
    0,    0,    0,   72,   79,   81,   82,   84,   83,   88,
   78,   80,    0,   77,  189,   47,    0,   59,   54,   53,
   64,    0,  100,    0,   87,   85,    0,    0,   46,    0,
   42,   63,   77,   99,    0,    0,   71,   45,   27,    0,
   98,    0,    0,    0,   62,   61,   97,    0,   77,   44,
    0,   96,   77,    0,   43,   41,    0,   60,   95,    0,
   94,    0,   90,   93,   77,    0,   92,    0,   91,   89,
};
final static short yydgoto[] = {                          3,
    4,    6,   16,   17,   18,   19,   20,   21,   45,  107,
  209,  244,   22,   55,  247,   24,  249,  250,  251,  252,
   56,  340,   73,  361,  362,  226,   57,   58,   48,   25,
   26,   59,   60,   61,   62,  174,   97,  168,  200,  201,
   78,
};
final static short yysindex[] = {                      -172,
    0,    0,    0, -262,    0,  582, -188,  -47,  -85,   43,
   46,   93,  -18,    0, -241, -190,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   42,
    0, -243,    0,  120,  116,   67,    0,  103,    0, -241,
    0, -237,  254,  116, -150, -124,    0, -147,    0,    0,
 -154,    0,    0,  -97,    0, -149,    0, -138, -121,  -61,
   63,    0,    0,  -73,    0,  -59,  -67,  -29,  -81,    0,
  -37,  -30,  -28,    0,    0,    0,    0, -142,    0,  -19,
  -13, -219,   11, -241,    0,    0,    0,  183,  116,    0,
    0,    0,    0,    0, -102, -102,  299, -250, -250, -102,
 -102,    0,    0,    0,   26,   28,  -84,   57,   92,   81,
  214,    0, -239,  116,    0, -244,    0,    0,  -46,  116,
 -241,    0,    0,    0, -242, -219,    0,   63,   63,    0,
 -219,    0,    0,    0,    0,    0,  131,    0,  114,  134,
   96,  220,    0,  260, -232,    0,  121,  189,    0,    0,
   19,  205,   32,    0,    0,  264,  289,   33,    0,    0,
  596,    0,    0,    0,    0,  -16,  192,  818,    0,    0,
  283,    0, -221,  141,    0,  295,    0, -238,  609,  623,
    0,  636,  650,  528,    0,   48, -250, -250,    0,  330,
 -250,  332,  229,    0,    0,    0,    0,    0,    0,  355,
    0,  363,    0,    0,    0,  111, -229,  356,  328,    0,
    0,  174,    0,    0,    0,    0,    0,    0,    0, -220,
    0,    0,  116,    0,  371,    0,    0, -241,  833,    0,
 -209,    0,    0,  122,  368,  169,  171,  172,   79,    0,
    0,    0,    0,  364,    0,    0,    0,    0,    0,    0,
    0,    0,  555,    0, -194,    0,    0,  369,    0,  370,
    0,    0,    0,    0,    0,  305,  116,  116,  311,    0,
  317,    0, -241,  256,    0,    0,    0,    0,  848,  387,
  365,  863,    0,   58,  -31,  373,    0,   65,    0,   70,
   71,  375,  384,  449,    0,    0,    0,    0,    0,  -56,
    0,  247,    0,  409,    0,  285,    0, -157,    0, -180,
  116, -241,    0,    0,  665,  913,    0,    0,    0, -179,
  382,    0, -175,    0,  209,    0,    0,  402,  404,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  212,  728,
  391,  396,  926,  466,  569,  480,    0,   87,    0,  210,
 -250,  332,  231,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  410,    0,    0,    0,  340,    0,    0,    0,
    0, -167,    0,  345,    0,    0, -241,  877,    0, -153,
    0,    0,    0,    0,   90,  407,    0,    0,    0,  743,
    0,  301,  398,  497,    0,    0,    0, -135,    0,    0,
   95,    0,    0,  900,    0,    0,  758,    0,    0,  346,
    0, -119,    0,    0,    0,  773,    0,  286,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  542,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  408,    0,    0,  435,    0,    0,
  155,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  195,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  361,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -22,    0,  233,  271,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  394,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  683,  698,    0,    0,    0,    0,    0,    0,    0,  713,
    0,    0,    0,    0,    0,    0,    0,  870,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  511,    0,    0,    0,    0,    0,    0,
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
  788,  803,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  123,    0,    0, -169, -186, -183, -159,    0,  544,
 -207,    0,  -15,   -4,    0, -113, -325, -324, -292, -268,
  -27, -354, -117,    0,    0,  335,  -41,  -38,    0,    0,
 -127,   38,   89,  512,  531,  424,    0, -197,    0,    0,
  570,
};
final static int YYTABLESIZE=1198;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   77,   23,  253,  153,  208,   82,  229,   67,   69,  378,
   71,  149,   63,  154,  357,  358,  146,  211,   74,   52,
   53,    5,  241,  163,   72,  242,  230,   46,  390,  231,
  232,   75,   52,   53,  203,  256,   76,  147,  212,  150,
  197,  155,   54,   27,  404,   64,  263,  359,  407,  243,
  126,  164,  357,  358,  196,   54,  132,  133,  131,  279,
  416,  277,  204,  257,  357,  358,  241,   29,  123,  242,
  294,  360,   95,   96,  264,  326,  333,   77,  357,  358,
  336,  357,  358,    1,   30,  359,  148,   49,  382,  278,
  357,  358,  152,  243,   28,  248,    2,  359,  324,  315,
  316,  197,  388,  327,  334,   72,   87,  241,  337,  360,
  242,  359,  208,  117,  359,  196,  383,   89,   42,  325,
  402,  360,   43,  359,   88,   83,  344,  345,   84,  346,
  389,  118,  343,  119,  243,  360,  414,   98,  360,  248,
   90,   91,   92,   93,   94,  221,  222,  360,  403,  224,
  186,  197,  195,   95,   96,  291,   23,  241,  241,  241,
  242,  242,  242,   44,  415,  196,  127,   52,   53,   23,
   33,  138,   85,   86,   23,   23,  110,   23,   23,   23,
  248,  394,  102,   34,  243,  243,  243,  197,  197,  139,
   54,  140,  111,  245,  329,  258,  104,   99,   27,  317,
  103,  196,  196,  318,  246,  198,  108,  241,   31,  105,
  242,   27,  260,  195,  106,  197,   27,   27,  112,   27,
   27,   27,   75,   52,   53,   32,  356,  115,  285,  196,
  248,  248,  248,  143,  243,  143,  113,  245,  284,  185,
  286,  288,  303,  290,  109,  116,   54,  114,  246,   28,
   41,  143,   46,  120,   42,  143,  199,   72,   43,  121,
   95,   96,   28,  195,  356,  161,  198,   28,   28,   44,
   28,   28,   28,  141,  169,  141,  356,   23,  245,  122,
  248,  179,  180,  328,  182,  183,  184,  172,  177,  246,
  356,  141,  170,  356,  136,  141,   72,   50,   35,  195,
  195,   37,  356,  219,  137,  173,  178,   11,   12,  375,
   51,   52,   53,  301,  141,   36,  198,  199,   38,   27,
  305,  220,   68,  348,  355,  307,  309,  195,  245,  245,
  245,  302,   11,   12,   54,   51,   52,   53,  306,  246,
  246,  246,  371,  308,  310,  391,  385,   41,   39,  142,
  405,   42,  198,  198,  406,  274,  100,  101,   70,   54,
  372,  386,  355,  392,  143,   40,   44,  199,   11,   12,
   28,   51,   52,   53,  355,   65,  165,  265,  245,  159,
  198,   11,   12,   42,   51,   52,   53,   43,  355,  246,
  157,  355,   66,  166,  266,   54,  205,    7,   44,  156,
  355,    8,  105,  199,  199,   10,   11,   12,   54,  206,
  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,
  123,  123,  123,  123,  268,  207,  270,  272,  123,  254,
  123,  199,  123,  123,  123,  123,  123,  123,  124,  123,
  125,  269,  255,  271,  273,  123,  123,  123,  123,  123,
  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,
  116,  116,  116,  116,  338,  373,  167,  347,  116,  144,
  116,  145,  116,  116,  116,  116,  116,  116,  171,  116,
   46,  339,  374,  187,  188,  116,  116,  116,  114,  114,
  114,  114,  114,  114,  114,  114,  114,  114,  114,  114,
  114,  114,  319,  160,  320,   43,  114,  274,  114,   79,
  114,  114,  114,  114,  114,  114,   44,  114,   44,   80,
   81,  292,  293,  114,  114,  114,  115,  115,  115,  115,
  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,
  322,  419,  323,  162,  115,  420,  115,  175,  115,  115,
  115,  115,  115,  115,  130,  115,  397,  176,  398,  202,
  283,  115,  115,  115,   11,   12,  287,   51,   52,   53,
   11,   12,  289,   51,   52,   53,   11,   12,  210,   51,
   52,   53,   11,   12,  234,   51,   52,   53,    8,    9,
  235,   54,  236,  237,  238,  379,  239,   54,  380,  381,
  384,  411,  223,   54,  412,  413,  128,  129,  225,   54,
   11,   12,  240,   51,   52,   53,  113,  113,   15,  113,
  113,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  134,  135,  227,  233,  113,  228,  113,   54,  113,  259,
  267,  275,  280,  281,  297,  113,  304,  311,  298,  163,
  163,  113,  163,  163,  163,  163,  312,  163,  163,  163,
  163,  163,  163,   12,   12,  335,  321,  363,   12,   12,
   12,  163,   12,   12,   12,  341,   12,  342,  163,  364,
  393,  399,  377,  158,  163,   12,  376,  282,  151,    0,
  103,  103,   12,    0,    0,  103,  103,  103,   12,  103,
  103,  103,    0,  103,  313,  234,    0,    0,    0,    8,
    9,  235,  103,  236,  237,  238,    0,  239,    0,  103,
    0,  366,  234,    0,    0,  103,    8,    9,  235,    0,
  236,  237,  238,  314,  239,  369,  234,    0,    0,   15,
    8,    9,  235,    0,  236,  237,  238,    0,  239,    0,
  367,    0,  400,  234,    0,    0,   15,    8,    9,  235,
    0,  236,  237,  238,  370,  239,   29,   29,    0,    0,
   15,   29,   29,   29,    0,   29,   29,   29,    0,   29,
    0,  401,    0,  217,    7,    0,    0,   15,    8,    9,
    0,    0,   10,   11,   12,   29,   13,    6,    6,    0,
    0,   29,    6,    6,    0,    0,    6,    6,    6,    0,
    6,  234,  218,    0,    0,    8,    9,  235,   15,  236,
  237,  238,    0,  239,    0,  234,    6,    0,    0,    8,
    9,  235,    6,  236,  237,  238,    0,  239,    7,  276,
    0,    0,    8,    9,    0,   15,   10,   11,   12,    0,
   13,    0,    7,  368,    0,    0,    8,    9,    0,   15,
   10,   11,   12,    0,   13,    7,   14,    0,    0,    8,
    9,    0,   15,   10,   11,   12,    0,   13,    0,    7,
  181,    0,    0,    8,    9,    0,   15,   10,   11,   12,
    0,   13,    7,  213,    0,    0,    8,    9,    0,   15,
   10,   11,   12,    0,   13,    0,    7,  214,    0,    0,
    8,    9,    0,   15,   10,   11,   12,    0,   13,    0,
  215,  190,    0,  330,  331,    8,   15,    0,  191,   10,
   11,   12,  192,  193,  216,    0,    0,    0,  185,  185,
   15,  185,  185,  185,    0,    0,  185,  185,  185,  185,
  185,  185,    0,  187,  187,    0,  187,  187,  187,    0,
  185,  187,  187,  187,  187,  187,  187,  185,  177,  177,
    0,  177,  177,  177,    0,  187,  177,  177,  177,  177,
  177,  177,  187,  349,  350,    0,    0,    0,    8,    0,
  235,  351,  236,  237,  238,  352,  353,  177,  395,  350,
    0,    0,    0,    8,    0,  235,  351,  236,  237,  238,
  352,  353,  354,  409,  350,    0,    0,    0,    8,    0,
  235,  351,  236,  237,  238,  352,  353,  396,  417,  350,
    0,    0,    0,    8,    0,  235,  351,  236,  237,  238,
  352,  353,  410,   86,   86,    0,    0,    0,   86,    0,
   86,   86,   86,   86,   86,   86,   86,  418,  187,  187,
    0,    0,    0,  187,    0,  187,  187,  187,  187,  187,
  187,  187,   86,  189,  190,    0,    0,    0,    8,    0,
    0,  191,   10,   11,   12,  192,  193,  187,  261,  190,
    0,    0,    0,    8,    0,    0,  191,   10,   11,   12,
  192,  193,  194,  295,  190,    0,    0,    0,    8,    0,
    0,  191,   10,   11,   12,  192,  193,  262,  299,    7,
    0,    0,    0,    8,    0,  151,  151,   10,   11,   12,
  151,  206,  296,  350,  151,  151,  151,    8,  151,  235,
  351,  236,  237,  238,  352,  353,    0,  300,    0,    0,
    0,    0,    0,    0,  151,    0,  350,    0,    0,    0,
    8,  387,  235,  351,  236,  237,  238,  352,  353,  190,
    0,    0,    0,    8,    0,    0,  191,   10,   11,   12,
  192,  193,  190,    0,  408,  365,    8,    0,    0,  191,
   10,   11,   12,  192,  193,    0,    0,  332,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   42,    6,  210,  121,  174,   44,  204,   35,   36,  364,
   38,  256,  256,  256,  340,  340,  256,  256,  256,  270,
  271,  284,  209,  256,   40,  209,  256,  269,  383,  259,
  260,  269,  270,  271,  256,  256,  274,  277,  277,  284,
  168,  284,  293,    6,  399,  289,  256,  340,  403,  209,
   89,  284,  378,  378,  168,  293,   98,   99,   97,  257,
  415,  256,  284,  284,  390,  390,  253,  256,   84,  253,
  278,  340,  292,  293,  284,  256,  256,  119,  404,  404,
  256,  407,  407,  256,  273,  378,  114,  278,  256,  284,
  416,  416,  120,  253,    6,  209,  269,  390,  256,  297,
  298,  229,  256,  284,  284,  121,  256,  294,  284,  378,
  294,  404,  282,  256,  407,  229,  284,  256,  273,  277,
  256,  390,  277,  416,  274,  276,  334,  335,  276,  337,
  284,  274,  330,  276,  294,  404,  256,  259,  407,  253,
  279,  280,  281,  282,  283,  187,  188,  416,  284,  191,
  166,  279,  168,  292,  293,  273,  161,  344,  345,  346,
  344,  345,  346,  288,  284,  279,  269,  270,  271,  174,
  256,  256,  270,  271,  179,  180,  258,  182,  183,  184,
  294,  389,  256,  269,  344,  345,  346,  315,  316,  274,
  293,  276,  274,  209,  312,  223,  256,  259,  161,  256,
  274,  315,  316,  260,  209,  168,  274,  394,  256,  269,
  394,  174,  228,  229,  274,  343,  179,  180,  256,  182,
  183,  184,  269,  270,  271,  273,  340,  256,  267,  343,
  344,  345,  346,  256,  394,  258,  274,  253,  266,  256,
  268,  269,  274,  271,  274,  274,  293,  278,  253,  161,
  269,  274,  269,  273,  273,  278,  168,  273,  277,  273,
  292,  293,  174,  279,  378,  143,  229,  179,  180,  288,
  182,  183,  184,  256,  256,  258,  390,  282,  294,  269,
  394,  159,  160,  311,  162,  163,  164,  256,  256,  294,
  404,  274,  274,  407,  269,  278,  312,  256,  256,  315,
  316,  256,  416,  256,  277,  274,  274,  266,  267,  351,
  269,  270,  271,  256,  258,  273,  279,  229,  273,  282,
  256,  274,  256,  339,  340,  256,  256,  343,  344,  345,
  346,  274,  266,  267,  293,  269,  270,  271,  274,  344,
  345,  346,  256,  274,  274,  256,  374,  269,  256,  258,
  256,  273,  315,  316,  260,  277,  294,  295,  256,  293,
  274,  377,  378,  274,  284,  273,  288,  279,  266,  267,
  282,  269,  270,  271,  390,  256,  256,  256,  394,  284,
  343,  266,  267,  273,  269,  270,  271,  277,  404,  394,
  277,  407,  273,  273,  273,  293,  256,  257,  288,  269,
  416,  261,  269,  315,  316,  265,  266,  267,  293,  269,
  256,  257,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  256,  285,  256,  256,  274,  256,
  276,  343,  278,  279,  280,  281,  282,  283,  256,  285,
  258,  273,  269,  273,  273,  291,  292,  293,  294,  295,
  256,  257,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  256,  256,  278,  256,  274,  256,
  276,  258,  278,  279,  280,  281,  282,  283,  274,  285,
  269,  273,  273,  292,  293,  291,  292,  293,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  284,  258,  277,  274,  277,  276,  256,
  278,  279,  280,  281,  282,  283,  288,  285,  288,  266,
  267,  266,  267,  291,  292,  293,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  256,  256,  258,  284,  274,  260,  276,  284,  278,  279,
  280,  281,  282,  283,  256,  285,  256,  269,  258,  277,
  256,  291,  292,  293,  266,  267,  256,  269,  270,  271,
  266,  267,  256,  269,  270,  271,  266,  267,  284,  269,
  270,  271,  266,  267,  257,  269,  270,  271,  261,  262,
  263,  293,  265,  266,  267,  256,  269,  293,  259,  260,
  256,  256,  273,  293,  259,  260,   95,   96,  277,  293,
  266,  267,  285,  269,  270,  271,  256,  257,  291,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  100,  101,  278,  278,  274,  273,  276,  293,  278,  269,
  273,  278,  274,  274,  258,  285,  274,  273,  284,  256,
  257,  291,  259,  260,  261,  262,  273,  264,  265,  266,
  267,  268,  269,  256,  257,  284,  258,  277,  261,  262,
  263,  278,  265,  266,  267,  274,  269,  274,  285,  284,
  274,  284,  273,  140,  291,  278,  352,  264,  119,   -1,
  256,  257,  285,   -1,   -1,  261,  262,  263,  291,  265,
  266,  267,   -1,  269,  256,  257,   -1,   -1,   -1,  261,
  262,  263,  278,  265,  266,  267,   -1,  269,   -1,  285,
   -1,  256,  257,   -1,   -1,  291,  261,  262,  263,   -1,
  265,  266,  267,  285,  269,  256,  257,   -1,   -1,  291,
  261,  262,  263,   -1,  265,  266,  267,   -1,  269,   -1,
  285,   -1,  256,  257,   -1,   -1,  291,  261,  262,  263,
   -1,  265,  266,  267,  285,  269,  256,  257,   -1,   -1,
  291,  261,  262,  263,   -1,  265,  266,  267,   -1,  269,
   -1,  285,   -1,  256,  257,   -1,   -1,  291,  261,  262,
   -1,   -1,  265,  266,  267,  285,  269,  256,  257,   -1,
   -1,  291,  261,  262,   -1,   -1,  265,  266,  267,   -1,
  269,  257,  285,   -1,   -1,  261,  262,  263,  291,  265,
  266,  267,   -1,  269,   -1,  257,  285,   -1,   -1,  261,
  262,  263,  291,  265,  266,  267,   -1,  269,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,   -1,  257,  285,   -1,   -1,  261,  262,   -1,  291,
  265,  266,  267,   -1,  269,  257,  285,   -1,   -1,  261,
  262,   -1,  291,  265,  266,  267,   -1,  269,   -1,  257,
  285,   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,
   -1,  269,  257,  285,   -1,   -1,  261,  262,   -1,  291,
  265,  266,  267,   -1,  269,   -1,  257,  285,   -1,   -1,
  261,  262,   -1,  291,  265,  266,  267,   -1,  269,   -1,
  285,  257,   -1,  259,  260,  261,  291,   -1,  264,  265,
  266,  267,  268,  269,  285,   -1,   -1,   -1,  256,  257,
  291,  259,  260,  261,   -1,   -1,  264,  265,  266,  267,
  268,  269,   -1,  256,  257,   -1,  259,  260,  261,   -1,
  278,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,  259,  260,  261,   -1,  278,  264,  265,  266,  267,
  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,   -1,
   -1,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,   -1,   -1,  261,   -1,   -1,  264,  265,  266,  267,
  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,   -1,
   -1,  264,  265,  266,  267,  268,  269,  285,  256,  257,
   -1,   -1,   -1,  261,   -1,  256,  257,  265,  266,  267,
  261,  269,  285,  257,  265,  266,  267,  261,  269,  263,
  264,  265,  266,  267,  268,  269,   -1,  285,   -1,   -1,
   -1,   -1,   -1,   -1,  285,   -1,  257,   -1,   -1,   -1,
  261,  285,  263,  264,  265,  266,  267,  268,  269,  257,
   -1,   -1,   -1,  261,   -1,   -1,  264,  265,  266,  267,
  268,  269,  257,   -1,  285,  260,  261,   -1,   -1,  264,
  265,  266,  267,  268,  269,   -1,   -1,  285,
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
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue ELSE bloque_break_continue END_IF",
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_break_continue END_IF",
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

//#line 277 "gramatica.y"

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
        /*if(t.getId() != -1){
          System.out.println("Id: " + t.getId()+" Lexema: " + t.getLexema());
        }else
          System.out.println("TERMINO LA EJECUCION");*/
        return t.getId();
}
//#line 837 "Parser.java"
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
{yyerror("Se esperaba (");}
break;
case 26:
//#line 48 "gramatica.y"
{yyerror("Se esperaba un nombre de funcion");}
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
case 142:
//#line 190 "gramatica.y"
{yyerror("Se esperaba otra expresion para comparar.");}
break;
case 143:
//#line 191 "gramatica.y"
{yyerror("Se esperaba un tipo de comparacion.");}
break;
case 151:
//#line 201 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 152:
//#line 203 "gramatica.y"
{System.out.println("Sentencia OUT");}
break;
case 153:
//#line 204 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 154:
//#line 205 "gramatica.y"
{yyerror("Se esperaba una CADENA");}
break;
case 155:
//#line 206 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 156:
//#line 208 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 157:
//#line 209 "gramatica.y"
{yyerror("Se esperaba } en el when");}
break;
case 158:
//#line 210 "gramatica.y"
{yyerror("Se esperaba { en el when");}
break;
case 159:
//#line 211 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 160:
//#line 212 "gramatica.y"
{yyerror("Se esperaba condicion en el when");}
break;
case 161:
//#line 213 "gramatica.y"
{yyerror("Se esperaba ( en el when");}
break;
case 162:
//#line 214 "gramatica.y"
{yyerror("Se esperaba ) en el when");}
break;
case 163:
//#line 215 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 164:
//#line 217 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 165:
//#line 218 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 166:
//#line 219 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 167:
//#line 220 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 168:
//#line 221 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 169:
//#line 222 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 170:
//#line 223 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 171:
//#line 224 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 172:
//#line 225 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 173:
//#line 226 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 174:
//#line 227 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 177:
//#line 231 "gramatica.y"
{yyerror("Se esperaba ;");}
break;
case 189:
//#line 248 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 190:
//#line 249 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 191:
//#line 251 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 192:
//#line 252 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 193:
//#line 253 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 194:
//#line 254 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 195:
//#line 255 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 196:
//#line 256 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 197:
//#line 257 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 198:
//#line 258 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 199:
//#line 259 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 200:
//#line 260 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 208:
//#line 272 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 209:
//#line 273 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 210:
//#line 274 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
//#line 1470 "Parser.java"
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
