package lispkit.secd;

import lispkit.sexp.Cons;
import lispkit.sexp.SExp;
import lispkit.sexp.SymAtom;

public class ConsStack implements Stack<SExp> {

    private SExp stack;

    public ConsStack() {
        stack = SymAtom.NIL;
    }

    @Override
    public SExp pop() {
        SExp result = stack.car();
        stack = stack.cdr();
        return result;
    }

    @Override
    public SExp peek() {
        if (empty()) {
            return SymAtom.NIL;
        } else {
            return stack.car();
        }
    }

    @Override
    public SExp push(SExp element) {
        stack = new Cons(element, stack);
        return element;
    }

    @Override
    public boolean empty() {
        return stack == SymAtom.NIL;
    }
}
