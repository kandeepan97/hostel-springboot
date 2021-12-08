package com.hostelregistration.hostelregistrtion.repository;
;
import com.hostelregistration.hostelregistrtion.model.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WardenRepository extends JpaRepository<Warden, String> {
    Warden findByFirstName (String firstName);

    Warden findByEmail(String email);

    Warden getByWardenid(String wardenid);



    @Query(value= "SELECT Warden.role FROM Warden where Warden.email = :email", nativeQuery = true)
    String findNameByEmail(@Param("email") String email);

    public Warden findByResetPasswordToken(String token);

    @Query(value= "SELECT * FROM Warden where Warden.resetPasswordToken = :token", nativeQuery = true)
    Warden findByToken(@Param("token") String token);
}
