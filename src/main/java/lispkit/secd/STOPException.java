/*
 * STOPException.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   February 2002
 *
 */

package lispkit.secd;

public class STOPException extends Exception {

  public STOPException() {}
  public STOPException(String msg) {
    super(msg);
  }

}