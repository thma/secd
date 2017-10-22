/*
 * Reg.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

import lispkit.sexp.*;

/**
 * This class represents the state of the SECD machine.
 * it hold the well known registers s, e, c, d and a working register w.
 */
public class State {
  public SExp s, e, c, d, w;

  public State(SExp s, SExp e, SExp c, SExp d) {
    this.s = s;
    this.e = e;
    this.c = c;
    this.d = d;
  }
}
