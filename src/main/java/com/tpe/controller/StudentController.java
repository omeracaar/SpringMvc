package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.repository.StudentRepository;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller//@RestController
@RequestMapping("/students")//http://localhost:8080/SpringMvc/students
//class level:tüm metodlar için geçerli
//method level:sadece o method için geçerli
public class StudentController {

    @Autowired
    private StudentService service;
    //controller requeste göre modelandview(data+view name) ya da String olarak sadece view name döner.
    @GetMapping("/hi")//http://localhost:8080/SpringMvc/students/hi
    public ModelAndView sayHi(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message","Hi;");
        mav.addObject("messagebody","I'm a Student Management System");
        mav.setViewName("hi");//hi.jsp
        return mav;
    }
    //view resolver mav içindeki model ı view name verilen dosyayı bulup içine bind eder.

    //1-Student Creation
    //http://localhost:8080/SpringMvc/students/new
    //kullanicidan bilgileri almak icin form gonderelim
    @GetMapping("/new")
    public String sendStudentForm(@ModelAttribute("student")Student student){
        return "studentForm";
    }

    //@ModelAttribute view katmani ile controller arasinda data transferini saglar


    //studenti DB ye kaydedince tum ogrencileri listeleyelim
    //http://localhost:8080/SpringMvc/students/saveStudent+POST
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student){

        //service de student i kaydet
        service.saveStudent(student);
        return "redirect:/students";//http://localhost:8080/SpringMvc/students/students bu istege yonlendirelim
    }

    //http://localhost:8080/SpringMvc/students/saveStudent+GET
    //2-list all student
    @GetMapping
    public ModelAndView listAllStudents(){
        List<Student> studentList=service.getAllStudent();
        ModelAndView mav=new ModelAndView();
        mav.addObject("students",studentList);
        mav.setViewName("students");
        return mav;
    }

    //3-update student
    //http://localhost:8080/SpringMvc/students/update?id=1
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id")Long id, Model model){
        Student foundStudent=service.getStudentById(id);
        model.addAttribute("student",foundStudent);
        return "studentForm";
    }

    //2.Yol
   // @GetMapping("/update")
   // public String showUpdateForm(@RequestParam("id")Long id, Model model){
   //     Student foundStudent=service.getStudentById(id);
   //     model.addAttribute("student",foundStudent);
   //     return "studentForm";
   // }


}
