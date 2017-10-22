/*
 * ADDInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class ADDInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.s = new Cons(state.s.cadr().intValue()+ state.s.car().intValue(), state.s.cddr());
    state.c = state.c.cdr();
  }

}
