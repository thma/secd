/*
 * SExpException.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 */

package lispkit.sexp;

/*
 * Class SExpException implements the exception that will be thrown
 * if there happens to be an error while manipulating s-expressions.
 */
public class SExpException extends RuntimeException {

  public SExpException() {}
  public SExpException(String msg) {
    super(msg);
  }

}