package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/*CoureseOffering class contains Courses objects
 * 2 field, subject and catalogNumber
 * 
 * also determine whether it's a graduate course
 * 
 * */
public class CourseOfferings {
	private Boolean isGrad = false;
	protected String subject;
	protected String catalogNumber;
	protected ArrayList<Course> courses;
	private ArrayList<String[]> semester_location_instructors; 

	public CourseOfferings(String sub, String cata){
		
		subject = sub; 
		catalogNumber = cata; 
		courses = new ArrayList<Course>();
		semester_location_instructors = new ArrayList<String[]>();
		setIsGrad(getCatalogNumber());
	}
	
	public CourseOfferings() {
	}

	public void addSemester_Location_Instrcutors(String[] s){
		boolean isDuplicate = false;
		for(String[] q : semester_location_instructors){
			//semester, location
			if (q[0].equals(s[0]) && q[3].equals(s[3] )){
				//find if is different instructor
				for(int i = 6; i < q.length - 1; i++){
					for (int j = 6; j < s.length - 1; j ++){
						if(i == j && !s[j].equals("(null)") && !q[i].equals(s[j])){
							ArrayList<String> temp = new ArrayList<String>(
									Arrays.asList(q));
							temp.add(s.length-1, s[j]);
							q = temp.toArray(q);
						}
					}
				}
				
				isDuplicate = true;
			}
		}
		if(!isDuplicate || semester_location_instructors.isEmpty()){
			semester_location_instructors.add(s);
		}
	}
	@Override
	public String toString(){
		return getSubject() + "  " + getCatalogNumber();
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getCatalogNumber(){
		return catalogNumber;
	}
	
	public void addCourse(Course course){
		courses.add(course);
	}
	
	public ArrayList<Course> getCourse(){
		return courses;
	}
	public void createCourses(){
		for(Iterator<String[]> itr = semester_location_instructors.iterator(); itr.hasNext();){
			courses.add(new Course(getSubject(), getCatalogNumber(), itr.next()));
		}
		semester_location_instructors = new ArrayList<String[]>();
	}

	public Boolean getIsGrad() {
		return isGrad;
	}

	public void setIsGrad(String catalo) {
		try{
			int first_char = Integer.parseInt(catalo.substring(0,1));
			if (first_char >= 5){
				isGrad = true;
			}
			
		}catch (NumberFormatException e){
			isGrad = false;
		}
		
	}
	
	
}
