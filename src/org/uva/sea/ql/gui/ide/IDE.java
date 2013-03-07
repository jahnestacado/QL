package org.uva.sea.ql.gui.ide;


public class IDE {
    private final EditorArea editorArea;	
    private final ConsoleArea consoleArea;
	
	public IDE(){
		editorArea = new EditorArea();
		consoleArea= new ConsoleArea();
		createIDE();
	}


	private void createIDE() {
		IDEFrame frame= new IDEFrame();
		frame.add(editorArea, "wrap");
		
		UtilityBarPanel utilityPanel = new UtilityBarPanel();
		utilityPanel.add(new RunButtonPanel(editorArea, consoleArea));
		utilityPanel.add(new ImportButtonPanel(editorArea));
		frame.add(utilityPanel, "wrap");
		
		frame.add(consoleArea);
		frame.setVisible(true);
		
		
	}
	

}
