package org.uva.sea.ql.gui.ide;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class IDEFrame extends JFrame {
	private final static ImageIcon imgIcon = new ImageIcon(IDEFrame.class.getResource("/org/uva/sea/ql/gui/ide/icons/logo.png"));


	public IDEFrame() {
		setSettings();

	}

	private void setSettings() {
		this.setIconImage(imgIcon.getImage());
		this.setLocation(50, 90);
		this.setTitle("QL Editor");
		this.setLayout(new MigLayout());
		this.setSize(new Dimension(575, 470));
		this.setResizable(false);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
