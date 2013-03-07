package org.uva.sea.ql.gui.qlform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.IntValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class QLSpinner extends JSpinner implements Observer,QLAtomComponent  {
	private final String varName;
	private  Map<String, Value> declaredVars;
	private final VariableUpdater varUpdater;


   
	
	
	public QLSpinner(String varName, Map<String, Value> declaredVar,VariableUpdater varUpdater) {
		super(new SpinnerNumberModel(0, 0, 1000, 1));
		this.varName = varName;
		this.declaredVars = declaredVar;
		this.varUpdater = varUpdater;
		this.varUpdater.addObserver(this);
		addListeners();
	}
	
	
	private JSpinner addListeners() {
		this.setValue(getCurrentDisplayedValue());
		JFormattedTextField tf = ((JSpinner.DefaultEditor)this.getEditor()).getTextField();
		tf.setEditable(false);
				  
		for (Component child : this.getComponents()) {
	        if ("Spinner.nextButton".equals(child.getName())) {
	            ((JButton) child).addActionListener(new UpButton());
	        }
	        if ("Spinner.previousButton".equals(child.getName())) {
	            ((JButton) child).addActionListener(new DownButton());
	        }
	    }
		return this;
	}
   
	private class UpButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			increaseValueByOne();			
		}
		
	}
	
	private class DownButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(getCurrentDisplayedValue()==0) return;
			decreaseValueByOne();			
		}
		
	}
	
	private void increaseValueByOne(){
		updateDisplay(getCurrentDisplayedValue()+1);
	}
	
	private void decreaseValueByOne(){
		updateDisplay(getCurrentDisplayedValue()-1);

	}
	
	private void updateDisplay(int value){
		varUpdater.updateValueAndNotify(varName, declaredVars, new IntValue(value));	
	}
    
	private int getCurrentDisplayedValue() {
		return ((IntValue) declaredVars.get(varName)).getValue();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		declaredVars=varUpdater.getUpdatedValues();
	}

	@Override
	public String getDisplayedValue() {
		return this.getValue().toString();
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