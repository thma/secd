/*
 * RAPInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class RAPInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.d = new Cons(state.s.cddr(),
                     new Cons(state.e.cdr(),
                              new Cons(state.c.cdr(), state.d)));
    state.e = state.s.cdar();
    state.e = state.e.rplaca(state.s.cadr());
    state.c = state.s.caar();
    state.s = SymAtom.NIL;
  }

}
