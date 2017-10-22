/*
 * SELInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class SELInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.d = new Cons(state.c.cdddr(), state.d);
    if (state.s.car().eq(SymAtom.T))
      state.c = state.c.cadr();
    else
      state.c = state.c.caddr();
    state.s = state.s.cdr();
  }

}
