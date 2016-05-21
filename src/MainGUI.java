import ca.cmpt213.courseplanner.model.Planner_Module;
import ca.cmpt213.courseplanner.ui.PlannerUI;

/*
 * This MainGUI class , where the main() is 
 * to invoke PlannerModule and PlannerUI object.   
 */


public class MainGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Planner_Module ml = new Planner_Module();
		PlannerUI ui = new PlannerUI(ml);
		ui.createAndShowGUI();
	}
}
