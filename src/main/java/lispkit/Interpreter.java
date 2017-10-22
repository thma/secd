/*
 * Interpreter.java
 *
 * Title:  An Implementation of the Programming Language LispKit LISP in Java
 * Author: Milos Radovanovic, University of Novi Sad
 * Date:   August-September 2001
 *
 * Modified:
 *   February 2002 - eliminated the switch statement
 *
 */

package lispkit;

import lispkit.sexp.*;
import lispkit.secd.*;

/*
 * Class Interpreter implements the interpreter for the virtual
 * SECD machine.
 */
class Interpreter {

    static Instruction[] instr = {
            null,
            new LDInstruction(),
            new LDCInstruction(),
            new LDFInstruction(),
            new APInstruction(),
            new RTNInstruction(),
            new DUMInstruction(),
            new RAPInstruction(),
            new SELInstruction(),
            new JOINInstruction(),
            new CARInstruction(),
            new CDRInstruction(),
            new ATOMInstruction(),
            new CONSInstruction(),
            new EQInstruction(),
            new ADDInstruction(),
            new SUBInstruction(),
            new MULInstruction(),
            new DIVInstruction(),
            new REMInstruction(),
            new LEQInstruction(),
            new STOPInstruction()
    };

    /* Executes the SECD program with the given arguments.
     */
    static SExp exec(SExp code, SExp argList) {
        State state = new State(new Cons(argList, SymAtom.NIL), SymAtom.NIL, code, SymAtom.NIL);
        try {
            while (true) {
                instr[state.c.car().intValue()].execute(state);
            }
        } catch (STOPException stopEx) {
            return state.s.car();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println("S: " + state.s);
            //System.out.println("E: " + state.e);
            System.out.println("C: " + state.c);
            //System.out.println("D: " + state.d);

        }
        return SymAtom.NIL;
    }

    /* Signals a SECD machine runtime error.
     */
    private static void RuntimeError() {
        System.out.println("-- SECD machine runtime error");
        //System.exit(1);
    }

}