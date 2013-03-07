package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.DecValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;


@SuppressWarnings("serial")
public class QLNumField extends JTextField implements ActionListener, Observer, QLAtomComponent{
	private final String varName;
	private final Map<String, Value> declaredVar;
	private  Float value;
	private final static Color defaultColor= new Color(238,238,238);
	private final VariableUpdater varUpdater;
	
	
	public QLNumField(String varName,Map<String, Value> runTimeValues, VariableUpdater varUpdater){
		super(8);
		this.varName=varName;
		this.declaredVar=runTimeValues;
		this.varUpdater=varUpdater;
		this.varUpdater.addObserver(this);
		setSettings();
	}
	
	private void setSettings(){
		value=((DecValue) declaredVar.get(varName)).getValue();
		this.setBackground(defaultColor);
		this.addActionListener(this);
		String roundedDisplayedValue=String.valueOf(roundTo2Decimals(value));
		this.setText(roundedDisplayedValue);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String txt=this.getText();
		if(!QLInputVerifier.isNumChar(txt)) return;
		Float input=Float.parseFloat(this.getText());
		varUpdater.updateValueAndNotify(varName, declaredVar, new DecValue(input));

	}
	
	
	
	public static float roundTo2Decimals(float num){
		return Math.round(num*100.0)/100.0f;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		value=((DecValue) varUpdater.getUpdatedValues().get(varName)).getValue();
		String roundedDisplayedValue=String.valueOf(roundTo2Decimals(value));
		this.setText(roundedDisplayedValue);	
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
