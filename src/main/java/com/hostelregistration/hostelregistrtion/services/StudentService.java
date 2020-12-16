package com.hostelregistration.hostelregistrtion.services;


import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Student saveStudent (Student newStudent){
        newStudent.setPassword(bCryptPasswordEncoder.encode(newStudent.getPassword()));
        return studentRepository.save(newStudent);
    }
}
