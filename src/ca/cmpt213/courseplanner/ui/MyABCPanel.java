package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * The class is a Panel Abstract Base Class, which inherits from JPanel.
 * it display all panel's title and border as well.
 */
@SuppressWarnings("serial")
public abstract class MyABCPanel extends JPanel{
	protected Planner_Module module;
	
	public MyABCPanel(Planner_Module module) {
		this.module = module;
		 setLayout(new BorderLayout());
		 add(getTitle(), BorderLayout.NORTH);
			
		 JPanel panel = new JPanel();
		 panel.setBorder(BorderFactory.createBevelBorder(
				 BevelBorder.LOWERED,
				 Color.black, Color.gray));
		 panel.setBackground(Color.WHITE);
		 add(panel, BorderLayout.CENTER);
		 makeContent(panel);
	}

	protected abstract Component getTitle();
	
	protected abstract void makeContent(JPanel pane);
	
	protected Planner_Module getModule() {
		return module;
	}
}
