package com.hostelregistration.hostelregistrtion.repository;

import com.hostelregistration.hostelregistrtion.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface HostelRepository extends JpaRepository<Hostel, String> {

    /*@Query("SELECT hostelName FROM Hostel WHERE Hostel.email LIKE %:email%")*/



    List<Hostel> findByEmail(String email);
}
