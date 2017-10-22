/*
 * APInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class APInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.d = new Cons(state.s.cddr(), new Cons(state.e, new Cons(state.c.cdr(), state.d)));
    state.e = new Cons(state.s.cadr(), state.s.cdar());
    state.c = state.s.caar();
    state.s = SymAtom.NIL;
  }

}
