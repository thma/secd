/*
 * SExp.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 */

package lispkit.sexp;

/*
 * Class SExp is the root of the hierarchy of classes used to implement
 * "s-expressions" from LISP. It is, actually, an abstract class,
 * that is, pure instances of SExp should never appear in a program.
 * It has been implemented in the following way to ensure detection of
 * errors - it defines all methods found in its subclasses, and if
 * a method defined in SExp actually gets called, it means that there
 * had been an error.
 */
public class SExp {

  public int intValue() throws SExpException {
    throw new SExpException("intValue() -- IntAtom expected.");
  }

  public String stringValue() throws SExpException {
    throw new SExpException("stringValue() -- SymAtom expected.");
  }

  public SExp car() throws SExpException {
    throw new SExpException("car() -- Cons expected.");
  }
  public SExp cdr() throws SExpException {
    throw new SExpException("cdr() -- Cons expected.");
  }
  public SExp caar() throws SExpException {
    throw new SExpException("caar() -- Cons expected.");
  }
  public SExp cadr() throws SExpException {
    throw new SExpException("cadr() -- Cons expected.");
  }
  public SExp cdar() throws SExpException {
    throw new SExpException("cdar() -- Cons expected.");
  }
  public SExp cddr() throws SExpException {
    throw new SExpException("cddr() -- Cons expected.");
  }
  public SExp caadr() throws SExpException {
    throw new SExpException("caadr() -- Cons expected.");
  }
  public SExp caddr() throws SExpException {
    throw new SExpException("caddr() -- Cons expected.");
  }
  public SExp cdadr() throws SExpException {
    throw new SExpException("cdadr() -- Cons expected.");
  }
  public SExp cdddr() throws SExpException {
    throw new SExpException("cdddr() -- Cons expected.");
  }
  public SExp cadddr() throws SExpException {
    throw new SExpException("cadddr() -- Cons expected.");
  }
  public SExp cddddr() throws SExpException {
    throw new SExpException("cddddr() -- Cons expected.");
  }

  public boolean eq(SExp se) throws SExpException {
    throw new SExpException("eq() -- concrete SExp expected.");
  }
  public boolean eq(String s) throws SExpException {
    throw new SExpException("eq() -- concrete SExp expected.");
  }
  public boolean eq(int i) throws SExpException {
    throw new SExpException("eq() -- concrete SExp expected.");
  }

  public SExp rplaca(SExp carSExp) throws SExpException {
    throw new SExpException("rplaca() -- Cons expected.");
  }

}