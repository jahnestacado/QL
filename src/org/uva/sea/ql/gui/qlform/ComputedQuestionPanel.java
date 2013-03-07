package org.uva.sea.ql.gui.qlform;

import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class ComputedQuestionPanel extends QLRowPanel {
	private final JLabel label;
	private final JTextField computedField;
	public final static String COM_QUESTION_PANEL="COM_QUESTION_PANEL";

	public ComputedQuestionPanel(ComputedQuestion qlElement,Map<String,Value> runTimeValues,VariableUpdater varUpdater){
		super();
		label= new QLLabel(qlElement.getLabel().getValue());
		computedField = new QLComputedField(qlElement,varUpdater,runTimeValues);
		addComponents();
	}
	
	private void addComponents(){
		this.setLayout(new MigLayout());
		this.add(label,"align label");
		this.add(computedField,"push, al right,wrap");
		
	}
	
	public JPanel getPanel(){
		return this;
	}

	
	
	



}
