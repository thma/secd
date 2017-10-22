/*
 * Cons.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 */

package lispkit.sexp;

/*
 * Class Cons implements the "constructed expression" from LispKit LISP.
 * A constructed expression consists of two s-expressions:
 * a head ("car") and a tail ("cdr").
 */
public class Cons extends SExp {

  public Cons(SExp carSExp, SExp cdrSExp) {
    this.carSExp = carSExp;
    this.cdrSExp = cdrSExp;
  }
  public Cons(int i, SExp cdrSExp) {
    this.carSExp = new IntAtom(i);
    this.cdrSExp = cdrSExp;
  }
  public Cons(String s, SExp cdrSExp) {
    this.carSExp = new SymAtom(s);
    this.cdrSExp = cdrSExp;
  }
  public Cons(int i, int j) {
    this.carSExp = new IntAtom(i);
    this.cdrSExp = new IntAtom(j);
  }

  /* Returns the head of "this" constructed expression.
   */
  public SExp car() { return carSExp; }

  /* Returns the tail of "this" constructed expression.
   */
  public SExp cdr() { return cdrSExp; }

  /*
   * The following methods are implemented for convenience only -
   * to make the code more compact.
   */
  public SExp caar() throws SExpException { return this.car().car(); }
  public SExp cadr() throws SExpException { return this.cdr().car(); }
  public SExp cdar() throws SExpException { return this.car().cdr(); }
  public SExp cddr() throws SExpException { return this.cdr().cdr(); }
  public SExp caadr() throws SExpException { return this.cdr().car().car(); }
  public SExp caddr() throws SExpException { return this.cdr().cdr().car(); }
  public SExp cdadr() throws SExpException { return this.cdr().car().cdr(); }
  public SExp cdddr() throws SExpException { return this.cdr().cdr().cdr(); }
  public SExp cadddr() throws SExpException { return this.cdr().cdr().cdr().car(); }
  public SExp cddddr() throws SExpException { return this.cdr().cdr().cdr().cdr(); }

  /* Returns "true" if objects are equal atoms, otherwise "false".
   */
  public boolean eq(SExp se)  { return false; }
  public boolean eq(String s) { return false; }
  public boolean eq(int i)    { return false; }

  /* Replaces the car part of "this" constructed expression.
   */
  public SExp rplaca(SExp carSExp) {
    this.carSExp = carSExp;
    return this;
  }

  /* Provides the String representation of "this" constructed expression.
   */
  public String toString() {
    String s = this.car().toString();
    SExp tmpSExp = this.cdr();

    while (tmpSExp instanceof Cons) {
      s = s + " " + ((Cons)tmpSExp).car().toString();
      tmpSExp = ((Cons)tmpSExp).cdr();
    }
    if (tmpSExp != SymAtom.NIL)
      s = s + " . " + tmpSExp.toString();
    return "(" + s + ")";
  }

  private SExp carSExp;
  private SExp cdrSExp;

}