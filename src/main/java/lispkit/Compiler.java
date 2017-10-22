/*
 * Compiler.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 * Modified:
 *   February 2002 - moved all error checking to LispSyntaxChecker
 *
 */

package lispkit;

import lispkit.sexp.*;

/*
 * Class Compiler implements the compiler from LispKit LISP to
 * SECD machine code.
 */
class Compiler {

  private static int errCount;

  /* Compiles the given LispKit LISP program, and returns
   * the appropriate SECD machine code.
   */
  static SExp compile(SExp e) {
    errCount = 0;
    return comp(e, SymAtom.NIL, new Cons(4, new Cons(21, SymAtom.NIL)));
  }


  /* Returns "true" if there were no errors during compilation,
   * otherwise "false". Should be called after compile().
   */
  static boolean Successful() {
    return errCount == 0;
  }

  /*
   * The following procedures implement the actual compilation.
   */

  /* Returns SECD machine code compiled from the LispKit LISP
   * expression `e' with respect to the namelist `n' (e*n),
   * with code from `c' appended to it.
   */
  private static SExp comp(SExp e, SExp n, SExp c) {
    try {
      if (e instanceof Atom)
        return new Cons(1, new Cons(location(e, n), c));
      else if (e.car().eq("QUOTE"))
        return new Cons(2, new Cons(e.cadr(), c));
      else if (e.car().eq("EQ"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(14, c)));
      else if (e.car().eq("ADD"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(15, c)));
      else if (e.car().eq("SUB"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(16, c)));
      else if (e.car().eq("MUL"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(17, c)));
      else if (e.car().eq("DIV"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(18, c)));
      else if (e.car().eq("REM"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(19, c)));
      else if (e.car().eq("LEQ"))
        return comp(e.cadr(), n, comp(e.caddr(), n, new Cons(20, c)));
      else if (e.car().eq("CAR"))
        return comp(e.cadr(), n, new Cons(10, c));
      else if (e.car().eq("CDR"))
        return comp(e.cadr(), n, new Cons(11, c));
      else if (e.car().eq("ATOM"))
        return comp(e.cadr(), n, new Cons(12, c));
      else if (e.car().eq("CONS"))
        return comp(e.caddr(), n, comp(e.cadr(), n, new Cons(13, c)));
      else if (e.car().eq("IF")) {
        SExp thenPt = comp(e.caddr(), n, new Cons(9, SymAtom.NIL));
        SExp elsePt = comp(e.cadddr(), n, new Cons(9, SymAtom.NIL));
        return comp(e.cadr(), n, new Cons(8, new Cons(thenPt, new Cons(elsePt, c))));
      }
      else if (e.car().eq("LAMBDA")) {
        SExp body = comp(e.caddr(), new Cons(e.cadr(), n), new Cons(5, SymAtom.NIL));
        return new Cons(3, new Cons(body, c));
      }
      else if (e.car().eq("LET")) {
        SExp m = new Cons(vars(e.cddr()), n);
        SExp args = exprs(e.cddr());
        SExp body = comp(e.cadr(), m, new Cons(5, SymAtom.NIL));
        return complis(args, n, new Cons(3, new Cons(body, new Cons(4, c))));
      }
      else if (e.car().eq("LETREC")) {
        SExp m = new Cons(vars(e.cddr()), n);
        SExp args = exprs(e.cddr());
        SExp body = comp(e.cadr(), m, new Cons(5, SymAtom.NIL));
        return new Cons(6, complis(args, m, new Cons(3, new Cons(body, new Cons(7, c)))));
      }
      else
        return complis(e.cdr(), n, comp(e.car(), n, new Cons(4, c)));
    }
    catch (SExpException ex) {
      LispSynError(0, e, SymAtom.NIL); // unknown error
      return c;
    }
  }

  /* Returns SECD machine code compiled from the list of LispKit LISP
   * expressions `e', of the form (e1 e2 ... ek), with `c' appended
   * to it. The resulting code is of the form
   *   (LDC NIL)|ek*n|(CONS)| ... |e1*n|(CONS)|c
   */
  private static SExp complis(SExp e, SExp n, SExp c) throws SExpException {
    if (e.eq(SymAtom.NIL))
      return new Cons(2, new Cons(SymAtom.NIL, c));
    else
      return complis(e.cdr(), n, comp(e.car(), n, new Cons(13, c)));
  }

  /* Retreives the list of variable names from the list of
   * (name.LispKit LISP expression) Cons pairs.
   */
  private static SExp vars(SExp d) throws SExpException {
    SExp var, list;
    if (d.eq(SymAtom.NIL))
      return SymAtom.NIL;
    else {
      var = d.caar();
      list = vars(d.cdr());
      return new Cons(var, list);
    }
  }

  /* Retreives the list of LispKit LISP expressions from the list of
   * (name.LispKit LISP expression) Cons pairs.
   */
  private static SExp exprs(SExp d) throws SExpException {
    if (d.eq(SymAtom.NIL))
      return SymAtom.NIL;
    else
      return new Cons(d.cdar(), exprs(d.cdr()));
  }

  /* Returns the location of name `x' in namelist `n'.
   * Location is a Cons of the form (p.q), where p is the position
   * in `n' of the sublist `x' is in, and q is the position
   * of `x' in that sublist.
   */
  private static SExp location(SExp x, SExp n) throws SExpException {
    if (member(x, n.car()))
      return new Cons(0, position(x, n.car()));
    else {
      SExp z = location(x, n.cdr());
      return new Cons(z.car().intValue() + 1, z.cdr());
    }
  }

  /* Returns the position of name `x' in list `a'.
   */
  private static int position(SExp x, SExp a) throws SExpException {
    if (x.eq(a.car()))
      return 0;
    else
      return 1 + position(x, a.cdr());
  }

  /* Returns "true" if `x' is a member of list `a' otherwise "false".
   */
  private static boolean member(SExp x, SExp a) {
    try {
      if (a.eq(SymAtom.NIL))
        return false;
      else if (x.eq(a.car()))
         return true;
      else
        return member(x, a.cdr());
    }
    catch (SExpException ex) {
      return false;
    }
  }

  /* Signals a LISP syntax error.
   */
  private static void LispSynError(int n, SExp e, SExp num) {
    errCount++;
    System.out.println(n + " : " +  e + " : " + num);
  }


}