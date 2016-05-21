package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
 * Department object, contains course offering objects
 *  1 field : department name 
 * 
 * */
public class Deparment {
	private ArrayList<CourseOfferings> courses;
	private String nameDeparment;
	private HashSet<String> course_list;
	
	Deparment(String name) {
		nameDeparment = name;
		courses = new ArrayList<CourseOfferings>();
		course_list = new HashSet<String>();
	}
	
	public void addCourse(CourseOfferings course){
		courses.add(course);
	}
	
	public String getNameDeparment(){
		return nameDeparment;
	}

	public void addToHashSet(String a){
		course_list.add(a);
	}
	
	public void createCourseList(){ //remove duplicate values from course list
		for(Iterator<String> itr = course_list.iterator(); itr.hasNext();){
			courses.add(new CourseOfferings(getNameDeparment(), itr.next()));
		}
		course_list = new HashSet<String>();
	}
	
	public ArrayList<CourseOfferings> getCourses(){
		return courses;
	}
}
