package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;

@SuppressWarnings("serial")
public class ConsoleArea extends JPanel {
	private final JTextArea console;

	public ConsoleArea() {
		super(new MigLayout());
		console = new JTextArea(7, 50);
		setSettings();
	}

	private void setSettings() {
		this.setBackground(new Color(49, 49, 49));
		console.setEditable(false);
		console.setBackground(Color.DARK_GRAY);
		JScrollPane sc = new JScrollPane(console,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(sc);
	}
	
	public void displayErrors(List<QLErrorMsg> errors){
		console.setForeground(Color.magenta);
		String displayedErrors= "TYPE ERRORS:"+'\n';
		for(QLErrorMsg msg : errors){
			displayedErrors+=msg.getError()+'\n';
		}
		console.setText(displayedErrors);
	}
	
	public void displayParseErrors(List<String> errors){
		console.setForeground(Color.cyan);
		String displayedErrors= "PARSE ERRORS:"+'\n';
		for(String msg : errors){
			displayedErrors+=msg+'\n';
		}
		console.setText(displayedErrors);
	}
	
	public void succeed(){
		console.setForeground(Color.green);
		console.setText("OK.");
	}

}
