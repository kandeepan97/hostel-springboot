package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Hostel;
import com.hostelregistration.hostelregistrtion.model.Registration;
import com.hostelregistration.hostelregistrtion.model.Room;
import com.hostelregistration.hostelregistrtion.repository.RegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private RegistrationRepository registrationRepository;

    public RegistrationController(RegistrationRepository registrationRepository) {
        super();
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/registrations")
    Collection<Registration> registrations() {
        return registrationRepository.findAll();
    }

    @PostMapping("/registration")
    ResponseEntity<Registration> registerRoom(@Validated @RequestBody Registration registration) throws URISyntaxException {
        Registration result = registrationRepository.save(registration);
        return ResponseEntity.created(new URI("/api/registration" + result.getREGISTRATIONID())).body(result);

    }
    @GetMapping("/registrations/{status}")
    ResponseEntity<?> getregistrations(@PathVariable String status){
        List<Registration> registration =registrationRepository.findByStatus(status);
        return new ResponseEntity<List<Registration>>(registration, HttpStatus.OK);
    }

}

