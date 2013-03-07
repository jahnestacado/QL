package org.uva.sea.ql.gui.qlform;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QLRowPanel extends JPanel implements QLContainerComponent {
	

	public QLRowPanel(){
		setSettings();
	}


	private void setSettings() {
		this.setLayout(new MigLayout());
		this.setMinimumSize(new Dimension(617, 40));
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.white));
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
