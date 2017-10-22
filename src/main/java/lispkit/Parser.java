package lispkit;

import lispkit.sexp.*;

import java.util.StringTokenizer;

import static lispkit.sexp.SymAtom.NIL;


/**
 * Very simple Parser that reads a "lisp"-expressions and parses it to a Lispkit / SECD
 * abstract syntax tree.
 * The parser has been taken from Fun4J and is thus ready to also parse SCHEME code including reader macro support.
 */
public class Parser {

    public static final String QUOTE = "quote";
    public static final String QUASIQUOTE = "quasiquote";
    public static final String UNQUOTE = "unquote";
    public static final String UNQUOTE_SPLICING = "unquote-splicing";
    public static final String LAMBDA = "lambda";
    public static final String MACRO = "macro";

	/**
	 * string containing all delimiters known to the tokenizer (i.e. our lexer)
	 */
    public static final String separators = "'` \t\n\r\f()[]{},@\"";
    
    /**
     * read a lisp expression from a string, return an abstract syntax tree
     * 
     * nil is parsed to NIL
     * Numbers are parsed to Integers / BigIntegers or BigDecimals
     * Symbols are parsed to Strings
     * Lists are parsed into Cons Binary trees
     * The Readmacro 'x is expanded to (quote x) 
     * The readmacro `(+ ,(+ 4 4) ,@(- 3 1)) is expanded to (quasiquote (+ (unquote (+ 4 4)) (unquote-splicing (- 3 1))))
     * 
     */
    public static SExp parse(String s) {
        StringTokenizer tokens = new StringTokenizer(s, separators, true);
        return parse(tokens);
    }

    /**
     * read lisp expression, return a lambda syntax tree 
     */
    public static SExp parse(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            return NIL;
        }
        String first = tokens.nextToken();

        if (first.equals("(")) return parseList(tokens, true);
        else if (first.equals(")") || first.equals("]")) return NIL;
        else if (first.equals("NIL")) return NIL;
        else if (first.equals("\\")) {return SymAtom.LAMBDA;}
        else if (first.equals("'")) {
            return parseReaderMacro(tokens, QUOTE);
        }
        else if (first.equals("`")) {
        	return parseReaderMacro(tokens, QUASIQUOTE);
        }
        else if (first.equals(",")) {
        	return parseReaderMacro(tokens, UNQUOTE);
        }
        else if (first.equals("@")) {
            return new SymAtom("@");
        }
        else if (first.equals("\"")) {
            return parseString(tokens);
        }
        else if ((first.charAt(0) == '-' && first.length() > 1) || (first.charAt(0) >= '0' && first.charAt(0) <= '9')) {
            return parseNumber(first);
        }
        else if (separators.contains(first)) return parse(tokens);
        
        else return new SymAtom(first);
    }


    private static NumAtom parseNumber(String first) {
        return new IntAtom(first);
    }

    
    /**
     * parse the next expression and wrap it with a macro call.
     * @param tokens the token stream 
     * @param macroSymbol the macro call to be constructed
     * @return
     */
    private static SExp parseReaderMacro(StringTokenizer tokens, String macroSymbol) {
    	SExp x = parse(tokens);
    	if (UNQUOTE.equals(macroSymbol) && "@".equals(x.toString())) {
    		return new Cons(new SymAtom(UNQUOTE_SPLICING), new Cons(parse(tokens), NIL));
    	}
    	else {
    		
    		return new Cons(macroSymbol, new Cons(x, NIL));
    	}
    }

    /**
     * parse a String from the token stream
     * @param tokens the token stream
     * @return the resulting string
     */
    private static SymAtom parseString(StringTokenizer tokens) {
        String result = "";
        while (tokens.hasMoreTokens()) {
            String next = tokens.nextToken();
            if ("\"".equals(next)) {
                break;
            }
            else {
                result += next;
            }
        }
        return new SymAtom("\"" + result + "\"");
    }

    /**
     * read a list of tokens, return a lambda syntax tree
     */
    private static SExp parseList(StringTokenizer tokens, boolean isFirstPosition) {
        String first = tokens.nextToken();        
        // distinguish DOT operator from pairs (x . y): 
        if (isFirstPosition && first.equals(".")) {
        	return new Cons(first, parseList(tokens, false));
        }
        if (first.equals("(")) return new Cons(parseList(tokens, true), parseList(tokens, false));
        else if (first.equals(")")) {
            return NIL;
        }
        else if (first.equals("NIL")) return new Cons(NIL, parseList(tokens, false));
        else if (first.equals("\\")) return new Cons(LAMBDA, parseList(tokens, false));
        else if (first.equals("'")) {
            SExp x = parseReaderMacro(tokens, QUOTE);
            SExp y = parseList(tokens, false);
            return new Cons(x, y);
        }
        else if (first.equals("`")) {
            SExp x = parseReaderMacro(tokens, QUASIQUOTE);
            SExp y = parseList(tokens, false);
            return new Cons(x, y);
        }
        else if (first.equals(",")) {
            SExp x = parseReaderMacro(tokens, UNQUOTE);
            SExp y = parseList(tokens, false);
            return new Cons(x, y);
        }
        else if (first.equals("\"")){
            return new Cons(parseString(tokens), parseList(tokens, false));
        }
        // parse a pair (x . y): 
        if (first.equals(".")){
            // skip next blank
        	tokens.nextToken();
            SExp second = parse(tokens);
        	// skip next )
        	tokens.nextToken();
            return second;        	
        }
        else if ((first.charAt(0) == '-' && first.length() > 1) || (first.charAt(0) >= '0' && first.charAt(0) <= '9')) {
            NumAtom number = parseNumber(first);
            return new Cons(number, parseList(tokens, false));
        }
        else if (separators.contains(first)) {
            return parseList(tokens, isFirstPosition);
        }
        else return new Cons(first, parseList(tokens, false));

    }

}