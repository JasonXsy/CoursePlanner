package ca.cmpt213.courseplanner.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.Deparment;
import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * This class create the Course list filter panel, it has a drop-down
 * JComboBox object, allow user to select a department. Also generate
 * two check-box allow user to select undergrad and grad courses.
 * after user select a department it will show all course in the 
 * "Course List" panel.
 */
@SuppressWarnings("serial")
public class CourseListFilter extends MyABCPanel{
	private boolean underGradStatus = false; // include 499 and under
	private boolean gradStatus = false;      // include 500 and upper
	
	public CourseListFilter(Planner_Module module) {
		super(module);
		this.module = super.getModule();
		setPreferredSize(new Dimension(320, 140));
	}
	
	@Override
	public Component getTitle() {
		//setLayout(new BorderLayout());
		final JLabel title = new JLabel("Course List Filter");
		title.setForeground(Color.BLUE);
		return title;
	}
	
	@Override
	public void makeContent(JPanel pane) {
		//JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// (top, left, bottom, right)
		c.insets = new Insets(3,0,3,0);
		
		// department
		JLabel title = new JLabel("Department");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		pane.add(title,c);
		
		// JComboBox
		final JComboBox<String> coList = new JComboBox<String>(getDepartment());
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHEAST;
		pane.add(coList,c);
		
		// Include undergrad courses
		final JCheckBox undergrad = new JCheckBox("Include undergrad courses");
		undergrad.setBackground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(undergrad,c);
		
		// Include grad courses
		final JCheckBox grad = new JCheckBox("Include grad courses");
		grad.setBackground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.5;
		c.ipadx = 50;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(grad,c);
		
		// update course list
		JButton update = new JButton("Update Course List");
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTHEAST;
		pane.add(update,c);
		
		update.addActionListener(new java.awt.event.ActionListener() {
			@Override
		      public void actionPerformed(ActionEvent e) {
		        int contents = coList.getSelectedIndex();
		       
		        if(undergrad.isSelected()) {
		        	underGradStatus = true;
		        }else{
		        	underGradStatus = false;
		        }
		        if(grad.isSelected()) {
		        	gradStatus = true;
		        }else {
		        	gradStatus = false;
		        }
		        module.updateCourse_List(contents, getUndergradStatus(), getGradStatus());
		        
		        
		        System.out.println(getUndergradStatus());
		        System.out.println(getGradStatus());
		        System.out.println("deparment index: " + contents);
		      }
		    });
		
		
	}
	
	public boolean getUndergradStatus() {
		return underGradStatus;
	}
	
	public boolean getGradStatus() {
		return gradStatus;
	}
	
	public Vector<String> getDepartment() {
		Vector<String> list = new Vector<String>();
		List<Deparment> department_list = module.getSortedDeparments();
		for (Deparment s : department_list){
			list.addElement(s.getNameDeparment());
		}
		return list;
	}
}
