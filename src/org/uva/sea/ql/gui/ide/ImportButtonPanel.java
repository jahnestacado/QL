package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;


@SuppressWarnings("serial")
public class ImportButtonPanel extends JPanel implements ActionListener {
	private final JButton openFile;
	private final static  Icon imgIcon = new ImageIcon(ImportButtonPanel.class.getResource("/org/uva/sea/ql/gui/ide/icons/folder.png"));
	private final EditorArea editorPanel;

	
	public ImportButtonPanel(EditorArea editorPanel){
		openFile = new JButton();
		this.editorPanel= editorPanel;
		setSettings();
	}
	

	private void setSettings() {
		openFile.addActionListener(this);
	    openFile.setContentAreaFilled(false);
		openFile.setBorderPainted(false);
		openFile.setIcon(imgIcon);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(openFile);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		showfileChooser();		
	}
	
	

	private void showfileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new TypeOfFile());
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String sourceCode = QLInputReader.readFile(selectedFile);
			editorPanel.setSourceCode(sourceCode);
		}
	}
	
	class TypeOfFile extends FileFilter {
		public boolean accept(File file) {
			return file.isDirectory() || file.getName().toLowerCase().endsWith(".ql");
		}

		public String getDescription() {
			return ".ql";
		}
	}
	

}
