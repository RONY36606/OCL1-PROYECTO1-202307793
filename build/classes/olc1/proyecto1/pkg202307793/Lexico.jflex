package olc1.proyecto1.pkg202307793;
import java_cup.runtime.*;
import java.util.LinkedList;
import Clases.token; 
import Clases.errorLexico;
%%

//Directrices
%class Lexico
%public
%line
%char
%cup
%unicode
%ignorecase

%{
    LinkedList<errorLexico> listaErrores = new LinkedList<>();
    LinkedList<token> listaTokens = new LinkedList<>();
    public LinkedList<token> getTokens() {
        return listaTokens;
    }

    public LinkedList<errorLexico> getErrores() {
        return listaErrores;
    }

    public void limpiarListas() {
        listaTokens.clear();
        listaErrores.clear();
    }

%}

%init{
    yyline = 1;
    yychar = 1;
%init}

// ================= EXPRESIONES REGULARES =================
// CORRECCIÓN: DD ahora solo matchea decimales con punto, D matchea enteros puros
// Antes DD = [0-9]+("."[0-9]+)? absorbía también los enteros y D nunca se alcanzaba
D       = [0-9]+
DD      = [0-9]+"."[0-9]+
ID      = [A-Za-z_][A-Za-z0-9_]*
CADENA  = \"([^\"\\]|\\.)*\"

%%

// ================= COMENTARIOS =================

// Comentario de una línea: ignorar
"##".* {
    System.out.println("Comentario de una línea (esto será ignorado): " + yytext());
    // no devolvemos token al parser
    /* skip */
}

// Comentario multilínea: ignorar
"#*"([^*]|\*+[^#])*"#*" {
    System.out.println("Comentario multilínea (esto será ignorado): " + yytext());
    // no devolvemos token al parser
    /* skip */
}

//============== VALORES BOOLEANOS ============
"true" {
    token t = new token("TRUE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Booleano TRUE reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.TRUE, (int)yyline, (int)yychar, Boolean.TRUE);
}

"false" {
    token t = new token("FALSE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Booleano FALSE reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.FALSE, (int)yyline, (int)yychar, Boolean.FALSE);
}

// ================= PALABRAS RESERVADAS =================
"Evaluar" {
    token t = new token("REVALUAR", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline +  " - " + yychar + " ->> " +  yytext());
    return new Symbol(sym.REVALUAR, (int)yyline, (int)yychar+1, yytext()); 
}

"int" {
    token t = new token("INT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.INT, (int)yyline, (int)yychar, yytext());
}

"float" {
    token t = new token("FLOAT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.FLOAT, (int)yyline, (int)yychar, yytext());
}

"bool" {
    token t = new token("BOOL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.BOOL, (int)yyline, (int)yychar, yytext());
}

"string" {
    token t = new token("STRING", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.STRING, (int)yyline, (int)yychar, yytext());
}

"array" {
    token t = new token("ARRAY", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.ARRAY, (int)yyline, (int)yychar, yytext());
}

"object" {
    token t = new token("OBJECT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.OBJECT, (int)yyline, (int)yychar, yytext());
}

"null" {
    token t = new token("NULL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.NULL, (int)yyline, (int)yychar, yytext());
}

"database" {
    token t = new token("DATABASE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->< " + yytext());
    return new Symbol(sym.DATABASE, (int)yyline, (int)yychar, yytext());
}

"use" {
    token t = new token("USE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.USE, (int)yyline, (int)yychar, yytext());
}

"table" {
    token t = new token("TABLE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.TABLE, (int)yyline, (int)yychar, yytext());
}

"read" {
    token t = new token("READ", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.READ, (int)yyline, (int)yychar, yytext());
}

"fields" {
    token t = new token("FIELDS", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.FIELDS, (int)yyline, (int)yychar, yytext());
}

"filter" {
    token t = new token("FILTER", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.FILTER, (int)yyline, (int)yychar, yytext());
}

"store" {
    token t = new token("STORE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.STORE, (int)yyline, (int)yychar, yytext());
}

"at" {
    token t = new token("AT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.AT, (int)yyline, (int)yychar, yytext());
}

"export" {
    token t = new token("EXPORT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.EXPORT, (int)yyline, (int)yychar, yytext());
}

"add" {
    token t = new token("ADD", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.ADD, (int)yyline, (int)yychar, yytext());
}

"update" {
    token t = new token("UPDATE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.UPDATE, (int)yyline, (int)yychar, yytext());
}

"set" {
    token t = new token("SET", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.SET, (int)yyline, (int)yychar, yytext());
}

"clear" {
    token t = new token("CLEAR", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Palabra reservada reconocida: " + yyline + " - " + yychar + " ->> " + yytext());
    return new Symbol(sym.CLEAR, (int)yyline, (int)yychar, yytext());
}

// ================= NÚMEROS =================
{DD} {
    token t = new token("DECIMAL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Token reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.DECIMAL, (int)yyline, (int)yychar, yytext());
}

{D} {
    token t = new token("ENTERO", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Token reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.ENTERO, (int)yyline, (int)yychar, yytext());
}

// ================= SÍMBOLOS DE AGRUPACIÓN =================
";" {
    token t = new token("PTCOMA", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.PTCOMA, (int)yyline, (int)yychar, yytext());
}

"," {
    token t = new token("COMA", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.COMA, (int)yyline, (int)yychar, yytext());
}

":" {
    token t = new token("DOSPUNTOS", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.DOSPUNTOS, (int)yyline, (int)yychar, yytext());
}

"(" {
    token t = new token("PARIZQ", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.PARIZQ, (int)yyline, (int)yychar, yytext());
}

")" {
    token t = new token("PARDER", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.PARDER, (int)yyline, (int)yychar, yytext());
}

"[" {
    token t = new token("CORIZQ", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.CORIZQ, (int)yyline, (int)yychar, yytext());
}

"]" {
    token t = new token("CORDER", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.CORDER, (int)yyline, (int)yychar, yytext());
}

"{" {
    token t = new token("LLAIZQ", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.LLAIZQ, (int)yyline, (int)yychar, yytext());
}

"}" {
    token t = new token("LLADER", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Simbolo reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.LLADER, (int)yyline, (int)yychar, yytext());
}

// ================= OPERADORES RELACIONALES =================

"==" {
    token t = new token("IGUAL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.IGUAL, (int)yyline, (int)yychar, yytext());
}

"!=" {
    token t = new token("DIFERENTE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.DIFERENTE, (int)yyline, (int)yychar, yytext());
}

">=" {
    token t = new token("MAYORIGUAL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.MAYORIGUAL, (int)yyline, (int)yychar, yytext());
}

"<=" {
    token t = new token("MENORIGUAL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.MENORIGUAL, (int)yyline, (int)yychar, yytext());
}

"=" {
    token t = new token("IGUAL_SIMPLE", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.IGUAL_SIMPLE, (int)yyline, (int)yychar, yytext());
}

">" {
    token t = new token("MAYOR", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.MAYOR, (int)yyline, (int)yychar, yytext());
}

"<" {
    token t = new token("MENOR", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.MENOR, (int)yyline, (int)yychar, yytext());
}

// ================= OPERADORES LÓGICOS =================
"&&" {
    token t = new token("AND", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador lógico AND reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.AND, (int)yyline, (int)yychar, yytext());
}

"||" {
    token t = new token("OR", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador lógico OR reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.OR, (int)yyline, (int)yychar, yytext());
}

"!" {
    token t = new token("NOT", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Operador lógico NOT reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.NOT, (int)yyline, (int)yychar, yytext());
}

//==================== ID =====================
// ID va después de todas las palabras reservadas para que no las absorba
{ID} {
    token t = new token("ID", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Identificador reconocido: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.ID, (int)yyline, (int)yychar, yytext());
}

//=============== CADENAS DE TEXTO ============
{CADENA} {
    token t = new token("STRING_LITERAL", yytext(), (int)yyline, (int)yychar);
    listaTokens.add(t);
    System.out.println("Cadena reconocida: " + yyline + " - " + yychar + " -> " + yytext());
    return new Symbol(sym.STRING_LITERAL, (int)yyline, (int)yychar, yytext());
}

// ================= ESPACIOS Y SALTOS DE LÍNEA =================
[\t\r\n\f ]          { }

// ================= ERROR LÉXICO =================
. {
    errorLexico e = new errorLexico(yytext(), (int)yyline, (int)yychar, "Símbolo no reconocido");
    listaErrores.add(e);
    System.out.println(
        "Error Léxico: " + yytext() +
        " en línea: " + yyline +
        " columna: " + yychar
    );
}
