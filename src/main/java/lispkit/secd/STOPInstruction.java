/*
 * STOPInstruction.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

public class STOPInstruction implements Instruction {

  public void execute(State state) throws STOPException {
    throw new STOPException();
  }

}
