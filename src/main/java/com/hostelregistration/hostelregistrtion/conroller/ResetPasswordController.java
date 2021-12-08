package com.hostelregistration.hostelregistrtion.conroller;

import com.hostelregistration.hostelregistrtion.model.*;
import com.hostelregistration.hostelregistrtion.repository.AdminRepository;
import com.hostelregistration.hostelregistrtion.repository.StudentRepository;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WardenRepository wardenRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/reset")
    ResponseEntity<?> getStudentByEmail(@Validated @RequestBody Reset reset){
        if (reset.getRole().equals("student")) {
            Student student = studentRepository.findByEmail(reset.getEmail());
            if (bCryptPasswordEncoder.matches(reset.getCurrentPassword(), student.getPassword())) {
                System.out.println("Password matches");
                student.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
                studentRepository.save(student);
                return new ResponseEntity<Student>(student, HttpStatus.OK);
            } else {
                System.out.println("Password do not match");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (reset.getRole().equals("warden")) {
            Warden warden = wardenRepository.findByEmail(reset.getEmail());
            if (bCryptPasswordEncoder.matches(reset.getCurrentPassword(), warden.getPassword())) {
                System.out.println("Password matches");
                warden.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
                wardenRepository.save(warden);
                return new ResponseEntity<Warden>(warden, HttpStatus.OK);
            } else {
                System.out.println("Password do not match");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (reset.getRole().equals("admin")) {
            Admin admin = adminRepository.findByEmail(reset.getEmail());
            if (bCryptPasswordEncoder.matches(reset.getCurrentPassword(), admin.getPassword())) {
                System.out.println("Password matches");
                admin.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
                adminRepository.save(admin);
                return new ResponseEntity<Admin>(admin, HttpStatus.OK);
            } else {
                System.out.println("Password do not match");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        Optional<Student> student = Optional.ofNullable(studentRepository.findByEmail(email));
//        return student.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/resetForForgetPassword")
    ResponseEntity<?> resetForForgetPassword(@Validated @RequestBody Reset reset){
        Warden warden = wardenRepository.findByToken(reset.getToken());
        Student student = studentRepository.findByToken(reset.getToken());
        Admin admin = adminRepository.findByToken(reset.getToken());

        if (null != warden) {
            warden.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
            warden.setResetPasswordToken(null);
            wardenRepository.save(warden);
            return new ResponseEntity<Warden>(warden, HttpStatus.OK);
        } else if (null != student) {
            student.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
            student.setResetPasswordToken(null);
            studentRepository.save(student);
            return new ResponseEntity<Student>(student, HttpStatus.OK);
        } else if (null != admin) {
            admin.setPassword(bCryptPasswordEncoder.encode(reset.getNewPassword()));
            admin.setResetPasswordToken(null);
            adminRepository.save(admin);
            return new ResponseEntity<Admin>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
