package com.example.tutorial3.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.tutorial3.model.StudentModel;

public class InMemoryStudentService implements StudentService{
	private static List<StudentModel> studentList = new ArrayList<StudentModel>();
	
	@Override
	public StudentModel selectStudent(String npm) {
		//implement
		StudentModel student = new StudentModel();
		for(StudentModel tmp : studentList) {
			if(tmp.getNpm().equals(npm)) {
				student = tmp;
			}
		}
		return student;
	}
	
	@Override
	public List<StudentModel> selectAllStudents(){
		return studentList;
	}
	
	@Override
	public void addStudent(StudentModel student) {
		studentList.add(student);
	}
	
	@Override
	public void removeStudent(String npm) {
		Iterator<StudentModel> studentIterator = studentList.iterator();
		while(studentIterator.hasNext()){
			StudentModel tmp = studentIterator.next();
			if(tmp.getNpm().equals(npm)){
				studentIterator.remove();
			}
		}
	}

}
