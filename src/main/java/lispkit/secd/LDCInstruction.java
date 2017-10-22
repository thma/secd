/*
 * LDCInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class LDCInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.s = new Cons(state.c.cadr(), state.s);
    state.c = state.c.cddr();
  }

}
