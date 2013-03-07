package org.uva.sea.ql.gui.qlform;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QuestionPanel extends QLRowPanel {

	private final JLabel label;
	private final JComponent inputComponent;
	private boolean needsVerifier;
	private final Type type;
	private  VariableUpdater varUpdater;

	
	
	public QuestionPanel(Question qlElement,Map<String,Value> runTimeValues, VariableUpdater varUpdater){
		super();
		this.varUpdater=varUpdater;
		type=qlElement.getType();
		label=new QLLabel(qlElement.getLabel().getValue());
		inputComponent=setInputComponent(qlElement.getId().getName(),type,runTimeValues);
		addComponents();
	}
	
	
	
	public JPanel getPanel(){
		return this;
	}
	
	private JComponent setInputComponent(final String varName,Type type,final Map<String,Value> runTimeValues){
	
		if (type.isCompatibleToStringType()) {
			needsVerifier=true;
			return new QLTextField(varName, runTimeValues,varUpdater);
		} 
		else if (type.isCompatibleToBoolType()) {
			return new QLCheckBox(varName, runTimeValues,varUpdater);
		}
		if (type.isCompatibleToIntType()) {
			return new QLSpinner(varName, runTimeValues,varUpdater);
		}
		if (type.isCompatibleToMoneyType()) {
			needsVerifier=true;
			return new QLNumField(varName, runTimeValues,varUpdater);
		} 
		return null;
	}


	
	private void addComponents() {
		if (needsVerifier) {
			this.add(label, "align label");
			this.add(inputComponent,"push, al right");
			this.add(new QLInputVerifier(inputComponent, type), "wrap");
		} else {
			this.add(label, "align label");
			this.add(inputComponent,"push, al right,wrap");
		}
	}



	

}
