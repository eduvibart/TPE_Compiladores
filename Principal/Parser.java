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
    7,    7,    7,    7,    7,    7,    7,    7,    7,   12,
   12,   12,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   18,   18,   18,   18,   18,
   18,   18,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   19,   19,   19,   19,   19,   19,   23,
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
    1,    1,    1,    2,    1,    3,    1,   12,   10,    9,
   12,   10,    9,    8,    7,    5,    4,    3,    2,    0,
    3,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,   13,    9,   13,   12,   10,    9,    8,
    6,    5,    4,    3,    2,    8,    8,    6,    5,    4,
    3,    8,   13,   11,   11,    9,    8,    7,    6,    5,
    4,    3,    2,    9,    7,    7,    5,    4,    2,    0,
    2,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    1,   13,    9,   13,   12,   10,    9,    8,    6,    5,
    4,    3,    2,    4,    2,    1,    2,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    3,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    2,    2,   13,    9,   13,   12,   10,    9,    8,    6,
    5,    4,    3,    2,    3,    3,    3,    1,    1,    1,
    1,    1,    0,    3,    2,    4,    4,    3,    2,    8,
    8,    8,    8,    8,    8,    7,    5,   13,   11,   11,
    9,    8,    7,    6,    5,    4,    3,    2,    0,    3,
    2,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    0,    2,    9,    7,    9,    9,    7,    6,    3,    7,
    7,    5,    4,    2,    6,    6,    1,    1,    6,    4,
    3,    6,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    4,    0,    0,    0,    0,    0,
    0,    0,    0,   12,   13,    1,    0,    0,    7,    8,
    9,   10,   11,    0,    0,  110,  116,  112,  111,  113,
  114,  115,  144,    0,  159,    0,   29,    0,    0,    0,
  178,    0,  204,    0,    0,    0,    0,    0,  109,    0,
    5,   17,    0,    0,  143,    0,    0,  129,  130,  121,
    0,  128,    0,    0,    0,    0,  126,  158,    0,   28,
    0,    0,    0,    0,  177,    0,    0,    0,  214,  208,
  211,  207,    0,  199,    0,    0,    0,    0,   16,  131,
  132,  142,    0,    0,  148,  149,  150,  151,  152,    0,
    0,    0,    0,    0,    0,    0,  157,  156,   27,  106,
    0,    0,    0,    0,    0,    0,    0,  176,    0,    0,
  203,    0,  213,  210,    0,    0,    0,  108,  141,    0,
    0,  127,    0,    0,  146,    0,  122,  123,  124,  125,
    0,  105,   26,    0,    0,    0,    0,    4,    0,    0,
  175,    0,    0,  202,  179,    0,    0,    0,  140,  153,
    0,    0,    0,    4,    4,    0,    4,    4,    4,  174,
    0,    0,    0,  212,  209,    0,  198,    0,    0,   30,
    0,   25,    0,    0,    0,  166,    0,    0,    0,  173,
    0,    0,    0,  201,    0,    0,    0,    0,  200,  182,
  184,  185,  187,  186,    0,  183,    0,  197,  179,  139,
    0,    0,    0,    0,   30,   24,    0,  165,  164,  163,
  162,  161,  160,  172,    0,  205,  206,    0,  190,    0,
  188,  180,    0,    0,  138,    0,  134,  154,    0,    0,
    0,    0,    0,    0,   20,   33,   34,   35,    0,   36,
   37,   38,   39,   40,   41,   42,   43,    0,   23,    0,
  171,  179,    0,  192,    0,  196,  195,  137,  153,   55,
    0,    0,    0,    0,   73,    0,   79,    0,    0,   31,
   19,   22,   30,    0,    0,    0,    0,   54,    0,    0,
    0,   61,    0,   72,    0,    0,    0,    0,    0,  170,
  169,  179,  179,  136,    0,   53,    0,  104,    0,   60,
    0,   71,    0,   78,    0,    0,    0,   21,   18,    0,
    0,  135,  133,   52,    0,    0,   59,    0,   70,    0,
   77,   80,    0,    0,  179,  194,  168,   51,   30,   30,
   58,   30,   69,    0,    0,    0,    0,    0,    0,    0,
    0,   68,    0,   76,    0,    0,    0,    0,   75,   82,
   84,   85,   87,   86,   91,   81,   83,    0,   80,  193,
   50,    0,   62,   57,   56,   67,    0,  103,    0,   90,
   88,    0,    0,   49,    0,   45,   66,   80,  102,    0,
    0,   74,   48,   30,    0,  101,    0,    0,    0,   65,
   64,  100,    0,   80,   47,    0,   99,   80,    0,   46,
   44,    0,   63,   98,    0,   97,    0,   93,   96,   80,
    0,   95,    0,   94,   92,
};
final static short yydgoto[] = {                          3,
    4,    6,   18,   19,   20,   21,   22,   23,   24,   25,
  113,  214,  249,   26,   60,  252,   28,  254,  255,  256,
  257,   61,  345,   78,  366,  367,  231,   62,   63,   50,
   29,   30,   64,   65,   66,   67,  179,  102,  173,  205,
  206,   83,
};
final static short yysindex[] = {                      -131,
    0,    0,    0, -260,    0,  769, -235, -171,  -51, -128,
 -112,  -60,  165,    0,    0,    0, -240, -246,    0,    0,
    0,    0,    0, -180, -199,    0,    0,    0,    0,    0,
    0,    0,    0,  -23,    0, -244,    0,  -47, -249,   -4,
    0,   12,    0, -240, -241,  174, -249, -179,    0, -193,
    0,    0, -199,  -79,    0,  -74,   72,    0,    0,    0,
 -231,    0,   58,  -55,  -29,  247,    0,    0,  -14,    0,
   46, -143,  -40, -239,    0,   -2,  -39,   20,    0,    0,
    0,    0,  -76,    0,   -7,   14,  140, -240,    0,    0,
    0,    0,  160, -249,    0,    0,    0,    0,    0, -185,
 -185,   93,  164,  164, -185, -185,    0,    0,    0,    0,
   -6,   28,  -25,   49,   52,   39,  189,    0, -205, -249,
    0, -247,    0,    0, -174, -249, -240,    0,    0, -222,
  140,    0,  247,  247,    0,  140,    0,    0,    0,    0,
  194,    0,    0,   77,  307,   51,  116,    0,  119, -217,
    0,   57,   99,    0,    0,   21,  210,   27,    0,    0,
  143,  194,   55,    0,    0,  780,    0,    0,    0,    0,
   29,  270, 1024,    0,    0,  261,    0, -216, 1069,    0,
  203,    0, -130,  807,  818,    0,  845,  856,  666,    0,
   62,  164,  164,    0,  251,  164,  297,  -13,    0,    0,
    0,    0,    0,    0,  322,    0,  256,    0,    0,    0,
  165,  240,  327,  704,    0,    0,  -96,    0,    0,    0,
    0,    0,    0,    0, -206,    0,    0, -249,    0,  343,
    0,    0, -240, 1039,    0, -202,    0,    0,  108,  351,
  124,  135,  137,  171,    0,    0,    0,    0,  340,    0,
    0,    0,    0,    0,    0,    0,    0,  731,    0, -191,
    0,    0,  364,    0,  365,    0,    0,    0,    0,    0,
  118, -249, -249,  132,    0,  153,    0, -240,  313,    0,
    0,    0,    0, 1054,  383,  359, 1076,    0,   70,  -34,
  371,    0,   89,    0,  101,  102,  373,  374,  561,    0,
    0,    0,    0,    0,  -31,    0,  269,    0,  390,    0,
  309,    0, -113,    0, -187, -249, -240,    0,    0, 1142,
 1128,    0,    0,    0, -186,  368,    0, -168,    0,  150,
    0,    0,  375,  376,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30,  934,  377,  369, 1155,  578,  742,
  605,    0,  105,    0,  151,  164,  297,  125,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  389,    0,    0,
    0,  242,    0,    0,    0,    0, -150,    0,  170,    0,
    0, -240, 1101,    0, -138,    0,    0,    0,    0,  138,
  397,    0,    0,    0,  949,    0,  345,  388,  622,    0,
    0,    0, -116,    0,    0,  202,    0,    0, 1115,    0,
    0,  964,    0,    0,  280,    0, -115,    0,    0,    0,
  979,    0,  229,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  453,    0,    0,    0,    0,  693,    0,    0,
    0,    0,    0,    0,  480,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  507,
    0,    0,  534,    0,    0,  212,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  252,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  366,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -46,    0,  290,  328,    0,  -21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  399,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  889,  904,    0,    0,    0,
    0,    0,    0,    0,  919,    0,    0,    0,    0,    0,
    0,    0, 1090,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  426,    0,    0,    0,    0,  649,    0,
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
    0,    0,    0,    0,    0,  994, 1009,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  287,    0,    0, -173, -192, -184, -178,  -66,  650,
  528, -207,    0,  -17,   -3,    0,   22, -322, -292, -282,
 -259,  -26, -362, -123,    0,    0,  318,  -44,  -45,    0,
    0, -142,    4,   35,  514,  511,  407,    0, -198,    0,
    0,  553,
};
final static int YYTABLESIZE=1424;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
   82,   87,   27,  158,  112,  213,  383,  258,  154,   31,
  234,   68,   72,   74,   79,   76,   11,   12,  116,   56,
   33,  246,  362,    5,   92,  395,   77,   80,   48,  247,
  202,   51,   81,  159,  117,  248,  155,   34,  168,  208,
   32,  409,   93,   57,   69,  412,   58,   59,  131,  261,
  151,   57,  363,  268,   58,   59,  136,  421,  137,  138,
  362,  160,  364,  284,  282,  246,  169,  209,  331,  338,
  128,  152,  362,  247,  161,  299,   54,  262,  112,  248,
   82,  269,   88,  132,   35,  365,  362,  341,   52,  362,
  363,  202,  283,  153,   80,  181,  332,  339,  362,  157,
  364,   36,  363,  320,  321,  387,  246,   57,   47,   77,
   58,   59,  364,  213,  247,  342,  363,  393,   57,  363,
  248,   58,   59,  365,    1,  216,  364,   39,  363,  364,
  114,  349,  350,  388,  351,  365,  348,    2,  364,  407,
  419,  202,  329,   41,   40,  394,  217,  226,  227,  365,
  260,  229,  365,  191,  296,  200,  246,  246,  246,  259,
   42,  365,   27,  330,  247,  247,  247,  408,  420,   31,
  248,  248,  248,   14,   15,   27,  203,  202,  202,  123,
   27,   27,   31,   27,   27,   27,  399,   31,   31,   89,
   31,   31,   31,  334,  201,   43,  250,  124,   45,  125,
   32,  263,   46,  103,   37,  202,  246,  204,   70,  147,
  251,  147,   44,   32,  247,  265,  200,   38,   32,   32,
  248,   32,   32,   32,  322,   71,  290,  147,  323,  104,
  143,  147,   55,  115,  145,  253,  145,  203,  120,  308,
  250,  107,   11,   12,  289,   56,  291,  293,  144,  295,
  145,   73,  145,  118,  251,  201,  145,  100,  101,  108,
   77,   11,   12,   46,   56,  126,  200,   75,  204,   57,
  141,  119,   58,   59,   47,  121,  174,   11,   12,  253,
   56,  250,  177,   27,  190,  352,  127,  203,   57,  333,
   31,   58,   59,  122,  175,  251,  142,   48,   48,   77,
  178,  109,  200,  200,   57,  201,  146,   58,   59,  147,
  182,  380,  170,   94,  110,   14,   15,  224,  204,  111,
  253,   32,  148,  203,  203,  306,  353,  360,  183,  171,
  200,  250,  250,  250,  164,  225,   95,   96,   97,   98,
   99,  201,  201,  307,  310,  251,  251,  251,  135,  100,
  101,  203,  390,  162,  204,  204,  312,  314,   11,   12,
  376,   56,  311,  270,  391,  360,  361,   90,   91,  201,
  253,  253,  253,  288,  313,  315,  172,  360,  377,  273,
  271,  250,  204,   11,   12,   57,   56,  292,   58,   59,
  275,  360,  277,  396,  360,  251,  274,   11,   12,  165,
   56,  279,  167,  360,  361,  343,  378,  276,  294,  278,
   57,  397,   47,   58,   59,  129,  361,  130,   11,   12,
  253,   56,  344,  379,   57,  389,  180,   58,   59,   84,
  361,  100,  101,  361,  166,   11,   12,   45,   56,   85,
   86,   46,  361,   45,  149,   57,  150,  279,   58,   59,
  184,  185,   47,  187,  188,  189,   57,  410,   47,   58,
   59,  411,   57,   14,   15,   58,   59,  127,  127,  127,
  127,  127,  127,  127,  127,  127,  127,  127,  127,  127,
  127,  127,  127,  176,  424,  127,  215,  127,  425,  127,
  127,  127,  127,  127,  127,  235,  127,  384,  236,  237,
  385,  386,  127,  127,  127,  127,  127,  120,  120,  120,
  120,  120,  120,  120,  120,  120,  120,  120,  120,  120,
  120,  120,  120,  228,  324,  120,  325,  120,  233,  120,
  120,  120,  120,  120,  120,  416,  120,  207,  417,  418,
  105,  106,  120,  120,  120,  118,  118,  118,  118,  118,
  118,  118,  118,  118,  118,  118,  118,  118,  118,  118,
  118,  192,  193,  118,  327,  118,  328,  118,  118,  118,
  118,  118,  118,  230,  118,  110,   14,   15,  297,  298,
  118,  118,  118,  119,  119,  119,  119,  119,  119,  119,
  119,  119,  119,  119,  119,  119,  119,  119,  119,  232,
  402,  119,  403,  119,  238,  119,  119,  119,  119,  119,
  119,  264,  119,  133,  134,  139,  140,  280,  119,  119,
  119,  117,  117,  272,  117,  117,  117,  117,  117,  117,
  117,  117,  117,  117,  117,  117,  117,  285,  286,  117,
  302,  117,  303,  117,  309,  316,  317,  326,  346,  347,
  117,  340,  369,  368,  167,  167,  117,  167,  167,  167,
  167,  382,  167,  167,  167,  167,  167,  167,  167,  167,
  398,  404,  163,   53,  381,  287,  167,  156,    0,    0,
    0,   17,   17,  167,    0,    0,   17,   17,   17,  167,
   17,   17,   17,    0,   17,   17,   17,    0,    0,    0,
    0,   17,    0,   17,    0,    0,    0,    0,   17,   17,
   17,    0,    0,   17,   17,    0,   17,   17,   17,   17,
    0,   17,   17,   17,    0,    0,    0,    0,   17,    0,
   17,    0,    0,    0,    0,   15,   15,   17,    0,    0,
   15,   15,   15,   17,   15,   15,   15,    0,   15,   15,
   15,    0,    0,    0,    0,    0,    0,   15,    0,    0,
    0,    0,  107,  107,   15,    0,    0,  107,  107,  107,
   15,  107,  107,  107,    0,  107,  107,  107,    0,    0,
    0,    0,    0,    0,  107,    0,    0,    0,    0,   14,
   14,  107,    0,    0,   14,   14,   14,  107,   14,   14,
   14,    0,   14,   14,   14,    0,    0,    0,    0,    0,
    0,   14,    0,    0,    0,    0,  318,  239,   14,    0,
    0,    8,    9,  240,   14,  241,  242,  243,    0,  244,
   14,   15,    0,  371,  239,    0,    0,    0,    8,    9,
  240,    0,  241,  242,  243,  319,  244,   14,   15,    0,
    0,   17,    0,    0,    0,    0,    0,    0,    0,    0,
  374,  239,  372,    0,    0,    8,    9,  240,   17,  241,
  242,  243,    0,  244,   14,   15,    0,  405,  239,    0,
    0,    0,    8,    9,  240,    0,  241,  242,  243,  375,
  244,   14,   15,    0,    0,   17,    0,    0,    0,    0,
    0,    0,    0,    0,   32,   32,  406,    0,    0,   32,
   32,   32,   17,   32,   32,   32,    0,   32,   32,   32,
    0,  222,    7,    0,    0,    0,    8,    9,    0,    0,
   10,   11,   12,   32,   13,   14,   15,    0,    0,   32,
    0,    0,    0,    0,    0,    0,    0,    0,    6,    6,
  223,    0,    0,    6,    6,    0,   17,    6,    6,    6,
  239,    6,    6,    6,    8,    9,  240,    0,  241,  242,
  243,    0,  244,   14,   15,    0,    0,    6,    0,    0,
    0,    0,    0,    6,    0,    0,    0,  239,  245,    0,
    0,    8,    9,  240,   17,  241,  242,  243,  239,  244,
   14,   15,    8,    9,  240,    0,  241,  242,  243,    0,
  244,   14,   15,    0,    0,  281,    0,    0,    0,    0,
    0,   17,    0,    0,    0,    7,  373,    0,    0,    8,
    9,    0,   17,   10,   11,   12,    7,   13,   14,   15,
    8,    9,    0,    0,   10,   11,   12,    0,   13,   14,
   15,    0,    0,   16,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    7,  186,    0,    0,    8,    9,    0,
   17,   10,   11,   12,    7,   13,   14,   15,    8,    9,
    0,    0,   10,   11,   12,    0,   13,   14,   15,    0,
    0,  218,    0,    0,    0,    0,    0,   17,    0,    0,
    0,    7,  219,    0,    0,    8,    9,    0,   17,   10,
   11,   12,    7,   13,   14,   15,    8,    9,    0,    0,
   10,   11,   12,    0,   13,   14,   15,    0,    0,  220,
    0,    0,    0,    0,    0,   17,    0,    0,    0,    0,
  221,    0,    0,    0,  189,  189,   17,  189,  189,  189,
    0,    0,  189,  189,  189,  189,  189,  189,    0,  191,
  191,    0,  191,  191,  191,    0,  189,  191,  191,  191,
  191,  191,  191,  189,  181,  181,    0,  181,  181,  181,
    0,  191,  181,  181,  181,  181,  181,  181,  191,  354,
  355,    0,    0,    0,    8,    0,  240,  356,  241,  242,
  243,  357,  358,  181,  400,  355,    0,    0,    0,    8,
    0,  240,  356,  241,  242,  243,  357,  358,  359,  414,
  355,    0,    0,    0,    8,    0,  240,  356,  241,  242,
  243,  357,  358,  401,  422,  355,    0,    0,    0,    8,
    0,  240,  356,  241,  242,  243,  357,  358,  415,   89,
   89,    0,    0,    0,   89,    0,   89,   89,   89,   89,
   89,   89,   89,  423,  191,  191,    0,    0,    0,  191,
    0,  191,  191,  191,  191,  191,  191,  191,   89,  194,
  195,    0,    0,    0,    8,    0,    0,  196,   10,   11,
   12,  197,  198,  191,  266,  195,    0,    0,    0,    8,
    0,    0,  196,   10,   11,   12,  197,  198,  199,  300,
  195,    0,    0,    0,    8,    0,    0,  196,   10,   11,
   12,  197,  198,  267,  210,    7,    0,    0,    0,    8,
    0,  304,    7,   10,   11,   12,    8,  211,  301,    0,
   10,   11,   12,    0,  211,  155,  155,    0,    0,    0,
  155,    0,    0,  212,  155,  155,  155,  355,  155,    0,
  305,    8,    0,  240,  356,  241,  242,  243,  357,  358,
    0,  355,    0,    0,  155,    8,    0,  240,  356,  241,
  242,  243,  357,  358,  195,  392,    0,    0,    8,    0,
    0,  196,   10,   11,   12,  197,  198,    0,  195,  413,
  335,  336,    8,    0,    0,  196,   10,   11,   12,  197,
  198,  195,  337,    0,  370,    8,    0,    0,  196,   10,
   11,   12,  197,  198,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         17,
   45,   47,    6,  127,   71,  179,  369,  215,  256,    6,
  209,  256,   39,   40,  256,   42,  266,  267,  258,  269,
  256,  214,  345,  284,  256,  388,   44,  269,  269,  214,
  173,  278,  274,  256,  274,  214,  284,  273,  256,  256,
    6,  404,  274,  293,  289,  408,  296,  297,   94,  256,
  256,  293,  345,  256,  296,  297,  102,  420,  103,  104,
  383,  284,  345,  262,  256,  258,  284,  284,  256,  256,
   88,  277,  395,  258,  141,  283,  276,  284,  145,  258,
  125,  284,  276,  269,  256,  345,  409,  256,  269,  412,
  383,  234,  284,  120,  269,  162,  284,  284,  421,  126,
  383,  273,  395,  302,  303,  256,  299,  293,  288,  127,
  296,  297,  395,  287,  299,  284,  409,  256,  293,  412,
  299,  296,  297,  383,  256,  256,  409,  256,  421,  412,
  274,  339,  340,  284,  342,  395,  335,  269,  421,  256,
  256,  284,  256,  256,  273,  284,  277,  192,  193,  409,
  217,  196,  412,  171,  278,  173,  349,  350,  351,  256,
  273,  421,  166,  277,  349,  350,  351,  284,  284,  166,
  349,  350,  351,  270,  271,  179,  173,  320,  321,  256,
  184,  185,  179,  187,  188,  189,  394,  184,  185,  269,
  187,  188,  189,  317,  173,  256,  214,  274,  273,  276,
  166,  228,  277,  259,  256,  348,  399,  173,  256,  256,
  214,  258,  273,  179,  399,  233,  234,  269,  184,  185,
  399,  187,  188,  189,  256,  273,  272,  274,  260,  259,
  256,  278,  256,  274,  256,  214,  258,  234,  278,  274,
  258,  256,  266,  267,  271,  269,  273,  274,  274,  276,
  276,  256,  274,  256,  258,  234,  278,  292,  293,  274,
  278,  266,  267,  277,  269,  273,  284,  256,  234,  293,
  277,  274,  296,  297,  288,  256,  256,  266,  267,  258,
  269,  299,  256,  287,  256,  256,  273,  284,  293,  316,
  287,  296,  297,  274,  274,  299,  269,  269,  269,  317,
  274,  256,  320,  321,  293,  284,  258,  296,  297,  258,
  256,  356,  256,  256,  269,  270,  271,  256,  284,  274,
  299,  287,  284,  320,  321,  256,  344,  345,  274,  273,
  348,  349,  350,  351,  284,  274,  279,  280,  281,  282,
  283,  320,  321,  274,  256,  349,  350,  351,  256,  292,
  293,  348,  379,  277,  320,  321,  256,  256,  266,  267,
  256,  269,  274,  256,  382,  383,  345,  296,  297,  348,
  349,  350,  351,  256,  274,  274,  278,  395,  274,  256,
  273,  399,  348,  266,  267,  293,  269,  256,  296,  297,
  256,  409,  256,  256,  412,  399,  273,  266,  267,  284,
  269,  277,  284,  421,  383,  256,  256,  273,  256,  273,
  293,  274,  288,  296,  297,  256,  395,  258,  266,  267,
  399,  269,  273,  273,  293,  256,  284,  296,  297,  256,
  409,  292,  293,  412,  148,  266,  267,  273,  269,  266,
  267,  277,  421,  273,  256,  293,  258,  277,  296,  297,
  164,  165,  288,  167,  168,  169,  293,  256,  288,  296,
  297,  260,  293,  270,  271,  296,  297,  256,  257,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  274,  256,  274,  284,  276,  260,  278,
  279,  280,  281,  282,  283,  256,  285,  256,  259,  260,
  259,  260,  291,  292,  293,  294,  295,  256,  257,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  273,  256,  274,  258,  276,  273,  278,
  279,  280,  281,  282,  283,  256,  285,  277,  259,  260,
  294,  295,  291,  292,  293,  256,  257,  258,  259,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  292,  293,  274,  256,  276,  258,  278,  279,  280,
  281,  282,  283,  277,  285,  269,  270,  271,  266,  267,
  291,  292,  293,  256,  257,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  278,
  256,  274,  258,  276,  278,  278,  279,  280,  281,  282,
  283,  269,  285,  100,  101,  105,  106,  278,  291,  292,
  293,  256,  257,  273,  259,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  274,  274,  274,
  258,  276,  284,  278,  274,  273,  273,  258,  274,  274,
  285,  284,  284,  277,  256,  257,  291,  259,  260,  261,
  262,  273,  264,  265,  266,  267,  268,  269,  270,  271,
  274,  284,  145,   24,  357,  269,  278,  125,   -1,   -1,
   -1,  256,  257,  285,   -1,   -1,  261,  262,  263,  291,
  265,  266,  267,   -1,  269,  270,  271,   -1,   -1,   -1,
   -1,  276,   -1,  278,   -1,   -1,   -1,   -1,  256,  257,
  285,   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,
   -1,  269,  270,  271,   -1,   -1,   -1,   -1,  276,   -1,
  278,   -1,   -1,   -1,   -1,  256,  257,  285,   -1,   -1,
  261,  262,  263,  291,  265,  266,  267,   -1,  269,  270,
  271,   -1,   -1,   -1,   -1,   -1,   -1,  278,   -1,   -1,
   -1,   -1,  256,  257,  285,   -1,   -1,  261,  262,  263,
  291,  265,  266,  267,   -1,  269,  270,  271,   -1,   -1,
   -1,   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,  256,
  257,  285,   -1,   -1,  261,  262,  263,  291,  265,  266,
  267,   -1,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,
   -1,  278,   -1,   -1,   -1,   -1,  256,  257,  285,   -1,
   -1,  261,  262,  263,  291,  265,  266,  267,   -1,  269,
  270,  271,   -1,  256,  257,   -1,   -1,   -1,  261,  262,
  263,   -1,  265,  266,  267,  285,  269,  270,  271,   -1,
   -1,  291,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,  285,   -1,   -1,  261,  262,  263,  291,  265,
  266,  267,   -1,  269,  270,  271,   -1,  256,  257,   -1,
   -1,   -1,  261,  262,  263,   -1,  265,  266,  267,  285,
  269,  270,  271,   -1,   -1,  291,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  285,   -1,   -1,  261,
  262,  263,  291,  265,  266,  267,   -1,  269,  270,  271,
   -1,  256,  257,   -1,   -1,   -1,  261,  262,   -1,   -1,
  265,  266,  267,  285,  269,  270,  271,   -1,   -1,  291,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,
  285,   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,
  257,  269,  270,  271,  261,  262,  263,   -1,  265,  266,
  267,   -1,  269,  270,  271,   -1,   -1,  285,   -1,   -1,
   -1,   -1,   -1,  291,   -1,   -1,   -1,  257,  285,   -1,
   -1,  261,  262,  263,  291,  265,  266,  267,  257,  269,
  270,  271,  261,  262,  263,   -1,  265,  266,  267,   -1,
  269,  270,  271,   -1,   -1,  285,   -1,   -1,   -1,   -1,
   -1,  291,   -1,   -1,   -1,  257,  285,   -1,   -1,  261,
  262,   -1,  291,  265,  266,  267,  257,  269,  270,  271,
  261,  262,   -1,   -1,  265,  266,  267,   -1,  269,  270,
  271,   -1,   -1,  285,   -1,   -1,   -1,   -1,   -1,  291,
   -1,   -1,   -1,  257,  285,   -1,   -1,  261,  262,   -1,
  291,  265,  266,  267,  257,  269,  270,  271,  261,  262,
   -1,   -1,  265,  266,  267,   -1,  269,  270,  271,   -1,
   -1,  285,   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,
   -1,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,  257,  269,  270,  271,  261,  262,   -1,   -1,
  265,  266,  267,   -1,  269,  270,  271,   -1,   -1,  285,
   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,   -1,   -1,
  285,   -1,   -1,   -1,  256,  257,  291,  259,  260,  261,
   -1,   -1,  264,  265,  266,  267,  268,  269,   -1,  256,
  257,   -1,  259,  260,  261,   -1,  278,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,  259,  260,  261,
   -1,  278,  264,  265,  266,  267,  268,  269,  285,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  285,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  285,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  285,  256,
  257,   -1,   -1,   -1,  261,   -1,   -1,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,
   -1,   -1,  264,  265,  266,  267,  268,  269,  285,  256,
  257,   -1,   -1,   -1,  261,   -1,   -1,  264,  265,  266,
  267,  268,  269,  285,  256,  257,   -1,   -1,   -1,  261,
   -1,  256,  257,  265,  266,  267,  261,  269,  285,   -1,
  265,  266,  267,   -1,  269,  256,  257,   -1,   -1,   -1,
  261,   -1,   -1,  285,  265,  266,  267,  257,  269,   -1,
  285,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
   -1,  257,   -1,   -1,  285,  261,   -1,  263,  264,  265,
  266,  267,  268,  269,  257,  285,   -1,   -1,  261,   -1,
   -1,  264,  265,  266,  267,  268,  269,   -1,  257,  285,
  259,  260,  261,   -1,   -1,  264,  265,  266,  267,  268,
  269,  257,  285,   -1,  260,  261,   -1,   -1,  264,  265,
  266,  267,  268,  269,
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
"sentencia_decl_datos : list_var",
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
"parametro : tipo ID",
"parametro : ID",
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
//#line 894 "Parser.java"
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
{yyerror("Se esperaba )");}
break;
case 28:
//#line 51 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 29:
//#line 52 "gramatica.y"
{yyerror("Se esperaba un nombre de funcion");}
break;
case 32:
//#line 56 "gramatica.y"
{System.out.println("Se esperaba ;");}
break;
case 44:
//#line 70 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 45:
//#line 71 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 46:
//#line 72 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 47:
//#line 73 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 48:
//#line 74 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 49:
//#line 75 "gramatica.y"
{yyerror("Se esperaba end_if ");}
break;
case 50:
//#line 76 "gramatica.y"
{yyerror("Se esperaba } ");}
break;
case 51:
//#line 77 "gramatica.y"
{yyerror("Se esperaba { ");}
break;
case 52:
//#line 78 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 53:
//#line 79 "gramatica.y"
{yyerror("Se esperaba ) ");}
break;
case 54:
//#line 80 "gramatica.y"
{yyerror("Se esperaba una condicion ");}
break;
case 55:
//#line 81 "gramatica.y"
{yyerror("Se esperaba ( ");}
break;
case 56:
//#line 83 "gramatica.y"
{System.out.println("Sentencia WHEN");}
break;
case 57:
//#line 84 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 58:
//#line 85 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 59:
//#line 86 "gramatica.y"
{yyerror("Se esperaba then ");}
break;
case 60:
//#line 87 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 61:
//#line 88 "gramatica.y"
{yyerror("Se esperaba condicion");}
break;
case 62:
//#line 89 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 63:
//#line 91 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 64:
//#line 92 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 65:
//#line 93 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 66:
//#line 94 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 67:
//#line 95 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 68:
//#line 96 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 69:
//#line 97 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 70:
//#line 98 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 71:
//#line 99 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 72:
//#line 100 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 73:
//#line 101 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 74:
//#line 103 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 75:
//#line 104 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 76:
//#line 105 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 77:
//#line 106 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 78:
//#line 107 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 79:
//#line 108 "gramatica.y"
{yyerror("Se esperaba (");}
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
//#line 1535 "Parser.java"
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
