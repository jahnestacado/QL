package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.StrValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QLTextField extends JTextField implements ActionListener, Observer, QLAtomComponent{
	
	private final String varName;
	private final Map<String, Value> runTimeValues;
	private String value;
	private final static Color defaultColor= new Color(238,238,238);
	private final VariableUpdater varUpdater;



	
	public QLTextField(String varName,Map<String, Value> runTimeValues, VariableUpdater varUpdater){
		super(8);
		this.varName=varName;
		this.runTimeValues=runTimeValues;
		this.varUpdater=varUpdater;
		this.varUpdater.addObserver(this);
		setSettings();
	}
	
	private void setSettings(){
		value=((StrValue) runTimeValues.get(varName)).getValue();
		this.setBackground(defaultColor);
		this.addActionListener(this);
		this.setText(value);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String input=this.getText();
		if(!QLInputVerifier.isStringChar(input)) return;
		
		 varUpdater.updateValueAndNotify(varName, runTimeValues, new StrValue(input));
		

	}
	
	

	@Override
	public void update(Observable arg0, Object arg1) {
		value=((StrValue) varUpdater.getUpdatedValues().get(varName)).getValue();
		this.setText(value);		
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
