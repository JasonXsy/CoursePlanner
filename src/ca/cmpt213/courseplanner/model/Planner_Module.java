package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * main model interface 
 * manipulate object's data for each UI panel and initialize the course data model
 * 
 * data model scheme: Department has CourseOffering,
 * 					  CourseOffering has Course, 
 * 					  Course has Section 
 * */
public class Planner_Module {
//	private ArrayList<int[]> number_coursesOffered_by_Campus_Semester = new ArrayList<int[]>();
	private static List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private ArrayList<String[]> dataset = new ArrayList<String[]>();
	private String filePath = "data/course_data_2015.csv";
	private List<Deparment> deparments;
	private ArrayList<CourseOfferings> course_list = new ArrayList<CourseOfferings>();
	private ArrayList<Course> course_offering = new ArrayList<Course>();
	private ArrayList<CourseSection> sections = new ArrayList<CourseSection>();
	private ArrayList<CourseOfferings> course_list_favorate = new ArrayList<CourseOfferings>();
	private CourseOfferings statistic_Offering = new CourseOfferings();
	private int index_course_offering;
	private int index_departments;
	private int index_course_list;


	public Planner_Module (){
		deparments = new ArrayList<Deparment>();
		buildCourseModel();
		
	}
	public static void addChangeListener (ChangeListener listener){
		listeners.add(listener);
	}
	public int[] statisticCampus(){
		int[] stat = new int[4];
		if((getCourse_offering() == null)){
			System.out.print("no courses in this offering, sth went wrong");
		}
		else{
			for(Course c : getCourse_offering()){
				if(c.isBURNABY()){
					stat[0] += 1;
				}
				else if(c.isSURREY()){
					stat[1] += 1;
				}
				else if (c.isHRBRCNTR()){
					stat[2] += 1;
				}
				else if(c.isOtherCampus()){
					stat[3] += 1;
				}
			}
		}
		return stat;
	}
	public String statisticTitle(){
		String title = "";
		if((getCourse_offering() == null)){
			title = "";
			System.out.print("no courses in this offering, sth went wrong");
		}
		else{
			for(Course c : getCourse_offering()){
				title = c.toString();
			}
		}
		return title;
	}
	public int[] statisicSemester(){
		int[] stat = new int[3];
		if((getCourse_offering() == null) ){
			System.out.print("no courses in this offering, sth went wrong");
		}
		else{
			for(Course c : getCourse_offering()){
				if(c.isSpring()){
					stat[0] += 1;
				}
				else if(c.isSummer()){
					stat[1] += 1;
				}
				else if (c.isFall()){
					stat[2] += 1;
				}
			}
		}
		return stat;
	}
	public void notifyListeners() {
		if (listeners == null) {
			return; 
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners){
			listener.stateChanged(event);
		}

	}
	public void updateSections(Course c){
		sections = c.getSection();
		notifyListeners();
	}
	public void updateStatistic_Offering(CourseOfferings c){
		statistic_Offering = c;
		System.out.println(c.toString());
		notifyListeners();
	}
	public CourseOfferings getStatistic_Offering(){
		return statistic_Offering;
	}
	public void clearSections(){
		sections = new ArrayList<CourseSection>();
		notifyListeners();
	}
	public void updateCourse_List(int index, Boolean undergrad, Boolean grad){
		setIndex_departments(index);
		if(undergrad && grad){
			course_list = deparments.get(getIndex_departments()).getCourses();
//			course_offering = new ArrayList<Course>();			
		}
		else if (!undergrad && !grad){
			course_list = new ArrayList<CourseOfferings>();
//			course_offering = new ArrayList<Course>();
		}
		else if (!undergrad && grad){
			course_list = new ArrayList<CourseOfferings>();
//			course_offering = new ArrayList<Course>();
			for (CourseOfferings s : deparments.get(getIndex_departments()).getCourses()){
				if(s.getIsGrad()){
					course_list.add(s);
				}
			}
		}
		else if (undergrad && !grad) {
			course_list = new ArrayList<CourseOfferings>();
//			course_offering = new ArrayList<Course>();
			for (CourseOfferings s : deparments.get(getIndex_departments()).getCourses()){
				if(!s.getIsGrad()){
					course_list.add(s);
				}
			}
		}
		
		Collections.sort(course_list, new Comparator<CourseOfferings>() {
			@Override
			public int compare(CourseOfferings o1, CourseOfferings o2) {
				return o1.catalogNumber.compareTo(o2.getCatalogNumber());
			}
		});
		notifyListeners();
	}
	public void updateCourse_offering(CourseOfferings c){
		if(!c.getCourse().isEmpty()){
			System.out.println("*****");
			course_offering = c.getCourse();
			Collections.sort(course_offering, new Comparator<Course>(){
				@Override
				public int compare(Course o1, Course o2) {
					return o1.getSemester().compareTo(o2.getSemester());
				}
			});
		}
		notifyListeners();
	}
	public void addFavorate(CourseOfferings c){
		if(!course_list_favorate.contains(c)){
		course_list_favorate.add(c);
		System.out.println(c.toString()+ "  added");
		}
		notifyListeners();
	}
	public void removeFavorate(CourseOfferings c){
		if(course_list_favorate.contains(c)){
			
			course_list_favorate.remove(c);
			System.out.println(c.toString()+ "  removed");

		}
		notifyListeners();
	}
	public ArrayList<CourseOfferings> getFavorate(){
		for(CourseOfferings c :course_list_favorate){
			System.out.println(c.toString());
		}
		return course_list_favorate;
	}
	public ArrayList<Course> getCourse_offering() {
		return course_offering;
	}
	public ArrayList<CourseOfferings> getCourse_list() {
		return course_list;
	}
	public ArrayList<CourseSection> getSections() {
		return sections;
	}
	public List<Deparment> getSortedDeparments(){
		Collections.sort(deparments, new Comparator<Deparment>(){
			public int compare(Deparment obj1, Deparment obj2){
				return obj1.getNameDeparment().compareTo(obj2.getNameDeparment());
			}
		});
		return deparments;
	}
	public void printListTEST_String(List<Deparment> a){
		for (Deparment s : a){
			System.out.println(s.getNameDeparment());
		}
		
	}
	public void addDeparment(Deparment dep){
		deparments.add(dep);
	}
	public void buildCourseModel(){
		//to store subject names
		ArrayList<String> item_subjects = new ArrayList<String>();
		File file  = new File(filePath);
		try {
			Scanner input = new Scanner(file);
			String firstLine = input.nextLine();
			//skip the first line
			while (input.hasNextLine()) {
				String data = input.nextLine();
				String[] items = data.split(",");
				dataset.add(items);
				/*put subject of each row into a list*/
				if (items.length > 0 ){
					item_subjects.add(items[1]);
				}
			}
			input.close();

		} catch (FileNotFoundException e){
			e.printStackTrace();		
		}
		extractDepartments(item_subjects); //step 1: get all deparments' name from data
		//step 2: find course list of each deparments
		extractCourseList();
		//step 3: find all courses that in same semester, location and instructors
		extractCourseOfferings();
		//		step4: find all course section offered in history by the same semester, location and instructor
		extractCourseSections();
	}
	private void extractDepartments(ArrayList<String> subjects){
		HashSet<String> set = new HashSet<>();
		for (String a : subjects){
			set.add(a);
		}
		for (Iterator<String> it = set.iterator(); it.hasNext();){

			deparments.add(new Deparment(it.next()));
		}

	}
	private void extractCourseSections() {
		for (String[] s : dataset){
			for(Deparment d : deparments){
				for(CourseOfferings o : d.getCourses()){
					for(Course c : o.getCourse()){
						/*first check if dataset has the same cousrseoffering's fields*/	
						/*semester, subject, catalong number, location */
						if (s[0].equals(c.getSemester()) && s[1].equals(c.getSubject()) &&
							s[2].equals(c.getCatalogNumber()) && s[3].equals(c.getLocation())){
							c.addStuffForSection(s);
						}
						
					}//finish adding Courses data into a ArrayList in the CourseOffering
					//object
					
					
				}
			}
		}
		
		for (Deparment d : deparments){
			for(CourseOfferings o : d.getCourses()){
				for(Course c : o.getCourse()){
					c.createSections();
				}
			}
		}
	}
	private void extractCourseOfferings() {
		for(String[] s : dataset) {
			for (Deparment a : deparments) {
				for (CourseOfferings o : a.getCourses()){ //get course list offered in each departments

					if(o.getSubject().equals(s[1]) && o.getCatalogNumber().equals(s[2])){//check subject, catalogNum
						
						o.addSemester_Location_Instrcutors(s);
					}

				}
			}

		}
		for (Deparment a : deparments){
			for(CourseOfferings o : a.getCourses()){
				o.createCourses();
			}
		}
	}
	private void extractCourseList() {
		for (String[] s : dataset) { //each row of data	
			for (Deparment a : deparments){
				if(a.getNameDeparment().equals(s[1])){//compare to the deparment name
					a.addToHashSet(s[2]); //put catalogNumber into hashset
				}
			}
		}
		for (Deparment a : deparments){
			a.createCourseList();
		}
	}
	public void dumpModel() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
			for(Deparment a : deparments){
				for (CourseOfferings offerings : a.getCourses()) {
					//					System.out.println(offerings.getSubject() + "  " + offerings.getCatalogNumber());
					/*check step 1 and 2 (subject + cataNum)*/
					out.println(offerings.getSubject() + "  " + offerings.getCatalogNumber()); 
					for(Course c : offerings.getCourse()){
						/*check step 3 (semester + location + instructors)*/
//						System.out.print("	" +c.getSemester() + " in " +c.getLocation() + " by ");
						out.print("	" +c.getSemester() + " in " +c.getLocation() + " by ");
						Iterator<String> itr = c.getInstrcutors().iterator();
						while(itr.hasNext()){
							//							System.out.print(itr.next());
							out.print(itr.next());
							if(itr.hasNext()){
								//								System.out.print(", ");
								out.print(", ");
							}
						}						
						//						System.out.println("");
						out.println("");//-----------------------step3
						for(CourseSection s : c.getSection()){
							out.println("		Type=" + s.getComponent_code() + ", Enrollment=" + 
									s.getEnrolment_total() + "/" + s.getEnrolment_capacity());
						}
						out.flush();
					}
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getIndex_course_list() {
		return index_course_list;
	}
	public void setIndex_course_list(int index_course_list) {
		this.index_course_list = index_course_list;
	}
	public int getIndex_course_offering() {
		return index_course_offering;
	}
	public void setIndex_course_offering(int index_course_offering) {
		this.index_course_offering = index_course_offering;
	}
	public int getIndex_departments() {
		return index_departments;
	}
	public void setIndex_departments(int index_departments) {
		this.index_departments = index_departments;
	}
}
