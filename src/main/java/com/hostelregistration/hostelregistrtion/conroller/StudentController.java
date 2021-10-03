package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Hostel;
import com.hostelregistration.hostelregistrtion.model.Room;
import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.payload.JWTLoginSucessResponse;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.RoomRepository;
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

    @Autowired
    private RoomRepository roomRepository;


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


    @GetMapping("/student/email/{email}")
    ResponseEntity<?> getRoomIds(@PathVariable String email) {
        Student student = studentRepository.findByEmail(email);
        String faculty = student.getFaculty();
        String gender = student.getGender();
        String year = student.getYear();

        List<Room> findByFaculty = roomRepository.findAllByFaculty(faculty);
        List<Room> findByGender = roomRepository.findAllByGender(gender);
        List<Room> findByYear = roomRepository.findAllByYear(year);

        List<Room> facultyAndGender = new ArrayList<>();

        for (int i = 0; i < findByFaculty.size(); i++) {
            for (int j = 0; j < findByGender.size(); j++) {
                if (findByFaculty.get(i).getROOMID().equals(findByGender.get(j).getROOMID())) {
                    facultyAndGender.add(findByFaculty.get(i));
                    break;
                }
            }
        }

        List<String> roomIds = new ArrayList<>();
        List<Room> allRoomDetails = new ArrayList<>();

        for (int i = 0; i < facultyAndGender.size(); i++) {
            for (int j = 0; j < findByYear.size(); j++) {
                if (facultyAndGender.get(i).getROOMID().equals(findByYear.get(j).getROOMID())) {
                    roomIds.add(facultyAndGender.get(i).getROOMID());
                    allRoomDetails.add(facultyAndGender.get(i));
                    break;
                }
            }
        }

        return new ResponseEntity<List<Room>>(allRoomDetails, HttpStatus.OK);

    }

    @GetMapping("/student/emails/{email}")
    ResponseEntity<?> getStudentByEmail(@PathVariable String email){
        Optional<Student> student = Optional.ofNullable(studentRepository.findByEmail(email));
        return student.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}











