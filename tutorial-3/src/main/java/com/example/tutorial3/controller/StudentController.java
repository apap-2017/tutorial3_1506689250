package com.example.tutorial3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import com.example.tutorial3.model.StudentModel;
import java.util.List;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "gpa", required = true) double gpa){
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	/*
	@RequestMapping("/student/view")
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}*/
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping (value = { "/student/view" , "student/view/{npm}" })
	public String viewStudent( @PathVariable Optional<String> npm, Model model ) {
		if (npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student.getNpm() == null ) {
				return "studentnotfound";
			}
			else {
				model.addAttribute ( "student" , student);
			}
		}else{
			return "npmnotfound";
		}
		return "view";
	}
	
	@RequestMapping (value = { "/student/delete" , "student/delete/{npm}" })
	public String deleteStudent( @PathVariable Optional<String> npm, Model model ) {
		if (npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student.getNpm() == null ) {
				model.addAttribute ( "result" , "Maaf, data tidak ditemukan");
			}
			else {
				studentService.removeStudent(npm.get());
				model.addAttribute ( "result" , "Data berhasil dihapus.");
			}
		}else{
			model.addAttribute ( "result" , "Error: Maaf NPM yang Anda masukkan kosong atau tidak ditemukan." );
		}
		return "delete";
	}

}
