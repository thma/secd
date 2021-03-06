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

import lispkit.secd.*;
import lispkit.sexp.Cons;
import lispkit.sexp.SExp;
import lispkit.sexp.SymAtom;

/**
 * Class Interpreter implements the interpreter for the virtual
 * SECD machine.
 */
class Interpreter {

    static final int STOP = 21;

    /**
     * the SECD instruction set.
     * the index in the array matches to the instruction in value generated by the LispKit compiler.
     * E.g. LDC is emitted by the compiler as '2', which corresponds to LispKit 'QUOTE' command.
     */
    static Instruction[] instructions = {
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

    /**
     *  Executes the SECD program with the given arguments.
     */
    static SExp exec(SExp code, SExp argList) {
        State state = new State(new Cons(argList, SymAtom.NIL), SymAtom.NIL, code, SymAtom.NIL);

        int instructionIndex = state.c.car().intValue();
        while (instructionIndex != STOP) {
            instructions[instructionIndex].execute(state);
            instructionIndex = state.c.car().intValue();
        }
        return state.s.car();
    }

}