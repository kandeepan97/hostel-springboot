package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Hostel;
import com.hostelregistration.hostelregistrtion.model.Registration;
import com.hostelregistration.hostelregistrtion.model.Room;
import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.repository.RegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
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

    @GetMapping("/registrationsOfWarden/{email}")
    Collection<Registration> registrationsOfWarden(@PathVariable String email) {
        List<Registration> allAvailableRegistrations = registrationRepository.findAll();
        List<Registration> result = new LinkedList<>();
        for (Registration each : allAvailableRegistrations) {
            if (each.getRoom().getHostel().getEmail().equals(email)) {
                result.add(each);
            }
        }
        return result;
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

    @PutMapping("registration/accepted/{id}")
    ResponseEntity<Registration> approveRegistration(@PathVariable Integer id) throws URISyntaxException{
        Optional<Registration> registration =registrationRepository.findById(id);
        if(registration.get() != null){
            System.out.println(registration.get().getStatus());
            registration.get().setStatus("ACCEPTED");
            System.out.println(registration.get().getStatus());
            registrationRepository.save(registration.get());
        }
        else{
            System.out.println("NOT WORKING");
        }
        return ResponseEntity.ok().body(registration.get());
    }

    @PutMapping("registration/rejected/{id}")
    ResponseEntity<Registration> rejectRegistration(@PathVariable Integer id) throws URISyntaxException{
        Optional<Registration> registration =registrationRepository.findById(id);
        if(registration.get() != null){
            System.out.println(registration.get().getStatus());
            registration.get().setStatus("REJECTED");
            System.out.println(registration.get().getStatus());
            registrationRepository.save(registration.get());
        }
        else{
            System.out.println("NOT WORKING");
        }
        return ResponseEntity.ok().body(registration.get());
    }

    @GetMapping("/registration/{id}")
    ResponseEntity<?> getRegistration(@PathVariable Integer id){
        Optional<Registration> registration =registrationRepository.findById(id);
        return registration.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registrationss/{email}")
    ResponseEntity<?> getRegistrationsByEmail(@PathVariable String email){
        Optional<Registration> registration =registrationRepository.findByEmail(email);
        return registration.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

