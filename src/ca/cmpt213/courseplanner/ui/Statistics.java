package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.bargraph.BarGraphIcon;
import ca.cmpt213.bargraph.BarGraphModel;
import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * This class will create the "Statistics" panel, it display two bar graph,
 * one is showing how frequently the selected course was offered in each
 * semester, another one showing how frequently the selected course is 
 * offered at each campus.
 */
@SuppressWarnings("serial")
public class Statistics extends MyABCPanel{
	private String[] semesters = {"Spring","Summer","fall"};
	private String[] campuses = {"BBY", "SRY", "VAN", "OTHER"};

	private static Planner_Module module;
	public Statistics(Planner_Module module) {
		super(module);
		this.module = super.getModule();
		setPreferredSize(new Dimension(320, 450));
	}

	@Override
	public Component getTitle() {
		setLayout(new BorderLayout());
		final JLabel title = new JLabel("Statistics");
		title.setForeground(Color.BLUE);
		return title;
	}

	@Override
	public void makeContent(final JPanel pane) {

		Planner_Module.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				pane.removeAll();
				if(!(module.getStatistic_Offering() == null)){
					
					updateStatCourse_offering(pane);
				}
				//				pane.revalidate();
				//				pane.repaint();
				updateUI();
			}

			private void updateStatCourse_offering(JPanel pane) {
				if(!(module.getCourse_offering() == null)){
					BarGraphModel semestersGraph = new BarGraphModel(module.statisicSemester()
							,semesters);
					BarGraphModel campusesGraph = new BarGraphModel(module.statisticCampus()
							, campuses);
					String title = "Course: " + module.statisticTitle();
					pane.add(new JLabel(title));
					pane.add(new JLabel(" "));
					pane.add(new JLabel("Semester Offerings:"));
					pane.add(new JLabel(new BarGraphIcon(semestersGraph, 220, 150)));
					pane.add(new JLabel("Campus Offerings:"));
					pane.add(new JLabel(new BarGraphIcon(campusesGraph, 220, 150)));
					pane.setMaximumSize(getMinimumSize());
					pane.revalidate();
					pane.repaint();
				}

			}
		});


	}
}
