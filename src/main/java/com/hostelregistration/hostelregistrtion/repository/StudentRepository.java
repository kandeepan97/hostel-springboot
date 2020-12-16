package com.hostelregistration.hostelregistrtion.repository;

import com.hostelregistration.hostelregistrtion.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByFirstName (String name);
    Student findByEmail (String email);
    Student getByStudentid (String studentid);
    Student getByEmail (String email);

    @Query(value= "SELECT Student.role FROM Student where Student.email = :email", nativeQuery = true)
    String findNameByEmail(@Param("email") String email);


}
