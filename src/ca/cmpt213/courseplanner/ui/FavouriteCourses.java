package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
 * This class will create the panel "Favourite Courses", when user
 * double-click a course in "Course List" panel, it will send the
 * course into this panel, user also can remove a course from this
 * panel by double-click.
 */
@SuppressWarnings("serial")
public class FavouriteCourses extends MyABCPanel {
	public FavouriteCourses(Planner_Module module) {
		super(module);
		this.module = super.getModule();
		setPreferredSize(new Dimension(320, 150));
	}

	@Override
	public Component getTitle() {
		setLayout(new BorderLayout());
		final JLabel title = new JLabel("Favourite Course List");
		title.setForeground(Color.BLUE);
		return title;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void makeContent(final JPanel pane) {
//		// TODO Auto-generated method stub
		pane.setLayout(new BorderLayout());
		CourseOfferings[] favorate = new CourseOfferings[module.getFavorate().size()];
		module.getFavorate().toArray(favorate);
		final JList list = new JList(favorate);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setFixedCellWidth(120);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1 && !list.getValueIsAdjusting()){
					JList src  = (JList) e.getSource();
					module.updateCourse_offering(module.getFavorate().get(src.getSelectedIndex()));
				}
				else{
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	System.out.println("2 click detected");
		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if(index != -1){
		            	
		            	module.removeFavorate(module.getFavorate().get(index));
		            }

		     
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
				pane.revalidate();
				pane.repaint();
//				updateUI();
			}

			private void updateCourseList(JList list) {
				list.setListData(module.getFavorate().toArray());
			}

		});

		pane.add(listScroller);
	}
}
