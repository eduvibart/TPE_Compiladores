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
    4,    1,    0,    2,    2,    2,    1,    1,    1,    1,
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
    0,    0,    0,    1,    0,    4,    0,    0,    8,    9,
   10,   73,   75,   74,   76,   77,   78,    0,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    0,   72,    0,
    5,    6,    0,   91,   92,    0,    0,   90,    0,    0,
    0,    0,   83,   88,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   93,
   94,    0,   98,   99,  100,  101,  102,    0,    0,    0,
    0,    0,    0,    0,  105,   69,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,   71,
  129,  132,  128,    0,    0,   89,    0,    0,    0,   84,
   85,   86,   87,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  124,    0,    0,  131,
    0,  103,   35,    0,   35,   35,    0,    0,    0,    0,
   35,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,   35,    0,    0,   35,    0,   35,    0,    0,
   35,    0,   35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  111,  113,  114,  116,  115,    0,  112,    0,
  125,    0,  130,    0,  104,    0,    0,    0,    0,    0,
    0,   19,   37,   38,   39,    0,   40,   41,   42,   43,
   44,   45,   46,    0,   23,   27,    0,   35,    0,    0,
    0,    0,    0,   32,    0,    0,  106,    0,  126,  127,
    0,  119,    0,  117,  110,    0,  133,    0,   96,    0,
    0,    0,    0,    0,    0,   36,   16,   18,    0,   22,
   35,    0,   35,   35,   26,   31,   35,  107,    0,  121,
    0,  103,    0,    0,    0,    0,    0,    0,    0,   15,
    0,   35,    0,    0,    0,    0,    0,    0,    0,   68,
    0,    0,    0,    0,    0,   17,    0,   21,   25,   30,
    0,  108,    0,    0,    0,    0,   54,    0,    0,   14,
    0,   95,   35,   35,    0,    0,    0,    0,    0,  123,
    0,    0,    0,    0,    0,    0,    0,   52,   56,   58,
   59,   61,   60,   65,   55,   57,    0,   54,    0,    0,
   49,    0,    0,   64,   62,    0,    0,  122,    0,   48,
   54,    0,    0,   53,   35,    0,    0,    0,    0,   50,
    0,   54,    0,   54,    0,   47,    0,   51,    0,    0,
   67,   54,    0,    0,   66,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   17,   18,  193,  194,  195,   37,   59,
  152,  196,  197,  198,  199,  200,  201,  202,  203,   47,
  296,   63,  315,  316,  224,   48,   49,   40,   24,   25,
   50,   51,   52,   53,   54,  151,   80,  127,  178,  179,
  104,
};
final static short yysindex[] = {                      -212,
    0,    0, -205,    0, -132,    0, -166, -155, -180, -152,
 -135, -129, -239,    0, -113,    0,  -93,  -81,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -157, -112, -142,
 -157, -157, -113,    0, -165, -157,  -76,  -83,    0,  -74,
    0,    0, -218,    0,    0, -106,  -63,    0,  579,  -44,
  -40, -101,    0,    0,  -41,  -34, -178,  -52, -125,  -30,
  -28,  -38,  -15,  -11,   -1, -123,    4, -113,  -87,    0,
    0,   10,    0,    0,    0,    0,    0, -154, -154, -157,
 -248, -248, -154, -154,    0,    0, -243,    5,  -86,    8,
   12,    9,   17,   26, -157,   -3, -157, -113,    0,    0,
    0,    0,    0,  -96,   21,    0, -101, -101, -123,    0,
    0,    0,    0,   22, -238,   23, -158,    9,   27,   24,
   40,   36,   31,   44,   41,   -3,    0,   53,   55,    0,
  -95,    0,    0,   46,    0,    0,   49, -226, -250,   50,
    0,   51,   61,    0, -113,  -97,  612,   62,   -3, -220,
  -18,  -35,    0,  188,  199,    0,   52,    0, -114,   76,
    0,  220,    0,   78,  -49,   77, -248, -248,   86, -248,
   83, -227,    0,    0,    0,    0,    0,   -6,    0,   88,
    0,   90,    0,  -50,    0,   93,  100,  102,  103,  104,
 -194,    0,    0,    0,    0,   84,    0,    0,    0,    0,
    0,    0,    0,  231,    0,    0,  252,    0,  263,   94,
 -184,   96,  284,    0,  295,  108,    0,   -3,    0,    0,
 -157,    0,  115,    0,    0, -113,    0,  109,    0, -157,
 -157, -157, -157, -113,  -43,    0,    0,    0,  316,    0,
    0,  110,    0,    0,    0,    0,    0,    0,  121,    0,
  123,    0,  125, -241,  132,  134,  135,  137,  138,    0,
  327,    0,  348,  359,  380,  159,   -3,  585,  167,    0,
  168,  136,  143, -157, -113,    0,  391,    0,    0,    0,
   -3,    0,  170,  144,  148,  166,    0,  169,  172,    0,
  -22,    0,    0,    0, -113,  481,  164,  158,   -3,    0,
  412,  423,  178,  171, -248,   83, -200,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  185,    0,  203,   -7,
    0,  175, -157,    0,    0, -113,  494,    0,  183,    0,
    0,  195,  196,    0,    0,  507,  213,  190,  444,    0,
  191,    0,  212,    0,  520,    0,  533,    0,   -2,  192,
    0,    0,  546,  218,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  202,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  217,
    0,    0,    0,    0,    0,    0,  221,    0,    0,  223,
    0,    0,   19,    0,    0,    0,    0,    0,    0,    0,
    0,   89,    0,    0,    0,    0,  224,    0,  225,    0,
    0,    0,    0,    0,    0,  458,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  122,  155, -116,    0,
    0,    0,    0,  226,  228,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  625,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  206,
  222,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  248,    0,
    0,    0,    0,    0,  559,  572,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  366,    0,    0, -140,    2,   14,   16,    0,  -45,
 -121,    0,   -5,    0,   -4, -283, -271, -267, -255,  -29,
 -247,  -89,    0,    0,  227,  -64,  -32,    0,    0,  365,
    1,    3,  176,    0,  177,  271,    0, -110,    0,    0,
  396,
};
final static int YYTABLESIZE=894;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
   23,   60,   61,   66,  103,   26,   19,   27,  129,   39,
  185,   89,  311,  154,  155,  147,  110,  111,   20,  162,
   21,   44,   45,  159,  312,  114,  160,   62,  313,   34,
  134,  204,  270,  115,  207,  182,  209,   35,  181,  213,
  314,  215,  157,  311,   46,  135,  122,  109,   36,   35,
   78,   79,  311,  183,   69,  312,    1,  158,   35,  313,
   36,  311,  100,  311,  312,  125,  103,  128,  313,  311,
  327,  314,  139,  312,   34,  312,  235,  313,    4,  313,
  314,  312,  235,  336,  242,  313,  239,   36,   30,  314,
   56,  314,   62,   36,  345,   87,  347,  314,   88,  243,
   64,   65,  219,  220,  353,  222,   28,  248,   11,   12,
  137,   43,   44,   45,  106,   44,   45,   29,  138,  261,
   31,  263,  264,    6,    7,  265,   56,  185,    8,    9,
   57,   58,   10,   11,   12,   46,   13,   32,   46,  166,
  277,  173,  174,   33,  257,   22,   23,  176,   91,  177,
   92,   26,   14,   27,  210,   38,  282,   97,   15,   22,
   23,   97,  211,   70,   71,   26,   19,   27,   78,   79,
  291,  301,  302,  101,   44,   45,   55,  130,   20,  131,
   21,  101,   44,   45,   41,  289,  102,  117,  319,  118,
  119,  249,   83,   84,  167,  168,   42,   46,  254,   67,
  253,   68,  255,  256,   36,   46,    6,    7,  228,  229,
   72,    8,    9,  339,   81,   10,   11,   12,   82,   13,
  251,  186,  258,  259,   90,    8,    9,  187,   62,  188,
  189,  190,   85,  191,   86,  217,  299,  300,    7,   95,
  324,   15,    8,   93,  288,   94,   10,   11,   12,  192,
  172,  329,  330,  107,  108,   15,  350,  351,   96,  112,
  113,   97,   22,   23,  109,  109,  184,  105,   26,   62,
   27,   98,   99,  116,  123,   89,  120,   56,  225,   89,
  126,   89,   89,   89,   89,   89,   89,   89,  121,  303,
  309,  310,   89,  332,   89,  140,   89,   89,   89,   89,
   89,   89,  124,   89,  132,  133,  136,  141,  142,  143,
   89,   89,   89,   89,  144,  109,  145,  109,  146,  109,
  333,  309,  310,  109,  109,  109,  148,  109,  149,  153,
  309,  310,  156,  161,  163,  208,  109,  164,  180,  309,
  310,  309,  310,  109,  212,   82,  216,  309,  310,   82,
  218,   82,   82,   82,   82,   82,   82,   82,  221,  223,
  226,  236,   82,  227,   82,  230,   82,   82,   82,   82,
   82,   82,  231,   82,  232,  233,  234,  241,   80,  244,
   82,   82,   80,  250,   80,   80,   80,   80,   80,   80,
   80,  247,  252,  262,  266,   80,  267,   80,  269,   80,
   80,   80,   80,   80,   80,  271,   80,  272,  273,  274,
  275,   81,  286,   80,   80,   81,  281,   81,   81,   81,
   81,   81,   81,   81,  284,  285,  287,  293,   81,  292,
   81,  294,   81,   81,   81,   81,   81,   81,  295,   81,
  317,  318,  297,  323,  186,  298,   81,   81,    8,    9,
  187,  322,  188,  189,  190,  186,  191,  326,  331,    8,
    9,  187,  328,  188,  189,  190,  335,  191,  337,  338,
  341,  346,  205,  342,  344,  352,  186,  355,   15,   34,
    8,    9,  187,  206,  188,  189,  190,  186,  191,   15,
  118,    8,    9,  187,   33,  188,  189,  190,   11,  191,
   70,   28,   29,   20,  214,   24,  120,  109,  186,  165,
   15,  175,    8,    9,  187,  237,  188,  189,  190,  186,
  191,   15,  268,    8,    9,  187,  150,  188,  189,  190,
    0,  191,  325,    0,    0,    0,  238,    0,    0,    0,
  186,    0,   15,    0,    8,    9,  187,  240,  188,  189,
  190,  186,  191,   15,    0,    8,    9,  187,    0,  188,
  189,  190,    0,  191,    0,    0,    0,    0,  245,    0,
    0,    0,  186,    0,   15,    0,    8,    9,  187,  246,
  188,  189,  190,  186,  191,   15,    0,    8,    9,  187,
    0,  188,  189,  190,    0,  191,    0,    0,    0,    0,
  260,    0,    0,    0,  186,    0,   15,    0,    8,    9,
  187,  276,  188,  189,  190,  186,  191,   15,    0,    8,
    9,  187,    0,  188,  189,  190,    0,  191,    0,    0,
    0,    0,  278,    0,    0,    0,  186,    0,   15,    0,
    8,    9,  187,  279,  188,  189,  190,  186,  191,   15,
    0,    8,    9,  187,    0,  188,  189,  190,    0,  191,
    0,    0,    0,    0,  280,    0,    0,    0,  186,    0,
   15,    0,    8,    9,  187,  290,  188,  189,  190,  186,
  191,   15,    0,    8,    9,  187,    0,  188,  189,  190,
    0,  191,    0,    0,    0,    0,  320,    0,    0,    0,
  186,    0,   15,    0,    8,    9,  187,  321,  188,  189,
  190,    0,  191,   15,   79,    0,    0,    0,   79,    0,
   79,   79,   79,   79,   79,   79,   79,    0,  343,    0,
    0,   79,    0,   79,   15,   79,    0,  304,    0,    0,
    0,    8,   79,  187,  305,  188,  189,  190,  306,  307,
  304,    0,    0,    0,    8,    0,  187,  305,  188,  189,
  190,  306,  307,  304,    0,  308,    0,    8,    0,  187,
  305,  188,  189,  190,  306,  307,  304,    0,  334,    0,
    8,    0,  187,  305,  188,  189,  190,  306,  307,  304,
    0,  340,    0,    8,    0,  187,  305,  188,  189,  190,
  306,  307,  304,    0,  348,    0,    8,    0,  187,  305,
  188,  189,  190,  306,  307,   63,    0,  349,    0,   63,
    0,   63,   63,   63,   63,   63,   63,   63,  120,    0,
  354,    0,  120,    0,  120,  120,  120,  120,  120,  120,
  120,    7,    0,   63,    0,    8,    0,    0,    0,   10,
   11,   12,    0,  172,    0,    0,  120,   73,   74,   75,
   76,   77,    0,    0,    0,    0,    0,    0,  169,  283,
   78,   79,    8,    0,    0,  170,   10,   11,   12,  171,
  172,  109,    0,    0,    0,  109,    0,    0,  109,  109,
  109,  109,  109,  109,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    5,   31,   32,   36,   69,    5,    5,    5,   98,   15,
  151,   57,  296,  135,  136,  126,   81,   82,    5,  141,
    5,  270,  271,  274,  296,  269,  277,   33,  296,  269,
  269,  153,  274,  277,  156,  256,  158,  277,  149,  161,
  296,  163,  269,  327,  293,  284,   92,   80,  288,  277,
  292,  293,  336,  274,  273,  327,  269,  284,  277,  327,
  288,  345,   68,  347,  336,   95,  131,   97,  336,  353,
  318,  327,  118,  345,  269,  347,  277,  345,  284,  347,
  336,  353,  277,  331,  269,  353,  208,  288,  269,  345,
  269,  347,   98,  288,  342,  274,  344,  353,  277,  284,
  266,  267,  167,  168,  352,  170,  273,  218,  266,  267,
  269,  269,  270,  271,  269,  270,  271,  273,  277,  241,
  273,  243,  244,  256,  257,  247,  269,  268,  261,  262,
  273,  274,  265,  266,  267,  293,  269,  273,  293,  145,
  262,  147,  147,  273,  234,  151,  151,  147,  274,  147,
  276,  151,  285,  151,  269,  269,  267,  274,  291,  165,
  165,  278,  277,  270,  271,  165,  165,  165,  292,  293,
  281,  293,  294,  269,  270,  271,  289,  274,  165,  276,
  165,  269,  270,  271,  278,  275,  274,  274,  299,  276,
  277,  221,  294,  295,  292,  293,  278,  293,  231,  276,
  230,  276,  232,  233,  288,  293,  256,  257,  259,  260,
  274,  261,  262,  335,  259,  265,  266,  267,  259,  269,
  226,  257,  266,  267,  277,  261,  262,  263,  234,  265,
  266,  267,  274,  269,  269,  285,  259,  260,  257,  278,
  305,  291,  261,  274,  274,  274,  265,  266,  267,  285,
  269,  259,  260,   78,   79,  291,  259,  260,  274,   83,
   84,  273,  268,  268,  259,  260,  285,  258,  268,  275,
  268,  273,  269,  269,  258,  257,  269,  269,  285,  261,
  284,  263,  264,  265,  266,  267,  268,  269,  277,  295,
  296,  296,  274,  323,  276,  269,  278,  279,  280,  281,
  282,  283,  277,  285,  284,  284,  284,  284,  269,  274,
  292,  293,  294,  295,  284,  257,  273,  259,  278,  261,
  326,  327,  327,  265,  266,  267,  274,  269,  274,  284,
  336,  336,  284,  284,  284,  284,  278,  277,  277,  345,
  345,  347,  347,  285,  269,  257,  269,  353,  353,  261,
  274,  263,  264,  265,  266,  267,  268,  269,  273,  277,
  273,  278,  274,  274,  276,  273,  278,  279,  280,  281,
  282,  283,  273,  285,  273,  273,  273,  284,  257,  284,
  292,  293,  261,  269,  263,  264,  265,  266,  267,  268,
  269,  284,  284,  284,  274,  274,  274,  276,  274,  278,
  279,  280,  281,  282,  283,  274,  285,  274,  274,  273,
  273,  257,  277,  292,  293,  261,  258,  263,  264,  265,
  266,  267,  268,  269,  258,  258,  284,  284,  274,  260,
  276,  284,  278,  279,  280,  281,  282,  283,  273,  285,
  277,  284,  274,  273,  257,  274,  292,  293,  261,  262,
  263,  274,  265,  266,  267,  257,  269,  273,  284,  261,
  262,  263,  260,  265,  266,  267,  284,  269,  274,  274,
  258,  260,  285,  284,  284,  284,  257,  260,  291,  278,
  261,  262,  263,  285,  265,  266,  267,  257,  269,  291,
  285,  261,  262,  263,  278,  265,  266,  267,  278,  269,
  278,  278,  278,  278,  285,  278,  285,  260,  257,  144,
  291,  147,  261,  262,  263,  285,  265,  266,  267,  257,
  269,  291,  252,  261,  262,  263,  131,  265,  266,  267,
   -1,  269,  306,   -1,   -1,   -1,  285,   -1,   -1,   -1,
  257,   -1,  291,   -1,  261,  262,  263,  285,  265,  266,
  267,  257,  269,  291,   -1,  261,  262,  263,   -1,  265,
  266,  267,   -1,  269,   -1,   -1,   -1,   -1,  285,   -1,
   -1,   -1,  257,   -1,  291,   -1,  261,  262,  263,  285,
  265,  266,  267,  257,  269,  291,   -1,  261,  262,  263,
   -1,  265,  266,  267,   -1,  269,   -1,   -1,   -1,   -1,
  285,   -1,   -1,   -1,  257,   -1,  291,   -1,  261,  262,
  263,  285,  265,  266,  267,  257,  269,  291,   -1,  261,
  262,  263,   -1,  265,  266,  267,   -1,  269,   -1,   -1,
   -1,   -1,  285,   -1,   -1,   -1,  257,   -1,  291,   -1,
  261,  262,  263,  285,  265,  266,  267,  257,  269,  291,
   -1,  261,  262,  263,   -1,  265,  266,  267,   -1,  269,
   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,  257,   -1,
  291,   -1,  261,  262,  263,  285,  265,  266,  267,  257,
  269,  291,   -1,  261,  262,  263,   -1,  265,  266,  267,
   -1,  269,   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,
  257,   -1,  291,   -1,  261,  262,  263,  285,  265,  266,
  267,   -1,  269,  291,  257,   -1,   -1,   -1,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,   -1,  285,   -1,
   -1,  274,   -1,  276,  291,  278,   -1,  257,   -1,   -1,
   -1,  261,  285,  263,  264,  265,  266,  267,  268,  269,
  257,   -1,   -1,   -1,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,  257,   -1,  285,   -1,  261,   -1,  263,
  264,  265,  266,  267,  268,  269,  257,   -1,  285,   -1,
  261,   -1,  263,  264,  265,  266,  267,  268,  269,  257,
   -1,  285,   -1,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,  257,   -1,  285,   -1,  261,   -1,  263,  264,
  265,  266,  267,  268,  269,  257,   -1,  285,   -1,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  257,   -1,
  285,   -1,  261,   -1,  263,  264,  265,  266,  267,  268,
  269,  257,   -1,  285,   -1,  261,   -1,   -1,   -1,  265,
  266,  267,   -1,  269,   -1,   -1,  285,  279,  280,  281,
  282,  283,   -1,   -1,   -1,   -1,   -1,   -1,  257,  285,
  292,  293,  261,   -1,   -1,  264,  265,  266,  267,  268,
  269,  257,   -1,   -1,   -1,  261,   -1,   -1,  264,  265,
  266,  267,  268,  269,
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
"bloque_sentencias : bloque_sentencias sentencia",
"sentencia : sentencia_declarativa PUNTOCOMA",
"sentencia : sentencia_ejecutable PUNTOCOMA",
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
  System.out.println("S: " + s);
  System.out.println("sval: " + sval);
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
//#line 670 "Parser.java"
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
{System.out.println("Se esperaba ;");}
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
//#line 1005 "Parser.java"
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
