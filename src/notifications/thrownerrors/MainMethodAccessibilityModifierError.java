package notifications.thrownerrors;

import notifications.RichDiagnostic;
import notifications.diagnostics.AccessibilityContractDiagnostic;
import checker.MethEnv;
import compiler.Position;

public class MainMethodAccessibilityModifierError extends CompilerDiagnosticBuilder {
	
	private AccessibilityContractDiagnostic accessibilityContractDiagnostic;
	
	public MainMethodAccessibilityModifierError(MethEnv method, int missingModifierMask) {
		accessibilityContractDiagnostic = new AccessibilityContractDiagnostic();  
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getPos() {
		// TODO Auto-generated method stub
		return null;
	}

}
