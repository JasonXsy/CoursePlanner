package ca.cmpt213.courseplanner.ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ca.cmpt213.courseplanner.model.Planner_Module;

/*
 * This class is will create a JFrame for all panel, and it set up
 * position for all panel. The function createAndShowGUI() will do
 * the job.
 */
@SuppressWarnings("serial")
public class PlannerUI extends JFrame {
	private Planner_Module module;
	
	// test only
	private static final int WIDTH = 1600;
	private static final int HEIGHT = 800;
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 200;
	
	public PlannerUI(Planner_Module module) {
		this.module = module;
	}
	
	public Planner_Module getModule() {
		return module;
	}
	
	public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FAS Course Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the content pane first edition. **using GridBagLayout**
        // not include bonus panel
        //addComponentsToPane(frame.getContentPane());
        
        
        // set up the content pane second edition. **using BorderLayout**
        addComponentsToPaneTwo(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	// using GridBagLayout, (not include FavouriteCourse panel)
	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		CourseListFilter upperLeft = new CourseListFilter(module);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(upperLeft, c);

		CourseList lowerLeft = new CourseList(module);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		pane.add(lowerLeft, c);
		
		CourseOfferingsBySemester center = new CourseOfferingsBySemester(module);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 30;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		//c.anchor = GridBagConstraints.PAGE_START;
		pane.add(center, c);
		
		Statistics upperRight = new Statistics(module);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		pane.add(upperRight, c);
		
		DetailsOfCourseOffering lowerRight = new DetailsOfCourseOffering(module);
		c.gridx = 2;       
		c.gridy = 1;       
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 1; 
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END; 
		pane.add(lowerRight, c);
	}
	
	// using BorderLayout (include all panel)
	private void addComponentsToPaneTwo(Frame frame) {
		frame.setLayout(new BorderLayout(6,6));
        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        
        JPanel westPanel = makeWestPanels(fixSize(new CourseListFilter(module)),
        		new CourseList(module));
        JPanel centerPanel = new CourseOfferingsBySemester(module);
        JPanel eastPanel = makeEastPanels(fixSize(new Statistics(module)),
        		new FavouriteCourses(module),
        		fixSize(new DetailsOfCourseOffering(module)));
        
        frame.add(westPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(eastPanel, BorderLayout.EAST);
	}
	
	private static JPanel makeEastPanels(JPanel top, JPanel mid, JPanel bottom) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(top);
		panel.add(mid);
		panel.add(bottom);
		panel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		return panel;
	}
	
	private static JPanel makeWestPanels(JPanel top, JPanel bottom) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(top);
		panel.add(bottom);
		panel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		return panel;
	}
	
	private static MyABCPanel fixSize(MyABCPanel panel) {
		Dimension prefSize = panel.getPreferredSize();
		Dimension newSize = new Dimension(Integer.MAX_VALUE,
				(int) prefSize.getHeight());
		panel.setMaximumSize(newSize);
		return panel;
	}
	
//	public static void main(String[] args) {
//		Planner_Module ml = new Planner_Module();
//		MainUI ui = new MainUI(ml);
//		ui.createAndShowGUI();
//    }
}
