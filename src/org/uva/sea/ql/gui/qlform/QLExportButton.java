package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.gui.qlform.output.OutputData;
import org.uva.sea.ql.gui.qlform.output.QLOutputSelectorFrame;
import org.uva.sea.ql.gui.qlform.output.QLOutputState;


public class QLExportButton implements ActionListener {

	private final JButton button = new JButton("Export");
	private final List<JPanel> questionPanelList;
	private final JFrame frame;
	private final VariableUpdater varUpdater;
	

	private QLExportButton(List<JPanel> questionPanelList,JFrame frame,VariableUpdater varUpdater) {
		this.questionPanelList = questionPanelList;
		this.varUpdater=varUpdater;
		this.frame=frame;
		setSettings();
	}
	
	private void setSettings(){
		button.addActionListener(this);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.white);
		
	}

	public static JButton responsiveButton(List<JPanel> questionPanelList,JFrame frame,VariableUpdater varUpdater) {
		QLExportButton genButton = new QLExportButton(questionPanelList,frame,varUpdater);
		return genButton.getButton();
	}

	private JButton getButton() {
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		QLOutputState outputState=new OutputData(questionPanelList,frame,varUpdater).getOutputData();
		if(!outputState.hasError()){
			showSelectorDialog(outputState);
		}
	
	}

	
	private void showSelectorDialog(QLOutputState outputState){
		String formName=frame.getTitle();
		QLOutputSelectorFrame.showFormatSelector(formName,outputState);
		
	}

}
