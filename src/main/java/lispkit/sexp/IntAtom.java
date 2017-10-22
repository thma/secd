/*
 * IntAtom.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 */

package lispkit.sexp;

/*
 * Class IntAtom implements the "numeric atom" from LispKit LISP,
 * as a Java "int".
 */
public class IntAtom extends NumAtom {

  public IntAtom(int value) {
    this.value = value;
  }
  public IntAtom(String s) throws NumberFormatException {
    this.value = Integer.parseInt(s);
  }

  public int intValue() { return value; }

  /* Returns "true" if objects are equal atoms, otherwise "false".
   */
  public boolean eq(String s) {
    return false;
  }
  public boolean eq(int i) {
    return this.value == i;
  }
  public boolean eq(SExp se) throws SExpException {
    if (se instanceof IntAtom)
      return this.value == se.intValue();
    else
      return false;
  }

  /* Provides the String representation of "this" int atom.
   */
  public String toString() {
    return Integer.toString(value);
  }

  private int value;

}