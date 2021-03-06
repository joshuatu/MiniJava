// Copyright (c) Mark P Jones, Portland State University
// Subject to conditions of distribution and use; see LICENSE for details
// February 3 2008 11:12 AM

package syntax;

import compiler.*;
import checker.*;
import codegen.*;
import interp.*;

/** Provides a representation for expressions.
 */
public abstract class Expression extends Syntax {
    public Expression(Position pos) {
        super(pos);
    }

    /** Check this expression and return an object that describes its
     *  type (or throw an exception if an unrecoverable error occurs).
     */
    public abstract Type typeOf(Context ctxt, VarEnv env)
      throws Diagnostic;

    /** A simple utility function to check that a type has the required value
     *  and report an error if that test fails.
     */
    protected void required(Context ctxt, String where, Type got, Type wanted) {
        if (!got.equal(wanted)) {
            ctxt.report(new Failure(pos, where  + " has type " +
                                         got    + "; a value of type " +
                                         wanted + " is required"));
        }           
    }

    /** This value is used as the depth for an expression that can have
     *  side effects, and for which a change of evaluation order might
     *  give the wrong behavior.  To test for expressions with a possible
     *  side effect, you should determine whether getDepth() returns a
     *  value that is greater than or equal to DEEP (testing for equality
     *  with DEEP is *not* enough).
     */
    protected final static int DEEP = 1000;

    /** Return the depth of this expression tree as a measure of its
     *  complexity.  By default, we treat all expressions as having
     *  a potential side-effect, and thus return the constant DEEP in
     *  this base class.
     */
    int getDepth() {
        return DEEP;
    }

    /** Generate code to evaluate this expression and
     *  leave the result in the specified free variable.
     */
    public abstract void compileExpr(Assembly a, int free);
    void compileExpr(Assembly a) {
        compileExpr(a, 0);
    }

    void compileExprOp(Assembly a, String op, int free) {
        a.spill(free+1);
        compileExpr(a, free+1);
        a.emit(op, a.reg(free+1), a.reg(free));
        a.unspill(free+1);
    }
    void compileExprOp(Assembly a, String op) {
        compileExprOp(a, op, 0);
    }

    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is true.
     */
    void branchTrue(Assembly a, String lab, int free) {
        compileExpr(a, free);
        a.emit("orl", a.reg(free), a.reg(free));
        a.emit("jnz", lab);
    }
    void branchTrue(Assembly a, String lab) {
        branchTrue(a, lab, 0);
    }

    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is false.
     */
    void branchFalse(Assembly a, String lab, int free) {
        compileExpr(a, free);
        a.emit("orl", a.reg(free), a.reg(free));
        a.emit("jz", lab);
    }
    void branchFalse(Assembly a, String lab) {
        branchFalse(a, lab, 0);
    }

    /** Evaluate this expression.
     */
    public abstract Value eval(State st);
}
