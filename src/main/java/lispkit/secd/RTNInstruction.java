/*
 * RTNInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class RTNInstruction implements Instruction {

  public void execute(State state) throws SExpException {
    state.s = new Cons(state.s.car(), state.d.car());
    state.e = state.d.cadr();
    state.c = state.d.caddr();
    state.d = state.d.cdddr();
  }

}
