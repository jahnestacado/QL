package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.gui.qlform.QLFrame;
import org.uva.sea.ql.gui.qlform.interpreter.SwingGenerator;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.checkers.ElementChecker;
import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public class RunButtonPanel extends JPanel implements ActionListener {
	
	private final JButton runButton;
	private final static Icon imgIcon = new ImageIcon(RunButtonPanel.class.getResource("/org/uva/sea/ql/gui/ide/icons/run.png"));
	private final EditorArea editorPanel;
	private final ConsoleArea consoleArea;


	
	public RunButtonPanel(EditorArea editorPanel, ConsoleArea consoleArea){
		super();
		this.editorPanel= editorPanel;
		this.consoleArea= consoleArea;
		runButton = new JButton();
		setSettings();
	}


	private void setSettings() {
		runButton.addActionListener(this);
		runButton.setContentAreaFilled(false);
		runButton.setBorderPainted(false);
		runButton.setIcon(imgIcon);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(runButton);
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String sourceCode = editorPanel.getSourceCode();
		if(sourceCode.isEmpty()) return;
		Form parsedForm = QLInputReader.getParsedForm(sourceCode,consoleArea);
		ElementChecker checker=new ElementChecker(new LinkedHashMap<String,Type>(), new ArrayList<QLErrorMsg>());
		if (checker.check(parsedForm)) {
			VariableUpdater varUpdater=new VariableUpdater(new LinkedHashMap<String,Value>());
            List<JPanel> questionList = new ArrayList<JPanel>();
			Map<String, Value> declaredVar = new HashMap<String, Value>();
			JFrame frame = QLFrame.createQLFrame();
			new SwingGenerator(questionList, declaredVar,varUpdater).generate(parsedForm,frame);
			consoleArea.succeed();
		}
		else{
			consoleArea.displayErrors(checker.getErrorReport());
			
		}
	}

}
