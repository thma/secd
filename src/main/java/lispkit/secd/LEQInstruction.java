/*
 * LEQInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class LEQInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    if (state.s.cadr().intValue() <= state.s.car().intValue())
      state.s = new Cons(SymAtom.T, state.s.cddr());
    else
      state.s = new Cons(SymAtom.F, state.s.cddr());
    state.c = state.c.cdr();
  }

}
