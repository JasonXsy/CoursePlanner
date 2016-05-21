package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.courseplanner.model.CourseSection;
import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * This class will generate the panel "Details of Course Offering",
 * it will display detailed information about the selected course
 * offering.
 */
@SuppressWarnings("serial")
public class DetailsOfCourseOffering extends MyABCPanel{
	private static Planner_Module module;
	private ArrayList<CourseSection> section;
	private static String[] typeTitle = {"Course Name:", "Semester:", "Location:", "Instructors:"};
	
	public DetailsOfCourseOffering(Planner_Module module) {
		super(module);
		this.module = super.getModule();
		setPreferredSize(new Dimension(320, 200));
	}
	
	@Override
	public Component getTitle() {
		setLayout(new BorderLayout());
		final JLabel title = new JLabel("Details Of Course Offering");
		title.setForeground(Color.BLUE);
		return title;
	}

	@Override
	public void makeContent(final JPanel pane) {
		// TODO Auto-generated method stub
		pane.setLayout(new GridLayout(2,1));
		makeEmptyTextArea(pane);
		
		Planner_Module.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				section = module.getSections();
			if(section == null || section.isEmpty()){
				pane.removeAll();
				makeEmptyTextArea(pane);
			}else{
				pane.removeAll();
				makeTextArea(pane);
				makeTable(pane);
				updateUI();
//				pane.revalidate();
//				pane.repaint();
				
				}
			}
		});
	}
	
	private void makeTable(JPanel pane) {
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(section.size()+1,2));
		
		JLabel sectionType = new JLabel("Section Type");
		JLabel enrollment = new JLabel("Enrollment (filled/cap)");
		panel2.add(sectionType);
		panel2.add(enrollment);

		for(CourseSection cs: section) {
			String type = cs.getComponent_code();
			String enrollments = cs.getEnrolment_total() +"/"+ cs.getEnrolment_capacity();
			JLabel test = new JLabel(type);
			JLabel test_data = new JLabel(enrollments);
			panel2.add(test);
			panel2.add(test_data);
		}
		pane.add(panel2);
	}
	
	private void makeTextArea(JPanel pane) {
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,2,-80,0));
		String instructor = "" ;
		for(Iterator<String> itr = section.get(0).getInstrcutors().iterator(); itr.hasNext(); ) {
			instructor = instructor + " " + itr.next();
		}
		String[] sectionList = {section.get(0).getSubject()+ " " +
								section.get(0).getCatalogNumber(),
								section.get(0).getSemester(),
								section.get(0).getLocation(),
								instructor};
		for(int i = 0; i < 4; i++) {
			JTextArea text = new JTextArea(sectionList[i]);
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setEditable(false);
			text.setColumns(20);
			text.setRows(1);
			panel1.add(new JLabel(typeTitle[i]));
			panel1.add(text);
		}
		pane.add(panel1);
	}
	
	private void makeEmptyTextArea(JPanel pane) {
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,2,-80,0));
		for(int i = 0; i < 4; i++) {
			JTextArea text = new JTextArea("");
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setEditable(false);
			text.setColumns(20);
			text.setRows(1);
			panel1.add(new JLabel(typeTitle[i]));
			panel1.add(text);
		}
		pane.add(panel1);
	}
	
}
