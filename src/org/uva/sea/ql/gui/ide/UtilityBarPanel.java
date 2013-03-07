package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class UtilityBarPanel extends JPanel  {
	
	public UtilityBarPanel(){
		super(new MigLayout());
		setSettings();
	}

	private void setSettings() {
		this.setMaximumSize(new Dimension(200,90));
		this.setBackground(Color.LIGHT_GRAY);		
	}

}
