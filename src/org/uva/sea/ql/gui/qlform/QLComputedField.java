package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QLComputedField extends JTextField implements Observer,QLAtomComponent {
	private final VariableUpdater varUpdater;
    private final Map<String,Value> runTimeValues;
    private final Expr expr;
    private final String varName;
	

	public QLComputedField(ComputedQuestion qlElement,VariableUpdater varUpdater,Map<String,Value> runTimeValues) {
		super(8);
		this.varUpdater=varUpdater;
		this.varUpdater.addObserver(this);
		this.expr=qlElement.getExpr();
		this.varName=qlElement.getId().getName();
		this.runTimeValues = runTimeValues;
		setSettings();
	}



	private void setSettings() {
		Value initValue = ExprEvaluator.eval(expr, runTimeValues);
		this.setText(String.valueOf(initValue.getValue()));
		this.setForeground(Color.orange);
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.white));
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setEditable(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Map<String,Value> currentValues = varUpdater.getUpdatedValues();
		Value newVal = ExprEvaluator.eval(expr, currentValues);
		varUpdater.updateValue(varName, currentValues, newVal);
		this.setText(String.valueOf(newVal.getValue()));

	}

	@Override
	public String getDisplayedValue() {
		return this.getText();
	}



	@Override
	public boolean isQuestionLabel() {
		return false;
	}



	@Override
	public boolean isAnswerHolder() {
		return true;
	}



	@Override
	public boolean isWarningLabel() {
		return false;
	}
	
	
	
	
	

	
		
	

}

