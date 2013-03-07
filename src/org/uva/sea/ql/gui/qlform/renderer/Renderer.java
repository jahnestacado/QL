package org.uva.sea.ql.gui.qlform.renderer;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uva.sea.ql.gui.qlform.QLContentPanel;
import org.uva.sea.ql.gui.qlform.QLExportButton;
import org.uva.sea.ql.gui.qlform.QLScrollBar;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;

public class Renderer  {
	private final List<JPanel> questionPanelList;
	private final JPanel contentPanel = new QLContentPanel();
	private final JFrame frame;
	private final VariableUpdater varUpdater;
	private final static QLScrollBar scrollBar = new QLScrollBar();
	

	public Renderer(List<JPanel> questionPanelList, JFrame frame,VariableUpdater varUpdater) {
		this.frame = frame;
		this.questionPanelList = questionPanelList;
		this.varUpdater=varUpdater;
	}
	
	
	public void addQuestionsToPanel() {
		for (JPanel question : questionPanelList) {
			contentPanel.add(question, "align label,wrap");
		}
		scrollBar.add(contentPanel);
		frame.add(scrollBar, "align center,wrap");
		frame.add(QLExportButton.responsiveButton(questionPanelList, frame, varUpdater),"align center");
		frame.setVisible(true);
	}

	
	



}
