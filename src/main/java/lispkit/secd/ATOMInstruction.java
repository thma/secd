/*
 * ATOMInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class ATOMInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    if (state.s.car() instanceof Atom)
      state.s = new Cons(SymAtom.T, state.s.cdr());
    else
      state.s = new Cons(SymAtom.F, state.s.cdr());
    state.c = state.c.cdr();
  }

}
