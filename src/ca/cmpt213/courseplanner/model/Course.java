package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/*
 * each Course object has Section objects
 * inherent from CourseOffering class
 * 
 * 3 fields added,  semester, location, and instructors
 * */

public class Course extends CourseOfferings {

	protected String location; 
	protected String semester; 
	protected ArrayList<String> instructors;
	private ArrayList<CourseSection> section;
	private ArrayList<String[]> stuff = new ArrayList<String[]>();

	Course (String Subject, String catalogNum,
			String[] stuff){

		super(Subject, catalogNum);
		semester = stuff[0];
		location = stuff[3];
		instructors = new ArrayList<String>();
		section = new ArrayList<CourseSection>();
		addInstructors(Arrays.copyOfRange(stuff, 6, stuff.length-1));
		this.subject = super.subject;
		this.catalogNumber = super.catalogNumber;
	}

	private void addInstructors(String[] Instructors) {
		for (int i = 0; i < Instructors.length; i++){
			if(Instructors[i].equals("(null)")){
				instructors.add("- Unknown -");
			}else {				
				instructors.add(Instructors[i]);
			}
		}
	}
	public ArrayList<CourseSection> getSection(){
		return section;
	}

	public void addSection(String[] s){
			section.add(new CourseSection(getSubject(), getCatalogNumber(), getLocation(), s, Integer.parseInt(s[4]), Integer.parseInt(s[5]), s[s.length-1]));
	}
	public void addStuffForSection(String[] s){
		boolean isDuplicate = false;
		for(String[] q : stuff){
			if(q[q.length-1].equals(s[s.length-1])){
				q[4] = Integer.toString(Integer.parseInt(q[4]) + Integer.parseInt(s[4]));
				q[5] = Integer.toString(Integer.parseInt(q[5]) + Integer.parseInt(s[5]));
				//if teacher is different and not (null); add it to instructors List
				
				isDuplicate = true;
			}
		}
		if (!isDuplicate || stuff.isEmpty()){
			stuff.add(s);
		}
	}
	public Boolean isSpring(){
		return Integer.parseInt(getSemester().substring(3)) == 1;
	}
	public Boolean isSummer(){
		return Integer.parseInt(getSemester().substring(3)) == 4;
	}
	public Boolean isFall(){
		return Integer.parseInt(getSemester().substring(3)) == 7;
	}
	public Boolean isBURNABY(){
		return getLocation().equals("BURNABY");
	}
	public Boolean isSURREY(){
		return getLocation().equals("SURREY");
	}
	public Boolean isHRBRCNTR(){
		return getLocation().equals("HRBRCNTR");
	}
	public Boolean isOtherCampus(){
		return !isBURNABY() && !isHRBRCNTR() && !isSURREY();
	}
	
	
	public void createSections(){
		for (Iterator<String[]> itr = stuff.iterator(); itr.hasNext();){
			addSection(itr.next());
		}
	}

	public void addInstructor(String name){
		instructors.add(name);
	}
	public String getLocation(){
		return location;
	}

	public String getSemester(){
		return semester;
	}
	public ArrayList<String> getInstrcutors(){
		return instructors;
	}

}
