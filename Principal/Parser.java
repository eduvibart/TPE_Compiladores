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
   16,   16,   31,   31,   31,   31,   31,   31,   33,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   33,   38,
   38,   39,   39,   39,   39,   39,   39,   39,   39,   39,
   26,   26,   40,   40,   32,   32,   32,   32,   32,   32,
   23,   23,   41,   41,   14,   14,   14,   14,   14,   14,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    2,    1,    1,    1,    1,    1,
    2,    3,    1,   12,   10,    9,   12,   10,    9,    8,
    7,    5,    4,    3,    2,    0,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,   13,
    9,   13,   12,   10,    9,    8,    6,    5,    4,    3,
    2,    8,    8,    6,    5,    4,    3,    8,   13,   11,
   11,    9,    8,    7,    6,    5,    4,    3,    2,    9,
    7,    7,    5,    4,    2,    0,    2,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    1,   13,    9,   13,
   12,   10,    9,    8,    6,    5,    4,    3,    2,    4,
    2,    2,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    1,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    1,    2,    2,   13,    9,   13,
   12,   10,    9,    8,    6,    5,    4,    3,    2,    3,
    1,    1,    1,    1,    1,    0,    3,    2,    4,    4,
    3,    2,    8,    8,    8,    8,    8,    7,   13,   11,
   11,    9,    8,    7,    6,    5,    4,    3,    2,    0,
    4,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    0,    2,    9,    7,    9,    7,    7,    5,    4,    2,
    6,    6,    1,    1,    6,    4,    3,    6,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    6,    7,    8,    9,   10,
  105,  111,  107,  106,  108,  109,  110,  139,    0,  152,
    0,   25,    0,    0,    0,  169,    0,  190,    0,   13,
    0,    0,    0,    0,    0,  104,    0,    4,  138,    0,
  124,  125,    0,  116,    0,  123,    0,    0,    0,    0,
  121,  151,    0,   24,    0,    0,    0,    0,  168,    0,
    0,    0,  200,  194,  197,  193,    0,    0,    0,    0,
    0,    0,  126,  127,  137,    0,  141,  142,  143,  144,
  145,    0,    0,    0,    0,    0,    0,    0,  150,  149,
   23,    0,    0,    0,    0,    0,    0,    0,  167,    0,
    0,  189,    0,  199,  196,    0,    0,    0,   12,  103,
  136,    0,  122,    0,    0,    0,  117,  118,  119,  120,
  101,    0,   22,    0,    0,    0,    0,    3,    0,    0,
  166,    0,    0,  188,    0,    0,    0,    0,  135,  146,
    0,    0,    0,    3,    3,    0,    3,    3,    3,  165,
    0,    0,    0,    0,  198,  195,    0,    0,    0,   26,
    0,   21,    0,    0,    0,  158,    0,    0,    0,  164,
    0,    0,    0,    0,  187,  186,    0,    0,  134,    0,
    0,    0,    0,   26,   20,    0,  157,  156,  155,  154,
  153,  163,    0,  191,  192,    0,    0,    0,    0,  172,
  174,  175,  177,  176,    0,  173,    0,    0,  133,    0,
  129,  147,    0,    0,    0,    0,    0,    0,   16,   29,
   30,   31,    0,   32,   33,   34,   35,   36,   37,   38,
   39,    0,   19,    0,  162,    0,    0,  180,    0,  178,
  171,    0,  185,  132,  146,   51,    0,    0,    0,    0,
   69,    0,   75,    0,    0,   27,   15,   18,   26,    0,
    0,  182,    0,    0,   50,    0,    0,    0,   57,    0,
   68,    0,    0,    0,    0,    0,  161,  160,    0,    0,
  131,    0,   49,    0,  100,    0,   56,    0,   67,    0,
   74,    0,    0,    0,   17,   14,    0,    0,  130,  128,
   48,    0,    0,   55,    0,   66,    0,   73,   76,    0,
    0,    0,  159,   47,   26,   26,   54,   26,   65,    0,
    0,    0,    0,    0,  184,    0,    0,    0,   64,    0,
   72,    0,    0,    0,    0,   71,   78,   80,   81,   83,
   82,   87,   77,   79,    0,   76,    0,   46,    0,   58,
   53,   52,   63,    0,   99,    0,   86,   84,    0,    0,
  183,   45,    0,   41,   62,   76,   98,    0,    0,   70,
   44,   26,    0,   97,    0,    0,    0,   61,   60,   96,
    0,   76,   43,    0,   95,   76,    0,   42,   40,    0,
   59,   94,    0,   93,    0,   89,   92,   76,    0,   91,
    0,   90,   88,
};
final static short yydgoto[] = {                          2,
    3,    5,   15,   16,   17,   18,   19,   20,   44,  104,
  193,  233,   21,   54,  236,   23,  238,  239,  240,  241,
   55,  331,   72,  353,  354,  250,   56,   57,   47,   24,
   25,   58,   59,   60,   61,  169,   94,  164,  215,  216,
   77,
};
final static short yysindex[] = {                      -260,
    0,    0, -231,    0,  428, -108,  -63, -178,  -61,  -47,
  -20,  301,    0, -189, -254,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -233,    0,
 -245,    0,   12,   60,  -15,    0,   -9,    0, -189,    0,
 -228,   26,   60, -103, -195,    0,  -96,    0,    0,  209,
    0,    0,   48,    0, -120,    0,  709, -114,  -71,  167,
    0,    0, -105,    0, -234,  -52,   38, -243,    0,  -51,
   54,  -25,    0,    0,    0,    0,  -72,   85,  121,  178,
  143, -189,    0,    0,    0, -164,    0,    0,    0,    0,
    0,  -26,  -26,   60,   -5,   -5,  -26,  -26,    0,    0,
    0,  163,  142,   33,  223,  225,  161,  -10,    0, -194,
   60,    0, -230,    0,    0,    5,   60, -189,    0,    0,
    0, -227,    0,  167,  167,  178,    0,    0,    0,    0,
    0,  194,    0,  230,  244,  237,  247,    0,  264, -217,
    0,   14,  278,    0,  293,    7,  307,  308,    0,    0,
  299,  315,   64,    0,    0,  441,    0,    0,    0,    0,
 -131,  205,  293, -240,    0,    0,  285,  304,  658,    0,
  313,    0, -177,  455,  468,    0,  482,  495,  509,    0,
   69,   -5,   -5,  742,    0,    0,  328,  293,    0,  171,
  316,  324,  387,    0,    0, -117,    0,    0,    0,    0,
    0,    0, -207,    0,    0,  330,   -5,  334,  133,    0,
    0,    0,    0,    0,  322,    0, -189,  339,    0, -183,
    0,    0,   24,  340,  134,  150,  152,  369,    0,    0,
    0,    0,  354,    0,    0,    0,    0,    0,    0,    0,
    0,  401,    0, -182,    0,  293,   60,    0,  364,    0,
    0,  373,    0,    0,    0,    0,   35,   60,   60,   44,
    0,  239,    0, -189,  233,    0,    0,    0,    0, -238,
  381,    0,  376,  672,    0,   86,  -58,  395,    0,   98,
    0,  111,  114,  388,  400, -135,    0,    0,  416,  293,
    0,  228,    0,  123,    0,  424,    0,  124,    0, -142,
    0, -172,   60, -189,    0,    0,  293,  399,    0,    0,
    0, -161,  403,    0, -151,    0,  177,    0,    0,  417,
  422,  260,    0,    0,    0,    0,    0,    0,    0,   52,
  553,  411,  420,  293,    0,  329,  414,  343,    0,  115,
    0,  187,   -5,  334,  169,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  427,    0,  451,    0,  359,    0,
    0,    0,    0, -147,    0,  245,    0,    0, -189,  702,
    0,    0, -143,    0,    0,    0,    0,  131,  435,    0,
    0,    0,  567,    0,  166,  430,  360,    0,    0,    0,
 -140,    0,    0,  280,    0,    0,  715,    0,    0,  590,
    0,    0,  536,    0, -138,    0,    0,    0,  604,    0,
  287,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  522,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  288,    0,    0,  302,    0,    0,   83,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  135,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  261,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  173,  211,   21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -235,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  755,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  433,    0,    0,
    0,  679,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  438,  442,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  374,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -235,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  433,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  274,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  471,    0,    0,    0,    0,    0,    0,
    0,    0,  627,  641,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  644,    0,    0, -163, -179, -123, -118,    0,  566,
  -88,    0,  -14,   -3,    0, -176, -312, -311, -301, -280,
  -30, -344, -115,    0,    0,  371,  -40,  -33,    0,    0,
  544,    8,   22,  449,  463,  481,    0,  -48,    0,    0,
  622,
};
final static int YYTABLESIZE=1024;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   76,   22,  148,   66,   68,  192,   70,  211,    1,   80,
   62,  370,   26,  230,  107,  185,  237,  287,  349,  350,
  170,  101,   49,   48,   71,  144,   27,   73,  149,  351,
  108,  383,   10,   11,  102,   50,   51,   52,  158,  103,
   74,   51,   52,   63,  186,   75,  288,  397,  245,  170,
  352,  400,    4,  145,  127,  128,  150,  349,  350,   53,
  126,  141,  230,  409,   53,  237,  159,  120,  351,  231,
  349,  350,  254,  268,  232,   76,  246,   32,  195,   45,
  143,  351,  142,  318,  349,  350,  147,  349,  350,  352,
   33,  121,   43,  122,  324,  351,  349,  350,  351,  196,
  255,  269,  352,   71,  327,  242,  230,  351,  375,  237,
  192,  319,  381,  316,  184,  395,  352,  407,  231,  352,
  305,  223,  325,  232,  180,    7,    8,  224,  352,  225,
  226,  227,  328,  228,  317,   85,  376,   45,  243,  218,
  382,  204,  205,  396,   95,  408,  181,   28,  283,  306,
   99,  244,   22,   86,  348,   14,  230,  230,  230,  237,
  237,  237,  231,   26,   29,   22,  248,  232,  100,  210,
   22,   22,   81,   22,   22,   22,   26,   27,  234,   82,
  286,   26,   26,  114,   26,   26,   26,   96,  321,  235,
   27,  213,   30,  348,   34,   27,   27,  270,   27,   27,
   27,  115,  252,  116,  109,  214,  348,  230,   36,   31,
  237,   35,  231,  231,  231,  295,  271,  232,  232,  232,
  348,  105,  110,  348,  277,   37,  276,  234,  278,  280,
  112,  282,  348,   92,   93,   38,  336,  337,  235,  338,
   67,  308,  123,   51,   52,  139,   69,  140,  113,   71,
   10,   11,   39,   50,   51,   52,   10,   11,  322,   50,
   51,   52,  165,  231,   51,   52,   53,   64,  232,  160,
   22,  234,  320,   74,   51,   52,  140,   53,  140,  256,
  166,   26,  235,   53,   65,  357,  161,   53,  133,   71,
  275,   78,   79,  387,  140,   27,  257,   53,  140,  279,
   10,   11,  367,   50,   51,   52,  134,  339,  135,   10,
   11,  106,   50,   51,   52,  340,  347,   83,   84,  172,
   45,  234,  234,  234,  202,   10,   11,   53,   50,   51,
   52,  111,  235,  235,  235,  378,   53,  173,  122,  122,
  122,  293,  203,  122,  122,  122,  122,  122,  122,  122,
  122,  122,   53,  297,  379,  347,  122,  117,  122,  294,
  122,  122,  122,  122,  122,  122,  299,  122,  347,  301,
  363,  298,  234,  122,  122,  122,  122,  122,  311,  314,
  312,  315,  347,  235,  300,  347,  384,  302,  364,  259,
  115,  115,  115,  118,  347,  115,  115,  115,  115,  115,
  115,  115,  115,  115,  385,  261,  260,  263,  115,   42,
  115,  119,  115,  115,  115,  115,  115,  115,  132,  115,
   43,  390,  262,  391,  264,  115,  115,  115,  113,  113,
  113,  131,  329,  113,  113,  113,  113,  113,  113,  113,
  113,  113,  365,   41,  138,  265,  113,   42,  113,  330,
  113,  113,  113,  113,  113,  113,   43,  113,   43,  366,
   97,   98,  151,  113,  113,  113,  114,  114,  114,   92,
   93,  114,  114,  114,  114,  114,  114,  114,  114,  114,
  136,   41,  137,  309,  114,   42,  114,  310,  114,  114,
  114,  114,  114,  114,  281,  114,  182,  183,  284,  285,
  377,  114,  114,  114,   10,   11,  152,   50,   51,   52,
   10,   11,  102,   50,   51,   52,  112,  112,  334,  335,
  154,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  155,   53,  170,  170,  112,  398,  112,   53,  112,  399,
  124,  125,  412,   11,   11,  112,  413,  157,   11,   11,
   11,  112,   11,   11,   11,  162,   11,  102,  102,  129,
  130,  187,  102,  102,  102,   11,  102,  102,  102,   40,
  102,  219,   11,   41,  220,  221,  163,   42,   11,  102,
  167,  168,  170,  171,  358,  223,  102,  188,   43,    7,
    8,  224,  102,  225,  226,  227,  194,  228,  361,  223,
  217,  222,  247,    7,    8,  224,  251,  225,  226,  227,
  249,  228,  258,  359,  372,  393,  223,  373,  374,   14,
    7,    8,  224,  253,  225,  226,  227,  362,  228,   28,
   28,  266,  272,   14,   28,   28,   28,   40,   28,   28,
   28,   41,   28,  223,  394,  265,  273,    7,    8,  224,
   14,  225,  226,  227,  289,  228,   43,  223,   28,  290,
  303,    7,    8,  224,   28,  225,  226,  227,  296,  228,
  223,  229,  304,  307,    7,    8,  224,   14,  225,  226,
  227,  313,  228,  323,    6,  267,  326,  355,    7,    8,
  332,   14,    9,   10,   11,  333,   12,    6,  360,  369,
  153,    7,    8,  356,   14,    9,   10,   11,  386,   12,
  371,    6,   13,  392,  368,    7,    8,  170,   14,    9,
   10,   11,  179,   12,    6,  176,  181,  212,    7,    8,
  170,   14,    9,   10,   11,  274,   12,  146,    6,  197,
    0,    0,    7,    8,    0,   14,    9,   10,   11,    0,
   12,    6,  198,    0,    0,    7,    8,    0,   14,    9,
   10,   11,    0,   12,    0,    6,  199,    0,    0,    7,
    8,    0,   14,    9,   10,   11,    0,   12,    5,  200,
    0,  156,    5,    5,    0,   14,    5,    5,    5,    0,
    5,  404,    0,  201,  405,  406,    0,  174,  175,   14,
  177,  178,  179,    0,    0,    0,    5,    0,  341,  342,
    0,    0,    5,    7,    0,  224,  343,  225,  226,  227,
  344,  345,  388,  342,    0,    0,    0,    7,    0,  224,
  343,  225,  226,  227,  344,  345,    0,  346,    0,    0,
    0,    0,    0,    0,    0,  402,  342,    0,    0,    0,
    7,  389,  224,  343,  225,  226,  227,  344,  345,  410,
  342,    0,    0,    0,    7,    0,  224,  343,  225,  226,
  227,  344,  345,    0,  403,    0,    0,    0,    0,    0,
    0,    0,   85,   85,    0,    0,    0,   85,  411,   85,
   85,   85,   85,   85,   85,   85,  181,  181,    0,    0,
    0,  181,    0,  181,  181,  181,  181,  181,  181,  181,
    0,   85,    0,  189,    6,    0,    0,    0,    7,    0,
    0,    0,    9,   10,   11,  181,  190,  291,    6,    0,
    0,    0,    7,    0,  148,  148,    9,   10,   11,  148,
  190,    0,  191,  148,  148,  148,    0,  148,    0,    0,
    0,    0,    0,    0,    0,    0,  292,    0,  342,    0,
    0,    0,    7,  148,  224,  343,  225,  226,  227,  344,
  345,  342,    0,    0,    0,    7,    0,  224,  343,  225,
  226,  227,  344,  345,    0,    0,  380,   87,   88,   89,
   90,   91,    0,    0,    0,    0,    0,    0,  206,  401,
   92,   93,    7,    0,    0,  207,    9,   10,   11,  208,
  209,  170,    0,    0,    0,  170,    0,    0,  170,  170,
  170,  170,  170,  170,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
   41,    5,  118,   34,   35,  169,   37,  184,  269,   43,
  256,  356,    5,  193,  258,  256,  193,  256,  331,  331,
  256,  256,  256,  278,   39,  256,    5,  256,  256,  331,
  274,  376,  266,  267,  269,  269,  270,  271,  256,  274,
  269,  270,  271,  289,  285,  274,  285,  392,  256,  285,
  331,  396,  284,  284,   95,   96,  284,  370,  370,  293,
   94,  256,  242,  408,  293,  242,  284,   82,  370,  193,
  383,  383,  256,  256,  193,  116,  284,  256,  256,  269,
  111,  383,  277,  256,  397,  397,  117,  400,  400,  370,
  269,  256,  288,  258,  256,  397,  409,  409,  400,  277,
  284,  284,  383,  118,  256,  194,  286,  409,  256,  286,
  274,  284,  256,  256,  163,  256,  397,  256,  242,  400,
  256,  257,  284,  242,  256,  261,  262,  263,  409,  265,
  266,  267,  284,  269,  277,  256,  284,  269,  256,  188,
  284,  182,  183,  284,  259,  284,  161,  256,  264,  285,
  256,  269,  156,  274,  331,  291,  336,  337,  338,  336,
  337,  338,  286,  156,  273,  169,  207,  286,  274,  184,
  174,  175,  276,  177,  178,  179,  169,  156,  193,  276,
  269,  174,  175,  256,  177,  178,  179,  259,  304,  193,
  169,  184,  256,  370,  256,  174,  175,  246,  177,  178,
  179,  274,  217,  276,  256,  184,  383,  387,  256,  273,
  387,  273,  336,  337,  338,  274,  247,  336,  337,  338,
  397,  274,  274,  400,  258,  273,  257,  242,  259,  260,
  256,  262,  409,  292,  293,  256,  325,  326,  242,  328,
  256,  290,  269,  270,  271,  256,  256,  258,  274,  264,
  266,  267,  273,  269,  270,  271,  266,  267,  307,  269,
  270,  271,  256,  387,  270,  271,  293,  256,  387,  256,
  274,  286,  303,  269,  270,  271,  256,  293,  258,  256,
  274,  274,  286,  293,  273,  334,  273,  293,  256,  304,
  256,  266,  267,  382,  274,  274,  273,  293,  278,  256,
  266,  267,  343,  269,  270,  271,  274,  256,  276,  266,
  267,  274,  269,  270,  271,  330,  331,  270,  271,  256,
  269,  336,  337,  338,  256,  266,  267,  293,  269,  270,
  271,  278,  336,  337,  338,  366,  293,  274,  256,  257,
  258,  256,  274,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  293,  256,  369,  370,  274,  273,  276,  274,
  278,  279,  280,  281,  282,  283,  256,  285,  383,  256,
  256,  274,  387,  291,  292,  293,  294,  295,  256,  256,
  258,  258,  397,  387,  274,  400,  256,  274,  274,  256,
  256,  257,  258,  273,  409,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  274,  256,  273,  256,  274,  277,
  276,  269,  278,  279,  280,  281,  282,  283,  277,  285,
  288,  256,  273,  258,  273,  291,  292,  293,  256,  257,
  258,  269,  256,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  273,  284,  277,  274,  277,  276,  273,
  278,  279,  280,  281,  282,  283,  288,  285,  288,  273,
  294,  295,  269,  291,  292,  293,  256,  257,  258,  292,
  293,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  258,  273,  258,  256,  274,  277,  276,  260,  278,  279,
  280,  281,  282,  283,  256,  285,  292,  293,  266,  267,
  256,  291,  292,  293,  266,  267,  277,  269,  270,  271,
  266,  267,  269,  269,  270,  271,  256,  257,  259,  260,
  284,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  284,  293,  259,  260,  274,  256,  276,  293,  278,  260,
   92,   93,  256,  256,  257,  285,  260,  284,  261,  262,
  263,  291,  265,  266,  267,  278,  269,  256,  257,   97,
   98,  277,  261,  262,  263,  278,  265,  266,  267,  269,
  269,  256,  285,  273,  259,  260,  284,  277,  291,  278,
  274,  274,  284,  269,  256,  257,  285,  284,  288,  261,
  262,  263,  291,  265,  266,  267,  284,  269,  256,  257,
  273,  278,  273,  261,  262,  263,  285,  265,  266,  267,
  277,  269,  273,  285,  256,  256,  257,  259,  260,  291,
  261,  262,  263,  285,  265,  266,  267,  285,  269,  256,
  257,  278,  269,  291,  261,  262,  263,  269,  265,  266,
  267,  273,  269,  257,  285,  277,  274,  261,  262,  263,
  291,  265,  266,  267,  274,  269,  288,  257,  285,  284,
  273,  261,  262,  263,  291,  265,  266,  267,  274,  269,
  257,  285,  273,  258,  261,  262,  263,  291,  265,  266,
  267,  258,  269,  285,  257,  285,  284,  277,  261,  262,
  274,  291,  265,  266,  267,  274,  269,  257,  285,  273,
  135,  261,  262,  284,  291,  265,  266,  267,  274,  269,
  260,  257,  285,  284,  344,  261,  262,  285,  291,  265,
  266,  267,  285,  269,  257,  285,  285,  184,  261,  262,
  260,  291,  265,  266,  267,  255,  269,  116,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,   -1,  269,   -1,  257,  285,   -1,   -1,  261,
  262,   -1,  291,  265,  266,  267,   -1,  269,  257,  285,
   -1,  138,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,  256,   -1,  285,  259,  260,   -1,  154,  155,  291,
  157,  158,  159,   -1,   -1,   -1,  285,   -1,  256,  257,
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
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN error bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C error LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A error PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN error condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_when : WHEN PARENT_A condicion THEN LLAVE_A bloque_sentencias LLAVE_C",
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

//#line 267 "gramatica.y"

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
//#line 791 "Parser.java"
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
int yyparse()
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
{yyerror("Se esperaba { en el when");}
break;
case 155:
//#line 207 "gramatica.y"
{yyerror("Se esperaba then en el when");}
break;
case 156:
//#line 208 "gramatica.y"
{yyerror("Se esperaba condicion en el when");}
break;
case 157:
//#line 209 "gramatica.y"
{yyerror("Se esperaba ( en el when");}
break;
case 158:
//#line 210 "gramatica.y"
{yyerror("Se esperaba ) en el when");}
break;
case 159:
//#line 212 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 160:
//#line 213 "gramatica.y"
{System.out.println("Sentencia WHILE");}
break;
case 161:
//#line 214 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 162:
//#line 215 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 163:
//#line 216 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 164:
//#line 217 "gramatica.y"
{yyerror("Se esperaba una asignacion");}
break;
case 165:
//#line 218 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 166:
//#line 219 "gramatica.y"
{yyerror("Se esperaba :");}
break;
case 167:
//#line 220 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 168:
//#line 221 "gramatica.y"
{yyerror("Se esperaba una condicion");}
break;
case 169:
//#line 222 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 183:
//#line 242 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 184:
//#line 243 "gramatica.y"
{System.out.println("Sentencia IF");}
break;
case 185:
//#line 245 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 186:
//#line 246 "gramatica.y"
{System.out.println("Sentencia FOR");}
break;
case 187:
//#line 247 "gramatica.y"
{yyerror("Se esperaba }");}
break;
case 188:
//#line 248 "gramatica.y"
{yyerror("Se esperaba {");}
break;
case 189:
//#line 249 "gramatica.y"
{yyerror("Se esperaba )");}
break;
case 190:
//#line 250 "gramatica.y"
{yyerror("Se esperaba (");}
break;
case 198:
//#line 262 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 199:
//#line 263 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
case 200:
//#line 264 "gramatica.y"
{System.out.println("Se esperaba )");}
break;
//#line 1384 "Parser.java"
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
 */
public void run()
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
