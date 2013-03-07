package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class EditorArea extends JPanel {
	private final JTextArea editorArea;
	
	public EditorArea() {
		super(new MigLayout());
		editorArea = new JTextArea(18, 50);
		setSettings();
	}

	private void setSettings() {
		this.setBackground(new Color(49, 49, 49));
		editorArea.setBackground(Color.black);
		editorArea.setForeground(new Color(255, 185, 15));
		editorArea.setLineWrap(true);
		editorArea.setWrapStyleWord(true);
		editorArea.setCaretColor(Color.white);
		JScrollPane bar = new JScrollPane(editorArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bar.setPreferredSize(new Dimension(550, 300));
		this.add(bar);
		
	}
	
	public String getSourceCode(){
		return editorArea.getText();
	}
	
	public void setSourceCode(String input){
		editorArea.setText(input);
	}

}
