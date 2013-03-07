package org.uva.sea.ql.gui.qlform.output;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.uva.sea.ql.gui.qlform.QLAtomComponent;
import org.uva.sea.ql.gui.qlform.QLContainerComponent;
import org.uva.sea.ql.gui.qlform.QLInputVerifier;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;

public class OutputData {
	private final List<JPanel> questionPanelList;
	private final QLOutputState outputState;
	private final JFrame frame;


	public OutputData(List<JPanel> questionPanelList, JFrame frame,VariableUpdater varUpdater) {
		this.questionPanelList = questionPanelList;
		this.frame = frame;
		outputState = new QLOutputState(varUpdater);
	}

	public QLOutputState getOutputData() {
		traverseFormsSubComponents(questionPanelList);
		return outputState;
	}
	
	/*
	 * Gathers the question label text and their respective answer
	 */

	private void gatherDataFromSubComponents(Component[] components) {
		for (int i = 0; i < components.length; i++) {
			QLAtomComponent component = (QLAtomComponent) components[i];
			if (component.isWarningLabel()) {
				setWarning(component);
			}
			else if (component.isQuestionLabel()) {
				outputState.addLabelText(component.getDisplayedValue());
			} 
			else if (component.isAnswerHolder()) {
				outputState.addValue(component.getDisplayedValue());
			} 

		}

	}
	
	private void traverseFormsSubComponents(List<JPanel> questionPanelList) {
		for (JPanel questionPanel : questionPanelList) {
			QLContainerComponent container =(QLContainerComponent) questionPanel;
			if (container.isConditionalBody()) {
				if (!isVisible(questionPanel))
					return;
				Component[] conditionalBodyPanel = questionPanel.getComponents();
				traverseConditionalBodysSubComponents(conditionalBodyPanel);

			} else {
				Component[] components = questionPanel.getComponents();
				gatherDataFromSubComponents(components);
			}

		}
	}
	
	private void traverseConditionalBodysSubComponents(Component[] conditionalBodyPanel){
		List<JPanel> panelList=new ArrayList<JPanel>();
		for (Component questionPanel : conditionalBodyPanel) {
			panelList.add((JPanel) questionPanel);
		}
		traverseFormsSubComponents(panelList);
	}
	
	private void setWarning(QLAtomComponent component) {
		QLInputVerifier label = (QLInputVerifier) component;
		if (!label.hasError())
			return;
		outputState.setErrorFlag(true);
		showWarning();

	}


	private void showWarning() {
		JOptionPane.showMessageDialog(frame,"Wrong input.Check input warnings!");

	}
	
	
	private boolean isVisible(Component component){
		return ((JPanel) component).isVisible();
	}
}
