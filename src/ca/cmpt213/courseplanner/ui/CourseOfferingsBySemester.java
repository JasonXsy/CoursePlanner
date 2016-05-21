package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.courseplanner.model.Course;
import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * This class will create the "Course Offerings By Semester" panel,
 * it has a GridBagLayout to display the offerings for the selected
 * course (from the "Course List" panel). when user click a section
 * it will send the informations to the panel"Details of Course Offering"
 */
@SuppressWarnings("serial")
public class CourseOfferingsBySemester extends MyABCPanel{
	ArrayList<Course> courses;

	public CourseOfferingsBySemester(Planner_Module module) {
		super(module);
		this.module = super.getModule();
	}

	@Override
	public Component getTitle() {
		setLayout(new BorderLayout());
		final JLabel title = new JLabel("Course Offerings By Semester");
		title.setForeground(Color.BLUE);
		return title;
	}

	@Override
	public void makeContent(final JPanel pane) {
		makeClear(pane);
		Planner_Module.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				pane.removeAll();
				updateCourse_offering(pane);
//				pane.revalidate();
//				pane.repaint();
				updateUI();
			}
		});
	}
	private void updateCourse_offering( JPanel pane) {
		courses = module.getCourse_offering();
		makeCourse_panel(courses, pane);
	}
	private void makeCourse_panel(final ArrayList<Course> courses, JPanel pane){
		if(courses == null || courses.isEmpty()){
			makeClear(pane);
		}
		else {
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			//display semester on first row
			String[] semesters = {"Spring", "Summer", "Fall"};
			for(int col = 1; col <= 3; col++) {
				JLabel semester = new JLabel(semesters[col-1]);
				c.gridx = col;
				c.gridy = 0;
				c.weightx = 1;
				c.gridwidth = 1;
				c.gridheight = 1;
				c.anchor = GridBagConstraints.NORTHWEST;
				pane.add(semester,c);
			}
			
			//display years on first column
			int startYear = 2000 + Integer.parseInt(courses.get(0).getSemester().substring(1,3));
			int endYear = 2000 + Integer.parseInt(courses.get(courses.size()-1).getSemester().substring(1,3));
			int[] years = new int[endYear-startYear + 1];
			for(int i = 0; i < years.length; i++) {
					years[i] = startYear + i;
			}
			for(int row = 1; row <= years.length; row++) {
				String x = Integer.toString(years[row-1]); 
				JLabel year = new JLabel(x);
				c.gridx = 0;
				c.gridy = row;
				c.weightx = 1;
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				pane.add(year,c);
			}
			
			// display courses
			for(int row = 0; row < years.length; row++) {
				for(int colm = 0; colm < 3; colm++) {
					String term;
					if (colm == 0){
						term = "1";
					}
					else if (colm == 1){
						term = "4";
					}
					else {
						term = "7";
					}
					
					JPanel panel = new JPanel();
					c.gridx = colm + 1;
					c.gridy = row + 1;
					c.weightx = 8;
					c.weighty = 2;
					c.gridwidth = 1;
					c.gridheight = 1;
					c.fill = GridBagConstraints.BOTH;
					c.anchor = GridBagConstraints.SOUTHEAST;
					panel.setBorder(BorderFactory.createBevelBorder(
							BevelBorder.LOWERED,
							Color.black, Color.gray));
					panel.setLayout(new GridBagLayout());
					GridBagConstraints cc = new GridBagConstraints();
					
					int intYear = years[row] - 2000;
					int intTerm = Integer.parseInt(term);
					int next = 0;
					for(int i = 0; i < courses.size(); i++) {
						if((Integer.parseInt(courses.get(i).getSemester().substring(1,3)) == intYear) &&
								(Integer.parseInt(courses.get(i).getSemester().substring(3)) == intTerm)) {
							String buttonText = courses.get(i).getSubject() + " " 
										+ courses.get(i).getCatalogNumber() + " - " + courses.get(i).getLocation();
							JButton button = new JButton(buttonText);
							final int x = i;
							button.addActionListener(new java.awt.event.ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									module.updateSections(courses.get(x));
								}
							});
							cc.gridx = 0;
							cc.gridy = 0 + next;
							cc.weightx = 8;
							cc.weighty = 1;
							cc.gridwidth = 1;
							cc.gridheight = 1;
							cc.fill = GridBagConstraints.HORIZONTAL;
							cc.anchor = GridBagConstraints.PAGE_START;
							panel.add(button,cc);
							next++;
						}
					}
					pane.add(panel,c);
				}
			}
		}
	}

	private void makeClear(JPanel pane) {
		String message = "Use a filter to select a course";
		JLabel emptyMessage = new JLabel(message);
		emptyMessage.setFont(new Font("Serif", Font.PLAIN, 24));
		emptyMessage.setHorizontalAlignment(SwingConstants.CENTER);
		pane.setLayout(new BorderLayout());
		pane.add(emptyMessage, BorderLayout.CENTER);
	}
}
