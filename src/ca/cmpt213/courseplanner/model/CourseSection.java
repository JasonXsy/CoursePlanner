package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * CourseSection inherent from Course class, which is inherented from CourseOffering class
 * 
 * 3 fields added, enrolment_capacity, enrolment_total, component_code
 * 
 * */

public class CourseSection extends Course implements Iterable<CourseSection>{

	private int enrolment_capacity;
	private int enrolment_total; 
	private String component_code;
		
	
	CourseSection(String Subject, String catalogNum, String location, String[] stuff, int cap, int total, String code) {
		super(Subject, catalogNum, stuff);
		setEnrolment_capacity(cap);
		setEnrolment_total(total);
		setComponent_code(code);
	}



	public int getEnrolment_capacity() {
		return enrolment_capacity;
	}


	public void setEnrolment_capacity(int enrolment_capacity) {
		this.enrolment_capacity = enrolment_capacity;
	}


	public int getEnrolment_total() {
		return enrolment_total;
	}


	public void setEnrolment_total(int enrolment_total) {
		this.enrolment_total = enrolment_total;
	}


	public String getComponent_code() {
		return component_code;
	}


	public void setComponent_code(String component_code) {
		this.component_code = component_code;
	}



	@Override
	public Iterator<CourseSection> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getInstrcutors(){
		System.out.println("how many teachers "+instructors.size());
		for (Iterator<String> itr = instructors.iterator(); itr.hasNext();){
			String ins = itr.next();
			System.out.println(ins);
			if (ins.equals(null)){
				itr.remove();
			}
			
		}
		return instructors;
	}
}
