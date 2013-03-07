package org.uva.sea.ql.gui.qlform;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QLElsePanel extends QLConditionalBody {
	
	private final static boolean isElseBody = true;

	public QLElsePanel(List<JPanel> questionPanelList,Expr condition,VariableUpdater varUpdater,Map<String,Value> runTimeValues){
		super(questionPanelList,condition,varUpdater,runTimeValues, isElseBody);
	}

	@Override
	public boolean isConditionalBody() {
		return true;
	}

	@Override
	public boolean isPanel() {
		return false;
	}
	
	


}
