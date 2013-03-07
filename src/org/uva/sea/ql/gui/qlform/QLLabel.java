package org.uva.sea.ql.gui.qlform;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class QLLabel extends JLabel implements QLAtomComponent {
	

	public QLLabel(String text) {
		super(text.replaceAll("\"", ""));
		setSettings();
	}

	
	
	private void setSettings(){
		this.setForeground(Color.white);
	}

	@Override
	public String getDisplayedValue() {
		return this.getText();
	}



	@Override
	public boolean isQuestionLabel() {
		return true;
	}



	@Override
	public boolean isAnswerHolder() {
		return false;
	}



	@Override
	public boolean isWarningLabel() {
		return false;
	}

}
