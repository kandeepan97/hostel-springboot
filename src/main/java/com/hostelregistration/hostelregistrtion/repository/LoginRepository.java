package com.hostelregistration.hostelregistrtion.repository;


import com.hostelregistration.hostelregistrtion.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends JpaRepository<Login, String> {

    @Query(value= "SELECT Login.role FROM Login where Login.email = :email", nativeQuery = true)
    String findNameByEmail(@Param("email") String email);

}
