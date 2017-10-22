/*
 * LDInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class LDInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.w = state.e;
    for (int i = 1; i<= state.c.caadr().intValue(); i++)
      state.w = state.w.cdr();
    state.w = state.w.car();
    for (int i = 1; i<= state.c.cdadr().intValue(); i++)
      state.w = state.w.cdr();
    state.w = state.w.car();
    state.s = new Cons(state.w, state.s);
    state.c = state.c.cddr();
  }

}
