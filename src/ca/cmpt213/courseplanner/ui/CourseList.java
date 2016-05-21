package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.cmpt213.courseplanner.model.CourseOfferings;
import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * The Class will generate the Course list panel, it has a scroll-able
 * list, when user clicks on a course, it selects the course for display
 * in the "Course Offerings by Semester" panel, and "Statistics" panel.
 */
@SuppressWarnings("serial")
public class CourseList extends MyABCPanel{
	public CourseList(Planner_Module module) {
		super(module);
		this.module = super.getModule();
	}

	@Override
	public Component getTitle() {
		setLayout(new BorderLayout());
		final JLabel title = new JLabel("Course List");
		title.setForeground(Color.BLUE);
		return title;
	}
	/*course list contet maker*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void makeContent(JPanel pane) {
		pane.setLayout(new BorderLayout());
		CourseOfferings[] course_list = new CourseOfferings[module.getCourse_list().size()];
		module.getCourse_list().toArray(course_list);
		final JList list = new JList(course_list);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setFixedCellWidth(120);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = list.getSelectedIndex();
				if (e.getValueIsAdjusting() && index != -1){
//					correct_index = index;
					JList src  = (JList) e.getSource();
					module.updateCourse_offering(module.getCourse_list().get(src.getSelectedIndex()));
					module.clearSections();
				}
				else{
					
				}
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            module.addFavorate(module.getCourse_list().get(index));
					System.out.println("favorate_list: " + module.getFavorate().size());

		     
		        } else if (evt.getClickCount() == 3) {

		            // Triple-click detected
//		            int index = list.locationToIndex(evt.getPoint());
		        }
		    }
		});

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.getVerticalScrollBar();

		Planner_Module.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateCourseList(list);
				updateUI();
			}

		});

		pane.add(listScroller);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateCourseList(JList list) {
		System.out.print(list.getComponentCount());
		list.setListData(module.getCourse_list().toArray());
	}
}
