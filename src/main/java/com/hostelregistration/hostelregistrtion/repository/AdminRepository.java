package com.hostelregistration.hostelregistrtion.repository;


import com.hostelregistration.hostelregistrtion.model.Admin;
import com.hostelregistration.hostelregistrtion.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByEmail (String email);
    Admin getByAdminid (String adminid);

    @Query(value= "SELECT Admin.role FROM Admin where Admin.email = :email", nativeQuery = true)
    String findNameByEmail(@Param("email") String email);

    @Query(value= "SELECT * FROM Admin where Admin.resetPasswordToken = :token", nativeQuery = true)
    Admin findByToken(@Param("token") String token);
}
