package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.uva.sea.ql.ast.types.Type;

@SuppressWarnings("serial")
public class QLInputVerifier extends JLabel implements KeyListener, QLAtomComponent{
	private final JTextField inputTextField;
	private final Type type;
	private String value="";

	public QLInputVerifier(JComponent component, Type type) {
		this.inputTextField = (JTextField) component;
		this.type = type;
		setSettings();
	}
	
	private void setSettings(){
		this.setForeground(Color.ORANGE);
		inputTextField.addKeyListener(this);
	}

	

	private void setVerifierMsg() {
		String input = inputTextField.getText();
		if (type.isCompatibleToNumericType()) {
			numVerifier(input);
			return;
		}
		stringVerifier(input);
	}

	private void numVerifier(String input) {
		if (!isNumChar(input)) {
			value="*accepts only numeric characters*";
			this.setText(value);
			return;
		}

		value="";
		this.setText(value);

	}

	private void stringVerifier(String input) {
		if (!isStringChar(input)) {
			this.setVisible(true);
			this.setText("*accepts only string characters*");
			return;
		}

		this.setVisible(false);
		this.setText("");
	}

	public static boolean isStringChar(String input) {
		return !input.matches(".*\\d.*");
	}

	public static boolean isNumChar(String input) {
		try {
			Float.parseFloat(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		setVerifierMsg();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public String getDisplayedValue() {
		return value;
	}
	
	public boolean hasError(){
		return !value.isEmpty();
	}

	@Override
	public boolean isQuestionLabel() {
		return false;
	}

	@Override
	public boolean isAnswerHolder() {
		return false;
	}

	@Override
	public boolean isWarningLabel() {
		return true;
	}
}
