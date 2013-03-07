package org.uva.sea.ql.gui.qlform;

import java.awt.Color;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;



@SuppressWarnings("serial")
public class QLContentPanel extends JPanel implements QLContainerComponent {

	public QLContentPanel(){
		setSettings();
	}
	

	private void setSettings(){
		this.setLayout(new MigLayout("hidemode 1"));
		this.setBackground(Color.black);
		
	}

	@Override
	public boolean isConditionalBody() {
		return false;
	}

	@Override
	public boolean isPanel() {
		return true;
	}

}
