package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QLCheckBox extends JCheckBox implements ActionListener,Observer,QLAtomComponent {

	private final String varName;
	private  Map<String, Value> runTimeValues;
	private final static String YES = "Yes";
	private final static String NO = "No";
	private final VariableUpdater varUpdater;
	


	public QLCheckBox(String varName, Map<String, Value> runTimeValues,VariableUpdater varUpdater) {
		super(NO);
		this.varName = varName;
		this.runTimeValues = runTimeValues;
		this.varUpdater=varUpdater;
		this.varUpdater.addObserver(this);
		setSettings();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		varUpdater.updateValueAndNotify(varName, runTimeValues,new BoolValue(!getState()));
	}

	
	private void setSettings() {
		this.addActionListener(this);
		this.setSelected(getState());
		setStatusText();
		this.setBackground(Color.gray);
		this.setForeground(Color.white);
	}

	

	private boolean getState() {
		return ((BoolValue) runTimeValues.get(varName)).getValue();
	}

	private void setStatusText() {
		if (getState()) {
			this.setText(YES);
			return;
		}
		this.setText(NO);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		runTimeValues=varUpdater.getUpdatedValues();
		this.setSelected(getState());
		setStatusText();
		
		
	}

	@Override
	public String getDisplayedValue() {
		return String.valueOf(this.isSelected());
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
