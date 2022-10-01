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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    4,    4,    4,
    4,    6,    9,    9,    7,    7,    7,   12,   10,   11,
   11,   11,   11,   14,   14,    8,   15,   15,    5,    5,
    5,    5,    5,    5,   16,   13,   13,   13,   13,   13,
   13,   22,   22,   22,   24,   24,   24,   25,   25,   17,
   17,   26,   28,   28,   28,   28,   28,   27,   27,   18,
   19,   21,   29,   29,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   32,   32,   31,   31,   20,   33,   33,
   34,   34,   34,   34,   35,   35,   35,   35,   23,   23,
};
final static short yylen[] = {                            2,
    4,    1,    0,    3,    0,    1,    1,    0,    1,    1,
    1,    2,    3,    1,   13,   11,   10,    5,    2,    2,
    2,    1,    1,    2,    1,    2,    3,    1,    1,    1,
    1,    1,    1,    1,    3,    3,    3,    1,    1,    1,
    1,    3,    3,    1,    2,    1,    1,    1,    1,    9,
    7,    3,    1,    1,    1,    1,    1,    0,    3,    4,
    6,   11,    0,    3,    1,    1,    1,    1,    1,    1,
    2,    1,    2,    0,    2,    9,    7,    5,    6,    6,
    3,    3,    1,    1,    3,    3,    1,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    6,    7,    9,   10,   11,
   29,   30,   31,   32,   33,   34,    0,    0,    0,    0,
    0,    0,   14,    0,    0,    0,    0,   28,    4,    0,
   48,   49,    0,    0,   40,   41,    0,   39,   44,   47,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,   53,   54,   55,   56,   57,    0,    0,    0,
    0,    0,    0,   60,    0,    0,    0,    0,    0,    0,
   63,   13,   27,   88,   90,   87,    0,   46,    0,    0,
    0,   42,   43,   58,   19,    0,    0,    0,    3,    0,
    0,    0,   89,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   65,   67,   68,   70,   69,    0,
   66,   85,   86,   58,   51,    0,    0,    0,    0,    0,
    0,    0,    0,   73,    0,   71,   64,    0,   59,   23,
    0,    0,   22,    0,    0,    0,   79,   80,    0,   75,
   50,    0,   24,   21,    0,   20,    0,    0,   63,    0,
    0,   17,    0,    0,    0,    0,    0,   16,    0,   62,
    0,    0,    0,    0,   77,   63,   18,   15,    0,   76,
};
final static short yydgoto[] = {                          2,
    3,    5,   15,  140,  141,   18,   19,   20,   35,   77,
  142,  153,   44,  143,   37,   21,   22,   23,   24,   25,
   26,   47,   48,   49,   50,   51,  105,   70,  102,  120,
  121,  136,   57,    0,   87,
};
final static short yysindex[] = {                      -254,
    0,    0, -259,    0,  -78, -212, -208, -189, -183, -177,
 -135, -237,    0, -118, -126,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   79, -136, -119,   79,
   79, -118,    0,   79, -120, -130, -117,    0,    0, -112,
    0,    0, -202,   46,    0,    0,   17,    0,    0,    0,
 -111, -109, -171, -107, -106, -103, -105,   54,  -93, -118,
 -248,    0,    0,    0,    0,    0,    0,  -26,  -26,   79,
  -26,  -26,  -92,    0,  -89, -100, -143,  -88,  -96, -153,
    0,    0,    0,    0,    0,    0, -139,    0,   17,   17,
   54,    0,    0,    0,    0,  -87,  -91,  -79,    0,  -80,
  -83,  173,    0, -161,  186,  -76,  -73,  -77,   94, -118,
   96,  -72, -202,  -68,    0,    0,    0,    0,    0,  -67,
    0,    0,    0,    0,    0,  -66,   94,  -74,  -61,  -69,
  -55,  -52,   79,    0,  -51,    0,    0,  197,    0,    0,
  -34,  -63,    0,   94,  -47,  -58,    0,    0,  -44,    0,
    0,  -38,    0,    0,  -54,    0,  -63,  -31,    0,   -3,
   79,    0,  -29,   94,  112,    0,  -41,    0,  -63,    0,
  208,  147,  -21,  -27,    0,    0,    0,    0,  160,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -18,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -191,    0, -144,    0,    0,  -42,
    0,    0,    0,    0,    0,    0,  -15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -228,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,   39,
   -4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,    0, -205,    0,
    0,    0,  -16,   -7,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    9,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  105,    0,    0,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,    0,  134,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  181,    0,    3,    2,    0,    0,    0,    0,  184,
 -108, -115,  -25,  -37,    0,   -8,    0,  -86,  -84,  -17,
   13,   74,    0,   78,  -12,  -19, -104,  203,  -99,    0,
    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=477;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   46,   68,   46,   69,   46,   38,   17,   16,   58,   45,
   54,   55,   45,   45,    1,  116,   45,  117,   43,  138,
   84,   41,   42,   56,    4,   85,  155,   38,   35,   38,
   62,   33,   35,   35,   35,  157,   35,   35,   35,   46,
   35,  163,   46,   46,   91,   35,   46,   35,   86,   35,
   34,   83,   45,  174,   36,  169,   36,   61,   71,  165,
   27,  171,   35,   72,   28,   12,  172,   41,   42,   12,
   12,   12,    5,   12,   12,   12,  179,   12,  116,   29,
  117,   37,   46,   37,  118,  116,   12,  117,   68,   30,
   69,  123,  116,  115,  117,   31,   68,   75,   69,   12,
  134,  130,   76,   78,  156,   78,  126,  122,   41,   42,
   17,   16,   26,  149,  119,   45,   26,   26,   26,  156,
   26,   26,   26,   43,   26,   63,   64,   65,   66,   67,
   97,  156,   98,   26,  103,  167,  104,   32,  131,  126,
  132,   89,   90,   45,  154,   46,   26,  118,   92,   93,
   36,   39,   52,   53,  118,   59,  115,   34,   60,  154,
   61,  118,   73,  115,   74,   94,   78,   79,   81,   99,
  115,  154,  126,   46,   80,   82,   96,  119,    6,   95,
  100,  106,    7,    8,  119,  107,    9,   10,   11,   75,
   12,  119,  110,    6,  111,  128,  129,    7,    8,  152,
  133,    9,   10,   11,  146,   12,   13,  127,  135,  144,
  137,  139,   14,  147,   46,  145,  148,  150,   46,   46,
   46,  158,   46,   46,   46,  159,   46,   14,  152,  160,
  162,   46,  173,   46,  161,   46,   46,   46,   46,   46,
   46,   38,   88,   41,   42,   38,   38,   38,   46,   38,
   38,   38,  164,   38,  166,  168,  177,  178,   38,    5,
   38,   72,   38,   38,   38,   38,   38,   38,   36,   52,
   74,    8,   36,   36,   36,   38,   36,   36,   36,  109,
   36,  108,  101,    0,    0,   36,    0,   36,    0,   36,
   36,   36,   36,   36,   36,   37,    0,    0,    0,   37,
   37,   37,   36,   37,   37,   37,    0,   37,    0,    0,
    0,    0,   37,    0,   37,    0,   37,   37,   37,   37,
   37,   37,   78,   78,   63,   64,   65,   66,   67,   37,
    0,    0,    0,    0,   78,    0,   78,    0,   78,   78,
   78,   78,   78,   78,   10,   11,    0,   40,   41,   42,
    6,   78,    0,    0,    7,    8,    0,    0,    9,   10,
   11,   25,   12,    0,    0,   25,   25,    0,  112,   25,
   25,   25,    7,   25,    0,  113,    9,   10,   11,  114,
   36,    0,    0,    0,   14,    0,    0,    0,    0,    0,
   58,    0,   63,   58,   58,   25,  170,   63,   58,   58,
   58,   63,   58,  112,    0,  176,    0,    7,    0,    0,
  113,    9,   10,   11,  114,   36,  112,    0,    0,  180,
    7,    0,    0,  113,    9,   10,   11,  114,   36,  112,
    0,    0,    0,    7,    0,    0,  113,    9,   10,   11,
  114,   36,    6,    0,  124,  125,    7,    0,    0,    0,
    9,   10,   11,    6,   36,    0,  151,    7,    0,    0,
    0,    9,   10,   11,    6,   36,    0,  175,    7,    0,
    0,    0,    9,   10,   11,    0,   36,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         42,
   43,   43,   45,   45,   47,   14,    5,    5,   34,   27,
   30,   31,   30,   31,  269,  102,   34,  102,   45,  124,
  269,  270,  271,   32,  284,  274,  142,   43,  257,   45,
   43,  269,  261,  262,  263,  144,  265,  266,  267,   27,
  269,  157,   30,   31,   70,  274,   34,  276,   61,  278,
  288,   60,   70,  169,   43,  164,   45,  263,   42,  159,
  273,  166,  291,   47,  273,  257,  166,  270,  271,  261,
  262,  263,  278,  265,  266,  267,  176,  269,  165,  269,
  165,   43,   70,   45,  102,  172,  278,  172,   43,  273,
   45,  104,  179,  102,  179,  273,   43,  269,   45,  291,
  113,  110,  274,   43,  142,   45,  105,  269,  270,  271,
  109,  109,  257,  133,  102,  133,  261,  262,  263,  157,
  265,  266,  267,   45,  269,  279,  280,  281,  282,  283,
  274,  169,  276,  278,  274,  161,  276,  273,   43,  138,
   45,   68,   69,  161,  142,  133,  291,  165,   71,   72,
  269,  278,  289,  273,  172,  276,  165,  288,  276,  157,
  273,  179,  274,  172,  274,  258,  274,  274,  274,  258,
  179,  169,  171,  161,  278,  269,  277,  165,  257,  269,
  277,  269,  261,  262,  172,  277,  265,  266,  267,  269,
  269,  179,  273,  257,  278,  269,  274,  261,  262,  263,
  273,  265,  266,  267,  274,  269,  285,  284,  277,  284,
  278,  278,  291,  269,  257,  277,  269,  269,  261,  262,
  263,  269,  265,  266,  267,  284,  269,  291,  263,  274,
  285,  274,  274,  276,  273,  278,  279,  280,  281,  282,
  283,  257,  269,  270,  271,  261,  262,  263,  291,  265,
  266,  267,  284,  269,  258,  285,  278,  285,  274,  278,
  276,  278,  278,  279,  280,  281,  282,  283,  257,  274,
  278,  263,  261,  262,  263,  291,  265,  266,  267,   99,
  269,   98,   80,   -1,   -1,  274,   -1,  276,   -1,  278,
  279,  280,  281,  282,  283,  257,   -1,   -1,   -1,  261,
  262,  263,  291,  265,  266,  267,   -1,  269,   -1,   -1,
   -1,   -1,  274,   -1,  276,   -1,  278,  279,  280,  281,
  282,  283,  262,  263,  279,  280,  281,  282,  283,  291,
   -1,   -1,   -1,   -1,  274,   -1,  276,   -1,  278,  279,
  280,  281,  282,  283,  266,  267,   -1,  269,  270,  271,
  257,  291,   -1,   -1,  261,  262,   -1,   -1,  265,  266,
  267,  257,  269,   -1,   -1,  261,  262,   -1,  257,  265,
  266,  267,  261,  269,   -1,  264,  265,  266,  267,  268,
  269,   -1,   -1,   -1,  291,   -1,   -1,   -1,   -1,   -1,
  257,   -1,  259,  260,  261,  291,  285,  264,  265,  266,
  267,  268,  269,  257,   -1,  259,   -1,  261,   -1,   -1,
  264,  265,  266,  267,  268,  269,  257,   -1,   -1,  260,
  261,   -1,   -1,  264,  265,  266,  267,  268,  269,  257,
   -1,   -1,   -1,  261,   -1,   -1,  264,  265,  266,  267,
  268,  269,  257,   -1,  259,  260,  261,   -1,   -1,   -1,
  265,  266,  267,  257,  269,   -1,  260,  261,   -1,   -1,
   -1,  265,  266,  267,  257,  269,   -1,  260,  261,   -1,
   -1,   -1,  265,  266,  267,   -1,  269,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=291;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,"'*'","'+'",null,
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,"IF","THEN","ELSE","END_IF","OUT","FUN","RETURN",
"BREAK","WHEN","WHILE","FOR","CONTINUE","ID","I32","F32","PUNTO","PARENT_A",
"PARENT_C","COMILLA","COMA","DOSPUNTOS","PUNTOCOMA","IGUAL","MAYOR","MENOR",
"MENORIGUAL","MAYORIGUAL","LLAVE_A","LLAVE_C","EXCL","DIST","ASIG","CADENA",
"COMENT","CONST",
};
final static String yyrule[] = {
"$accept : program",
"program : nombre_program LLAVE_A bloque_sentencias LLAVE_C",
"nombre_program : ID",
"bloque_sentencias :",
"bloque_sentencias : bloque_sentencias sentencia PUNTOCOMA",
"sentencia :",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa :",
"sentencia_declarativa : sentencia_decl_datos",
"sentencia_declarativa : sentencia_decl_fun",
"sentencia_declarativa : lista_const",
"sentencia_decl_datos : ID list_var",
"list_var : list_var COMA ID",
"list_var : ID",
"sentencia_decl_fun : FUN ID PARENT_A parametro COMA parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A parametro PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
"sentencia_decl_fun : FUN ID PARENT_A PARENT_C DOSPUNTOS ID LLAVE_A cuerpo_fun retorno LLAVE_C",
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
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : llamado_func",
"expresion : sentencia_for",
"expresion : sentencia_while",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : '-' cte",
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
"sentencia_when : WHEN PARENT_A condicion PARENT_C THEN bloque_sentencias",
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
"sentencia_if_break : IF PARENT_A condicion PARENT_C THEN bloque_ejecutable END_IF",
"sentencia_for : FOR PARENT_A encabezado_for PARENT_C bloque_break_continue",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '+' ID",
"encabezado_for : asignacion PUNTOCOMA comparacion PUNTOCOMA '-' ID",
"sentencia_control : ID DOSPUNTOS sentencia_for",
"sentencia_control : ID DOSPUNTOS sentencia_while",
"sentencia_control : sentencia_for",
"sentencia_control : sentencia_while",
"list_param_real : list_param_real COMA ID",
"list_param_real : list_param_real COMA cte",
"list_param_real : cte",
"list_param_real : ID",
"llamado_func : ID PARENT_A list_param_real PARENT_C",
"llamado_func : ID PARENT_A PARENT_C",
};

//#line 144 "gramatica.y"

void yyerror(String mensaje){
        System.out.println("Error yacc: " + mensaje);
}

int yylex() throws IOException{
        BufferedReader entrada = new BufferedReader(new FileReader("archivos/entrada.txt"));
        AnalizadorLexico a = new AnalizadorLexico(entrada);
        Token t = a.getToken();
        return t.getId();
}

public static void main(String[] args) {
        Parser parser = new Parser();
        parser.run();
}
//#line 460 "Parser.java"
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
case 40:
//#line 64 "gramatica.y"
{/*Si sentencia_for no devuelve nada asignar algo por defecto*/}
break;
case 41:
//#line 65 "gramatica.y"
{/*Si sentencia_while no devuelve nada asignar algo por defecto*/}
break;
//#line 617 "Parser.java"
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
