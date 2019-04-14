package rs.ac.bg.etf.pp1.sm120554d;

import java_cup.runtime.Symbol;


%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" "		{}
"\b"	{}
"\t"	{}
"\r\n"	{}
"\f"	{}

"program"   { return new_symbol(sym.PROGRAM, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }
"void"		{ return new_symbol(sym.VOID, yytext()); }
"while"		{ return new_symbol(sym.WHILE, yytext()); }
"extends"	{ return new_symbol(sym.EXTENDS, yytext()); }

"+" 		{ return new_symbol(sym.ADD, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.PERCENT, yytext()); }
"==" 		{ return new_symbol(sym.EQU, yytext()); }
"!=" 		{ return new_symbol(sym.NEQU, yytext()); }
">"			{ return new_symbol(sym.GRT, yytext()); }
">="		{ return new_symbol(sym.GEQU, yytext()); }
"<"			{ return new_symbol(sym.LSS, yytext()); }
"<="		{ return new_symbol(sym.LEQU, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"="			{ return new_symbol(sym.ASSIGN, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SC, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"."			{ return new_symbol(sym.POINT, yytext()); }
"(" 		{ return new_symbol(sym.OBRAC, yytext()); }
")" 		{ return new_symbol(sym.CBRAC, yytext()); }
"["			{ return new_symbol(sym.OSBRAC, yytext()); }
"]"			{ return new_symbol(sym.CSBRAC, yytext()); }
"{" 		{ return new_symbol(sym.OCBRAC, yytext()); }
"}"			{ return new_symbol(sym.CCBRAC, yytext()); }

"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

("true"|"false") 	{ return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol (sym.IDENT, yytext()); }
[0-9]+  			{ return new_symbol(sym.NUMBER, new Integer (yytext())); }
("'")[ -~]("'")  	{ return new_symbol(sym.CHAR_CONST, new Character (yytext().charAt(1))); }
("\"")[ -~]*("\"")  { return new_symbol(sym.STR_CONST, new String (yytext())); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }


