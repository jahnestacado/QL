package org.uva.sea.ql.gui.qlform.output;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.uva.sea.ql.gui.qlform.QLFrame;
import org.uva.sea.ql.output.generators.json.QLToJSON;
import org.uva.sea.ql.output.generators.pdf.QLToPDF;
import org.uva.sea.ql.visitor.evaluator.values.Value;

public class QLOutputSelectorFrame implements ActionListener{
	private final JFrame dialog;
	private final String [] comboSelection = new String[]{"PDF","JSON"};
	private final List<String> questionLabels;
	private final List<String> questionVisibleValues;
	private JComboBox comboBox;
	private final Map<String,Value> allRunTimeValues;


	
	private QLOutputSelectorFrame(JFrame dialog,QLOutputState outputState){
		this.dialog=dialog;
		this.questionLabels=outputState.getQuestionLabels();
		this.questionVisibleValues=outputState.getQuestionVisibleValues();
		allRunTimeValues=outputState.getAllRunTimeValues();
	}
	
	private void setSettings(){
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.pack();
		dialog.setLocation(870, 225);
	}
	
	public static void showFormatSelector(String formName,QLOutputState outputState){
		JFrame frame=QLFrame.createQLFrame();
		frame.setTitle(formName);
		QLOutputSelectorFrame selector=new QLOutputSelectorFrame(frame,outputState);
		selector.setContentPanel();
		selector.showDialog();

	}
	
	private JPanel getComboBoxPanel(){
		JPanel comboBoxPanel=new JPanel(new MigLayout());
		JLabel label=new JLabel("Choose format");
		comboBox=new JComboBox(comboSelection);
		comboBoxPanel.add(label,"align label");
		comboBoxPanel.add(comboBox,"align label,wrap");
		return comboBoxPanel;
	}
	
	
	private JPanel getButtonPanel(){
		JPanel buttonPanel=new JPanel(new MigLayout());
		JButton button=new JButton("Generate");
		button.addActionListener(this);
		buttonPanel.add(button,"align label"); 
		return buttonPanel;

	}
	
	private void setContentPanel(){
		JPanel comboBoxPanel=getComboBoxPanel();
		JPanel buttonPanel=getButtonPanel();
		JPanel contentPanel=new JPanel(new MigLayout());
		contentPanel.add(comboBoxPanel,"align label,wrap");
		contentPanel.add(buttonPanel,"align label");
		dialog.add(contentPanel);
	}
	
	
	private void showDialog() {
		setSettings();
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		switch (comboBox.getSelectedIndex()) {
		case 0:
			
			QLToPDF.generatePdf(dialog, questionLabels, questionVisibleValues);
			break;
		case 1:
			QLToJSON.generateJson(dialog, allRunTimeValues);
			break;

		default:
			return;
		}

	}
	
	public static void showConfirmationMessage(JFrame frame, String message){
		JOptionPane.showMessageDialog(frame,message);

	}

}
