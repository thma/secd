/*
 * SymAtom.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 */

package lispkit.sexp;

/*
 * Class SymAtom implements the "symbolic atom" from LispKit LISP,
 * as a Java "String".
 */
public class SymAtom extends Atom {

  public SymAtom(String value) {
    this.value = new String(value);
  }

  public String stringValue() { return this.value; }

  /* Returns "true" if objects are equal atoms, otherwise "false".
   */
  public boolean eq(String s) {
    return this.value.equals(s);
  }
  public boolean eq(int i) {
    return false;
  }
  public boolean eq(SExp se) {
    if (se instanceof SymAtom)
      return this.value.equals(((SymAtom)se).stringValue());
    else
      return false;
  }

  /* Provides the String representation of "this" symbolic atom.
   */
  public String toString() {
    return this.value;
  }

  /* Some SymAtom constants that have special meaning in LISP,
   * and will be used frequently */
  public static final SExp NIL = new SymAtom("NIL");
  public static final SExp T   = new SymAtom("T");
  public static final SExp F   = new SymAtom("F");
  public static final SExp LAMBDA   = new SymAtom("lambda");

  private String value;

}