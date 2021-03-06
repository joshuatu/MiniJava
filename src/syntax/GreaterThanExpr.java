// Copyright (c) Mark P Jones, Portland State University
// Subject to conditions of distribution and use; see LICENSE for details
// February 3 2008 11:12 AM

package syntax;

import compiler.Position;
import codegen.*;
import interp.*;

/** Provides a representation for greater than comparsions.
 */
public final class GreaterThanExpr extends RelOpExpr {
    public GreaterThanExpr(Position pos, Expression left, Expression right) {
        super(pos, left, right);
    }

    /** Generate code to evaluate this expression and
     *  leave the result in the specified free variable.
     */ 
    public void compileExpr(Assembly a, int free) {
        compileComp(a, "jg", free);
    }   
    
    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is false.
     */
    void branchFalse(Assembly a, String lab, int free) {
        branchCond(a, "jng", lab, free);
    }

    /** Generate code to evaluate this expression and
     *  branch to a specified label if the result is true.
     */
    void branchTrue(Assembly a, String lab, int free) {
        branchCond(a, "jg", lab, free);
    }

    /** Evaluate this expression.
     */
    public Value eval(State st) {
        // Should handle comparisons other than integers ...
        return BoolValue.make(left.eval(st).getInt() > right.eval(st).getInt());
    }
}
