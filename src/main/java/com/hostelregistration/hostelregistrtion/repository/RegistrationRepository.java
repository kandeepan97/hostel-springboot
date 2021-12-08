package com.hostelregistration.hostelregistrtion.repository;

import com.hostelregistration.hostelregistrtion.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    List<Registration> findByStatus(String status);

    Optional<Registration> findByEmail(String email);
}
