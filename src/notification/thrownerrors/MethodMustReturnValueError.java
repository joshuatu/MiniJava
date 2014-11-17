package notification.thrownerrors;

import notifications.diagnostics.MissingReqiredStatementDiagnostic;
import notifications.thrownerrors.CompilerDiagnosticBuilder;
import syntax.Return;
import checker.VarEnv;

public class MethodMustReturnValueError extends CompilerDiagnosticBuilder {

	private MissingReqiredStatementDiagnostic missingRequiredStatementDiagnostic;

	public MethodMustReturnValueError(Return return1, VarEnv env) {
		this.missingRequiredStatementDiagnostic = new MissingReqiredStatementDiagnostic(return1, env);
	}

}
