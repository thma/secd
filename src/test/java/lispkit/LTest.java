package lispkit;


import lispkit.sexp.Cons;
import lispkit.sexp.SExp;
import lispkit.sexp.SExpException;
import org.junit.Test;

import java.io.InputStream;

import static lispkit.sexp.SymAtom.NIL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LTest {

    SExp parseFromFile(String inFileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(inFileName);
        String inputStreamString = new java.util.Scanner(is,"UTF-8").useDelimiter("\\A").next();
        return Parser.parse(inputStreamString);
    }

    SExp parse(String input) {
        return Parser.parse(input);
    }



    @Test
    public void bootstrapTheCompilerShouldWork() {
        SExp compilerSourceCode = parseFromFile("compile.lkl");
        SExp bootstrappedCompiler = Compiler.compile(compilerSourceCode);

        SExp verifiedCompiler = Interpreter.exec(bootstrappedCompiler, new Cons(compilerSourceCode, NIL));
        assertEquals(verifiedCompiler.toString(), bootstrappedCompiler.toString());
    }


    /**
     * Compiles the Compiler from source and returns the emitted SECD code.
     * @return the compiled compiler.
     */
    private SExp bootsTrapCompiler() {
        SExp compilerSourceCode = parseFromFile("compile.lkl");
        return Compiler.compile(compilerSourceCode);
    }

    @Test
    public void bootstrapMultipleTimesShouldWork() {
        SExp initialCompiler = bootsTrapCompiler();
        SExp compilerSourceCode = new Cons(parseFromFile("compile.lkl"), NIL);

        long start = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            SExp bootstrappedCompiler = Interpreter.exec(initialCompiler, compilerSourceCode);
            assertEquals(initialCompiler.toString(), bootstrappedCompiler.toString());
            initialCompiler = bootstrappedCompiler;
        }
        System.out.println("duration: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void compileAndRunFactorialShouldWork() throws SExpException {
        SExp compiler = bootsTrapCompiler();
        SExp sourceFactorial = new Cons(parseFromFile("fac.lkl"), NIL);
        SExp codeFactorial = Interpreter.exec(compiler, sourceFactorial);
        SExp result = Interpreter.exec(codeFactorial, parse("(10)"));
        assertTrue(result.eq(parse("3628800")));
    }

    @Test
    public void compileAndRunTakeShouldWork() throws SExpException {
        SExp compiler = bootsTrapCompiler();
        SExp source = new Cons(parseFromFile("take.lkl"), NIL);
        SExp code = Interpreter.exec(compiler, source);
        SExp result = Interpreter.exec(code, parse("(18 12 6)"));
        assertTrue(result.eq(parse("7")));
    }

    @Test
    public void compileAndRunFibShouldWork() throws SExpException {
        SExp compiler = bootsTrapCompiler();
        SExp source = new Cons(parseFromFile("fib.lkl"), NIL);
        SExp code = Interpreter.exec(compiler, source);
        SExp result = Interpreter.exec(code, parse("(10)"));
        System.out.println(result);
        assertTrue(result.eq(parse("89")));
    }


}
