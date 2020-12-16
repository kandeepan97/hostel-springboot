package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.payload.JWTLoginSucessResponse;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.StudentRepository;
import com.hostelregistration.hostelregistrtion.security.JwtTokenProvider;
import com.hostelregistration.hostelregistrtion.services.MapValidationErrorService;
import com.hostelregistration.hostelregistrtion.services.StudentService;
import com.hostelregistration.hostelregistrtion.validater.StudentValidater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api")
public class StudentController {
    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private StudentValidater studentValidater;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/students")
    Collection<Student> students(){

        return studentRepository.findAll();
    }

    @GetMapping("/student/{id}")
    ResponseEntity<?> getStudent(@PathVariable String id){
        Optional<Student> student =studentRepository.findById(id);
        return student.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PutMapping("/student")
    ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student){
        Student result= studentRepository.save(student);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/student/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable String id){
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody Student student,BindingResult result){

        studentValidater.validate(student,result);

        System.out.println(result.hasErrors());

       /* if (result.hasErrors()) {

            List<String> errors = new ArrayList<String>();
            for (ObjectError res : result.getFieldErrors()) {
                errors.add(res.getDefaultMessage());
            }
            throw new RuntimeException(errors.toString());
        }

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;

        Student newStudent = studentService.saveStudent(student);

        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED); */

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;

        Student newStudent = studentService.saveStudent(student);

        return  new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
    }

 /*   @PostMapping("/loginstudent")
    public ResponseEntity<?> authenticateStudent(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
         if (errorMap != null ) return errorMap;

        Authentication authentication  = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateTokenStudent(authentication);
        return ResponseEntity.ok(new JWTLoginSucessResponse(true,jwt ,studentRepository.findNameByEmail(loginRequest.getEmail()) ));


  */
    }








