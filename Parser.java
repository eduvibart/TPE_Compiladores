import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

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
    9,    9,    7,    7,    7,   12,   10,   11,   11,   11,
   11,   14,   14,    8,   15,   15,    5,    5,    5,    5,
    5,    5,   16,   13,   13,   13,   13,   13,   13,   22,
   22,   22,   24,   24,   24,   25,   25,   17,   17,   26,
   28,   28,   28,   28,   28,   27,   27,   18,   19,   21,
   29,   29,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   32,   32,   31,   31,   20,   33,   33,   34,   34,
   34,   34,   23,   23,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    1,    1,    1,    1,    1,    2,
    3,    1,   12,   10,    9,    5,    2,    2,    2,    1,
    1,    2,    1,    2,    3,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    1,    1,    1,    1,    3,
    3,    1,    2,    1,    1,    1,    1,    9,    7,    3,
    1,    1,    1,    1,    1,    0,    3,    4,    8,   11,
    0,    3,    1,    1,    1,    1,    1,    1,    2,    1,
    2,    0,    2,    9,    7,    7,    6,    6,    3,    3,
    1,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    5,    6,    7,    8,    9,
   27,   28,   29,   30,   31,   32,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,   26,    4,    0,
   46,   47,    0,    0,   38,   39,    0,   37,   42,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   43,   51,   52,   53,   54,   55,    0,    0,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,   11,   25,   82,   84,   81,    0,   44,    0,    0,
    0,   40,   41,   56,   17,    0,    0,    0,    0,    0,
    0,   61,   83,    0,    0,    0,    0,    0,    3,    0,
    0,    0,   79,   80,   56,   49,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   76,   63,   65,
   66,   68,   67,    0,   64,    0,   57,   21,    0,    0,
   20,    0,    0,   59,    0,   77,   78,    0,   71,    0,
   69,   62,   48,    0,   22,   15,   19,   18,    0,    0,
   61,    0,   73,    0,   14,    0,    0,    0,    0,    0,
   60,   61,    0,   13,    0,   16,   61,   75,    0,   74,
};
final static short yydgoto[] = {                          2,
    3,    5,   15,  138,  139,   18,   19,   20,   35,   77,
  140,  155,   44,  141,   37,   21,   22,   23,   24,   25,
   26,   47,   48,   49,   50,   51,  105,   70,  112,  134,
  135,  151,   57,   87,
};
final static short yysindex[] = {                      -253,
    0,    0, -230,    0,   58, -216, -209, -193, -178, -176,
 -171, -250,    0, -154, -149,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -237, -165, -142, -237,
 -237, -154,    0, -237, -143, -151, -141,    0,    0, -133,
    0,    0, -180, -134,    0,    0, -195,    0,    0,    0,
 -123, -114, -214, -112, -110, -121, -104, -187,  -88, -154,
 -208,    0,    0,    0,    0,    0,    0, -249, -249, -237,
 -249, -249,  -84,    0,  -86,  -72, -206,  -68,  -64,  -87,
  -75,    0,    0,    0,    0,    0, -188,    0, -195, -195,
 -187,    0,    0,    0,    0,  -54,  -61,  -52,  -66,  -53,
  -56,    0,    0, -245, -139,  -51,  -40,  -43,    0, -154,
 -116,  154,    0,    0,    0,    0,  -46, -153,  -50,  -35,
   71,  -28,  -20,  -19,  -18, -180,  -29,    0,    0,    0,
    0,    0,    0,  -27,    0,  139,    0,    0,  -10,   85,
    0, -153,   -7,    0,  -17,    0,    0, -237,    0,   -5,
    0,    0,    0,   -8,    0,    0,    0,    0,   98,   -9,
    0,    5,    0, -237,    0, -153,  167,    8, -182,  112,
    0,    0,    3,    0,  181,    0,    0,    0,  194,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -184,    0,   44,    0,    0,  -94,
    0,    0,    0,    0,    0,    0,  -55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -226,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -22,   11,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  125,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  177,    0,   -4,   -2,    0,    0,    0,    0,  190,
 -115,    0,  -21, -117,    0,  -14,    0, -100,  -95,  -25,
  -23,  110,    0,  132,  -33,  -16,  180,  217, -103,    0,
    0,    0,    0,    0,
};
final static int YYTABLESIZE=463;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   16,   45,   17,   46,   45,   45,   46,   46,   45,   62,
   46,  130,   58,   54,   55,    1,  131,   56,   33,   88,
   41,   42,  158,  113,   41,   42,  159,   86,   10,   11,
   33,   40,   41,   42,   33,   33,   33,   34,   33,   33,
   33,  158,   33,   43,   45,   83,   46,   33,   91,   33,
  170,   33,  158,    4,   75,   43,   27,  167,   33,   76,
   84,   41,   42,   28,   33,   85,  130,   97,  175,   98,
  114,  131,   10,  179,  130,   29,   10,   10,  130,  131,
   10,   10,   10,  131,   10,  103,  132,  104,  133,   41,
   42,  173,  149,   10,   30,  122,   31,  129,   71,   72,
   10,   32,  117,    6,   68,   69,   10,    7,    8,   68,
   69,    9,   10,   11,   36,   12,   16,    6,   17,  115,
  116,    7,   45,   52,   46,    9,   10,   11,   39,   36,
   53,  162,   59,  117,   60,  157,   34,   14,   45,   61,
   46,  132,  169,  133,   63,   64,   65,   66,   67,  132,
   73,  133,  129,  132,  157,  133,   80,   68,   69,   74,
  129,   78,   44,   79,  129,  157,   44,   44,   44,   81,
   44,   44,   44,   94,   44,  123,  124,   89,   90,   44,
   82,   44,   95,   44,   44,   44,   44,   44,   44,   99,
   44,   63,   64,   65,   66,   67,   44,   44,   44,   44,
   44,   36,   92,   93,   96,   36,   36,   36,  102,   36,
   36,   36,  100,   36,  106,  107,   75,  109,   36,  110,
   36,  111,   36,   36,   36,   36,   36,   36,  119,   36,
  120,  137,  118,  142,   34,   36,   36,   36,   34,   34,
   34,  143,   34,   34,   34,  145,   34,  150,  146,  147,
  152,   34,  154,   34,  148,   34,   34,   34,   34,   34,
   34,  160,   34,  163,  164,  172,  161,   35,   34,   34,
   34,   35,   35,   35,  166,   35,   35,   35,  168,   35,
  176,   70,   50,   72,   35,  121,   35,  108,   35,   35,
   35,   35,   35,   35,  136,   35,  101,    0,    0,    0,
   24,   35,   35,   35,   24,   24,    0,    0,   24,   24,
   24,    0,   24,    0,    6,    0,    0,    0,    7,    8,
    0,   24,    9,   10,   11,    0,   12,    6,   24,    0,
    0,    7,    8,    0,   24,    9,   10,   11,    0,   12,
    0,    6,   13,    0,    0,    7,    8,    0,   14,    9,
   10,   11,    0,   12,    6,  144,    0,    0,    7,    8,
    0,   14,    9,   10,   11,    0,   12,    0,    6,  156,
    0,    0,    7,    8,    0,   14,    9,   10,   11,    0,
   12,   23,  165,    0,    0,   23,   23,    0,   14,   23,
   23,   23,    0,   23,    0,    6,  174,    0,  153,    7,
    0,    0,   14,    9,   10,   11,    0,   36,    0,   23,
  125,    0,    0,    0,    7,   23,    0,  126,    9,   10,
   11,  127,   36,  125,    0,    0,    0,    7,    0,    0,
  126,    9,   10,   11,  127,   36,    0,  125,  128,  177,
  178,    7,    0,    0,  126,    9,   10,   11,  127,   36,
  125,  171,    0,  180,    7,    0,    0,  126,    9,   10,
   11,  127,   36,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
    5,   27,    5,   27,   30,   31,   30,   31,   34,   43,
   34,  112,   34,   30,   31,  269,  112,   32,  269,  269,
  270,  271,  140,  269,  270,  271,  142,   61,  266,  267,
  257,  269,  270,  271,  261,  262,  263,  288,  265,  266,
  267,  159,  269,  293,   70,   60,   70,  274,   70,  276,
  166,  278,  170,  284,  269,  293,  273,  161,  285,  274,
  269,  270,  271,  273,  291,  274,  167,  274,  172,  276,
  104,  167,  257,  177,  175,  269,  261,  262,  179,  175,
  265,  266,  267,  179,  269,  274,  112,  276,  112,  270,
  271,  274,  126,  278,  273,  110,  273,  112,  294,  295,
  285,  273,  105,  257,  292,  293,  291,  261,  262,  292,
  293,  265,  266,  267,  269,  269,  121,  257,  121,  259,
  260,  261,  148,  289,  148,  265,  266,  267,  278,  269,
  273,  148,  276,  136,  276,  140,  288,  291,  164,  273,
  164,  167,  164,  167,  279,  280,  281,  282,  283,  175,
  274,  175,  167,  179,  159,  179,  278,  292,  293,  274,
  175,  274,  257,  274,  179,  170,  261,  262,  263,  274,
  265,  266,  267,  258,  269,  292,  293,   68,   69,  274,
  269,  276,  269,  278,  279,  280,  281,  282,  283,  258,
  285,  279,  280,  281,  282,  283,  291,  292,  293,  294,
  295,  257,   71,   72,  277,  261,  262,  263,  284,  265,
  266,  267,  277,  269,  269,  277,  269,  284,  274,  273,
  276,  278,  278,  279,  280,  281,  282,  283,  269,  285,
  274,  278,  284,  284,  257,  291,  292,  293,  261,  262,
  263,  277,  265,  266,  267,  274,  269,  277,  269,  269,
  278,  274,  263,  276,  273,  278,  279,  280,  281,  282,
  283,  269,  285,  269,  273,  258,  284,  257,  291,  292,
  293,  261,  262,  263,  284,  265,  266,  267,  274,  269,
  278,  278,  274,  278,  274,  109,  276,   98,  278,  279,
  280,  281,  282,  283,  115,  285,   80,   -1,   -1,   -1,
  257,  291,  292,  293,  261,  262,   -1,   -1,  265,  266,
  267,   -1,  269,   -1,  257,   -1,   -1,   -1,  261,  262,
   -1,  278,  265,  266,  267,   -1,  269,  257,  285,   -1,
   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,  269,
   -1,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,   -1,  269,  257,  285,   -1,   -1,  261,  262,
   -1,  291,  265,  266,  267,   -1,  269,   -1,  257,  285,
   -1,   -1,  261,  262,   -1,  291,  265,  266,  267,   -1,
  269,  257,  285,   -1,   -1,  261,  262,   -1,  291,  265,
  266,  267,   -1,  269,   -1,  257,  285,   -1,  260,  261,
   -1,   -1,  291,  265,  266,  267,   -1,  269,   -1,  285,
  257,   -1,   -1,   -1,  261,  291,   -1,  264,  265,  266,
  267,  268,  269,  257,   -1,   -1,   -1,  261,   -1,   -1,
  264,  265,  266,  267,  268,  269,   -1,  257,  285,  259,
  260,  261,   -1,   -1,  264,  265,  266,  267,  268,  269,
  257,  285,   -1,  260,  261,   -1,   -1,  264,  265,  266,
  267,  268,  269,
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
"retorno : RETURN PARENT_A expresion PARENT_C PUNTOCOMA",
"parametro : ID ID",
"cuerpo_fun : cuerpo_fun bloque_ejecutable_funcion",
"cuerpo_fun : cuerpo_fun sentencia_declarativa",
"cuerpo_fun : bloque_ejecutable_funcion",
"cuerpo_fun : sentencia_declarativa",
"bloque_ejecutable_funcion : sentencia_ejecutable retorno",
"bloque_ejecutable_funcion : sentencia_ejecutable",
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
"expresion : sentencia_for",
"expresion : sentencia_while",
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
"bloque_ejecutable : bloque_ejecutable sentencia_ejecutable PUNTOCOMA",
"sentencia_out : OUT PARENT_A CADENA PARENT_C",
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN LLAVE_A bloque_sentencias LLAVE_C",
"sentencia_while : WHILE PARENT_A condicion PARENT_C DOSPUNTOS PARENT_A asignacion PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"bloque_break_continue :",
"bloque_break_continue : bloque_break_continue ejecutables_break_continue PUNTOCOMA",
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
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C LLAVE_A bloque_break_continue LLAVE_C",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA SUMA ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA RESTA ID",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
};

//#line 134 "gramatica.y"

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}

int yylex() throws IOException{
        Token t = AnalizadorLexico.getToken();
        System.out.println("NUEVO TOKEN");
        System.out.println(t.getId());
        System.out.println(t.getLexema());
        return t.getId();
}

public static void main(String[] args) throws IOException {
  BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
  AnalizadorLexico.setEntrada(entrada);
  Parser parser = new Parser();
  parser.run();
}
//#line 456 "Parser.java"
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
case 38:
//#line 62 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 39:
//#line 63 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
//#line 613 "Parser.java"
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
